/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.DataHolder;

import java.util.ArrayList;

import semestralka1_us2.Data_Structures.AVL.AVL;
import semestralka1_us2.Data_Structures.BinarySearchTree.TreeTraversals;
import semestralka1_us2.MainObjects.Kat_uzemie;
import semestralka1_us2.MainObjects.Kat_uzemie_str;
import semestralka1_us2.MainObjects.List_vlastn;
import semestralka1_us2.MainObjects.Nehnutelnost;
import semestralka1_us2.MainObjects.Obcan;
import semestralka1_us2.MainObjects.Vlastnictvo;
import semestralka1_us2.Utilities.Datum;
import semestralka1_us2.Utilities.InstanceSaverLoader;
import semestralka1_us2.Generate.DataGenerator;

/**
 * <b> Trieda implemetujúca databázu, drží si potrebné stromy a implementuje
 * jednotlivé zadania </b>
 * <br>
 * Stromy získa buď od generátora alebo od čítača zo súborov, Zadania vracajú
 * len výstupné reťazce
 *
 * @see DataGenerator
 * @see InstanceSaverLoader
 *
 * @author MarekPC
 */
public class Holder {

    /**
     * AVL strom pre katastrálne územia, kde kľúčom je <b>ID katastrálneho
     * územia</b>
     *
     * @see Kat_uzemie
     */
    private AVL<Kat_uzemie> uzemia;
    /**
     * AVL strom pre katastrálne územia, kde kľúčom je <b>Názov katastrálneho
     * územia</b>
     *
     * @see Kat_uzemie_str
     */
    private AVL<Kat_uzemie_str> uzemiaNazov;
    /**
     * AVL strom všetkých občanov v systéme, kde kĺúčom je <b> Rodné číslo
     * občana</b>
     *
     * @see Obcan
     */
    private AVL<Obcan> obcania;

    public Holder(AVL<Kat_uzemie> uzemia, AVL<Obcan> obcania, AVL<Kat_uzemie_str> uzemiaS) {
        this.uzemia = uzemia;
        this.obcania = obcania;
        this.uzemiaNazov = uzemiaS;
    }

    /**
     * Pomocný výpis katastrov a všetkých nehnuteľností v ňom
     *
     * @return pomocný výpis
     */
    public String help_kat_nehn() {
        String retString = "Existujúce katastrálne územia a nehnuteľnosti v ňom:\n\n";
        ArrayList<Kat_uzemie> arr = TreeTraversals.getInOrder(uzemia);
        for (Kat_uzemie kat_uzemie : arr) {
            retString += "Katastrálne územie ID: " + kat_uzemie.getId() + " Názov: " + kat_uzemie.getNazov() + "\n";
            ArrayList<Nehnutelnost> arrN = TreeTraversals.getInOrder(kat_uzemie.getNehnutelnosti());
            for (Nehnutelnost nehnutelnost : arrN) {
                retString += "\t\t Nehnutelnosť ID: " + nehnutelnost.getSup_cislo() + "\n";

            }

        }
        return retString;
    }

    /**
     * Pomocný výpis občanov
     *
     * @return výpis občanov
     */
    public String help_obc() {
        String retString = "Existujúci občania v systéme:\n\n";
        ArrayList<Obcan> arr = TreeTraversals.getInOrder(obcania);
        for (Obcan o : arr) {
            retString += "Rod č.: : " + o.getRod_cislo() + "\n";
        }
        return retString;
    }

    /**
     * Pomocný výpis občanov a katastrálnych území v ktorých má občan podieľ
     *
     * @return
     */
    public String help_obc_listKat() {
        String retString = "Existujúci občania v systéme spolu s katastrálnymi územiami v ktorých má list vlastníctva:\n\n";
        ArrayList<Obcan> arr = TreeTraversals.getInOrder(obcania);
        for (Obcan obcan : arr) {
            retString += "Rod č.: : " + obcan.getRod_cislo() + "\n";
            ArrayList<List_vlastn> arrVl = obcan.getVlastnictvaAsArray();
            for (List_vlastn list_vlastn : arrVl) {
                retString += "   " + list_vlastn.getKat().getId() + "\n";
            }

        }
        return retString;
    }

