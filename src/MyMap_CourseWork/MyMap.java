package MyMap_CourseWork;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class MyMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;
    private DataItem<K, V>[] map;
    private int arraySize;
    private int size;
    private final ReentrantLock lock = new ReentrantLock();
    private static class DataItem<K, V> implements Map.Entry<K, V> {
        private final K key;
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
        lock.lock();
        try {
            return size;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return size == 0;
        } finally {
            lock.unlock();
        }
    }

    public boolean containsKey(Object key) {
        lock.lock();
        try {
            int hashValue = hashCode(key);
            DataItem<K, V> current = map[hashValue];
            while (current != null) {
                if (current.getKey().equals(key)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean containsValue(Object value) {
        lock.lock();
        try {
            for (DataItem<K, V> item : map) {
                while (item != null) {
                    if (item.getValue().equals(value)) {
                        return true;
                    }
                    item = item.next;
                }
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public V get(Object key) {
        lock.lock();
        try {
            int hashValue = hashCode(key);
            DataItem<K, V> current = map[hashValue];
            while (current != null) {
                if (current.getKey().equals(key)) {
                    return current.getValue();
                }
                current = current.next;
            }
            return null;
        } finally {
            lock.unlock();
        }
    }

    public V put(K key, V value) {
        lock.lock();
        try {
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
        } finally {
            lock.unlock();
        }
    }

    private void resize() {
        int newCapacity = arraySize * 2;
        DataItem<K, V>[] newMap = new DataItem[newCapacity];

        for (DataItem<K, V> item : map) {
            while (item != null) {
                Object key = item.getKey();
                int hashValue = (key == null) ? 0 : Math.abs(key.hashCode() % newCapacity);
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
        lock.lock();
        try {
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
        } finally {
            lock.unlock();
        }
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        lock.lock();
        try {
            for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        } finally {
            lock.unlock();
        }
    }

    public void clear() {
        lock.lock();
        try {
            Arrays.fill(map, null);
            size = 0;
        } finally {
            lock.unlock();
        }
    }

    public Set<K> keySet() {
        lock.lock();
        try {
            Set<K> keySet = new HashSet<>();
            for (DataItem<K, V> item : map) {
                while (item != null) {
                    keySet.add(item.getKey());
                    item = item.next;
                }
            }
            return keySet;
        } finally {
            lock.unlock();
        }
    }

    public Collection<V> values() {
        lock.lock();
        try {
            Collection<V> values = new ArrayList<>();
            for (DataItem<K, V> item : map) {
                while (item != null) {
                    values.add(item.getValue());
                    item = item.next;
                }
            }
            return values;
        } finally {
            lock.unlock();
        }
    }

    public Set<Entry<K, V>> entrySet() {
        lock.lock();
        try {
            Set<Entry<K, V>> entrySet = new HashSet<>();
            for (DataItem<K, V> item : map) {
                while (item != null) {
                    entrySet.add(item);
                    item = item.next;
                }
            }
            return entrySet;
        } finally {
            lock.unlock();
        }
    }

    public String toString() {
        lock.lock();
        try {
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
        } finally {
            lock.unlock();
        }
    }
}
