import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    /**
     * Gets the name of the user.
     * @return the name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * @param name the name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email of the user.
     * @return the email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * @param email the email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password the password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the password verification input.
     * @return the password verification input.
     */
    public String getPasswordVerification() {
        return passwordVerification;
    }

    /**
     * Sets the password verification input.
     * @param passwordVerification the password verification to set.
     */
    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    /**
     * Gets the invite code provided by the user.
     * @return the invite code.
     */
    public String getInviteCode() {
        return inviteCode;
    }

    /**
     * Sets the invite code provided by the user.
     * @param inviteCode the invite code to set.
     */
    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    /**
     * Validates the input fields, displays appropriate messages, and redirects to the login page if successful.
     */
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

        String newhashedPassword = hashPassword(password);
        cleverquiz.model.User newUser = Controller.addUser(name, email, newhashedPassword);
        if (newUser == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("userCreationFailed.text")));
            return;
        }
        System.out.println("Name: " + name);
        System.out.println("E-Mail: " + email);
        System.out.println("Invite Code: " + inviteCode);

        try {
            String hashedPassword = hashPassword(password);
            cleverquiz.model.User user = Controller.login(name, hashedPassword);
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

    /**
     * Validates the input string against a specific pattern.
     * @param input the input string to validate.
     * @return true if the input is valid or empty, false otherwise.
     */
    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"-]*$";
        return Pattern.matches(regex, input);
    }

    /**
     * Hashes the given password using SHA-256.
     * 
     * @param password the password to hash
     * @return the hashed password as a hexadecimal string
     */
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