    /**
     * Pomocný výpis katastrálnych území v systéme
     *
     * @return
     */
    public String help_katOnly() {
        String retString = "Exsitujúce katastrálne územia v systéme:\n\n";
        ArrayList<Kat_uzemie_str> arr = TreeTraversals.getInOrder(uzemiaNazov);
        for (Kat_uzemie_str ks : arr) {
            retString += ks.getKat().getNazov() + "\n";

        }
        return retString;
    }
    /**
     * Pomocný výpis katastrov a listov vlastníctva v nich
     * @return 
     */
    public String help_kat_list() {
        String retString = "Existujúce katastrálne územia a listy vlastníctva v ňom:\n\n";
        ArrayList<Kat_uzemie> arr = TreeTraversals.getInOrder(uzemia);
        for (Kat_uzemie kat_uzemie : arr) {
            retString += "Katastrálne územie ID: " + kat_uzemie.getId() + " Názov: " + kat_uzemie.getNazov() + "\n";
            ArrayList<List_vlastn> arrN = TreeTraversals.getInOrder(kat_uzemie.getListy());
            for (List_vlastn list : arrN) {
                retString += "\t\t List vlastníctva ID: " + list.getId() + "\n";

            }

        }
        return retString;

    }
    
    
    

    public String zadanie1(int kat, int sup_c) {
        Kat_uzemie u = uzemia.findKey(new Kat_uzemie("", kat));
        if (u == null) {
            return "Katastrálne územie s ID " + kat + " neexistuje ";
        }
        Nehnutelnost n = u.getNehnutelnost(sup_c);
        if (n == null) {
            return "Nehnuteľnosť so súpisným číslom  " + sup_c + " sa v katastri s ID " + kat + " nenachádza ";
        }
        return n.toString_1list_1obc();
    }

    public String zadanie2(String rc) {
        Obcan o = obcania.findKey(new Obcan(rc));
        if (o == null) {
            return "Občan s rodným číslom " + rc + " neexistuje ";
        }
        return o.toString();
    }

    public String zadanie3(int kat, int list, int sup) {
        Kat_uzemie k = uzemia.findKey(new Kat_uzemie("", kat));
        if (k == null) {
            return "Katastrálne územie s ID " + kat + " neexistuje ";
        }

        Nehnutelnost n = k.getNehnutelnost(sup);
        if (n == null) {
            return "Nehnuteľnosť so súpisným číslom  " + sup + " sa v katastri s ID " + kat + " nenachádza ";
        }
        String ret = n.toString_basicOnly() + " \n Trvalé pobyty: \n";
        ArrayList<Obcan> arr = TreeTraversals.getInOrder(n.getTrvalePobyty());
        for (Obcan o : arr) {
            ret += o.toString_0pobyt() + "\n";
        }
        return ret;
    }

    public String zadanie4(int kat, int list) {
        Kat_uzemie k = uzemia.findKey(new Kat_uzemie("", kat));
        if (k == null) {
            return "Katastrálne územie s ID " + kat + " neexistuje ";
        }

        List_vlastn n = k.getList(list);
        if (n == null) {
            return "List  s číslom  " + list + " sa v katastri s číslom " + kat + " nenachádza ";
        }
        return n.toString();

    }

    public String zadanie5(String kat, int sup_c) {
        Kat_uzemie_str str = uzemiaNazov.findKey(new Kat_uzemie_str(new Kat_uzemie(kat, 0)));
        if (str == null) {
            return "Katastrálne územie s názvom " + kat + " neexistuje ";
        }
        Nehnutelnost n = str.getKat().getNehnutelnost(sup_c);
        if (n == null) {
            return "Nehnuteľnosť so súpisným číslom  " + sup_c + " sa v katastri s názvom " + kat + " nenachádza ";
        }
        return n.toString_1list_1obc();
    }

    public String zadanie6(String kat, int list) {
        Kat_uzemie_str k = uzemiaNazov.findKey(new Kat_uzemie_str(new Kat_uzemie(kat, 0)));
        if (k == null) {
            return "Katastrálne územie s názvom " + kat + " neexistuje ";
        }

        List_vlastn n = k.getKat().getList(list);
        if (n == null) {
            return "List  s číslom  " + list + " sa v katastri s číslom " + kat + " nenachádza";
        }
        return n.toString();

    }

