/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sin;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sin.service.ApplicationService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author sin
 */
public class Main extends GuiceApplication {

    @Inject
    private GuiceFXMLLoader fxmlLoader;
    private Stage stage;
    private GuiceFXMLLoader.Result configWindow;
    private GuiceFXMLLoader.Result mainWindow;
    private GuiceFXMLLoader.Result inputWindow;

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        configWindow = fxmlLoader.load(Main.class.getClass().getResource("/sin/view/ConfigWindow.fxml"));
        mainWindow = fxmlLoader.load(Main.class.getClass().getResource("/sin/view/MainWindow.fxml"));
        inputWindow = fxmlLoader.load(Main.class.getClass().getResource("/sin/view/InputWindow.fxml"));
        showConfigWindow();
    }

    public void showConfigWindow(){
        stage.setScene(new Scene(configWindow.<Parent>getRoot()));
        stage.show();
        stage.toFront();
    }

    public void showMainWindow(){
        stage.setScene(new Scene(mainWindow.<Parent>getRoot()));
        stage.show();
    }

    public void showInputWindow(){
        Stage inputWindowStage=new Stage();
        inputWindowStage.setTitle("Input Window");
        inputWindowStage.setScene(new Scene(inputWindow.<Parent>getRoot()));
        inputWindowStage.show();
    }

    @Override
    public void init(List<Module> modules) throws Exception {
    }
}