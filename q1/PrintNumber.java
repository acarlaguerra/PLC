package q1;
import java.util.Random;

import java.lang.System;

class PrintNumber {
    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        Thread[] thr = new Thread[50];
        BST tree = new BST();
        int i;

    for (i = 0; i < 50; i++) {
        thr[i] = new ArvoreBusca(tree);
    }    

    for (i = 0; i < 50; i++) {
        thr[i].start();
    }

    for (i = 0; i < 50; i++){
      try {
        thr[i].join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    long endTime = System.currentTimeMillis();
    long executionTime = endTime - startTime;

    System.out.println("Tempo de execução: " + executionTime + " milissegundos");
    System.out.println("Número total de nós da árvore: " + tree.getNodeCount()); 
    }
}

class ArvoreBusca extends Thread{
    Random random = new Random();
    BST tree;

    ArvoreBusca(BST arvore){
        this.tree = arvore;
    }

    public void run() {
        int i;
        for (i = 0; i < 2000; i++){
            int randomInt = random.nextInt(2000);
            this.tree.insert(randomInt);
            // System.out.println("Thread: " + this.tree + " - Valor: " + randomInt);
        }
    }
}

class BST {
    private int nodeCount = 0;
    Node root;


    public synchronized void incrementNodeCount() {
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

    BST(int value) {
        root = new Node(value);
    }

    synchronized void insert(int key) {
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
