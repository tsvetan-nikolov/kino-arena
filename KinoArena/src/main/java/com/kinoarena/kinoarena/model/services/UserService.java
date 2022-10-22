package com.kinoarena.kinoarena.model.services;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.MovieWithoutUsersDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.ChangePasswordDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.EditProfileDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.LoginDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.City;
import com.kinoarena.kinoarena.model.entities.Movie;
import com.kinoarena.kinoarena.model.entities.User;
import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;
import com.kinoarena.kinoarena.model.repositories.CityRepository;
import com.kinoarena.kinoarena.model.repositories.MovieRepository;
import com.kinoarena.kinoarena.model.repositories.UserRepository;
import com.kinoarena.kinoarena.util.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserInfoResponse register(RegisterRequestDTO user) {
        validateRegisterInformation(user);

        User createUserToRegister = createUserToRegister(user);
        return modelMapper.map(createUserToRegister, UserInfoResponse.class);
    }

    private User createUserToRegister(RegisterRequestDTO user) {
        User newUser = modelMapper.map(user, User.class);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));

        String cityName = user.getCityName();
        City city = cityRepository.findFirstByName(cityName).orElse(cityRepository.save(new City(cityName)));
        newUser.setCity(city);

        userRepository.save(newUser);
        return newUser;
    }

    private void validateRegisterInformation(RegisterRequestDTO user) {
        Validator.validatePassword(user.getPassword());

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new UnauthorizedException("Passwords don't match!");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new BadRequestException("User with email " + user.getEmail() + " already exists!");
        }
    }

    public UserWithoutPasswordDTO login(LoginDTO dto) {
        String email = dto.getEmail();
        String password = dto.getPassword();

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            User u = user.get();

            if (passwordEncoder.matches(password, u.getPassword())) {
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

        if (user.isPresent()) {
            User u = user.get();

            if (passwordEncoder.matches(oldPassword, u.getPassword())) {
                if (Validator.validatePassword(newPassword)) {
                    if (newPassword.equals(confirmPassword)) {

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
        if (user.isPresent()) {
            User u = user.get();

            setNewValues(dto, u);
            userRepository.save(u);

            return modelMapper.map(u, UserWithoutPasswordDTO.class);
        } else {
            throw new UnauthorizedException("User not found");
        }
    }

    private void setNewValues(EditProfileDTO dto, User u) {
        if (!dto.getFirstName().isEmpty()) {
            u.setFirstName(dto.getFirstName());
        }
        if (dto.getMiddleName() != null) {
            u.setMiddleName(dto.getMiddleName());
        }
        if (dto.getLastName() != null) {
            u.setLastName(dto.getLastName());
        }
        //todo validate phone number
        if (dto.getPhoneNumber() != null) {
            u.setPhoneNumber(dto.getPhoneNumber());
        }
        //todo validate DoB
        if (dto.getDateOfBirth() != null) {
            u.setDateOfBirth(dto.getDateOfBirth());
        }
        if (dto.getAddress() != null) {
            u.setAddress(dto.getAddress());
        }
        if (dto.getCity() != null) {
            Optional<City> city = cityRepository.findFirstByName(dto.getCity());

            if (city.isPresent()) {
                City c = city.get();
                u.setCity(c);
            }
            //todo if city is not in db add it
        }
    }

    @Transactional
    public MovieResponseDTO addRemoveFavouriteMovie(int movieId, HttpSession s) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie doesn't exist"));


        User u = userRepository.findById((int) s.getAttribute("USER_ID" /*TODO constants*/))
                .orElseThrow(() -> new NotFoundException("Can't add movie to favourites"));


        boolean movieAlreadyInFavourites = u.getFavouriteMovies().contains(movie) || movie.getUsers().contains(u);
        MovieResponseDTO movieResponse = new MovieResponseDTO();
        if (movieAlreadyInFavourites) {
            u.getFavouriteMovies().remove(movie);
            movie.getUsers().remove(u);
            movieResponse.setRemoved(true);
            movieResponse.setMessage("Movie removed from favourites");
        } else {
            u.getFavouriteMovies().add(movie);
            movie.getUsers().add(u);
            movieResponse.setRemoved(false);
            movieResponse.setMessage("Movie added to favourites");
        }

        return movieResponse;
    }

    public UserWithoutPasswordDTO showFavouriteMovies(int uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new NotFoundException("User not found"));
        UserWithoutPasswordDTO dto = modelMapper.map(user, UserWithoutPasswordDTO.class);
        dto.setFavouriteMovies(user.getFavouriteMovies()
                .stream()
                .map(m -> modelMapper.map(m, MovieWithoutUsersDTO.class))
                .collect(Collectors.toList()));
        return dto;
    }
}