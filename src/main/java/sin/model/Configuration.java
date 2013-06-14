package sin.model;

import lombok.Data;
import lombok.NonNull;

/**
 * Created with IntelliJ IDEA.
 * User: sin
 * Date: 6/13/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Data
public class Configuration {
    private String port;
    private Integer baudRate;
    private Integer dataBits;
    private Integer stopBits;
    private Integer parity;
    private Integer flowControl;

}
