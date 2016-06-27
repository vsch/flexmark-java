package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.internal.util.collection.OrderedMap;
import com.vladsch.flexmark.internal.util.collection.OrderedSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

public class OrderedMapTest {
    @Test
    public void testAddRemove() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i++;
        }

        for (int j = 0; j < 10; j++) {
            Assert.assertEquals((Integer)j, orderedMap.remove(String.valueOf(j)));

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int)it.getValue());
                lastJ++;
            }
        }
    }

    @Test
    public void testAddRemoveReversed() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i++;
        }

        for (int j = 10; j-- > 0; ) {
            Assert.assertEquals((Integer)j, orderedMap.remove(String.valueOf(j)));

            Assert.assertEquals(j, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int)it.getValue());
                lastJ++;
            }

            Assert.assertEquals(lastJ, j);
        }
    }

    @Test
    public void testRetainAll() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
        OrderedSet<String> retainSet = new OrderedSet<String>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i++;
        }

        for (i = 0; i < 10; i += 2) {
            Assert.assertEquals(true, retainSet.add(String.valueOf(i)));
            Assert.assertEquals(false, retainSet.add(String.valueOf(i)));
        }

        Assert.assertEquals(false, orderedMap.keySet().retainAll(orderedMap.keySet()));
        Assert.assertEquals(false, retainSet.retainAll(retainSet));

        Assert.assertEquals(true, orderedMap.keySet().retainAll(retainSet));
        Assert.assertEquals(true, orderedMap.keySet().equals(retainSet));

        i = 0;
        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals((Integer)i,it.getValue());
            i += 2;
        }

        for (int j = 10; j-- > 0; ) {
            Assert.assertEquals((j & 1) == 0, orderedMap.keySet().remove(String.valueOf(j)));
            Assert.assertEquals(false, orderedMap.containsKey(String.valueOf(j)));
        }
    }

    @Test
    public void testRemoveIteration() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i++;
        }

        iterator = orderedMap.entrySet().iterator();
        int j = 0;
        while (iterator.hasNext()) {
            Map.Entry<String,Integer> item = iterator.next();
            iterator.remove();

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int)it.getValue());
                lastJ++;
            }

            j++;
        }
    }

    @Test
    public void testRemoveReversedReversedIteration() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().reversedIterator().reversed();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i++;
        }

        iterator = orderedMap.entrySet().reversedIterator().reversed();
        int j = 0;
        while (iterator.hasNext()) {
            Map.Entry<String,Integer> item = iterator.next();
            iterator.remove();

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int)it.getValue());
                lastJ++;
            }

            j++;
        }
    }

    @Test
    public void testRemoveReversedIteration() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().reversedIterator();
        int i = 9;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i--;
        }
        
        Assert.assertEquals(-1, i);

        iterator = orderedMap.entrySet().reversedIterator();
        int j = 9;
        while (iterator.hasNext()) {
            Map.Entry<String,Integer> item = iterator.next();
            iterator.remove();

            Assert.assertEquals(j, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int)it.getValue());
                lastJ++;
            }

            Assert.assertEquals(lastJ, j);
            j--;
        }
    }
    
    @Test
    public void testRemoveIteratorReversedIteration() throws Exception {
        OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().iterator().reversed();
        int i = 9;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int)it.getValue());
            i--;
        }
        
        Assert.assertEquals(-1, i);

        iterator = orderedMap.entrySet().iterator().reversed();
        int j = 9;
        while (iterator.hasNext()) {
            Map.Entry<String,Integer> item = iterator.next();
            iterator.remove();

            Assert.assertEquals(j, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int)it.getValue());
                lastJ++;
            }

            Assert.assertEquals(lastJ, j);
            j--;
        }
    }
}
