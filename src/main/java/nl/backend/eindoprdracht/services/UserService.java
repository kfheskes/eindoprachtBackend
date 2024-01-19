package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.user.UserInputDto;
import nl.backend.eindoprdracht.dtos.user.UserOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.User;
import nl.backend.eindoprdracht.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Lazy
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<UserOutputDto> getUsers() {
        List<UserOutputDto> collection = new ArrayList<>();
        List<User> list = userRepository.findAll();
        for (User user : list) {
            collection.add(userTransferToDto(user));
        }
        return collection;
    }

    public UserOutputDto getUser(String username) {
        UserOutputDto dto = new UserOutputDto();
        Optional<User> user = userRepository.findById(username);
        if (user.isPresent()){
            dto = userTransferToDto(user.get());
        }else {
            throw new RecordNotFoundException( username);
        }
        return dto;
    }

    public boolean userExists(String username) {
        return userRepository.existsById(username);
    }

    public String createUser(UserInputDto userDto) {

        User newUser = new User();
        newUser.setUsername(userDto.getUsername());
        newUser.setPassword(passwordEncoder.encode(userDto.getPassword()));

        userRepository.save(dtoTransfertoUser(userDto));
        return newUser.getUsername();
    }

    public void deleteUser(String username) {
        userRepository.deleteById(username);
    }

    public void updateUser(String username, UserInputDto newUser) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException();
        User user = userRepository.findById(username).get();
        user.setPassword(newUser.getPassword());
        userRepository.save(user);
    }

    public Set<Role> getRoles(String username) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        UserInputDto userDto = fromUser(user);
        return userDto.getAuthorities();
    }

    public void addAuthority(String username, String authority) {

        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        user.addAuthority(new Authority(username, authority));
        userRepository.save(user);
    }

    public void removeAuthority(String username, String authority) {
        if (!userRepository.existsById(username)) throw new RecordNotFoundException(username);
        User user = userRepository.findById(username).get();
        Authority authorityToRemove = user.getAuthorities().stream().filter((a) -> a.getAuthority().equalsIgnoreCase(authority)).findAny().get();
        user.removeAuthority(authorityToRemove);
        userRepository.save(user);
    }

    public  UserOutputDto userTransferToDto(User user){

        UserOutputDto dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword());


        return dto;
    }

    public User dtoTransfertoUser(UserInputDto userDto) {

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());


        return user;
    }


}