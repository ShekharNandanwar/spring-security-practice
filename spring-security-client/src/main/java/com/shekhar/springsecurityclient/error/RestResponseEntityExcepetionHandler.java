package com.shekhar.springsecurityclient.error;

import com.shekhar.springsecurityclient.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseStatus
public class RestResponseEntityExcepetionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse> userNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest){
        ApiResponse apiResponse = new ApiResponse(HttpStatus.NOT_FOUND.value(), userNotFoundException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @ExceptionHandler(UserInvalidRequestException.class)
    public ResponseEntity<ApiResponse>  userInvalidRequestException(UserInvalidRequestException userInvalidRequestException, WebRequest webRequest){
        ApiResponse apiResponse = ApiResponse.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(userInvalidRequestException.getMessage())
                .responseData(null)
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
