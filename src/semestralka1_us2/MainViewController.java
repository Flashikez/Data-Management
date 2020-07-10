/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.annotation.Resource;
import semestralka1_us2.DataHolder.Holder;
import semestralka1_us2.UserInput.GridController;
import semestralka1_us2.UserInput.I_HelpCall;
import semestralka1_us2.UserInput.InputController;
import semestralka1_us2.Utilities.AlertManager;
import semestralka1_us2.Utilities.ZadaniaText;

/**
 * <p>
 * <b>Hlavný GUI komponent, predstavuje kontrolér pre hlavné okno. </b><br>
 * S databázou komunikuje pomocou dataHolderu, ktorému pošle číslo zadania,
 * uživateľské vstupy a zobrazí výstup zadania</p>
 *
 * @see Holder
 * 
 *
 * @author MarekPC
 */
public class MainViewController implements Initializable {

    /**
     * Holder implementujúci jednotlivé zadania, predstavuje databázu.
     *
     * @see Holder
     */
    private Holder dataHolder;
    @FXML
    TextFlow tfOutput;
    @FXML
    MenuButton menuZadania;
    @FXML
    Button btnUloz;

    static int POCET_ZADANI = 21;
    static String ROD_CISLO_PROMPT = "Rodné číslo občana";
    static String MENO_PROMPT = "Celé meno občana";
    static String DATUM_NARODENIA_PROMPT = "Dátum narodenia:";
    static String ROD_CISLO2_PROMPT = "Nový majiteľ;Starý majiteľ (rodné čísla)";
    static String KAT_CIS_PROMPT = "Číslo katastrálneho územia";
    static String KAT1_CIS_PROMPT = "Staré číslo katastrálneho územia: ";
    static String KAT2_CIS_PROMPT = "Nové číslo katastrálneho územia: ";
    static String KAT_NAZOV_PROMPT = "Názov katastrálneho územia";
    static String SUP_CIS_PROMPT = "Súpisné číslo nehnutelnosti";
    static String ADRESA_PROMPT = "Adresa novej nehnuteľnosti";
    static String POPIS_PROMPT = "Popis novej nehnuteľnosti";
    static String LIST_CIS_PROMPT = "Číslo listu vlastníctva";
    static String LIST1_CIS_PROMPT = "Staré číslo listu vlastníctva";
    static String LIST2_CIS_PROMPT = "Nové číslo listu vlastníctva";
    static String INPUT_DISABLE_PROMPT = "-----------";
    /**
     * Uživateľské vstupy
     */
    private String vstup1, vstup2, vstup3;
    /**
     * List používaný pri zmene podieľov občanov, tvar stringu: "RČ;podieľ"
     */
    private ArrayList<String> gridStrings;

    private boolean inputWindowClosed;

