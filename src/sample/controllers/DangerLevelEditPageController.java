package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.models.DangerLevels;


public class DangerLevelEditPageController {

    @FXML
    private TextField levelField;

    private Stage dialogStage;
    private DangerLevels currentLevel;
    private boolean okClicked = false;

    public void initialize(Stage dialogStage, DangerLevels currentLevel){
        this.dialogStage=dialogStage;
        this.currentLevel=currentLevel;
        setLevel(currentLevel);
    }

    public void setLevel(DangerLevels level){
        if(level.idProperty()==null){
            levelField.setText(currentLevel.getDescription());
        }else {
            levelField.setText(level.getDescription());
        }
    }

    public boolean isOkClicked(){return okClicked;}

    public DangerLevels getClickedLevel(){return currentLevel;}

    @FXML
    private void handleCancel(){
        dialogStage.close();
    }

    @FXML
    private void handleOk(){
        if(isInputValid()){
            currentLevel.setDescription(levelField.getText());
            okClicked = true;
            dialogStage.close();
        }

    }

    private boolean isInputValid(){
        String errorMessage = "";
        if(levelField.getText() == null){
            errorMessage += "Поле ввода 'Уровень опасности' не может быть пустым ";
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
