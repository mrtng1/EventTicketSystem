package ets.gui.controller;

// imports
import ets.gui.model.CoordinatorModel;
import ets.gui.model.EventModel;
import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// java imports
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateCoordinatorController implements Initializable {
    private CoordinatorModel model;
    @FXML
    private TextField nameField, passwordField;
    @FXML
    private MFXToggleButton customToggle;

    public void setModel(CoordinatorModel model){
        this.model = model;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String randomUsername = generateRandomText(6, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz")+"EASV";
        String randomPassword = generateRandomText(10, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        nameField.setText(randomUsername);
        passwordField.setText(randomPassword);

        customToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            nameField.setEditable(isSelected);
            passwordField.setEditable(isSelected);
        });
    }

    private String generateRandomText(int length, String characters) {
        Random random = new Random();
        StringBuilder randomText = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomText.append(characters.charAt(randomIndex));
        }

        return randomText.toString();
    }

    @FXML
    public void cancelBtn(javafx.event.ActionEvent event) {
        System.out.println("canceling...");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void createBtn(javafx.event.ActionEvent event) {
        System.out.println("creating...");



        String username = nameField.getText();
        String password = passwordField.getText();

        try {
            model.createCoordinator(username, password);
        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
        }

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
