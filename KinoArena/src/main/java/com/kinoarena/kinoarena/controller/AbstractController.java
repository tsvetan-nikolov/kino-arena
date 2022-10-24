package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.exceptions.BadRequestException;
import com.kinoarena.kinoarena.exceptions.NotFoundException;
import com.kinoarena.kinoarena.exceptions.UnauthorizedException;
import com.kinoarena.kinoarena.model.DTOs.error.ErrorDTO;
import com.kinoarena.kinoarena.model.DTOs.user.response.UserWithoutPasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

public abstract class AbstractController /*TODO implement*/ {

    public static final String LOGGED = "LOGGED";
    public static final String USER_ID = "USER_ID";
    public static final String IS_ADMIN = "IS_ADMIN";
    public static final String REMOTE_IP = "REMOTE_IP";

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(Exception e) {
        return buildErrorInfo(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ErrorDTO handleNotFound(Exception e) {
        return buildErrorInfo(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public ErrorDTO handleUnauthorized(Exception e) {
        return buildErrorInfo(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleAllOthers(Exception e) {
        return buildErrorInfo(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorDTO buildErrorInfo(Exception e, HttpStatus status) {
        e.printStackTrace(); //add to log file??
        ErrorDTO dto = new ErrorDTO();
        dto.setStatus(status.value());
        dto.setMsg(e.getMessage());
        dto.setTime(LocalDateTime.now());
        return dto;
    }

    public void logUser(UserWithoutPasswordDTO dto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute(LOGGED, true);
        session.setAttribute(USER_ID, dto.getId());
        session.setAttribute(IS_ADMIN, dto.isAdmin());
        session.setAttribute(REMOTE_IP, request.getRemoteAddr());
    }

}
