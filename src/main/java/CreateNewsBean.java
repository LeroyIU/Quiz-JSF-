import java.io.Serializable;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@ViewScoped
public class CreateNewsBean implements Serializable {
    private String title;
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void saveNews() {
        if (!isValidInput(title) || !isValidInput(description)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid input detected."));
            return;
        }

        if (title != null && !title.isEmpty() && description != null && !description.isEmpty()) {
            // Simulate saving the news
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "News saved successfully"));
            
            //ToDo: Remove
            System.out.println("News saved: " + this.title + " - " + this.description);
            resetForm();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Title and Description are required"));
        }

        //ToDo: Add connector to database
    }

    private boolean isValidInput(String input) {
        if (input == null || input.isEmpty()) {
            return true; // Allow empty fields
        }
        String regex = "^[a-zA-Z0-9\\s.,!?@#'\"-]*$";
        return Pattern.matches(regex, input);
    }

    public void resetForm() {
        title = null;
        description = null;
    }
}