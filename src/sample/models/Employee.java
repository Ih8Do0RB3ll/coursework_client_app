package sample.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.beans.property.*;

import java.util.HashMap;
import java.util.Map;


public class Employee {
    private final LongProperty id;
    private final StringProperty username;
    private final StringProperty password;
    private final ObjectProperty<Departments> department;
    private final ObjectProperty<OldPasswords> old_password;

    public Employee(){
        this(null, null);
    }

    public Employee(String username, String password){
        /*
         * Модуль описывающий модель данных класса Сотрудник(Employee)
         * Данный модуль предназначен для реализации методов получения и хранения информации об объекте
         * Здесь прописаны методы получения необходимых атрибутов данного объекта и его конструктор
         */
        this.id = null;
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.department = new SimpleObjectProperty<Departments>();
        this.old_password = new SimpleObjectProperty<OldPasswords>();
    }

    public Employee(Long id, String username, String password, Departments department, OldPasswords old_password){
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.department = new SimpleObjectProperty<>(department);
        this.old_password = new SimpleObjectProperty<>(old_password);
        this.id = new SimpleLongProperty(id);
    }

    public String toJson() {

        Map<String, Object> map = new HashMap<>();
        if (id == null){
            map.put("id", null);
        } else{
            map.put("id", id.get());
        }
        map.put("username", username.get());
        map.put("password", password.get());
        map.put("department", new Gson().fromJson(department.get().toJson(), JsonObject.class));
        Gson gson = new Gson();
        return gson.toJson(map);
    }

    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Departments getDepartment() {
        return department.get();
    }

    public ObjectProperty<Departments> departmentProperty() {
        return department;
    }

    public void setDepartment(Departments department) {
        this.department.set(department);
    }

    public OldPasswords getOld_password() {
        return old_password.get();
    }

    public ObjectProperty<OldPasswords> old_passwordProperty() {
        return old_password;
    }

    public void setOld_password(OldPasswords old_password) {
        this.old_password.set(old_password);
    }
}
