package com.window;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class infl_controller {
    @FXML
    public javafx.scene.control.Button qwe;
    public Label second;
    public Label fifth;
    public Label third;
    public Label forth;
    public Label first;
    public TextField perviy;
    public TextField vtoroy;
    public TextField tretiy;
    public TextField chetr;
    public TextField piatiy;

    @FXML
    public void initialize() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        first.setText("Уровень инфляции за " + (year - 4) + " год :");
        second.setText("Уровень инфляции за " + (year - 3) + " год : ");
        third.setText("Уровень инфляции за " + (year - 2) + " год : ");
        forth.setText("Уровень инфляции за " + (year - 1) + " год : ");
        fifth.setText("Уровень инфляции за текущий год : ");
    }

    @FXML
    public void subm(ActionEvent actionEvent) throws Exception {
        Double fir = Double.valueOf(perviy.getText());
        Double sec = Double.valueOf(vtoroy.getText());
        Double thr = Double.valueOf(tretiy.getText());
        Double fort = Double.valueOf(chetr.getText());
        Double fifth = Double.valueOf(piatiy.getText());

        List<Double> infl = new ArrayList<Double>();

        infl.add(fir);
        infl.add(sec);
        infl.add(thr);
        infl.add(fort);
        infl.add(fifth);

        PrintWriter writer = new PrintWriter("src/main/java/com/nado/influations.txt", "UTF-8");
        writer.println(perviy.getText());
        writer.println(vtoroy.getText());
        writer.println(tretiy.getText());
        writer.println(chetr.getText());
        writer.println(piatiy.getText());
        writer.close();

        new SecondScene();
        Stage stage = (Stage) qwe.getScene().getWindow();
        stage.close();
    }
}
