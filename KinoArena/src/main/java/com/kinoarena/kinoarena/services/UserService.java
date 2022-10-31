package com.kinoarena.kinoarena.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.kinoarena.kinoarena.model.DTOs.movie.FavouriteMovieDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.ChangePasswordDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.EditProfileDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.*;
import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;
import com.kinoarena.kinoarena.model.repositories.*;
import com.kinoarena.kinoarena.util.Validator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kinoarena.kinoarena.util.constant.AuthConstants.Role.ROLE_ADMIN;
import static com.kinoarena.kinoarena.util.constant.AuthConstants.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final GenderRepository genderRepository;

    private final JwtService jwtService;

    public UserInfoResponse register(RegisterRequestDTO user) {
        validateRegisterInformation(user);

        User userToRegister = createUserToRegister(user);
        userRepository.save(userToRegister);
        return modelMapper.map(userToRegister, UserInfoResponse.class);
    }

    private User createUserToRegister(RegisterRequestDTO u) {
        User user = modelMapper.map(u, User.class);
        user.setPassword(passwordEncoder.encode(u.getPassword()));

        String cityName = u.getCityName();
        City city = cityRepository.findFirstByName(cityName)
                .orElse(cityRepository.save(new City(cityName)));
        user.setCity(city);

        String userGender = u.getGender();
        Gender gender = genderRepository.findFirstByGender(userGender)
                .orElse(genderRepository.save(new Gender(userGender)));
        user.setGender(gender);

        setUserRoles(user);
        return user;
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

//    public UserWithoutPasswordDTO login(LoginDTO dto) {
//        String email = dto.getEmail();
//        String password = dto.getPassword();
//
//        Optional<User> user = userRepository.findByEmail(email);
//
//        if (user.isPresent()) {
//            User u = user.get();
//
//            if (passwordEncoder.matches(password, u.getPassword())) {
//                return modelMapper.map(u, UserWithoutPasswordDTO.class);
//            } else {
//                throw new UnauthorizedException("Wrong Credentials!");
//            }
//        } else {
//            throw new UnauthorizedException("Wrong Credentials!");
//        }
//    }

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
            if (Validator.validatePhoneNumber(dto.getPhoneNumber())) {
                u.setPhoneNumber(dto.getPhoneNumber());
            } else {
                throw new BadRequestException("Invalid phone number!");
            }
        }

        if (!dto.getDateOfBirth().equals(u.getDateOfBirth())) {
            if (Validator.dateIsValid(dto.getDateOfBirth())) {
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

    @Transactional
    public MovieResponseDTO addRemoveFavouriteMovie(int movieId, int userId) {
//        checkIfLoggedIn(user);

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new NotFoundException("Movie doesn't exist"));


        User u = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User doesn't exist"));


        boolean movieAlreadyInFavourites = u.getFavouriteMovies().stream().anyMatch(m -> m.getId() == movieId);
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

//    private static void checkIfLoggedIn(User user) {
//        if (s.getAttribute(LOGGED) != null) {
//            if (!(boolean) s.getAttribute(LOGGED)) {
//                throw new BadRequestException("You must first log in!");
//            }
//        } else {
//            throw new BadRequestException("You must first log in!");
//        }
//    }


    public List<FavouriteMovieDTO> showFavouriteMovies(int uid) {
        User user = userRepository.findById(uid).orElseThrow(() -> new NotFoundException("User not found"));
        UserWithoutPasswordDTO dto = modelMapper.map(user, UserWithoutPasswordDTO.class);
        dto.setFavouriteMovies(user.getFavouriteMovies()
                .stream()
                .map(m -> modelMapper.map(m, FavouriteMovieDTO.class))
                .collect(Collectors.toList()));
        return dto.getFavouriteMovies();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @PostConstruct
    public void initRoles() {
        if (roleRepository.count() > 0) {
            return;
        }

        roleRepository.saveAll(
                List.of(new Role(ROLE_ADMIN), new Role(ROLE_USER)));
    }

    private void setUserRoles(User user) {
        List<String> userRoles = new ArrayList<>(Collections.singleton(ROLE_USER));

        boolean userExists = userRepository.count() > 0;
        if (!userExists) {
            userRoles.add(ROLE_ADMIN);
        }

        user.getRoles().addAll(roleRepository.findAllByNameIn(userRoles));
    }

    public String logout(String token) {
        jwtService.invalidateToken(token);
        return "You have logged out successfully!";
    }
}