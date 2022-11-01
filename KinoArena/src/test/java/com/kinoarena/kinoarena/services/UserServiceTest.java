package com.kinoarena.kinoarena.services;

import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private GenderRepository genderRepository;
    @Mock
    private JwtService jwtService;
    @InjectMocks
    private UserService userService;

    @BeforeEach

    @Test
    void register() throws Exception {
        RegisterRequestDTO registerRequest = getRegisterRequest();
//        when(validatePassword(registerRequest.getPassword())).thenReturn(true);
        Method createUserToRegister = UserService.class
                .getDeclaredMethod("createUserToRegister", RegisterRequestDTO.class);
        createUserToRegister.setAccessible(true); //samo tuk li e public, Sasho?

        User user  = (User) createUserToRegister.invoke(userService, registerRequest);

    }

    private RegisterRequestDTO getRegisterRequest() {
        RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setFirstName("Ivan");
        registerRequestDTO.setMiddleName("Ivanov");
        registerRequestDTO.setLastName("Ivanov");
        registerRequestDTO.setEmail("van4o@gmail.com");
        registerRequestDTO.setPassword("SomeP4ss");
        registerRequestDTO.setConfirmPassword("SomeP4ss");
        registerRequestDTO.setCityName("Sofia");
        registerRequestDTO.setDateOfBirth(LocalDate.of(1950, 10, 10));
        registerRequestDTO.setGender("male");
        return registerRequestDTO;
    }
}