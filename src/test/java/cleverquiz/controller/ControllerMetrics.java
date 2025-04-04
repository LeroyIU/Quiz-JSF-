package cleverquiz.controller;

import cleverquiz.model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class ControllerMetrics {

    private static IController controller;

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @return duration of task in ms
     */
    public static long measureTime(Runnable task) {
        long start = System.nanoTime();
        task.run();
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @param param parameter
     * @return duration of task in ms
     */
    public static long measureTime(Consumer<String> task, String param) {
        long start = System.nanoTime();
        task.accept(param);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @param param1 parameter1
     * @param param2 parameter2
     * @return duration of task in ms
     */
    public static long measureTime(BiConsumer<String, String> task, String param1, String param2) {
        long start = System.nanoTime();
        task.accept(param1, param2);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @param param parameter
     * @return duration of task in ms
     */
    public static long measureTime(Consumer<Integer> task, int param) {
        long start = System.nanoTime();
        task.accept(param);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @param param1 parameter1
     * @param param2 parameter2
     * @return duration of task in ms
     */
    public static long measureTime(BiConsumer<Integer, Integer> task, int param1, int param2) {
        long start = System.nanoTime();
        task.accept(param1, param2);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @param param1 parameter1
     * @param param2 parameter2
     * @return duration of task in ms
     */
    public static long measureTime(BiConsumer<Category, Integer> task, Category param1, int param2) {
        long start = System.nanoTime();
        task.accept(param1, param2);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    /**
     * Measure the duration in milliseconds of a task
     * @param task task to measure
     * @param param parameter
     * @return duration of task in ms
     */
    public static long measureTime(Consumer<User> task, User param) {
        long start = System.nanoTime();
        task.accept(param);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Duration: " + durationMs + " ms");
        return durationMs;
    }

    @BeforeAll
    public static void init() {
        System.out.println("Init");
        controller = new Controller();
        //init db connection
        controller.login("", "");
    }

    @Test
    public void testGetLatestNews() {
        System.out.println("LatestNews");
        long duration = measureTime(controller::getLatestNews);
        System.out.println();
        assertTrue(duration < 1000);
    }

    @Test
    public void testUserRanking() {
        System.out.println("UserRanking");
        long duration = measureTime(controller::getUserRanking);
        assertTrue(duration < 1000);
    }

    @Test
    public void testUserBadges() {
        System.out.println("UserBadges");
        long duration = measureTime(controller::getUserBadges, 97);
        assertTrue(duration < 1000);
    }

    @Test
    public void testCategories() {
        System.out.println("Categories");
        long duration = measureTime(controller::getCategories);
        assertTrue(duration < 1000);
    }

    @Test
    public void testAddCategory() {
       // System.out.println("AddCategory");
       // long duration = measureTime(controller::addCategory, "Testkategorie");
        // assertTrue(duration < 1000);
    }

    @Test
    public void testGetFriends() {
        System.out.println("GetFriends");
        long duration = measureTime(controller::getFriends, 97);
        assertTrue(duration < 1000);
    }

    @Test
    public void testRemoveFriend() {
        System.out.println("RemoveFriend");
        long duration = measureTime(controller::deleteFriend, 97, 98);
        assertTrue(duration < 1000);
    }

    @Test
    public void testSearchUser() {
        System.out.println("SearchUser");
        long duration = measureTime(controller::searchUser, "na");
        assertTrue(duration < 1000);
    }

    @Test
    public void testUpdateUser() {

        System.out.println("UpdateUser");
        User user = new User();
        user.setName("TestName");
        long duration = measureTime(controller::editProfile, user);
        assertTrue(duration < 1000);
    }

    @Test
    public void testCreateUser() {
        System.out.println("CreateUser");
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    @Test
    public void testLogin() {
        System.out.println("Login");
        long duration = measureTime(controller::login, "", "");
        assertTrue(duration < 1000);
    }

    @Test
    public void testCreateQuestion() {
        System.out.println("CreateQuestion");
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

        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
     }

    public void testCreateNews() {
        System.out.println("CreateNews");
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testAddFriend() {
        System.out.println("AddFriend");
        long duration = measureTime(controller::addFriend, 97, 98);
        assertTrue(duration < 1000);
    }

    public void testStartQuiz() {
        System.out.println("StartQuiz");
        Category category = new Category();
        category.setCategoryId(1);
        category.setName("Java");
        long duration = measureTime(controller::startQuiz, category, 2);
        assertTrue(duration < 1000);
    }
}
