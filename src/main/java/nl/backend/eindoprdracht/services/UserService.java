package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.role.RoleOutputDto;
import nl.backend.eindoprdracht.dtos.user.UserInputDto;
import nl.backend.eindoprdracht.dtos.user.UserOutputDto;
import nl.backend.eindoprdracht.models.Role;
import nl.backend.eindoprdracht.models.User;

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


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(userTransferToDto(user));
        }
        return collection;
    }


    public UserOutputDto createUser(UserInputDto userDto) {
        User newUser = dtoTransfertoUser(userDto);

        userRepository.save(newUser);
        return userTransferToDto(newUser);
    }

//    public void deleteUser(String username) {
//        userRepository.deleteById(username);
//    }

//    public void updateUser(String username, UserInputDto newUser) {
//        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
//        User user = userRepository.findById(username).get();
//        user.setPassword(newUser.getPassword());
//        userRepository.save(user);
//    }
//
//    public Set<Role> getRoles(String username) {
//        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
//        User user = userRepository.findById(username).get();
//        UserInputDto userDto = fromUser(user);
//        return userDto.getAuthorities();
//    }
//
//    public void addAuthority(String username, String authority) {
//
//        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
//        User user = userRepository.findById(username).get();
//        user.addAuthority(new Authority(username, authority));
//        userRepository.save(user);
//    }
//
//    public void removeAuthority(String username, String authority) {
//        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
//        User user = userRepository.findById(username).get();
//        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
//        user.removeAuthority(authorityToRemove);
//        userRepository.save(user);
//    }

    public  UserOutputDto userTransferToDto(User user){

        UserOutputDto dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());

        if(user.getRoles() != null){
            Set<RoleOutputDto>  roleOutputDtos = new HashSet<>();
            for(Role role : user.getRoles()) {
                roleOutputDtos.add(roleService.roleTransferToDto(role));
            }
            dto.setRoles(roleOutputDtos);
        }

        return dto;
    }

    public User dtoTransfertoUser(UserInputDto userDto) {

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.password));

        Set<Role> roles = new HashSet<>();
        for (String roleName : userDto.getRoles()) {
            Role role = roleRepository.findByRolename("ROLE_" + roleName)
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            roles.add(role);
        }
        user.setRoles(roles);

        return user;
    }

    public void assignRolesToUser(long userId, String roleName) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Role> optionalRole = roleRepository.findById("ROLE_ " + roleName);

        if (optionalUser.isPresent() && optionalRole.isPresent()) {
            User user = optionalUser.get();
            user.getRoles().add(optionalRole.get());
            userRepository.save(user);
        } else {
            throw new RuntimeException("User or Role not found");
        }
    }



}