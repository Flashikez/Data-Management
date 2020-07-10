/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.UserInput;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Kontrolér okna InputView, ktoré získava vstupy pre jednotlivé zadania od
 * uživateľa
 *
 * @author MarekPC
 */
public class InputController implements Initializable {

    @FXML
    Label lab1;
    @FXML
    Label lab2;
    @FXML
    Label lab3;
    @FXML
    Label labZadanie;
    @FXML
    TextField tf1;
    @FXML
    TextField tf2;
    @FXML
    TextField tf3;
    @FXML
    Button btnExe;
    @FXML
    Text textZadanie;
    @FXML
    Button btnHelp;

    private String zadaniec, zadanie, l1, l2, l3, out1, out2, out3;
    /**
     * Funkc. interface obsahujúci volanie danej pomocnej metódy triedy
     * Holder<br>
     * Táto metóda sa spustí po stlačení tlačídla Show Data
     */
    private I_HelpCall helpCall;
    boolean b1, b2, b3;

    public InputController(int zadaniec, String zadanie, String l1, String l2, String l3, boolean b1, boolean b2, boolean b3, I_HelpCall h) {
        this.zadaniec = "Zadanie č. " + zadaniec;
        this.zadanie = zadanie;
        this.l1 = l1;
        this.l2 = l2;
        this.l3 = l3;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        helpCall = h;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        labZadanie.setText(zadaniec);
        lab1.setText(l1);
        lab2.setText(l2);
        lab3.setText(l3);
        textZadanie.setText(zadanie);

        tf1.disableProperty().set(!b1);
        tf2.disableProperty().set(!b2);
        tf3.disableProperty().set(!b3);
        btnExe.setOnAction(e -> {
            out1 = tf1.getText();
            out2 = tf2.getText();
            out3 = tf3.getText();
            Stage st = (Stage) btnExe.getScene().getWindow();
            st.close();

        });
        btnHelp.setOnAction(e -> {
            FXMLLoader fx = new FXMLLoader();

            fx.setLocation(HelpController.class.getResource("HelpView.fxml"));

            HelpController contr = new HelpController(helpCall.run());

            Stage stage2 = new Stage();

            fx.setController(contr);

            Scene scene;

            try {
                scene = new Scene(fx.load());

                stage2.setScene(scene);
                stage2.initModality(Modality.WINDOW_MODAL);
                stage2.show();

            } catch (IOException ex) {
                Logger.getLogger(InputController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

    }

    /**
     *
     * @return Uživateľský vstup z prvej kolonky
     */
    public String getOut1() {
        return out1;
    }

    /**
     *
     * @return Uživateľský vstup z druhej kolonky
     */
    public String getOut2() {
        return out2;
    }

    /**
     *
     * @return Uživateľský vstup z tretej kolonky
     */
    public String getOut3() {
        return out3;
    }

}
