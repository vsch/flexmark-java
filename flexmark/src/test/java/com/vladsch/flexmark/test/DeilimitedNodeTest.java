package com.vladsch.flexmark.test;

import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.Parser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DeilimitedNodeTest {

    @Test
    public void emphasisDelimiters() {
        String input = "* *emphasis* \n"
                + "* **strong** \n"
                + "* _important_ \n"
                + "* __CRITICAL__ \n";

        Parser parser = Parser.builder().build();
        Node document = parser.parse(input);

        final List<DelimitedNodeImpl> list = new ArrayList<>();
        Visitor visitor = new AbstractVisitor() {
            @Override
            public void visit(Emphasis node) {
                list.add(node);
            }

            @Override
            public void visit(StrongEmphasis node) {
                list.add(node);
            }
        };
        document.accept(visitor);

        assertEquals(4, list.size());

        DelimitedNodeImpl emphasis = list.get(0);
        DelimitedNodeImpl strong = list.get(1);
        DelimitedNodeImpl important = list.get(2);
        DelimitedNodeImpl critical = list.get(3);

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
