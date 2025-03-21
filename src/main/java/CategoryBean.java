import java.io.Serializable;
import java.util.regex.Pattern;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void saveCategory() {
        if (!isValidInput(categoryName)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Invalid category name."));
            return;
        }

        FacesContext context = FacesContext.getCurrentInstance();
        if (categoryName == null || categoryName.trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Category name cannot be empty"));
        } else {
            IController controller = new Controller();
            boolean success = controller.addCategory(categoryName);
            if(success) {
                // Add a success message to the FacesContext
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Category saved: " + categoryName));
            } else {
                // Add an error message to the FacesContext
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Category could not be saved"));
            }

            categoryName = null;
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