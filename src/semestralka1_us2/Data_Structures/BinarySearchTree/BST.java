/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Data_Structures.BinarySearchTree;

import java.util.ArrayList;
import semestralka1_us2.Data_Structures.Node.Node;

/**
 * Binárny vyhladávací strom, predok AVL
 * @author MarekPC
 * 
 */
public class BST<K extends Comparable<K>> {

    protected Node<K> root;

    public BST() {
        root = null;
    }

    public boolean insert(K data) {
        Node<K> ret = put(data);
        if (ret == null) {
            return false;
        } else {
            return true;
        }

    }

    // Vráti vytvorenú NODE
    protected Node<K> put(K data) {
        Node<K> nodeToInsert = new Node<K>(data);

        if (root == null) {
            root = nodeToInsert;
            return root;
        }

        Node<K> currentNode = root;
        Node<K> parentOfCurrent = null;
        while (currentNode != null) {
            int result = data.compareTo(currentNode.key);
            if (result < 0) {
                parentOfCurrent = currentNode;
                currentNode = currentNode.left;
            } else if (result > 0) {
                parentOfCurrent = currentNode;
                currentNode = currentNode.right;
            } else {
                // Klúč už je v strome
                return null;
            }

        }
        if (data.compareTo(parentOfCurrent.key) < 0) {
            parentOfCurrent.left = nodeToInsert;
            //  parentOfCurrent.leftSubtreeHeight = 1;
            nodeToInsert.parent = parentOfCurrent;
        } else {
            parentOfCurrent.right = nodeToInsert;
            //parentOfCurrent.rightSubtreeHeight = 1;
            nodeToInsert.parent = parentOfCurrent;
        }
        return nodeToInsert;
    }

    public K getMaxKey() {
        if (root == null) {
            return null;
        }
        Node<K> parent = root;
        Node<K> current = root.right;
        while (current != null) {
            parent = current;
            current = current.right;
        }
        return parent.key;

    }

    public void delete(K key) {
        
        Node<K> r = delete(root, key);

    }

    // Vráti rodiča zmazeného prvku
    protected Node<K> delete(Node<K> startOfSearch, K key) {
        Node<K> nodeToDelete = null;

        Node<K> parentOfnodeToDelete = startOfSearch.parent;

        Node<K> currentNode = startOfSearch;

        while (currentNode != null) {
            if (key.compareTo(currentNode.key) == 0) {
                nodeToDelete = currentNode;
                break;
            } else if (key.compareTo(currentNode.key) > 0) {
                parentOfnodeToDelete = currentNode;
                currentNode = currentNode.right;
            } else {
                parentOfnodeToDelete = currentNode;
                currentNode = currentNode.left;
            }
        }
        // Nenajdena
        if (nodeToDelete == null) {
            return null;
        }
        // Mažem koreň
        if (nodeToDelete == root) {
            if (nodeToDelete.left == null && nodeToDelete.right == null) { // Žiadne dieta
                root = null;
                return root;
            } else if (nodeToDelete.left != null && nodeToDelete.right != null) { // Dve deti
                Node<K> successor = getInOrderSuccessor(nodeToDelete.right);
                nodeToDelete.key = (K) successor.key;
                // Len jedna rekurzia
                return delete(nodeToDelete.right, successor.key);

            } else { // Jedno dieta
                if (nodeToDelete.left == null) { // Pravé
                    root = nodeToDelete.right;
                    nodeToDelete.right.parent = null;
                } else {
                    root = nodeToDelete.left; // Lavé
                    nodeToDelete.left.parent = null;
                }
                return root;
            }

            // Nemažem koreň
        } else {
            boolean isLeftOfParent = (parentOfnodeToDelete.left == nodeToDelete);

            if (nodeToDelete.left == null && nodeToDelete.right == null) { // Žiadne dieta
                if (isLeftOfParent) {
                    parentOfnodeToDelete.left = null;
                    parentOfnodeToDelete.leftSubtreeHeight = 0;
                } else {
                    parentOfnodeToDelete.right = null;
                    parentOfnodeToDelete.rightSubtreeHeight = 0;
                }

                return parentOfnodeToDelete;

            } else if (nodeToDelete.left != null && nodeToDelete.right != null) { // Dve deti
                Node<K> successor = getInOrderSuccessor(nodeToDelete.right);
                nodeToDelete.key = (K) successor.key;
                // Len jedna rekurzia!
                return delete(nodeToDelete.right, successor.key);

            } else if (nodeToDelete.left == null) { // Jedno dieta prave
                if (isLeftOfParent) { // Ak node na zmazanie je v lavo od jeho rodica
                    parentOfnodeToDelete.left = nodeToDelete.right;
                    parentOfnodeToDelete.leftSubtreeHeight = nodeToDelete.getMaxHeight();
                } else {
                    parentOfnodeToDelete.right = nodeToDelete.right;
                    parentOfnodeToDelete.rightSubtreeHeight = nodeToDelete.getMaxHeight();

                }
                nodeToDelete.right.parent = parentOfnodeToDelete;
                return parentOfnodeToDelete;
            } else { // Jedno dieta lave
                if (isLeftOfParent) { // Ak node na zmazanie je v lavo od jeho rodica
                    parentOfnodeToDelete.left = nodeToDelete.left;
                    parentOfnodeToDelete.leftSubtreeHeight = nodeToDelete.getMaxHeight();
                } else {
                    parentOfnodeToDelete.right = nodeToDelete.left;
                    parentOfnodeToDelete.rightSubtreeHeight = nodeToDelete.getMaxHeight();

                }
                nodeToDelete.left.parent = parentOfnodeToDelete;
                return parentOfnodeToDelete;
            }
        }

    }

    protected Node<K> getInOrderSuccessor(Node<K> r) {

        Node<K> current = r;
        Node<K> parentOfCurr = r.parent;
        while (current != null) {
            parentOfCurr = current;
            current = current.left;
        }
        return parentOfCurr;

//        if (r.left != null) {
//            return getInOrderSuccessor(r.left);
//        } else {
//            return r;
//        }
    }

    public K findKey(K key) {
        Node<K> r = (searchForKey(key));
        if (r == null) {
            return null;
        } else {
            return r.key;
        }
    }

    public boolean containsKey(K key) {
        return (searchForKey(key)) != null;
    }

    protected Node<K> searchForKey(K key) {
        if (root == null) {
            return null;
        }
        Node<K> currentNode = root;
        while (currentNode != null) {
            int result = key.compareTo(currentNode.key);
            if (result < 0) {
                currentNode = currentNode.left;
            } else if (result > 0) {
                currentNode = currentNode.right;
            } else {
                // KEY FOUND
                return currentNode;
            }
        }
        return null;
    }

    protected void printTree(Node<K> root, int level) {
        if (root == null) {
            return;
        }
        printTree(root.right, level + 1);
        if (level != 0) {
            for (int i = 0; i < level - 1; i++) {
                System.out.print("|\t");
            }
            System.out.println("|------- " + root.key);
        } else {
            System.out.println(root.key);
        }
        printTree(root.left, level + 1);
    }

    public void printTree() {

        printTree(root, 0);

    }

   

}
