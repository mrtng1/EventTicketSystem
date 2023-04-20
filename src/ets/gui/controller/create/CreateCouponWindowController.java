package ets.gui.controller.create;

// imports
import ets.gui.controller.CouponWindowController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

// java imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateCouponWindowController implements Initializable {

    @FXML
    private AnchorPane createCouponAnchorPane;
    @FXML
    private TextField couponNameField;
    private String name;

    public void printCoupon() {
        try {
            // Load the FXML file
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/coupon_preview.fxml"));
            Parent createCouponParent = fxmlLoader.load();

            CouponWindowController couponWindowController = fxmlLoader.getController();
            name = couponNameField.getText();
            couponWindowController.setCoupon(name);

            // Create a new stage and scene
            Stage stage = new Stage();
            stage.setResizable(false);
            Scene scene = new Scene(createCouponParent);
            stage.setScene(scene);

            // Show the new stage
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) createCouponAnchorPane.getScene().getWindow();
        stage.close();
    }

    public void cancel() {
        Stage stage = (Stage) createCouponAnchorPane.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
