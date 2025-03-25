import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;

@ManagedBean
@RequestScoped
public class RegisterBean {
    private String name;
    private String email;
    private String password;
    private String passwordVerification;
    private String inviteCode;
    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    // Save method to validate input, show growl messages, and redirect to login page
    public void save() {
        IController Controller = new Controller();
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = ResourceBundle.getBundle("messages", context.getViewRoot().getLocale());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordVerification.isEmpty() || inviteCode.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("allFieldsRequired.text")));
            return;
        }
        // Password verification
        if (!password.equals(passwordVerification)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("passwordsDoNotMatch.text")));
            return;
        }
        // Email format verification
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidEmailFormat.text")));
            return;
        }

        if (!inviteCode.equals("9021830")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidInviteCode.text")));
            return;
        }

        cleverquiz.model.User newUser = Controller.addUser(name, email, password);
        if (newUser == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("userCreationFailed.text")));
            return;
        }
        System.out.println("Name: " + name);
        System.out.println("E-Mail: " + email);
        System.out.println("Invite Code: " + inviteCode);

        try {
            cleverquiz.model.User user = Controller.login(name, password);
            SessionBean sessionBean = context.getApplication().evaluateExpressionGet(context, "#{sessionBean}", SessionBean.class);
            sessionBean.setLoggedIn(true);
            sessionBean.setUsername(user.getUsername());
            sessionBean.setUser(user);
            System.out.println("User login successful!");
            FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/index.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"-]*$";
        return Pattern.matches(regex, input);
    }
}
