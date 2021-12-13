package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Report {
    /**
     * Модуль описывающий модель данных класса Отчёты(Reports)
     * Данный модуль предназначен для реализации методов получения и хранения информации об объекте
     * Здесь прописаны методы получения необходимых атрибутов данного объекта и его конструктор
     */
    private final LongProperty id;
    private final ObjectProperty<LocalDate> creation_date;
    private final ObjectProperty<Employee> employee;
    private final ObjectProperty<DangerLevels>  danger_level;
    private final ObjectProperty<Errors> error;


    public Report(){
        this.id = null;
        this.creation_date = new SimpleObjectProperty<LocalDate>();
        this.employee = new SimpleObjectProperty<Employee>();
        this.danger_level = new SimpleObjectProperty<DangerLevels>();
        this.error = new SimpleObjectProperty<Errors>();
    }

    public Report(Long id, LocalDate creation_date, Employee employee, DangerLevels danger_level, Errors error){
        this.id = new SimpleLongProperty(id);
        this.creation_date = new SimpleObjectProperty<>(creation_date);
        this.employee = new SimpleObjectProperty<>(employee);
        this.danger_level = new SimpleObjectProperty<>(danger_level);
        this.error = new SimpleObjectProperty<>(error);
    }

    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (id == null){
            map.put("id", null);
        } else{
            map.put("id", id.get());
        }
        map.put("creation_date", String.valueOf(creation_date.get()));
        map.put("employee", new Gson().fromJson(employee.get().toJson(), JsonObject.class));
        map.put("dangerLevel", new Gson().fromJson(danger_level.get().toJson(), JsonObject.class));
        map.put("errors", new Gson().fromJson(error.get().toJson(), JsonObject.class));
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public LocalDate getCreation_date() {
        return creation_date.get();
    }

    public ObjectProperty<LocalDate> creation_dateProperty() {
        return creation_date;
    }

    public void setCreation_date(LocalDate creation_date) {
        this.creation_date.set(creation_date);
    }

    public Employee getEmployee() {
        return employee.get();
    }

    public ObjectProperty<Employee> employeeProperty() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee.set(employee);
    }


    public DangerLevels getDanger_level() {
        return danger_level.get();
    }

    public ObjectProperty<DangerLevels> danger_levelProperty() {
        return danger_level;
    }

    public void setDanger_level(DangerLevels danger_level) {
        this.danger_level.set(danger_level);
    }

    public Errors getError() {
        return error.get();
    }

    public ObjectProperty<Errors> errorProperty() {
        return error;
    }

    public void setError(Errors error) {
        this.error.set(error);
    }
}
