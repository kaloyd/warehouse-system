package system.application.controllers.owner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import system.application.controllers.AbstractController;
import system.application.controllers.Controller;
import system.application.controllers.FxmlLoader;
import system.application.others.DataRetriever;
import system.application.others.MessageService;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;
import system.backend.profiles.ProfileManager;

import javax.validation.ConstraintViolation;
import java.net.URL;
import java.util.*;

public class ViewProfileController extends AbstractController implements Initializable, Controller {
    @FXML
    private AnchorPane editPane;  // Container for edit. Loads whenever edit button is pressed
    @FXML
    private VBox profileVbox;   // Container for user info
    @FXML
    private AnchorPane anchorPane = null;   // Parent Container
    @FXML
    private Label firstNameLabel = null;
    @FXML
    private Hyperlink why1;
    @FXML
    private Hyperlink why2;
    @FXML
    private TextField firstNameField;
    @FXML
    private Label lastNameLabel = null;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label usernameLabel = null;
    @FXML
    private TextField usernameField;
    @FXML
    private Label emailLabel = null;
    @FXML
    private TextField emailField;
    @FXML
    private Label phoneLabel = null;
    @FXML
    private TextField phoneField;
    @FXML
    private Button editButton = null;
    @FXML
    private Hyperlink changePassword = null;
    @FXML
    private VBox consVbox1;  // Container which shows violations if there are
    @FXML
    private VBox consVbox2;  // Container which shows violations if there are
    @FXML
    private Label violationsLabel;

    private Owner owner;
    private OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();

    private Map<String, String> newData = new HashMap<>();
    private Map<String, String> oldData = new HashMap<>();
    private Set<ConstraintViolation<Object>> cons = new LinkedHashSet<>();

    private List<String> firstname_con = new ArrayList<>();
    private List<String> lastname_con = new ArrayList<>();
    private List<String> username_con = new ArrayList<>();
    private List<String> pass_con = new ArrayList<>();
    private List<String> email_con = new ArrayList<>();
    private List<String> phone_con = new ArrayList<>();

    ProfileManager<Owner> ownerProfileManager = new ProfileManager<>();
    DataRetriever dataRetriever = DataRetriever.getInstance();
    MessageService messageService = MessageService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.owner = ownerDataHolder.getOwner();

