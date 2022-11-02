package com.kinoarena.kinoarena.controller;

import com.kinoarena.kinoarena.model.DTOs.error.ErrorDTO;
import com.kinoarena.kinoarena.model.exceptions.BadRequestException;
import com.kinoarena.kinoarena.model.exceptions.NotFoundException;
import com.kinoarena.kinoarena.model.exceptions.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

public abstract class AbstractController {

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

//    public void logUser(UserWithoutPasswordDTO dto, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        session.setAttribute(LOGGED, true);
//        session.setAttribute(USER_ID, dto.getId());
//        session.setAttribute(REMOTE_IP, request.getRemoteAddr());
//    }

}
