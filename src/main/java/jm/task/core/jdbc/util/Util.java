package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Util {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    static {
        loadDriver();
    }

    private Util() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Не удалось установить соединение с базой данных: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Ошибка при загрузке класса Driver: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}