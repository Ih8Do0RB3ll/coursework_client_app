package sample.controllers;

import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.models.DangerLevels;
import sample.models.Departments;
import sample.utils.DangerLevelsRequests;
import sample.utils.DepartmentsRequests;

import java.io.IOException;

public class DepartmentsPageController {
    /**
     * Модуль контроллера страницы приложения, содержащей данные о Департаментах
     * В данном модуле прописано программное заполнение объектов формы необходимыми данными
     * при помощью метода инициализации
     */
    @FXML
    private TableView<Departments> dep_table;

    @FXML
    private TableColumn<Departments, Long> dep_id;

    @FXML
    private TableColumn<Departments, String> dep_name;

    private Stage stage;
    private ObservableList<Departments> departments;
    MainController controller = new MainController();

    public void initialize(Stage stage, ObservableList<Departments> departments){
        this.stage=stage;
        this.departments=departments;
        dep_table.setItems(departments);
        dep_id.setCellValueFactory(cellData -> cellData.getValue().idProperty() == null
                                            ? new SimpleLongProperty(Long.valueOf(0)).asObject()
                                            : cellData.getValue().idProperty().asObject());
        dep_name.setCellValueFactory(cellData -> cellData.getValue().department_nameProperty());
    }

    @FXML
    private void handleNewDepartment() throws IOException {
        Departments tempDep = new Departments();
        Departments resDep = controller.showDepartmentEditPage(stage, tempDep);
        if(resDep != null){
            departments.add(resDep);
            DepartmentsRequests.createDep(resDep);
            System.out.println("created level");
        }
    }

    @FXML
    private void handleEditDepartment() throws IOException {
        Departments tempDep = dep_table.getSelectionModel().getSelectedItem();
        Departments resDep = controller.showDepartmentEditPage(stage, tempDep);
        if(resDep != null) {
            DepartmentsRequests.updateDep(resDep);
        }
    }

    @FXML
    public void handleDeleteDepartment(){
        int selected = dep_table.getSelectionModel().getSelectedIndex();
        if(selected>=0){
            Boolean res = DepartmentsRequests.deleteDep(dep_table.getSelectionModel().getSelectedItem());
            if(res){
                dep_table.getItems().remove(selected);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Невозможно удалить данный объект");
                alert.setContentText("Попробуйте снова");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(stage);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing to delete");
            alert.setContentText("Select object to delete");
            alert.showAndWait();
        }
    }
}
