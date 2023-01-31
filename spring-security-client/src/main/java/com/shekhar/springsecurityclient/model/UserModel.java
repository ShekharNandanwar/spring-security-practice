package com.shekhar.springsecurityclient.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String password;
}
