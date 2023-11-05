package com.example.UserLogin.service;

import com.example.UserLogin.domain.User;
import com.example.UserLogin.exception.InvalidCustomerDetails;
import com.example.UserLogin.exception.UserAlreadyExist;

public interface UserService {
    User registerUser(User user) throws UserAlreadyExist;
    User findByEmail(String email) throws InvalidCustomerDetails;

    User loginUser(String email,String password) throws InvalidCustomerDetails;
}
