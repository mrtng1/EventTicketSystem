package ets.bll.logic;

// imports
import ets.be.Coordinator;
import ets.dal.dao.CoordinatorDAO;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CoordinatorLogic {

    CoordinatorDAO coordinatorDAO = new CoordinatorDAO();

    public Coordinator createCoordinator(Coordinator coordinator) throws SQLException {
        return coordinatorDAO.createCoordinator(coordinator);
    }

    public List<Coordinator> getAllCoordinators() throws SQLException{
        return coordinatorDAO.getAllCoordinators();
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException{
        coordinatorDAO.deleteCoordinator(coordinator);
    }

    public Coordinator getCoordinatorByUsername(String username) throws SQLException {
        return coordinatorDAO.getCoordinatorByUsername(username);
    }
}
