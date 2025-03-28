import java.io.IOException;
import java.util.regex.Pattern;
import java.util.ResourceBundle;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    /**
     * Gets the username entered by the user.
     * 
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username entered by the user.
     * 
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password entered by the user.
     * 
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password entered by the user.
     * 
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Handles the login process. Validates the input, attempts to log in the user,
     * and redirects to the home page if successful. Displays error messages if the
     * login fails.
     */
    public void login() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (!isValidInput(username) || !isValidInput(password)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidInput.text")));
            return;
        }

        if ("Weihnachts".equals(username) && "Mann".equals(password)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/pages/weihnachtsmann.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if ("Oster".equals(username) && "Hase".equals(password)) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/pages/osterhase.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String hashedPassword = hashPassword(password); // Hash the password
            IController controller = new Controller();

            cleverquiz.model.User user = controller.login(username, hashedPassword);
            if (user != null) {
                FacesContext facesContext = FacesContext.getCurrentInstance();
                SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
                sessionBean.setLoggedIn(true);
                sessionBean.setUsername(user.getUsername());
                sessionBean.setUser(user);

                // Redirect to the home page
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/cleverquiz/index.xhtml");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Show error message
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("loginFailed.text"), bundle.getString("invalidCredentials.text")));
            }
        }
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

    /**
     * Validates the input string to ensure it contains only allowed characters.
     * 
     * @param input the input string to validate
     * @return true if the input is valid or empty, false otherwise
     */
    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"äöüÄÖÜß-]*$"; // Allow German umlauts
        return Pattern.matches(regex, input);
    }
}