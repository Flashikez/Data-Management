/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestralka1_us2.Data_Structures.BinarySearchTree;

import semestralka1_us2.Data_Structures.Node.Node;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Trieda, ktorá vykonáva prehliadky BST stromu
 * @author MarekPC
 */
public class TreeTraversals {

   

    public static void printLevelOrder(BST tree) {
        if (tree.root == null) {
            System.out.println("******** Tree is empty **********");
            return;
        }
        System.out.println("******** LEVEL ORDER **********\n");
        Queue<Node<?>> q = new LinkedList<>();
        q.add(tree.root);
        while (!q.isEmpty()) {
            Node<?> current = q.remove();
            System.out.print(current.key + "   ");
            if (current.left != null) {
                q.add(current.left);
            }
            if (current.right != null) {
                q.add(current.right);
            }
        }
        System.out.println("\n");
    }

    public static <K extends Comparable> ArrayList<K> getInOrder(BST tree) {
        ArrayList<K> arr = new ArrayList<>();
        if (tree.root == null) {

            return arr;
        }

        Stack<Node<?>> stack = new Stack<>();
        Node<?> current = tree.root;
        while (current != null || stack.size() > 0) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            arr.add((K) current.key);
            current = current.right;
        }

        return arr;

    }

    public static <K extends Comparable<K>> ArrayList<Node<K>> getInorderNodes(BST tree) {
        ArrayList<Node<K>> arr = new ArrayList<>();

        if (tree.root == null) {
            return arr;
        }

        Stack<Node<K>> stack = new Stack<>();
        Node<K> current = tree.root;
        while (current != null || stack.size() > 0) {

            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            current = stack.pop();
            arr.add(current);
            current = current.right;
        }

        return arr;

    }

}
