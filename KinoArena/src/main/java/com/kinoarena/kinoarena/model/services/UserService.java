package com.kinoarena.kinoarena.model.services;

import com.kinoarena.kinoarena.model.DTOs.user.request.ChangePasswordDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.EditProfileDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.LoginDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.City;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;
import com.kinoarena.kinoarena.model.repositories.CityRepository;
import com.kinoarena.kinoarena.model.repositories.UserRepository;
import com.kinoarena.kinoarena.util.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public UserWithoutPasswordDTO login(LoginDTO dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        Optional<User> user = userRepository.findByEmail(email);

        if(user.isPresent()) {
            User u = user.get();

            if(passwordEncoder.matches(password, u.getPassword())) {
                return modelMapper.map(u, UserWithoutPasswordDTO.class);
            } else {
                throw new UnauthorizedException("Wrong Credentials!");
            }
        } else {
            throw new UnauthorizedException("Wrong Credentials!");
        }
    }

    public UserWithoutPasswordDTO changePassword(int uid, ChangePasswordDTO dto) {
        String oldPassword = dto.getOldPassword();
        String newPassword = dto.getNewPassword();
        String confirmPassword = dto.getConfirmPassword();

        Optional<User> user = userRepository.findById(uid);

        if(user.isPresent()) {
            User u = user.get();

            if(passwordEncoder.matches(oldPassword, u.getPassword())) {
                if(Validator.validatePassword(newPassword)) {
                    if(newPassword.equals(confirmPassword)) {

                        u.setPassword(passwordEncoder.encode(newPassword));
                        userRepository.save(u);

                        return modelMapper.map(u, UserWithoutPasswordDTO.class);
                    } else {
                        throw new UnauthorizedException("Password mismatch!");
                    }
                } else {
                    throw new UnauthorizedException("Invalid new password!");
                }
            } else {
                throw new UnauthorizedException("Wrong password!");
            }
        } else {
            throw new NotFoundException("User not found!");
        }
    }

    public UserWithoutPasswordDTO editProfile(EditProfileDTO dto, int uid) {
        Optional<User> user = userRepository.findById(uid);
        if(user.isPresent()) {
            User u = user.get();

            setNewValues(dto, u);
            userRepository.save(u);

            return modelMapper.map(u, UserWithoutPasswordDTO.class);
        } else {
            throw new UnauthorizedException("User not found");
        }
    }

    private void setNewValues(EditProfileDTO dto, User u) {
        if(!dto.getFirstName().isEmpty()) {
            u.setFirstName(dto.getFirstName());
        }
        if(dto.getMiddleName() != null) {
            u.setMiddleName(dto.getMiddleName());
        }
        if(dto.getLastName() != null) {
            u.setLastName(dto.getLastName());
        }
        //todo validate phone number
        if(dto.getPhoneNumber() != null) {
            u.setPhoneNumber(dto.getPhoneNumber());
        }
        //todo validate DoB
        if(dto.getDateOfBirth() != null) {
            u.setDateOfBirth(dto.getDateOfBirth());
        }
        if(dto.getAddress() != null) {
            u.setAddress(dto.getAddress());
        }
        if(dto.getCity() != null) {
            Optional<City> city = cityRepository.findCityByName(dto.getCity());

            if(city.isPresent()) {
                City c = city.get();
                u.setCity(c);
            }
            //todo if city is not in db add it
        }
    }
}