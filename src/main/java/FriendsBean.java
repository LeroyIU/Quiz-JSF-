import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.Icon;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;



@ManagedBean
@ViewScoped
public class FriendsBean implements Serializable {
    private List<Friend> friends;
    private List<Friend> searchResults;
    private String searchQuery;
    private Friend selectedFriend;

    public FriendsBean() {
        friends = new ArrayList<>();
        searchResults = new ArrayList<>();
        // Add test data
        friends.add(new Friend("user1", "Badge1", "2025-03-08", "About user1", 100));
        friends.add(new Friend("user2", "Badge2", "2025-03-07", "About user2", 200));
        friends.add(new Friend("user3", "Badge3", "2025-03-06", "About user3", 300));
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
        IController controller = new Controller();
        List<cleverquiz.model.User> tmpList = controller.searchUser(searchQuery);
        searchResults.clear();
        for (cleverquiz.model.User user : tmpList) {
            searchResults.add(new Friend(user.getUsername()));
        }
    }

    public void deleteFriend(Friend friend) {
        friends.remove(friend);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Friend Deleted", "Removed friend: " + friend.getUsername()));
        System.out.println("User deleted: " + friend.getUsername());
    }

    public void addFriend(Friend friend) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Friend Added", "Adding friend: " + friend.getUsername()));
        System.out.println("User added: " + friend.getUsername());
    }

    public static class Friend {
        private String username;
        private String badge;
        private String lastSeen;
        private String aboutMe;
        private int xp;

        public Friend(String username, String badge, String lastSeen, String aboutMe, int xp) {
            this.username = username;
            this.badge = badge;
            this.lastSeen = lastSeen;
            this.aboutMe = aboutMe;
            this.xp = xp;
        }
        public Friend(String username) {
            this(username,"","","",0);
        }

        public String getUsername() {
            return username;
        }

        public String getBadge() {
            return badge;
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
    }
}