package com.example.board.service;

import com.example.board.domain.*;
import com.example.board.exception.ProjectNotFound;
import com.example.board.exception.UserAlreadyExist;
import com.example.board.exception.UserNotFound;
import com.example.board.proxy.UserProxy;
import com.example.board.repository.KanbanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Arrays.stream;

@Service
public class KanbanServiceImpl implements KanbanInterface {
    private UserProxy userProxy;
    private KanbanRepository kanbanRepository;

    @Autowired
    public KanbanServiceImpl(UserProxy userProxy, KanbanRepository kanbanRepository) {
        this.userProxy = userProxy;
        this.kanbanRepository = kanbanRepository;
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExist {
        if (kanbanRepository.findById(user.getEmail()).isPresent()) {
            throw new UserAlreadyExist();
        }
        User saveUser = kanbanRepository.save(user);
        if (!(saveUser.getEmail().isEmpty())) {
            ResponseEntity r = userProxy.saveUser(user);
        }
        return saveUser;
    }


    @Override
    public User saveProjectToList(Project project, String email) throws UserNotFound {
        Optional<User> userOptional = kanbanRepository.findById(email);
        if (userOptional.isEmpty()) {
            throw new UserNotFound();
        }

        User user = userOptional.get();
        if (user.getProjects() == null) {
            user.setProjects(new ArrayList<>());
        }
//        else {
//            List<Project> projects = user.getProjects();
//            projects.add(project);
//            user.setProjects(projects);
//        }
        user.getProjects().add(project);

        return kanbanRepository.save(user);
    }

//    @Override
//    public Board getBoardDetailsByProjectId(String projectId) throws ProjectNotFound {
//        return null;
//    }

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
    @Override
    public String deleteTaskFromColumn(String email, String projectId, Task taskToDelete) throws ProjectNotFound, UserNotFound {
        Optional<User> userOptional = kanbanRepository.findById(email);

        if (userOptional.isEmpty()) {
            throw new UserNotFound();
        }

        User user = userOptional.get();
        List<Project> projects = user.getProjects();

        Optional<Project> projectOptional = projects.stream()
                .filter(project -> project.getProjectId().equals(projectId))
                .findFirst();

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Board board = project.getBoard();

            List<Column> columns = board.getColumns();

//            for (Column column : columns) {
//                List<Task> tasks = column.getTasks();
//
//                if (tasks.contains(taskToDelete)) {
//                    tasks.remove(taskToDelete);
//                    kanbanRepository.save(user);                }
//            }
            for(var i=0;i<columns.size();i++){
                if(columns.get(i).equals(taskToDelete)){
                    columns.remove(i);
                    kanbanRepository.save(user);
                }
            }

//            return kanbanRepository.save(user);
        }
        else {
            throw new ProjectNotFound();
        }
        return "Not Done";
    }




    @Override
    public List<Project> getAllProjects(String email) throws UserNotFound {
        Optional<User> userOptional = kanbanRepository.findById(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return user.getProjects();
        } else {
            throw new UserNotFound();
        }
    }

    @Override
    public Project getProjectDetails(String email, String projectId) throws ProjectNotFound {
        Optional<User> userOptional = kanbanRepository.findById(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Optional<Project> projectOptional = user.getProjects().stream()
                    .filter(project -> project.getProjectId().equals(projectId))
                    .findFirst();

            if (projectOptional.isPresent()) {
                return projectOptional.get();
            }
        }
        throw new ProjectNotFound();
    }


    @Override
    public Project createBoardInProject(String projectId, Board board) throws ProjectNotFound {
        Optional<User> userOptional = kanbanRepository.findById(projectId);

        if (userOptional.isEmpty()) {
            throw new ProjectNotFound();
        }

        User user = userOptional.get();
        board.setId();
        Project project = new Project(); // Create a new project
        project.setBoard(board); // Set the board for the project


        if (user.getProjects() == null) {
            user.setProjects(Arrays.asList(project));
        } else {
            List<Project> projects = user.getProjects();
            projects.add(project);
            user.setProjects(projects);
        }

        // Save the updated user object
        kanbanRepository.save(user);

        return project;
    }


