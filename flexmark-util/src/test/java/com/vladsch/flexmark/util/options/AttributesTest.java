package com.vladsch.flexmark.util.options;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.vladsch.flexmark.util.html.MutableAttributes;
import org.junit.Test;

public class AttributesTest {
  @Test
  public void testBasic() {
    MutableAttributes attributes = new MutableAttributes();

    assertFalse("empty no attributes", attributes.contains("class"));
    assertFalse("empty no values", attributes.containsValue("class", "class1"));

    attributes.addValue("class", "class1");
    assertEquals("add value", "class1", attributes.getValue("class"));
    assertTrue("contains added attribute", attributes.contains("class"));
    assertTrue("contains added value", attributes.containsValue("class", "class1"));

    attributes.addValue("class", "class2");
    assertEquals("add value", "class1 class2", attributes.getValue("class"));
    assertTrue("contains added attribute", attributes.contains("class"));
    assertTrue("contains old value", attributes.containsValue("class", "class1"));
    assertTrue("contains added value", attributes.containsValue("class", "class2"));

    attributes.addValue("class", "class3");
    assertEquals("add value", "class1 class2 class3", attributes.getValue("class"));
    assertTrue("contains added attribute", attributes.contains("class"));
    assertTrue("contains old value", attributes.containsValue("class", "class1"));
    assertTrue("contains added value", attributes.containsValue("class", "class2"));
    assertTrue("contains added value", attributes.containsValue("class", "class3"));

    attributes.removeValue("class", "class2");
    assertEquals("removed value", "class1 class3", attributes.getValue("class"));
    assertTrue("contains removed value attribute", attributes.contains("class"));
    assertTrue("contains old value", attributes.containsValue("class", "class1"));
    assertFalse("does not contain removed value", attributes.containsValue("class", "class2"));
    assertTrue("contains old value", attributes.containsValue("class", "class3"));

    attributes.removeValue("class", "class3");
    assertEquals("removed value", "class1", attributes.getValue("class"));
    assertTrue("contains removed value attribute", attributes.contains("class"));
    assertTrue("contains old value", attributes.containsValue("class", "class1"));
    assertFalse("does not contain removed value", attributes.containsValue("class", "class2"));
    assertFalse("does not contain removed value", attributes.containsValue("class", "class3"));

    attributes.removeValue("class", "class1");
    assertEquals("removed value", "", attributes.getValue("class"));
    assertTrue("contains removed value attribute", attributes.contains("class"));
    assertFalse("does not contain removed value", attributes.containsValue("class", "class1"));
    assertFalse("does not contain removed value", attributes.containsValue("class", "class2"));
    assertFalse("does not contain removed value", attributes.containsValue("class", "class3"));

    attributes.replaceValue("class", "class1 class2 class3");
    assertEquals("replaced value", "class1 class2 class3", attributes.getValue("class"));
    assertTrue("contains value attribute", attributes.contains("class"));
    assertTrue("contains added values", attributes.containsValue("class", "class1"));
    assertTrue("contains added values", attributes.containsValue("class", "class2"));
    assertTrue("contains added values", attributes.containsValue("class", "class3"));

    attributes.addValue("id", "id1");
    assertEquals("add value", "id1", attributes.getValue("id"));
    assertTrue("contains added attribute", attributes.contains("id"));
    assertTrue("contains added value", attributes.containsValue("id", "id1"));
  }
}
