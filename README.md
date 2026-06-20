# Java Task Manager

A desktop task management application built using Java Swing. The application allows users to create, organize, prioritize, and manage tasks through an interactive graphical user interface.

## Features

* Add new tasks with:
  * Title
  * Description
  * Priority level (High, Medium, Low)
  * Deadline
* Edit existing tasks
* Mark tasks as completed
* Delete tasks
* Sort tasks by priority
* Save tasks to a file
* Load previously saved tasks when the application starts
* Automatic deadline management using Java LocalDate
* Detect/highlight overdue tasks
* Modern dark-themed graphical user interface (GUI)


## Technologies Used

* Java
* Java Swing
* Object-Oriented Programming (OOP)
* Collections Framework (ArrayList)
* Enums
* File I/O (Scanner, PrintWriter)

## Project Structure

* `Task.java` – Represents a task and its properties
* `TaskManager.java` – Handles task management logic
* `Priority.java` – Defines task priority levels
* `TaskManagerGUI.java` – Graphical user interface
* `Main.java` – Application entry point

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/linamarg/task-manager-app.git
   ```

2. Open the project in IntelliJ IDEA.

3. Run `Main.java`.

## Future Improvements

* Search tasks
* Task filtering
* Database integration
* Notifications and reminders

## Author

Lina Margaryan
