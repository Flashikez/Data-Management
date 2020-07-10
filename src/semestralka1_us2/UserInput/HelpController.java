/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.UserInput;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * Kontrolér pre okno HelpView, ktoré len zobrazuje pomocné dáta
 * @author MarekPC
 */
public class HelpController implements Initializable{
    @FXML
    TextFlow tfHelp;
    String helpString;
    public HelpController(String help) {
        helpString = help;
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfHelp.getChildren().add(new Text(helpString));
    }
    
}
