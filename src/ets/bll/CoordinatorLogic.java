package ets.bll;

// imports
import ets.be.Coordinator;
import ets.dal.CoordinatorDAO;

// java imports
import java.sql.SQLException;
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