    public MainViewController(Holder h) {
        dataHolder = h;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        populateComboBox();
        btnUloz.setOnAction(e -> {

            tfOutput.getChildren().clear();

            tfOutput.getChildren().add(new Text(dataHolder.saveInstance()));

        });
        tfOutput.getStylesheets().add(getClass().getResource("main.css").toExternalForm());
        tfOutput.getChildren().add(new Text("Naplnenie úspešné!"));

    }
    /**
     * Naplní comboBox 
     */
    private void populateComboBox() {
        for (int i = 1; i <= POCET_ZADANI; i++) {
            MenuItem item = new MenuItem(i + "");
            item.setOnAction(e -> {
                MenuItem m = (MenuItem) e.getSource();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        executeTask(Integer.parseInt(m.getText()));
                    }
                });

            });
            menuZadania.getItems().add(item);
        }

    }

    /**
     * <p>
     * Metóda, ktorá vykonáva jednotlivé zadania.<br> Získa vstupy od uživateľa, a
     * zobrazuje výstup dataHolderu, ktorý implementuje funkcionalitu zadaní</p>
     *
     * @param i číslo zadania
     */
    private void executeTask(int i) {

        I_HelpCall helpCall = null;
        inputWindowClosed = false;
        

        switch (i) {
            case 1:

                helpCall = () -> dataHolder.help_kat_nehn();
                getInputs(i, ZadaniaText.ZADANIE_1, KAT_CIS_PROMPT, SUP_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie1(Integer.parseInt(vstup1), Integer.parseInt(vstup2))));
                break;
            case 2:
                helpCall = () -> dataHolder.help_obc();
                getInputs(i, ZadaniaText.ZADANIE_2, ROD_CISLO_PROMPT, INPUT_DISABLE_PROMPT, INPUT_DISABLE_PROMPT, true, false, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie2((vstup1))));
                break;
            case 3:
                helpCall = () -> dataHolder.help_kat_nehn();
                getInputs(i, ZadaniaText.ZADANIE_3, KAT_CIS_PROMPT, SUP_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie3(Integer.parseInt(vstup1), 0, Integer.parseInt(vstup2))));
                break;
            case 4:
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_4, KAT_CIS_PROMPT, LIST_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie4(Integer.parseInt(vstup1), Integer.parseInt(vstup2))));
                break;
            case 5:
                helpCall = () -> dataHolder.help_kat_nehn();
                getInputs(i, ZadaniaText.ZADANIE_5, KAT_NAZOV_PROMPT, SUP_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie5((vstup1), Integer.parseInt(vstup2))));
                break;
            case 6:
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_6, KAT_NAZOV_PROMPT, LIST_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie6((vstup1), Integer.parseInt(vstup2))));
                break;
            case 7:
                helpCall = () -> dataHolder.help_katOnly();
                getInputs(i, ZadaniaText.ZADANIE_7, KAT_NAZOV_PROMPT, INPUT_DISABLE_PROMPT, INPUT_DISABLE_PROMPT, true, false, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie7((vstup1))));
                break;
            case 8:
                helpCall = () -> dataHolder.help_obc_listKat();
                getInputs(i, ZadaniaText.ZADANIE_8, ROD_CISLO_PROMPT, KAT_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie8(vstup1, Integer.parseInt(vstup2))));
                break;
            case 9:
                helpCall = () -> dataHolder.help_obc();
                getInputs(i, ZadaniaText.ZADANIE_8, ROD_CISLO_PROMPT, INPUT_DISABLE_PROMPT, INPUT_DISABLE_PROMPT, true, false, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie9(vstup1)));
                break;
            case 10:
                helpCall = () -> dataHolder.help_obc();
                getInputs(i, ZadaniaText.ZADANIE_10, ROD_CISLO_PROMPT, KAT_CIS_PROMPT, SUP_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie10(vstup1, Integer.parseInt(vstup2), Integer.parseInt(vstup3))));
                break;
            case 11:
                helpCall = () -> dataHolder.help_obc();
                getInputs(i, ZadaniaText.ZADANIE_11, ROD_CISLO2_PROMPT, KAT_CIS_PROMPT, SUP_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                String[] rodneCisla = vstup1.split(";");
                if (rodneCisla.length == 2) {
                    tfOutput.getChildren().add(new Text(dataHolder.zadanie11(rodneCisla[0], rodneCisla[1], Integer.parseInt(vstup2), Integer.parseInt(vstup3))));
                }
                break;
            case 12: {
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_12, ROD_CISLO_PROMPT, KAT_CIS_PROMPT, LIST_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                String zadanieReturn = dataHolder.zadanie12_1(vstup1, Integer.parseInt(vstup2), Integer.parseInt(vstup3));
                boolean success = zadanieReturn.substring(0, 3).compareTo("(S)") == 0;
                if (success) {
                    // INVOKE CHANGE OF PODIEL
                    ArrayList<String> arr = dataHolder.getPodielyGrid(Integer.parseInt(vstup2), Integer.parseInt(vstup3));

                    getGridInput(arr);
                    tfOutput.getChildren().add(new Text(dataHolder.applyPodielyFromGrid(Integer.parseInt(vstup2), Integer.parseInt(vstup3), gridStrings)));

                } else {
                    tfOutput.getChildren().add(new Text(zadanieReturn.substring(3)));
                }
                break;
            }
            case 13: {
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_13, ROD_CISLO_PROMPT, KAT_CIS_PROMPT, LIST_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                String zadanieReturn = dataHolder.zadanie13_1(vstup1, Integer.parseInt(vstup2), Integer.parseInt(vstup3));
                boolean success = zadanieReturn.substring(0, 3).compareTo("(S)") == 0;
                if (success) {
                    // INVOKE CHANGE OF PODIEL
                    ArrayList<String> arr = dataHolder.getPodielyGrid(Integer.parseInt(vstup2), Integer.parseInt(vstup3));

                    getGridInput(arr);
                    tfOutput.getChildren().add(new Text(dataHolder.applyPodielyFromGrid(Integer.parseInt(vstup2), Integer.parseInt(vstup3), gridStrings)));

                } else {
                    tfOutput.getChildren().add(new Text(zadanieReturn.substring(3)));
                }
                break;
            }
            case 14: {
                tfOutput.getChildren().clear();
                String zadanieReturn = dataHolder.zadanie14();
                tfOutput.getChildren().add(new Text(zadanieReturn));
                break;
            }
            case 15:
                helpCall = () -> dataHolder.help_obc();
                getInputs(i, ZadaniaText.ZADANIE_15, ROD_CISLO_PROMPT, MENO_PROMPT, DATUM_NARODENIA_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie15(vstup1, vstup2, vstup3)));
                break;
            case 16:
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_16, LIST_CIS_PROMPT, KAT_NAZOV_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie16(vstup2, Integer.parseInt(vstup1))));
                break;
            case 17:
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_17, SUP_CIS_PROMPT, KAT_CIS_PROMPT, LIST_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                String sup_cislo = vstup1;
                String kat_cislo = vstup2;
                String newList_cislo = vstup3;
                String firstResponse = dataHolder.zadanie17(Integer.parseInt(sup_cislo), Integer.parseInt(kat_cislo), Integer.parseInt(newList_cislo));
                String responseCode = firstResponse.substring(0, 3);
                String restOfResponse = firstResponse.substring(3);
                if (responseCode.compareTo("(F)") == 0) {
                    tfOutput.getChildren().add(new Text(restOfResponse));
                    return;
                } else if (responseCode.compareTo("(C)") == 0) {
                    // VYTVORENIE NOVEJ
                    String message = "Vytvorenie novej nehnuteľnosti \nSúpisné číslo nehnuteľnosti: " + sup_cislo + "\n\n Vyplň potrebné údaje";
                    getInputs(i, message, ADRESA_PROMPT, POPIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                    tfOutput.getChildren().add(new Text(dataHolder.zadanie17_pridanieNovej(Integer.parseInt(sup_cislo), Integer.parseInt(kat_cislo), Integer.parseInt(newList_cislo), vstup1, vstup2)));

                } else if (responseCode.compareTo("(P)") == 0) {
                    // POTVRDENIE ZMENY LISTU VLASTNICTVA PRE EXISTUJUCU NEHNUTELNOST
                    // OLD LIST ID = restOfResponse
                    String message = "Nehnuteľnosť sa v katastri č. " + kat_cislo + " nachádza v inom liste vlastníctva č. " + restOfResponse + " chceš nehnuteľnosť premiestniť na zadaný list č. " + newList_cislo;
                    boolean alertConfirmed = AlertManager.confirmAlert("Zmena podieľov", message);
                    if (alertConfirmed) {
                        String zadanieResponse = dataHolder.zadanie17_zmenaListu(Integer.parseInt(sup_cislo), Integer.parseInt(kat_cislo), Integer.parseInt(restOfResponse), Integer.parseInt(newList_cislo));
                        tfOutput.getChildren().add(new Text(zadanieResponse));
                    } else {
                        tfOutput.getChildren().add(new Text("Akcia zrušená"));
                    }

                } else {
                    // NEHNUTELNOST JE V KAT_UZEMI ALE NIE JE V ZIADOM LISTE
                    String message = "Nehnuteľnosť sa v katastri č. " + kat_cislo + " nachádza , ale nie je na žiadom liste vlastníctva!\n Chceš túto nehnuteľnosť premiestniť na list č. " + newList_cislo;
                    boolean alertConfirmed = AlertManager.confirmAlert("Pridanie nehnuteľnosti do listu", message);
                    if (alertConfirmed) {
                        String zadanieResponse = dataHolder.zadanie17_pridanieDoListu(Integer.parseInt(sup_cislo), Integer.parseInt(kat_cislo), Integer.parseInt(newList_cislo));
                        tfOutput.getChildren().add(new Text(zadanieResponse));
                    } else {
                        tfOutput.getChildren().add(new Text("Akcia zrušená"));
                    }
                }
                break;
            case 18:
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_18, KAT_CIS_PROMPT, LIST1_CIS_PROMPT, LIST2_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                String returnString = dataHolder.zadanie18(Integer.parseInt(vstup1), Integer.parseInt(vstup2), Integer.parseInt(vstup3));
                tfOutput.getChildren().add(new Text(returnString.substring(3)));
                if (returnString.substring(0, 3).compareTo("(S)") == 0) {

                    ArrayList<String> arr = dataHolder.getPodielyGrid(Integer.parseInt(vstup1), Integer.parseInt(vstup3));

                    getGridInput(arr);
                    tfOutput.getChildren().add(new Text(dataHolder.applyPodielyFromGrid(Integer.parseInt(vstup1), Integer.parseInt(vstup3), gridStrings)));

                }
                break;
            case 19:
                helpCall = () -> dataHolder.help_kat_list();
                getInputs(i, ZadaniaText.ZADANIE_19, SUP_CIS_PROMPT, KAT_CIS_PROMPT, LIST_CIS_PROMPT, true, true, true, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie19(Integer.parseInt(vstup1), Integer.parseInt(vstup2), Integer.parseInt(vstup3))));
                break;
            case 20:
                helpCall = () -> dataHolder.help_katOnly();
                getInputs(i, ZadaniaText.ZADANIE_20, KAT_CIS_PROMPT, KAT_NAZOV_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie20(Integer.parseInt(vstup1), (vstup2))));
                break;
            case 21:
                helpCall = () -> dataHolder.help_katOnly();
                getInputs(i, ZadaniaText.ZADANIE_21, KAT1_CIS_PROMPT, KAT2_CIS_PROMPT, INPUT_DISABLE_PROMPT, true, true, false, helpCall);
                if (inputWindowClosed) {
                    return;
                }
                tfOutput.getChildren().add(new Text(dataHolder.zadanie21(Integer.parseInt(vstup1), Integer.parseInt(vstup2))));
                break;
            default:
                break;
        }

    }

    /**
     * Funkcia vyvolá InputView , z ktorého získa uživateľské vstupy, ktoré budú v atribútoch: vstup1 -- vstup3
     */
    private void getInputs(int i, String zadanie, String t1, String t2, String t3, boolean b1, boolean b2, boolean b3, I_HelpCall h) {
        FXMLLoader fx = new FXMLLoader();

        fx.setLocation(InputController.class.getResource("InputView.fxml"));

        InputController contr = new InputController(i, zadanie, t1, t2, t3, b1, b2, b3, h);

        Stage stage2 = new Stage();

        fx.setController(contr);

        Scene scene;

        try {
            scene = new Scene(fx.load());
            stage2.setScene(scene);
            stage2.initModality(Modality.WINDOW_MODAL);
            stage2.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }

        vstup1 = contr.getOut1();
        if (vstup1 == null && b1) {
            inputWindowClosed = true;
        }

        vstup2 = contr.getOut2();
        if (vstup2 == null && b2) {
            inputWindowClosed = true;
        }
        vstup3 = contr.getOut3();
        if (vstup3 == null && b3) {
            inputWindowClosed = true;
        }
        if (!inputWindowClosed) {
            tfOutput.getChildren().clear();
        }

    }

    /** 
    * Funkcia vyvolá GridView , z ktorého načíta od uživateľa nové majetkové podiely, ktoré budú v atribúte gridStrings v tvare "RČ;podiel"
    */
    
    private void getGridInput(ArrayList<String> arr) {
        FXMLLoader fx = new FXMLLoader();

        fx.setLocation(InputController.class.getResource("GridView.fxml"));

        GridController contr = new GridController(arr);

        Stage stage2 = new Stage();

        fx.setController(contr);

        Scene scene;

        try {
            scene = new Scene(fx.load());
            stage2.setScene(scene);
            stage2.initModality(Modality.WINDOW_MODAL);
            stage2.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tfOutput.getChildren().clear();
        gridStrings = contr.getOutputs();

    }

}
