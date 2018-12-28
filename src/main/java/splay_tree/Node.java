package splay_tree;

public class Node<V> {

    public Node<V> left;
    public Node<V> right;

    public V value;

    public Node(V value) {
        this.value = value;
        left = null;
        right = null;
    }
}
