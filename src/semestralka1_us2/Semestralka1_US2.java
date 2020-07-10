/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import semestralka1_us2.Data_Structures.AVL.AVL;
import semestralka1_us2.Data_Structures.TreeTests.TreeTests;
import semestralka1_us2.Generate.DataGenerate_Controller;
import semestralka1_us2.Utilities.InstanceSaverLoader;

/**
 * Trieda obsahujúca main, získa uživateľský vstup, či dáta majú byť generované alebo načítané zo súborov
 * <br> Takisto obsahuje aj metódu, ktorá volá testovanie AVL stromu
 * @author MarekPC
 */
public class Semestralka1_US2 extends Application {

    @Override
    public void start(Stage stage) throws Exception {

//        testTree();

        normalStart(stage);
        
       

    }
    /**
     * Metóda volajúca test AVL stromu
     * @see TreeTests
     */
    public void testTree() {
        
        long startTime = System.nanoTime();
        int num_of_tests = 100;
        int num_of_operations_per_test = 10000;
        double insertChance = 0.65;
        
        
        for (int i = 0; i < num_of_tests; i++) {
            AVL<Integer> strom = new AVL<>();
//             System.out.println("TEST #"+i);
            TreeTests.test(strom, num_of_operations_per_test, insertChance);
        }
        long endTime = System.nanoTime();
        System.out.println("Vykonaných " + num_of_tests + " úspešných testov \n V priebehu: " + (endTime - startTime) * Math.pow(10, -9) + " sekúnd");
        System.exit(0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void normalStart(Stage stage) throws Exception {
        ButtonType generate = new ButtonType("Generovať", ButtonBar.ButtonData.OK_DONE);
        ButtonType load = new ButtonType("Načítať zo súborov", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.NONE, "Vyber akým spôsobom chceš naplniť systém", generate, load);
        alert.setTitle("Naplnenie systému");
        alert.showAndWait();
        if (alert.getResult() == generate) {
            Parent root = FXMLLoader.load(getClass().getResource("Generate/GenerateView.fxml"));
            Scene scene = new Scene(root);

            stage.setScene(scene);
            stage.show();
        } else {
            MainViewController contr = new MainViewController(InstanceSaverLoader.loadState());
            FXMLLoader fx = new FXMLLoader();
            fx.setLocation(MainViewController.class.getResource("MainView.fxml"));

            Stage stage2 = new Stage();

            fx.setController(contr);
            Scene scene;

            try {
                scene = new Scene(fx.load());
                stage2.setTitle("Semestrálna práca Marek Zaťko");
                stage2.setScene(scene);
                stage2.show();
            } catch (IOException ex) {
                Logger.getLogger(DataGenerate_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}
