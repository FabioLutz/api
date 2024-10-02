package org.project.userManagement.service;

import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Optional<UserDto> findUserDtoByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    public Optional<UserDto> findUserDtoByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = UserMapper.INSTANCE.userToUserDto(user);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existsByUsername(String username) {
        return findUserDtoByUsername(username).isPresent();
    }

    public Boolean existsByEmail(String email) {
        return findUserDtoByEmail(email).isPresent();
    }

    public UserDto createUser(CreateUserDto createUserDto) {
        User user = UserMapper.INSTANCE.createUserDtoToUser(createUserDto);
        user = userRepository.save(user);
        return UserMapper.INSTANCE.userToUserDto(user);
    }

    public Boolean deleteUser(DeleteUserDto deleteUserDto) {
        Optional<User> user = findUserByEmail(deleteUserDto.getEmail());
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }
}