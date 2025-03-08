import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ManagedBean
@ViewScoped
public class GameBean implements Serializable {
    private List<String> categories;
    private String selectedCategory;
    private int questionCount;

    // Initialize categories and other properties
    public GameBean() {
        // Use a static list of categories for now
        categories = Arrays.asList("Category 1", "Category 2", "Category 3");
    }

    public List<String> getCategories() {
        return categories;
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public void startGame() {
        // Print out the selected category and question count
        System.out.println("Selected Category: " + selectedCategory);
        System.out.println("Question Count: " + questionCount);
    }
}