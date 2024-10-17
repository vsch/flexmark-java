package com.vladsch.flexmark.util.collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class OrderedSetTest {
  @Test
  public void testAddRemove() {
    OrderedSet<String> orderedSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    assertFalse(orderedSet.addAll(orderedSet));

    int i = 0;
    for (String it : orderedSet) {
      assertEquals(String.valueOf(i), it);
      i++;
    }

    for (int j = 0; j < 10; j++) {
      assertTrue(orderedSet.remove(String.valueOf(j)));

      assertEquals(j == 9 ? 0 : 10, orderedSet.getValueList().size());

      int lastJ = j + 1;
      for (String it : orderedSet) {
        assertEquals(String.valueOf(lastJ), it);
        lastJ++;
      }
    }
  }

  @Test
  public void testAddRemoveReversed() {
    OrderedSet<String> orderedSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    assertFalse(orderedSet.addAll(orderedSet));

    int i = 0;
    for (String it : orderedSet) {
      assertEquals(String.valueOf(i), it);
      i++;
    }

    for (int j = 10; j-- > 0; ) {
      assertTrue(orderedSet.remove(String.valueOf(j)));

      assertEquals(j, orderedSet.getValueList().size());

      int lastJ = 0;
      for (String it : orderedSet) {
        assertEquals(String.valueOf(lastJ++), it);
      }

      assertEquals(lastJ, j);
    }
  }

  @Test
  public void testRetainAll() {
    OrderedSet<String> orderedSet = new OrderedSet<>();
    OrderedSet<String> retainSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    for (int i = 0; i < 10; i += 2) {
      assertTrue(retainSet.add(String.valueOf(i)));
      assertFalse(retainSet.add(String.valueOf(i)));
    }

    assertFalse(orderedSet.addAll(orderedSet));
    assertFalse(retainSet.addAll(retainSet));

    assertFalse(orderedSet.retainAll(orderedSet));
    assertFalse(retainSet.retainAll(retainSet));

    assertTrue(orderedSet.retainAll(retainSet));
    assertEquals(orderedSet, retainSet);

    int i = 0;
    for (String it : orderedSet) {
      assertEquals(String.valueOf(i), it);
      i += 2;
    }

    for (int j = 10; j-- > 0; ) {
      assertEquals((j & 1) == 0, orderedSet.remove(String.valueOf(j)));
    }
  }

  @Test
  public void testRemoveIteration() {
    OrderedSet<String> orderedSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    assertFalse(orderedSet.addAll(orderedSet));

    int i = 0;
    Iterator<String> iterator = orderedSet.iterator();
    while (iterator.hasNext()) {
      String it = iterator.next();
      assertEquals(String.valueOf(i), it);
      i++;
    }

    iterator = orderedSet.iterator();
    int j = 0;
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();

      assertEquals(j == 9 ? 0 : 10, orderedSet.getValueList().size());

      int lastJ = j + 1;
      for (String it : orderedSet) {
        assertEquals(String.valueOf(lastJ), it);
        lastJ++;
      }

      j++;
    }
  }

  @Test
  public void testRemoveReversedIteration() {
    OrderedSet<String> orderedSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    assertFalse(orderedSet.addAll(orderedSet));

    int i = 9;
    Iterator<String> iterator = orderedSet.reversedIterator();
    while (iterator.hasNext()) {
      String it = iterator.next();
      assertEquals(String.valueOf(i), it);
      i--;
    }

    iterator = orderedSet.reversedIterator();
    int j = 9;
    while (iterator.hasNext()) {
      iterator.next();
      iterator.remove();

      assertEquals(j, orderedSet.getValueList().size());

      int lastJ = 0;
      for (String it : orderedSet) {
        assertEquals(String.valueOf(lastJ++), it);
      }

      assertEquals(lastJ, j);
      j--;
    }
  }

  @Test
  public void testConcurrentMod() {
    OrderedSet<String> orderedSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    assertFalse(orderedSet.addAll(orderedSet));

    Iterator<String> iterator = orderedSet.iterator();

    orderedSet.removeIndex(0);
    Assert.assertThrows(ConcurrentModificationException.class, () -> iterator.next());
  }

  @Test
  public void testSetConflict() {
    OrderedSet<String> orderedSet = new OrderedSet<>();

    for (int i = 0; i < 10; i++) {
      assertTrue(orderedSet.add(String.valueOf(i)));
      assertFalse(orderedSet.add(String.valueOf(i)));
    }

    String string = Integer.toString(1);
    Assert.assertThrows(IllegalStateException.class, () -> orderedSet.setValueAt(0, string, "1"));
  }
}
