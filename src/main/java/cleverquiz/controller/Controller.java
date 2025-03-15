package cleverquiz.controller;

import cleverquiz.db.DBUtil;
import cleverquiz.model.*;

import java.util.List;

public class Controller implements IController {

    @Override
    public List<News> getLatestNews() {
        return DBUtil.getLatestNews();
    }

    @Override
    public List<User> getUserRanking() {
        return List.of();
    }

    @Override
    public List<Badge> getUserBadges(int userId) {
        return List.of();
    }

    @Override
    public List<Category> getCategories() {
        return List.of();
    }

    @Override
    public boolean addCategory(String name) {
        return false;
    }

    @Override
    public boolean createQuestion(Difficulty difficulty, String question, List<Answer> answers) {
        return false;
    }

    @Override
    public List<Question> startQuiz(Category category, int amount) {
        return List.of();
    }

    @Override
    public List<User> getFriends(int userId) {
        return List.of();
    }

    @Override
    public void deleteFriend(int userId, int friendToDelete) {

    }

    @Override
    public List<User> searchUser(String token) {
        return List.of();
    }

    @Override
    public User editProfile(User user) {
        return null;
    }

    @Override
    public boolean addUser(String name, String email, String password) {
        return false;
    }

    @Override
    public void updateGame(int questionId, List<UserAnswer> answers) {

    }
}
