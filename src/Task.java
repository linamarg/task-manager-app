public class Task {
    private String title;
    private boolean isDone;
    private String description;
    private Priority priority;
    private String deadline;

    public Task(String title, String description, Priority priority, String deadline) {
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

    public String getDeadline() {
        return deadline;
    }
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void editDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
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
