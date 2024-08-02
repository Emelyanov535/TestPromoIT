package MyMap;

import java.util.Map;
import java.util.Objects;
public class DataItem<K, V> implements Map.Entry<K, V> {
    private K key;
    private V value;
    DataItem<K, V> next;

    public DataItem(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = null;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V oldValue = this.value;
        this.value = value;
        return oldValue;
    }

    public int hashCode() {
        return Objects.hash(key, value);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataItem<?, ?> dataItem = (DataItem<?, ?>) o;
        return Objects.equals(key, dataItem.key) && Objects.equals(value, dataItem.value);
    }

    public String toString() {
        return key + "=" + value;
    }
}

