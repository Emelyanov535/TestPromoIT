package MyMap;

import java.util.*;

public class MyMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private DataItem<K, V>[] map;
    private int arraySize;
    private int size;

    public MyMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public MyMap(int initialCapacity) {
        arraySize = initialCapacity;
        map = new DataItem[arraySize];
        size = 0;
    }

    public MyMap(Map<? extends K, ? extends V> m) {
        this(DEFAULT_INITIAL_CAPACITY);
        putAll(m);
    }

    private int hashCode(Object key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % arraySize);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(Object key) {
        int hashValue = hashCode(key);
        DataItem<K, V> current = map[hashValue];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean containsValue(Object value) {
        for (DataItem<K, V> item : map) {
            while (item != null) {
                if (item.getValue().equals(value)) {
                    return true;
                }
                item = item.next;
            }
        }
        return false;
    }

    public V get(Object key) {
        int hashValue = hashCode(key);
        DataItem<K, V> current = map[hashValue];
        while (current != null) {
            if (current.getKey().equals(key)) {
                return current.getValue();
            }
            current = current.next;
        }
        return null;
    }

    public V put(K key, V value) {
        if (size >= arraySize * LOAD_FACTOR) {
            resize();
        }

        int hashValue = hashCode(key);
        DataItem<K, V> current = map[hashValue];
        if (current == null) {
            map[hashValue] = new DataItem<>(key, value);
        } else {
            while (true) {
                if (current.getKey().equals(key)) {
                    V oldValue = current.getValue();
                    current.setValue(value);
                    return oldValue;
                }
                if (current.next == null) {
                    current.next = new DataItem<>(key, value);
                    break;
                }
                current = current.next;
            }
        }
        size++;
        return null;
    }

    private void resize() {
        int newCapacity = arraySize * 2;
        DataItem<K, V>[] newMap = new DataItem[newCapacity];

        for (DataItem<K, V> item : map) {
            while (item != null) {
                int hashValue = item.getKey().hashCode() % newCapacity;
                DataItem<K, V> nextItem = item.next;

                item.next = newMap[hashValue];
                newMap[hashValue] = item;

                item = nextItem;
            }
        }

        map = newMap;
        arraySize = newCapacity;
    }

    public V remove(Object key) {
        int hashValue = hashCode(key);
        DataItem<K, V> current = map[hashValue];
        DataItem<K, V> prev = null;

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    map[hashValue] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return current.getValue();
            }
            prev = current;
            current = current.next;
        }
        return null;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public void clear() {
        Arrays.fill(map, null);
        size = 0;
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (DataItem<K, V> item : map) {
            while (item != null) {
                keySet.add(item.getKey());
                item = item.next;
            }
        }
        return keySet;
    }

    public Collection<V> values() {
        Collection<V> values = new ArrayList<>();
        for (DataItem<K, V> item : map) {
            while (item != null) {
                values.add(item.getValue());
                item = item.next;
            }
        }
        return values;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for (DataItem<K, V> item : map) {
            while (item != null) {
                entrySet.add(item);
                item = item.next;
            }
        }
        return entrySet;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("{");
        boolean first = true;

        for (DataItem<K, V> item : map) {
            while (item != null) {
                if (!first) {
                    result.append(", ");
                }
                result.append(item.getKey()).append("=").append(item.getValue());
                first = false;
                item = item.next;
            }
        }

        result.append("}");
        return result.toString();
    }
}
