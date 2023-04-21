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
import javafx.stage.Modality;
import javafx.stage.Stage;

// java imports
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateCouponWindowController implements Initializable {

    // instance variables with @FXML
    @FXML
    private AnchorPane createCouponAnchorPane;
    @FXML
    private TextField couponNameField;

    /**
     * Print Coupon method - to create coupon
     */
    public void printCoupon() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ets/gui/view/coupon_preview.fxml"));
            Parent createCouponParent = fxmlLoader.load();

            CouponWindowController couponWindowController = fxmlLoader.getController();

            String name = couponNameField.getText();
            couponWindowController.setCouponLabel(name);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            Scene scene = new Scene(createCouponParent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) createCouponAnchorPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Cancel method - close the current window
     */
    public void cancel() {
        Stage stage = (Stage) createCouponAnchorPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Initialize method
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
