import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RankingBean implements Serializable {
    private List<Ranking> rankingList;

    @PostConstruct
    public void init() {
        rankingList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            rankingList.add(new Ranking(i, "User " + i, i * 10));
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