package com.shekhar.springsecurityclient.utils;

import com.shekhar.springsecurityclient.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
public class CommonUtils {
    private User user;
    private String url;
    private String token;
    public static String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
