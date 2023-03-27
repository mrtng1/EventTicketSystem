package ets.gui.model;

// imports
import ets.be.Coordinator;
import ets.bll.CoordinatorLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// java imports
import java.sql.SQLException;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CoordinatorModel {

    public CoordinatorModel() {
        try {
            coordinators.addAll(coordinatorLogic.getAllCoordinators());
        } catch (SQLException e) {
            // Handle the exception, e.g. log the error or show an error message
            e.printStackTrace();
        }
    }

    CoordinatorLogic coordinatorLogic = new CoordinatorLogic();

    private ObservableList<Coordinator> coordinators = FXCollections.observableArrayList();

    public ObservableList<Coordinator> getCoordinators() {
        return coordinators;
    }

    public Coordinator createCoordinator(Coordinator coordinator) throws SQLException {
        Coordinator c = coordinatorLogic.createEvent(coordinator);
        coordinators.add(c);
        return c;
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException {
        coordinatorLogic.deleteCoordinator(coordinator);
    }

    public boolean isValidCoordinator(String inputUsername, String inputPassword) {
        return coordinators.stream()
                .anyMatch(coordinator -> coordinator.getUsername().equals(inputUsername) && coordinator.getPassword().equals(inputPassword));
    }
}