package ets.gui.controller;

// imports
import ets.be.Customer;
import ets.be.Event;
import ets.bll.util.QRCodeGenerator;
import ets.gui.model.CustomerModel;
import ets.gui.model.EventModel;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

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


/**
 *
 * @author tomdra01, mrtng1
 */
public class TicketWindowController implements Initializable {

    @FXML
    private AnchorPane ticketAnchor;
    @FXML
    private Label ticketEvent, ticketLocation, ticketDateAndTime, ticketParticipantName;
    @FXML
    private ImageView imgQRCode;
    EventModel eventModel;
    CustomerModel customerModel;
    Event event;
    Customer customer;

    public void setModel(EventModel eventModel, CustomerModel customerModel) {
        this.eventModel = eventModel;
        this.customerModel = customerModel;
    }

    public void setDetails(Event event, Customer customer) {
        this.event = event;
        this.customer = customer;
        ticketInitialize();
    }

    public void ticketInitialize(){
        ticketEvent.setText(event.getName());
        ticketLocation.setText(event.getLocation());
        ticketDateAndTime.setText(event.getDate() +", " +event.getTime());
        ticketParticipantName.setText(customer.getName());

        try {
            Image qrCode = SwingFXUtils.toFXImage(QRCodeGenerator.generateQRCodeImage(UUID.randomUUID().toString()), null);
            imgQRCode.setImage(qrCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void printTicket() throws IOException {
        // Get the current scene
        Scene scene = ticketAnchor.getScene();

        float aspectRatio = (float) scene.getWidth() / (float) scene.getHeight();

        // Calculate the new height while maintaining the aspect ratio
        float pdfWidth = 595; // A4 width
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
        String event = ticketEvent.getText();
        String name = ticketParticipantName.getText();
        File outputFile = new File("tickets/Ticket" + event + "_" + name + ".pdf");
        document.save(outputFile);

        // Close the document
        document.close();
    }
}

