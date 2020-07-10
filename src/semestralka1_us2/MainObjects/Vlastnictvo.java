/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.MainObjects;



/**
 * Trieda predstavuje vlastníctvo jedného občana
 * @see List_vlastn
 * @author MarekPC
 */
public class Vlastnictvo implements Comparable<Vlastnictvo> {
    /**
     * Občan vlastník 
     */
    private Obcan vlastnik;
    /**
     * Podieľ občana vlastníka
     */
    private double podiel;

    public Vlastnictvo() {
       
    }

    public Vlastnictvo(Obcan vlastnik, double podiel) {
        this.vlastnik = vlastnik;
        this.podiel = podiel;
    }
    
    public Vlastnictvo(Obcan vlastnik){
        this.vlastnik = vlastnik;
    }
    public void setVlastnik(Obcan vlastnik) {
        this.vlastnik = vlastnik;
    }

    public void setPodiel(double podiel) {
        this.podiel = podiel;
    }

    public Obcan getVlastnik() {
        return vlastnik;
    }

    public double getPodiel() {
        return podiel;
    }
    @Override
    public String toString(){
        return "Vlastnik : "+vlastnik.toString_0pobyt() + "\nPodiel: "+podiel+" %";
    }

    @Override
    public int compareTo(Vlastnictvo t) {
       return this.vlastnik.compareTo(t.vlastnik);
    }
   
    
    
    
    
    
}
