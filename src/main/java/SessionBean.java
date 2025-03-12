import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@SessionScoped
public class SessionBean {

    private boolean loggedIn;

    // Getters and setters
    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // Method to handle logout
    public void logout() {
        loggedIn = false;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        try {
            facesContext.getExternalContext().redirect("/cleverquiz/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}