package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.models.Employee;
import sample.models.Errors;
import sample.models.Report;
import sample.utils.EmployeeRequests;
import sample.utils.ErrorsRequests;
import sample.utils.ReportRequests;

import java.io.IOException;

public class ErrorsPageController {
    /**
     * Модуль контроллера страницы приложения, содержащей данные об угрозах
     * В данном модуле прописано программное заполнение объектов формы необходимыми данными
     * при помощью метода инициализации
     */
    @FXML
    private TableView<Errors> err_table;

    @FXML
    private TableColumn<Errors, Long> error_code;

    @FXML
    private TableColumn<Errors, String> error_name;

    private Stage stage;
    private ObservableList<Errors> errors;
    MainController controller = new MainController();

    public void initialize(Stage stage, ObservableList<Errors> errors){
        this.stage=stage;
        this.errors=errors;
        err_table.setItems(errors);
        error_code.setCellValueFactory(cellData -> cellData.getValue().error_codeProperty().asObject());
        error_name.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    @FXML
    private void handleNewError() throws IOException {
        Errors tempError = new Errors();
        Errors resError = controller.showErrorEditPage(stage, tempError);
        if(resError != null){
            errors.add(resError);
            ErrorsRequests.createError(resError);

        }
    }

    @FXML
    private void handleEditError() throws IOException {
        Errors tempError = err_table.getSelectionModel().getSelectedItem();
        Errors resError = controller.showErrorEditPage(stage, tempError);
        if(resError != null) {
            ErrorsRequests.updateError(resError);
        }
    }

    @FXML
    public void handleDeleteError(){
        int selected = err_table.getSelectionModel().getSelectedIndex();
        if(selected>=0){
            Boolean res = ErrorsRequests.deleteError(err_table.getSelectionModel().getSelectedItem());
            if(res){
                err_table.getItems().remove(selected);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Невозможно удалить данную угрозу");
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
    public void handleStatistics() throws IOException {
        ObservableList<Report> errors = ReportRequests.getAll();
        controller.showStatisticsPage(stage, errors);

    }


}
