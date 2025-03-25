import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@SessionScoped
public class LanguageBean implements Serializable {
    private boolean english = false; // Default to German

    /**
     * Checks if the current language is English.
     * 
     * @return true if the language is English, false otherwise.
     */
    public boolean isEnglish() {
        return english;
    }

    /**
     * Sets the language to English or not.
     * 
     * @param english true to set the language to English, false to set it to German.
     */
    public void setEnglish(boolean english) {
        this.english = english;
    }

    /**
     * Sets the language to English or not based on an AjaxBehaviorEvent.
     * 
     * @param event the AjaxBehaviorEvent containing the new language value.
     */
    public void setEnglish(AjaxBehaviorEvent event) {
        // Assuming the value is passed as a boolean in the event
        Object newValue = event.getComponent().getAttributes().get("value");
        if (newValue instanceof Boolean) {
            this.english = (Boolean) newValue;
        }
    }

    /**
     * Gets the current language code.
     * 
     * @return "en" if the language is English, "de" if the language is German.
     */
    public String getLanguage() {
        return english ? "en" : "de";
    }
}
