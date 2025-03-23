import java.util.regex.Pattern;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

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

    @PostConstruct
    public void init() {
        populateTestData();
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

        // Save logic here (use parameterized queries in actual database interaction)
        this.editable = false;
        printData();

        // Add growl message
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("profileSaved.text"));
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().update("growl");
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
        // Cancel logic here
        this.editable = false;
    }

    public void populateTestData() {
        this.lastName = "Doe";
        this.firstName = "John";
        this.favoriteColor = "Blue";
        this.favoriteCategory = "Technology";
        this.favoriteMusicGenre = "Rock";
        this.favoriteFood = "Pizza";
        this.aboutMe = "This is a test user.";
        this.selectedBadge = "Option1";
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
}