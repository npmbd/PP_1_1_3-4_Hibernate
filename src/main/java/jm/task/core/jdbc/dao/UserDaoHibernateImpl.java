package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    public UserDaoHibernateImpl() { }

    @Override
    public void createUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
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
        } catch (HibernateException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException e1) {
                    System.out.println("Ошибка при откате транзакции: " + e1.getMessage());
                }
            }
            System.out.println("Ошибка при выполнении транзакции: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        Session session = null;
        try{
            session = Util.getSessionFactory().openSession();

            session.beginTransaction();

            String sql = "DROP TABLE IF EXISTS user_profile;";

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException e1) {
                    System.out.println("Ошибка при откате транзакции: " + e1.getMessage());
                }
            }
            System.out.println("Ошибка при выполнении транзакции: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) { // save: HibernateException
        User user = new User(name, lastName, age);
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException e1) {
                    System.out.println("Ошибка при откате транзакции: " + e1.getMessage());
                }
            }
            System.out.println("Ошибка при выполнении транзакции: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();

            session.beginTransaction();

            session.createQuery("DELETE FROM User WHERE id = :id").setParameter("id", id).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException e1) {
                    System.out.println("Ошибка при откате транзакции: " + e1.getMessage());
                }
            }
            System.out.println("Ошибка при выполнении транзакции: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = null;
        List<User> users = null;

        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            users = session.createQuery("from User").list();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException e1) {
                    System.out.println("Ошибка при откате транзакции: " + e1.getMessage());
                }
            }
            System.out.println("Ошибка при выполнении транзакции: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = null;
        try {
            session = Util.getSessionFactory().openSession();
            session.beginTransaction();

            String sql = "TRUNCATE TABLE user_profile;";
            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            if (session != null) {
                try {
                    session.getTransaction().rollback();
                } catch (HibernateException e1) {
                    System.out.println("Ошибка при откате транзакции: " + e1.getMessage());
                }
            }
            System.out.println("Ошибка при выполнении транзакции: " + e.getMessage());
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}