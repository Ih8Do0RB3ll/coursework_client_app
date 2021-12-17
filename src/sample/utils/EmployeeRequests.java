package sample.utils;


import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Departments;
import sample.models.Employee;

public class EmployeeRequests {
    /**
     * Модуль обработки и выполнения запросов к сущности Сотрудники(EmployeeRequests)
     * В данном модуле выполняется подключение к ннеобходимому api для выполнения запросов и обработки полученных
     * из ответов данных, необходимых для выполнения работы программы
     *
     */
    private static final String URL = "http://localhost:8080/api/employees/";

    public static ObservableList<Employee> getAll(){
        ObservableList<Employee> emp_data = FXCollections.observableArrayList();
        String conn = HTTPConnection.GetRequest(URL);
        if(!conn.equals("null")){
            JsonArray result = new JsonParser().parse(conn).getAsJsonArray();
            for(int i=0; i<result.size(); i++){
                JsonObject emp_json = result.get(i).getAsJsonObject();
                Long empid = emp_json.get("id").getAsLong();
                String empname = emp_json.get("username").getAsString();
                String password = emp_json.get("password").getAsString();
                JsonObject dep = emp_json.get("department").getAsJsonObject();
                Departments departments = DepartmentsRequests.parsedep(dep);
                emp_data.add(new Employee(empid, empname, password, departments));
            }
            return emp_data;
        }
        else {return null;}

    }

    public static Employee getByUsername(String username){
        String conn = HTTPConnection.GetRequest(URL + "usernames/" + username);
        if(!conn.equals("null")){
            JsonObject result = new JsonParser().parse(conn).getAsJsonObject();
            Long empid = result.get("id").getAsLong();
            String empname = result.get("username").getAsString();
            String password = result.get("password").getAsString();
            JsonObject dep = result.get("department").getAsJsonObject();
            Departments departments = DepartmentsRequests.parsedep(dep);
            return new  Employee(empid, empname, password, departments);
        }
        else{
            return null;
        }
    }

    public static Boolean deleteEmployee(Employee users){
        Long id = users.getId();
        if(id == null) return false;
        Boolean val = HTTPConnection.DeleteRequest(URL + id);
        return val;
    }

    public static void createEmployee(Employee users){
        System.out.println(users.toJson());
        HTTPConnection.PostRequest(URL, users.toJson());
    }

    public static void updateEmployee(Employee users){
        System.out.println(users.toJson());
        HTTPConnection.PutRequest(URL + users.getId(), users.toJson());
    }
}
