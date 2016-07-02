package com.vladsch.flexmark.test;

import com.vladsch.flexmark.internal.util.AbstractVisitor;
import com.vladsch.flexmark.node.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class AbstractVisitorTest {

    @Test
    public void replacingNodeInVisitorShouldNotDestroyVisitOrder() {
        Visitor visitor = new AbstractVisitor() {
            @Override
            public void visit(Text text) {
                text.insertAfter(new Code(text.getChars()));
                text.unlink();
            }
        };

        Paragraph paragraph = new Paragraph();
        paragraph.appendChild(new Text("foo"));
        paragraph.appendChild(new Text("bar"));

        paragraph.accept(visitor);

        assertCode("foo", paragraph.getFirstChild());
        assertCode("bar", paragraph.getFirstChild().getNext());
        assertNull(paragraph.getFirstChild().getNext().getNext());
        assertCode("bar", paragraph.getLastChild());
    }

    private static void assertCode(String expectedLiteral, Node node) {
        assertEquals("Expected node to be a Code node: " + node, Code.class, node.getClass());
        Code code = (Code) node;
        assertEquals(expectedLiteral, code.getChars().toString());
    }
}
