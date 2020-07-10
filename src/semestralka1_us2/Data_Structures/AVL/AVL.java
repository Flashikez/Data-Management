/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Data_Structures.AVL;

import java.util.ArrayList;
import semestralka1_us2.Data_Structures.BinarySearchTree.BST;
import semestralka1_us2.Data_Structures.BinarySearchTree.TreeTraversals;
import semestralka1_us2.Data_Structures.Node.Node;

/**
 * AVL strom 
 * @author MarekPC
 * 
 */
public class AVL<K extends Comparable<K>> extends BST<K> {


//    public AVL() {
//        super();
//    }
    /**
     * Vloží prvok do stromu, po vložení sa výšky podstromov kontrolujú v metóda updateHeights_afterInsert
     * 
     * @param data - dáta na vloženie
     * @return true ak bol prvok vložený, false ak nebol
     */
    @Override
    public boolean insert(K data) {
        Node<K> inserted = super.put(data);
        if (inserted == null) {
            return false;
        } else {
            checkHeights_AfterInsert(inserted);
            return true;
        }
    }
    /**
     * Odoberie prvok zo stromu klasicky pomocou zdedenej metódy BST, po odobratí sa výšky podstromov kontrolujú v metóde updateHeights_afterDelete
     * 
     */
    @Override
    public void delete(K key) {
        Node<K> r = super.delete(root, key);
        if (r != null) 
            checkHeights_AfterDelete(r);
        
        
    }


    /**
     * Aktualizovanie výšiek podstromov predkov current a kontrola balance faktorov
     * @param current - vložená Node 
     */
    public void checkHeights_AfterInsert(Node current) {
        // Priamo v inserte sa výšky podstromov nemenia, nastavujú sa až tu!

        // Node na začiatku je pridaný prvok,!
        while (current.parent != null) {

            // Ak current je v lavo od parenta, tak nastav parentovu lavú výšku
            if (compareNodes(current, current.parent) < 0) {
                current.parent.leftSubtreeHeight = 1 + current.getMaxHeight();
            } else {
                // Ak current je v pravo od parenta, tak nastav pravú výšku
                current.parent.rightSubtreeHeight = 1 + current.getMaxHeight();
            }
            
            int bFactor = current.parent.getBalanceFactor();
            if (Math.abs(bFactor) > 1) {
                balanceNode(current.parent, bFactor);
                // Po vybalancovaní nejakej nody, sa výšky nodov od neho hore nemusia prepočítavať pretože ostanú zachované, takže insert končí
                break;

            }

            current = current.parent;
        }

    }
    /**
     * Aktualizácia výšok predkov odobranej Nody, a kontrola balance faktorov
     * @param current - rodič odobranej Nody
     */
    public void checkHeights_AfterDelete(Node current) {

        boolean balancedOnPreviousNode = false;
        // Current je na začiaku parent vymazaného prvku, ak mal mazaný dve deti, tak current je parent inOrder successora mazaného prvku
        // Priamo v delete sa výška podstromu mení len pre rodiča mazaného prvku, teda pre current 

        while (current != null) {
            // Ak current je root, nemožno nastaviť výšku jeho parenta
            if (current != root) {
                //Ak je current vpravo od parenta, nastav výšku parentovho pravého podstromu
                if (compareNodes(current, current.parent) > 0) {
                    //Ale ak prebehla rotácia na predchádzajúcom prvku, a current.parent má už správne nastavenú výšku, nemá zmysel pokračovať, delete skončí
                    if (current.getMaxHeight() + 1 == current.parent.rightSubtreeHeight && balancedOnPreviousNode) {
//                        
                        return;
                    }
                    current.parent.rightSubtreeHeight = 1 + current.getMaxHeight();
                    //Ak je current vlavo od parenta, nastav výšku parentovho lavého podstromu
                } else {
                    //Ale ak prebehla rotácia na predchádzajúcom prvku, a current.parent má už správne nastavenú výšku, nemá zmysel pokračovať, delete skončí
                    if (current.getMaxHeight() + 1 == current.parent.leftSubtreeHeight && balancedOnPreviousNode) {
//                        
                        return;
                    }
                    current.parent.leftSubtreeHeight = 1 + current.getMaxHeight();
                }

            }
            int bFactor = current.getBalanceFactor();
            if (Math.abs(bFactor) > 1) {
                balanceNode(current, bFactor);
                balancedOnPreviousNode = true;
            } else {
                balancedOnPreviousNode = false;
            }

            current = current.parent;

        }
    }