        // sets the labels with owner info
        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());

        // Makes the size of the container dynamic
        consVbox1.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox1.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxHeight(Region.USE_PREF_SIZE);

        // Makes the size of the container dynamic
        consVbox2.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox2.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setMaxHeight(Region.USE_PREF_SIZE);
    }

    @Override
    public void fillConsBox1(String message) {  // Fills the container with  violation labels
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 10px");
        consVbox1.getChildren().add(consLabel);
        System.out.println(message);
        why1.setVisible(true);
    }

    @Override
    public void fillConsBox2(String message) { // Fills the container with  violation labels
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 10px");
        consVbox2.getChildren().add(consLabel);
        System.out.println(message);
        why2.setVisible(true);
    }

    public void setLoader(String fxmlFile){ // sets the parent container to a new FXML File
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);

        anchorPane.getChildren().clear();   // clears all children elements of the container
        anchorPane.getChildren().add(view);  // loads new fxmlFile with new children elements
    }

    public void handleEditButton(ActionEvent event) {

        System.out.println("You clicked: " + editButton.getText());
        // setLoader("changePasswordFXML");
        firstNameField.setText(owner.getFirstname());
        lastNameField.setText(owner.getLastname());
        usernameField.setText(owner.getUsername());
        emailField.setText(owner.getEmailAddress());
        phoneField.setText(owner.getPhoneNumber());

        editPane.setVisible(true);
        profileVbox.setVisible(false);
    }

    public void handleChangePass(ActionEvent event) {   // loads fxml file
        System.out.println("You clicked: " + changePassword.getText());
        setLoader("changePasswordFXML");
    }

    public void handleDoneButton(ActionEvent event) {   // Updates user info
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();
        why1.setVisible(false);
        why2.setVisible(false);
        violationsLabel.setVisible(false);

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

//        firstNameLabel.setText(firstNameField.getText());
//        lastNameLabel.setText(lastNameField.getText());
//        usernameLabel.setText(usernameField.getText());
//        emailLabel.setText(emailField.getText());
//        phoneLabel.setText(phoneField.getText());

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());

        firstname_con.clear();
        lastname_con.clear();
        username_con.clear();
        email_con.clear();
        phone_con.clear();

        cons.clear();
        oldData.clear();
        newData.clear();

        dataRetriever.getEditDataWithoutPass(this, newData);
        ownerProfileManager.getProfileDataWithoutPass(owner, oldData);
        cons = ownerProfileManager.updateProfileWithoutPass(owner, Owner.class, newData);

        if(cons.isEmpty()) {
            System.out.println("Owner successfully updated!");
            editPane.setVisible(false);
            profileVbox.setVisible(true);
        }
        else {
            violationsLabel.setVisible(true);

            ownerProfileManager.setProfileDataWithoutPass(owner, oldData);
            addConstraints(cons);
            showMessages();
        }
        //ownerDataHolder.setOwner(owner);
        System.out.println("owner phone:" + owner.getPhoneNumber());

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());
    }

    public void addConstraints(Set<ConstraintViolation<Object>> cons){
        for (ConstraintViolation<Object> con : cons) {
            if (con.getPropertyPath().toString().equals("firstname"))
                firstname_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("lastname"))
                lastname_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("username")) {
                if (con.getMessage().equals(" is already taken")) {
                    username_con.add("Username" + con.getMessage());
                } else username_con.add(con.getMessage());
            }
            else if (con.getPropertyPath().toString().equals("emailAddress")) {
                if (con.getMessage().equals(" is already taken"))
                    email_con.add("Email address" + con.getMessage());
                else email_con.add(con.getMessage());
            }
            else if (con.getPropertyPath().toString().equals("phoneNumber")) {
                if (con.getMessage().equals(" is already taken"))
                    phone_con.add("Phone number" + con.getMessage());
                else phone_con.add(con.getMessage());
            }
        }
    }

    public void showMessages(){
        System.out.println("\n\n\nShow messages:");
        System.out.println("First Name Violations:");

        for (String message : firstname_con) {  // traverses through the List
            if (!message.isEmpty()) {
                fillConsBox1(message);  // passes the message to the method which will print violations
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Last Name Violations:");
        for (String message : lastname_con) {   // traverses through the List
            if (!message.isEmpty()) {
                fillConsBox1(message);  // passes the message to the method which will print violations
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Username Violations:");
        for (String message : username_con) {   // traverses through the List
            if (!message.isEmpty()) {
                fillConsBox1(message);  // passes the message to the method which will print violations
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Password Violations:");
        for (String message : pass_con) {   // traverses through the List
            if (!message.isEmpty()) {
                fillConsBox2(message);  // passes the message to the method which will print violations
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Email Violations:");
        for (String message : email_con) {  // traverses through the List
            if (!message.isEmpty()) {
                fillConsBox2(message);  // passes the message to the method which will print violations
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Phone Violations:");
        for (String message : phone_con) {  // traverses through the List
            if (!message.isEmpty()) {
                fillConsBox2(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
    }

    public void handleCancelButton(ActionEvent event) {
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();
        violationsLabel.setVisible(false);
        why1.setVisible(false);
        why2.setVisible(false);
        editPane.setVisible(false);
        profileVbox.setVisible(true);
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneNumberField() {
        return phoneField;
    }

    public void showConsBox1(MouseEvent mouseEvent) {
        consVbox1.setVisible(true);
    }

    public void hideConsBox1(MouseEvent mouseEvent) {
        consVbox1.setVisible(false);
    }

    public void showConsBox2(MouseEvent mouseEvent) {
        consVbox2.setVisible(true);
    }

    public void hideConsBox2(MouseEvent mouseEvent) {
        consVbox2.setVisible(false);
    }
}
