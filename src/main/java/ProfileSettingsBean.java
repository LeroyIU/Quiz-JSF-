import java.util.regex.Pattern;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import java.util.List;

import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;
import cleverquiz.model.Badge;
import cleverquiz.model.User;

@ManagedBean
@ViewScoped
public class ProfileSettingsBean {
    private boolean editable = false;
    private String lastName;
    private String firstName;
    private String favoriteColor;
    private String favoriteCategory;
    private String favoriteMusicGenre;
    private String favoriteFood;
    private String aboutMe;
    private String selectedBadge;
    private int gamesPlayed;
    private int xp;
    private List<Badge> userBadges;
    private String currentPassword;
    private String newPassword;
    private String repeatNewPassword;

    /**
     * Initializes the bean by populating user data and loading user badges.
     */
    @PostConstruct
    public void init() {
        populateUserData();
        loadUserBadges();
    }

    /**
     * Checks if the profile is in editable mode.
     * @return true if editable, false otherwise.
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * Sets the editable mode for the profile.
     * @param editable true to enable editing, false to disable.
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * Enables editing mode for the profile.
     */
    public void edit() {
        this.editable = true;
    }

    /**
     * Saves the updated profile settings after validation.
     */
    public void save() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        // Validate inputs
        if (!isValidInput(lastName) || !isValidInput(firstName) || !isValidInput(favoriteColor) ||
            !isValidInput(favoriteCategory) || !isValidInput(favoriteMusicGenre) || !isValidInput(favoriteFood) ||
            !isValidInput(aboutMe)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidInput.text"));
            FacesContext.getCurrentInstance().addMessage(null, message);
            PrimeFaces.current().ajax().update("growl");
            return;
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();

        // Update user data
            User user = sessionBean.getUser();
            System.out.println("User-ID: " + sessionBean.getUser());
            if (user != null) {
                user.setLastname(lastName);
                user.setName(firstName);
                user.setFavColour(favoriteColor);
                user.setFavCategory(favoriteCategory);
                user.setFavMusic(favoriteMusicGenre);
                user.setFavFood(favoriteFood);
                user.setAboutme(aboutMe);

                try {
                    // Save updated user using the controller
                    controller.editProfile(user);

                    // Add success message
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile settings saved successfully.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    PrimeFaces.current().ajax().update("growl");
                } catch (Exception e) {
                    // Add error message
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Failed to save profile settings.");
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    PrimeFaces.current().ajax().update("growl");
                    e.printStackTrace();
                }
            } else {
                // Add error message if user is not found
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "User not found.");
                FacesContext.getCurrentInstance().addMessage(null, message);
                PrimeFaces.current().ajax().update("growl");
            }
        this.editable = false;
        printData();
        // Add growl message
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("profileSaved.text"));
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    /**
     * Validates the input string to ensure it meets the required format.
     * @param input The input string to validate.
     * @return true if the input is valid, false otherwise.
     */
    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        // Allow only alphanumeric characters, spaces, and basic punctuation
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"äöüÄÖÜß-]*$"; // Allow German umlauts
        return Pattern.matches(regex, input);
    }

    /**
     * Cancels the editing mode and restores the original profile data.
     */
    public void cancel() {
        this.editable = false;
        populateUserData();
    }

    /**
     * Populates the user data from the session bean.
     */
    public void populateUserData() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        if (sessionBean != null && controller != null) {
            User user = sessionBean.getUser();
            if (user != null) {
                this.lastName = user.getLastname();
                this.firstName = user.getName();
                this.favoriteColor = user.getFavColour();
                this.favoriteCategory = user.getFavCategory();
                this.favoriteMusicGenre = user.getFavMusic();
                this.favoriteFood = user.getFavFood();
                this.aboutMe = user.getAboutme();
                this.gamesPlayed = user.getGameCount();
                this.xp = user.getXp();
            }
        }
    }

    /**
     * Loads the badges associated with the user.
     */
    public void loadUserBadges() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        if (sessionBean != null && controller != null) {
            userBadges = controller.getUserBadges(sessionBean.getUser().getUserId());
        }
    }

    /**
     * Retrieves the list of badges associated with the user.
     * @return A list of user badges.
     */
    public List<Badge> getUserBadges() {
        return userBadges;
    }

    /**
     * Prints the current profile data to the console for debugging purposes.
     */
    public void printData() {
        System.out.println("Last Name: " + lastName);
        System.out.println("First Name: " + firstName);
        System.out.println("Favorite Color: " + favoriteColor);
        System.out.println("Favorite Category: " + favoriteCategory);
        System.out.println("Favorite Music Genre: " + favoriteMusicGenre);
        System.out.println("Favorite Food: " + favoriteFood);
        System.out.println("About Me: " + aboutMe);
        System.out.println("Selected Badge: " + selectedBadge);
    }

    /**
     * Changes the user's password after validating the current password and matching new passwords.
     */
    public void changePassword() {
        System.out.println("changePassword method invoked"); // Debugging log

        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);

        if (sessionBean == null || sessionBean.getUser() == null) {
            System.out.println("SessionBean or User is null"); // Debugging log
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("userNotFound.text"));
            FacesContext.getCurrentInstance().addMessage("passwordGrowl", message);
            return;
        }

        User user = sessionBean.getUser();
        if (!user.getPassword().equals(currentPassword)) {
            System.out.println("Current password does not match"); // Debugging log
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("incorrectPassword.text"));
            FacesContext.getCurrentInstance().addMessage("passwordGrowl", message); // Ensure growl is updated
            return;
        }

        if (newPassword == null || newPassword.isEmpty()) {
            System.out.println("New password is empty"); // Debugging log
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("emptyPassword.text"));
            FacesContext.getCurrentInstance().addMessage("passwordGrowl", message); // Ensure growl is updated
            return;
        }

        if (!newPassword.equals(repeatNewPassword)) {
            System.out.println("New passwords do not match"); // Debugging log
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("passwordMismatch.text"));
            FacesContext.getCurrentInstance().addMessage("passwordGrowl", message); // Ensure growl is updated
            return;
        }

        try {
            IController controller = new Controller();
            user.setPassword(newPassword); // Update the password
            controller.editProfile(user); // Save the updated user to the database

            System.out.println("Password changed successfully"); // Debugging log
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("passwordChanged.text"));
            FacesContext.getCurrentInstance().addMessage("passwordGrowl", message);

            // Clear password fields after successful change
            currentPassword = null;
            newPassword = null;
            repeatNewPassword = null;
        } catch (Exception e) {
            System.out.println("Error while changing password: " + e.getMessage()); // Debugging log
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("passwordChangeFailed.text"));
            FacesContext.getCurrentInstance().addMessage("passwordGrowl", message);
            e.printStackTrace();
        }
    }

    // Getters and setters for other properties
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFavoriteColor() {
        return favoriteColor;
    }

    public void setFavoriteColor(String favoriteColor) {
        this.favoriteColor = favoriteColor;
    }

    public String getFavoriteCategory() {
        return favoriteCategory;
    }

    public void setFavoriteCategory(String favoriteCategory) {
        this.favoriteCategory = favoriteCategory;
    }

    public String getFavoriteMusicGenre() {
        return favoriteMusicGenre;
    }

    public void setFavoriteMusicGenre(String favoriteMusicGenre) {
        this.favoriteMusicGenre = favoriteMusicGenre;
    }

    public String getFavoriteFood() {
        return favoriteFood;
    }

    public void setFavoriteFood(String favoriteFood) {
        this.favoriteFood = favoriteFood;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public String getSelectedBadge() {
        return selectedBadge;
    }

    public void setSelectedBadge(String selectedBadge) {
        this.selectedBadge = selectedBadge;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatNewPassword() {
        return repeatNewPassword;
    }

    public void setRepeatNewPassword(String repeatNewPassword) {
        this.repeatNewPassword = repeatNewPassword;
    }
}