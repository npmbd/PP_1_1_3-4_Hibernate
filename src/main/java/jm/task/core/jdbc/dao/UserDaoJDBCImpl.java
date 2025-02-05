package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            throw  new RuntimeException(e);
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
            throw  new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                INSERT INTO user_profile (firstName, lastName, age)
                VALUES (%s, %s, %d);
                """.formatted("'" + name + "'", "'" + lastName + "'", age);
            statement.execute(sql);
            System.out.printf("User с именем — %s добавлен в базу данных\n", name);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                DELETE FROM user_profile
                WHERE id = %d;
                """.formatted(id);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                SELECT *
                FROM user_profile;
                """;
            ResultSet executeResult = statement.executeQuery(sql);
            while (executeResult.next()) {
                String name = executeResult.getString("firstName");
                String lastName = executeResult.getString("lastName");
                byte age = executeResult.getByte("age");
                User user = new User(name, lastName, age);
                user.setId(executeResult.getLong("id"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = """
                DELETE FROM user_profile;
                """;
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw  new RuntimeException(e);
        }
    }
}