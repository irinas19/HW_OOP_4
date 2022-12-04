package main.java.ru.gb.oseminar.view;

import ru.gb.oseminar.controller.TaskController;
import ru.gb.oseminar.model.Priority;
import ru.gb.oseminar.model.Task;
import ru.gb.oseminar.model.User;

import java.util.Scanner;

public class ViewTask {
    private TaskController taskController;

    public ViewTask(TaskController taskController) {
        this.taskController = taskController;
    }

    public void run() {
        Commands com = Commands.NONE;
        String lastName = prompt("Введите Фамилию: ");
        String firstName = prompt("Введите Имя: ");
        String patronymic = prompt("Введите Отчество: ");
        if (lastName.isEmpty() || firstName.isEmpty() || patronymic.isEmpty()) {
            throw new IllegalStateException("Fields is empty");
        }
        User user = new User(lastName, firstName, patronymic);
        while (true) {
            try {
                String command = prompt("Введите команду (HELP - список команд): ");
                com = Commands.valueOf(command.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Unknown command");
                continue;
            }
            if (com == Commands.EXIT)   return;
            switch (com) {
                case CREATE:
                    try {
                        String task = prompt("Задача: ");
                        Priority priority = Priority.NONE;
                        try {
                            String input = prompt("Приоритет (high/medium/low): ");
                            priority = Priority.valueOf(input.toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Unknown priority");
                            continue;
                        }
                        String deadline = prompt("Крайний срок выполнения: ");
                        taskController.saveTask(new Task(task, priority, deadline, user));
                    } catch (IllegalStateException e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case READ:
                    String ID = prompt("Идентификатор задачи: ");
                    try {
                        Task task = taskController.readTask(ID);
                        System.out.println(task);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case LIST:
                    taskController.readTasks().forEach(System.out::println);
                    break;
                case DELETE:
                    String taskID = prompt("Идентификатор задачи: ");
                    try {
                        taskController.deleteTask(taskID);
                        System.out.println("OK");
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }
                    break;
                case HELP:
                    System.out.println("Список команд:");
                    System.out.println("CREATE - создание новой задачи");
                    System.out.println("READ - вывод задачи по идентификатору");
                    System.out.println("LIST - вывод списка всех задач");
                    System.out.println("DELETE - удаление задачи по идентификатору");
                    System.out.println("EXIT - выход из программы");
                    break;
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
}
