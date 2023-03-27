package ets.bll;

// imports
import ets.be.Coordinator;
import ets.dal.IDataAccess;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CoordinatorLogic {

    IDataAccess dataAccess;

    public Coordinator createEvent(Coordinator coordinator) throws SQLException {
        return dataAccess.createCoordinator(coordinator);
    }

    public List<Coordinator> getAllCoordinators() throws SQLException{
        return dataAccess.getAllCoordinators();
    }

    public void deleteCoordinator(Coordinator coordinator) throws SQLException{
        dataAccess.deleteCoordinator(coordinator);
    }
}
