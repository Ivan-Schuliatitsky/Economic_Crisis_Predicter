package com.window;

import  javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Path;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();
        URL url = new File("src/main/resources/enter.fxml").toURI().toURL();
        Parent content = FXMLLoader.load(url);
        BorderPane root = new BorderPane();
        root.setCenter(content);
        group.getChildren().add(root);
        primaryStage.setScene(new Scene(group, 1050,620));
        primaryStage.setTitle("Economic Analyst");
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new MainSceneController());

        File imageFile = new File("src/main/resources/icons8-brain-64.png");
        Image image = new Image(imageFile.toURI().toString());

        primaryStage.getIcons().add(image);
        primaryStage.show();
    }




}
