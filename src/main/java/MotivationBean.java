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

    /**
     * Initializes the list of motivational quotes and the random number generator.
     * This method is called automatically after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        motivations = Arrays.asList(
            "Believe in yourself!",
            "You can do it!",
            "Never give up!",
            "Stay positive!",
            "Work hard, dream big!",
            "Aufgeben ist keine Option! - Außer es gibt Kuchen!",
            "Mach´s wie ein Staubsauger: Zieh´s durch egal, was im Weg liegt!",
            "Gib immer 100% - außer beim Blutspenden!",
            "Do more of what makes you happy – unless it’s illegal!" ,
            "Work hard so your dog can have a better life!",
            "Wer aufhört besser zu werden, hat aufgehört gut zu sein! - Philip Rosenthal", 
            "Gib niemals auf! Scheitern und Rückschläge sind nur ein Beweis, dass du es versuchts!- Barack Obama",
            "Ich habe nicht versagt. Ich habe nur 10.000 Wege gefunden, die nicht funktionieren!-Thomas Edison", 
            "Es gibt nur eine Sache, die teurer ist als Bildung: Keine Bildung. – John F. Kennedy",
            "Du kannst alles schaffen- aber vielleicht nicht heute. Morgen klingt auch gut",
            "Lernen ist wie Schokolade – es macht zwar manchmal Kopfweh, aber am Ende fühlt es sich gut an!",
            "Lernen: Der einzige Sport, bei dem du im Sitzen schwitzen kannst.",
            "Wer aufhört zu lernen, hat vermutlich die falsche Serie auf Netflix entdeckt.",
            "Lernen ist wie Tanzen: Manchmal stolpert man, aber das bedeutet nicht, dass man aufhören sollte!",
            "Wissen ist der Schlüssel zum Erfolg. Und der Kaffee ist der Schlüssel, um den Schlüssel zu finden.",
            "Lernen ist wie ein Kaugummi – es mag anfangs zäh sein, aber je länger du dranbleibst, desto süßer wird es!",
            "Learning is like a buffet – you can’t try everything at once, but keep going, there’s always something new on the menu!",
            "They say knowledge is free, but apparently, my textbooks aren’t.",
            "Learning: the art of pretending you understand something until it actually makes sense!"
        );
        random = new Random();
    }

    /**
     * Returns a random motivational quote from the list.
     * 
     * @return A randomly selected motivational quote.
     */
    public String getRandomMotivation() {
        int index = random.nextInt(motivations.size());
        return motivations.get(index);
    }
}
