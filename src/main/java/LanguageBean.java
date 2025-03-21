import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

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

    public void setEnglish(AjaxBehaviorEvent event) {
        // Assuming the value is passed as a boolean in the event
        Object newValue = event.getComponent().getAttributes().get("value");
        if (newValue instanceof Boolean) {
            this.english = (Boolean) newValue;
        }
    }

    public String getLanguage() {
        return english ? "en" : "de";
    }
}
