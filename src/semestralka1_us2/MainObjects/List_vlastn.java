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
 * Trieda predstavujúca List vlastníctva
 * @author MarekPC
 */
public class List_vlastn implements Comparable<List_vlastn> {
    /**
     * Katastrále územia, do ktorého patrí list
     */
    private Kat_uzemie kat;
    /**
     * ID Listu vlastníctva, jedinečné len v rámci Katastrálneho územia
     */
    private int id;
    /**
     * AVL strom nehnuteľností, ktoré sú na liste
     */
    private AVL<Nehnutelnost> nehnutelnosti;
    /**
     * AVL strom vlastníctiev pre list vlastníctva
     * @see Vlastnictvo
     */
    private AVL<Vlastnictvo> vlastnictva;

    public List_vlastn(Kat_uzemie kat, int id) {
        this.kat = kat;
        this.id = id;
        this.nehnutelnosti = new AVL<>();
        this.vlastnictva = new AVL<>();
    }

    public AVL<Nehnutelnost> getNehnutelnosti() {
        return nehnutelnosti;
    }

    public Kat_uzemie getKat() {
        return kat;
    }

    public int getId() {
        return id;
    }

    public AVL<Vlastnictvo> getPodiely() {
        return vlastnictva;
    }

    public void pridajVlastnictvo(Vlastnictvo vl) {
        vlastnictva.insert(vl);
    }

    public void odoberVlastnictvo(Vlastnictvo vl) {
        vlastnictva.delete(vl);
    }
    
    public Nehnutelnost dajNehnutelnost(int sup_c){
        return nehnutelnosti.findKey(new Nehnutelnost(sup_c));
    }
    public boolean pridajNehnutelnost(Nehnutelnost n) {
        return nehnutelnosti.insert(n);
    }

    public void odoberNehnutelnost(Nehnutelnost n) {
         nehnutelnosti.delete(n);
    }

    public void setId(int id) {
        this.id = id;
    }
    /**
     * Komparačná funkcia pre list vlastníctva, porovnáva ID
     */
    @Override
    public int compareTo(List_vlastn t) {
        if (id < t.id) {
            return -1;
        } else if (id > t.id) {
            return 1;
        } else {
            return 0;
        }
    }

    public String toString() {
        String ret = "List vlastníctva č. " + id + " s nehnutelnosťami:";
        ret+=  "\n ....................\n";
        ArrayList<Nehnutelnost> nehn = TreeTraversals.getInOrder(nehnutelnosti);
        ArrayList<Vlastnictvo> vl = TreeTraversals.getInOrder(vlastnictva);
        ret+="Počet nehnuteľností na liste: "+nehn.size() +"\nPočet vlastníkov na liste: "+vl.size()+"\n";
        int countNehn = 1;
        double kontSucet = 0;
        for (Nehnutelnost n : nehn) {
            ret += countNehn+"."+n.toString_basicOnly() ;
            countNehn++;
        }
        int count = 1;
        ret += " \n Vlastníci a podiely:\n";
        
        for (Vlastnictvo vlastnictvo : vl) {
            ret+= "\n***********************************************\n";
            ret += count + ". " + vlastnictvo ;
            kontSucet += vlastnictvo.getPodiel();
            count++;
            ret+= "\n***********************************************\n";

        }
        ret += "\n   Konktrolný súčet podielov : " + kontSucet + "%   \n";
        return ret + " \n ....................\n ";
    }

    public void setKat(Kat_uzemie kat) {
        this.kat = kat;
    }

    public String toString_OnlyNehnutelnosti() {

        String ret = "List vlastníctva č. " + id + " s nehnutelnosťami: \n ....................\n";
        ArrayList<Nehnutelnost> nehn = TreeTraversals.getInOrder(nehnutelnosti);
        
        for (Nehnutelnost n : nehn) {
            ret += n.toString_basicOnly() + "\n";
        }
        return ret;
    }

}
