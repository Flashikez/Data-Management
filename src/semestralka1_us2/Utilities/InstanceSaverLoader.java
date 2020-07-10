/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Utilities;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import semestralka1_us2.DataHolder.Holder;
import semestralka1_us2.Data_Structures.AVL.AVL;
import semestralka1_us2.Data_Structures.BinarySearchTree.TreeTraversals;
import semestralka1_us2.MainObjects.Kat_uzemie;
import semestralka1_us2.MainObjects.Kat_uzemie_str;
import semestralka1_us2.MainObjects.List_vlastn;
import semestralka1_us2.MainObjects.Nehnutelnost;
import semestralka1_us2.MainObjects.Obcan;
import semestralka1_us2.MainObjects.Vlastnictvo;

/**
 * Trieda ukladá a načítava dáta zo súborov
 * @author MarekPC
 */
public class InstanceSaverLoader {

    private static String fieldSeparator = ";";

    private static String txtFileKatastre = "katastre.txt";
    private static String txtFileListy = "listy.txt";
    private static String txtFileNehnutelnosti = "nehnutelnosti.txt";
    private static String txtFileObcania = "obcania.txt";
    
    /**
     * Načítanie databázy zo súborov
     * @return Holder s načítanými stromami
     */
    public static Holder loadState() {
        int nehnLoaded, obcLoaded, katLoaded, listyLoaded;
        nehnLoaded = obcLoaded = katLoaded = listyLoaded = 0;
        AVL<Kat_uzemie> katastre = new AVL<>();
        AVL<Kat_uzemie_str> katatstre_str = new AVL<>();
        AVL<Obcan> obcania = new AVL<>();
        BufferedReader reader;
        try {
            // Nacitanie katastrov
            reader = new BufferedReader(new FileReader(txtFileKatastre));

            String riadok = "";

            while ((riadok = reader.readLine()) != null) {
                String[] split = riadok.split(fieldSeparator);
                Kat_uzemie kat = new Kat_uzemie(split[1], Integer.parseInt(split[0]));
                Kat_uzemie_str kat_st = new Kat_uzemie_str(kat);
                katastre.insert(kat);
                katLoaded++;
                katatstre_str.insert(kat_st);
            }
            reader.close();
            // Listy vlastnictva zatial bez podielov
            reader = new BufferedReader(new FileReader(txtFileListy));
            riadok = "";
            while ((riadok = reader.readLine()) != null) {
                String[] split = riadok.split(fieldSeparator);
                Kat_uzemie kat = katastre.findKey(new Kat_uzemie("", Integer.parseInt(split[0])));
                List_vlastn list = new List_vlastn(kat, Integer.parseInt(split[1]));
                listyLoaded++;
                kat.addList(list);
                int pocetPodielov = Integer.parseInt(reader.readLine());
                for (int i = 0; i < pocetPodielov; i++) {
                    reader.readLine();
                }

            }

            reader.close();

            // Nehnutelnosti bez pobytov
            reader = new BufferedReader(new FileReader(txtFileNehnutelnosti));
            riadok = "";
            while ((riadok = reader.readLine()) != null) {
                String[] split = riadok.split(fieldSeparator);
                Kat_uzemie kat = katastre.findKey(new Kat_uzemie("", Integer.parseInt(split[3])));
                Nehnutelnost nehn = new Nehnutelnost(Integer.parseInt(split[0]), split[1], split[2], null);
                nehn.setKataster(kat);
                kat.addNehn(nehn);
                nehnLoaded++;

                // nehnutelnosť  je priradená v liste 
                if (split[4].compareTo(" ") != 0) {
                    List_vlastn list = kat.getList(Integer.parseInt(split[4]));
                    nehn.setList(list);
                    list.pridajNehnutelnost(nehn);

                }
                int pocetPobytov = Integer.parseInt(reader.readLine());
                for (int i = 0; i < pocetPobytov; i++) {
                    reader.readLine();

                }

            }
            reader.close();
            // Obcania
            reader = new BufferedReader(new FileReader(txtFileObcania));
            riadok = "";
            while ((riadok = reader.readLine()) != null) {
                String[] split = riadok.split(fieldSeparator);
                int pocetListov = Integer.parseInt(reader.readLine());
                Obcan obc = null;

                if (split[4].compareTo(" ") != 0) {
                    Kat_uzemie kat = katastre.findKey(new Kat_uzemie("", Integer.parseInt(split[3])));
                    Nehnutelnost nehn = kat.getNehnutelnost(Integer.parseInt(split[4]));
                    obc = new Obcan(split[0], split[1], new Datum(split[2]), nehn);
                } else {
                    obc = new Obcan(split[0], split[1], new Datum(split[2]), null);
                }

                for (int i = 0; i < pocetListov; i++) {
                    split = reader.readLine().split(fieldSeparator);
                    Kat_uzemie katl = katastre.findKey(new Kat_uzemie("", Integer.parseInt(split[0])));
                    List_vlastn list = katl.getList(Integer.parseInt(split[1]));
                    obc.pridajVlastnictvo(list);

                }
                obcania.insert(obc);
                obcLoaded++;
            }

            reader.close();

            //Trvale pobyty v nehnutelnostiach
            reader = new BufferedReader(new FileReader(txtFileNehnutelnosti));
            riadok = "";
            while ((riadok = reader.readLine()) != null) {
                String[] split = riadok.split(fieldSeparator);
                Kat_uzemie kat = katastre.findKey(new Kat_uzemie("", Integer.parseInt(split[3])));
                Nehnutelnost nehn = kat.getNehnutelnost(Integer.parseInt(split[0]));
                nehn.setKataster(kat);
                kat.addNehn(nehn);

                // nehnutelnosť  je priradená v liste 
                if (split[4].compareTo(" ") != 0) {
                    List_vlastn list = kat.getList(Integer.parseInt(split[4]));
                    nehn.setList(list);
                    list.pridajNehnutelnost(nehn);

                }
                int pocetPobytov = Integer.parseInt(reader.readLine());
                for (int i = 0; i < pocetPobytov; i++) {
                    Obcan obc = obcania.findKey(new Obcan(reader.readLine()));
                    nehn.pridajPobyt(obc);

                }

            }

            reader.close();
            // Vlastnictva na listoch

            reader = new BufferedReader(new FileReader(txtFileListy));
            riadok = "";
            while ((riadok = reader.readLine()) != null) {
                String[] split = riadok.split(fieldSeparator);
                Kat_uzemie kat = katastre.findKey(new Kat_uzemie("", Integer.parseInt(split[0])));
                List_vlastn list = kat.getList(Integer.parseInt(split[1]));

                int pocetPodielov = Integer.parseInt(reader.readLine());
                for (int i = 0; i < pocetPodielov; i++) {
                    split = reader.readLine().split(fieldSeparator);
                    Obcan obc = obcania.findKey(new Obcan(split[0]));
                    Vlastnictvo vlastnictvo = new Vlastnictvo(obc, Double.parseDouble(split[1]));
                    list.pridajVlastnictvo(vlastnictvo);
                }

            }

            reader.close();

        } catch (IOException | NumberFormatException ex) {
            Logger.getLogger(InstanceSaverLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String output = "Počet načítaných katastov: " + katLoaded + " \nPočet načítaných"
                + " listov: " + listyLoaded + " \nPočet načítaných nehnutelností: " + nehnLoaded + ""
                + "\nPočet načítaných občanov: " + obcLoaded;

        AlertManager.notify(output);

        return new Holder(katastre, obcania, katatstre_str);
    }
    /**
     * Uloží databázu do súborov
     * @param katastre_str AVL strom všetkých katastrálnych území
     * @param obcania AVL strom všetkých občanov v systéme
     * 
     */
    public static String saveState(AVL<Kat_uzemie_str> katastre_str, AVL<Obcan> obcania) {
        int nehnSaved, obcSaved, katSaved, listySaved;
        nehnSaved = obcSaved = katSaved = listySaved = 0;
        PrintWriter writer;
        try {
            writer = new PrintWriter(txtFileKatastre, "UTF-8");
            // Katastre
            ArrayList<Kat_uzemie_str> arrKat = TreeTraversals.getInOrder(katastre_str);
            for (Kat_uzemie_str kat_uzemie_str : arrKat) {
                String outputString = kat_uzemie_str.getKat().getId() + fieldSeparator + kat_uzemie_str.getKat().getNazov();
                writer.println(outputString);
                katSaved++;
            }
            //Listy
            writer.close();
            writer = new PrintWriter(txtFileListy, "UTF-8");
            for (Kat_uzemie_str kat_uzemie_str : arrKat) {
                Kat_uzemie kat = kat_uzemie_str.getKat();
                ArrayList<List_vlastn> listy = TreeTraversals.getInOrder(kat.getListy());
                for (List_vlastn list_vlastn : listy) {
                    ArrayList<Vlastnictvo> vlastnictva = TreeTraversals.getInOrder(list_vlastn.getPodiely());
                    int pocetVlastnictiev = vlastnictva.size();
                    String out = list_vlastn.getKat().getId() + fieldSeparator + list_vlastn.getId();
                    writer.println(out);
                    listySaved++;
                    writer.println(pocetVlastnictiev);
                    
                    for (Vlastnictvo vlastnictvo : vlastnictva) {
                        writer.println(vlastnictvo.getVlastnik().getRod_cislo() + fieldSeparator + vlastnictvo.getPodiel());

                    }

                }

            }

            writer.close();
            // Nehnutelnosti
            writer = new PrintWriter(txtFileNehnutelnosti, "UTF-8");
            for (Kat_uzemie_str kat_uzemie_str : arrKat) {
                Kat_uzemie kat = kat_uzemie_str.getKat();
                ArrayList<Nehnutelnost> nehn = TreeTraversals.getInOrder(kat.getNehnutelnosti());
                for (Nehnutelnost nehnutelnost : nehn) {
                    String out = nehnutelnost.getSup_cislo() + fieldSeparator + nehnutelnost.getAdresa() + fieldSeparator + nehnutelnost.getPopis() + fieldSeparator + nehnutelnost.getKatID() + fieldSeparator;
                    if (nehnutelnost.getList() == null) {
                        out += " ";
                    } else {
                        out += nehnutelnost.getList().getId();
                    }
                    nehnSaved++;
                    writer.println(out);

                    ArrayList<Obcan> trvalePobyty = TreeTraversals.getInOrder(nehnutelnost.getTrvalePobyty());
                    writer.println(trvalePobyty.size());
                    for (Obcan obc : trvalePobyty) {
                        writer.println(obc.getRod_cislo());
                    }

                }

            }
            writer.close();
            // Obcania
            writer = new PrintWriter(txtFileObcania, "UTF-8");
            ArrayList<Obcan> arrObc = TreeTraversals.getInOrder(obcania);
            for (Obcan obcan : arrObc) {
                String out = obcan.getRod_cislo() + fieldSeparator + obcan.getCele_meno() + fieldSeparator + obcan.getDat_nar() + fieldSeparator;
                if (obcan.getPobyt() == null) {
                    out += " ; ";
                } else {
                    out += obcan.getPobyt().getKatID() + fieldSeparator + obcan.getPobyt().getSup_cislo();
                }
                obcSaved++;
                writer.println(out);
                int listyCount = obcan.getVlastnictvaAsArray().size();
                writer.println(listyCount);
                for (List_vlastn list_vlastn : obcan.getVlastnictvaAsArray()) {
                    writer.println(list_vlastn.getKat().getId() + fieldSeparator + list_vlastn.getId());

                }

            }
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(InstanceSaverLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        String output = "Počet uložených katastov: " + katSaved + " \nPočet uložených"
                + " listov: " + listySaved + " \nPočet uložených nehnutelností: " + nehnSaved + ""
                + "\nPočet uložených občanov: " + obcSaved;
        AlertManager.notify(output);
        return "Stav uložený";
    }

}
