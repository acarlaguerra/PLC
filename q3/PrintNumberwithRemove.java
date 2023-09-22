package q3;

import java.util.Random;
import java.lang.System;

class PrintNumberwithRemove {
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
            
            randomInt = random.nextInt(2000);
            tree.remove(randomInt);
        }
    }
}

class BST {
    private int nodeCount = 0;
    Node root;
    private final Object lock = new Object();

    public synchronized void incrementNodeCount() {
        nodeCount++;
    }
    public synchronized void decrementNodeCount() {
        nodeCount--;
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

    void insert(int key) {
        synchronized (lock) {
            root = insertRec(root, key);
            incrementNodeCount();
        }
        
    }

    void remove(int key){
        synchronized (lock){
        root = removeRec(root, key);
        decrementNodeCount();
        }
    }

    int minValue(Node root) {
    int minValue = root.key;
    while (root.left != null) {
        minValue = root.left.key;
        root = root.left;
    }
    return minValue;
    }

    Node removeRec(Node root, int key) {
    if (root == null) {
        return root;
    }

    if (key < root.key)
        root.left = removeRec(root.left, key);
    else if (key > root.key)
        root.right = removeRec(root.right, key);
    else {
        if (root.left == null)
            return root.right;
        else if (root.right == null)
            return root.left;

        root.key = minValue(root.right);
        root.right = removeRec(root.right, root.key);
    }

    return root;
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
