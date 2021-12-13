package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Departments;

public class DepartmentsRequests {
    /**
     * Модуль обработки и выполнения запросов к сущности Департаменты(DaepartmentsRequests)
     * В данном модуле выполняется подключение к ннеобходимому api для выполнения запросов и обработки полученных
     * из ответов данных, необходимых для выполнения работы программы
     *
     */
    private static final String URL = "http://localhost:8080/api/departments/";

    public static Departments parsedep(JsonObject department){
        Long depid = department.get("id").getAsLong();
        String depname = department.get("department_name").getAsString();
        return new Departments(depid, depname);
    }

    public static ObservableList<Departments> getAll(){
        ObservableList<Departments> departments = FXCollections.observableArrayList();
        String conn = HTTPConnection.GetRequest(URL);
        if(!conn.equals("null")){
            JsonArray result = new JsonParser().parse(conn).getAsJsonArray();
            for(int i=0; i<result.size(); i++){
                JsonObject dep_json = result.get(i).getAsJsonObject();
                departments.add(parsedep(dep_json));
            }
            return departments;
        }else return null;
    }

    public static Departments getById(Long id){
        String conn = HTTPConnection.GetRequest(URL + id);
        if(!conn.equals("null")){
            JsonObject res = new JsonParser().parse(conn).getAsJsonObject();
            return parsedep(res);
        }else {
            return null;
        }
    }

    public static Boolean deleteDep(Departments departments){
        Long id = departments.getId();
        if(id == null) return false;
        Boolean val = HTTPConnection.DeleteRequest(URL + id);
        return val;
    }

    public static void createDep(Departments departments){
        System.out.println(departments.toJson());
        HTTPConnection.PostRequest(URL, departments.toJson());
    }

    public static void updateDep(Departments departments){
        System.out.println(departments.toJson());
        HTTPConnection.PutRequest(URL + departments.getId(), departments.toJson());
    }
}
