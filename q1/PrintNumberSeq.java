package q1;

import java.util.Random;
import java.lang.System;


public class PrintNumberSeq {
    long startTime = System.currentTimeMillis();
    Random random = new Random();
    BST tree = new BST();


    PrintNumberSeq(BST arvore){
        this.tree = arvore;
    }

    public void run() {
        int i;
        for (i = 0; i < 100000; i++){
            int randomInt = random.nextInt(2000);
            this.tree.insert(randomInt);
        }

        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;

        System.out.println("Tempo de execução: " + executionTime + " milissegundos");
        System.out.println("Número total de nós da árvore: " + tree.getNodeCount()); 
    }

public static void main(String[] args) {
    BST tree = new BST();
    PrintNumberSeq printer = new PrintNumberSeq(tree);

    executeSequentially(printer);
}

public static void executeSequentially(PrintNumberSeq printer) {
    printer.run();
}
}

class BST {
    private int nodeCount = 0;
    Node root;


    public void incrementNodeCount() {
        nodeCount++;
    }

    public int getNodeCount() {
        return nodeCount;
    }

    
    class Node {
        int key;
        Node left;
        Node right;

        Node (int num){
            key = num;
            left = null;
            right = null;
        }
    }

    BST() {
        root = null;
    }

    BST (int value) {
        root = new Node(value);
    }

    public void insert(int key) {
        root = insertRec(root, key);
        incrementNodeCount();
    }

    Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }

        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        return root;

    
    }

    public int Treelength() {
        return lengthRec(root);
    }

    public int lengthRec(Node root) {
        if (root == null) {
            return 0;
        }

        return lengthRec(root.left) + lengthRec(root.right) + 1;
    }
}

