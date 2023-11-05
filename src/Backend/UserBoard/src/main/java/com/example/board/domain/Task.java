package com.example.board.domain;

import org.springframework.data.annotation.Id;

public class Task {

    private String title;
    private String description;
    private String  assigningName;
    private String status;

    public Task() {
    }

    public Task(String title, String description,String assigningName, String status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.assigningName=assigningName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssigningName() {
        return assigningName;
    }

    public void setAssigningName(String assigningName) {
        this.assigningName = assigningName;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", assigningName='" + assigningName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
