package ets.gui.controller;

// imports
import ets.be.Event;
import ets.gui.controller.create.CreateCoordinatorWindowController;
import ets.gui.controller.create.CreateEventWindowController;
import ets.gui.model.CoordinatorModel;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
public class AdminWindowController implements Initializable {

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;

    private int currentPage, totalPages;

    @FXML
    private void viewEvents() {
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
    private void createEvent() {
        scrollPane.setEffect(new GaussianBlur(10));

        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_event_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);// Set the modality if you want to block interaction with other windows while this one is open
            stage.setTitle("Create Event");
            stage.setResizable(false);
            Scene scene = new Scene(createEventParent);
            stage.setScene(scene);

            // Set the model for the CreateEventWindowController
            CreateEventWindowController createEventWindowController = fxmlLoader.getController();
            createEventWindowController.setEventModel(new EventModel());
            createEventWindowController.setCoordinatorModel(new CoordinatorModel());
            createEventWindowController.setRefreshCallback(this::refreshEventCards);
            createEventWindowController.setScrollPane(scrollPane);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createCoordinator(){
        scrollPane.setEffect(new GaussianBlur(10));
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_coordinator_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Set the modality if you want to block interaction with other windows while this one is open
            stage.setResizable(false);
            stage.setTitle("Create Coordinator");
            stage.setScene(new Scene(createEventParent));

            CreateCoordinatorWindowController createCoordinatorWindowController = fxmlLoader.getController();
            createCoordinatorWindowController.setModel(new CoordinatorModel());
            createCoordinatorWindowController.setScrollPane(scrollPane);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshEventCards() {
        try {
            gridPane.getChildren().clear();
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateGridPane() throws IOException {
        EventModel eventModel = new EventModel();
        List<Event> events;
        try {
            events = eventModel.getEvents();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch events from the database.", e);
        }
        int numRows = 4;
        int numColumns = 2;
        int eventsPerPage = numRows * numColumns;
        totalPages = (int) Math.ceil((double) events.size() / eventsPerPage);

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                int eventIndex = currentPage * eventsPerPage + row * numColumns + col;
                if (eventIndex >= events.size()) {
                    break;
                }
                Pane pane = new Pane();

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/event_card.fxml"));

                // Pass both Event and EventModel instances to the constructor
                fxmlLoader.setControllerFactory(clazz -> new EventCardController(events.get(eventIndex), scrollPane, new CustomerModel(), eventModel, this));
                Pane contentPane = fxmlLoader.load();
                pane.getChildren().add(contentPane);
                gridPane.add(pane, col, row);
            }
        }
    }

    @FXML
    private void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            refreshEventCards();
        }
    }

    @FXML
    private void nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            refreshEventCards();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentPage = 0;
        try {
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}