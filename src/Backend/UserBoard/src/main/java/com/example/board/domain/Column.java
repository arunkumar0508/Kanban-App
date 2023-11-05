package com.example.board.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Column {
@Id
    private String columnId;

    public String getColumnId() {
        return columnId;
    }

    public void setId() {

        this.columnId = new ObjectId().toString();
    }

    private String name;
    List<Task> tasks= Arrays.asList();

    public Column() {
    }

    public void addTask(Task task) {
        tasks.add(task);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public Column(String name) {
        this.name = name;
        this.tasks=new ArrayList<>();
    }

    public Column(String name, List<Task> tasks) {
        this.name = name;
        this.tasks = tasks != null ? tasks : new ArrayList<>();    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
