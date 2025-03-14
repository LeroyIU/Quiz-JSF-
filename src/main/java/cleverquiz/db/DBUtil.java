package cleverquiz.db;

import cleverquiz.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class DBUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();

            // Hibernate-Properties aus resources laden
            Properties properties = new Properties();
            InputStream input = DBUtil.class.getClassLoader().getResourceAsStream("hibernate-secret.properties");

            if (input == null) {
                throw new RuntimeException("hibernate-secret.properties nicht gefunden!");
            }

            properties.load(input);
            configuration.setProperties(properties);

            // Hier die User-Klasse explizit hinzufügen
            configuration.addAnnotatedClass(User.class);

            return configuration.buildSessionFactory();
        } catch (Exception ex) {
            throw new RuntimeException("Fehler beim Laden der Hibernate-Properties", ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void main(String[] args) {
        // Neue Session öffnen
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        // Neuen User speichern
        User newUser = new User();
        newUser.setName("TestUSer");
        newUser.setPassword("1234");
        newUser.setEmail("testMail");
        session.save(newUser);

        transaction.commit();

        // Benutzer aus der Datenbank abrufen
        User user = session.get(User.class, newUser.getUserId());
        System.out.println("Geladener Benutzer: " + user);

        session.close();
        getSessionFactory().close();
    }

    public static User login(String username, String password) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = null;
        User user = null;

        try {
            transaction = session.beginTransaction();

            // HQL Query, um den User zu finden
            Query<User> query = session.createQuery("FROM User WHERE name = :username AND password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);  // Nur wenn das Passwort nicht gehasht gespeichert wird!

            user = query.uniqueResult(); // Falls kein Ergebnis, dann null zurückgeben

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }
}
