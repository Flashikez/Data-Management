/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Generate;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import semestralka1_us2.MainViewController;

/**
 * Kontrolér pre okno GenerateView, získava počet dát na vygenerovanie od užívateľa
 * @author MarekPC
 */
public class DataGenerate_Controller implements Initializable{
    @FXML
    Button btnGenerate;
    @FXML
    TextField tfKat;
    @FXML
    TextField tfList;
    @FXML
    TextField tfObc;
       @FXML
    TextField tfNehn;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       btnGenerate.setOnAction(e->{
          int kats = Integer.parseInt(tfKat.getText() );
          int list = Integer.parseInt(tfList.getText() );
          int obc = Integer.parseInt(tfObc.getText() );
          int nehn = Integer.parseInt(tfNehn.getText());
          
          MainViewController contr = new MainViewController(DataGenerator.generateData(kats, list, obc,nehn));
          FXMLLoader fx = new FXMLLoader();
         fx.setLocation(MainViewController.class.getResource("MainView.fxml"));

        Stage stage2 = new Stage();

        fx.setController(contr);
        Scene scene;
           try {
               scene = new Scene(fx.load());
                         stage2.setScene(scene);
                         stage2.setTitle("Semestrálna práca Marek Zaťko");
                         stage2.show();
           } catch (IOException ex) {
               Logger.getLogger(DataGenerate_Controller.class.getName()).log(Level.SEVERE, null, ex);
           }
          Stage stage = (Stage)  btnGenerate.getScene().getWindow();
            stage.close();
          
          
           
           
       });
       
        
        
        
    }
    
}
