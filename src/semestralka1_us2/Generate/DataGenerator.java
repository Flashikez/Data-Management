/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Generate;

import java.util.Random;
import semestralka1_us2.DataHolder.Holder;
import semestralka1_us2.Data_Structures.AVL.AVL;
import semestralka1_us2.MainObjects.Kat_uzemie;
import semestralka1_us2.MainObjects.Kat_uzemie_str;
import semestralka1_us2.MainObjects.List_vlastn;
import semestralka1_us2.MainObjects.Nehnutelnost;
import semestralka1_us2.MainObjects.Obcan;
import semestralka1_us2.MainObjects.Vlastnictvo;
import semestralka1_us2.Utilities.Datum;

/**
 * <b> Trieda generujúca zadaný počet jednotlivých druhov dát</b>
 *
 * @author MarekPC
 */
public class DataGenerator {

    /**
     * Prvé ID vygenerovaného katastrálneho územia
     *
     * @see Kat_uzemie
     */
    static int ID_OFFSET_UZEMIE = 1000;
    /**
     * Názov vygenerovaného katastrálneho územia je "Kataster ID"
     *
     * @see Kat_uzemie_str
     */
    static String UZEMIE_NAZOV = "Kataster ";
    /**
     * Prvé ID vygenerovaného listu vlastníctva
     *
     * @see List_vlastn
     */
    static int ID_OFFSET_LIST = 220;
    /**
     * Prvé ID vygenerovanej nehnuteľnosti
     *
     * @see Nehnutelnost
     */
    static int ID_OFFSET_NEHN = 75000;
    /**
     * Prvé rodné číslo občana, pri vytváraní konkrétnej inštancie sa prevedie
     * na korektný tvar
     *
     * @see Obcan
     */
    static int ID_OFFSET_OBCAN = 1000000000;

    static Random rand = new Random();

    /**
     * Generuje náhodný dátum
     *
     * @return Vygenerovaný dátum
     * @see Datum
     */
    private static Datum generateDate() {

        int den = rand.nextInt((31 - 1) + 1) + 1;
        int mesiac = rand.nextInt((12 - 1) + 1) + 1;
        int rok = rand.nextInt((2015 - 1924) + 1) + 1924;
        return new Datum(den, mesiac, rok);

    }

    /**
     * Generuje adresu pre nehnuteľnosť zo slovníka
     *
     * @return Vygenerovaná adresa
     * @see RandomData
     */
    private static String generateAdresa() {
        int index = rand.nextInt((RandomData.adresy.length));
        return RandomData.adresy[index];
    }

    /**
     * Generuje meno pre občana zo slovníka
     *
     * @return Vygenerované meno
     * @see RandomData
     */
    private static String generateName() {
        int index = rand.nextInt((RandomData.names.length));
        return RandomData.names[index];
    }

    /**
     * Generuje popis pre nehnuteľnosť zo slovníka
     *
     * @return Vygenerovaný popis
     * @see RandomData
     */
    private static String generatePopis() {
        int index = rand.nextInt((RandomData.descritpionsNehn.length));
        return RandomData.descritpionsNehn[index];
    }

