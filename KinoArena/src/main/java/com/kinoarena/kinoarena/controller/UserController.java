package com.kinoarena.kinoarena.controller;
import com.kinoarena.kinoarena.util.annotation.UserId;

import com.kinoarena.kinoarena.model.DTOs.movie.MovieSummarizedResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.movie.MovieResponseDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.ChangePasswordRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.EditProfileRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.request.RegisterRequestDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserInfoResponse;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import com.kinoarena.kinoarena.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.http.HttpClient;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequiredArgsConstructor
public class UserController extends AbstractController {

    private final UserService userService;

    @PostMapping(value = "register")
    public UserInfoResponse register(@RequestBody @Valid @NotNull RegisterRequestDTO user) {
        return userService.register(user);
    }

//    @PostMapping(value = "/auth")
//    public UserWithoutPasswordDTO login(@RequestBody LoginDTO dto, HttpServletRequest request) {
//        if (request.getSession().getAttribute(LOGGED) != null) {
//            if ((boolean) request.getSession().getAttribute(LOGGED)) {
//                throw new BadRequestException("You are already logged in!");
//            }
//        }
//
//        UserWithoutPasswordDTO result = userService.login(dto);
//
//        if (result != null) {
//            logUser(result, request);
//            return result;
//        } else {
//            throw new BadRequestException("Wrong Credentials!");
//        }
//    }

    @PostMapping("/logout") /*TODO blacklist stuff*/
    public String logout(@RequestHeader(AUTHORIZATION) String token) {
        return userService.logout(token);
    }

    @PutMapping(value = "/users/{uid}/password-change")
    public UserWithoutPasswordDTO changePassword(@RequestBody ChangePasswordRequestDTO dto, @PathVariable int uid) {
        return userService.changePassword(uid, dto);
    }

    @PutMapping(value = "/users/{uid}/edit")
    public UserWithoutPasswordDTO editProfile(@RequestBody EditProfileRequestDTO dto, @PathVariable int uid) {
        return userService.editProfile(dto, uid);
    }

    @PostMapping(value = "/users/add-remove-fav-movie/{movieId}")
    @Transactional
    public MovieResponseDTO addRemoveFavouriteMovie(@PathVariable int movieId, @UserId int userId) {
        return userService.addRemoveFavouriteMovie(movieId, userId);
    }

    @GetMapping(value = "/users/{uid}/favourite-movies")
    public List<MovieSummarizedResponseDTO> showFavoriteMovies(@PathVariable int uid) {
        return userService.showFavouriteMovies(uid);
    }

}

