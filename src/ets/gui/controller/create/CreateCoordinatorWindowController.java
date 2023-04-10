package ets.gui.controller.create;

// imports
import ets.be.Coordinator;
import ets.gui.model.CoordinatorModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

// java imports
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateCoordinatorWindowController implements Initializable {

    @FXML
    private TextField nameField, passwordField;
    private ScrollPane scrollPane;
    private CoordinatorModel model;

    public void setModel(CoordinatorModel model){
        this.model = model;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }


    @FXML
    public void createBtn(ActionEvent actionEvent) {
        String username = nameField.getText();
        String password = passwordField.getText();

        try {
            Coordinator coordinator = new Coordinator(username, password);
            model.createCoordinator(coordinator);
            scrollPane.setEffect(null);
        } catch (SQLException e) {
            // Handle the exception (e.g., show an error message)
            e.printStackTrace();
        }
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void cancelBtn(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        scrollPane.setEffect(null);
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}