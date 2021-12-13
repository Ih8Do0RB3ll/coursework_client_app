package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.Errors;

public class ErrorEditPageController {
    @FXML
    private TextField error_codeField;

    @FXML
    private TextField er_descriptionField;

    private Stage dialogStage;
    private Errors currentError;
    private boolean okClicked = false;

    public void initialize(Stage dialogStage, Errors currentError){
        this.dialogStage=dialogStage;
        this.currentError=currentError;
        setError(currentError);
    }

    public void setError(Errors error){
        if(!(error.error_codeProperty().get()==0)){
            error_codeField.setText(Long.toString(error.getError_code()));
            er_descriptionField.setText(error.getDescription());
        }
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public Errors getCurrentError(){return currentError; }

    @FXML
    private void handleCancel() { dialogStage.close(); }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            currentError.setError_code(Long.parseLong(error_codeField.getText()));
            System.out.println(currentError.getError_code());
            currentError.setDescription(er_descriptionField.getText());
            okClicked=true;
            dialogStage.close();
        }
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if(error_codeField.getText() == null){
            errorMessage += "No input";
        }
        if (er_descriptionField.getText() == null) {
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
