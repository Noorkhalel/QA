package com.example.currencyexchange;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class convertApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(convertApplication.class.getResource("converter.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 415, 455);
        stage.setTitle("MONEY-EXCHANGE$$");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}