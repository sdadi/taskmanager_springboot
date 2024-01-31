package com.taskmanager.springboot.services;

import com.taskmanager.springboot.entities.TaskEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TaskService {
    private final ArrayList<TaskEntity> tasks = new ArrayList<TaskEntity>();
    private int taskId = 1;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public TaskEntity addTask(String title, String description, String deadline) throws ParseException {
        TaskEntity task = new TaskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(dateFormat.parse(deadline));
        task.setStatus(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    public ArrayList<TaskEntity> getTasks() {
        return tasks;
    }

    public TaskEntity getTaskById(int id) {
        return tasks.stream().findAny().filter(t -> t.getId() == id).orElse(null);
    }
    public TaskEntity updateTask(int id, String description,String deadline,Boolean status) throws ParseException {
        TaskEntity task = getTaskById(id);
        if (null == task)
            return null;
        if(null != description) {
            task.setDescription(description);
        }
        if(null != deadline){
            task.setDeadline(dateFormat.parse(deadline));
    }
        if(null != status){
            task.setStatus(status);
        }
        return task;
    }
}
