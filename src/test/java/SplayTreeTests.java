import org.junit.jupiter.api.Test;
import splay_tree.SplayTree;

import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SplayTreeTests {

    private static final Random random = new Random();
    private static final int SIZE = 10;

    private Set<Integer> generateValues() {
        return IntStream.range(0, SIZE).mapToObj(i -> random.nextInt()).collect(Collectors.toSet());
    }

    private Integer differentValue(Set<Integer> values) {
        Integer value = random.nextInt();
        while (values.contains(value)) {
            value = random.nextInt();
        }
        return value;
    }

    private SplayTree<Integer> generateSplayTree(Set<Integer> values) {
        SplayTree<Integer> splayTree = new SplayTree<>();
        splayTree.addAll(values);
        return splayTree;
    }

    @Test
    void addTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        assertEquals(splayTree.size(), values.size());
    }

    @Test
    void removeTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        values.forEach(splayTree::remove);
        values.forEach(v -> assertFalse(splayTree.contains(v)));

    }

    @Test
    void containsTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        values.forEach(value -> assertTrue(splayTree.contains(value)));
    }

    @Test
    void iteratorTest() {
        Set<Integer> values = generateValues();
        SplayTree<Integer> splayTree = generateSplayTree(values);

        splayTree.forEach(v -> {
            assertTrue(values.contains(v));
            values.remove(v);
        });
    }

}
