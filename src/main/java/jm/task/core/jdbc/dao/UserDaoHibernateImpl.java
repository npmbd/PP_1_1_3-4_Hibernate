package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = """
                    CREATE TABLE IF NOT EXISTS user_profile (
                    id BIGINT AUTO_INCREMENT,
                    firstName VARCHAR(128),
                    lastName VARCHAR(128),
                    age TINYINT UNSIGNED,
                    PRIMARY KEY (id)
                    );
                """;
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS user_profile;";

        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);

        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        session.delete((User) session.get(User.class, id));

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = """
                SELECT *
                FROM user_profile;
                """;
        List<User> users = session.createSQLQuery(sql).addEntity(User.class).list();

        session.getTransaction().commit();
        session.close();

        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();

        String sql = "TRUNCATE TABLE user_profile;";
        session.createSQLQuery(sql).executeUpdate();

        session.getTransaction().commit();
        session.close();
    }
}