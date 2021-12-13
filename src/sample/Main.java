package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.controllers.LoginPageController;
import sample.controllers.MainController;
import sample.models.Employee;

import java.io.IOException;

public class Main extends Application {

    private Stage stage;
    public Employee emp;
    private BorderPane mainLayout;

    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    public void getEmp(Employee emp){

    }

    @Override
    public void start(Stage stage){
        try{
            FXMLLoader load = new FXMLLoader();
            load.setLocation(Main.class.getResource("views/login.fxml"));
            StackPane login = (StackPane) load.load();
            Scene scene = new Scene(login, 220, 100);
            stage.setScene(scene);
            LoginPageController loginPageController = load.getController();
            loginPageController.initialize(this, stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showMain(Stage stage) throws IOException {
        FXMLLoader mainload = new FXMLLoader();
        mainload.setLocation(Main.class.getResource("views/main.fxml"));
        mainLayout = (BorderPane) mainload.load();
        Scene mainscene = new Scene(mainLayout, 600, 400);
        stage.setScene(mainscene);
        MainController mainController = mainload.getController();
        mainController.initialize(this, stage, emp);
        stage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
