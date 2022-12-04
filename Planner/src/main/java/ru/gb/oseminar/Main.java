package main.java.ru.gb.oseminar;

import ru.gb.oseminar.controller.TaskController;
import ru.gb.oseminar.model.*;
import ru.gb.oseminar.view.ViewTask;

public class Main {
    public static void main(String[] args) {
        FileOperation fileOperation = new FileOperationImpl("tasks.csv");
        Repository repository = new RepositoryFile(fileOperation);
        TaskController controller = new TaskController(repository);
        ViewTask view = new ViewTask(controller);
        view.run();
    }
}