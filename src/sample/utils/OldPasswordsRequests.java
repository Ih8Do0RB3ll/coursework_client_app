package sample.utils;

import com.google.gson.JsonObject;
import sample.models.Departments;
import sample.models.OldPasswords;

public class OldPasswordsRequests {
    /**
     * Модуль обработки и выполнения запросов к сущности Старые пароли(OldPasswordsRequests)
     * В данном модуле выполняется подключение к ннеобходимому api для выполнения запросов и обработки полученных
     * из ответов данных, необходимых для выполнения работы программы
     *
     */
    private static final String URL = "http://localhost:8080/api/oldPasswords/";

    public static OldPasswords parsepass(JsonObject oldpassword){
        Long passid = oldpassword.get("id").getAsLong();
        String passold = oldpassword.get("old_pass").getAsString();
        return new OldPasswords(passid, passold);
    }
}
