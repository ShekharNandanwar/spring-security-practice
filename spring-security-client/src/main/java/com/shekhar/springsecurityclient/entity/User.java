package com.shekhar.springsecurityclient.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(
        name = "tbl_User",
        uniqueConstraints = @UniqueConstraint(
                name = "user_unique",
                columnNames = {"email","contact"}
        )
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long userId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String contact;
    @NotEmpty
    private String password;
    private boolean enabled = false;
}
