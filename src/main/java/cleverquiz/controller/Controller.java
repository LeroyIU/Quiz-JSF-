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
        return DBUtil.getUserRanking();
    }

    @Override
    public List<Badge> getUserBadges(int userId) {
        return DBUtil.getUserBadges(userId);
    }

    @Override
    public List<Category> getCategories() {
        return DBUtil.getCategories();
    }

    @Override
    public boolean addCategory(String name) {
        return DBUtil.addCategory(name);
    }

    @Override
    public boolean createQuestion(Difficulty difficulty, String question, List<Answer> answers) {
        return false;
    }

    @Override
    public List<Question> startQuiz(Category category, int amount) {
        return DBUtil.startQuiz(category, amount);
    }

    @Override
    public List<User> getFriends(int userId) {
        return DBUtil.getFriends(userId);
    }

    @Override
    public void deleteFriend(int userId, int friendToDelete) {
        DBUtil.removeFriend(userId, friendToDelete);
    }

    @Override
    public List<User> searchUser(String token) {
        return DBUtil.serchUser(token);
    }

    @Override
    public User editProfile(User user) {
        return DBUtil.editProfile(user);
    }

    @Override
    public User addUser(String name, String email, String password) {
        User user = User.create(name, email, password);
        return DBUtil.createUser(user);
    }

    @Override
    public void updateGame(User user, Question question, List<UserAnswer> answers) {
        boolean correct = true;
        for (UserAnswer userAnswer : answers) {
            correct &= userAnswer.isCorrect();
        }
        int xp = 0;
        if(correct) xp = question.getDifficulty().getPoints();
        int userxp = user.getXp();
        user.setXp(userxp + xp);
        editProfile(user);
    }
}
