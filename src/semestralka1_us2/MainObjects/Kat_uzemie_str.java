/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.MainObjects;

/**
 * <b>Pomocná trieda, aby mohlo existovať AVL katastrálnych území, kde kľúčom je názov katastrálneho územia</b>
 * @author MarekPC
 */
public class Kat_uzemie_str implements Comparable<Kat_uzemie_str>{
    Kat_uzemie kat;
    
    public Kat_uzemie_str(Kat_uzemie k) {
        kat = k;
        
    }

    public void setKat(Kat_uzemie kat) {
        this.kat = kat;
    }

    public Kat_uzemie getKat() {
        return kat;
    }
    /*
    Komparačná funkcia pre AVL, porovnáva názvy Katastrálnych území
    */
   
    @Override
    public int compareTo(Kat_uzemie_str par) {
       return kat.getNazov().compareTo(par.kat.getNazov());
    }
    
    
}
