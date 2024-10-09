package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Paired;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class OrderedMultiMapTest {
  @Test
  public void testAddRemove() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

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
  public void testAddRemoveReversed() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
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

    Assert.assertTrue(orderedMap.equals(orderedMap));

    i = 0;
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
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    OrderedSet<String> retainSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

    int i = 0;
    for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals(i, (int) it.getValue());
      i++;
    }

    for (i = 0; i < 10; i += 2) {
      Assert.assertTrue(retainSet.add(String.valueOf(i)));
      Assert.assertFalse(retainSet.add(String.valueOf(i)));
    }

    Assert.assertFalse(orderedMap.keySet().retainAll(orderedMap.keySet()));
    Assert.assertFalse(retainSet.retainAll(retainSet));

    Assert.assertTrue(orderedMap.keySet().retainAll(retainSet));
    Assert.assertEquals(orderedMap.keySet(), retainSet);

    i = 0;
    for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals((Integer) i, it.getValue());
      i += 2;
    }

    for (int j = 10; j-- > 0; ) {
      Assert.assertEquals((j & 1) == 0, orderedMap.keySet().remove(String.valueOf(j)));
      Assert.assertFalse(orderedMap.containsKey(String.valueOf(j)));
    }
  }

  @Test
  public void testRemoveIteration() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

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
      iterator.next();
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
  public void testRemoveReversedReversedIteration() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

    Iterator<Map.Entry<String, Integer>> iterator =
        orderedMap.entrySet().reversedIterable().reversedIterator();
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
      iterator.next();
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
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

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
      iterator.next();
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
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.put(String.valueOf(i), i));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

    Iterator<Map.Entry<String, Integer>> iterator =
        orderedMap.entrySetIterable().reversed().iterator();
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
      iterator.next();
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

  /** reverse key/values */
  @Test
  public void testAddRemoveValue() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.putValueKey(i, String.valueOf(i)));
      Assert.assertEquals(String.valueOf(i), orderedMap.putValueKey(i, String.valueOf(i)));
    }

    orderedMap.putAll(orderedMap);

    int i = 0;
    for (Map.Entry<String, Integer> it : orderedMap.entrySet()) {
      Assert.assertEquals(String.valueOf(i), it.getKey());
      Assert.assertEquals(i, (int) it.getValue());
      i++;
    }

    for (int j = 0; j < 10; j++) {
      Assert.assertEquals(String.valueOf(j), orderedMap.removeValue(j));

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
  public void testAddRemoveReversedValue() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();

    for (int i = 0; i < 10; i++) {
      Assert.assertNull(orderedMap.putValueKey(i, String.valueOf(i)));
      Assert.assertEquals((Integer) i, orderedMap.put(String.valueOf(i), i));
    }

    orderedMap.putAll(orderedMap);

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
      Assert.assertEquals(String.valueOf(j), orderedMap.removeValue(j));

      // hosted sets don't shrink
      Assert.assertEquals(
          orderedMap.size() == 0 ? 0 : 10, orderedMap.keySet().getValueList().size());

      int lastJ = 0;
      for (Map.Entry<String, Integer> it : orderedMap) {
        Assert.assertEquals(String.valueOf(lastJ), it.getKey());
        Assert.assertEquals(lastJ, (int) it.getValue());
        lastJ++;
      }

      Assert.assertEquals(lastJ, j);
    }
  }

  @Test
  public void testConcurrentModIterator() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();

    orderedMap.remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModKeyIterator() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<String> iterator1 = orderedMap.keyIterator();

    orderedMap.remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator1.next());
  }

  @Test
  public void testConcurrentModValueIterator() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Integer> iterator = orderedMap.valueIterator();

    orderedMap.remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModIteratorOnKey() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();

    orderedMap.keySet().remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModKeyIteratorOnKey() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<String> iterator1 = orderedMap.keyIterator();

    orderedMap.keySet().remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator1.next());
  }

  @Test
  public void testConcurrentModValueIteratorOnKey() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Integer> iterator = orderedMap.valueIterator();

    orderedMap.keySet().remove("0");
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModIteratorOnValue() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Map.Entry<String, Integer>> iterator = orderedMap.iterator();

    orderedMap.valueSet().removeIndex(0);
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testConcurrentModKeyIteratorOnValue() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<String> iterator1 = orderedMap.keyIterator();

    orderedMap.valueSet().removeIndex(0);
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator1.next());
  }

  @Test
  public void testConcurrentModValueIteratorOnValue() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Iterator<Integer> iterator = orderedMap.valueIterator();

    orderedMap.valueSet().removeIndex(0);
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testAddConflict() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);

    Assert.assertThrows(IllegalStateException.class, () -> orderedMap.putKeyValue("1", 0));
  }

  @Test
  public void testAddKeyValuePair() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);
    Assert.assertTrue(orderedMap.putKeyValuePair(new Pair<>("2", 2)));
    Assert.assertFalse(orderedMap.putKeyValuePair(new Pair<>("2", 2)));

    Pair<String, Integer> pair = new Pair<>("1", 0);
    Assert.assertThrows(IllegalStateException.class, () -> orderedMap.putKeyValuePair(pair));
  }

  @Test
  public void testAddValueKeyPair() {
    OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>();
    orderedMap.put("0", 0);
    orderedMap.put("1", 1);
    Assert.assertTrue(orderedMap.putValueKeyPair(new Pair<>(2, "2")));
    Assert.assertFalse(orderedMap.putValueKeyPair(new Pair<>(2, "2")));

    Pair<Integer, String> pair = new Pair<>(0, "1");
    Assert.assertThrows(IllegalStateException.class, () -> orderedMap.putValueKeyPair(pair));
  }

  @Test
  public void testHostedCallback() {
    CollectionHostValidator<Paired<String, Integer>> validator = new CollectionHostValidator<>();
    final OrderedMultiMap<String, Integer> orderedMap = new OrderedMultiMap<>(validator.getHost());

    validator.reset().expectAdding(0, new Pair<>("0", 0), null).test(() -> orderedMap.put("0", 0));

    validator.reset().expectAdding(1, new Pair<>("1", 1), null).test(() -> orderedMap.put("1", 1));

    for (int j = 0; j < 2; j++) {
      final int finalJ = j;
      validator
          .reset()
          .setId(j)
          .expectRemoving(j, new Pair<>(String.valueOf(j), (Integer) null))
          .expectRemoving(j, new Pair<>((String) null, j))
          .setRepeat(2)
          .setConditional(j == 1)
          .expectClearing()
          .test(() -> orderedMap.keySet().remove(String.valueOf(finalJ)));
    }
  }
}
