package dayue.datastructure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.*;

public class BSTTest {

    BST<Integer, String> newBst() {
        BST<Integer, String> bst = new BST<Integer, String>();
        bst.put(5, String.valueOf(5));
        bst.put(3, String.valueOf(3));
        bst.put(1, String.valueOf(1));
        bst.put(2, String.valueOf(2));
        bst.put(4, String.valueOf(4));
        bst.put(8, String.valueOf(8));
        bst.put(7, String.valueOf(7));
        bst.put(6, String.valueOf(6));
        bst.put(10, String.valueOf(10));
        bst.put(9, String.valueOf(9));
        bst.put(11, String.valueOf(11));

        assertTreeStructure("((1(2))3(4))5(((6)7)8((9)10(11)))", bst);
        assertEquals(11, bst.size());
        return bst;
    }

    void assertTreeStructure(String expected, BST<?, ?> bst) {
        assertEquals(expected, bst.toString());
    }

    @org.junit.Test
    public void testGet() throws Exception {
        BST<Integer, String> bst = newBst();
        for (int i = 1; i <= 11; i++)
            assertEquals(String.valueOf(i), bst.get(i));
        assertNull(bst.get(0));
        assertNull(bst.get(12));
    }

    @org.junit.Test
    public void testPut() throws Exception {
        BST<Integer, Integer> bst = new BST<Integer, Integer>();
        assertTreeStructure("", bst);
        bst.put(5, 5);
        assertTreeStructure("5", bst);
        bst.put(3, 3);
        assertTreeStructure("(3)5", bst);
        bst.put(8, 8);
        assertTreeStructure("(3)5(8)", bst);
        bst.put(1, 1);
        assertTreeStructure("((1)3)5(8)", bst);
        bst.put(2, 2);
        assertTreeStructure("((1(2))3)5(8)", bst);
        assertEquals(5, bst.size());

        // put values with the same key
        bst.put(3, 100);
        assertTreeStructure("((1(2))3)5(8)", bst);
        bst.put(2, 200);
        assertTreeStructure("((1(2))3)5(8)", bst);
        assertEquals(5, bst.size());
    }

    @org.junit.Test
    public void testDelete() throws Exception {
        BST<Integer, String> bst = new BST<Integer, String>();
        assertTrue(bst.isEmpty());
        assertNull(bst.delete(1));
        assertTrue(bst.isEmpty());

        bst = newBst();
        // delete non-exist keys
        assertNull(bst.delete(0));
        assertNull(bst.delete(12));
        // delete leaf node
        assertEquals("4", bst.delete(4));
        assertTreeStructure("((1(2))3)5(((6)7)8((9)10(11)))", bst);
        // delete node with one child
        assertEquals("3", bst.delete(3));
        assertTreeStructure("(1(2))5(((6)7)8((9)10(11)))", bst);
        assertEquals("1", bst.delete(1));
        assertTreeStructure("(2)5(((6)7)8((9)10(11)))", bst);
        assertEquals("7", bst.delete(7));
        assertTreeStructure("(2)5((6)8((9)10(11)))", bst);
        // delete node with both children
        assertEquals("10", bst.delete(10));
        assertTreeStructure("(2)5((6)8((9)11))", bst);
        assertEquals("8", bst.delete(8));
        assertTreeStructure("(2)5((6)9(11))", bst);
        assertEquals("5", bst.delete(5));
        assertTreeStructure("(2)6(9(11))", bst);
        assertEquals("6", bst.delete(6));
        assertTreeStructure("(2)9(11)", bst);
        assertEquals("9", bst.delete(9));
        assertTreeStructure("(2)11", bst);
        // delete remaining nodes
        assertEquals("11", bst.delete(11));
        assertEquals("2", bst.delete(2));
        assertTrue(bst.isEmpty());
        assertEquals(0, bst.size());

    }

    @org.junit.Test
    public void testMin() throws Exception {
        BST<Integer, String> bst = new BST<Integer, String>();
        assertNull(bst.min());

        bst = newBst();
        assertEquals("1", bst.min());
        bst.put(0, "min");
        assertEquals("min", bst.min());
    }

    @org.junit.Test
    public void testMax() throws Exception {
        BST<Integer, String> bst = new BST<Integer, String>();
        assertNull(bst.max());

        bst = newBst();
        assertEquals("11", bst.max());
        bst.put(12, "max");
        assertEquals("max", bst.max());
    }

    @org.junit.Test
    public void testDeleteMin() throws Exception {
        BST<Integer, String> bst = new BST<Integer, String>();
        assertTrue(bst.isEmpty());
        assertNull(bst.deleteMin());
        assertTrue(bst.isEmpty());

        bst = newBst();
        assertEquals("1", bst.deleteMin());
        assertTreeStructure("((2)3(4))5(((6)7)8((9)10(11)))", bst);
        assertEquals("2", bst.deleteMin());
        assertTreeStructure("(3(4))5(((6)7)8((9)10(11)))", bst);
        assertEquals("3", bst.deleteMin());
        assertTreeStructure("(4)5(((6)7)8((9)10(11)))", bst);
        assertEquals("4", bst.deleteMin());
        assertTreeStructure("5(((6)7)8((9)10(11)))", bst);
        assertEquals("5", bst.deleteMin());
        assertTreeStructure("((6)7)8((9)10(11))", bst);
        assertEquals(6, bst.size());
    }

    @org.junit.Test
    public void testDeleteMax() throws Exception {
        BST<Integer, String> bst = new BST<Integer, String>();
        assertTrue(bst.isEmpty());
        assertNull(bst.deleteMax());
        assertTrue(bst.isEmpty());

        bst = newBst();
        assertEquals("11", bst.deleteMax());
        assertTreeStructure("((1(2))3(4))5(((6)7)8((9)10))", bst);
        assertEquals("10", bst.deleteMax());
        assertTreeStructure("((1(2))3(4))5(((6)7)8(9))", bst);
        assertEquals("9", bst.deleteMax());
        assertTreeStructure("((1(2))3(4))5(((6)7)8)", bst);
        assertEquals("8", bst.deleteMax());
        assertTreeStructure("((1(2))3(4))5((6)7)", bst);
        assertEquals("7", bst.deleteMax());
        assertTreeStructure("((1(2))3(4))5(6)", bst);
        assertEquals("6", bst.deleteMax());
        assertTreeStructure("((1(2))3(4))5", bst);
        assertEquals("5", bst.deleteMax());
        assertTreeStructure("(1(2))3(4)", bst);
        assertEquals(4, bst.size());
    }

    @org.junit.Test
    public void testValues() throws Exception {
        BST<Integer, String> bst = new BST<Integer, String>();
        assertEquals(Collections.emptyList(), bst.values());

        bst = newBst();
        Collection<String> values = bst.values();
        assertEquals(11, values.size());
        int i = 1;
        for (String item : values) {
            assertEquals(String.valueOf(i), item);
            i++;
        }
    }

    @org.junit.Test
    public void testRangedValues() throws Exception {
        BST<Integer, String> bst = newBst();

        assertEquals(bst.values(), bst.values(1, 11));
        assertEquals(Arrays.asList("3", "4", "5"), bst.values(3, 5));
        assertEquals(Arrays.asList("4", "5", "6", "7", "8", "9"), bst.values(4, 9));
        assertEquals(Arrays.asList(), bst.values(12, 14));
    }
}