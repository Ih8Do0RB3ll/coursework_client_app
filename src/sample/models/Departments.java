package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.HashMap;
import java.util.Map;

public class Departments {
    /**
     * Модуль описывающий модель данных класса Департаменты(Departments)
     * Данный модуль предназначен для реализации методов получения и хранения информации об объекте
     * Здесь прописаны методы получения необходимых атрибутов данного объекта и его конструктор
     */
    private  final LongProperty id;
    private final StringProperty department_name;

    public Departments(){ this(null); }

    public Departments(String department_name){
        this.id = null;
        this.department_name = new SimpleStringProperty(department_name);
    }

    public Departments(Long id, String department_name){
        this.id = new SimpleLongProperty(id);
        this.department_name = new SimpleStringProperty(department_name);
    }

    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (id == null){
            map.put("id", null);
        } else{
            map.put("id", id.get());
        }
        map.put("department_name", department_name.get());
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getDepartment_name() {
        return department_name.get();
    }

    public StringProperty department_nameProperty() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name.set(department_name);
    }
}
