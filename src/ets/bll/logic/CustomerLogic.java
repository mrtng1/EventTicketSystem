package ets.bll.logic;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.dal.dao.CustomerDAO;

// java imports
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CustomerLogic {

    CustomerDAO customerDAO = new CustomerDAO();

    public Customer createCustomer(Customer customer) throws SQLException {
        return customerDAO.createCustomer(customer);
    }

    public List<Customer> getAllCustomers(Event event) throws SQLException{
        return customerDAO.getAllCustomers(event);
    }

    public void deleteCustomer(Customer customer) throws SQLException{
        customerDAO.deleteCustomer(customer);
    }
}
