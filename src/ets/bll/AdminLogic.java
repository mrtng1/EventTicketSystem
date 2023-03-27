package ets.bll;

// imports
import ets.be.Admin;
import ets.dal.IDataAccess;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class AdminLogic {

    IDataAccess dataAccess;

    public List<Admin> getAllAdmins() throws SQLException{
        return dataAccess.getAllAdmins();
    }

}
