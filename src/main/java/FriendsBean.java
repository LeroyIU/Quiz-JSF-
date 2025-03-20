import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

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
        friends.add(new Friend("user1", "Badge1", "2025-03-08", "About user1", 100, 10));
        friends.add(new Friend("user2", "Badge2", "2025-03-07", "About user2", 200, 20));
        friends.add(new Friend("user3", "Badge3", "2025-03-06", "About user3", 300, 30));
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
        if (!isValidInput(searchQuery)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid search query."));
            return;
        }

        searchResults.clear();
        for (Friend friend : friends) {
            if (friend.getUsername().contains(searchQuery)) {
                searchResults.add(friend);
            }
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

    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"-]*$";
        return Pattern.matches(regex, input);
    }

    public static class Friend {
        private String username;
        private String badge;
        private String lastSeen;
        private String aboutMe;
        private int xp;
        private int gamesPlayed;

        public Friend(String username, String badge, String lastSeen, String aboutMe, int xp, int gamesPlayed) {
            this.username = username;
            this.badge = badge;
            this.lastSeen = lastSeen;
            this.aboutMe = aboutMe;
            this.xp = xp;
            this.gamesPlayed = gamesPlayed;
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

        public int getGamesPlayed() {
            return gamesPlayed;
        }

        public void setGamesPlayed(int gamesPlayed) {
            this.gamesPlayed = gamesPlayed;
        }
    }
}