import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;
import java.util.Collections;
import java.util.Comparator;

public class TaskManager {
    private ArrayList<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public void addTask(String title) {

        Task t = new Task(
                title,
                "",
                Priority.LOW,
                ""
        );

        tasks.add(t);
    }

    public void addTask(String title, String description, Priority priority, String deadline) {
        Task t = new Task(title, description, priority, deadline);
        tasks.add(t);
    }

    public void removeTask(int index){
        if(index>=0 && index<tasks.size()) {
            tasks.remove(index);
        }
    }

    public void markTaskDone(int index){
        if(index>=0 && index<=tasks.size()) {
            tasks.get(index).markDone();
        }
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public ArrayList<Task> getCompletedTasks() {
        ArrayList<Task> completedTasks = new ArrayList<>();
        for(Task t : tasks) {
            if(t.isDone()) {
                completedTasks.add(t);
            }
        }
        return completedTasks;
    }

    public ArrayList<Task> getIncompletedTasks(){
        ArrayList<Task> incompletedTasks = new ArrayList<>();
        for(Task t : tasks) {
            if(!t.isDone()) {
                incompletedTasks.add(t);
            }
        }
        return incompletedTasks;
    }

    public ArrayList<Task> searchTasks(String keyword) {
        ArrayList<Task> results = new  ArrayList<>();

        for(Task t : tasks) {
            if(t.getTitle().toLowerCase().contains(keyword.toLowerCase()) || t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(t);
            }
        }
        return results;
    }

    public void displayAllTasks(ArrayList<Task> list) {
        for(int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i).getTitle());
        }
    }

    public void saveToFile(){
        try{
            PrintWriter writer = new PrintWriter("tasks.txt");

            for(Task t : tasks) {
                writer.println(
                        t.getTitle() + ";"
                        + t.getDescription() + ";" + t.getPriority() + ";"
                        + t.getDeadline() + ";" + t.isDone()
                );
            }
            writer.close();

        } catch (Exception e) {
            System.out.println("Error while saving file");
        }
    }

    public void loadFromFile(){
        try{
            File file = new File("tasks.txt");

            if(!file.exists()) {
                return;
            }

            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] splits =  line.split(";");

                String title = splits[0];
                String description = splits[1];
                Priority priority = Priority.valueOf(splits[2]);
                String deadline = splits[3];
                Boolean isDone = Boolean.parseBoolean(splits[4]);

                Task t = new Task(title, description, priority, deadline);

                if(isDone){
                    t.markDone();
                }

                tasks.add(t);
            }
        } catch(Exception e) {
            System.out.println("Error while loading file");
        }
    }

    public void sortByPriority() {

        Collections.sort(tasks, new Comparator<Task>() {

            public int compare(Task t1, Task t2) {

                return t1.getPriority().ordinal()
                        - t2.getPriority().ordinal();
            }
        });
    }

    public int getSize() {
        return tasks.size();
    }
}
