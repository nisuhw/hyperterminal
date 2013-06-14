/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sin.controller;

import com.cathive.fx.guice.GuiceFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import jssc.SerialPortException;
import sin.Main;
import sin.service.ApplicationService;

import javax.inject.Inject;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author sin
 */
public class MainWindowController implements Initializable {
    @Inject
    private Main mainApp;
    @Inject
    private ApplicationService applicationService;
    @FXML
    private TextArea output;
    @FXML
    private ToggleButton startButton;

    @FXML
    private void showInputWindow(){
        mainApp.showInputWindow();
    }

    @FXML
    private void readData() {
        try {
            if (startButton.isSelected()) {
                applicationService.startReadPort(new ApplicationService.DataReceivedListener() {
                    @Override
                    public void onDataReceived(String data) {
                        output.appendText(data);
                    }
                });
            } else {
                applicationService.stopReadPort();
            }
        } catch (SerialPortException | InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
