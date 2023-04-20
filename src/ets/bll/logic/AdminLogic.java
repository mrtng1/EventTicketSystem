package ets.bll.logic;

// imports
import ets.be.Admin;
import ets.dal.dao.AdminDAO;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class AdminLogic {

    AdminDAO adminDAO = new AdminDAO();

    public List<Admin> getAllAdmins() throws SQLException{
        return adminDAO.getAllAdmins();
    }

}
