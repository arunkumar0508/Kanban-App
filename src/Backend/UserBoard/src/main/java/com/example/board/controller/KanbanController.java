package com.example.board.controller;

import com.example.board.domain.*;
import com.example.board.exception.ProjectNotFound;
import com.example.board.exception.UserAlreadyExist;
import com.example.board.exception.UserNotFound;
import com.example.board.service.KanbanInterface;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import io.jsonwebtoken.Claims;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/apii/users")
public class KanbanController {

    private final KanbanInterface kanbanService;

    @Autowired
    public KanbanController(KanbanInterface kanbanService) {
        this.kanbanService = kanbanService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws UserAlreadyExist {
        User registeredUser = kanbanService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
    @GetMapping("/emails")
    public List<String> getAllUserEmails() {
        List<User> users;
        users = kanbanService.getAll();
        List<String> userEmails = new ArrayList<>();

        for (User user : users) {
            userEmails.add(user.getEmail());
        }

        return userEmails;
    }


    @PostMapping("/projects")
    public ResponseEntity<User> saveProjectToList(
            @RequestBody Project project,
            HttpServletRequest request
    ) throws UserNotFound {
        Claims claims = (Claims) request.getAttribute("username");
        String email = claims.getSubject();
        ObjectId projectId = new ObjectId();
        project.setId(projectId.toHexString());


        User updatedUser = kanbanService.saveProjectToList(project, email);
        return ResponseEntity.ok(updatedUser);
    }


    @DeleteMapping("/{projectId}/task")
    public ResponseEntity<String> deleteProjectFromList(
//            @PathVariable String email,
            @PathVariable String projectId,
            @RequestBody Task taskToMove,
            HttpServletRequest request
    ) throws ProjectNotFound, UserNotFound {
        Claims claims = (Claims) request.getAttribute("username");
        String email=claims.getSubject();


        // Check if email matches the subject in claims
        if (!claims.getSubject().equals(email)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        try {
            kanbanService.deleteTaskFromColumn(email, projectId, taskToMove);
            return ResponseEntity.noContent().build();
        } catch (ProjectNotFound | UserNotFound ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + ex.getMessage());
        }
    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getAllProjects(HttpServletRequest request) throws UserNotFound {
        Claims claims = (Claims) request.getAttribute("username");
        String authenticatedUserEmail = claims.getSubject();
        System.out.println("authenticatedUserEmail: " + authenticatedUserEmail);

        List<Project> projects = kanbanService.getAllProjects(authenticatedUserEmail);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/projects/{email}/{projectId}")
    public ResponseEntity<Project> getProjectDetails(
            @PathVariable String email,
            @PathVariable String projectId
    ) throws  ProjectNotFound {
        Project project = kanbanService.getProjectDetails(email, projectId);
        return ResponseEntity.ok(project);
    }





    @PostMapping("/{projectId}/boards")
    public ResponseEntity<Project> createBoardInProject(
            @PathVariable String projectId,
            @RequestBody Board board,
            HttpServletRequest request
    ) throws ProjectNotFound {
        Claims claims = (Claims) request.getAttribute("username");
//        String boardId = new ObjectId().toString();
//        board.setId(boardId);
        Project createdProject = kanbanService.createBoardInProject(projectId, board);

        return ResponseEntity.ok(createdProject);
    }

    @PostMapping("/{projectId}/boards/{boardId}/columns")
    public ResponseEntity<Project> createColumnInBoard(
            @PathVariable String projectId,
            @PathVariable String boardId,
            @RequestBody Column column,
            HttpServletRequest request
    ) throws ProjectNotFound {
        Claims claims = (Claims) request.getAttribute("username");
//        String columnId = UUID.randomUUID().toString();

        column.setId();
        Project updatedProject = kanbanService.createColumnInBoard(projectId, boardId, column);
        return ResponseEntity.ok(updatedProject);
    }
    private String generateRandomString() {
        return UUID.randomUUID().toString();
    }

    @PutMapping("/{projectId}/tasks")
    public ResponseEntity<Project> createTaskInFirstColumn(
            @PathVariable String projectId,
            @RequestBody Task task,
            HttpServletRequest request
    ) throws ProjectNotFound, UserNotFound {
        Claims claims = (Claims) request.getAttribute("username");
        String email=claims.getSubject();
        Project updatedProject = kanbanService.createTaskInColumn(email, projectId,task);
        return ResponseEntity.ok(updatedProject);
    }

    @PutMapping("/{projectId}/tasks/move")
    public ResponseEntity<Project> moveTaskBetweenColumns(
            @PathVariable String projectId,
            @RequestBody Task taskToMove,

            HttpServletRequest request
    ) throws ProjectNotFound {
        try {
            Claims claims = (Claims) request.getAttribute("username");
            String email = claims.getSubject();

            Project update = kanbanService.moveTaskBetweenColumns(email, projectId, taskToMove);
//            System.out.println(taskToMove);
//            System.out.println(update);
            return ResponseEntity.ok(update);
        } catch (ProjectNotFound | UserNotFound e) {
            throw new ProjectNotFound();
        }
    }






}
