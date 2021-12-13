package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.DangerLevels;
import sample.models.Errors;
import sample.models.Report;

public class DangerLevelsRequests {
    /**
     * Модуль обработки и выполнения запросов к сущности Степени угрозы(DangerLevelsRequests)
     * В данном модуле выполняется подключение к ннеобходимому api для выполнения запросов и обработки полученных
     * из ответов данных, необходимых для выполнения работы программы
     *
     */
    private static final String URL = "http://localhost:8080/api/dangerLevels/";

    public static DangerLevels parselvl(JsonObject level){
        Long lvlid = level.get("id").getAsLong();
        String leveldes = level.get("description").getAsString();
        return new DangerLevels(lvlid, leveldes);
    }

    public static ObservableList<DangerLevels> getAll(){
        ObservableList<DangerLevels> levels = FXCollections.observableArrayList();
        String conn = HTTPConnection.GetRequest(URL);
        if(!conn.equals("null")){
            JsonArray result = new JsonParser().parse(conn).getAsJsonArray();
            for(int i=0; i<result.size(); i++){
                JsonObject lvl_json = result.get(i).getAsJsonObject();
                levels.add(parselvl(lvl_json));
            }
            return levels;
        }else return null;
    }

    public static DangerLevels getById(Long id){
        String conn = HTTPConnection.GetRequest(URL + id);
        if(!conn.equals("null")){
            JsonObject res = new JsonParser().parse(conn).getAsJsonObject();
            return parselvl(res);
        }else {
            return null;
        }
    }

    public static Boolean deleteLevel(DangerLevels levels){
        Long id = levels.getId();
        if(id == null) return false;
        Boolean val = HTTPConnection.DeleteRequest(URL + id);
        return val;
    }

    public static void createLevel(DangerLevels levels){
        System.out.println(levels.toJson());
        HTTPConnection.PostRequest(URL, levels.toJson());
    }

    public static void updateLevel(DangerLevels levels){
        System.out.println(levels.toJson());
        HTTPConnection.PutRequest(URL + levels.getId(), levels.toJson());
    }
}
