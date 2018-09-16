package com;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {
    static Set<Node<Board>> solutions;

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
        if (root.getData().getQueensCount() >= 8) {
            return;
        }
        attachEveryNextState(root);
        for (Node<Board> n : root.getChildren()) {
            if (n.getData().getQueensCount() < 8) {
                recursiveFill(n);
            }
            else
                System.out.println(n.getData().getFormattedBoard());
        }
    }

    public static void main(String[] args){
        float startTime = 0, finishTime = 0;
        startTime = System.nanoTime();
        solutions = new HashSet<>();
        Board board = new Board(8);
        Node<Board> root = new Node<>(board);
        System.out.println("Calculating every state...");
        recursiveFill(root);
        finishTime = System.nanoTime();
        System.out.printf("Time: %.0f%n", (finishTime-startTime) );// / 1000000000);

        for (Node<Board> n : solutions) {
            System.out.println(n.getData().getFormattedBoard());
            System.out.println();
        }
     }
}
