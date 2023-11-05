package com.example.UserLogin.security;

import com.example.UserLogin.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface SecurityTokenGenerator {
//    String createToken(User user);
    Map<String, String> generateToken(User user);
//  public String extractTokenFromRequest(HttpServletRequest request);

}
