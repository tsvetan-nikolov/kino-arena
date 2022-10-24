package com.kinoarena.kinoarena.model.services;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.FavouriteMovieDTO;
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
import java.time.LocalDate;
import java.util.List;
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
        validateRegisterInformation(user); //TODO finish with validator.validatePass();

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
        if (!dto.getFirstName().equals(u.getFirstName())) {
            u.setFirstName(dto.getFirstName());
        }

        if (!dto.getMiddleName().equals(u.getMiddleName())) {
            u.setMiddleName(dto.getMiddleName());
        }

        if (!dto.getLastName().equals(u.getLastName())) {
            u.setLastName(dto.getLastName());
        }

        if (!dto.getPhoneNumber().equals(u.getPhoneNumber())) {
            if(Validator.validatePhoneNumber(dto.getPhoneNumber())) {
                u.setPhoneNumber(dto.getPhoneNumber());
            } else {
                throw new BadRequestException("Invalid phone number!");
            }
        }

        if (!dto.getDateOfBirth().equals(u.getDateOfBirth())) {
            if(Validator.dateIsValid(dto.getDateOfBirth())) {
                u.setDateOfBirth(dto.getDateOfBirth());
            } else {
                throw new BadRequestException("Invalid date of birth!");
            }
        }

        if (!dto.getAddress().equals(u.getAddress())) {
            u.setAddress(dto.getAddress());
        }

        if (!dto.getCity().equals(u.getCity().getName())) {
            Optional<City> city = cityRepository.findFirstByName(dto.getCity());
            City c = new City();
            if (city.isPresent()) {
                c = city.get();
            } else {
                c.setName(dto.getCity());
                cityRepository.save(c);
            }

            u.setCity(c);
        }
    }

    public MovieInfoDTO addFavouriteMovie(int movieId, HttpSession s) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie doesn't exist"));
        //TODO ENDLESS RECURSION <<<<<<<<<<=============================================================================


        User u = userRepository.findById((int) s.getAttribute("USER_ID" /*constant*/))
                .orElseThrow(() -> new NotFoundException("Can't add movie to favourites"));
        u.getFavouriteMovies().add(movie);
        userRepository.save(u);

        return modelMapper.map(movie, MovieInfoDTO.class);
    }

    public List<FavouriteMovieDTO> showFavouriteMovies(int uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new NotFoundException("User not found"));
        UserWithoutPasswordDTO dto = modelMapper.map(user, UserWithoutPasswordDTO.class);
        dto.setFavouriteMovies(user.getFavouriteMovies()
                .stream()
                .map(m -> modelMapper.map(m, FavouriteMovieDTO.class))
                .collect(Collectors.toList()));
        return dto.getFavouriteMovies();
    }
}