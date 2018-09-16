package com;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static List<Node<Board>> solutions;

    public static void attachEveryNextState(Node<Board> root){
        int size = root.getData().getSize();
        for (int i = 1; i <= size; i++){
            for (int j = 1; j <= size; j++){
                Board nextState = root.getData().getDuplicate();
                if (nextState.insertQueen(i,j)) {
                    Node<Board> node = new Node<>(nextState);
                    int queensCount = nextState.getQueensCount();
                    //nextState = null;
                    //node.setParent(root);
                    root.addChild(node);
                    if (queensCount == 8){
                        solutions.add(node);
                    }
                }
            }
        }
    }

    static void recursiveFill(Node<Board> root){
        attachEveryNextState(root);
        for (Node n : root.getChildren()) {
            recursiveFill(n);
        }
    }

    static void nonRecursiveFill(Node<Board> root){
        attachEveryNextState(root);
        System.out.println("Finished second level...");

        for (Node<Board> n : root.getChildren())
            attachEveryNextState(n);
        System.out.println("Finished second level...");

        for (Node<Board> n : root.getChildren())
            for (Node<Board> j : n.getChildren())
                attachEveryNextState(j);
        System.out.println("Finished third level...");
    }

    public static void main(String[] args){
        float startTime = 0, finishTime = 0;
        startTime = System.nanoTime();
        solutions = new ArrayList<>();
        Board board = new Board(8);
        Node<Board> root = new Node<>(board);
        System.out.println("Calculating every state...");
        //recursiveFill(root);
        nonRecursiveFill(root);
        for (Node<Board> n : solutions) {
            System.out.println(n.getData().getFormattedBoard());
            System.out.println();
        }
        finishTime = System.nanoTime();
        System.out.printf("Time: %.0f%n", (finishTime-startTime) );/// 1000000000);
     }
}
