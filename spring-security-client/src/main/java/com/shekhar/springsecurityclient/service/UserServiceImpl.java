package com.shekhar.springsecurityclient.service;

import com.shekhar.springsecurityclient.entity.User;
import com.shekhar.springsecurityclient.entity.VerificationToken;
import com.shekhar.springsecurityclient.error.UserInvalidRequestException;
import com.shekhar.springsecurityclient.error.UserNotFoundException;
import com.shekhar.springsecurityclient.event.RegistrationCompleteEvent;
import com.shekhar.springsecurityclient.model.ApiResponse;
import com.shekhar.springsecurityclient.model.UserModel;
import com.shekhar.springsecurityclient.repository.UserRepository;
import com.shekhar.springsecurityclient.repository.VeificationTokenRepository;
import com.shekhar.springsecurityclient.utils.CommonUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/*
Class: UserServiceImpl
Type: Service Implementation
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VeificationTokenRepository veificationTokenRepository;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private PasswordEncoder passwordEncoder;
 /*
    Impl: User Registration
  */
    @Override
    public ApiResponse registerUser(UserModel userModel, final HttpServletRequest request) throws UserInvalidRequestException {
        try{
            User user = userRepository.save(User.builder()
                    .firstName(userModel.getFirstName())
                    .lastName(userModel.getLastName())
                    .email(userModel.getEmail())
                    .contact(userModel.getContact())
                    .password(passwordEncoder.encode(userModel.getPassword()))
                    .build());
            log.info("User"+user);
            ApiResponse apiResponse =  ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Success")
                    .responseData(List.of(user))
                    .build();
            /*
            Create event and generated token
             */
            applicationEventPublisher.publishEvent(RegistrationCompleteEvent.builder()
                    .user(user)
                    .applicationUrl(CommonUtils.applicationUrl(request)).build());
            return apiResponse;
        }catch (Exception e){
            log.info("Exception at userRegistration:"+e);
            throw new UserInvalidRequestException("User cannot Register");
        }
    }

    /*
    Impl: Find All users
     */
    @Override
    public ApiResponse fetchAllUsers() throws UserNotFoundException {
        try{
            List<User> users = userRepository.findAll();
            if(users.isEmpty()){
                throw  new UserNotFoundException("Users does not exist");
            }
            return ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Success")
                    .responseData(users).build();
        }catch (Exception e){
            log.info("Exception at fetchALlUsers:"+e);
            throw  new UserNotFoundException("Users does not exist");
        }
    }
    /*
    Impl: Find User by Id
     */
    @Override
    public ApiResponse fetchUserById(Long userId) throws UserNotFoundException {
        try{
            Optional<User> userOptional = userRepository.findById(userId);
            return ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Success")
                    .responseData(List.of(userOptional.get()))
                    .build();
        }catch (Exception e){
            log.info("Exception at fetchUserById:"+e);
            throw  new UserNotFoundException("User does not exist");
        }
    }

    @Override
    public ApiResponse fetchUserByfirstName(String firstName) throws UserNotFoundException {
        try {
            List<User> userList = userRepository.findByFirstNameIgnoreCase(firstName);
            if(userList.isEmpty()){
                throw new UserNotFoundException("User does not exist");
            }
            return ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Success")
                    .responseData(userList)
                    .build();
        }catch (Exception e){
            log.info("Exception at fetchUserByName:"+e);
            throw new UserNotFoundException("User does not exist");
        }
    }
    @Override
    public void saveVerficationTokenForUser(User user, String token) {
        VerificationToken verificationToken = veificationTokenRepository.save(new VerificationToken(user, token));
        log.info("Verification Token: "+verificationToken);
    }

    @Override
    public ApiResponse verifyRegistration(String token) {
        VerificationToken verificationToken = veificationTokenRepository.findByToken(token);
        if(verificationToken == null){
            return ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Invalid Request")
                    .responseData(null)
                    .build();
        }

        User user = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if((verificationToken.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            veificationTokenRepository.delete(verificationToken);
            return ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("Link Expired request Resend")
                    .responseData(null)
                    .build();
        }
        if(user.isEnabled()){
            return ApiResponse.builder()
                    .statusCode(HttpStatus.OK.value())
                    .message("User "+user.getFirstName()+" has already verified")
                    .responseData(null)
                    .build();
        }
        user.setEnabled(true);
        userRepository.save(user);
        return ApiResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("User "+user.getFirstName()+" has verified Successfully")
                .responseData(null)
                .build();
    }
}
