import java.time.LocalDate;

public class Task {
    private String title;
    private boolean isDone;
    private String description;
    private Priority priority;
    private LocalDate deadline;

    public Task(String title, String description, Priority priority, LocalDate deadline) {
        this.title = title;
        isDone = false;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markDone() {
        isDone = true;
    }

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public void editDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public boolean isOverdue() {
        return deadline.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {

        String status = isDone ? "[✔]" : "[ ]";

        return status + " "
                + title
                + " | " + description
                + " | " + priority
                + " | Due: " + deadline;
    }
}
