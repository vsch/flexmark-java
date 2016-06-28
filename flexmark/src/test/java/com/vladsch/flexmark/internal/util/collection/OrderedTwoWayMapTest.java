package com.vladsch.flexmark.internal.util.collection;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.Map;

public class OrderedTwoWayMapTest {
    @Test
    public void testAddRemove() throws Exception {
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

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
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

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

            // hosted sets don't shrink
            Assert.assertEquals(orderedMap.size() == 0 ? 0: 10, orderedMap.keySet().getValueList().size());

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
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();
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
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

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
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

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
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

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

            // hosted sets don't shrink until empty
            Assert.assertEquals(orderedMap.size() == 0 ? 0: 10, orderedMap.keySet().getValueList().size());

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
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

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

            // hosted sets don't shrink
            Assert.assertEquals(orderedMap.size() == 0 ? 0: 10, orderedMap.keySet().getValueList().size());

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

    /** 
     * reverse key/values
     * 
     * @throws Exception
     */
    @Test
    public void testAddRemoveValue() throws Exception {
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.putValueKey(i, String.valueOf(i)));
            Assert.assertEquals(String.valueOf(i), orderedMap.putValueKey(i, String.valueOf(i)));
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
            Assert.assertEquals(String.valueOf(j), orderedMap.removeValue((Integer)j));

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
    public void testAddRemoveReversedValue() throws Exception {
        OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.putValueKey(i, String.valueOf(i)));
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

        i = 0;
        for (Map.Entry<Integer, String> it : orderedMap.valueKeyEntrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getValue());
            Assert.assertEquals(i, (int)it.getKey());
            i++;
        }

        for (int j = 10; j-- > 0; ) {
            Assert.assertEquals(String.valueOf(j), orderedMap.removeValue((Integer)j));

            // hosted sets don't shrink
            Assert.assertEquals(orderedMap.size() == 0 ? 0: 10, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<Integer, String> it : orderedMap.valueKeyEntrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getValue());
                Assert.assertEquals(lastJ, (int)it.getKey());
                lastJ++;
            }

