package cleverquiz.controller;

import cleverquiz.model.*;

import java.util.List;

public interface IController {

    /**
     * Get the latest 15 news
     *
     * @return latest 15 news
     */
    List<News> getLatestNews();

    /**
     * Get 30 best users
     *
     * @return 30 best user by xp
     */
    List<User> getUserRanking();

    /**
     * Get badges owned by user
     *
     * @param userId owner
     * @return badges owned by user
     */
    List<Badge> getUserBadges(int userId);

    /**
     * Get categories
     *
     * @return categories
     */
    List<Category> getCategories();

    /**
     * Add a new category
     *
     * @param name with name
     * @return true, if category could be added, otherwise false
     */
    boolean addCategory(String name);

    /**
     * Create question
     *
     * @param difficulty with difficulty
     * @param question   question text
     * @param answers    answer options
     * @return true, if it could be created, otherwise false
     */
    boolean createQuestion(Difficulty difficulty, String question, List<Answer> answers);

    /**
     * Start a quiz
     *
     * @param category with category
     * @param amount   with amount of questions
     * @return list of questions
     */
    List<Question> startQuiz(Category category, int amount);

    /**
     * Get friends
     *
     * @param userId ofUser
     * @return list of friends
     */
    List<User> getFriends(int userId);

    /**
     * Remove a friend
     *
     * @param userId         user
     * @param friendToDelete friend to delete
     */
    void deleteFriend(int userId, int friendToDelete);

    /**
     * search for users
     *
     * @param token token to search in username
     * @return list of users which have the token contained in their username
     */
    List<User> searchUser(String token);

    /**
     * Edit userprofile
     *
     * @param user user attributes
     * @return changed user
     */
    User editProfile(User user);

    /**
     * Create User
     *
     * @param name     with name
     * @param email    with email
     * @param password with password
     * @return true, if user could be added, otherwise false
     */
    boolean addUser(String name, String email, String password);

    /**
     * Update user game with current answer
     *
     * @param questionId question id
     * @param answers    user answers
     */
    void updateGame(int questionId, List<UserAnswer> answers);

}
