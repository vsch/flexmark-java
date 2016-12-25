package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.AttributeImpl;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AttributeTest {

    @Test
    public void testBasic() throws Exception {
        Attribute attribute = AttributeImpl.of("name", "value1", ' ');
        assertEquals("no name change", "name", attribute.getName());

        assertEquals("contains a simple value", true, attribute.containsValue("value1"));

        Attribute attribute1 = attribute.setValue("value2");
        assertEquals("add a new value", "value1 value2", attribute1.getValue());
        assertEquals("non-equality", false, attribute1.equals(attribute));
        assertEquals("no name change", "name", attribute1.getName());

        Attribute attribute2 = attribute.removeValue("value2");
        assertEquals("remove non-existent value", "value1", attribute.getValue());
        assertEquals("remove non-existent value, no new attribute", attribute2, attribute);
        assertEquals("equality", true, attribute2.equals(attribute));
        assertEquals("no name change", "name", attribute2.getName());

        Attribute attribute3 = attribute.replaceValue("value2");
        assertEquals("replace value", "value2", attribute3.getValue());
        assertEquals("no name change", "name", attribute3.getName());

        Attribute attribute4 = attribute1.setValue("value1");
        assertEquals("add existing value", "value1 value2", attribute4.getValue());
        assertEquals("add existing value, no new attribute", attribute4, attribute1);
        assertEquals("no name change", "name", attribute4.getName());

        Attribute attribute5 = attribute1.setValue("value1");
        assertEquals("add existing value", "value1 value2", attribute5.getValue());
        assertEquals("add existing value, no new attribute", attribute5, attribute1);
        assertEquals("no name change", "name", attribute5.getName());

        Attribute attribute6 = attribute1.setValue("value2");
        assertEquals("add existing value", "value1 value2", attribute6.getValue());
        assertEquals("add existing value, no new attribute", attribute6, attribute1);
        assertEquals("no name change", "name", attribute6.getName());

        Attribute attribute7 = attribute1.setValue("value3");
        assertEquals("add existing value", "value1 value2 value3", attribute7.getValue());
        assertEquals("no name change", "name", attribute7.getName());

        Attribute attribute8 = attribute7.removeValue("value2");
        assertEquals("remove middle value", "value1 value3", attribute8.getValue());
        assertEquals("non-equality", false, attribute8.equals(attribute7));
        assertEquals("no name change", "name", attribute8.getName());

        Attribute attribute9 = attribute3.replaceValue("value2");
        assertEquals("replace value", "value2", attribute9.getValue());
        assertEquals("replace same value, no new attribute", attribute9, attribute3);
        assertEquals("no name change", "name", attribute9.getName());
    }
}
