package com.cli.taskTracker.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.cli.taskTracker.Enum.Status;
import com.cli.taskTracker.model.Task;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class TaskService {
 

    private final ObjectMapper objectMapper;
    private final File jsonFile;
    private final AtomicLong idGenerator;

    public TaskService() throws StreamReadException, DatabindException, IOException {

        this.objectMapper = new ObjectMapper();
        this.jsonFile = new File("src/main/resources/tasks.json");
        this.idGenerator = new AtomicLong(loadLastId());

    }

    private long loadLastId() throws StreamReadException, DatabindException, IOException {
        List<Task> tasks = readTasksFromFile();

        return tasks.stream()
                .map(Task::getId)
                .max(Long::compareTo)
                .orElse(0L);
    }

    private List<Task> readTasksFromFile() throws StreamReadException, DatabindException, IOException {
        if(!jsonFile.exists()) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(jsonFile, new TypeReference<List<Task>>(){});
    }

    public Task addTask(String description, String status) throws StreamReadException, DatabindException, IOException {
        List<Task> tasks = readTasksFromFile();
        Task task = new Task(idGenerator.getAndIncrement() ,description, Status.getEnum(status));
        tasks.add(task);
        objectMapper.writeValue(jsonFile, tasks);
        return task;
    }

    public List<Task> listAllTasks() throws StreamReadException, DatabindException, IOException {
        return readTasksFromFile();
    }
    

    public Task updateTask(Long id, String desc, String status) throws StreamReadException, DatabindException, IOException {
        List<Task> tasks = readTasksFromFile();
        Optional<Task> optionalTask =  tasks.stream().filter(task -> task.getId().equals(id))
        .findFirst();

        if(optionalTask.isPresent()) {
            Task task = optionalTask.get();

            if(!desc.isEmpty() && desc != null) {
                task.setDescription(desc);
            }
            if(!status.isEmpty() && status != null) {
                task.setStatus(Status.getEnum(status));
            }
            task.setUpdatedAt(new Date());
            objectMapper.writeValue(jsonFile, tasks);
            return task;
        }
        return null;
    }

    public boolean deleteTask(Long id) throws StreamWriteException, DatabindException, IOException {
        List<Task> tasks = readTasksFromFile();

        boolean removed = tasks.removeIf(task -> task.getId().equals(id));

        if(removed) {
            objectMapper.writeValue(jsonFile, tasks);
        }

        return removed;

    }

    public List<Task> listAllStatusTasks(String status) throws StreamReadException, DatabindException, IOException {
        List<Task> tasks = readTasksFromFile();

        List<Task> doneTasks = tasks.stream().filter(task -> task.getStatus().getValue().equals(status)).collect(Collectors.toList());

        return doneTasks;
    }
}
