/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.MainObjects;


import java.util.ArrayList;
import semestralka1_us2.Data_Structures.AVL.AVL;
import semestralka1_us2.Data_Structures.BinarySearchTree.TreeTraversals;
import semestralka1_us2.Utilities.Datum;

/**
 * Trieda predstavujúca občana v systéme
 * @author MarekPC
 */
public class Obcan implements Comparable<Obcan> {
    private String rod_cislo;
    private String cele_meno;
    private Datum dat_nar;
    /**
     * Trvalý pobyt občana
     */
    private Nehnutelnost pobyt;
    /**
     * AVL strom listov vlastníctva, na ktorých ma občan podieľ
     */
    private AVL<List_vlastn> vlastnictva;

    public Obcan(String rod_cislo, String cele_meno, Datum dat_nar, Nehnutelnost pobyt) {
        this.rod_cislo = rod_cislo;
        this.cele_meno = cele_meno;
        this.dat_nar = dat_nar;
        this.pobyt = pobyt;
        this.vlastnictva = new AVL<>();
    }
    public Obcan(String rc){
        this.rod_cislo = rc;
    }

   public void pridajVlastnictvo(List_vlastn list){
       vlastnictva.insert(list);
   }

    

   @Override
    public int compareTo(Obcan par) {
       return rod_cislo.compareTo(par.rod_cislo);
    }

    public String getRod_cislo() {
        return rod_cislo;
    }

    public String getCele_meno() {
        return cele_meno;
    }

    public Datum getDat_nar() {
        return dat_nar;
    }

    public Nehnutelnost getPobyt() {
        return pobyt;
    }

    public ArrayList<List_vlastn> getVlastnictvaAsArray() {
        return TreeTraversals.getInOrder(vlastnictva);        
    }
    
    public boolean vlastni(List_vlastn list){
        return vlastnictva.containsKey(list);
    }

    public void setRod_cislo(String rod_cislo) {
        this.rod_cislo = rod_cislo;
    }

    public void setCele_meno(String cele_meno) {
        this.cele_meno = cele_meno;
    }

    public void setDat_nar(Datum dat_nar) {
        this.dat_nar = dat_nar;
    }

    public void setPobyt(Nehnutelnost pobyt) {
        this.pobyt = pobyt;
    }

    
    @Override
    public String toString(){
        if(pobyt == null){
             return " Rodné číslo: "+rod_cislo+" Celé Meno: "+cele_meno+" \nPobyt na: neznáme";
        }else
        return " Rodné číslo: "+rod_cislo+" Celé Meno: "+cele_meno+" \nPobyt na: "+ pobyt.toString_basic_list();
    }
    public String toString_0pobyt(){
         return "  "+rod_cislo+" "+cele_meno;
    }
    

    
    public void odoberVlastnictvo(List_vlastn list){
        this.vlastnictva.delete(list);
    }
    
    
    
}
