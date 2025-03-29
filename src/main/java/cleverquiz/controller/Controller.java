package cleverquiz.controller;

import cleverquiz.db.DBUtil;
import cleverquiz.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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
    public boolean createQuestion(Difficulty difficulty, String question, List<Answer> answers, Category category, Integer creatorId) {
        return DBUtil.createQuestion(difficulty, question, answers, category, creatorId);
    }

    @Override
    public List<Game> startQuiz(Category category, int amount) {
        List<Game> games = new ArrayList<>();
        List<Question> questions = DBUtil.getQuestions(category, amount);
        for(Question question : questions) {
            Game game = new Game();
            game.setQuestion(question);
            List<Answer> answers = selectRandomAnswers(DBUtil.getAnswersByQuestionId(question.getQuestionId()));
            game.setAnswers(answers);
            games.add(game);
        }
        return games;
    }

    @Override
    public List<User> getFriends(int userId) {
        return DBUtil.getFriends(userId);
    }

    @Override
    public boolean addFriend(int ownerId, int friendId) {
        return DBUtil.addFriend(ownerId, friendId);
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
    public User login(String username, String password) {
        return DBUtil.login(username, password);
    }

    @Override
    public void updateGame(User user, Question question, List<UserAnswer> answers) {
        boolean correct = true;
        for (UserAnswer userAnswer : answers) {
            correct &= userAnswer.isCorrect();
        }
        int xp = 0;
        if(correct) xp = question.getDifficulty().getValue();
        int userxp = user.getXp();
        user.setXp(userxp + xp);
        editProfile(user);
    }

    @Override
    public boolean createNews(String title, String text, User author) {
        return DBUtil.createNews(title, text, author);
    }

    public List<Answer> selectRandomAnswers(List<Answer> allAnswers) {
        // Shuffle die gesamte Liste
        Collections.shuffle(allAnswers);

        // Teile in korrekt und falsch auf
        List<Answer> correct = allAnswers.stream()
                .filter(a -> Boolean.TRUE.equals(a.getCorrectness()))
                .collect(Collectors.toList());

        List<Answer> incorrect = allAnswers.stream()
                .filter(a -> !Boolean.TRUE.equals(a.getCorrectness()))
                .collect(Collectors.toList());

        Random rand = new Random();
        int correctCount = Math.min(correct.size(), rand.nextInt(4) + 1); // 1 bis 4 richtige

        // Hole korrekt Antworten
        List<Answer> selected = new ArrayList<>();
        Collections.shuffle(correct);
        selected.addAll(correct.subList(0, correctCount));

        // Ergänze mit falschen Antworten bis insgesamt 4
        Collections.shuffle(incorrect);
        int remaining = 4 - selected.size();
        selected.addAll(incorrect.subList(0, Math.min(remaining, incorrect.size())));

        // Noch mal mischen für Zufallsreihenfolge
        Collections.shuffle(selected);

        return selected;
    }
}
