import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

@ManagedBean
@SessionScoped
public class ThemeSwitcherBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean darkTheme = false; // Default to light theme

    public boolean isDarkTheme() {
        return darkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }

    public void setDarkTheme(AjaxBehaviorEvent event) {
        // Logic to toggle the theme based on the event, if needed
        this.darkTheme = !this.darkTheme;
    }

    public String getTheme() {
        return darkTheme ? "arya" : "saga";
    }

    public String getBackgroundColor() {
        return darkTheme ? "#1e1e1e" : "#ffffff";
    }
}