package nl.backend.eindoprdracht.services;

import jakarta.transaction.Transactional;
import nl.backend.eindoprdracht.dtos.role.RoleOutputDto;
import nl.backend.eindoprdracht.dtos.user.UserInputDto;
import nl.backend.eindoprdracht.dtos.user.UserOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.EmployeeAccount;
import nl.backend.eindoprdracht.models.Role;
import nl.backend.eindoprdracht.models.User;

import nl.backend.eindoprdracht.repositories.EmployeeAccountRepository;
import nl.backend.eindoprdracht.repositories.RoleRepository;
import nl.backend.eindoprdracht.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;
    private final RoleRepository roleRepository;

    private final EmployeeAccountService employeeAccountService;

    private final EmployeeAccountRepository employeeAccountRepository;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, RoleRepository roleRepository, EmployeeAccountService employeeAccountService, EmployeeAccountRepository employeeAccountRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.employeeAccountService = employeeAccountService;
        this.employeeAccountRepository = employeeAccountRepository;
    }

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(userTransferToDto(user));
        }
        return collection;
    }


    public UserOutputDto getUser(long id){
        Optional<User> getUser = userRepository.findById(id);
        if(getUser.isPresent()){
            User u = getUser.get();
            UserOutputDto dto = userTransferToDto(u);
            return dto;
        } else {
            throw new RecordNotFoundException("No User found with id: " + id);
        }
    }

    @Transactional
    public UserOutputDto createUser(UserInputDto userDto) {
        User newUser = dtoTransfertoUser(userDto);
        newUser = userRepository.save(newUser);
        if (newUser.getEmployeeAccount() != null) {
            employeeAccountRepository.save(newUser.getEmployeeAccount());
        }
        return userTransferToDto(newUser);
    }


    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public UserOutputDto updateUser(Long userId, UserInputDto inputDto) {
        Optional<User> getUser = userRepository.findById(userId);
        if (!userRepository.existsById(userId)) {
            throw new RecordNotFoundException("No user found by id" + userId);
        } else {
            User userUpdate = getUser.get();
            if (inputDto.getUsername() != null) {
                userUpdate.setUsername(inputDto.getUsername());
            }
            if (inputDto.getPassword() != null) {
                userUpdate.setPassword(passwordEncoder.encode(inputDto.getPassword()));
            }
            User updateUser = userRepository.save(userUpdate);
            return userTransferToDto(updateUser);
        }
    }

    //TODO enabled toevoegen zodat de persoon bestaat in database maar niet meer kan inloggen.

    public void removeRole(Long userId, String roleName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findByRoleName("ROLE_" + roleName.toUpperCase());

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            Role role = optionalRole.get();

            if (user.getRoles().contains(role)) {
                user.getRoles().remove(role);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User does not have role: " + roleName);
            }
        } else {
            throw new RuntimeException("User or Role not found");
        }
    }


    public void addRoleToUser(long userId, String roleName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findByRoleNameContainingIgnoreCase(roleName);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            Role role = optionalRole.get();

            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
                userRepository.save(user);
            } else {
                throw new RuntimeException("User already has role: " + roleName);
            }
        } else {
            throw new RuntimeException("User or Role not found");
        }
    }


    public UserOutputDto userTransferToDto(User user) {

        UserOutputDto dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEnabled(user.isEnabled());


        if(user.getEmployeeAccount() != null) {
            dto.setEmployeeAccountOutputDto(employeeAccountService.employeeAccountTransferToDto(user.getEmployeeAccount()));
        }
        if (user.getRoles() != null) {
            Set<RoleOutputDto> roleOutputDtos = new HashSet<>();
            for (Role role : user.getRoles()) {
                roleOutputDtos.add(roleService.roleTransferToDto(role));
            }
            dto.setRoles(roleOutputDtos);
        }
        return dto;
    }

    public User dtoTransfertoUser(UserInputDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(userDto.isEnabled());

        Set<Role> roles = new HashSet<>();
        EmployeeAccount employeeAccount = null;

        for (String roleName : userDto.getRoles()) {
            Role role = roleRepository.findByRoleName("ROLE_" + roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
            if ("ROLE_EMPLOYEE".equals(roleName)) {
                employeeAccount = new EmployeeAccount();
                employeeAccount.setFName(userDto.getFName());

                employeeAccount.setUser(user); /
            }
        }
        user.setRoles(roles);

        if (employeeAccount != null) {
            user.setEmployeeAccount(employeeAccount);
            employeeAccountRepository.save(employeeAccount); // Sla EmployeeAccount op
        }

        return user;
    }





}