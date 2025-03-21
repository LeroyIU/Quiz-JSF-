import java.io.IOException;
import java.util.regex.Pattern;

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

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordVerification.isEmpty() || inviteCode.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "All fields are required."));
            return;
        }
        // Überprüfung des Passworts
        if (!password.equals(passwordVerification)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Passwords do not match."));
            return;
        }
        // Überprüfung der E-Mail-Adresse
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid email format."));
            return;
        }

           // Überprüfung des Invite Codes
        if (!inviteCode.equals("9021830")) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid invite code."));
        return;
        }
        
        cleverquiz.model.User newUser = Controller.addUser(name, email, password);
        if (newUser == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User could not be created."));
            return;
        }
        System.out.println("Name: " + name);
        System.out.println("E-Mail: " + email);
        System.out.println("Invite Code: " + inviteCode);

        try {
            context.getExternalContext().redirect("index.xhtml");
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
