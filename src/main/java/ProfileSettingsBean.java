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
    private String plz;
    private String city;
    private String street;
    private String housenumber;
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
        // Save logic here
        this.editable = false;
        printData();

        // Add growl message
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", "Profile settings saved successfully.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        PrimeFaces.current().ajax().update("growl");
    }

    public void cancel() {
        // Cancel logic here
        this.editable = false;
    }

    public void populateTestData() {
        this.lastName = "Doe";
        this.firstName = "John";
        this.plz = "12345";
        this.city = "Musterstadt";
        this.street = "Main Street";
        this.housenumber = "1a";
        this.aboutMe = "This is a test user.";
        this.selectedBadge = "Option1";
    }

    public void printData() {
        System.out.println("Last Name: " + lastName);
        System.out.println("First Name: " + firstName);
        System.out.println("PLZ: " + plz);
        System.out.println("City: " + city);
        System.out.println("Street: " + street);
        System.out.println("Housenumber: " + housenumber);
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

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
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