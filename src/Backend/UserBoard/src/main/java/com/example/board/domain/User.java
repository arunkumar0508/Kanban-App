package com.example.board.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Document

public class User {

  @Id
  private String email;
    private String userName;
    private String password;
    private String phoneNumber;

    List<Project> projects;

  public User() {
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public List<Project> getProjects() {
    return projects;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }

  public User(String email, String userName, String password, String phoneNumber, List<Project> projects) {
    this.email = email;
    this.userName = userName;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.projects = projects;
  }

  @Override
  public String toString() {
    return "User{" +
            "email='" + email + '\'' +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", projects=" + projects +
            '}';
  }
}
