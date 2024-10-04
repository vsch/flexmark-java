package com.vladsch.flexmark.util.collection;

import org.junit.Assert;
import org.junit.Test;

public class ClassificationBagTest {
  @Test
  public void testBasic() {
    ClassificationBag<Class<?>, Object> bag = new ClassificationBag<>(value -> value.getClass());

    Object item;
    for (int i = 0; i < 10; i++) {
      item = i;
      bag.add(item);
    }

    Assert.assertTrue(bag.containsCategory(Integer.class));
    Assert.assertEquals(10, bag.getCategoryCount(Integer.class));

    Assert.assertFalse(bag.containsCategory(String.class));
    Assert.assertEquals(0, bag.getCategoryCount(String.class));

    for (int i = 0; i < 10; i++) {
      item = String.valueOf(i);
      bag.add(item);
    }

    Assert.assertTrue(bag.containsCategory(String.class));
    Assert.assertEquals(10, bag.getCategoryCount(String.class));

    // now we removeIndex them
    for (int i = 0; i < 10; i += 2) {
      item = i;
      bag.remove(item);
    }

    Assert.assertTrue(bag.containsCategory(Integer.class));
    Assert.assertEquals(5, bag.getCategoryCount(Integer.class));

    Assert.assertTrue(bag.containsCategory(String.class));
    Assert.assertEquals(10, bag.getCategoryCount(String.class));

    // now we removeIndex them
    for (int i = 0; i < 10; i += 2) {
      item = String.valueOf(i);
      bag.remove(item);
    }

    Assert.assertTrue(bag.containsCategory(Integer.class));
    Assert.assertEquals(5, bag.getCategoryCount(Integer.class));

    Assert.assertTrue(bag.containsCategory(String.class));
    Assert.assertEquals(5, bag.getCategoryCount(String.class));

    // now we removeIndex them
    for (int i = 1; i < 10; i += 2) {
      item = i;
      bag.remove(item);
    }

    Assert.assertFalse(bag.containsCategory(Integer.class));
    Assert.assertEquals(0, bag.getCategoryCount(Integer.class));

    Assert.assertTrue(bag.containsCategory(String.class));
    Assert.assertEquals(5, bag.getCategoryCount(String.class));

    // now we removeIndex them
    for (int i = 1; i < 10; i += 2) {
      item = String.valueOf(i);
      bag.remove(item);
    }

    Assert.assertFalse(bag.containsCategory(Integer.class));
    Assert.assertEquals(0, bag.getCategoryCount(Integer.class));

    Assert.assertFalse(bag.containsCategory(String.class));
    Assert.assertEquals(0, bag.getCategoryCount(String.class));
  }

  @Test
  public void testInterleave() {
    ClassificationBag<Class<?>, Object> bag = new ClassificationBag<>(value -> value.getClass());

    Object item;
    for (int i = 0; i < 10; i++) {
      item = i;
      bag.add(item);
      item = String.valueOf(i);
      bag.add(item);
    }

    Assert.assertTrue(bag.containsCategory(Integer.class));
    Assert.assertEquals(10, bag.getCategoryCount(Integer.class));

    Assert.assertTrue(bag.containsCategory(String.class));
    Assert.assertEquals(10, bag.getCategoryCount(String.class));
  }
}
