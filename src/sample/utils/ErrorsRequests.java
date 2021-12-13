package sample.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.models.Errors;

public class ErrorsRequests {
    /**
     * Модуль обработки и выполнения запросов к сущности Ошибки(ErrorsRequests)
     * В данном модуле выполняется подключение к ннеобходимому api для выполнения запросов и обработки полученных
     * из ответов данных, необходимых для выполнения работы программы
     *
     */
    private static final String URL = "http://localhost:8080/api/errors/";

    public static Errors parseerror(JsonObject error){
        Long errorid = error.get("error_code").getAsLong();
        String errordes = error.get("description").getAsString();
        return new Errors(errorid, errordes);
    }

    public static ObservableList<Errors> getAll(){
        ObservableList<Errors> errors = FXCollections.observableArrayList();
        String conn = HTTPConnection.GetRequest(URL);
        if(!conn.equals("null")){
            JsonArray result = new JsonParser().parse(conn).getAsJsonArray();
            for(int i=0; i<result.size(); i++){
                JsonObject err_json = result.get(i).getAsJsonObject();
                errors.add(parseerror(err_json));
            }
            return errors;
        }else return null;
    }

    public static Errors getById(Long id){
        String conn = HTTPConnection.GetRequest(URL + id);
        if(!conn.equals("null")){
            JsonObject res = new JsonParser().parse(conn).getAsJsonObject();
            return parseerror(res);
        }else {
            return null;
        }
    }

    public static Boolean deleteError(Errors errors){
        Long id = errors.getError_code();
        if(id == null) return false;
        Boolean val = HTTPConnection.DeleteRequest(URL + id);
        return val;
    }

    public static void createError(Errors errors){
        System.out.println(errors.toJson());
        HTTPConnection.PostRequest(URL, errors.toJson());
    }

    public static void updateError(Errors errors){
        System.out.println(errors.toJson());
        HTTPConnection.PutRequest(URL + errors.getError_code(), errors.toJson());
    }

}
