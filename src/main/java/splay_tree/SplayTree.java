package splay_tree;

import java.util.*;
import java.util.Set;

public class SplayTree<V extends Comparable<V>> implements Set<V> {

    private int size;
    private Node<V> root;

    public SplayTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        if (root == null) return false;
        V v = (V) o;
        root = splay(root, v);
        return root.value.equals(v);
    }

    public Iterator<V> iterator() {
        return new SplayTreeIterator<>(this);
    }

    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (V v : this) {
            array[i] = v;
            i++;
        }
        return array;
    }

    public <T> T[] toArray(T[] a) {
        return (T[]) this.toArray();
    }

    public boolean add(V v) {
        if (root == null) {
            root = new Node<>(v);
            size = 1;
            return true;
        }

        root = splay(root, v);

        Node<V> node = new Node<>(v);
        if (v.compareTo(root.value) < 0) {
            node.left = root.left;
            node.right = root;
            root.left = null;
            root = node;
            size++;
            return true;
        } else if (v.compareTo(root.value) > 0) {
            node.right = root.right;
            node.left = root;
            root.right = null;
            root = node;
            size++;
            return true;
        }
        return false;
    }

    public boolean remove(Object o) {
        if (root == null) return false;

        V v = (V) o;
        root = splay(root, (V) o);

        if (v.equals(root.value)) {
            if (root.left == null) {
                root = root.right;
            } else {
                Node<V> x = root.right;
                root = root.left;
                splay(root, v);
                root.right = x;
            }
            size--;
            return true;
        }
        return false;
    }

    public boolean containsAll(Collection<?> c) {
        return c.stream().allMatch(this::contains);
    }

    public boolean addAll(Collection<? extends V> c) {
        return c.stream().allMatch(this::add);
    }

    public boolean retainAll(Collection<?> c) {
        return this.stream().filter(v -> !c.contains(v)).allMatch(this::remove);
    }

    public boolean removeAll(Collection<?> c) {
        return c.stream().allMatch(this::remove);
    }

    public void clear() {
        this.forEach(this::remove);
    }

    private Node<V> splay(Node<V> node, V value) {
        if (node == null) return null;

        if (value.compareTo(node.value) < 0) {
            if (node.left == null) {
                return node;
            }
            if (value.compareTo(node.left.value) < 0) {
                node.left.left = splay(node.left.left, value);
                node = rotateRight(node);
            } else if (value.compareTo(node.left.value) > 0) {
                node.left.right = splay(node.left.right, value);
                if (node.left.right != null)
                    node.left = rotateLeft(node.left);
            }

            return node.left == null ? node : rotateRight(node);
        } else if (value.compareTo(node.value) > 0) {
            if (node.right == null) {
                return node;
            }

            if (value.compareTo(node.right.value) < 0) {
                node.right.left = splay(node.right.left, value);
                if (node.right.left != null)
                    node.right = rotateRight(node.right);
            } else if (value.compareTo(node.right.value) > 0) {
                node.right.right = splay(node.right.right, value);
                node = rotateLeft(node);
            }

            return node.right == null ? node : rotateLeft(node);
        } else return node;
    }

    private Node<V> rotateRight(Node<V> h) {
        Node<V> x = h.left;
        h.left = x.right;
        x.right = h;
        return x;
    }

    private Node<V> rotateLeft(Node<V> h) {
        Node<V> x = h.right;
        h.right = x.left;
        x.left = h;
        return x;
    }

    public Node<V> getRoot() {
        return root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Set)) return false;
        Set<?> splayTree = (Set<?>) o;

        return splayTree.size() == this.size && splayTree.stream().allMatch(this::contains);
    }

    @Override
    public int hashCode() {
        return this.stream().mapToInt(Object::hashCode).sum();
    }
}
