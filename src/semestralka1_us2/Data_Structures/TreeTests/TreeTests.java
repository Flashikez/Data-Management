/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Data_Structures.TreeTests;

import semestralka1_us2.Data_Structures.AVL.AVL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import semestralka1_us2.Data_Structures.BinarySearchTree.TreeTraversals;

/**
 *
 * Trieda obsahujúca testy pre AVL strom
 * @author MarekPC
 */
public class TreeTests {


    /**
     * Test AVL stromu<br>
     * Spraví zadaný počet operácií (insert alebo delete) so zadanou pravdepodobnosťou operácie insert
     * Súčasne si drží ArrayList nad ktorými robí operácie (add alebo remove)<br>
     * Po vykonaní zadaného počtu operácií vypíše na konzolu inOrder poradie prvkov stromu a takisto utriedených prvkov v ArrayListe, poradie týchto prvkov by malo byť rovnaké
     * Na konci zavolá metódu, checkHeights, ktorá skontroluje výšky podstromov jednotlivých Nodov v strome a ich balance faktory.<br> Ak narazí na porušenie pravidiel AVL stromu, program sa zastaví System.exit()
     * 
     * @param tree AVL strom, ktorý sa testuje
     * @param reps počet operácií
     * @param insertChance pravdepodobnosť operácie insert
     */
    public static void test(AVL tree, int reps, double insertChance) {
        ArrayList<Integer> arr = new ArrayList<>();

        Random rand = new Random();

        int min = -10000;
        int max = 20000;
        int pocetOperacii = reps;
        for (int i = 0; i < pocetOperacii; i++) {

            if (rand.nextDouble() < insertChance) {
                int rando = rand.nextInt((max - min) + 1) + min;

                Integer k = new Integer(rando);

                tree.insert(k);
                boolean there = false;

                for (Integer kniha : arr) {
                    if (kniha.compareTo(k) == 0) {
                        there = true;
                        break;
                    }

                }
                if (!there) {
                    arr.add(k);
                } 
            } else {
                if (arr.size() > 0) {

                    Integer k = arr.remove(rand.nextInt(arr.size()));
//                    System.out.println(i + ". DELETING " + k + "\n");
                    tree.delete(k);
                }
            }

        }
//        tree.printTree();
        ArrayList<Integer> treeArr = TreeTraversals.getInOrder(tree);
        System.out.println("\nTREE NODES COUNT :" + treeArr.size() + "\n");
        for (Integer integer : treeArr) {
            System.out.print(integer + "   ");
        }
         System.out.println("\n");
        System.out.println("\nARRAY NODES COUNT :" + arr.size() + "\n");
        if (treeArr.size() != arr.size()) {
            System.out.println("!!!!!!! TEST FAILED !!!!!!!!!");
            System.exit(0);
        }
        System.out.println("******** TEST ARRAY SORTED *********\n");
        Collections.sort(arr);
        for (Integer integer : arr) {
            System.out.print(integer + "   ");
        }
        System.out.println("\n");
        tree.checkHeights();
    }
}
