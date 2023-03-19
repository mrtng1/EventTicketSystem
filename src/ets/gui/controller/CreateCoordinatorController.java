package ets.gui.controller;

import io.github.palexdev.materialfx.controls.MFXToggleButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateCoordinatorController implements Initializable {
    @FXML
    private TextField nameField, passwordField;
    @FXML
    private MFXToggleButton customToggle;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String randomUsername = generateRandomText(10);
        String randomPassword = generateRandomText(10);
        nameField.setText(randomUsername);
        passwordField.setText(randomPassword);

        customToggle.setOnAction(event -> {
            nameField.clear();
            passwordField.clear();
        });
    }

    private String generateRandomText(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomText = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            randomText.append(characters.charAt(randomIndex));
        }

        return randomText.toString();
    }
}
