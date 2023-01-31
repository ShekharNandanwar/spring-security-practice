package com.shekhar.springsecurityclient.model;

import lombok.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private int statusCode;
    private String message;
    private List<?> responseData;
    public ApiResponse(int statusCode, String message){
        this.statusCode = statusCode;
        this.message = message;
    }
}