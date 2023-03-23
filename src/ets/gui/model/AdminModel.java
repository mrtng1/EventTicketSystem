package ets.gui.model;

// imports
import ets.be.Admin;
import ets.bll.AdminLogic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

// java imports
import java.sql.SQLException;

/**
 *
 * @author tomdra01, mrtng1
 */
public class AdminModel {

    AdminLogic adminLogic = new AdminLogic();

    private ObservableList<Admin> admins = FXCollections.observableArrayList();

    public AdminModel() {
        try {
            admins.addAll(adminLogic.getAllAdmins());
        } catch (SQLException e) {
            // Handle the exception, e.g. log the error or show an error message
            e.printStackTrace();
        }
    }

    public ObservableList<Admin> getAdmins() {
        return admins;
    }

    public boolean isValidAdmin(String inputUsername, String inputPassword) {
        return admins.stream()
                .anyMatch(admin -> admin.getUsername().equals(inputUsername) && admin.getPassword().equals(inputPassword));
    }
}