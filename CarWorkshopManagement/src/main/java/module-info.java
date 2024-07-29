module com.mypackage.carworkshopmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.java;
    requires com.google.protobuf;
    requires jdk.jdi;
    requires java.desktop;
    requires io;
    requires kernel;
    requires layout;
    requires javafx.swing;
    requires jdk.compiler;
    requires org.controlsfx.controls;

    opens com.mypackage.carworkshopmanagement to javafx.fxml;
    exports com.mypackage.carworkshopmanagement.manage.view to javafx.graphics;
    exports com.mypackage.carworkshopmanagement.manage to javafx.graphics;
    exports com.mypackage.carworkshopmanagement to javafx.graphics;
}