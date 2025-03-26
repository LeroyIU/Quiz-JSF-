import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;

@ManagedBean
@ViewScoped
public class RankingBean implements Serializable {
    private List<Ranking> rankingList = new ArrayList<>(); // Initialisierung hinzugefügt

    /**
     * Initializes the ranking list by fetching user rankings from the controller
     * and populating the list with ranking data.
     */
    @PostConstruct
    public void init() {
        IController controller = new Controller();
        List<cleverquiz.model.User> rankinglist = controller.getUserRanking();

        // Entferne die Überschreibung von rankinglist
        for (int i = 0; i < rankinglist.size(); i++) {
            cleverquiz.model.User user = rankinglist.get(i);
            Ranking ranking = new Ranking(i + 1, user.getUsername(), user.getXp()); // Position beginnt bei 1
            System.out.println(ranking);
            rankingList.add(ranking); // Füge die Daten zur initialisierten Liste hinzu
        }
    }

    /**
     * Retrieves the list of rankings.
     * 
     * @return a list of {@link Ranking} objects representing the user rankings.
     */
    public List<Ranking> getRankingList() {
        return rankingList;
    }

    public static class Ranking {
        private int position;
        private String username;
        private int points;

        /**
         * Constructs a new Ranking object.
         * 
         * @param position the position of the user in the ranking.
         * @param username the username of the user.
         * @param points   the points (XP) of the user.
         */
        public Ranking(int position, String username, int points) {
            this.position = position;
            this.username = username;
            this.points = points;
        }

        /**
         * Retrieves the position of the user in the ranking.
         * 
         * @return the position of the user.
         */
        public int getPosition() {
            return position;
        }

        /**
         * Sets the position of the user in the ranking.
         * 
         * @param position the position to set.
         */
        public void setPosition(int position) {
            this.position = position;
        }

        /**
         * Retrieves the username of the user.
         * 
         * @return the username of the user.
         */
        public String getUsername() {
            return username;
        }

        /**
         * Sets the username of the user.
         * 
         * @param username the username to set.
         */
        public void setUsername(String username) {
            this.username = username;
        }

        /**
         * Retrieves the points (XP) of the user.
         * 
         * @return the points of the user.
         */
        public int getPoints() {
            return points;
        }

        /**
         * Sets the points (XP) of the user.
         * 
         * @param points the points to set.
         */
        public void setPoints(int points) {
            this.points = points;
        }
    }
}