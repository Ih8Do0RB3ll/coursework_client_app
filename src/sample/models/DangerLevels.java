package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class DangerLevels {
    /**
     * Модуль описывающий модель данных класса Степени угрозы(DangerLevels)
     * Данный модуль предназначен для реализации методов получения и хранения информации об объекте
     * Здесь прописаны методы получения необходимых атрибутов данного объекта и его конструктор
     */
    private final LongProperty id;
    private final StringProperty description;

    public DangerLevels(){this(null);}

    public DangerLevels(String description){
        this.id = null;
        this.description = new SimpleStringProperty(description);
    }

    public DangerLevels(Long id, String description){
        this.id = new SimpleLongProperty(id);
        this.description = new SimpleStringProperty(description);
    }

    public String toJson() {
        Map<String, Object> map = new HashMap<>();
        if (id == null){
            map.put("id", null);
        } else{
            map.put("id", id.get());
        }
        map.put("description", description.get());
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }
}
