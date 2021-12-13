package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Departments;

public class DepartmentEditPageController {
    @FXML
    private TextField depNameField;

    private Stage dialogStage;
    private Departments currentDepartment;
    private boolean okClicked = false;

    public void inintialize(Stage dialogStage, Departments currentDepartment) {
        this.dialogStage = dialogStage;
        this.currentDepartment = currentDepartment;
        setDepartment(currentDepartment);
    }

    public void setDepartment(Departments department) {
        if (department.idProperty() == null) {
            depNameField.setText(currentDepartment.getDepartment_name());
        } else {
            depNameField.setText(department.getDepartment_name());
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Departments getCurrentDepartment() {
        return currentDepartment;
    }

    @FXML
    private void handleCancel() { dialogStage.close(); }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            currentDepartment.setDepartment_name(depNameField.getText());
            okClicked = true;
            dialogStage.close();
        }

    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (depNameField.getText() == null) {
            errorMessage += "No input";
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Error!");
            alert.setHeaderText("Wrong input!");
            alert.setContentText(errorMessage);

            alert.showAndWait();
            return false;
        }
    }

}