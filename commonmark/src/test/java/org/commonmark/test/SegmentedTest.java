package org.commonmark.test;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SegmentedTest {

    @Test
    public void emphasisDelimiters() {
        String input = "* *emphasis* \n"
                + "* **strong** \n"
                + "* _important_ \n"
                + "* __CRITICAL__ \n";

        Parser parser = Parser.builder().build();
        Node document = parser.parse(input);

        final List<DelimitedNode> list = new ArrayList<>();
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

        DelimitedNode emphasis = list.get(0);
        DelimitedNode strong = list.get(1);
        DelimitedNode important = list.get(2);
        DelimitedNode critical = list.get(3);

        assertEquals("*", String.valueOf(emphasis.getOpeningMarkerChars()));
        assertEquals("*", String.valueOf(emphasis.getClosingMarkerChars()));
        assertEquals("**", String.valueOf(strong.getOpeningMarkerChars()));
        assertEquals("**", String.valueOf(strong.getClosingMarkerChars()));
        assertEquals("_", String.valueOf(important.getOpeningMarkerChars()));
        assertEquals("_", String.valueOf(important.getClosingMarkerChars()));
        assertEquals("__", String.valueOf(critical.getOpeningMarkerChars()));
        assertEquals("__", String.valueOf(critical.getClosingMarkerChars()));
    }
}