    public int compareNodes(Node<K> first, Node<K> second) {
        return first.key.compareTo(second.key);
    }
    /**
     * Metóda,ktorá na základe balance faktoru danej Nody, spraví potrebné rotácie aby boli splnené podmienky AVL stromu
     * @param node Node, ktorá sa má balancovať
     * @param b_factor balance faktor node
     */
    private void balanceNode(Node<K> node, int b_factor) {
        // Ľavý podstrom je väčší
        if (b_factor > 1) {

            // = v podmienke je pre niektoré prípady pri delete
            if (node.left.getBalanceFactor() >= 0) {//RR-ROTATE

                node = rotateRight(node);
            } else { // L-R rotate
                // LEFT ROTATE LAVEHO SYNA
                node.left = rotateLeft(node.left);
                // A pravý rotate na celú node
                node = rotateRight(node);
            }
            // Pravý podstrom väčší
        } else {
            // = v podmienke je pre niektoré prípady pri delete

            if (node.right.getBalanceFactor() <= 0) { //LL-ROTATE

                node = rotateLeft(node);
            } else { // R-L rotate
                // RIGHT ROTATE pravého syna
                node.right = rotateRight(node.right);

                node = rotateLeft(node);
            }

        }
        // kedže rotácie vracajú nového rodiča rotácie pozrieme či náhodou nemá byť novým rootom
        if (node.parent == null) {
            root = node;
        }

    }

    protected Node<K> rotateRight(Node<K> node) {
        Node<K> newSubRoot = node.left;

        newSubRoot.parent = node.parent;

        node.parent = newSubRoot;

        if (newSubRoot.right != null) {
            newSubRoot.right.parent = node;
        }

        node.left = newSubRoot.right;
        node.leftSubtreeHeight = newSubRoot.rightSubtreeHeight;

        newSubRoot.right = node;
        newSubRoot.rightSubtreeHeight = 1 + node.getMaxHeight();

        if (newSubRoot.parent != null) {
            if(compareNodes(newSubRoot.parent, newSubRoot) < 0){
//            if (newSubRoot.parent.key.compareTo(newSubRoot.key) < 0) {
                newSubRoot.parent.right = newSubRoot;

            } else {
                newSubRoot.parent.left = newSubRoot;

            }

        }

        return newSubRoot;
    }

    protected Node<K> rotateLeft(Node<K> node) {
        Node<K> newSubRoot = node.right;

        newSubRoot.parent = node.parent;

        node.parent = newSubRoot;

        if (newSubRoot.left != null) {
            newSubRoot.left.parent = node;
        }
        node.right = newSubRoot.left;
        node.rightSubtreeHeight = newSubRoot.leftSubtreeHeight;

        newSubRoot.left = node;
        newSubRoot.leftSubtreeHeight = 1 + node.getMaxHeight();

        if (newSubRoot.parent != null) {
            if(compareNodes(newSubRoot.parent, newSubRoot) < 0){
//            if (newSubRoot.parent.key.compareTo(newSubRoot.key) < 0) {
                newSubRoot.parent.right = newSubRoot;

            } else {
                newSubRoot.parent.left = newSubRoot;

            }
        }
        return newSubRoot;

    }
     public void printTreeWithInfo() {

        printTree(root, 0);
        ArrayList<Node<K>> arr = TreeTraversals.getInorderNodes(this);
        for (Node<K> node : arr) {

            System.out.println(node + "  LEFTH: " + node.leftSubtreeHeight + " RIGHTH: " + node.rightSubtreeHeight);
            //  kniha.realHeightsCheck();

        }

    }

    public void checkHeights() {
        ArrayList<Node<K>> arr = TreeTraversals.getInorderNodes(this);
        for (Node<K> node : arr) {
            node.realHeightsCheck();
        }
//        System.out.println("******HEIGHTS OK*****");
    }

}
