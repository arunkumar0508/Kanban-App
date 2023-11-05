package com.example.UserLogin.service;

import com.example.UserLogin.domain.User;
import com.example.UserLogin.exception.InvalidCustomerDetails;
import com.example.UserLogin.exception.UserAlreadyExist;
import com.example.UserLogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }


    @Override
    public User registerUser(User user) throws UserAlreadyExist {
       User u=userRepository.findByEmail(user.getEmail());
       if(u!= null){
           throw new UserAlreadyExist();
       }
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) throws InvalidCustomerDetails {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new InvalidCustomerDetails();
        }
        return user;
    }

    @Override
    public User loginUser(String email, String password) throws InvalidCustomerDetails {
        User user = findByEmail(email);

        if (user.getPassword().equals(password)){
            return user;
        }else {

            throw new InvalidCustomerDetails();
        }
    }




}
