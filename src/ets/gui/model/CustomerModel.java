package ets.gui.model;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.bll.logic.CustomerLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// java imports
import java.sql.SQLException;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CustomerModel {

    CustomerLogic customerLogic = new CustomerLogic();
    private ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomers() {
        return customers;
    }

    public void fetchAllCustomers(Event event) throws SQLException {
        customers.clear();
        customers.addAll(customerLogic.getAllCustomers(event));
    }

    public Customer createCustomer(Customer customer) throws SQLException {
        Customer c = customerLogic.createCustomer(customer);
        customers.add(c);
        return c;
    }

    public void deleteCustomers(Customer customer) throws SQLException {
        customerLogic.deleteCustomer(customer);
        customers.remove(customer);
    }
}
