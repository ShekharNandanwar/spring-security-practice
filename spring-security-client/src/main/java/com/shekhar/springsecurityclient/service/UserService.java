package com.shekhar.springsecurityclient.service;

import com.shekhar.springsecurityclient.entity.User;
import com.shekhar.springsecurityclient.error.UserInvalidRequestException;
import com.shekhar.springsecurityclient.error.UserNotFoundException;
import com.shekhar.springsecurityclient.model.ApiResponse;
import com.shekhar.springsecurityclient.model.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;

public interface UserService {
    public ApiResponse registerUser(UserModel userModel, final HttpServletRequest request) throws UserInvalidRequestException;

    public ApiResponse fetchAllUsers() throws UserNotFoundException;

    public ApiResponse fetchUserById(Long userId) throws UserNotFoundException;

    public ApiResponse fetchUserByfirstName(String firstName) throws UserNotFoundException;

    public void saveVerficationTokenForUser(User user, String token);

    public ApiResponse verifyRegistration(String token);
}