            Assert.assertEquals(lastJ, j);
        }
    }

    //@Test
    //public void testRetainAll() throws Exception {
    //    OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();
    //    OrderedSet<String> retainSet = new OrderedSet<String>();
    //
    //    for (int i = 0; i < 10; i++) {
    //        Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
    //        Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
    //    }
    //
    //    orderedMap.putAll(orderedMap);
    //    Assert.assertEquals(true, orderedMap.equals(orderedMap));
    //
    //    int i = 0;
    //    for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
    //        Assert.assertEquals(String.valueOf(i), it.getKey());
    //        Assert.assertEquals(i, (int)it.getValue());
    //        i++;
    //    }
    //
    //    for (i = 0; i < 10; i += 2) {
    //        Assert.assertEquals(true, retainSet.add(String.valueOf(i)));
    //        Assert.assertEquals(false, retainSet.add(String.valueOf(i)));
    //    }
    //
    //    Assert.assertEquals(false, orderedMap.keySet().retainAll(orderedMap.keySet()));
    //    Assert.assertEquals(false, retainSet.retainAll(retainSet));
    //
    //    Assert.assertEquals(true, orderedMap.keySet().retainAll(retainSet));
    //    Assert.assertEquals(true, orderedMap.keySet().equals(retainSet));
    //
    //    i = 0;
    //    for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
    //        Assert.assertEquals(String.valueOf(i), it.getKey());
    //        Assert.assertEquals((Integer)i,it.getValue());
    //        i += 2;
    //    }
    //
    //    for (int j = 10; j-- > 0; ) {
    //        Assert.assertEquals((j & 1) == 0, orderedMap.keySet().remove(String.valueOf(j)));
    //        Assert.assertEquals(false, orderedMap.containsKey(String.valueOf(j)));
    //    }
    //}
    //
    //@Test
    //public void testRemoveIteration() throws Exception {
    //    OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();
    //
    //    for (int i = 0; i < 10; i++) {
    //        Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
    //        Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
    //    }
    //
    //    orderedMap.putAll(orderedMap);
    //    Assert.assertEquals(true, orderedMap.equals(orderedMap));
    //
    //    Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().iterator();
    //    int i = 0;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String, Integer> it = iterator.next();
    //        Assert.assertEquals(String.valueOf(i), it.getKey());
    //        Assert.assertEquals(i, (int)it.getValue());
    //        i++;
    //    }
    //
    //    iterator = orderedMap.entrySet().iterator();
    //    int j = 0;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String,Integer> item = iterator.next();
    //        iterator.remove();
    //
    //        Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());
    //
    //        int lastJ = j + 1;
    //        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
    //            Assert.assertEquals(String.valueOf(lastJ), it.getKey());
    //            Assert.assertEquals(lastJ, (int)it.getValue());
    //            lastJ++;
    //        }
    //
    //        j++;
    //    }
    //}
    //
    //@Test
    //public void testRemoveReversedReversedIteration() throws Exception {
    //    OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();
    //
    //    for (int i = 0; i < 10; i++) {
    //        Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
    //        Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
    //    }
    //
    //    orderedMap.putAll(orderedMap);
    //    Assert.assertEquals(true, orderedMap.equals(orderedMap));
    //
    //    Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().reversedIterator().reversed();
    //    int i = 0;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String, Integer> it = iterator.next();
    //        Assert.assertEquals(String.valueOf(i), it.getKey());
    //        Assert.assertEquals(i, (int)it.getValue());
    //        i++;
    //    }
    //
    //    iterator = orderedMap.entrySet().reversedIterator().reversed();
    //    int j = 0;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String,Integer> item = iterator.next();
    //        iterator.remove();
    //
    //        Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());
    //
    //        int lastJ = j + 1;
    //        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
    //            Assert.assertEquals(String.valueOf(lastJ), it.getKey());
    //            Assert.assertEquals(lastJ, (int)it.getValue());
    //            lastJ++;
    //        }
    //
    //        j++;
    //    }
    //}
    //
    //@Test
    //public void testRemoveReversedIteration() throws Exception {
    //    OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();
    //
    //    for (int i = 0; i < 10; i++) {
    //        Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
    //        Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
    //    }
    //
    //    orderedMap.putAll(orderedMap);
    //    Assert.assertEquals(true, orderedMap.equals(orderedMap));
    //
    //    Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().reversedIterator();
    //    int i = 9;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String, Integer> it = iterator.next();
    //        Assert.assertEquals(String.valueOf(i), it.getKey());
    //        Assert.assertEquals(i, (int)it.getValue());
    //        i--;
    //    }
    //    
    //    Assert.assertEquals(-1, i);
    //
    //    iterator = orderedMap.entrySet().reversedIterator();
    //    int j = 9;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String,Integer> item = iterator.next();
    //        iterator.remove();
    //
    //        // hosted sets don't shrink until empty
    //        Assert.assertEquals(orderedMap.size() == 0 ? 0: 10, orderedMap.keySet().getValueList().size());
    //
    //        int lastJ = 0;
    //        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
    //            Assert.assertEquals(String.valueOf(lastJ), it.getKey());
    //            Assert.assertEquals(lastJ, (int)it.getValue());
    //            lastJ++;
    //        }
    //
    //        Assert.assertEquals(lastJ, j);
    //        j--;
    //    }
    //}
    //
    //@Test
    //public void testRemoveIteratorReversedIteration() throws Exception {
    //    OrderedTwoWayMap<String, Integer> orderedMap = new OrderedTwoWayMap<>();
    //
    //    for (int i = 0; i < 10; i++) {
    //        Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
    //        Assert.assertEquals((Integer)i, orderedMap.put(String.valueOf(i), i));
    //    }
    //
    //    orderedMap.putAll(orderedMap);
    //    Assert.assertEquals(true, orderedMap.equals(orderedMap));
    //
    //    Iterator<Map.Entry<String,Integer>> iterator = orderedMap.entrySet().iterator().reversed();
    //    int i = 9;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String, Integer> it = iterator.next();
    //        Assert.assertEquals(String.valueOf(i), it.getKey());
    //        Assert.assertEquals(i, (int)it.getValue());
    //        i--;
    //    }
    //    
    //    Assert.assertEquals(-1, i);
    //
    //    iterator = orderedMap.entrySet().iterator().reversed();
    //    int j = 9;
    //    while (iterator.hasNext()) {
    //        Map.Entry<String,Integer> item = iterator.next();
    //        iterator.remove();
    //
    //        // hosted sets don't shrink
    //        Assert.assertEquals(orderedMap.size() == 0 ? 0: 10, orderedMap.keySet().getValueList().size());
    //
    //        int lastJ = 0;
    //        for (Map.Entry<String,Integer> it : orderedMap.entrySet()) {
    //            Assert.assertEquals(String.valueOf(lastJ), it.getKey());
    //            Assert.assertEquals(lastJ, (int)it.getValue());
    //            lastJ++;
    //        }
    //
    //        Assert.assertEquals(lastJ, j);
    //        j--;
    //    }
    //}
}
