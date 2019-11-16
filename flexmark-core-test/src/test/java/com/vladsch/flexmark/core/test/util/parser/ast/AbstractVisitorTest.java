package com.vladsch.flexmark.core.test.util.parser.ast;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

final public class AbstractVisitorTest {

    @Test
    public void replacingNodeInVisitorShouldNotDestroyVisitOrder() {
        NodeVisitor visitor = new NodeVisitor(
                new VisitHandler<>(Text.class, node -> {
                    node.insertAfter(new Code(node.getChars()));
                    node.unlink();
                })
        );

        Paragraph paragraph = new Paragraph();
        paragraph.appendChild(new Text("foo"));
        paragraph.appendChild(new Text("bar"));

        visitor.visit(paragraph);

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
