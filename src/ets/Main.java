package ets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file
        URL fxmlUrl = getClass().getResource("gui/view/MainWindow.fxml");
        Parent root = FXMLLoader.load(fxmlUrl);

        Scene scene = new Scene(root); // Set the window width and height

        primaryStage.setTitle("Ticket system"); // Set the window title
        primaryStage.setScene(scene); // Set the scene to the stage
        primaryStage.show(); // Show the stage
    }
}