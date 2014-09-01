package dayue.datastructure;

import java.util.List;

public interface SearchTree<K extends Comparable<? super K>, V> {
    int size();
    boolean isEmpty();

    V get(K key);
    V put(K key, V value);
    V delete(K key);

    V min();
    V max();
    V deleteMin();
    V deleteMax();

    /**
     * Returns a list view of the values contained in this tree,
     * sorted in ascending key order
     * @return
     */
    List<V> values();

    /**
     * Returns a list view of the values contained in this tree,
     * sorted in ascending key order and within the key range [start, end]
     * @param start low endpoint of keys
     * @param end
     * @return
     */
    List<V> values(K start, K end);

}
