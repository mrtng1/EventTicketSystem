package ets.gui.controller;

// imports
import ets.be.Event;
import ets.dal.EventDAO;
import ets.gui.model.CoordinatorModel;
import ets.gui.model.EventModel;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

// java imports
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class MainWindowController implements Initializable {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private GridPane eventPane;

    @FXML
    private void viewEventsBtn(ActionEvent event) {
        double startValue = scrollPane.getVvalue();
        double endValue = startValue + 1;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), endValue, Interpolator.EASE_BOTH)
                )
        );
        timeline.play();
    }
    @FXML
    private void createEventBtn(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/createEvent.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Create Event");
            stage.setScene(new Scene(createEventParent));

            // Set the model for the CreateEventController
            CreateEventController createEventController = fxmlLoader.getController();
            createEventController.setModel(new EventModel());
            createEventController.setRefreshCallback(this::refreshEventCards);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void createCoordinatorBtn(ActionEvent event){
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/createCoordinator.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setTitle("Create Coordinator");
            stage.setScene(new Scene(createEventParent));

            CreateCoordinatorController createCoordinatorController = fxmlLoader.getController();
            createCoordinatorController.setModel(new CoordinatorModel());


            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateGridPane() throws IOException {
        EventDAO eventDAO = new EventDAO();
        List<Event> events;
        try {
            events = eventDAO.getAllEvents();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch events from the database.", e);
        }

        int numRows = 5;
        int numColumns = 3;
        String borderColor = "#000000";
        double borderWidth = 1.0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                int eventIndex = row * numColumns + col;
                if (eventIndex >= events.size()) {
                    break;
                }

                Pane pane = new Pane();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/eventCard.fxml"));
                fxmlLoader.setControllerFactory(clazz -> new EventCardController(events.get(eventIndex)));
                Pane contentPane = fxmlLoader.load();

                pane.getChildren().add(contentPane);
                pane.setStyle("-fx-border-color: " + borderColor + "; -fx-border-width: " + borderWidth + ";");

                eventPane.add(pane, col, row);
            }
        }
    }

    public void refreshEventCards() {
        try {
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}