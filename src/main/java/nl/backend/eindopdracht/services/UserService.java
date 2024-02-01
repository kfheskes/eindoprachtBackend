package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.role.RoleOutputDto;
import nl.backend.eindopdracht.dtos.user.UserInputDto;
import nl.backend.eindopdracht.dtos.user.UserOutputDto;
import nl.backend.eindopdracht.exceptions.RecordNotFoundException;
import nl.backend.eindopdracht.models.*;

import nl.backend.eindopdracht.repositories.*;
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

    private final ManagerAccountService managerAccountService;

    private final ManagerAccountRepository managerAccountRepository;

    private final CustomerAccountService customerAccountService;

    private final CustomerAccountRepository customerAccountRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, RoleRepository roleRepository, EmployeeAccountService employeeAccountService, EmployeeAccountRepository employeeAccountRepository, ManagerAccountService managerAccountService, ManagerAccountRepository managerAccountRepository, CustomerAccountService customerAccountService, CustomerAccountRepository customerAccountRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
        this.employeeAccountService = employeeAccountService;
        this.employeeAccountRepository = employeeAccountRepository;
        this.managerAccountService = managerAccountService;
        this.managerAccountRepository = managerAccountRepository;
        this.customerAccountService = customerAccountService;
        this.customerAccountRepository = customerAccountRepository;
    }

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(userTransferToDto(user));
        }
        return collection;
    }


    public UserOutputDto getUser(long id) {
        Optional<User> getUser = userRepository.findById(id);
        if (getUser.isPresent()) {
            User u = getUser.get();
            UserOutputDto dto = userTransferToDto(u);
            return dto;
        } else {
            throw new RecordNotFoundException("No User found with id: " + id);
        }
    }

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
            if (inputDto.getFName() != null) {
                userUpdate.setFName(inputDto.getFName());
            }
            if (inputDto.getMName() != null) {
                userUpdate.setMName(inputDto.getMName());
            }
            if (inputDto.getLName() != null) {
                userUpdate.setLName(inputDto.getLName());
            }
            if (inputDto.getDob() != null) {
                userUpdate.setDob(inputDto.getDob());
            }
            if (inputDto.getAddress() != null) {
                userUpdate.setAddress(inputDto.getAddress());
            }
            if (inputDto.getHouseNumber() != null) {
                userUpdate.setHouseNumber(inputDto.getHouseNumber());
            }
            if (inputDto.getZipcode() != null) {
                userUpdate.setZipcode(inputDto.getZipcode());
            }
            if (inputDto.getPNumber() != null) {
                userUpdate.setPNumber(inputDto.getPNumber());
            }
            if (inputDto.getEmail() != null) {
                userUpdate.setEmail(inputDto.getEmail());
            }
            if (inputDto.getResidence() != null) {
                userUpdate.setResidence(inputDto.getResidence());
            }

            User updateUser = userRepository.save(userUpdate);
            return userTransferToDto(updateUser);
        }
    }


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
        dto.setFName(user.getFName());
        dto.setMName(user.getMName());
        dto.setLName(user.getLName());
        dto.setDob(user.getDob());
        dto.setAddress(user.getAddress());
        dto.setHouseNumber(user.getHouseNumber());
        dto.setZipcode(user.getZipcode());
        dto.setPNumber(user.getPNumber());
        dto.setEmail(user.getEmail());


        if (user.getEmployeeAccount() != null) {
            dto.setEmployeeAccountOutputDto(employeeAccountService.employeeAccountTransferToDto(user.getEmployeeAccount()));
        }
        if (user.getManagerAccount() != null) {
            dto.setManagerAccountOutputDto(managerAccountService.managerAccountTransferToDto(user.getManagerAccount()));
        }
        if (user.getCustomerAccount() != null) {
            dto.setCustomerAccountOutputDto(customerAccountService.customerAccountTransferCustomerAccountOutputDto(user.getCustomerAccount()));
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
        user.setFName(userDto.getFName());
        user.setMName(userDto.getMName());
        user.setLName(userDto.getLName());
        user.setDob(userDto.getDob());
        user.setAddress(userDto.getAddress());
        user.setAddress(userDto.getHouseNumber());
        user.setZipcode(userDto.getZipcode());
        user.setPNumber(userDto.getPNumber());
        user.setEmail(userDto.getEmail());


        Set<Role> roles = new HashSet<>();

        for (String roleName : userDto.getRoles()) {
            Role role = roleRepository.findByRoleName("ROLE_" + roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);

        }


        return userRepository.save(user);
    }

    public void assignUserToEmployeeAccount(String userName, long employeeId) {
        Optional<User> optionalUser = userRepository.findByUsername(userName);
        Optional<EmployeeAccount> optionalEmployeeAccount = employeeAccountRepository.findById(employeeId);

        if (optionalUser.isPresent() && optionalEmployeeAccount.isPresent()) {
            User user = optionalUser.get();
            EmployeeAccount ea = optionalEmployeeAccount.get();
            user.setEmployeeAccount(ea);

            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("no user or employeeAccount is found");
        }
    }

    public void assignUserToManagerAccount(String userName, long managerId) {
        Optional<User> oUser = userRepository.findByUsername(userName);
        Optional<ManagerAccount> oManagerAccount = managerAccountRepository.findById(managerId);
        if (oUser.isPresent() && oManagerAccount.isPresent()) {
            User user = oUser.get();
            ManagerAccount managerAccount = oManagerAccount.get();
            user.setManagerAccount(managerAccount);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("no user or managerAccount is found");
        }
    }

    public void assignUserToCustomerAccount(String userName, long customerId) {
        Optional<User> oUser = userRepository.findByUsername(userName);
        Optional<CustomerAccount> oCustomerAccount = customerAccountRepository.findById(customerId);
        if (oUser.isPresent() && oCustomerAccount.isPresent()) {
            User user = oUser.get();
            CustomerAccount customerAccount = oCustomerAccount.get();
            user.setCustomerAccount(customerAccount);
            userRepository.save(user);
        } else {
            throw new RecordNotFoundException("no user or managerAccount is found");
        }
    }

}