import java.io.IOException;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import cleverquiz.model.User;

@ManagedBean
@SessionScoped
public class SessionBean {

    private boolean loggedIn;
    private String username;
    private User user;

    /**
     * Gets the current user.
     * 
     * @return the user object
     */
    public User getUser() {
        return user;
    }

    /**
     * Sets the current user.
     * 
     * @param user the user object to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Gets the username of the logged-in user.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the logged-in user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    // Getters and setters
    /**
     * Checks if the user is logged in.
     * 
     * @return true if the user is logged in, false otherwise
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * Sets the logged-in status of the user.
     * 
     * @param loggedIn the logged-in status to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    // Method to handle logout
    /**
     * Logs out the current user by invalidating the session and redirecting to the
     * login page.
     */
    public void logout() {
        loggedIn = false;
        username = null;
        FacesContext facesContext = FacesContext.getCurrentInstance();
        facesContext.getExternalContext().invalidateSession();
        try {
            facesContext.getExternalContext().redirect("/cleverquiz/login.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}