/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.MainObjects;

import semestralka1_us2.Data_Structures.AVL.AVL;

/**
 * <b>Trieda predstavuje jedno Katastrálne územia v systéme</b>
 *
 * @author MarekPC
 */
public class Kat_uzemie implements Comparable<Kat_uzemie> {

    /**
     * Jedinečný názov územia
     */
    private String nazov;
    /**
     * Jedinečné ID územia
     */
    private int id;
    /**
     * AVL strom listov vlastníctva, ktoré sa nachádzajú v Katastrálnom území
     *
     * @see List_vlastn
     */
    private AVL<List_vlastn> listy;
    /**
     * AVL strom všetkých nehnuteľností, ktoré sa nachádzajú v Katastrálnom území
     *
     * @see Nehnutelnost
     */
    private AVL<Nehnutelnost> nehnutelnosti;

    public Kat_uzemie(String nazov, int id) {
        this.nazov = nazov;
        this.id = id;
        listy = new AVL<>();
        nehnutelnosti = new AVL<>();
    }

    public AVL<List_vlastn> getListy() {
        return listy;
    }

    public AVL<Nehnutelnost> getNehnutelnosti() {
        return nehnutelnosti;
    }
    /**
     * Komparačná funkcia pre AVL katastrálnych územi, porovnáva ID území
     * 
     *  
     */
    @Override
    public int compareTo(Kat_uzemie t) {
        if (id < t.id) {
            return -1;
        } else if (id > t.id) {
            return 1;
        } else {
            return 0;
        }
    }
    /**
     * Vráti najäčšie ID listu vlastníctva, v Katastrálnom území.
     * Funkcia je potrebná pri spájaní dvoch katastrálnych území, kde obe majú list vlastníctva s rovnakým ID
     * @return najväčšie ID listu vlastníctva v Katastrálnom území
     */
    public int getMaxID_listy() {
        return listy.getMaxKey().getId();

    }

    public void odoberList(List_vlastn list) {
        listy.delete(list);
    }
    /**
     * Vráti najäčšie ID nehnuteľnosti, v Katastrálnom území.
     * Funkcia je potrebná pri spájaní dvoch katastrálnych území, kde obe majú nehnuteľnosť s rovnakým ID
     * @return najväčšie ID nehnuteľnosti v Katastrálnom území
     */
    public int getMaxID_nehnutelnosti() {
        return nehnutelnosti.getMaxKey().getSup_cislo();
    }

    public void setNazov(String nazov) {
        this.nazov = nazov;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazov() {
        return nazov;
    }

    public int getId() {
        return id;
    }

    public void addList(List_vlastn list) {
        listy.insert(list);
    }

    public void addNehn(Nehnutelnost nehn) {
        nehnutelnosti.insert(nehn);
    }

    public void odstranNehnutelnost(Nehnutelnost n) {
        nehnutelnosti.delete(n);
    }
    
    public Nehnutelnost getNehnutelnost(int id) {
        return nehnutelnosti.findKey(new Nehnutelnost(id, "", "", null));
    }

    public List_vlastn getList(int id) {
        return listy.findKey(new List_vlastn(null, id));
    }
}
