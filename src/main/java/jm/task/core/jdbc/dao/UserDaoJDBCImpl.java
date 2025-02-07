package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                CREATE TABLE IF NOT EXISTS user_profile (
                id BIGINT AUTO_INCREMENT,
                firstName VARCHAR(128),
                lastName VARCHAR(128),
                age TINYINT UNSIGNED,
                PRIMARY KEY (id)
                );
            """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQLException при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                DROP TABLE IF EXISTS user_profile;
                """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQLException при удалении таблицы: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = """
                INSERT INTO user_profile (firstName, lastName, age)
                VALUES (?, ?, ?);
                """;
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            System.out.println("SQLException при добавлении пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String sql = """
                DELETE FROM user_profile
                WHERE id = ?;
                """;
        try (Connection connection = Util.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException при удалении пользователя по ID: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        String sql = """
                SELECT *
                FROM user_profile;
                """;
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet executeResult = statement.executeQuery();
            while (executeResult.next()) {
                String name = executeResult.getString("firstName");
                String lastName = executeResult.getString("lastName");
                byte age = executeResult.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(executeResult.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.out.println("SQLException при получении списка всех пользователей: " + e.getMessage());
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                TRUNCATE TABLE user_profile;
                """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQLException при очистке таблицы: " + e.getMessage());
        }
    }
}