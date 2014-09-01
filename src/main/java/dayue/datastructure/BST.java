package dayue.datastructure;

import java.util.*;

/**
 * Binary Search Tree.
 * @param <K>
 * @param <V>
 */
public class BST<K extends Comparable<? super K>, V> implements SearchTree<K, V> {

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;
        BSTNode parent;

        BSTNode(K key, V value, BSTNode parent, BSTNode left, BSTNode right) {
            this.key = key;
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        BSTNode(K key, V value, BSTNode parent) {
            this(key, value, parent, null, null);
        }

        void appendToBuffer(StringBuilder sb) {
            if (left != null) {
                sb.append("(");
                left.appendToBuffer(sb);
                sb.append(")");
            }
            sb.append(key);
            if (right != null) {
                sb.append("(");
                right.appendToBuffer(sb);
                sb.append(")");
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            appendToBuffer(sb);
            return sb.toString();
        }
    }

    private BSTNode root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public V get(K key) {
        BSTNode x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp == 0)   return x.value;
            x = (cmp < 0) ? x.left : x.right;
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        if (isEmpty()) {
            root = new BSTNode(key, value, null);
            size++;
            return null;
        }

        int cmp;
        BSTNode parent;
        BSTNode x = root;

        do {
            parent = x;
            cmp = key.compareTo(x.key);
            if (cmp == 0) {
                V oldValue = x.value;
                x.value = value;
                return oldValue;
            }
            x = (cmp < 0) ? x.left : x.right;
        } while (x != null);

        BSTNode newNode = new BSTNode(key, value, parent);
        if (cmp < 0)
            parent.left = newNode;
        else
            parent.right = newNode;

        size++;
        return null;
    }

//    private void updateChild(BSTNode parent, int cmp, BSTNode newChild) {
//        if (parent == null) {   // update root
//            root = newChild;
//        } else {
//            if      (cmp < 0)   parent.left = newChild;
//            else if (cmp > 0)   parent.right = newChild;
//        }
//
//        if (newChild != null)   newChild.parent = parent;
//    }
//
//    /**
//     * Update one of `parent`'s child with `newChild`. If `parent` is null,
//     * `newChild` becomes the new root.
//     * @param parent
//     * @param leftChild if parent isn't null, whether newChild is parent's
//     *                  left child or right child
//     * @param newChild new child of parent
//     */
//    private void updateChild(BSTNode parent, boolean leftChild, BSTNode newChild) {
//        updateChild(parent, leftChild ? -1 : 1, newChild);
//    }

    private void replaceNode(BSTNode oldNode, BSTNode newNode) {
        if (oldNode == root) {
            root = newNode;
        } else {
            if (oldNode.parent.left == oldNode)
                oldNode.parent.left = newNode;
            else
                oldNode.parent.right = newNode;
        }

        if (newNode != null) {
            newNode.parent = oldNode.parent;
        }
    }

    @Override
    public V delete(K key) {
        if (isEmpty()) return null;

        BSTNode x = root;
        do {
            int cmp = key.compareTo(x.key);
            if (cmp == 0) break;
            x = (cmp < 0) ? x.left : x.right;
        } while (x != null);
        // specified key not found
        if (x == null) return null;

        V oldValue = x.value;
        if (x.left == null && x.right == null) {
            replaceNode(x, null);
        } else if (x.left == null) {
            replaceNode(x, x.right);
        } else if (x.right == null) {
            replaceNode(x, x.left);
        } else {
            // replace x with x's successor
            BSTNode successor = deleteMin(x.right);
            successor.left = x.left;
            x.left.parent = successor;
            successor.right = x.right;
            if (x.right != null) x.right.parent = successor;
            replaceNode(x, successor);
        }
        size--;
        return oldValue;
    }

    @Override
    public V min() {
        for (BSTNode x = root; x != null; x = x.left) {
            if (x.left == null) return x.value;
        }
        return null;
    }

    @Override
    public V max() {
        for (BSTNode x = root; x != null; x = x.right) {
            if (x.right == null) return x.value;
        }
        return null;
    }

    /**
     * Delete the min node rooted at x.
     * @param x root of the subtree to look for min node
     * @return the deleted min node
     */
    private BSTNode deleteMin(BSTNode x) {
        while (x.left != null) {
            x = x.left;
        }
        replaceNode(x, x.right);
        return x;
    }

    @Override
    public V deleteMin() {
        if (isEmpty()) return null;
        BSTNode minNode = deleteMin(root);
        size--;
        return minNode.value;
    }

    /**
     * Delete the max node rooted at x.
     * @param x root of the subtree to look for max node
     * @return the deleted max node
     */
    private BSTNode deleteMax(BSTNode x) {
        while (x.right != null) {
            x = x.right;
        }
        replaceNode(x, x.left);
        return x;
    }

    @Override
    public V deleteMax() {
        if (isEmpty()) return null;
        BSTNode maxNode = deleteMax(root);
        size--;
        return maxNode.value;
    }

    @Override
    public List<V> values() {
        // Use non-recursive in-order traversal to collect
        // all values in key's ascending order
        List<V> values = new ArrayList<V>();
        Deque<BSTNode> stack = new ArrayDeque<BSTNode>();

        BSTNode x = root;
        while (x != null) {
            stack.addFirst(x);
            x = x.left;
        }

        while (!stack.isEmpty()) {
            x = stack.removeFirst();    // the node to visit
            values.add(x.value);
            // go left deep of x's right subtree
            x = x.right;
            while (x != null) {
                stack.addFirst(x);
                x = x.left;
            }
        }

        return values;
    }

    private void inOrderAddValues(Collection<V> collections, BSTNode root, K start, K end) {
        if (root == null) return;
        if (start.compareTo(root.key) < 0)
            inOrderAddValues(collections, root.left, start, end);
        if (start.compareTo(root.key) <= 0 && end.compareTo(root.key) >= 0)
            collections.add(root.value);
        if (end.compareTo(root.key) > 0)
            inOrderAddValues(collections, root.right, start, end);
    }

    @Override
    public List<V> values(K start, K end) {
        // for ranged queries, collect values using recursive in-order
        // traversal, which can easily prune search space.
        List<V> values = new ArrayList<V>();
        inOrderAddValues(values, root, start, end);
        return values;
    }

    @Override
    public String toString() {
        if (root == null) return "";
        return root.toString();
    }
}
