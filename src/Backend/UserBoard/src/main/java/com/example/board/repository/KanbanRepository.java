package com.example.board.repository;

import com.example.board.domain.Project;
import com.example.board.domain.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KanbanRepository extends MongoRepository<User,String> {
    Project findByProjects_ProjectId(String projectId);

}
