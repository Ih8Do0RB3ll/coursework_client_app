package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.models.DangerLevels;
import sample.models.Employee;
import sample.models.Report;
import sample.utils.DangerLevelsRequests;
import sample.utils.EmployeeRequests;
import sample.utils.ReportRequests;

import java.io.IOException;


public class EmployeePageController {
    /**
     * Модуль контроллера страницы приложения, содержащей данные о сотрудниках
     * В данном модуле прописано программное заполнение объектов формы необходимыми данными
     * при помощью метода инициализации
     */
    @FXML
    private TableView<Employee> emp_table;

    @FXML
    private TableColumn<Employee, String> emp_username;

    @FXML
    private TableColumn<Employee, String> emp_department;

    private Stage stage;
    private ObservableList<Employee> users;

    MainController controller = new MainController();

    public void initialize(Stage stage, ObservableList<Employee> users){
        this.stage=stage;
        this.users=users;
        emp_table.setItems(users);
        emp_username.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
        emp_department.setCellValueFactory(cellData -> cellData.getValue().departmentProperty().get().department_nameProperty());

    }

    @FXML
    private void handleNewEmployee() throws IOException {
        Employee tempEmployee = new Employee();
        Employee resEmployee = controller.showEmployeeEditPage(stage, tempEmployee);
        if(resEmployee != null){
            users.add(resEmployee);
            EmployeeRequests.createEmployee(resEmployee);

        }
    }

    @FXML
    private void handleEditEmployee() throws IOException {
        Employee tempEmployee = emp_table.getSelectionModel().getSelectedItem();
        Employee resEmployee = controller.showEmployeeEditPage(stage, tempEmployee);
        if(resEmployee != null) {
            EmployeeRequests.updateEmployee(resEmployee);
        }
    }

    @FXML
    public void handleDeleteEmployee(){
        int selected = emp_table.getSelectionModel().getSelectedIndex();
        if(selected>=0){
            Boolean res = EmployeeRequests.deleteEmployee(emp_table.getSelectionModel().getSelectedItem());
            if(res){
                emp_table.getItems().remove(selected);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Нельзя удалить данного сотрудника");
                alert.setContentText("Попробуйте снова");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Нечего удалять");
            alert.setContentText("Выберите объект для удаления");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleGetReports() throws IOException {
        int selected = emp_table.getSelectionModel().getSelectedIndex();
        if (selected>=0){
            ObservableList<Report> reports = ReportRequests.getReportsByUser(emp_table.getSelectionModel().getSelectedItem());
            controller.showReportsByUser(stage, reports);
        }
    }

}
