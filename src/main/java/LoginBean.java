import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;

@ManagedBean
@RequestScoped
public class LoginBean {

    private String username;
    private String password;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method to handle login
    public void login() {
        // Print the username and password to the console
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        // Redirect to the home page
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}