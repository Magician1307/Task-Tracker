# Task-Tracker

Prerequisites
- Java: Version 17 or later
- Maven: Version 3.6.0 or later
- A terminal or command prompt
- Git (optional, for cloning the repository)


Setup Instructions:
1. Clone the repository
git clone https://github.com/Magician1307/Task-Tracker.git
cd task-tracker

2. Install maven dependencies
mvn clean install

3. Configure the tasks.json
- The application creates src/main/resources/tasks.json automatically if it doesn't exist.
- If you want to start with an empty task list, create src/main/resources/tasks.json with the following content:
    []

Running the application
1. Start the application
mvn spring-boot::run

Interact the CLI Application
=== Task Tracker ===
1. Add Task
2. List all Tasks
3. Update Task
4. Delete Task
5. List Status Tasks
6. Exit
Choose an option: 