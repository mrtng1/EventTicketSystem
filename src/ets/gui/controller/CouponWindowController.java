package ets.gui.controller;

// imports
import ets.bll.util.BarcodeGenerator;
import ets.bll.util.QRCodeGenerator;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

// java imports
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;

public class CouponWindowController implements Initializable {

    @FXML
    private Line couponLine;
    @FXML
    private AnchorPane couponAnchor;
    @FXML
    private Label coupon;
    @FXML
    private ImageView imgBarcode, imgQRCode;
    @FXML
    private Button printButton;
    private String couponName;
    private UUID uuid = UUID.randomUUID();
    
    public void setCoupon(String name) {
        this.couponName = name;
        couponInitialize();
    }

    private void couponInitialize() {
        coupon.setText(couponName);

        try {
            javafx.scene.image.Image qrCode = SwingFXUtils.toFXImage(QRCodeGenerator.generateQRCodeImage(uuid.toString()), null);
            imgQRCode.setImage(qrCode);

            Image barCode = SwingFXUtils.toFXImage(BarcodeGenerator.generateBarcodeImage(uuid.toString()), null);
            imgBarcode.setImage(barCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printCoupon() throws IOException {
        printButton.setVisible(false);

        // Gets the scene
        Scene scene = couponAnchor.getScene();
        float aspectRatio = (float) scene.getWidth() / (float) scene.getHeight();
        float pdfWidth = 595;
        float pdfHeight = pdfWidth / aspectRatio;

        // Create a PDF document
        PDDocument document = new PDDocument();
        PDPage page = new PDPage(new PDRectangle(pdfWidth, pdfHeight));
        document.addPage(page);

        // Convert the scene to an image
        WritableImage fxImage = new WritableImage((int) scene.getWidth(), (int) scene.getHeight());
        scene.snapshot(fxImage);
        BufferedImage image = SwingFXUtils.fromFXImage(fxImage, null);

        // Convert the BufferedImage to a byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        // Add the image to the PDF document
        PDImageXObject xImage = PDImageXObject.createFromByteArray(document, imageBytes, "ticket");
        PDPageContentStream contentStream = new PDPageContentStream(document, page);
        contentStream.drawImage(xImage, 0, 0, pdfWidth, pdfHeight);
        contentStream.close();

        // Save the image to a file at the specified path
        File outputFile = new File("tickets/Coupon" +"_" +couponName +".pdf");
        document.save(outputFile);
        document.close();

        // Opens the PDF document
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(outputFile);
            }
        }

        // Stage close
        Stage stage = (Stage) couponAnchor.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        couponLine.setStrokeWidth(2);
        couponLine.setStrokeType(StrokeType.CENTERED);
        couponLine.setStrokeLineCap(StrokeLineCap.ROUND);
        couponLine.setStrokeLineJoin(StrokeLineJoin.ROUND);
        couponLine.getStrokeDashArray().addAll(15d, 10d);
    }
}