    public String zadanie7(String kat) {
        Kat_uzemie_str k = uzemiaNazov.findKey(new Kat_uzemie_str(new Kat_uzemie(kat, 0)));
        if (k == null) {
            return "Katastrálne územie s názvom " + kat + " neexistuje ";
        }
        String retString = "Nehnuteľnosti v katastrálnom území " + kat + "  :\n";
        ArrayList<Nehnutelnost> arr = TreeTraversals.getInOrder(k.getKat().getNehnutelnosti());
        for (Nehnutelnost nehnutelnost : arr) {
            retString += "Súpisné číslo: " + nehnutelnost.getSup_cislo() + "  Popis: " + nehnutelnost.getPopis() + " \n";

        }
        return retString;
    }

    public String zadanie8(String rc, int kat) {
        Obcan o = obcania.findKey(new Obcan(rc));
        if (o == null) {
            return "Občan s rodným číslom " + rc + " neexistuje ";
        }
        String retString = "Občan s rodným číslom " + rc + " vlastní v katastrálnom území " + kat + " nasledovné nehnutelnosti: \n";
        ArrayList<List_vlastn> arr = o.getVlastnictvaAsArray();
        for (List_vlastn list_vlastn : arr) {
            if (list_vlastn.getKat().getId() == kat) {
                retString += list_vlastn.toString_OnlyNehnutelnosti()
                        + "Podieľ občana s RČ " + rc + " : " + list_vlastn.getPodiely().findKey(new Vlastnictvo(new Obcan(rc))).getPodiel() + "% \n\n";

            }

        }
        return retString;
    }

    public String zadanie9(String rc) {
        Obcan o = obcania.findKey(new Obcan(rc));
        if (o == null) {
            return "Občan s rodným číslom " + rc + " neexistuje ";
        }
        String retString = "Občan s rodným číslom " + rc + " vlastní " + "nasledovné nehnutelnosti: \n";
        ArrayList<List_vlastn> arr = o.getVlastnictvaAsArray();
        for (List_vlastn list_vlastn : arr) {
            retString += list_vlastn.toString_OnlyNehnutelnosti()
                    + "Podieľ občana s RČ " + rc + " : " + list_vlastn.getPodiely().findKey(new Vlastnictvo(new Obcan(rc))).getPodiel() + "% \n\n";

        }
        return retString;
    }

    public String zadanie10(String rc, int kat, int sup_c) {
        Obcan o = obcania.findKey(new Obcan(rc));
        if (o == null) {
            return "Občan s rodným číslom " + rc + " neexistuje ";
        }
        Kat_uzemie kataster = uzemia.findKey(new Kat_uzemie("", kat));
        if (kataster == null) {
            return "Katastrálne územie s ID " + kat + " neexistuje ";
        }
        Nehnutelnost novyPobyt = kataster.getNehnutelnost(sup_c);
        if (novyPobyt == null) {
            return "Nehnuteľnosť so súpisným číslom  " + sup_c + " sa v katastri s názvom " + kat + " nenachádza ";
        }
        Nehnutelnost staryPobyt = o.getPobyt();
        if (staryPobyt == null) {
            // Obcan nemá trvalý pobyt
            novyPobyt.pridajPobyt(o);
            o.setPobyt(novyPobyt);
            return "Pobyt úspešne pridaný";
        } else {
            //Obcan už má pobyt
            staryPobyt.odoberPobyt(o);
            novyPobyt.pridajPobyt(o);
            o.setPobyt(novyPobyt);
            return "Občan s r.č. " + rc + " už mal trvalý pobyt v nehnutelnosti č. " + staryPobyt.getSup_cislo() + " \nTento pobyt bol zmenený, nový pobyt v nehnutelnosti č." + novyPobyt.getSup_cislo();
        }

    }

