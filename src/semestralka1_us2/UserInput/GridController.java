/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.UserInput;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Kontrolér pre okno GridView, v ktorom používateľ mení majetkové podiely občanov
 * <br>
 * @author MarekPC
 */
public class GridController implements Initializable {

    @FXML
    GridPane grid;
    @FXML
    Label lbSucet;
    @FXML
    Button btnExe;
    ArrayList<TextField> tfPodiely;
    /**
     * Vstupný list stringov v tvare "RČ;pôvodnýPodiel"
     */
    ArrayList<String> input_strings;
    /**
     * Výstupný list stringov v tvare "RČ;novýPodiel"
     */
    ArrayList<String> output_strings;
    /**
     * Konštruktor
     * @param rc_podiely vstupný list pôovodných podieľov a rodných čísel občanov v tvare "RČ;podieľ"
     */
    public GridController(ArrayList<String> rc_podiely) {
        tfPodiely = new ArrayList<>();
        input_strings = rc_podiely;
        output_strings = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int rowCount = 0;
        for (String s : input_strings) {
            String[] split = s.split(";");
            Label rc = new Label(split[0]);
            double hodnota = (Double.parseDouble(split[1]));
            String pod = hodnota+"";
            TextField podiel = new TextField(pod);
            tfPodiely.add(podiel);
            podiel.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.compareTo("") != 0) 
                      calculateSum();
            });
            grid.add(rc, 0, rowCount);
            grid.add(podiel, 1, rowCount);
            rowCount++;

        }
        calculateSum();
        grid.setHgap(10); 
        grid.setVgap(10); 
        grid.setPadding(new Insets(10, 10, 10, 10));
        btnExe.setOnAction(e -> {
            int row = 0;
             for (String s : input_strings) {
                 String[] o = s.split(";");
                 String out = o[0]+";"+tfPodiely.get(row).getText();
                         row++;
                         output_strings.add(out);
             }

            Stage st = (Stage) btnExe.getScene().getWindow();
            st.close();

        });

    }
    /**
     * Vracia nové podieľy občanov
     * @return list nových podieľov v tvare "RČ;podieľ"
     */
    public ArrayList<String> getOutputs() {
        return output_strings;
    }
    /**
     * Počíta a zobrazuje súčasný súčet podieľov v okne
     */
    private void calculateSum() {
        double sum = 0;
        for (TextField textField : tfPodiely) {
            sum += Double.parseDouble(textField.getText());

        }
        lbSucet.setText("Súčet: " + sum + " %");
    }

}
