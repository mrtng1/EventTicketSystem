package ets;

// imports
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// java imports
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author tomdra01, mrtng1
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Load the FXML file
        URL fxmlUrl = getClass().getResource("gui/view/login_window.fxml");
        Parent root = FXMLLoader.load(fxmlUrl);

        Scene scene = new Scene(root); // Set the window width and height

        primaryStage.setTitle("Ticket system"); // Set the window title
        primaryStage.setScene(scene); // Set the scene to the stage
        primaryStage.show(); // Show the stage
    }
}