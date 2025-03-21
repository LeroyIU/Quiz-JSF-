import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;

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


        IController controller = new Controller();
        cleverquiz.model.User user = controller.login(username, password);
        if(user != null) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
            sessionBean.setLoggedIn(true);
            // Redirect to the home page
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show error message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid username or password"));
        }
       


        // Simulate login validation for default user
        if ("user".equals(username) && "123".equals(password)) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
            sessionBean.setLoggedIn(true);
            System.out.println("User login successful!");

            // Redirect to the home page
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/index.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show error message
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid username or password"));
        }
    }
}