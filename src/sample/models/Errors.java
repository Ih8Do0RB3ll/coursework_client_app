package sample.models;

import com.google.gson.Gson;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Errors {
    /**
     * Модуль описывающий модель данных класса Ошибки(Errors)
     * Данный модуль предназначен для реализации методов получения и хранения информации об объекте
     * Здесь прописаны методы получения необходимых атрибутов данного объекта и его конструктор
     */
    private final LongProperty error_code;
    private final StringProperty description;

    public Errors(){this(null);}

    public Errors(String description){
        this.error_code= new SimpleLongProperty();
        this.description = new SimpleStringProperty(description);}

    public Errors(Long error_code, String description){
        this.error_code = new SimpleLongProperty(error_code);
        this.description = new SimpleStringProperty(description);
    }

    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (error_code == null){
            map.put("error_code", null);
        } else{
            map.put("error_code", error_code.get());
        }
        map.put("description", description.get());
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public long getError_code() {
        return error_code.get();
    }

    public LongProperty error_codeProperty() {
        return error_code;
    }

    public void setError_code(long error_code) {
        this.error_code.set(error_code);
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