    public String zadanie11(String noveRC, String stareRC, int kat, int sup_c) {
        Obcan novyVlastnik = obcania.findKey(new Obcan(noveRC));
        if (novyVlastnik == null) {
            return "Občan s rodným číslom " + noveRC + " neexistuje ";
        }

        Kat_uzemie kataster = uzemia.findKey(new Kat_uzemie("", kat));
        if (kataster == null) {
            return "Katastrálne územie s ID " + kat + " neexistuje ";
        }
        Nehnutelnost nehnutelnost = kataster.getNehnutelnost(sup_c);
        if (nehnutelnost == null) {
            return "Nehnuteľnosť so súpisným číslom  " + sup_c + " sa v katastri s názvom " + kat + " nenachádza ";
        }
        List_vlastn list = nehnutelnost.getList();
        if (list == null) {
            return "Zadaná nehnuteľnosť nie je na žiadnom liste vlastníctva, použi Zadanie 17, ak chceš nehnuteľnosť pridať";
        }

        Vlastnictvo stareVlastnictvo = list.getPodiely().findKey(new Vlastnictvo(new Obcan(stareRC)));
        if (stareVlastnictvo == null) {
            return "Starý vlastník s RČ. " + stareRC + " nevlastní podieľ na nehnuteľnosti " + sup_c;
        }

        Obcan povodnyVlastnik = stareVlastnictvo.getVlastnik();

        if (novyVlastnik.vlastni(list)) {
            return "Zadaný nový vlastník s RČ. " + noveRC + " už vlastní nehnuteľnosť s č. " + sup_c;
        }

        povodnyVlastnik.odoberVlastnictvo(list);
        Vlastnictvo noveVlastnictvo = new Vlastnictvo(novyVlastnik, stareVlastnictvo.getPodiel());
        list.odoberVlastnictvo(stareVlastnictvo);
        list.pridajVlastnictvo(noveVlastnictvo);
        novyVlastnik.pridajVlastnictvo(list);

        stareVlastnictvo.setVlastnik(novyVlastnik);
        return "Zmena majitela úspešná";

    }

