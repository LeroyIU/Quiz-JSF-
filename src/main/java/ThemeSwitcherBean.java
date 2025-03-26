import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@SessionScoped
public class ThemeSwitcherBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean darkTheme = false; // Default to light theme

    /**
     * Checks if the dark theme is enabled.
     * 
     * @return true if dark theme is enabled, false otherwise.
     */
    public boolean isDarkTheme() {
        return darkTheme;
    }

    /**
     * Sets the dark theme state.
     * 
     * @param darkTheme true to enable dark theme, false to enable light theme.
     */
    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }

    /**
     * Toggles the theme state based on an Ajax behavior event.
     * 
     * @param event the Ajax behavior event triggering the theme toggle.
     */
    public void setDarkTheme(AjaxBehaviorEvent event) {
        // Logic to toggle the theme based on the event, if needed
        this.darkTheme = !this.darkTheme;
    }

    /**
     * Retrieves the current theme name.
     * 
     * @return "arya" if dark theme is enabled, "saga" otherwise.
     */
    public String getTheme() {
        return darkTheme ? "arya" : "saga";
    }

    /**
     * Retrieves the background color based on the current theme.
     * 
     * @return the background color as a hex string.
     */
    public String getBackgroundColor() {
        return darkTheme ? "#1e1e1e" : "#ffffff";
    }
}