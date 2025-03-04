import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class HelloWorldBean {
    private String message;

    public HelloWorldBean() {
        message = "Hello, World!";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String greet() {
        return "greeting";
    }
}