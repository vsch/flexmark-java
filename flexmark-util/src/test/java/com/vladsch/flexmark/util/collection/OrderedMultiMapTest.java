package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Paired;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;

public class OrderedMultiMapTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testAddRemove() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        for (int j = 0; j < 10; j++) {
            Assert.assertEquals((Integer) j, orderedMap.remove(String.valueOf(j)));

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
                lastJ++;
            }
        }
    }

    @Test
    public void testAddRemoveReversed() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        int i = 0;
        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        OrderedSet<Map.Entry<String, Integer>> entries = orderedMap.entrySet();

        orderedMap.putAll(orderedMap);
        i = 0;
        for (Map.Entry<String, Integer> it : entries) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        i = 0;
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        for (int j = 10; j-- > 0; ) {
            Assert.assertEquals((Integer) j, orderedMap.remove(String.valueOf(j)));

            // hosted sets don't shrink
            Assert.assertEquals(orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
                lastJ++;
            }

            Assert.assertEquals(lastJ, j);
        }
    }

    @Test
    public void testRetainAll() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        OrderedSet<String> retainSet = new OrderedSet<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
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
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals((Integer) i, it.getValue());
            i += 2;
        }

        for (int j = 10; j-- > 0; ) {
            Assert.assertEquals((j & 1) == 0, orderedMap.keySet().remove(String.valueOf(j)));
            Assert.assertEquals(false, orderedMap.containsKey(String.valueOf(j)));
        }
    }

    @Test
    public void testRemoveIteration() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.entrySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        iterator = orderedMap.entrySet().iterator();
        int j = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> item = iterator.next();
            iterator.remove();

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
                lastJ++;
            }

            j++;
        }
    }

    @Test
    public void testRemoveReversedReversedIteration() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.entrySet().reversedIterable().reversedIterator();
        int i = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        iterator = orderedMap.entrySet().reversedIterable().reversedIterator();
        int j = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> item = iterator.next();
            iterator.remove();

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
                lastJ++;
            }

            j++;
        }
    }

    @Test
    public void testRemoveReversedIteration() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.entrySet().reversedIterator();
        int i = 9;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i--;
        }

        Assert.assertEquals(-1, i);

        iterator = orderedMap.entrySet().reversedIterator();
        int j = 9;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> item = iterator.next();
            iterator.remove();

            // hosted sets don't shrink until empty
            Assert.assertEquals(orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
                lastJ++;
            }

            Assert.assertEquals(lastJ, j);
            j--;
        }
    }

    @Test
    public void testRemoveIteratorReversedIteration() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.entrySetIterable().reversed().iterator();
        int i = 9;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> it = iterator.next();
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i--;
        }

        Assert.assertEquals(-1, i);

        iterator = orderedMap.entrySetIterable().reversedIterator();
        int j = 9;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> item = iterator.next();
            iterator.remove();

            // hosted sets don't shrink
            Assert.assertEquals(orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
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
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.putValueKey(i, String.valueOf(i)));
            Assert.assertEquals(String.valueOf(i), orderedMap.putValueKey(i, String.valueOf(i)));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        for (int j = 0; j < 10; j++) {
            Assert.assertEquals(String.valueOf(j), orderedMap.removeValue((Integer) j));

            Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = j + 1;
            for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
                lastJ++;
            }
        }
    }

    @Test
    public void testAddRemoveReversedValue() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

        for (int i = 0; i < 10; i++) {
            Assert.assertEquals(null, orderedMap.putValueKey(i, String.valueOf(i)));
            Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
        }

        orderedMap.putAll(orderedMap);
        Assert.assertEquals(true, orderedMap.equals(orderedMap));

        int i = 0;
        for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        i = 0;
        for (Map.Entry<String, Integer> it : orderedMap) {
            Assert.assertEquals(String.valueOf(i), it.getKey());
            Assert.assertEquals(i, (int) it.getValue());
            i++;
        }

        for (int j = 10; j-- > 0; ) {
            Assert.assertEquals(String.valueOf(j), orderedMap.removeValue((Integer) j));

            // hosted sets don't shrink
            Assert.assertEquals(orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

            int lastJ = 0;
            for (Map.Entry<String, Integer> it : orderedMap) {
                Assert.assertEquals(String.valueOf(lastJ), it.getKey());
                Assert.assertEquals(lastJ, (int) it.getValue());
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
    //        Assert.assertEquals((j & 1) == 0, orderedMap.keySet().removeIndex(String.valueOf(j)));
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
    //        iterator.removeIndex();
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
    //        iterator.removeIndex();
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
    //        iterator.removeIndex();
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
    //        iterator.removeIndex();
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

    @Test
    public void testConcurrentModIterator() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();
        int j = 0;
        while (iterator.hasNext()) {
            orderedMap.remove("0");
            thrown.expect(ConcurrentModificationException.class);
            Map.Entry<String, Integer> item = iterator.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on modification", false, true);
        }
    }

    @Test
    public void testConcurrentModKeyIterator() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<String> iterator1 = orderedMap.keyIterator();
        int j = 0;
        while (iterator1.hasNext()) {
            orderedMap.remove("0");
            thrown.expect(ConcurrentModificationException.class);
            String item = iterator1.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on modification", false, true);
        }
    }

    @Test
    public void testConcurrentModValueIterator() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<Integer> iterator = orderedMap.valueIterator();
        int j = 0;
        while (iterator.hasNext()) {
            orderedMap.remove("0");
            thrown.expect(ConcurrentModificationException.class);
            int item = iterator.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on modification", false, true);
        }
    }

    @Test
    public void testConcurrentModIteratorOnKey() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();
        int j = 0;
        while (iterator.hasNext()) {
            orderedMap.keySet().remove("0");
            thrown.expect(ConcurrentModificationException.class);
            Map.Entry<String, Integer> item = iterator.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on keySet modification", false, true);
        }
    }

    @Test
    public void testConcurrentModKeyIteratorOnKey() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<String> iterator1 = orderedMap.keyIterator();
        int j = 0;
        while (iterator1.hasNext()) {
            orderedMap.keySet().remove("0");
            thrown.expect(ConcurrentModificationException.class);
            String item = iterator1.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on keySet modification", false, true);
        }
    }

    @Test
    public void testConcurrentModValueIteratorOnKey() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<Integer> iterator = orderedMap.valueIterator();
        int j = 0;
        while (iterator.hasNext()) {
            orderedMap.keySet().remove("0");
            thrown.expect(ConcurrentModificationException.class);
            int item = iterator.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on keySet modification", false, true);
        }
    }

    @Test
    public void testConcurrentModIteratorOnValue() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();
        int j = 0;
        while (iterator.hasNext()) {
            orderedMap.valueSet().removeIndex(0);
            thrown.expect(ConcurrentModificationException.class);
            Map.Entry<String, Integer> item = iterator.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on valueSet modification", false, true);
        }
    }

    @Test
    public void testConcurrentModKeyIteratorOnValue() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<String> iterator1 = orderedMap.keyIterator();
        int j = 0;
        while (iterator1.hasNext()) {
            orderedMap.valueSet().removeIndex(0);
            thrown.expect(ConcurrentModificationException.class);
            String item = iterator1.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on valueSet modification", false, true);
        }
    }

    @Test
    public void testConcurrentModValueIteratorOnValue() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);

        Iterator<Integer> iterator = orderedMap.valueIterator();
        int j = 0;
        while (iterator.hasNext()) {
            orderedMap.valueSet().removeIndex(0);
            thrown.expect(ConcurrentModificationException.class);
            int item = iterator.next();
            Assert.assertEquals("ConcurrentModificationException was not thrown on valueSet modification", false, true);
        }
    }

    @Test
    public void testAddConflict() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);
        thrown.expect(IllegalStateException.class);
        Assert.assertEquals((Integer) 1, orderedMap.putKeyValue("1", 0));
    }

    @Test
    public void testAddKeyValuePair() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);
        Assert.assertEquals(true, orderedMap.putKeyValuePair(new Pair<>("2", 2)));
        Assert.assertEquals(false, orderedMap.putKeyValuePair(new Pair<>("2", 2)));

        thrown.expect(IllegalStateException.class);
        Assert.assertEquals(false, orderedMap.putKeyValuePair(new Pair<>("1", 0)));
    }

    @Test
    public void testAddValueKeyPair() throws Exception {
        OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
        orderedMap.put("0", 0);
        orderedMap.put("1", 1);
        Assert.assertEquals(true, orderedMap.putValueKeyPair(new Pair<>(2, "2")));
        Assert.assertEquals(false, orderedMap.putValueKeyPair(new Pair<>(2, "2")));

        thrown.expect(IllegalStateException.class);
        Assert.assertEquals(false, orderedMap.putValueKeyPair(new Pair<>(0, "1")));
    }

    @Test
    public void testHostedCallback() throws Exception {
        CollectionHostValidator<Paired<String, Integer>> validator = new CollectionHostValidator<>();
        final OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>(validator.getHost());

        //validator.trace();

        validator.reset()
                .expectAdding(0, Pair.of("0", 0), null)
                .test(() -> orderedMap.put("0", 0));

        validator.reset()
                .expectAdding(1, Pair.of("1", 1), null)
                //.expectAdding(0, "1", 1)
                .test(() -> orderedMap.put("1", 1));

        for (int j = 0; j < 2; j++) {
            final int finalJ = j;
            validator.reset().id(j)
                    .expectRemoving(j, Pair.of(String.valueOf(j), (Integer) null))
                    .expectRemoving(j, Pair.of((String) null, j))
                    .repeat(2).onCond(j == 1).expectClearing()
                    .test((Runnable) () -> orderedMap.keySet().remove(String.valueOf(finalJ)));
        }
    }
}
