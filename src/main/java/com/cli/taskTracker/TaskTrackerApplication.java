package com.cli.taskTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.cli.taskTracker.cli.TaskCLI;

@SpringBootApplication
public class TaskTrackerApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TaskTrackerApplication.class, args);

		TaskCLI taskCLI = context.getBean(TaskCLI.class);
		taskCLI.run();

	}

}
