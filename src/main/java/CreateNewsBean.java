import java.io.Serializable;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import cleverquiz.controller.Controller;
import cleverquiz.controller.IController;
import java.util.ResourceBundle;


@ManagedBean
@ViewScoped
public class CreateNewsBean implements Serializable {
    private String title;
    private String description;

    /**
     * Gets the title of the news.
     * 
     * @return the title of the news
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the news.
     * 
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description of the news.
     * 
     * @return the description of the news
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the news.
     * 
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Saves the news by validating the input and calling the controller to persist the data.
     * Displays appropriate messages based on the success or failure of the operation.
     */
    public void saveNews() {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if (!isValidInput(title) || !isValidInput(description)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("invalidInput.text")));
            return;
        }

        if (title != null && !title.isEmpty() && description != null && !description.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            SessionBean sessionBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{sessionBean}", SessionBean.class);
            IController controller = new Controller();
            controller.createNews(title, description,sessionBean.getUser());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, bundle.getString("success.text"), bundle.getString("newsSaved.text")));
            
            resetForm();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, bundle.getString("error.text"), bundle.getString("titleDescriptionRequired.text")));
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

    /**
     * Resets the form fields by clearing the title and description.
     */
    public void resetForm() {
        title = null;
        description = null;
    }
}