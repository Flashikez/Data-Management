/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Data_Structures.Node;

/**
 * Trieda reprezentujúca vrchol stromu
 * @author MarekPC
 * 
 */
public class Node<K extends Comparable<K>> {
    /**
     * Kľúč implementujúci metódu compareTo na základe, ktorej prebieha porovnávanie vrcholov v strome
     */
    public K key;
    public Node<K> left, right, parent;
    /**
     * Výšky lavého a pravého podstromu
     */
    public int leftSubtreeHeight, rightSubtreeHeight;

    public Node(K key) {
        this.key = key;
        leftSubtreeHeight = rightSubtreeHeight = 0;
    }
    public int getMaxHeight(){
        return Math.max(leftSubtreeHeight,rightSubtreeHeight);
    }
    /**
     * Počíta balance faktor = výška lavého podstromu - výška pravého podstromu
     * @return balance faktor 
     */
    public int getBalanceFactor() {
        return leftSubtreeHeight - rightSubtreeHeight;
    }
    
    /**
     * Funkcia na testovacie účely, spočíta reálne výšky podstromov a porovná ich s uloženými premennými, ak nájde nezhodu, ukončí program<br>
     * Takisto kontroluje aj balanceFactor
     */
    public void realHeightsCheck() {
        int leftHeight = 0;
        int rightHeight = 0;
        if(left !=null){
           leftHeight =  left.getSubtreeHeight();
            if(leftSubtreeHeight != leftHeight){
                System.out.println("***** KEY: "+key +" LEFTH WRONG MY: "+leftSubtreeHeight+" =! "+leftHeight+" *************\n");
                System.exit(leftHeight);
            }
        }
         if(right !=null){
           rightHeight =  right.getSubtreeHeight();
            if(rightSubtreeHeight != rightHeight){
                System.out.println("***** KEY: "+key +" RIGHTH WRONG MY: "+rightSubtreeHeight+" =! "+rightHeight+" *************\n");
                System.exit(rightHeight);
            }
        }
         // Kontrola Balance Factorov
         if(Math.abs(leftHeight-rightHeight)>1){
             System.out.println("BALANCE FACTOR RULE VIOLATED FOR NODE: "+key+" BF IS: " +(leftHeight-rightHeight));
             System.exit(rightHeight);
         }
            
    }
    /**
     * Rekurzívna metóda na vypočítanie reálnej výšky podstromu, použitá len v testoch
     * @return výška podstromu 
     */
    private int getSubtreeHeight() {
//        System.out.println("SUBHEIGHT FOR "+key);

        int leftSubTree = 1;
        int rightSubTree = 1;
        if (left != null) {
            leftSubTree += left.getSubtreeHeight();
        }
        if (right != null) {
            rightSubTree += right.getSubtreeHeight();
        }

        return Math.max(leftSubTree, rightSubTree);
    }

    @Override
    public String toString() {
        return " KEY " + key + " PARENT: " + ((parent == null) ? "null" : parent.key) + "  LEFT: " + ((left == null) ? "null" : left.key) + "  RIGHT: " + ((right == null) ? "null" : right.key);
    }

}
