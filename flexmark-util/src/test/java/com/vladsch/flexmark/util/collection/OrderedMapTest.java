package com.vladsch.flexmark.util.collection;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class OrderedMapTest {
  @Test
  public void testAddRemove() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

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
      for (Map.Entry<String, Integer> it : orderedMap) {
        Assert.assertEquals(String.valueOf(lastJ), it.getKey());
        Assert.assertEquals(lastJ, (int) it.getValue());
        lastJ++;
      }
    }
  }

  @Test
  public void testAddRemoveReversed() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

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

    for (int j = 10; j-- > 0; ) {
      Assert.assertEquals((Integer) j, orderedMap.remove(String.valueOf(j)));

      // hosted sets don't shrink
      Assert.assertEquals(
          orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

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
  public void testRetainAll() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
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
  public void testRemoveIteration() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);
    Assert.assertEquals(true, orderedMap.equals(orderedMap));

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();
    int i = 0;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> it = iterator.next();
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals(i, (int) it.getValue());
      i++;
    }

    iterator = orderedMap.iterator();
    int j = 0;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> item = iterator.next();
      iterator.remove();

      Assert.assertEquals(j == 9 ? 0 : 10, orderedMap.keySet().getValueList().size());

      int lastJ = j + 1;
      for (Map.Entry<String, Integer> it : orderedMap) {
        Assert.assertEquals(String.valueOf(lastJ), it.getKey());
        Assert.assertEquals(lastJ, (int) it.getValue());
        lastJ++;
      }

      j++;
    }
  }

  @Test
  public void testRemoveReversedReversedIteration() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);
    Assert.assertEquals(true, orderedMap.equals(orderedMap));

    Iterator<Map.Entry<String, Integer>> iterator =
        orderedMap.reversedIterable().reversed().iterator();
    int i = 0;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> it = iterator.next();
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals(i, (int) it.getValue());
      i++;
    }

    iterator = orderedMap.reversedIterable().reversedIterator();
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
  public void testRemoveReversedIteration() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);
    Assert.assertEquals(true, orderedMap.equals(orderedMap));

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.reversedEntryIterator();
    int i = 9;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> it = iterator.next();
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals(i, (int) it.getValue());
      i--;
    }

    Assert.assertEquals(-1, i);

    iterator = orderedMap.reversedEntryIterator();
    int j = 9;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> item = iterator.next();
      iterator.remove();

      // hosted sets don't shrink until empty
      Assert.assertEquals(
          orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

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
  public void testRemoveIteratorReversedIteration() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertEquals(null, orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);
    Assert.assertEquals(true, orderedMap.equals(orderedMap));

    Iterator<Map.Entry<String, Integer>> iterator =
        orderedMap.entryIterable().reversed().reversed().reversedIterator();
    int i = 9;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> it = iterator.next();
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals(i, (int) it.getValue());
      i--;
    }

    Assert.assertEquals(-1, i);

    iterator = orderedMap.entryIterable().reversed().reversed().reversedIterator();
    int j = 9;
    while (iterator.hasNext()) {
      Map.Entry<String, Integer> item = iterator.next();
      iterator.remove();

      // hosted sets don't shrink
      Assert.assertEquals(
          orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

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
  public void testConcurrentModIterator() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();

    orderedMap.remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModKeyIterator() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<String> iterator1 = orderedMap.keyIterator();

    orderedMap.remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator1.next());
  }

  @Test
  public void testConcurrentModValueIterator() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Integer> iterator = orderedMap.valueIterator();

    orderedMap.remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModIteratorOnKey() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();

    orderedMap.keySet().remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModKeyIteratorOnKey() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<String> iterator1 = orderedMap.keyIterator();

    orderedMap.keySet().remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator1.next());
  }

  @Test
  public void testConcurrentModValueIteratorOnKey() {
    OrderedMap<String, Integer> orderedMap = new OrderedMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Integer> iterator = orderedMap.valueIterator();

    orderedMap.keySet().remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testHostedCallback() {
    CollectionHostValidator<String> validator = new CollectionHostValidator<>();
    final OrderedMap<String, Integer> orderedMap = new OrderedMap<>(validator.getHost());

    validator.reset().expectAdding(0, "0", 0).test(() -> orderedMap.put("0", 0));

    validator.reset().expectAdding(1, "1", 1).test(() -> orderedMap.put("1", 1));

    for (int j = 0; j < 2; j++) {
      final int finalJ = j;
      validator
          .reset()
          .id(j)
          .expectRemoving(j, String.valueOf(j))
          .onCond(j == 1)
          .expectClearing()
          .test(() -> orderedMap.keySet().remove(String.valueOf(finalJ)));
    }
  }
}
