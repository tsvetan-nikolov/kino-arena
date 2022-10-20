package com.kinoarena.kinoarena.service;

import com.kinoarena.kinoarena.model.dto.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.dto.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.entities.City;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserInfoResponse register(RegisterRequestDTO user) {
        if (!user.getPassword().equals(user.getConfirmPassword())) {
//            throw new PasswordMismatchException(PASSWORD_MISMATCH);
            //TODO throwEm
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
//            throw new DuplicateEmailException(String.format(DUPLICATE_EMAIL, user.getEmail()));
            //TODO well.. ya know
        }

        var newUser = modelMapper.map(user, User.class);

        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        //TODO sett stuff

        newUser.setCity(new City("Sofia pidal aj zapi6i se v bazata molq te"));
        newUser.setPhoneNumber("FakeNumber"); //TODO
        newUser.setAdmin(false);
        newUser.setDateOfBirth(LocalDate.of(1999,1,1));
        userRepository.save(newUser);

        UserInfoResponse userInfoResponse = modelMapper.map(newUser, UserInfoResponse.class);
        return userInfoResponse;
    }
}