    public String zadanie12_1(String rc, int kat, int list) {
        Obcan novyVlastnik = obcania.findKey(new Obcan(rc));
        if (novyVlastnik == null) {
            return "(F)Občan s rodným číslom " + rc + " neexistuje ";
        }

        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));
        if (kat_u == null) {
            return "(F)Katastrálne územie s číslom " + kat + " neexistuje ";
        }

        List_vlastn list_vl = kat_u.getList(list);
        if (list_vl == null) {
            return "(F)List  s číslom  " + list + " sa v katastri s číslom " + kat + " nenachádza ";
        }
        Vlastnictvo povodneVlastnictvo = list_vl.getPodiely().findKey(new Vlastnictvo(new Obcan(rc)));
        if (povodneVlastnictvo == null) {
            // NOVE VLASTNICTVO
            Vlastnictvo noveVLastnictvo = new Vlastnictvo(novyVlastnik, 0.0);
            novyVlastnik.pridajVlastnictvo(list_vl);
            list_vl.pridajVlastnictvo(noveVLastnictvo);
            return "(S)Vytvorené nové vlastníctvo ,  uprav podiely vlastníkov";

        } else {
            // ZMENA POVODNEHO
//            Vlastnictvo noveVlastnictvo = new Vlastnictvo(novyVlastnik, povodneVlastnictvo.getPodiel());
//            Obcan povodnyVlastnik = povodneVlastnictvo.getVlastnik();
////            povodnyVlastnik.getVlastnictva().remove(povodneVlastnictvo);
//
//            list_vl.odoberVlastnictvo(povodneVlastnictvo);
//            list_vl.pridajVlastnictvo(noveVlastnictvo);
//            novyVlastnik.pridajVlastnictvo(list_vl);
            return "(S)Zmena pôvodného vlastníctva, uprav podiely vlastníkov";

        }

    }

    public ArrayList<String> getPodielyGrid(int kat, int list) {
        ArrayList<String> retArr = new ArrayList<>();
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));

        List_vlastn list_vl = kat_u.getList(list);
        ArrayList<Vlastnictvo> arr = TreeTraversals.getInOrder(list_vl.getPodiely());
        for (Vlastnictvo vlastnictvo : arr) {
            String s = vlastnictvo.getVlastnik().getRod_cislo() + ";" + vlastnictvo.getPodiel();
            retArr.add(s);
        }
        return retArr;
    }

    public String applyPodielyFromGrid(int kat, int list, ArrayList<String> arr) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));

        List_vlastn list_vl = kat_u.getList(list);
        ArrayList<Vlastnictvo> arrV = TreeTraversals.getInOrder(list_vl.getPodiely());
        int podielCount = 0;
        for (Vlastnictvo vlastnictvo : arrV) {
            String rodneCislo = (arr.get(podielCount).split(";"))[0];
            String podiel = (arr.get(podielCount).split(";"))[1];
            Double hodnota = Double.parseDouble(podiel);
            if (hodnota == 0) {
                // ZMAZ PODIEL
                zadanie13_1(rodneCislo, kat, list);
            } else {
                vlastnictvo.setPodiel(hodnota);
            }

            podielCount++;
        }

        return "Nové podiely:\n" + zadanie4(kat, list);

    }

    public String zadanie13_1(String rc, int kat, int list) {
        Obcan povodnyVlastnik = obcania.findKey(new Obcan(rc));
        if (povodnyVlastnik == null) {
            return "(F)Občan s rodným číslom " + rc + " neexistuje ";
        }

        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));
        if (kat_u == null) {
            return "(F)Katastrálne územie s číslom " + kat + " neexistuje ";
        }

        List_vlastn list_vl = kat_u.getList(list);
        if (list_vl == null) {
            return "(F)List  s číslom  " + list + " sa v katastri s číslom " + kat + " nenachádza ";
        }
        Vlastnictvo povodneVlastnictvo = list_vl.getPodiely().findKey(new Vlastnictvo(new Obcan(rc)));

        if (povodneVlastnictvo == null) {
            return "(F)Občan s rodným číslom " + rc + " nemá podieľ na liste vlastníctva s číslom " + list;
        }

        povodnyVlastnik.odoberVlastnictvo(list_vl);
        list_vl.odoberVlastnictvo(povodneVlastnictvo);
        return "(S)Vlastníctvo odstránené, uprav zvyšné podiely";

    }

    public String zadanie14() {
        String retString = "Katastrálne územia utriedené podľa názvu:\n********************************\n";
        ArrayList<Kat_uzemie_str> arr = TreeTraversals.getInOrder(uzemiaNazov);
        for (Kat_uzemie_str kat_uzemie_str : arr) {
            Kat_uzemie kat = kat_uzemie_str.getKat();
            retString += "Názov: " + kat.getNazov() + "   Číslo územia: " + kat.getId() + "\n";

        }
        return retString;
    }

    public String zadanie15(String rc, String meno, String datumNarodenia) {
        Obcan newObcan = new Obcan(rc, meno, new Datum(datumNarodenia), null);
        boolean inserted = obcania.insert(newObcan);
        if (!inserted) {
            return "Občan s rodným číslom " + rc + " sa v systéme už nachádza ";
        } else {
            return "Občan úspešne pridaný";
        }
    }

    public String zadanie16(String kat, int list) {
        Kat_uzemie_str k = uzemiaNazov.findKey(new Kat_uzemie_str(new Kat_uzemie(kat, 0)));
        if (k == null) {
            return "Katastrálne územie s názvom " + kat + " neexistuje ";
        }
        Kat_uzemie kat_u = k.getKat();
        List_vlastn list_vl = new List_vlastn(kat_u, list);
        boolean inserted = kat_u.getListy().insert(list_vl);
        if (!inserted) {
            return "List vlastníctva s ID " + list + " sa v katastrálnom území s názvom " + kat + " už nachádza";
        }
        return "List vlastníctva úspešne pridaný";
    }

    public String zadanie17(int sup_c, int kat, int list) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));
        if (kat_u == null) {
            return "(F)Katastrálne územie s číslom " + kat + " neexistuje ";
        }

        List_vlastn list_vl = kat_u.getList(list);
        if (list_vl == null) {
            return "(F)List  s číslom  " + list + " sa v katastri s číslom " + kat + " nenachádza ";
        }
        Nehnutelnost nehn_v_liste = list_vl.dajNehnutelnost(sup_c);

        Nehnutelnost nehn_v_kat = kat_u.getNehnutelnost(sup_c);

        List_vlastn list_nehn = null;
        if (nehn_v_kat != null) {
            list_nehn = nehn_v_kat.getList();
        }

        if (nehn_v_liste == null && nehn_v_kat == null) {
            // NOVA NEHNUTELNOST
            return "(C)Vytvorenie novej nehnuteľnosti";
        } else if (nehn_v_liste != null) {
            // NEHNUTELNOST UZ JE V ZADANOM LISTE
            return "(F)Nehnuteľnosť s číslom " + sup_c + " sa v liste už nachádza";

        } else if (nehn_v_liste == null && nehn_v_kat != null && list_nehn == null) {
            // NEHNUTELNOST JE V KAT ALE NIE JE V LISTE

            return "(V)" + nehn_v_kat.getSup_cislo();

        } else {
            // NEHNUTELNOST NIE JE V ZADANOM LISTE ALE JE V INOM
            // ZMENA LISTU VLASTNICTVA NEHNUTELNOST
            List_vlastn staryList = nehn_v_kat.getList();

            // NASTAVENIE NOVEHO LISTU
            return "(P)" + staryList.getId();
        }

    }

    public String zadanie17_pridanieNovej(int sup_c, int kat, int list, String adresa, String popis) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));

        List_vlastn list_vl = kat_u.getList(list);
        Nehnutelnost nova = new Nehnutelnost(sup_c, adresa, popis, list_vl);
        kat_u.addNehn(nova);
        list_vl.pridajNehnutelnost(nova);
        return "Nehnuteľnosť úspešne pridaná";

    }

    public String zadanie17_pridanieDoListu(int sup_c, int kat, int newList) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));
        List_vlastn novyList = kat_u.getList(newList);
        Nehnutelnost nehn_v_kat = kat_u.getNehnutelnost(sup_c);
        nehn_v_kat.setList(novyList);
        novyList.pridajNehnutelnost(nehn_v_kat);
        return "Nehnuteľnosť pridaná do listu";
    }

    public String zadanie17_zmenaListu(int sup_c, int kat, int stareCisloListu, int noveCisloListu) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));

        List_vlastn novyList = kat_u.getList(noveCisloListu);

        Nehnutelnost nehn_v_kat = kat_u.getNehnutelnost(sup_c);
        List_vlastn staryList = nehn_v_kat.getList();

        zadanie19(sup_c, kat, staryList.getId()); // ODSTRANI Z POVODNEHO

        novyList.pridajNehnutelnost(nehn_v_kat);
        nehn_v_kat.setList(novyList);

        return "Zmena listu vlastníctva úspešná";
    }

    public String zadanie18(int kat, int oldList, int newList) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));
        if (kat_u == null) {
            return "(F)Katastrálne územie s číslom " + kat + " neexistuje ";
        }
        List_vlastn list_old = kat_u.getList(oldList);
        if (list_old == null) {
            return "(F)List  s číslom  " + oldList + " sa v katastri s číslom " + kat + " nenachádza ";
        }
        List_vlastn list_new = kat_u.getList(newList);
        if (list_new == null) {
            return "(F)List  s číslom  " + newList + " sa v katastri s číslom " + kat + " nenachádza ";
        }

        ArrayList<Nehnutelnost> nehnutelnosti = TreeTraversals.getInOrder(list_old.getNehnutelnosti());
        ArrayList<Vlastnictvo> vlastnictva = TreeTraversals.getInOrder(list_old.getPodiely());

        String retString = "(S)Nehnuteľnosti a vlastníctva z listu č. " + oldList + " boli úspešne prepísané na list č. " + newList;
        int nehnCount = 0;

        // Zmena listu v nehnutelnosti a pridanie nehnutelnosti do listu
        for (Nehnutelnost nehnutelnost : nehnutelnosti) {
            nehnutelnost.setList(list_new);
            list_new.pridajNehnutelnost(nehnutelnost);
            nehnCount++;
        }
        // Priradenie vlastnictiev do noveho listu
        int vlastCount = 0;
        for (Vlastnictvo vlastnictvo : vlastnictva) {
            Obcan vlastnik = vlastnictvo.getVlastnik();
            vlastnik.odoberVlastnictvo(list_old);
            vlastnik.pridajVlastnictvo(list_new);
            list_new.pridajVlastnictvo(vlastnictvo);
            vlastCount++;

        }
        // odobratie stareho listu zo systemu
        kat_u.odoberList(list_old);

        return retString += "\n Počet preradených nehnuteľností : " + nehnCount + "\n Počet preradených podielov : " + vlastCount;

    }

    public String zadanie19(int sup_c, int kat, int list) {
        Kat_uzemie kat_u = uzemia.findKey((new Kat_uzemie("", kat)));
        if (kat_u == null) {
            return "Katastrálne územie s číslom " + kat + " neexistuje ";
        }

        List_vlastn list_vl = kat_u.getList(list);
        if (list_vl == null) {
            return "List  s číslom  " + list + " sa v katastri s číslom " + kat + " nenachádza ";
        }
        Nehnutelnost nehn = list_vl.dajNehnutelnost(sup_c);

        if (nehn == null) {
            return "Na liste vlastníctva č. " + list + " sa nenachádza nehnuteľnosť so súpisným číslom " + sup_c;
        } else {
            // kat_u.odstranNehnutelnost(nehn);
            list_vl.odoberNehnutelnost(nehn);
            nehn.setList(null);
            return "Nehnuteľnosť odstránená";
        }

    }

    public String zadanie20(int kat, String nazov) {
        Kat_uzemie uz = new Kat_uzemie(nazov, kat);
        Kat_uzemie_str uzs = new Kat_uzemie_str(uz);
        if (!uzemia.insert(uz)) {
            return "Katastrálne územie s ID. " + kat + " sa v systéme už nachádza";
        }
        if (!uzemiaNazov.insert(uzs)) {
            return "Katastrálne územie s názvom. " + nazov + " sa v systéme už nachádza";
        }
        return "Katatstrálne územie úspešne vytvorené";

    }

    public String zadanie21(int oldKat, int newKat) {
        Kat_uzemie kat_old = uzemia.findKey((new Kat_uzemie("", oldKat)));

        if (kat_old == null) {
            return "Katastrálne územie s číslom " + oldKat + " neexistuje ";
        }
        Kat_uzemie kat_new = uzemia.findKey((new Kat_uzemie("", newKat)));
        if (kat_new == null) {
            return "Katastrálne územie s číslom " + newKat + " neexistuje ";
        }
        uzemia.delete(kat_old);
        uzemiaNazov.delete(new Kat_uzemie_str(kat_old));

        ArrayList<Nehnutelnost> oldNehnutelnosti = TreeTraversals.getInOrder(kat_old.getNehnutelnosti());
        ArrayList<List_vlastn> oldListy = TreeTraversals.getInOrder(kat_old.getListy());
        int countListy = 0;
        int countNehn = 0;
        int maxID_list = kat_new.getMaxID_listy() + 1;
        int maxID_nehn = kat_new.getMaxID_nehnutelnosti() + 1;
        String returnString = "Agenda preradená:\n";
        for (List_vlastn list_vlastn : oldListy) {
            if (kat_new.getListy().containsKey(list_vlastn)) {
                returnString += "List vlastníctva s číslom " + list_vlastn.getId() + " sa v novom katastrálnom území už nachádzal, nové číslo listu je: " + maxID_list + "\n";
                list_vlastn.setId(maxID_list);
                maxID_list++;
            }
            list_vlastn.setKat(kat_new);
            kat_new.addList(list_vlastn);
            countListy++;

        }
        for (Nehnutelnost nehnutelnost : oldNehnutelnosti) {

            if (kat_new.getNehnutelnosti().containsKey(nehnutelnost)) {
                returnString += "Nehnutelnosť so súpisným číslom" + nehnutelnost.getSup_cislo() + " sa v novom katastrálnom území už nachádzala, nové súpinsé číslo je: " + maxID_nehn + "\n";
                nehnutelnost.setSup_cislo(maxID_nehn);

                maxID_nehn++;
            }
            kat_new.addNehn(nehnutelnost);
            nehnutelnost.setKataster(kat_new);
            countNehn++;

        }
        return returnString += "\n\n Počet preradených listov: " + countListy + "\n Počet prereadených nehnuteľností: " + countNehn;

    }

    /**
     * Uloží momentálný stav stromov(databázy)
     *
     * @see InstanceSaverLoader
     * @return
     */
    public String saveInstance() {
        return InstanceSaverLoader.saveState(uzemiaNazov, obcania);

    }

}
