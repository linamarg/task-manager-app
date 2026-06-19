import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TaskManagerGUI extends JFrame {

    private TaskManager manager;

    private DefaultListModel<Task> listModel;
    private JList<Task> taskList;

    private JTextField titleField;
    private JTextField descriptionField;
    private JTextField deadlineField;

    private JComboBox<Priority> priorityBox;

    private Color backgroundColor = new Color(24, 24, 28);
    private Color panelColor = new Color(36, 36, 42);
    private Color accentColor = new Color(120, 119, 198);
    private Color textColor = Color.WHITE;

    public TaskManagerGUI() {

        manager = new TaskManager();
        manager.loadFromFile();

        setTitle("Task Manager Pro");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // MAIN PANEL
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // HEADER
        JLabel titleLabel = new JLabel("Task Manager");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 32));
        titleLabel.setForeground(textColor);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(backgroundColor);
        headerPanel.add(titleLabel);

        // INPUT PANEL
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
        inputPanel.setBackground(panelColor);
        inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        titleField = createStyledTextField();
        descriptionField = createStyledTextField();
        deadlineField = createStyledTextField();

        priorityBox = new JComboBox<>(Priority.values());
        styleComboBox(priorityBox);

        inputPanel.add(createLabel("Title"));
        inputPanel.add(titleField);

        inputPanel.add(createLabel("Description"));
        inputPanel.add(descriptionField);

        inputPanel.add(createLabel("Deadline"));
        inputPanel.add(deadlineField);

        inputPanel.add(createLabel("Priority"));
        inputPanel.add(priorityBox);

        JButton addButton = createStyledButton("Add Task");
        JButton saveButton = createStyledButton("Save Tasks");

        inputPanel.add(addButton);
        inputPanel.add(saveButton);

        // TASK LIST
        listModel = new DefaultListModel<>();
        taskList = new JList<>(listModel);

        taskList.setBackground(panelColor);
        taskList.setForeground(textColor);
        taskList.setFont(new Font("SansSerif", Font.PLAIN, 16));
        taskList.setSelectionBackground(accentColor);
        taskList.setFixedCellHeight(40);

        JScrollPane scrollPane = new JScrollPane(taskList);
        scrollPane.setBorder(BorderFactory.createLineBorder(accentColor, 2));
        scrollPane.getViewport().setBackground(panelColor);

        // LOAD TASKS
        for (Task t : manager.getTasks()) {
            listModel.addElement(t);
        }

        // BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(backgroundColor);

        JButton doneButton = createStyledButton("Mark Done");
        JButton deleteButton = createStyledButton("Delete Task");
        JButton sortButton = createStyledButton("Sort By Priority");
        JButton editButton = createStyledButton("Edit Task");

        buttonPanel.add(doneButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(sortButton);

        // LEFT SIDE
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(backgroundColor);

        leftPanel.add(headerPanel, BorderLayout.NORTH);
        leftPanel.add(inputPanel, BorderLayout.CENTER);

        // MAIN LAYOUT
        mainPanel.add(leftPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // ADD TASK
        addButton.addActionListener(e -> {

            String title = titleField.getText();
            String description = descriptionField.getText();
            String deadline = deadlineField.getText();

            Priority priority =
                    (Priority) priorityBox.getSelectedItem();

            if (!title.isEmpty()) {

                manager.addTask(
                        title,
                        description,
                        priority,
                        deadline
                );

                listModel.addElement(
                        manager.getTasks().get(
                                manager.getSize() - 1
                        )
                );

                titleField.setText("");
                descriptionField.setText("");
                deadlineField.setText("");
            }
        });

        // DELETE TASK
        deleteButton.addActionListener(e -> {

            int selectedIndex = taskList.getSelectedIndex();

            if (selectedIndex != -1) {

                manager.removeTask(selectedIndex);
                listModel.remove(selectedIndex);
            }
        });

        // MARK DONE
        doneButton.addActionListener(e -> {

            int selectedIndex = taskList.getSelectedIndex();

            if (selectedIndex != -1) {

                manager.markTaskDone(selectedIndex);
                taskList.repaint();
            }
        });

        // SORT TASKS
        sortButton.addActionListener(e -> {

            manager.sortByPriority();
            refreshTaskList();
        });

        // SAVE TASKS
        saveButton.addActionListener(e -> {

            manager.saveToFile();

            JOptionPane.showMessageDialog(
                    null,
                    "Tasks saved successfully!"
            );
        });

        editButton.addActionListener(e -> {

            Task selectedTask = taskList.getSelectedValue();

            if (selectedTask == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please select a task first."
                );
                return;
            }

            JTextField titleField =
                    new JTextField(selectedTask.getTitle());

            JTextField descriptionField =
                    new JTextField(selectedTask.getDescription());

            JTextField deadlineField =
                    new JTextField(selectedTask.getDeadline());

            JComboBox<Priority> priorityBox =
                    new JComboBox<>(Priority.values());

            priorityBox.setSelectedItem(
                    selectedTask.getPriority()
            );

            Object[] fields = {
                    "Title:", titleField,
                    "Description:", descriptionField,
                    "Deadline:", deadlineField,
                    "Priority:", priorityBox
            };

            int result = JOptionPane.showConfirmDialog(
                    null,
                    fields,
                    "Edit Task",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (result == JOptionPane.OK_OPTION) {

                manager.updateTask(
                        selectedTask,
                        titleField.getText(),
                        descriptionField.getText(),
                        (Priority) priorityBox.getSelectedItem(),
                        deadlineField.getText()
                );

                refreshTaskList();
            }
        });

        setVisible(true);
    }



    // REFRESH TASK LIST
    private void refreshTaskList() {

        listModel.clear();

        for (Task t : manager.getTasks()) {
            listModel.addElement(t);
        }
    }

    // STYLED BUTTON
    private JButton createStyledButton(String text) {

        JButton button = new JButton(text);

        button.setFocusPainted(false);
        button.setBackground(accentColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setBorder(new EmptyBorder(10, 15, 10, 15));

        return button;
    }

    // STYLED TEXT FIELD
    private JTextField createStyledTextField() {

        JTextField field = new JTextField();

        field.setBackground(new Color(50, 50, 58));
        field.setForeground(textColor);
        field.setCaretColor(textColor);
        field.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));

        return field;
    }

    // STYLED LABEL
    private JLabel createLabel(String text) {

        JLabel label = new JLabel(text);

        label.setForeground(textColor);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));

        return label;
    }

    // STYLE COMBO BOX
    private void styleComboBox(JComboBox<Priority> comboBox) {

        comboBox.setBackground(new Color(50, 50, 58));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }
}