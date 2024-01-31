package com.taskmanager.springboot.services;

import com.taskmanager.springboot.entities.NotesEntity;
import com.taskmanager.springboot.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NotesService {
    private final TaskService taskService;
    private int noteId =1;
    private final ArrayList<NotesEntity> notes = new ArrayList<NotesEntity>();
    private final HashMap<Integer,List<NotesEntity>> taskNotesMap = new HashMap<Integer,List<NotesEntity>>();
    public NotesService(TaskService taskService){
        this.taskService = taskService;
    }

    public NotesEntity addNoteForTask(int taskId, String title, String body){
        TaskEntity task = taskService.getTaskById(taskId);
        if(null == task){
            return  null;
        }
        NotesEntity note = new NotesEntity();
        note.setId(noteId);
        note.setTitle(title);
        note.setBody(body);
        notes.add(note);
        taskNotesMap.putIfAbsent(taskId,notes);

        noteId++;
        return note;
    }
    public List<NotesEntity> getNotesForTask(int taskId){
        TaskEntity task = taskService.getTaskById(taskId);
        if(null == task){
            return null;
        }
        return taskNotesMap.get(taskId);
    }

}
