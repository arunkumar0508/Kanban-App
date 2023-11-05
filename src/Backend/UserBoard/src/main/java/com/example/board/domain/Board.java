package com.example.board.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    @Id
    String boardId;

    public String getBoardId() {
        return boardId;
    }

    public void setId() {
        this.boardId = new ObjectId().toString();
    }
    public void setId(String boardId) {
        this.boardId = boardId;
    }

    String name;

    List<Column> columns= Arrays.asList();

    public Board(String name, List<Column> column) {
        this.name = name;
        this.columns = new ArrayList<>();
    }
    public void addColumn(Column column) {
        columns.add(column);
    }

    public void removeColumn(Column column) {
        columns.remove(column);
    }

    public Board() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumn(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "Board{" +
                "name='" + name + '\'' +
                ", column=" + columns +
                '}';
    }
}
