package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieInfoDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.ChangePasswordDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.EditProfileDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.LoginDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.model.entities.Movie;
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

    @PostMapping(value = "/users/add_fav_movie/{movieId}")
    public MovieInfoDTO addFavouriteMovie(@PathVariable int movieId, HttpSession s) {
        return userService.addFavouriteMovie(movieId, s);
    }

    @PostMapping(value = "/auth")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpSession session, HttpServletRequest request) {
        UserWithoutPasswordDTO result = userService.login(dto);

        if (result != null) {
            session.setAttribute("LOGGED", true);
            session.setAttribute("USER_ID", result.getId());
            //TODO check with krasi
            session.setAttribute("REMOTE_IP", request.getRemoteAddr());
            session.setAttribute("ADMIN", result.isAdmin());

            System.out.println(session.getAttribute("USER_ID"));
            return result;
        } else {
            throw new BadRequestException("Wrong Credentials!");
        }
    }

    @PutMapping(value = "/users/{uid}")
    public UserWithoutPasswordDTO changePassword(@RequestBody ChangePasswordDTO dto, @PathVariable int uid) {
        return userService.changePassword(uid, dto);
    }

    @PutMapping(value = "users/{uid}/edit")
    public UserWithoutPasswordDTO editProfile(@RequestBody EditProfileDTO dto, @PathVariable int uid) {
        return userService.editProfile(dto, uid);
    }

}

