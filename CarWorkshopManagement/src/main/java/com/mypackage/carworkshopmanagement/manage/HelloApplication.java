package com.mypackage.carworkshopmanagement.manage;
import javafx.application.Application;
import javafx.stage.Stage;
public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        com.mypackage.carworkshopmanagement.manage.view.Home home = new com.mypackage.carworkshopmanagement.manage.view.Home();
        home.start(stage);
    }
    public static void main(String[] args) {
        launch();
    }
}
