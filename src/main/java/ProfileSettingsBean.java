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

    @PostConstruct
    public void init() {
        populateUserData();
        loadUserBadges();
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void edit() {
        this.editable = true;
    }

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

    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        // Allow only alphanumeric characters, spaces, and basic punctuation
        String regex = "^[a-zA-Z0-9\\s.,!?'-]*$";
        return Pattern.matches(regex, input);
    }

    public void cancel() {
        this.editable = false;
        populateUserData();
    }

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

    public void loadUserBadges() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
        IController controller = new Controller();
        if (sessionBean != null && controller != null) {
            userBadges = controller.getUserBadges(sessionBean.getUser().getUserId());
        }
    }

    public List<Badge> getUserBadges() {
        return userBadges;
    }

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
}