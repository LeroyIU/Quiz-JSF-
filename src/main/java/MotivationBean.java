import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MotivationBean {

    private List<String> motivations;
    private Random random;

    @PostConstruct
    public void init() {
        motivations = Arrays.asList(
            "Believe in yourself!",
            "You can do it!",
            "Never give up!",
            "Stay positive!",
            "Work hard, dream big!"
        );
        random = new Random();
    }

    public String getRandomMotivation() {
        int index = random.nextInt(motivations.size());
        return motivations.get(index);
    }
}
