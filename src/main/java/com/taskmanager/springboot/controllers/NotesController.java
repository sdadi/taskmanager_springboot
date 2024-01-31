package com.taskmanager.springboot.controllers;

import com.taskmanager.springboot.dto.CreateNoteDTO;
import com.taskmanager.springboot.dto.CreateNoteResponseDTO;
import com.taskmanager.springboot.entities.NotesEntity;
import com.taskmanager.springboot.services.NotesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NotesController {
    private final NotesService notesService;
    public NotesController(NotesService noteService){
        this.notesService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<NotesEntity>> getNotes(@PathVariable("taskId") Integer taskId){
        var notes = notesService.getNotesForTask(taskId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("")
    public ResponseEntity<CreateNoteResponseDTO> addNote(@PathVariable("taskId") Integer taskId,@RequestBody CreateNoteDTO body){
        var note = notesService.addNoteForTask(taskId,body.getTitle(),body.getBody());
        return ResponseEntity.ok(new CreateNoteResponseDTO(taskId,note));
    }
}
