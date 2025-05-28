package com.cli.taskTracker.cli;

import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import com.cli.taskTracker.model.Task;
import com.cli.taskTracker.service.TaskService;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;


@Component
public class TaskCLI {

    private final TaskService taskService;
    private final Scanner scanner;

    public TaskCLI(TaskService taskService) {
        this.taskService = taskService;
        this.scanner = new Scanner(System.in);
    }
    

    public void run() {
        while(true) {
            displayMenu();
            String choice = scanner.nextLine();
            try{
                switch(choice) {
                    case "1" : 
                        addTask();
                        break;
                    case "2" :
                        listTasks();
                        break;
                    case "3" :
                        updateTask();
                        break;
                    case "4" :
                        deleteTask();
                        break;
                    case "5" :
                        listAllStatusTasks();
                        break;
                    case "6" :
                        System.out.println("Exiting....");
                        return;
                    default :
                        System.out.println("Invalid choice...");
                }
            }catch(Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }


    public void displayMenu() {
        System.out.println("\n========== Task Tracker ============\n");
        System.out.println("1. Add Task");
        System.out.println("2. List all tasks");
        System.out.println("3. Update Task");
        System.out.println("4. Delete Task");
        System.out.println("5. List Status Tasks");
        System.out.println("6. Exit");
        System.out.println("Choose an option: ");
    }

    public void addTask() throws StreamReadException, DatabindException, IOException {
        System.out.println("Enter task description: ");
        String desc = scanner.nextLine();
        System.out.println("Enter the status (e.g., TODO, IN_PROGRESS, DONE): ");
        String status = scanner.nextLine();
        Task task = taskService.addTask(desc, status);
        System.out.println("Task added.... " + task);

    }

    public void listTasks() throws StreamReadException, DatabindException, IOException {
        System.out.println("========= Task List =========");
        taskService.listAllTasks().stream().forEach(System.out::println);
    }

    public void updateTask() throws StreamReadException, DatabindException, IOException {
        System.out.println("Enter task Id to update: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        }catch(NumberFormatException n) {
            System.out.println("Invalid Id format.");
            return;
        }

        System.out.println("Enter the new description (Leave blank if want to keep same): ");
        String description = scanner.nextLine();
        System.out.println("Enter the new status of task (Leave blank if want same): ");
        String status = scanner.nextLine();
        
        Task updatedTask = taskService.updateTask(id, description, status);
        
        if(updatedTask != null) {
            System.out.println("Task updated: " + updatedTask);
        }else {
            System.out.println("Task Id not found.");
        }

    }

    public void deleteTask() throws StreamWriteException, DatabindException, IOException {
        System.err.println("Enter task Id to delete: ");
        Long id;
        try {
            id = Long.parseLong(scanner.nextLine());
        }catch(NumberFormatException n) {
            System.out.println("Invalid Id Format");
            return;
        }

        if(taskService.deleteTask(id)) {
            System.out.println("Task deleted");
        }else {
            System.out.println("Task Id not found");
        }

    }

    public void listAllStatusTasks() throws StreamReadException, DatabindException, IOException {

        System.out.println("Enter the status to list all tasks: ");
        String status = scanner.nextLine();

        if(status.equals("done")) {
            System.out.println("========== Done Tasks ==========");
            taskService.listAllStatusTasks(status).stream().forEach(System.out::println);
        }else if(status.equals("in progress")) {
            System.out.println("========== In Progress Tasks ========");
            taskService.listAllStatusTasks(status).stream().forEach(System.out::println);
        }else {
            System.out.println("========== TODO Tasks ========"); 
            taskService.listAllStatusTasks(status).stream().forEach(System.out::println);
        }
    }

}