    /**
     * <b> Metóda generujúca zadaný počet dát</b>
     * Generovanie je rovnomerné t.j. napr. pre 3 katastrálne územia a pre 10
     * nehnuteľností:<br> 10/3 = 3 nehnuteľnosti pre kataster, do posledného doplníme zvyšok:<br>
     * 1. kataster - 3 nehnuteľnosti <br>
     * 2. kataster - 3 nehnuteľnosti <br>
     * 3. kataster - 4 nehnuteľnosti <br>
     * <br>
     * Rovnakým princípom generujeme všetky dáta
     * @param pocetKat celkový počet katastrálnych území
     * @param pocetList celkový počet listov vlastníctva
     * @param pocetObc celkový počet občanov
     * @param pocetNehn celkový počet nehnuteľnosti
     * @return vygenerovaný Holder obsahujúci potrebné stromy
     * @see Holder
     */
    public static Holder generateData(int pocetKat, int pocetList, int pocetObc, int pocetNehn) {
        // GENERATE DATA HERE AND RETURN HOLDER
        AVL<Kat_uzemie> uzemia = new AVL<>();
        AVL<Kat_uzemie_str> uzemiaNazov = new AVL<>();
        AVL<Obcan> obcania = new AVL<>();
        int listyCounter = ID_OFFSET_LIST;
        int nehnCounter = ID_OFFSET_NEHN;
        int obcanCounter = ID_OFFSET_OBCAN;
        if (pocetObc < pocetNehn) {
            pocetObc = pocetNehn;
        }

        int pocetListPerCycle = pocetList / pocetKat;

        double help = (pocetKat * ((double) pocetList / pocetKat));
        int pocetNehnPerCycle = (int) (pocetNehn / help); // LOSE DECIMAL POINT

        int pocetObcnPerCycle = pocetObc / (pocetNehnPerCycle * pocetListPerCycle * pocetKat);

        int katCounter = 0;

        for (int i = 0; i < pocetKat; i++) {
            katCounter++;
            int uzemie_id = i + ID_OFFSET_UZEMIE;
            Kat_uzemie uzemie = new Kat_uzemie(UZEMIE_NAZOV + uzemie_id, uzemie_id);
            uzemia.insert(uzemie);
            uzemiaNazov.insert(new Kat_uzemie_str(uzemie));
            // Do posledneho katastralneho uzemia dopln zvysne listy vlastnictva
            if (katCounter == pocetKat) {
                pocetListPerCycle = pocetList - (listyCounter - ID_OFFSET_LIST);
            }
            for (int j = 0; j < pocetListPerCycle; j++) {
                List_vlastn list_vl = new List_vlastn(uzemie, listyCounter);

                uzemie.addList(list_vl);
                listyCounter++;
                // Do posledneho listu vlastnictva dopln zvysne nehnutelnosti
                if (listyCounter - ID_OFFSET_LIST == pocetList) {
                    pocetNehnPerCycle = pocetNehn - (nehnCounter - ID_OFFSET_NEHN);
                }
                for (int k = 0; k < pocetNehnPerCycle; k++) {
                    Nehnutelnost nehn = new Nehnutelnost(nehnCounter, generateAdresa(), generatePopis(), list_vl);

                    nehnCounter++;
                    uzemie.addNehn(nehn);
                    list_vl.pridajNehnutelnost(nehn);
                    // Do poslednej nehnuteľnosti dopln zvyšok občanov
                    if (nehnCounter - ID_OFFSET_NEHN == pocetNehn) {
                        pocetObcnPerCycle = pocetObc - (obcanCounter - ID_OFFSET_OBCAN);
                    }
                    for (int l = 0; l < pocetObcnPerCycle; l++) {
                        String rod_cislo = Integer.toString(obcanCounter);
                        rod_cislo = rod_cislo.substring(0, 6) + "/" + rod_cislo.substring(6);
                        Obcan obc = new Obcan(rod_cislo, generateName(), generateDate(), nehn);
                        Vlastnictvo vlast = new Vlastnictvo();
                        nehn.pridajPobyt(obc);
                        vlast.setVlastnik(obc);
                        
                        double podielObcana = (1.0 / (pocetNehnPerCycle * pocetObcnPerCycle)) * 100;
//                        double roundOff = Math.round(podielObcana * 100.0) / 100.0;
                        vlast.setPodiel(podielObcana);
                        list_vl.pridajVlastnictvo(vlast);
                        obc.pridajVlastnictvo(list_vl);
                        obcania.insert(obc);
                        obcanCounter++;
                    }

                }

            }

        }

        return new Holder(uzemia, obcania, uzemiaNazov);
    }

}
