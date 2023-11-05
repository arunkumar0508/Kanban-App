package com.example.board.service;

import com.example.board.domain.*;
import com.example.board.exception.ProjectNotFound;
import com.example.board.exception.UserAlreadyExist;
import com.example.board.exception.UserNotFound;

import java.util.List;

public interface KanbanInterface {

    User registerUser(User user) throws UserAlreadyExist;
    List<User> getAll();
    User saveProjectToList(Project project, String email) throws UserNotFound;

//    Board getBoardDetailsByProjectId(String projectId) throws ProjectNotFound;

//    User deleteProjectFromList(String email, String id) throws ProjectNotFound, UserNotFound;


//    User deleteTaskFromColumn(String email, ObjectId id, Task taskToMove) throws ProjectNotFound, UserNotFound;

    //    @Override
//    public Board getBoardDetailsByProjectId(String projectId) throws ProjectNotFound {
//        Optional<Project> projectOptional = kanbanRepository.findProjectById(projectId);
//
//        if (projectOptional.isPresent()) {
//            Project project = projectOptional.get();
//            return project.getBoard(); // Return the list of boards in the project
//        } else {
//            throw new ProjectNotFound(); // Handle case when project is not found
//        }
//    }
    String deleteTaskFromColumn(String email, String projectId, Task taskToDelete) throws ProjectNotFound, UserNotFound;

    List<Project> getAllProjects(String email) throws UserNotFound;
    Project getProjectDetails(String email,String projectId) throws ProjectNotFound;

    Project moveTaskBetweenColumns(String email, String projectId, Task taskToMove)throws ProjectNotFound, UserNotFound;
    List<String> getAllUserEmails();
    //    Board getBoardDetailsByProjectId(int projectId);
    Project createBoardInProject(String projectId, Board board) throws ProjectNotFound;
    Project createColumnInBoard(String projectId, String boardId, Column column) throws ProjectNotFound;
    Project createTaskInColumn(String email,String projectId, Task task) throws ProjectNotFound, UserNotFound;



}
