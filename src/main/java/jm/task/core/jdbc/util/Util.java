package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public final class Util {
    private static final String URL = "";
    private static final String USER = "";
    private static final String PASSWORD = "";

    private static SessionFactory sessionFactory;

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

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.FORMAT_SQL, "true");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}