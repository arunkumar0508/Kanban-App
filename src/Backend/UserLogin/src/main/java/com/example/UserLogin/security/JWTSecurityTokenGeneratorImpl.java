package com.example.UserLogin.security;
import io.jsonwebtoken.Jwts;


import com.example.UserLogin.domain.User;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.DoubleStream;

@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator{
    @Override
    public Map<String, String> generateToken(User user) {
        String jwtToken = Jwts.builder().setIssuer("User")
                .setSubject(user.getEmail())

                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"21182518")
                .compact();
        System.out.println(user.getEmail());
        Map<String,String> map = new HashMap<>();
        map.put("token",jwtToken);
        map.put("message","Authentication Successful");
        return map;
    }


}
