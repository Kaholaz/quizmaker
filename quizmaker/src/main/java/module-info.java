open module org.ntnu.k2.g2.quizmaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires google.http.client.gson;
    requires google.api.services.sheets.v4.rev493;
    requires google.http.client.jackson2;
    requires google.api.client;
    requires google.oauth.client.jetty;
    requires google.oauth.client.java6;
    requires kernel;
    requires layout;
    requires java.desktop;
    requires core;
    requires io;
    requires org.xerial.sqlitejdbc;
    requires com.google.api.client;
    requires com.google.api.services.drive;
    requires com.google.api.client.auth;

    exports org.ntnu.k2.g2.quizmaker;
}