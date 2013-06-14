/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sin.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.util.StringConverter;
import jssc.SerialPort;
import jssc.SerialPortList;
import sin.Main;
import sin.model.Configuration;
import sin.service.ApplicationService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author sin
 */
public class ConfigWindowController implements Initializable {
    @Inject
    private Main mainApp;
    @Inject
    private ApplicationService applicationService;
    @FXML
    private ComboBox<String> port;
    @FXML
    private ComboBox<Integer> baudRate;
    @FXML
    private ComboBox<Integer> dataBits;
    @FXML
    private ComboBox<Integer> stopBits;
    @FXML
    private ComboBox<Pair<Integer, String>> parity;
    @FXML
    private ComboBox<Pair<Integer, String>> flowControl;
    @FXML
    private Button cancel;
    @FXML
    private TextField customPort;

    @FXML
    private void save() {
        Configuration configuration = new Configuration();
        if (customPort.isDisabled())
            configuration.setPort(port.getValue());
        else
            configuration.setPort(customPort.getText());
        configuration.setBaudRate(baudRate.getValue());
        configuration.setDataBits(dataBits.getValue());
        configuration.setStopBits(dataBits.getValue());
        configuration.setParity(parity.getValue().getKey());
        configuration.setFlowControl(flowControl.getValue().getKey());
        applicationService.setConfiguration(configuration);
        mainApp.showMainWindow();
    }

    @FXML
    private void close() {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        port.getItems().add("Custom...");
        port.getItems().addAll(SerialPortList.getPortNames());
        port.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                if (s2.equals("Custom...")) {
                    customPort.disableProperty().setValue(false);
                } else {
                    customPort.disableProperty().setValue(true);
                }
            }
        });
        baudRate.getItems().addAll(
                SerialPort.BAUDRATE_110,
                SerialPort.BAUDRATE_300,
                SerialPort.BAUDRATE_600,
                SerialPort.BAUDRATE_1200,
                SerialPort.BAUDRATE_4800,
                SerialPort.BAUDRATE_9600,
                SerialPort.BAUDRATE_14400,
                SerialPort.BAUDRATE_19200,
                SerialPort.BAUDRATE_38400,
                SerialPort.BAUDRATE_57600,
                SerialPort.BAUDRATE_115200,
                SerialPort.BAUDRATE_128000,
                SerialPort.BAUDRATE_256000);
        dataBits.getItems().addAll(SerialPort.DATABITS_5,
                SerialPort.DATABITS_6,
                SerialPort.DATABITS_7,
                SerialPort.DATABITS_8);
        dataBits.getSelectionModel().select(3);
        stopBits.getItems().addAll(SerialPort.STOPBITS_1,
                SerialPort.STOPBITS_1_5,
                SerialPort.STOPBITS_2);
        stopBits.getSelectionModel().select(0);
        parity.getItems().addAll(
                new Pair<>(SerialPort.PARITY_EVEN, "Even"),
                new Pair<>(SerialPort.PARITY_MARK, "Mark"),
                new Pair<>(SerialPort.PARITY_NONE, "None"),
                new Pair<>(SerialPort.PARITY_ODD, "Odd"),
                new Pair<>(SerialPort.PARITY_SPACE, "Space")
        );
        parity.setConverter(new StringConverterImpl(parity));
        parity.getSelectionModel().select(2);
        flowControl.getItems().addAll(
                new Pair<>(SerialPort.FLOWCONTROL_NONE, "None"),
                new Pair<>(SerialPort.FLOWCONTROL_RTSCTS_IN, "RTSCTS_IN"),
                new Pair<>(SerialPort.FLOWCONTROL_RTSCTS_OUT, "RTSCTS_OUT"),
                new Pair<>(SerialPort.FLOWCONTROL_XONXOFF_IN, "XONXOFF_IN"),
                new Pair<>(SerialPort.FLOWCONTROL_XONXOFF_OUT, "XONXOFF_OUT")
        );
        flowControl.setConverter(new StringConverterImpl(parity));
        flowControl.getSelectionModel().select(0);
    }

    private class StringConverterImpl extends StringConverter<Pair<Integer, String>> {

        private ComboBox<Pair<Integer, String>> comboBox;

        public StringConverterImpl(ComboBox<Pair<Integer, String>> comboBox) {
            this.comboBox = comboBox;
        }

        @Override
        public String toString(Pair<Integer, String> t) {
            return t.getValue();
        }

        @Override
        public Pair<Integer, String> fromString(String string) {
            ObservableList<Pair<Integer, String>> items = comboBox.getItems();
            for (Pair<Integer, String> item : items) {
                if (item.getValue().equals(string))
                    return item;
            }
            return null;
        }
    }
}
