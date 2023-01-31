package com.shekhar.springsecurityclient.controller;

import com.shekhar.springsecurityclient.entity.User;
import com.shekhar.springsecurityclient.error.UserInvalidRequestException;
import com.shekhar.springsecurityclient.error.UserNotFoundException;
import com.shekhar.springsecurityclient.event.RegistrationCompleteEvent;
import com.shekhar.springsecurityclient.model.ApiResponse;
import com.shekhar.springsecurityclient.model.UserModel;
import com.shekhar.springsecurityclient.repository.UserRepository;
import com.shekhar.springsecurityclient.service.UserService;
import com.shekhar.springsecurityclient.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

/*
Class: UserController
Type:RestController
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    /*
    Request: POST
    Operation: Register New User
     */
    @PostMapping("/registration")
    public ApiResponse registerUser(@RequestBody UserModel userModel, final HttpServletRequest request) throws UserInvalidRequestException {
        ApiResponse apiResponse =  userService.registerUser(userModel, request);
        return  apiResponse;
    }
    /*
   Request:GET
   Operation:Verify user registration
    */
    @GetMapping("/verifyRegistration")
    public ApiResponse verifyRegistration(@RequestParam("token") String token){
        return userService.verifyRegistration(token);
    }

    /*
    Request: GET
    Operation: Fetch all users
     */
    @GetMapping("/user")
    public ApiResponse fetchAllUsers() throws UserNotFoundException{
        return userService.fetchAllUsers();
    }

    /*
    Request: GET
    Operation: Fetch user by Id
     */
    @GetMapping("/user/{id}")
    public ApiResponse fetchUserById(@PathVariable("id") Long userId) throws UserNotFoundException {
        return userService.fetchUserById(userId);
    }
    /*
    Request:GET
    Operation: Fetch user by Name
     */
    @GetMapping("/user/name/{name}")
    public ApiResponse fetchUserByfirstName(@PathVariable("name") String firstName) throws UserNotFoundException{
        return userService.fetchUserByfirstName(firstName);
    }

}
