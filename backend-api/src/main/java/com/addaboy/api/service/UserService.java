package com.addaboy.api.service;

import com.addaboy.api.dto.CredentialsDto;
import com.addaboy.api.dto.SignUpDto;
import com.addaboy.api.dto.UserDto;
import com.addaboy.api.entities.User;
import com.addaboy.api.exception.AppException;
import com.addaboy.api.mappers.UserMapper;
import com.addaboy.api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.CharBuffer;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;          // Avoid the passwords to be stored in plain text but hash the passwords to unreadable

    public UserDto findByLogin(String login) {

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        return userMapper.toUserDto(user);
    }

    public UserDto login(CredentialsDto credentialsDto) {
        User user = userRepository.findByLogin(credentialsDto.getLogin())
                .orElseThrow(() -> new AppException("Unknown User", HttpStatus.NOT_FOUND));

        if (passwordEncoder.matches(CharBuffer.wrap(credentialsDto.getPassword()), user.getPassword())) {
            return userMapper.toUserDto(user);
        }

        throw new AppException("Invalid Password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto userDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userDto.getLogin());

        if (optionalUser.isPresent()) {
            throw new AppException("Login already exists", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToUser(userDto);

        user.setPassword(passwordEncoder.encode(CharBuffer.wrap(userDto.getPassword())));       // Store the password in Encoded form (To do not store the password in plain text but hash the password)

        User savedUser = userRepository.save(user);

        return userMapper.toUserDto(user);
    }

}
