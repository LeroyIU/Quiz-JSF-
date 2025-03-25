import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.Icon;
import java.io.Serializable;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;
import java.util.ResourceBundle;


@ManagedBean
@ViewScoped
public class FriendsBean implements Serializable {
    private List<Friend> friends;
    private List<Friend> searchResults;
    private String searchQuery;
    private Friend selectedFriend;
    private static int gamesPlayed;

    /**
     * Initializes the FriendsBean and populates the friends list.
     */
    public FriendsBean() {
        friends = new ArrayList<>();
        searchResults = new ArrayList<>();
        populateFriendsList(); // Populate the friends list during initialization
    }

    /**
     * Populates the friends list with data from the controller.
     */
    private void populateFriendsList() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        List<cleverquiz.model.User> tmpList = controller.getFriends(sessionBean.getUser().getUserId());
        friends.clear();
        for (cleverquiz.model.User user : tmpList) {
            friends.add(new Friend(user.getUsername(), user.getLastLogin().toString(), user.getAboutme(), user.getXp(), user.getUserId()));
        }
    }

    /**
     * Returns the list of friends.
     * @return List of friends.
     */
    public List<Friend> getFriends() {
        return friends;
    }

    /**
     * Returns the search results.
     * @return List of search results.
     */
    public List<Friend> getSearchResults() {
        return searchResults;
    }

    /**
     * Returns the current search query.
     * @return Search query string.
     */
    public String getSearchQuery() {
        return searchQuery;
    }

    /**
     * Sets the search query.
     * @param searchQuery The search query string.
     */
    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    /**
     * Returns the currently selected friend.
     * @return The selected friend.
     */
    public Friend getSelectedFriend() {
        return selectedFriend;
    }

    /**
     * Sets the currently selected friend.
     * @param selectedFriend The friend to set as selected.
     */
    public void setSelectedFriend(Friend selectedFriend) {
        this.selectedFriend = selectedFriend;
    }

    /**
     * Searches for users based on the search query and filters out current user and friends.
     */
    public void search() {
        populateFriendsList(); // Ensure the friends list is up-to-date
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();

        // Create a set of usernames from the friends list
        List<String> friendUsernames = new ArrayList<>();
        for (Friend friend : friends) {
            friendUsernames.add(friend.getUsername());
        }

        // Filter search results
        List<cleverquiz.model.User> tmpList = controller.searchUser(searchQuery);
        searchResults.clear();
        for (cleverquiz.model.User user : tmpList) {
            if (!user.getUsername().equals(sessionBean.getUsername()) && !friendUsernames.contains(user.getUsername())) { // Exclude current user and friends
                searchResults.add(new Friend(user.getUsername()));
            }
        }
    }

    /**
     * Deletes a friend from the user's friend list.
     * @param friend The friend to delete.
     */
    public void deleteFriend(Friend friend) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        controller.deleteFriend(sessionBean.getUser().getUserId(), friend.getUserId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Friend Deleted", "Removed friend: " + friend.getUsername()));

        System.out.println("User deleted: " + friend.getUsername());
    }

    /**
     * Adds a friend to the user's friend list.
     * @param friend The friend to add.
     */
    public void addFriend(Friend friend) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Friend Added", "Adding friend: " + friend.getUsername()));

        System.out.println("User added: " + friend.getUsername());
    }

    /**
     * Validates the input string to ensure it matches the allowed pattern.
     * @param input The input string to validate.
     * @return True if the input is valid, false otherwise.
     */
    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"äöüÄÖÜß-]*$"; // Allow German umlauts
        return Pattern.matches(regex, input);
    }

    /**
     * Represents a friend with details such as username, last seen, about me, XP, and user ID.
     */
    public static class Friend {
        private String username;
        private String lastSeen;
        private String aboutMe;
        private int xp;
        private int userid;
        private int gamesPlayed;
      
        /**
         * Constructs a Friend object with full details.
         * @param username The username of the friend.
         * @param lastSeen The last seen timestamp of the friend.
         * @param aboutMe The "about me" description of the friend.
         * @param xp The XP points of the friend.
         * @param id The user ID of the friend.
         */
        public Friend(String username, String lastSeen, String aboutMe, int xp, int id) {
            this.username = username;
            this.lastSeen = lastSeen;
            this.aboutMe = aboutMe;
            this.xp = xp;
            this.userid = id;
        }

        /**
         * Constructs a Friend object with only a username.
         * @param username The username of the friend.
         */
        public Friend(String username) {
            this(username,"","",0,0);
        }

        /**
         * Returns the username of the friend.
         * @return The username.
         */
        public String getUsername() {
            return username;
        }
      
        /**
         * Returns the last seen timestamp of the friend.
         * @return The last seen timestamp.
         */
        public String getLastSeen() {
            return lastSeen;
        }

        /**
         * Returns the "about me" description of the friend.
         * @return The "about me" description.
         */
        public String getAboutMe() {
            return aboutMe;
        }

        /**
         * Returns the XP points of the friend.
         * @return The XP points.
         */
        public int getXp() {
            return xp;
        }

        /**
         * Returns the user ID of the friend.
         * @return The user ID.
         */
        public int getUserId() {
            return userid;
        }

        /**
         * Returns the number of games played by the friend.
         * @return The number of games played.
         */
        public int getGamesPlayed() {
            return gamesPlayed;
        }

    }
}