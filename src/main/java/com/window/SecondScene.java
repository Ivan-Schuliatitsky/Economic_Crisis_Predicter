package com.window;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SecondScene  {
        public SecondScene() throws Exception{
            Group group = new Group();
            URL url = new File("src/main/resources/second.fxml").toURI().toURL();
            Parent content = FXMLLoader.load(url);
            BorderPane root = new BorderPane();
            root.setCenter(content);
            group.getChildren().add(root);
            Stage stage = new Stage();
            stage.setScene(new Scene(group, 1050,620));
            stage.setTitle("Economic Analyst");
            FXMLLoader loader = new FXMLLoader();
            loader.setController(new SecondSceneController());
            File imageFile = new File("src/main/resources/icons8-brain-64.png");
            Image image = new Image(imageFile.toURI().toString());
            stage.getIcons().add(image);
            stage.show();
    }


}
