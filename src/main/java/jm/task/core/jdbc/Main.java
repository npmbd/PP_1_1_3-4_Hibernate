package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Иван", "Иванов", (byte) 20);
        userService.saveUser("Петр", "Соколов", (byte) 25);
        userService.saveUser("Николай", "Борисов", (byte) 30);
        userService.saveUser("Даниил", "Родионов", (byte) 35);

        userService.removeUserById(1L);

        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        userService.cleanUsersTable();

        userService.dropUsersTable();
    }
}