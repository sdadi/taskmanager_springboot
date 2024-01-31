package com.taskmanager.springboot.controllers;

import com.taskmanager.springboot.dto.CreateTaskDTO;
import com.taskmanager.springboot.dto.ErrorResponseDTO;
import com.taskmanager.springboot.dto.UpdateTaskDTO;
import com.taskmanager.springboot.entities.TaskEntity;
import com.taskmanager.springboot.services.NotesService;
import com.taskmanager.springboot.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final NotesService noteService;

//    private final ModelMapper modelMapper = new ModelMapper();
    public TaskController(TaskService taskService, NotesService noteService) {
        this.taskService = taskService;
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks() {
        return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTask(@PathVariable("id") Integer id) {
        var task = taskService.getTaskById(id);
        if (null == task) {
            return ResponseEntity.notFound().build();
        }
        var notes = noteService.getNotesForTask(id);
//        TaskResponseDTO taskResponse = modelMapper.map(task, TaskResponseDTO.class);
//        taskResponse.setNotes(notes);
        task.setNotes(notes);
        return ResponseEntity.ok(task);
    }

    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException {
        TaskEntity task = taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDTO body) throws ParseException {
        TaskEntity task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getStatus());
        if (null == task) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(task);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e) {
        if (e instanceof ParseException) {
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid date format"));
        }
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal server error"));
    }
}
