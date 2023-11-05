package com.example.UserLogin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND,reason = "User Details are Invalid")

public class InvalidCustomerDetails extends Exception{
}
