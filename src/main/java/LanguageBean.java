import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

@ManagedBean
@SessionScoped
public class LanguageBean implements Serializable {
    private boolean english = false; // Default to German

    public boolean isEnglish() {
        return english;
    }

    public void setEnglish(boolean english) {
        this.english = english;
    }

    public String getLanguage() {
        return english ? "en" : "de";
    }
}
