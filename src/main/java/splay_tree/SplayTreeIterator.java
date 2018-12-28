package splay_tree;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class SplayTreeIterator<V extends Comparable> implements Iterator<V> {

    private SplayTree splayTree;
    private Stack<Node<V>> stack;

    public SplayTreeIterator(SplayTree splayTree) {

        this.splayTree = splayTree;

        stack = new Stack<>();
        Node<V> root = this.splayTree.getRoot();
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public V next() {
        if (!hasNext()) throw new NoSuchElementException();

        Node<V> smallest = stack.pop();
        if (smallest.right != null) {
            Node<V> node = smallest.right;
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
        }
        return smallest.value;
    }

    @Override
    public void remove() {
        splayTree.remove(next());
    }
}