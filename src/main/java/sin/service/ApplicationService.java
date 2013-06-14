package sin.service;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lombok.Data;
import sin.model.Configuration;

import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: sin
 * Date: 6/13/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Data
public class ApplicationService {
    private Configuration configuration;
    private SerialPort serialPort;

    public void startReadPort(final DataReceivedListener dataReceivedListener) throws SerialPortException {
        try {
            serialPort = new SerialPort(configuration.getPort());
            serialPort.openPort();
            serialPort.setParams(configuration.getBaudRate(), configuration.getDataBits(), configuration.getStopBits(), configuration.getParity());

            serialPort.addEventListener(new SerialPortEventListener() {
                @Override
                public void serialEvent(SerialPortEvent serialPortEvent) {
                    try {
                        if (serialPortEvent.isRXCHAR()) {
                            dataReceivedListener.onDataReceived(serialPort.readString());
                        }
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SerialPortException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void stopReadPort() throws SerialPortException, InterruptedException {
        if (serialPort != null && serialPort.isOpened()) {
            serialPort.closePort();
        }
    }

    public void sendData(String data, boolean newLine) throws SerialPortException {
        if (newLine) data += "\r\n";
        if (serialPort.isOpened()) {
            serialPort.writeString(data);
        }
    }

    public interface DataReceivedListener {
        public void onDataReceived(String data);
    }
}
