package com;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T data;
    private Node<T> parent;
    private List<Node<T>> children;
    private int level = 0;

    Node(T data){
        this.data = data;
        children = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getParent() {
        return parent;
    }

    private boolean setParent(Node<T> parent) {
        if (parent == null) return false;
        this.parent = parent;
        this.level = parent.getLevel() + 1;
        return true;
    }

    public boolean addChild(Node<T> child){
        if (child == null) return false;
        this.children.add(child);
        child.setParent(this);
        return true;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public int getLevel() {
        return level;
    }
}
