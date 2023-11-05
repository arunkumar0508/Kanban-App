package com.example.board.domain;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Project {
    @Id
    private String projectId;
    private String name;
    private String description;
    Board board;

    public Project( String name, String description, Board board) {
//        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.board = board;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + projectId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", board=" + board +
                '}';
    }

    public Project() {
    }
}
