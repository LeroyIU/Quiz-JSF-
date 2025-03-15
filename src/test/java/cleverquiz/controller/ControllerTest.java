package cleverquiz.controller;

import cleverquiz.model.Badge;
import cleverquiz.model.Category;
import cleverquiz.model.News;
import cleverquiz.model.User;
import junit.framework.TestCase;
import java.util.List;

public class ControllerTest extends TestCase {

    public void testGetLatestNews() {
        IController controller = new Controller();
        List<News> news = controller.getLatestNews();
        assertEquals(15, news.size());
        for(News newspost : news) {
            System.out.println(newspost);
        }
    }

    public void testUserRanking() {
        IController controller = new Controller();
        List<User> users = controller.getUserRanking();
        assertEquals(30, users.size());
        for(User user : users) {
            System.out.println(user);
        }
    }

    public void testUserBadges() {
        IController controller = new Controller();
        List<Badge> badges= controller.getUserBadges(100);
        for(Badge badge : badges) {
            System.out.println(badge);
        }
    }

    public void testCategories() {
        IController controller = new Controller();
        List<Category> categories= controller.getCategories();
        for(Category category : categories) {
            System.out.println(category);
        }
    }

    public void testAddCategory() {
        String CategoryName = "Informatik";
        IController controller = new Controller();
        boolean success = controller.addCategory(CategoryName);
        assertTrue(success);
    }

    public void testGetFriends() {
        IController controller = new Controller();
        List<User> friends = controller.getFriends(97);
        assertFalse(friends.isEmpty());
        for ( User friend : friends) {
            System.out.println(friend.toString());
        }
    }

    public void testSearchUser() {
        IController controller = new Controller();
        List<User> users = controller.searchUser("na");
        assertFalse(users.isEmpty());
        for ( User user : users) {
            System.out.println(user.toString());
        }
    }
}