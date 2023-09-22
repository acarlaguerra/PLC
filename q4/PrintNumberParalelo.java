package q4;

import java.util.Random;

public class PrintNumberParalelo {
    public static void main(String args[]) {
        long startTime = System.currentTimeMillis();
        Thread[] threads = new Thread[50];
        BST tree = new BST();
        int i;

        for (i = 0; i < 50; i++) {
            threads[i] = new ArvoreBusca(tree);
        }

        for (i = 0; i < 50; i++) {
            threads[i].start();
        }

        for (i = 0; i < 50; i++) {
            try {
                threads[i].join();
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

class ArvoreBusca extends Thread {
    private Random random = new Random();
    private BST tree;

    ArvoreBusca(BST arvore) {
        this.tree = arvore;
    }

    public void run() {
        int i;
        for (i = 0; i < 1000; i++) {
            int randomInt = random.nextInt(2000);
            tree.insert(randomInt);

            randomInt = random.nextInt(2000);
            tree.remove(randomInt);
        }
    }
}

class BST {
    private int nodeCount = 0;
    private Node root;

    public int getNodeCount() {
        return nodeCount;
    }

    class Node {
        int key;
        Node left;
        Node right;
        private final Object lock = new Object(); // Travamento em nível de nó

        Node(int num) {
            key = num;
            left = null;
            right = null;
        }

        void insert(int key) {
            synchronized (lock) {
                if (key < this.key) {
                    if (left == null) {
                        left = new Node(key);
                    } else {
                        left.insert(key);
                    }
                } else if (key > this.key) {
                    if (right == null) {
                        right = new Node(key);
                    } else {
                        right.insert(key);
                    }
                }
            }
        }

        Node remove(int key) {
            synchronized (lock) {
                if (key < this.key) {
                    if (left != null) {
                        left = left.remove(key);
                    }
                } else if (key > this.key) {
                    if (right != null) {
                        right = right.remove(key);
                    }
                } else {
                    if (left == null) {
                        return right;
                    } else if (right == null) {
                        return left;
                    }

                    this.key = right.minValue();
                    right = right.remove(this.key);
                }
                return this;
            }
        }

        int minValue() {
            if (left == null) {
                return key;
            }
            return left.minValue();
        }
    }

    BST() {
        root = null;
    }

    BST(int value) {
        root = new Node(value);
    }

    void insert(int key) {
        if (root == null) {
            root = new Node(key);
        } else {
            root.insert(key);
        }
        incrementNodeCount();
    }

    void remove(int key) {
        if (root != null) {
            root = root.remove(key);
            decrementNodeCount();
        }
    }

    private void incrementNodeCount() {
        nodeCount++;
    }

    private void decrementNodeCount() {
        nodeCount--;
    }
}
