package com.example.board.proxy;

import com.example.board.domain.User;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@FeignClient(name="user-login-service",url="localhost:8090")
public interface UserProxy {

    @PostMapping("/api/users/signup")
    public ResponseEntity<?> saveUser(@RequestBody User user);
}
