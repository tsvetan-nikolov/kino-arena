package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.ChangePasswordDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.EditProfileDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.LoginDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import com.kinoarena.kinoarena.model.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    //just for testing
//    @PostMapping(value = "/users")
//    public UserWithoutPasswordDTO addUser(@RequestBody AddUserDTO u) {
//        System.out.println(u.getCity());
//        System.out.println(u.getDateOfBirth());
//        return userService.addUser(u);
//    }

    @PostMapping(value = "register")
    public UserInfoResponse register(@RequestBody @Valid @NotNull RegisterRequestDTO user) {
        return userService.register(user);
    }

    @PostMapping(value = "/auth")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpServletRequest request) {
        if (request.getSession().getAttribute(LOGGED) != null) {
            if ((boolean) request.getSession().getAttribute(LOGGED)) {
                throw new BadRequestException("You are already logged in!");
            }
        }

        UserWithoutPasswordDTO result = userService.login(dto);

        if (result != null) {
            logUser(result, request);
            return result;
        } else {
            throw new BadRequestException("Wrong Credentials!");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession s) {
        if (s.getAttribute(LOGGED) != null) {
            if ((boolean) s.getAttribute(LOGGED)) {
                s.invalidate();
                return "User logged out successfully!";
            }
        }

        return "You are already logged out!";
    }

    @PutMapping(value = "/users/{uid}")
    public UserWithoutPasswordDTO changePassword(@RequestBody ChangePasswordDTO dto, @PathVariable int uid) {
        return userService.changePassword(uid, dto);
    }

    @PutMapping(value = "users/{uid}/edit")
    public UserWithoutPasswordDTO editProfile(@RequestBody EditProfileDTO dto, @PathVariable int uid) {
        return userService.editProfile(dto, uid);
    }

    @PostMapping(value = "/users/add_fav_movie/{movieId}")
    public MovieResponseDTO addRemoveFavouriteMovie(@PathVariable int movieId, HttpSession s) {
        return userService.addRemoveFavouriteMovie(movieId, s);
    }

    @GetMapping(value = "users/{uid}/favourite-movies")
    public UserWithoutPasswordDTO showFavoriteMovies(@PathVariable int uid) {
        return userService.showFavouriteMovies(uid);
    }
}

