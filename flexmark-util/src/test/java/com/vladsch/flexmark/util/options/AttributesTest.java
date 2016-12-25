package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.html.Attributes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributesTest {
    @Test
    public void testBasic() throws Exception {
        Attributes attributes = new Attributes();

        assertEquals("empty no attributes", false, attributes.contains("class"));
        assertEquals("empty no values", false, attributes.containsValue("class", "class1"));

        attributes.addValue("class", "class1");
        assertEquals("add value", "class1", attributes.getValue("class"));
        assertEquals("contains added attribute", true, attributes.contains("class"));
        assertEquals("contains added value", true, attributes.containsValue("class", "class1"));

        attributes.addValue("class", "class2");
        assertEquals("add value", "class1 class2", attributes.getValue("class"));
        assertEquals("contains added attribute", true, attributes.contains("class"));
        assertEquals("contains old value", true, attributes.containsValue("class", "class1"));
        assertEquals("contains added value", true, attributes.containsValue("class", "class2"));

        attributes.addValue("class", "class3");
        assertEquals("add value", "class1 class2 class3", attributes.getValue("class"));
        assertEquals("contains added attribute", true, attributes.contains("class"));
        assertEquals("contains old value", true, attributes.containsValue("class", "class1"));
        assertEquals("contains added value", true, attributes.containsValue("class", "class2"));
        assertEquals("contains added value", true, attributes.containsValue("class", "class3"));

        attributes.removeValue("class", "class2");
        assertEquals("removed value", "class1 class3", attributes.getValue("class"));
        assertEquals("contains removed value attribute", true, attributes.contains("class"));
        assertEquals("contains old value", true, attributes.containsValue("class", "class1"));
        assertEquals("does not contain removed value", false, attributes.containsValue("class", "class2"));
        assertEquals("contains old value", true, attributes.containsValue("class", "class3"));

        attributes.removeValue("class", "class3");
        assertEquals("removed value", "class1", attributes.getValue("class"));
        assertEquals("contains removed value attribute", true, attributes.contains("class"));
        assertEquals("contains old value", true, attributes.containsValue("class", "class1"));
        assertEquals("does not contain removed value", false, attributes.containsValue("class", "class2"));
        assertEquals("does not contain removed value", false, attributes.containsValue("class", "class3"));

        attributes.removeValue("class", "class1");
        assertEquals("removed value", "", attributes.getValue("class"));
        assertEquals("contains removed value attribute", true, attributes.contains("class"));
        assertEquals("does not contain removed value", false, attributes.containsValue("class", "class1"));
        assertEquals("does not contain removed value", false, attributes.containsValue("class", "class2"));
        assertEquals("does not contain removed value", false, attributes.containsValue("class", "class3"));

        attributes.replaceValue("class", "class1 class2 class3");
        assertEquals("replaced value", "class1 class2 class3", attributes.getValue("class"));
        assertEquals("contains value attribute", true, attributes.contains("class"));
        assertEquals("contains added values", true, attributes.containsValue("class", "class1"));
        assertEquals("contains added values", true, attributes.containsValue("class", "class2"));
        assertEquals("contains added values", true, attributes.containsValue("class", "class3"));

        attributes.addValue("id", "id1");
        assertEquals("add value", "id1", attributes.getValue("id"));
        assertEquals("contains added attribute", true, attributes.contains("id"));
        assertEquals("contains added value", true, attributes.containsValue("id", "id1"));

    }
}
