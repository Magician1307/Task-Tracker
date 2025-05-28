package com.cli.taskTracker.model;

import java.util.Date;

import com.cli.taskTracker.Enum.*;

public class Task {
    
    private  Long id;

    private String description;

    private Status status;

    private Date createdAt;

    private Date updatedAt;


    Task() {

    }

    public Task(Long id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = new Date();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public  Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Status getStatus() {
        return status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "Task [Id=" + id + ", description=" + description + ", status=" + status + ", createdAt=" + createdAt + ", updatedAt="
                + updatedAt + "]";
    }

    





}
