package ets.gui.controller;

// imports
import ets.be.Coordinator;
import ets.be.Event;
import ets.gui.model.CoordinatorModel;
import ets.gui.model.EventModel;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import org.controlsfx.control.CheckComboBox;

// java imports
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

/**
 *
 * @author tomdra01, mrtng1
 */
public class CreateEventWindowController implements Initializable {

    @FXML
    private Spinner eventTimeField;
    @FXML
    private CheckComboBox eventCoordinatorsField;
    @FXML
    private TextField eventNameField, eventLocationField;
    @FXML
    private DatePicker eventDateField;
    private Runnable refreshCallback;
    private EventModel eventModel;
    private CoordinatorModel coordinatorModel;
    private byte[] imageData;

    public void setCoordinatorModel(CoordinatorModel coordinatorModel) {
        this.coordinatorModel = coordinatorModel;

        eventCoordinatorsField.setTitle("Coordinators");
        eventCoordinatorsField.getItems().addAll(coordinatorModel.getCoordinators());

        coordinatorModel.getCoordinators().addListener((ListChangeListener<? super Coordinator>) obs->{
            eventCoordinatorsField.getItems().clear();
            eventCoordinatorsField.getItems().addAll(coordinatorModel.getCoordinators());
        });
    }

    public void setEventModel(EventModel eventModel) {
        this.eventModel = eventModel;
    }

    public void setRefreshCallback(Runnable refreshCallback) {this.refreshCallback = refreshCallback;}

    @FXML
    private void createBtn(ActionEvent actionEvent) {
        String name = eventNameField.getText();
        String location = eventLocationField.getText();
        LocalDate date = eventDateField.getValue();
        float time = 0;
        String note = "";

        if (name.isEmpty() || location.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Input Error");
            alert.setContentText("Please fill in all the fields.");
            alert.showAndWait();
        } else {
            try {
                Event event = new Event(name, location, date, time, note, imageData);
                eventModel.createEvent(event);

                // assign coordinators to the event
                List<Coordinator> selectedItems = eventCoordinatorsField.getCheckModel().getCheckedItems();
                for (Coordinator item : selectedItems) { //Loop
                    eventModel.assignEventCoordinator(event, new Coordinator(item.getId(), item.getUsername(), item.getPassword()));
                }

                // Call the refreshCallback
                if (refreshCallback != null) {
                    refreshCallback.run();
                }
            } catch (SQLException e) {
                // Handle the exception (e.g., show an error message)
                e.printStackTrace();
            }

            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    private void cancelBtn(ActionEvent actionEvent){
        System.out.println("canceling...");
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void getEventImage(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Images File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images Files", "*.png", "*.jpg", "*.gif"));
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imageData = readBytesFromFile(selectedFile);
        }
    }

    private static byte[] readBytesFromFile(File file) throws Exception {
        InputStream is = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        is.close();
        bos.close();
        return bos.toByteArray();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CustomSpinnerValueFactory valueFactory = new CustomSpinnerValueFactory(0, 24, 12.30);
        eventTimeField.setValueFactory(valueFactory);

        DecimalFormat df = new DecimalFormat("0.00");
        StringConverter<Double> converter = new StringConverter<>() {
            @Override
            public String toString(Double value) {
                if (value == null) {
                    return "";
                }
                return df.format(value);
            }

            @Override
            public Double fromString(String string) {
                try {
                    if (string == null) {
                        return null;
                    }
                    string = string.trim();
                    if (string.length() < 1) {
                        return null;
                    }
                    return df.parse(string).doubleValue();
                } catch (ParseException ex) {
                    return null;
                }
            }
        };

        eventTimeField.getEditor().setTextFormatter(new TextFormatter<>(converter, 12.30));
    }

    public static class CustomSpinnerValueFactory extends SpinnerValueFactory<Double> {
        private double min;
        private double max;

        public CustomSpinnerValueFactory(double min, double max, double initialValue) {
            this.min = min;
            this.max = max;
            setValue(initialValue);

            setConverter(new StringConverter<>() {
                @Override
                public String toString(Double value) {
                    return String.format("%.2f", value);
                }

                @Override
                public Double fromString(String string) {
                    return Double.parseDouble(string);
                }
            });
        }

        @Override
        public void increment(int steps) {
            double currentValue = getValue();
            double newValue = currentValue + steps * 0.5;

            double wholePart = Math.floor(newValue);
            double decimalPart = newValue - wholePart;

            if (steps > 0) {
                if (decimalPart > 0 && decimalPart <= 0.5) {
                    newValue = wholePart + 0.3;
                } else {
                    newValue = wholePart + 1;
                }
            } else {
                if (decimalPart > 0 && decimalPart < 0.3) {
                    newValue = wholePart - 0.3;
                } else if (decimalPart >= 0.3 && decimalPart < 0.8) {
                    newValue = wholePart + 0.3;
                } else {
                    newValue = wholePart;
                }
            }

            // Clamp the value between min and max
            if (newValue > max) {
                newValue = max;
            } else if (newValue < min) {
                newValue = min;
            }

            setValue(newValue);
        }


        @Override
        public void decrement(int steps) {
            increment(-steps);
        }
    }

}
