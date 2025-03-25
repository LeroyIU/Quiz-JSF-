import java.io.Serializable;
import java.util.regex.Pattern;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.Icon;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;

@ManagedBean
@ViewScoped
public class CategoryBean implements Serializable {
    private String categoryName;

    /**
     * Gets the name of the category.
     * 
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     * 
     * @param categoryName the category name to set
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Saves the category. Validates the input and interacts with the controller
     * to persist the category. Displays appropriate messages based on the outcome.
     */
    public void saveCategory() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (!isValidInput(categoryName)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidCategoryName.text")));
            return;
        }

        FacesContext context = FacesContext.getCurrentInstance();
        if (categoryName == null || categoryName.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("emptyCategoryName.text")));
        } else {
            IController controller = new Controller();
            boolean success = controller.addCategory(categoryName);

            if (success) {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("categorySaved.text") + ": " + categoryName));
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("categorySaveFailed.text")));
            }

            categoryName = null;
        }
    }

    /**
     * Validates the input string to ensure it matches the allowed pattern.
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