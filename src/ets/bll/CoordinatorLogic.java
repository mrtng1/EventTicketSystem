package ets.bll;

import ets.be.Coordinator;
import ets.be.Event;
import ets.dal.CoordinatorDAO;
import ets.dal.EventDAO;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CoordinatorLogic {

    CoordinatorDAO coordinatorDAO = new CoordinatorDAO();

    public Coordinator createEvent(String username, String password) throws SQLException {
        return coordinatorDAO.createCoordinator(username, password);
    }


    public List<Coordinator> getAllCoordinators() throws SQLException{
        return coordinatorDAO.getAllCoordinators();
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException{
        coordinatorDAO.deleteCoordinator(coordinator);
    }
}
