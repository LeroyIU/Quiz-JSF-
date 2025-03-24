package cleverquiz.controller;

import cleverquiz.db.DBUtil;
import cleverquiz.model.*;
import junit.framework.TestCase;

import java.util.ArrayList;
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

//    public void testAddCategory() {
//        String CategoryName = "Informatik";
//        IController controller = new Controller();
//        boolean success = controller.addCategory(CategoryName);
//        assertTrue(success);
//    }

    public void testGetFriends() {
        IController controller = new Controller();
        List<User> friends = controller.getFriends(175);
        assertFalse(friends.isEmpty());
        System.out.println("Friends:");
        for ( User user : friends) {
            System.out.println(user.toString());
        }
    }

    public void testRemoveFriend() {
        IController controller = new Controller();
        controller.deleteFriend(97, 98);
        List<User> friends = controller.getFriends(97);
        assertTrue(friends.isEmpty());
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

    public void testUpdateUser() {
        IController controller = new Controller();
        List<User> users = controller.searchUser("na");
        User user = users.getFirst();
        user.setName("UpdatedName");
        User newUser = controller.editProfile(user);
        assertEquals(user, newUser);
    }

    public void testCreateUser() {
        IController controller = new Controller();
        String name = "username";
        String email = "newUser@Email";
        String password = "1234";
        User newUser = controller.addUser(name, email, password);
        assertNotNull(newUser);
    }
    public void testLogin() {
        IController controller = new Controller();
        //wrong username
        String name = "asdf";
        String password = "1234";
        User user = controller.login(name, password);
        assertNull(user);
        //wrong pw
        name = "username";
        password = "asdf";
        user = controller.login(name, password);
        assertNull(user);
        //correct credentials
        name = "bot";
        password = "robot";
        user = controller.login(name, password);
        assertNotNull(user);
    }

    public void testCreateQuestion() {
        IController controller = new Controller();
        Difficulty difficulty = Difficulty.Easy;
        String text = "";
        List<Answer> answers = new ArrayList<>();
        //easy
        answers.clear();
        text = "Das zu welchem Modul gehört dieses Projekt ?";
        for(int i = 1; i < 20; i++) {
            Answer answer = new Answer();
            answer.setText("Answer" + i);
            answer.setCorrectness(i%2==0);
            answers.add(answer);
        }

        assertTrue(controller.createQuestion(difficulty, text, answers));
    }

    public void testCreateNews() {
        IController controller = new Controller();
        User user = controller.login("bot", "robot");
        assertTrue(controller.createNews("Ich bin ein Titel", "Ich bin ein Text", user));
    }

    public void testGetAnswersByQuestionId() {
        List<Answer> list = DBUtil.getAnswersByQuestionId(18);
        assertNotNull(list);
        assertFalse(list.isEmpty());
    }

    public void testAddFriend() {
        IController controller = new Controller();
        boolean success = controller.addFriend(97, 98);
        assertTrue(success);
    }

    public void testStartQuiz() {
        IController controller = new Controller();
        Category cat = new Category();
        cat.setName("Geography");
        cat.setCategoryId(3);
        List<Game> games = controller.startQuiz(cat, 5 );
        assertNotNull(games);
        assertFalse(games.isEmpty());
    }
}