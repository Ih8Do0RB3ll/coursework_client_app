package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import sample.models.DangerLevels;
import sample.models.Employee;
import sample.models.Errors;
import sample.models.Report;
import sample.utils.DangerLevelsRequests;
import sample.utils.EmployeeRequests;
import sample.utils.ErrorsRequests;
import sample.utils.ReportRequests;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.stream.Stream;

public class ReportEditPageController {
    @FXML
    private TextField usernameField;

    @FXML
    private TextField creationDateField;

    @FXML
    private TextField departmentField;

    @FXML
    private ChoiceBox<String> errorBox;

    @FXML
    private ChoiceBox<String> dangerLevelBox;

    @FXML
    private TextArea descriptionArea;

    private Stage dialogueStage;
    private Report currentReport;
    private Employee currentUser;
    private boolean okClicked = false;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void initialize(Stage stage, Report currentReport, Employee currentUser) throws IOException {
        this.dialogueStage = stage;
        this.currentReport = currentReport;
        this.currentUser = currentUser;
        setReport(currentReport);
    }

    private static ObservableMap<Long, String> getErrorValues() throws IOException {
        ObservableList<Errors> list = ErrorsRequests.getAll();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getError_code(), list.get(i).getError_code() + " - " + list.get(i).getDescription());
        }
        return values;
    }

    private static  ObservableMap<Long, String> getDangerLevelValues() throws IOException {
        ObservableList<DangerLevels> list = DangerLevelsRequests.getAll();
        ObservableMap<Long, String> values = FXCollections.observableHashMap();
        for (int i = 0; i<list.size(); i++){
            values.put(list.get(i).getId(), list.get(i).getDescription());
        }
        return values;
    }

    public void setReport(Report report) throws IOException {
        ObservableList<String> errorsValues = FXCollections.observableArrayList(getErrorValues().values());
        ObservableList<String> dangerLevelsValues = FXCollections.observableArrayList(getDangerLevelValues().values());
        if (report.idProperty() == null) {
            usernameField.setText(currentUser.getUsername());
            creationDateField.setText(dtf.format(LocalDateTime.now()));
            departmentField.setText(currentUser.getDepartment().getDepartment_name());
        } else {
            usernameField.setText(report.getEmployee().getUsername());
            creationDateField.setText(report.getCreation_date().toString());
            departmentField.setText(report.getEmployee().getDepartment().getDepartment_name());
        }
        errorBox.setItems(errorsValues);
        dangerLevelBox.setItems(dangerLevelsValues);
        System.out.println(report.danger_levelProperty());
        if (report.danger_levelProperty().get() != null) {
            dangerLevelBox.setValue(report.getDanger_level().getDescription());
        }
        if (report.errorProperty().get() != null) {
            errorBox.setValue(report.getError().getError_code() + " - " + report.getError().getDescription());
        }
        if (report.reportDescriptionProperty().get() != null) {
            descriptionArea.setText(report.getReportDescription());
        }
    }


    public boolean isOkClicked(){
        return okClicked;
    }

    public Report getClickedReport() {
        return currentReport;
    }

    @FXML
    private void handleCancel(){
        dialogueStage.close();
    }

    @FXML
    private void handleOk() throws IOException {
        if(isInputValid()){
            currentReport.setEmployee(EmployeeRequests.getByUsername(usernameField.getText()));
            currentReport.setCreation_date(LocalDate.parse(creationDateField.getText()));
            currentReport.setError(ErrorsRequests.getById(keys(getErrorValues(), errorBox.getValue()).findAny().get()));
            currentReport.setDanger_level(DangerLevelsRequests.getById(keys(getDangerLevelValues(), dangerLevelBox.getValue()).findAny().get()));
            currentReport.setRep_description(descriptionArea.getText());
            okClicked = true;
            dialogueStage.close();
        }
    }

    private static <K, V> Stream<K> keys(Map<K, V> map, V value) {
        return map
                .entrySet()
                .stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey);
    }

    private boolean isInputValid(){
        String errorMessage = "";

        if (errorBox.getValue() == null){
            errorMessage += "Поле выбора ошибки не может быть пустым\n";
        }

        if (dangerLevelBox.getValue() == null){
            errorMessage += "Поле уровня опасности не может быть пустым";
        }

        if (errorMessage.length()==0){
            return true;
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogueStage);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Ошибка ввода!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }

    }

}

