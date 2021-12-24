package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.models.Employee;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class WelcomeController{
    /**
     * Модуль контроллера приветствующей страницы
     * В данном модуле прописано программное заполнение объектов формы необходимыми данными
     * при помощью метода инициализации
     */

    @FXML
    private Label usernameLabel;

    @FXML
    private Label departmentLabel;

    @FXML
    private Label dateLabel;

    private Stage stage;
    private Employee emp;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public void initialize(Stage stage, Employee emp){
        this.stage = stage;
        this.emp = emp;
       // System.out.println(emp);
        showWelcome(emp);
    }

    private void showWelcome(Employee emp){
        if (emp == null){
            //System.out.println("show welcome func emp is null");
            usernameLabel.setText("Unknown");
            departmentLabel.setText("Unknown");
        }
        else {
            //System.out.println("show welcome func emp is not null ");
            usernameLabel.setText(emp.getUsername());
            departmentLabel.setText(emp.getDepartment().getDepartment_name());
        }
        dateLabel.setText(dtf.format(LocalDateTime.now()));
    }
}
