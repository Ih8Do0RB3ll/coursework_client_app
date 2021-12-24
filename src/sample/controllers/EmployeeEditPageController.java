package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import sample.models.Departments;
import sample.models.Employee;
import sample.utils.DepartmentsRequests;


import java.io.IOException;
import java.util.Map;
import java.util.stream.Stream;

public class EmployeeEditPageController {
    @FXML
    private TextField usernameField;

    @FXML
    private ChoiceBox<String> depBox;

    @FXML
    private PasswordField passwordField;

    private Stage dialogStage;
    private Employee currentEmployee;
    private boolean okClicked = false;

    public void initialize(Stage dialogStage, Employee currentEmployee) throws IOException {
        this.currentEmployee=currentEmployee;
        this.dialogStage=dialogStage;
        setEmployee(currentEmployee);
    }

    private static ObservableMap<Long, String> getDepValues() throws IOException {
        ObservableList<Departments> list = DepartmentsRequests.getAll();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getId(), list.get(i).getDepartment_name());
        }
        return values;
    }

    public void setEmployee(Employee employee) throws IOException {
        ObservableList<String> depValues = FXCollections.observableArrayList(getDepValues().values());
        if(employee.idProperty()!=null){
            usernameField.setText(employee.getUsername());
            passwordField.setText(employee.getPassword());
        }
        depBox.setItems(depValues);
        if (employee.departmentProperty().get() != null) {
            depBox.setValue(employee.getDepartment().getDepartment_name());
        }
    }

    public boolean isOkClicked(){return okClicked;}

    public Employee getCurrentEmployee(){return currentEmployee;}

    @FXML
    public void handleCancel(){dialogStage.close();}

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            currentEmployee.setUsername(usernameField.getText());
            currentEmployee.setDepartment(
                    DepartmentsRequests.getById(keys(getDepValues(), depBox.getValue()).findAny().get())
            );
            currentEmployee.setPassword(BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
            dialogStage.close();
            okClicked=true;
        }
    }

    private static <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    public boolean isInputValid(){
        String errorMessage = "";
        if(usernameField.getText() == null){
            errorMessage += "Поле 'имя пользователя' не может быть пустым\n";
        }
        if(depBox.getValue() == null){
            errorMessage += "Вы не указали департамент пользователя\n";
        }
        if(passwordField.getText().isEmpty()){
            errorMessage += "Поле 'Пароль' не может быть пустым";
        }
        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Неверный ввод!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
    }
    }
}
