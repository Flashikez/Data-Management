/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Utilities;

/**
 * Trieda reprezentujúca dátum 
 * @author MarekPC
 */
public class Datum {
    private int den;
    private int mesiac;
    private int rok;

    public Datum(int den, int mesiac, int rok) {
        this.den = den;
        this.mesiac = mesiac;
        this.rok = rok;
    }
    public Datum(String datum){
        String[] split = datum.split("\\.");
        this.den = Integer.parseInt(split[0]);
        this.mesiac = Integer.parseInt(split[1]);
        this.rok = Integer.parseInt(split[2]);
    }
    
    @Override
    public String toString(){
        return ""+den+"."+mesiac+"."+rok;
    }
    
    
}
