package sample.utils;

import com.google.gson.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import sample.models.*;

import java.sql.Date;
import java.time.LocalDate;

public class ReportRequests {
    /**
     * Модуль обработки и выполнения запросов к сущности Отчёты(ReportsRequests)
     * В данном модуле выполняется подключение к ннеобходимому api для выполнения запросов и обработки полученных
     * из ответов данных, необходимых для выполнения работы программы
     *
     */
    private static final String URL = "http://localhost:8080/api/reports/";
    public static Employee parseemp(JsonObject emp_json){
        Long empid = emp_json.get("id").getAsLong();
        String empname = emp_json.get("username").getAsString();
        String password = emp_json.get("password").getAsString();
        JsonObject dep = emp_json.get("department").getAsJsonObject();
        Departments departments = DepartmentsRequests.parsedep(dep);
        return new Employee(empid, empname, password, departments);
    }


    public static ObservableList<Report> getAll(){
        ObservableList<Report> reports = FXCollections.observableArrayList();
        String conn = HTTPConnection.GetRequest(URL);
        if(!conn.equals("null")){
            JsonArray result = new JsonParser().parse(conn).getAsJsonArray();
            for(int i=0; i<result.size(); i++){
                JsonObject report_json = result.get(i).getAsJsonObject();
                Long rep_id = report_json.get("id").getAsLong();
                LocalDate rep_date = DateUtil.parse(report_json.get("creation_date").getAsString());
                Employee rep_creator = parseemp(report_json.get("employee").getAsJsonObject());
                DangerLevels rep_level = DangerLevelsRequests.parselvl(report_json.get("dangerLevel").getAsJsonObject());
                Errors rep_error = ErrorsRequests.parseerror(report_json.get("errors").getAsJsonObject());
                String rep_description = report_json.get("report_description").getAsString();
                reports.add(new Report(rep_id, rep_date, rep_creator, rep_level, rep_error, rep_description) );
            }
            return reports;
        }else return null;
    }

    public static Boolean deleteReport(Report report){
        Long id = report.getId();
        if(id == null) return false;
        Boolean val = HTTPConnection.DeleteRequest(URL + id);
        return val;
    }

    public static void createReport(Report report){
        System.out.println(report.toJson());
        HTTPConnection.PostRequest(URL, report.toJson());
    }

    public static void updateReport(Report report){
        System.out.println(report.toJson());
        HTTPConnection.PutRequest(URL + report.getId(), report.toJson());
    }

    public static ObservableList<Report> getReportsByUser(Employee employee) {
        ObservableList<Report> reports = FXCollections.observableArrayList();
        Long id = employee.getId();
        String value = HTTPConnection.GetRequest(URL + "by_user/" + id);
        if (value == null) {
            return null;
        } else {
            JsonElement json = new JsonParser().parse(value);
            if (json instanceof JsonObject) {
                JsonObject report_json = json.getAsJsonObject();
                Long rep_id = report_json.get("id").getAsLong();
                LocalDate rep_date = DateUtil.parse(report_json.get("creation_date").getAsString());
                Employee rep_creator = parseemp(report_json.get("employee").getAsJsonObject());
                DangerLevels rep_level = DangerLevelsRequests.parselvl(report_json.get("dangerLevel").getAsJsonObject());
                Errors rep_error = ErrorsRequests.parseerror(report_json.get("errors").getAsJsonObject());
                String rep_description = report_json.get("report_description").getAsString();
                reports.add(new Report(rep_id, rep_date, rep_creator, rep_level, rep_error, rep_description));
            } else {
                JsonArray result = json.getAsJsonArray();
                for (int i = 0; i < result.size(); i++) {
                    JsonObject report_json = result.get(i).getAsJsonObject();
                    Long rep_id = report_json.get("id").getAsLong();
                    LocalDate rep_date = DateUtil.parse(report_json.get("creation_date").getAsString());
                    Employee rep_creator = parseemp(report_json.get("employee").getAsJsonObject());
                    DangerLevels rep_level = DangerLevelsRequests.parselvl(report_json.get("dangerLevel").getAsJsonObject());
                    Errors rep_error = ErrorsRequests.parseerror(report_json.get("errors").getAsJsonObject());
                    String rep_description = report_json.get("report_description").getAsString();
                    reports.add(new Report(rep_id, rep_date, rep_creator, rep_level, rep_error, rep_description));
                }
            }
            return reports;
        }
    }

}
