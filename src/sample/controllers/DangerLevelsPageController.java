package sample.controllers;

import javafx.beans.property.SimpleLongProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.models.DangerLevels;
import sample.utils.DangerLevelsRequests;

import java.io.IOException;

public class DangerLevelsPageController {
    /**
     * Модуль контроллера страницы приложения, содержащей данные о степенях угрозы
     * В данном модуле прописано программное заполнение объектов формы необходимыми данными
     * при помощью метода инициализации
     */
    @FXML
    private TableView <DangerLevels> lvl_table;

    @FXML
    private TableColumn<DangerLevels, Long> lvl_id;

    @FXML
    private TableColumn<DangerLevels, String> lvl_description;

    private Stage stage;
    private ObservableList<DangerLevels> levels;

    MainController controller = new MainController();

    public void initialize(Stage stage){
        this.stage=stage;
        this.levels=DangerLevelsRequests.getAll();
        lvl_table.setItems(levels);
        System.out.println(levels);
        System.out.println("Downloading levels");
        lvl_id.setCellValueFactory(cellData -> cellData.getValue().idProperty() == null
                                            ?  new SimpleLongProperty(Long.valueOf(0)).asObject()
                                            : cellData.getValue().idProperty().asObject());
        lvl_description.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    }

    @FXML
    private void handleNewLevel() throws IOException {
        DangerLevels tempLevel = new DangerLevels();
        DangerLevels resLevel = controller.showLevelEditPage(stage, tempLevel);
        if(resLevel != null){
            levels.add(resLevel);
            DangerLevelsRequests.createLevel(resLevel);
            System.out.println("created level");
        }
    }

    @FXML
    private void handleEditLevel() throws IOException {
        DangerLevels tempLevel = lvl_table.getSelectionModel().getSelectedItem();
        DangerLevels resLevel = controller.showLevelEditPage(stage, tempLevel);
        if(resLevel != null) {
            DangerLevelsRequests.updateLevel(resLevel);
        }
    }

    @FXML
    public void handleDeleteLevel(){
        int selected = lvl_table.getSelectionModel().getSelectedIndex();
        if(selected>=0){
            Boolean res = DangerLevelsRequests.deleteLevel(lvl_table.getSelectionModel().getSelectedItem());
            if(res){
                lvl_table.getItems().remove(selected);
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(stage);
                alert.setTitle("Error");
                alert.setHeaderText("Could not delete this object");
                alert.setContentText("Try again");
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
