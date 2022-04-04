open module org.ntnu.k2.g2.quizmaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.base;
    requires google.http.client;
    requires google.http.client.gson;
    requires google.api.services.sheets.v4.rev493;
    requires google.http.client.jackson2;
    requires google.api.client;
    requires google.oauth.client.jetty;
    requires google.oauth.client.java6;
    requires google.oauth.client;
    requires kernel;
    requires layout;
    requires java.desktop;

    exports org.ntnu.k2.g2.quizmaker;
}