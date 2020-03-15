package com.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainSceneController {
    @FXML
    private Button mainButton;

    @FXML
    private void buttonClicked() throws Exception {
        new infl_data();
        Stage stage = (Stage) mainButton.getScene().getWindow();
        stage.close();
    }
}
