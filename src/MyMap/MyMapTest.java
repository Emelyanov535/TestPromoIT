package MyMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {

    private MyMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyMap<String, Integer>();
    }

    @Test
    void testSize() {
        assertEquals(0, map.size());
        map.put("one", 1);
        assertEquals(1, map.size());
        map.put("two", 2);
        assertEquals(2, map.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(map.isEmpty());
        map.put("one", 1);
        assertFalse(map.isEmpty());
    }

    @Test
    void testContainsKey() {
        map.put("one", 1);
        assertTrue(map.containsKey("one"));
        assertFalse(map.containsKey("two"));
    }

    @Test
    void testContainsValue() {
        map.put("one", 1);
        assertTrue(map.containsValue(1));
        assertFalse(map.containsValue(2));
    }

    @Test
    void testGet() {
        map.put("one", 1);
        assertEquals(1, map.get("one"));
        assertNull(map.get("two"));
    }

    @Test
    void testPut() {
        assertNull(map.put("one", 1));
        assertEquals(1, map.get("one"));
        assertEquals(1, map.put("one", 2));
        assertEquals(2, map.get("one"));
    }

    @Test
    void testRemove() {
        map.put("one", 1);
        map.put("two", 2);
        assertEquals(1, map.remove("one"));
        assertNull(map.remove("three"));
        assertFalse(map.containsKey("one"));
        assertEquals(1, map.size());
    }

    @Test
    void testPutAll() {
        Map<String, Integer> otherMap = new HashMap<>();
        otherMap.put("one", 1);
        otherMap.put("two", 2);
        map.putAll(otherMap);
        assertEquals(2, map.size());
        assertTrue(map.containsKey("one"));
        assertTrue(map.containsKey("two"));
    }

    @Test
    void testClear() {
        map.put("one", 1);
        map.put("two", 2);
        map.clear();
        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
    }

    @Test
    void testKeySet() {
        map.put("one", 1);
        map.put("two", 2);
        Set<String> keySet = map.keySet();
        assertEquals(2, keySet.size());
        assertTrue(keySet.contains("one"));
        assertTrue(keySet.contains("two"));
    }

    @Test
    void testValues() {
        map.put("one", 1);
        map.put("two", 2);
        Collection<Integer> values = map.values();
        assertEquals(2, values.size());
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
    }

    @Test
    void testEntrySet() {
        map.put("one", 1);
        map.put("two", 2);
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        assertEquals(2, entrySet.size());
        for (Map.Entry<String, Integer> entry : entrySet) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (key.equals("one")) {
                assertEquals(1, value);
            } else if (key.equals("two")) {
                assertEquals(2, value);
            } else {
                fail("Unexpected key: " + key);
            }
        }
    }

    @Test
    void testToString() {
        map.put("one", 1);
        map.put("two", 2);
        String mapString = map.toString();
        assertTrue(mapString.contains("one=1"));
        assertTrue(mapString.contains("two=2"));
    }
}
