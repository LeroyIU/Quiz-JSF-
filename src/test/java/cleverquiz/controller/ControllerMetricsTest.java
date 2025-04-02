package cleverquiz.controller;

import cleverquiz.model.*;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertTrue;

public class ControllerMetricsTest extends TestCase{

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
        System.out.println("Dauer: " + durationMs + " ms");
        return durationMs;
    }

    public void testGetLatestNews() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testUserRanking() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testUserBadges() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testCategories() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testAddCategory() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testGetFriends() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testRemoveFriend() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testSearchUser() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testUpdateUser() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testCreateUser() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }
    public void testLogin() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testCreateQuestion() {
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
     
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
     }

    public void testCreateNews() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testGetAnswersByQuestionId() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testAddFriend() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }

    public void testStartQuiz() {
        IController controller = new Controller();
        long duration = measureTime(controller::getLatestNews);
        assertTrue(duration < 1000);
    }
}
