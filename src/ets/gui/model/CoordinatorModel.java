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

    CoordinatorLogic coordinatorLogic = new CoordinatorLogic();

    private ObservableList<Coordinator> coordinators = FXCollections.observableArrayList();

    public Coordinator createCoordinator(Coordinator coordinator) throws SQLException {
        Coordinator c = coordinatorLogic.createEvent(coordinator);
        coordinators.add(c);
        return c;
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException {
        coordinatorLogic.deleteCoordinator(coordinator);
    }
}