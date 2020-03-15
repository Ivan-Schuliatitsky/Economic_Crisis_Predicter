package com.window;

import com.economic.economic;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;
import org.apache.spark.sql.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class ThirdStageController {
    public List<Double> sum = new ArrayList<Double>();
    public List<Double> years = new ArrayList<Double>();
    //public static PipelineModel model = economic.getModel();
    //public static Dataset<Row> m_Predictions = com.ecomcomic.economic.create_prediction(model);
    @FXML
    public TextArea area;

    @FXML
    public Button test;

    @FXML
    public Button correct;

    @FXML
    public Button conc;
    @FXML
    public Button go;

    @FXML
    public ChoiceBox choice;


    @FXML
    public void initialize() {

        ObservableList<String> langs = FXCollections.observableArrayList("Logistic Regression","Decision Tree","Perceptron");
        choice.setItems(langs);
    }

    /*
    public void test1(ActionEvent actionEvent) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command("C:\\Users\\admin\\IdeaProjects\\WindowsForML\\run.exe");
        Process process = processBuilder.start();

        StringBuilder output = new StringBuilder();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()));

        String lines;
        while ((lines = reader.readLine()) != null) {
            output.append(lines + "\n");
        }

        int exitVal = process.waitFor();
        if (exitVal == 0) {
            System.out.println("Success!");
            System.out.println(output);
            System.exit(0);
        } else {
            //abnormal...
        }

        String fileName = "src/main/resources/test.txt";
        File file = new File(fileName);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) != null){
            //process the line
            area.appendText(line);
            area.appendText("\n");
        }
        area.appendText("\n");
        conc.setOpacity(1);
        correct.setOpacity(1);
    }
    */

    public void test(ActionEvent actionEvent) throws FileNotFoundException, UnsupportedEncodingException {
        //test.setText("Again");
        List<Row> data = economic.testLogReg();
        if (choice.getValue() == "Logistic Regression") {
            data = economic.testLogReg();
        }
        if (choice.getValue() == "Decision Tree") {
            data = economic.testTree();
        }
        if (choice.getValue() == "Perceptron") {
            data = economic.testTree();
        }
        area.appendText("\n");
        area.appendText("Country ; Year ; Systemic_crisis ; USD ; GDP ; Inflation ; Inflation_Crises ; OUTPUT");
        area.appendText("\n");
        for (Row row : data) {
            area.appendText(valueOf(row.get(2)));
            area.appendText(" | ");
            area.appendText(valueOf(row.get(3)));
            area.appendText(" | ");
            if(valueOf(row.get(4)).equals("1.0") ) {
                area.appendText("crisis");
            }else {
                area.appendText("no_crisis");
            }
            area.appendText(" | ");
            area.appendText(valueOf(row.get(5)));

            years.add((Double) row.get(3));


            area.appendText(" | ");
            area.appendText(valueOf(Math.round(((Double) row.get(8)))));
            area.appendText(" | ");
            area.appendText(valueOf(Math.round(((Double) row.get(9)))) + " %");
            area.appendText(" | ");
            area.appendText(valueOf(row.get(12)));
            area.appendText(" | ");
            area.appendText(valueOf(row.get(16)));

            sum.add((Double) row.get(16));

            area.appendText("\n");
            conc.setOpacity(1);
            correct.setOpacity(1);
        }
    }

    public void correcting(ActionEvent actionEvent) throws Exception {
       new SecondScene();
       Stage stage = (Stage) correct.getScene().getWindow();
       stage.close();
    }

    public void doing_conclusion(ActionEvent actionEvent) {
        correct.setOpacity(0);
        area.appendText("+-------------------------------------------------------------------------------------------------+");
        area.appendText("\n");
        for (int i = 0; i < 5; i++) {
                area.appendText("В ");
                area.appendText(String.valueOf(Math.round(years.get(i))));
                if(sum.get(i) == 0){
                    area.appendText(" экономического кризиса не будет");
                }else{
                    area.appendText(" намечается экономический кризис");
                }
                area.appendText(".");
                area.appendText("\n");

        }
        go.setOpacity(1);
        test.setOpacity(0);
    }

    public void going(ActionEvent actionEvent) throws Exception {
        new FourthStage();
        Stage stage = (Stage) go.getScene().getWindow();
        stage.close();
    }
}