    @Override
    public Project createColumnInBoard(String projectId, String boardId, Column column) throws ProjectNotFound {
        Optional<User> userOptional = kanbanRepository.findById(String.valueOf(projectId));

        if (userOptional.isEmpty()) {
            throw new ProjectNotFound();
        }

        User user = userOptional.get();

        // Find the board within the project
        Optional<Project> projectOptional = user.getProjects().stream()
                .filter(project -> project.getBoard() != null && project.getBoard().getBoardId().equals(boardId))
                .findFirst();

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Board board = project.getBoard();
            board.addColumn(column); // Add the new column to the board

            // Save the updated user object
            kanbanRepository.save(user);

            return project; // Return the project containing the updated board
        } else {
            throw new ProjectNotFound();
        }
    }


    @Override
    public Project createTaskInColumn(String email,String projectId, Task task) throws ProjectNotFound, UserNotFound {
        // Fetch the user using the repository method
        Optional<User> userOptional = kanbanRepository.findById(email);

        // Ensure the user exists
        if (userOptional.isEmpty()) {
            throw new UserNotFound();
        }

        User user = userOptional.get();

        // Find the project within the user's projects list
        Optional<Project> projectOptional = user.getProjects().stream()
                .filter(project -> project.getProjectId().equals(projectId))
                .findFirst();

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();

            Board board = project.getBoard();

            if (board != null && !board.getColumns().isEmpty()) {
                Column firstColumn = board.getColumns().get(0);

                firstColumn.getTasks().add(task);

                kanbanRepository.save(user);

                return project;
            } else {
                throw new ProjectNotFound();
            }
        } else {
            throw new ProjectNotFound();
        }
    }
    @Override
    public List<String> getAllUserEmails() {
        List<User> users = kanbanRepository.findAll();
        List<String> userEmails = new ArrayList<>();

        for (User user : users) {
            userEmails.add(user.getEmail());
        }

        return userEmails;
    }
    @Override
    public List<User> getAll() {
        List<User> users=kanbanRepository.findAll();
        return users;
    }



    @Override
    public Project moveTaskBetweenColumns(String email, String projectId, Task taskToMove)
            throws ProjectNotFound, UserNotFound {
        // Fetch the user using the repository method
        Optional<User> userOptional = kanbanRepository.findById(email);

        // Ensure the user exists
        if (userOptional.isEmpty()) {
            throw new UserNotFound();
        }

        User user = userOptional.get();

        // Find the project within the user's projects list
        Optional<Project> projectOptional = user.getProjects().stream()
                .filter(project -> project.getProjectId().equals(projectId))
                .findFirst();

        if (projectOptional.isPresent()) {
            Project project = projectOptional.get();
            Board board = project.getBoard();

            if (board != null) {
                List<Column> columns = board.getColumns();

                // Find the target column based on task status
                Column targetColumn = null;
                for (Column column : columns) {
                    if ((column.getName() != null && column.getName().equals(taskToMove.getStatus())) ||
                            (column.getName() == null && taskToMove.getStatus() == null)) {
                        targetColumn = column;
                        System.out.println(targetColumn.getTasks());
                        System.out.println(targetColumn.getTasks());

                        break;
                    }
                }

                // Check if the target column is null or already contains the task
                if (targetColumn == null || (targetColumn.getTasks() != null && targetColumn.getTasks().contains(taskToMove))) {
                    // No need to move or task already exists in the target column
                    System.out.println("Already exist");
                    return project;
                }

                // Add the task to the target column
                if (targetColumn.getTasks() == null) {
                    targetColumn.setTasks(new ArrayList<>());
                }
                targetColumn.getTasks().add(taskToMove);
                System.out.println(targetColumn.getTasks());
                System.out.println("Working");


                 kanbanRepository.save(user);

                return project;
            } else {
                throw new ProjectNotFound();
            }
        } else {
            throw new ProjectNotFound();
        }
    }

}





