package ets.gui.model;

import ets.be.Coordinator;
import ets.be.Event;
import ets.bll.CoordinatorLogic;
import ets.bll.EventLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.LocalDate;

public class CoordinatorModel {

    CoordinatorLogic coordinatorLogic = new CoordinatorLogic();

    private ObservableList<Coordinator> coordinators = FXCollections.observableArrayList();

    public Coordinator createCoordinator(String username, String password) throws SQLException {
        Coordinator coordinator = coordinatorLogic.createEvent(username, password);
        coordinators.add(coordinator);
        return coordinator;
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException {
        coordinatorLogic.deleteCoordinator(coordinator);
    }
}
