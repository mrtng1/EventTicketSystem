package ets.gui.controller;

// imports
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateEventController {
    @FXML
    private void createBtn(ActionEvent event){
        System.out.println("creating...");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelBtn(ActionEvent event){
        System.out.println("canceling...");
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
