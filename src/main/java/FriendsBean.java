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
    public FriendsBean() {
        friends = new ArrayList<>();
        searchResults = new ArrayList<>();
        populateFriendsList(); // Populate the friends list during initialization
    }

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

    public List<Friend> getFriends() {
        return friends;
    }

    public List<Friend> getSearchResults() {
        return searchResults;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public Friend getSelectedFriend() {
        return selectedFriend;
    }

    public void setSelectedFriend(Friend selectedFriend) {
        this.selectedFriend = selectedFriend;
    }

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

    public void deleteFriend(Friend friend) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        controller.deleteFriend(sessionBean.getUser().getUserId(), friend.getUserId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Friend Deleted", "Removed friend: " + friend.getUsername()));

        System.out.println("User deleted: " + friend.getUsername());
    }

    public void addFriend(Friend friend) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Friend Added", "Adding friend: " + friend.getUsername()));

        System.out.println("User added: " + friend.getUsername());
    }

    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"äöüÄÖÜß-]*$"; // Allow German umlauts
        return Pattern.matches(regex, input);
    }

    public static class Friend {
        private String username;
        private String lastSeen;
        private String aboutMe;
        private int xp;
        private int userid;
        private int gamesPlayed;
      
        public Friend(String username, String lastSeen, String aboutMe, int xp, int id) {
            this.username = username;
            this.lastSeen = lastSeen;
            this.aboutMe = aboutMe;
            this.xp = xp;
            this.userid = id;
        }
        public Friend(String username) {
            this(username,"","",0,0);
        }

        public String getUsername() {
            return username;
        }
      
        public String getLastSeen() {
            return lastSeen;
        }

        public String getAboutMe() {
            return aboutMe;
        }

        public int getXp() {
            return xp;
        }

        public int getUserId() {
            return userid;
        }

        public int getGamesPlayed() {
            return gamesPlayed;
        }

    }
}