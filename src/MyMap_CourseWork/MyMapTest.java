package MyMap_CourseWork;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

class MyMapTest {

    private MyMap<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new MyMap<>();
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

    @Test
    void testConcurrentPut() throws InterruptedException {
        int numThreads = 4;
        int numEntriesPerThread = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);

        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < numEntriesPerThread; j++) {
                        map.put("key" + (threadIndex * numEntriesPerThread + j), j);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        int expectedSize = numThreads * numEntriesPerThread;
        int actualSize = map.size();
        assertEquals(expectedSize, actualSize, () -> "Expected size: " + expectedSize + ", but got: " + actualSize);

        if (expectedSize != actualSize) {
            System.out.println("Map contents: " + map);
        }
    }

    @Test
    void testConcurrentPutAndGet() throws InterruptedException {
        int numThreads = 4;
        int numEntriesPerThread = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads * 2);
        CountDownLatch latch = new CountDownLatch(numThreads * 2);

        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < numEntriesPerThread; j++) {
                        map.put("key" + (threadIndex * numEntriesPerThread + j), j);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });

            executor.submit(() -> {
                try {
                    for (int j = 0; j < numEntriesPerThread; j++) {
                        map.get("key" + (threadIndex * numEntriesPerThread + j));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        int expectedSize = numThreads * numEntriesPerThread;
        int actualSize = map.size();
        assertEquals(expectedSize, actualSize, () -> "Expected size: " + expectedSize + ", but got: " + actualSize);

        for (int i = 0; i < numThreads * numEntriesPerThread; i++) {
            assertNotNull(map.get("key" + i), "Expected to find key" + i + " but it was missing");
        }
    }
}
