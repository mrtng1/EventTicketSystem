package ets.gui.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class CreateEventController {
    @FXML
    private void createBtn(ActionEvent event){
        System.out.println("creating...");
    }

    @FXML
    private void cancelBtn(ActionEvent event){
        System.out.println("canceling...");
    }
}
