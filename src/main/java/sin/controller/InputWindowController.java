package sin.controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import jssc.SerialPortException;
import sin.service.ApplicationService;

import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: sin
 * Date: 6/13/13
 * Time: 7:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class InputWindowController {
    @Inject
    private ApplicationService applicationService;
    @FXML
    private CheckBox addNewLineCheckbox;
    @FXML
    private CheckBox keepTextCheckbox;
    @FXML
    private TextArea inputTextArea;
    @FXML
    private void sendData(){
        try {
            applicationService.sendData(inputTextArea.getText(),addNewLineCheckbox.isSelected());
        } catch (SerialPortException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        if(!keepTextCheckbox.isSelected()){
            clearData();
        }
    }
    @FXML
    private void clearData(){
        inputTextArea.setText("");
    }
}
