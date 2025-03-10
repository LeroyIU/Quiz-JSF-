import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

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

    public String getTheme() {
        return darkTheme ? "arya" : "saga";
    }

    public String getBackgroundColor() {
        return darkTheme ? "#1e1e1e" : "#ffffff";
    }
}