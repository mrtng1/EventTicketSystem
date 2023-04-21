package ets.gui.controller;

// imports
import ets.be.Event;
import ets.gui.controller.create.CreateCoordinatorWindowController;
import ets.gui.controller.create.CreateEventWindowController;
import ets.gui.model.CoordinatorModel;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import ets.gui.util.BlurEffectUtil;
import javafx.animation.*;
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

    // instance variables with @FXML
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private GridPane gridPane;

    // instance variables
    private int currentPage, totalPages;

    /**
     * View Events - shows the events with a scroll animation
     */
    public void viewEvents() {
        double startValue = scrollPane.getVvalue();
        double endValue = startValue + 1;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1),
                        new KeyValue(scrollPane.vvalueProperty(), endValue, Interpolator.EASE_BOTH)
                )
        );
        timeline.play();
    }

    /**
     * Create Event method - opens the create_event_window.fxml
     */
    public void createEvent() {
        BlurEffectUtil.applyBlurEffect(scrollPane, 10);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_event_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Create Event");
            stage.setResizable(false);
            Scene scene = new Scene(createEventParent);
            stage.setScene(scene);

            CreateEventWindowController createEventWindowController = fxmlLoader.getController();
            createEventWindowController.setEventModel(new EventModel());
            createEventWindowController.setCoordinatorModel(new CoordinatorModel());
            createEventWindowController.setRefreshCallback(this::refreshEventCards);
            createEventWindowController.setScrollPane(scrollPane);
            createEventWindowController.setOnCloseRequestHandler(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create Coordinator method - opens the create_coordinator_window.fxml
     */
    public void createCoordinator(){
        BlurEffectUtil.applyBlurEffect(scrollPane, 10);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/create_coordinator_window.fxml"));
            Parent createEventParent = fxmlLoader.load();

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.setTitle("Create Coordinator");
            stage.setScene(new Scene(createEventParent));

            CreateCoordinatorWindowController createCoordinatorWindowController = fxmlLoader.getController();
            createCoordinatorWindowController.setModel(new CoordinatorModel());
            createCoordinatorWindowController.setScrollPane(scrollPane);
            createCoordinatorWindowController.setOnCloseRequestHandler(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Refresh Event Cards method - refresh events
     */
    public void refreshEventCards() {
        try {
            gridPane.getChildren().clear();
            populateGridPane();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Populate Grid Pane method - spawn events into the gird pane
     */
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

                fxmlLoader.setControllerFactory(clazz -> {
                    EventCardController controller = new EventCardController(events.get(eventIndex), scrollPane, new CustomerModel(), eventModel, this);
                    controller.setOnDeleteEventCallback(deletedEvent -> refreshEventCards());
                    return controller;
                });
                Pane contentPane = fxmlLoader.load();
                pane.getChildren().add(contentPane);
                gridPane.add(pane, col, row);
            }
        }
    }

    /**
     * Previous Page method - go to the previous page
     */
    public void previousPage() {
        if (currentPage > 0) {
            currentPage--;
            refreshEventCards();
        }
    }

    /**
     * Next Page method - go to the next page
     */
    public void nextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            refreshEventCards();
        }
    }

    /**
     * Initialize method
     */
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