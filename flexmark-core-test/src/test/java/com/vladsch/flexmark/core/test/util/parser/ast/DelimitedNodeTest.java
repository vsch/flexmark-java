package com.vladsch.flexmark.core.test.util.parser.ast;

import com.vladsch.flexmark.ast.Emphasis;
import com.vladsch.flexmark.ast.StrongEmphasis;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

final public class DelimitedNodeTest {

    @Test
    public void emphasisDelimiters() {
        String input = "* *emphasis* \n"
                + "* **strong** \n"
                + "* _important_ \n"
                + "* __CRITICAL__ \n";

        Parser parser = Parser.builder().build();
        Node document = parser.parse(input);

        final List<DelimitedNode> list = new ArrayList<>();
        NodeVisitor visitor = new NodeVisitor(
                new VisitHandler<>(Emphasis.class, list::add),
                new VisitHandler<>(StrongEmphasis.class, list::add)
        );

        visitor.visit(document);

        assertEquals(4, list.size());

        DelimitedNode emphasis = list.get(0);
        DelimitedNode strong = list.get(1);
        DelimitedNode important = list.get(2);
        DelimitedNode critical = list.get(3);

        assertEquals("*", String.valueOf(emphasis.getOpeningMarker()));
        assertEquals("*", String.valueOf(emphasis.getClosingMarker()));
        assertEquals("**", String.valueOf(strong.getOpeningMarker()));
        assertEquals("**", String.valueOf(strong.getClosingMarker()));
        assertEquals("_", String.valueOf(important.getOpeningMarker()));
        assertEquals("_", String.valueOf(important.getClosingMarker()));
        assertEquals("__", String.valueOf(critical.getOpeningMarker()));
        assertEquals("__", String.valueOf(critical.getClosingMarker()));
    }
}
