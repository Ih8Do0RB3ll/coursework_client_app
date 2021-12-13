package sample.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.models.*;
import sample.utils.*;

import java.io.IOException;

public class MainController {
    /**
     * Модуль контроллера всего приложения в целом
     * Данный модуль обеспечивает отображение всех элементов приложения, а также содержит в себе все обработчики событий
     * переключения между страницами приложения
     */


    private Main main;
    private Stage stage;
    private Employee emp;

    @FXML
    private Tab welcome;

    @FXML
    private Tab report;

    @FXML
    private Tab employee;

    @FXML
    private Tab departments;

    @FXML
    private Tab danger_level;

    @FXML
    private Tab error;

    @FXML
    private Tab about;

    @FXML
    private TabPane tabPane;

    private WelcomeController welcomeController;
    private EmployeePageController employeePageController;
    private ReportsPageController reportsPageController;
    private DepartmentsPageController departmentsPageController;
    private DangerLevelsPageController dangerLevelsPageController;
    private ErrorsPageController errorsPageController;
    private ReportEditPageController reportEditPageController;
    private DangerLevelEditPageController dangerLevelEditPageController;
    private EmployeeEditPageController employeeEditPageController;
    private DepartmentEditPageController departmentEditPageController;
    private ErrorEditPageController errorEditPageController;
    private StatisticsPageController statisticsPageController;

    public void initialize(Main main, Stage stage, Employee emp){
        this.main = main;
        this.stage = stage;
       // System.out.println(emp.getUsername() + " main controller init");
        this.emp = emp;

    }

    @FXML
    public void pressButton(){
        main.setEmp(null);
        main.start(stage);
    }

    public Report showReportEditPage(Stage stage, Report report, Employee employee) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/reportEditPage.fxml"));
        AnchorPane reportEditPage = (AnchorPane) loader.load();
        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(stage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(reportEditPage);
        dialogueStage.setScene(scene);
        reportEditPageController = loader.getController();
        reportEditPageController.initialize(dialogueStage, report,employee);
        dialogueStage.showAndWait();
        if (reportEditPageController.isOkClicked()){
            return reportEditPageController.getClickedReport();
        }else{
            return null;
        }
    }

    public Employee showEmployeeEditPage(Stage stage, Employee employee) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/employeeEditPage.fxml"));
        AnchorPane employeeEditPane = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("EDIT");
        dialogStage.initOwner(stage);
        dialogStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(employeeEditPane);
        dialogStage.setScene(scene);
        employeeEditPageController = loader.getController();
        employeeEditPageController.initialize(dialogStage, employee);
        dialogStage.showAndWait();
        if (employeeEditPageController.isOkClicked()){
            return employeeEditPageController.getCurrentEmployee();
        }else return null;
    }

    public DangerLevels showLevelEditPage(Stage stage, DangerLevels levels) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/dangerLevelEditPage.fxml"));
        AnchorPane levelEditPane = (AnchorPane) loader.load();
        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(stage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(levelEditPane);
        dialogueStage.setScene(scene);
        dangerLevelEditPageController = loader.getController();
        dangerLevelEditPageController.initialize(dialogueStage, levels);
        dialogueStage.showAndWait();
        if (dangerLevelEditPageController.isOkClicked()){
            return dangerLevelEditPageController.getClickedLevel();
        }else return null;
    }

    public Departments showDepartmentEditPage(Stage stage, Departments departments) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/departmentEditPage.fxml"));
        AnchorPane departmentEditPane = (AnchorPane) loader.load();
        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(stage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(departmentEditPane);
        dialogueStage.setScene(scene);
        departmentEditPageController = loader.getController();
        departmentEditPageController.inintialize(dialogueStage, departments);
        dialogueStage.showAndWait();
        if (departmentEditPageController.isOkClicked()){
            return departmentEditPageController.getCurrentDepartment();
        }else return null;
    }

    public Errors showErrorEditPage(Stage stage, Errors errors) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/errorEditPage.fxml"));
        AnchorPane errorEditPane = (AnchorPane) loader.load();
        Stage dialogueStage = new Stage();
        dialogueStage.setTitle("EDIT");
        dialogueStage.initOwner(stage);
        dialogueStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(errorEditPane);
        dialogueStage.setScene(scene);
        errorEditPageController = loader.getController();
        errorEditPageController.initialize(dialogueStage, errors);
        dialogueStage.showAndWait();
        if (errorEditPageController.isOkClicked()){
            return errorEditPageController.getCurrentError();
        }else return null;
    }

    public void showStatisticsPage(Stage stage, ObservableList<Report> errors) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/statisticsViewPage.fxml"));
        AnchorPane statistics = (AnchorPane) loader.load();
        Stage dialougeStage = new Stage();
        dialougeStage.setTitle("STATISTICS");
        dialougeStage.initOwner(stage);
        dialougeStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(statistics);
        dialougeStage.setScene(scene);
        statisticsPageController = loader.getController();
        statisticsPageController.initialize(dialougeStage, errors);
        dialougeStage.showAndWait();
    }

    public void showReportsByUser(Stage stage, ObservableList<Report> reports) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/reports.fxml"));
        AnchorPane reportsPage = (AnchorPane) loader.load();
        Stage dialougeStage = new Stage();
        dialougeStage.setTitle("Reports");
        dialougeStage.initOwner(stage);
        dialougeStage.initModality(Modality.WINDOW_MODAL);
        Scene scene = new Scene(reportsPage);
        dialougeStage.setScene(scene);
        reportsPageController = loader.getController();
        reportsPageController.initialize(dialougeStage, reports, emp);
        dialougeStage.showAndWait();
    }

    @FXML
    public void eventHandler() throws IOException {
        if (welcome.isSelected()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/welcomePage.fxml"));
            AnchorPane welcomePage = (AnchorPane) loader.load();
            welcomeController = loader.getController();
            welcomeController.initialize(stage, emp);
            welcome.setContent(welcomePage);
        }
        else if (employee.isSelected()){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/employees.fxml"));
            AnchorPane employeePage = (AnchorPane) loader.load();
            employeePageController = loader.getController();
            employeePageController.initialize(stage, EmployeeRequests.getAll());
            employee.setContent(employeePage);
        }
        else if(report.isSelected()){
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/reports.fxml")));
            AnchorPane reportPage = (AnchorPane) loader.load();
            reportsPageController = loader.getController();
            reportsPageController.initialize(stage, ReportRequests.getAll(), emp);
            report.setContent(reportPage);
        } else if(danger_level.isSelected()){
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/dangerlevels.fxml")));
            AnchorPane danger_level_page = (AnchorPane) loader.load();
            dangerLevelsPageController = loader.getController();
            dangerLevelsPageController.initialize(stage);
            danger_level.setContent(danger_level_page);
        } else if(departments.isSelected()){
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/departments.fxml")));
            AnchorPane departmentPage = (AnchorPane) loader.load();
            departmentsPageController = loader.getController();
            departmentsPageController.initialize(stage, DepartmentsRequests.getAll());
            departments.setContent(departmentPage);
        }else if(error.isSelected()){
            FXMLLoader loader = new FXMLLoader((getClass().getResource("../views/errors.fxml")));
            AnchorPane errorPage = (AnchorPane) loader.load();
            errorsPageController = loader.getController();
            errorsPageController.initialize(stage, ErrorsRequests.getAll());
            error.setContent(errorPage);
        }
    }

}
