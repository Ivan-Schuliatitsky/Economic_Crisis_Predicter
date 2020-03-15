package com.window;

import com.economic.Influarion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class SecondSceneController {

    @FXML
    private TextField Domestic;
    @FXML
    private TextField Sovereign;
    @FXML
    private TextField Revenues;
    @FXML
    private TextField Expenses;
    @FXML
    private TextField Taxes;
    @FXML
    private TextField Subsidies;
    @FXML
    private TextField first_half;
    @FXML
    private TextField second_half;
    @FXML
    private Button submit;
    @FXML
    private TextField in_Year;
    @FXML
    private TextField dollar;
    @FXML
    private TextField sys;
    @FXML
    private void submitButtonController() throws Exception {
        try {
            //submit.setText("Do It Again !");
            Double domestic = Double.parseDouble(Domestic.getText().trim());
            Double sovereign = Double.parseDouble(Sovereign.getText().trim());
            Double revenues  = 0.0;
            Double expenses  = 0.0;
            Double taxes     = 0.0;
            Double subsidies = 0.0;
            try {
                revenues = Double.parseDouble(Revenues.getText().trim());
                expenses = Double.parseDouble(Expenses.getText().trim());
                taxes = Double.parseDouble(Taxes.getText().trim());
                subsidies = Double.parseDouble(Subsidies.getText().trim());
            }catch (Exception e){
                System.out.println("just ok");
            }
            Double year = Double.parseDouble(in_Year.getText().trim());
            Double m_dollar = Double.parseDouble(dollar.getText().trim());
            String system = sys.getText().trim().toLowerCase();
            Double sys_crises = 0.0;
            if (system.equals("yes")) {
                sys_crises = 1.0;
            }
            System.out.println(system);
            System.out.println(String.valueOf(sys_crises));

            File file = new File("src/main/java/com/nado/influations.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;

            List<Double> infl = new ArrayList<Double>();
            while ((st = br.readLine()) != null) {
                infl.add(Double.valueOf(st));
            }
            List<Double> predictit = Influarion.getPrediction(infl);
            List<String[]> data = new ArrayList<String[]>();
            Integer influation_crises = 0;

            if (predictit.get(4) >= 20) {
                influation_crises = 1;
            }





            //if(expenses == 0.0 || taxes == 0.0|| revenues == 0.0 || subsidies == 0.0)
            //{
            //    expenses = 0.0;
            //    taxes = 0.0;
            //    revenues = 0.0;
            //    subsidies = 0.0;
            //}

            Double gdp = (revenues - expenses) + (taxes - subsidies);
            Double sys_crises2;
            if(sys_crises == 1.0){
                sys_crises2 = 0.0;
            }else{
                sys_crises2 = 0.0;  
            }

            data.add(new String[]{"99", "RUS", "Russia", String.valueOf(year), String.valueOf(sys_crises), String.valueOf(m_dollar), String.valueOf(domestic), String.valueOf(sovereign), String.valueOf(gdp), String.valueOf(predictit.get(0)), "1", "0", String.valueOf(influation_crises)});
            data.add(new String[]{"99", "RUS", "Russia", String.valueOf(year + 1), String.valueOf(sys_crises), String.valueOf(m_dollar), String.valueOf(domestic), String.valueOf(sovereign), String.valueOf(gdp), String.valueOf(predictit.get(1)), "1", "0", String.valueOf(influation_crises)});
            data.add(new String[]{"99", "RUS", "Russia", String.valueOf(year + 2), String.valueOf(sys_crises), String.valueOf(m_dollar), String.valueOf(domestic), String.valueOf(sovereign), String.valueOf(gdp), String.valueOf(predictit.get(2)), "1", "0", String.valueOf(influation_crises)});
            data.add(new String[]{"99", "RUS", "Russia", String.valueOf(year + 3), String.valueOf(sys_crises2), String.valueOf(m_dollar), String.valueOf(domestic), String.valueOf(sovereign), String.valueOf(gdp), String.valueOf(predictit.get(3)), "1", "0", String.valueOf(influation_crises)});
            data.add(new String[]{"99", "RUS", "Russia", String.valueOf(year + 4), String.valueOf(sys_crises2), String.valueOf(m_dollar), String.valueOf(domestic), String.valueOf(sovereign), String.valueOf(gdp), String.valueOf(predictit.get(4)), "1", "0", String.valueOf(influation_crises)});
            Scripts.createCSV(data);

            new ThirdStage();
            Stage stage = (Stage) submit.getScene().getWindow();
            stage.close();
        }catch (Exception e) {
            submit.setText("Problems!");
            submitButtonController();
        }
    }
}
