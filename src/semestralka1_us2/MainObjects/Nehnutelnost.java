/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.MainObjects;

import java.util.ArrayList;
import semestralka1_us2.Data_Structures.AVL.AVL;
import semestralka1_us2.Data_Structures.BinarySearchTree.TreeTraversals;

/**
 * Trida prestavujúca Nehnuteľnosť
 * @author MarekPC
 */
public class Nehnutelnost implements Comparable<Nehnutelnost> {
    /**
     * Jedinečné súpisné číslo
     */
    private int sup_cislo;
    /**
     * Kataster do ktorého nehnuteľnosť patrí, potrebný aby mohla nehnuteľnosť existovať aj bez zápisu na liste vlastníctva
     */
    private Kat_uzemie kataster;
    private String adresa;
    private String popis;
    /**
     * AVL strom občanov, ktorí majú trvalý pobyt v tejto nehnuteľnosti
     */
    private AVL<Obcan> trvalePobyty;
    /**
     * List vlastníctva na ktorom je nehnuteľnosť zapísaná
     */
    private List_vlastn list;

    public Nehnutelnost(int sup_cislo, String adresa, String popis, List_vlastn list) {
        this.sup_cislo = sup_cislo;
        this.adresa = adresa;
        this.popis = popis;
        trvalePobyty = new AVL<>();
        this.list = list;
        if (list != null) {
            this.kataster = list.getKat();
        }
    }

    public Nehnutelnost(int sup_cislo) {
        this.sup_cislo = sup_cislo;
    }

    public List_vlastn getList() {
        return list;
    }

    public int getKatID() {
        return kataster.getId();
    }

    public void setList(List_vlastn list) {
        this.list = list;
        if (list != null) {
            this.kataster = list.getKat();
        }
    }

    public void setKataster(Kat_uzemie kat) {
        this.kataster = kat;
    }

    public void pridajPobyt(Obcan o) {
        trvalePobyty.insert(o);
    }

    public void odoberPobyt(Obcan o) {
        trvalePobyty.delete(o);
    }

    @Override
    public int compareTo(Nehnutelnost t) {
        if (sup_cislo < t.sup_cislo) {
            return -1;
        } else if (sup_cislo > t.sup_cislo) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setSup_cislo(int sup_cislo) {
        this.sup_cislo = sup_cislo;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setTrvalePobyty(AVL<Obcan> trvalePobyty) {
        this.trvalePobyty = trvalePobyty;
    }

    public int getSup_cislo() {
        return sup_cislo;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getPopis() {
        return popis;
    }

    public AVL<Obcan> getTrvalePobyty() {
        return trvalePobyty;
    }

    public String toString_1list_1obc() {
        String returnString = "\nKataster: " + kataster.getId() + "\nSúpisné číslo: " + sup_cislo + " \nAdresa: " + adresa + " \nPopis: " + popis + " \n V tejto nehnuteľnosti majú "
                + "trvalý pobyt nahlásení občania: \n**********************\n";
        ArrayList<Obcan> obcania = TreeTraversals.getInOrder(trvalePobyty);
        for (Obcan obcan : obcania) {
            returnString += obcan.toString_0pobyt()+ "\n";

        }
        returnString+="**********************\n";

        if (list != null) {
            returnString += "***************************\n List vlastníctva na ktorej sa nachádza táto nehnutelnosť:\n----------------------\n"
                    + list.toString() + "\n----------------------------------------\n";
        } else {
            returnString += "\n Nehnutelnosť nie je zapísaná na liste vlastníctva";
        }

        return returnString;
    }

    public String toString_basic_list() {
        String returnString = this.toString_basicOnly();
        if (list != null) {
            returnString += "\n List vlastníctva na ktorej sa nachádza táto nehnutelnosť:\n----------------------------------------\n"
                    + list.toString() + "\n----------------------------------------\n";
        } else {
            returnString += "\n Nehnutelnosť nie je zapísaná na liste vlastníctva";
        }
        return returnString;

    }

    public String toString_basicOnly() {
        return "\n********************\n"+"Kataster: " + kataster.getId() + "\nSúpisné číslo: " + sup_cislo + " \nAdresa: " + adresa + " \nPopis: " + popis+"\n********************\n";
    }

}
