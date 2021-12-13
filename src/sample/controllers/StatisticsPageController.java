package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import sample.models.Report;

import java.util.Map;

public class StatisticsPageController {
    /**
     *
     */

    @FXML
    BarChart<String, Number> stat;

    private Stage stage;
    private ObservableList<Report> errors;

    public void initialize(Stage stage,ObservableList<Report> errors){
        this.stage=stage;
        this.errors=errors;
        getStat();
    }

    public void getStat(){
        ObservableMap<Long, Integer> res = FXCollections.observableHashMap();
        System.out.println(errors);
        for(int i = 0; i<errors.size(); i++){
            Long f = errors.get(i).errorProperty().get().getError_code();
            if(res.containsKey(f)){
                res.put(f, res.get(f)+1);
                System.out.println(res);
            }else res.put(f, 1);
        }
        XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
        for(Map.Entry<Long, Integer> entry : res.entrySet()){
            series.getData().add(new XYChart.Data<String, Number>(entry.getKey().toString(), entry.getValue()));
        }
        stat.getData().add(series);
    }
}
