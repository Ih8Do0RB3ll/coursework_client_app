package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import sample.Main;
import sample.models.Employee;
import sample.utils.EmployeeRequests;

import java.io.IOException;


public class LoginPageController {
    /**
     * Модуль контроллера страницы приложения, содержащей форму логина в приложение
     * В данном модуле прописано программная проверка объектов формы на предмет правильности запонения данных
     */

    @FXML
    private TextField userField;

    @FXML
    private PasswordField passwordField;

    private Main main;
    private Stage stage;
    private Employee emp;

    public void initialize(Main main, Stage stage){
        this.main = main;
        this.stage = stage;
    }

    @FXML
    public void handleLogin() throws IOException{
        if(isInputValid()){
            emp = EmployeeRequests.getByUsername(userField.getText());
            if ((emp != null) && BCrypt.checkpw(passwordField.getText(),emp.getPassword())){
                this.main.setEmp(emp);
                this.main.showMain(stage);
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.initOwner(stage);
                alert.setTitle("Error");
                alert.setHeaderText("Wrong input");
                alert.setContentText("No such username or password is incorrect");
                alert.showAndWait();
            }
        }

        }



    private Boolean isInputValid(){
        String errorMessage = "";
        if(userField.getText() == null || userField.getText().length() == 0){
            errorMessage += "No username input\n";
        }

        if(passwordField.getText() == null || passwordField.getText().length() == 0){
            errorMessage += "No password input\n";
        }

        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong input");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }

}

}
