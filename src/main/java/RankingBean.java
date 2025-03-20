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

    @PostConstruct
    public void init() {
        IController controller = new Controller();
        List<cleverquiz.model.User> rankinglist = controller.getUserRanking();

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!Size: " + rankinglist.size());

        // Entferne die Überschreibung von rankinglist
        for (int i = 0; i < rankinglist.size(); i++) {
            cleverquiz.model.User user = rankinglist.get(i);
            Ranking ranking = new Ranking(i + 1, user.getUsername(), user.getXp()); // Position beginnt bei 1
            System.out.println(ranking);
            rankingList.add(ranking); // Füge die Daten zur initialisierten Liste hinzu
        }
    }

    public List<Ranking> getRankingList() {
        return rankingList;
    }

    public static class Ranking {
        private int position;
        private String username;
        private int points;

        public Ranking(int position, String username, int points) {
            this.position = position;
            this.username = username;
            this.points = points;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }
}