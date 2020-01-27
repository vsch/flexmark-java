package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkNodeBase;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;

import java.util.Arrays;

public class FormatterWithMods {
    final private static DataHolder OPTIONS = new MutableDataSet();

    static final MutableDataSet FORMAT_OPTIONS = new MutableDataSet();
    static {
        // copy extensions from Pegdown compatible to Formatting
        FORMAT_OPTIONS.set(Parser.EXTENSIONS, Parser.EXTENSIONS.get(OPTIONS));
    }

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final Formatter RENDERER = Formatter.builder(FORMAT_OPTIONS).build();

    static class LinkNodeVisitor {
        NodeVisitor visitor = new NodeVisitor(
                new VisitHandler<>(Link.class, this::visit),
                new VisitHandler<>(Reference.class, this::visit),
                new VisitHandler<>(Image.class, this::visit)
        );

        public void replaceUrl(Node node) {
            visitor.visit(node);
        }

        private void visit(Link node) {
            visit((LinkNodeBase) node);
        }

        private void visit(Reference node) {
            visit((LinkNodeBase) node);
        }

        private void visit(Image node) {
            visit((LinkNodeBase) node);
        }

        private void visit(LinkNodeBase node) {
            if (node.getPageRef().endsWith("replace.com")) {
                node.setUrlChars(PrefixedSubSequence.prefixOf("http://replaced.com", node.getPageRef().getEmptyPrefix()));
                node.setChars(SegmentedSequence.create(node.getChars(), Arrays.asList(node.getSegmentsForChars())));
            }
        }
    }

    // use the PARSER to parse and RENDERER to parse pegdown indentation rules and render CommonMark
    public static void main(String[] args) {
        String original = "#Heading\n" +
                "-----\n" +
                "paragraph text \n" +
                "[Not Changed](https://example.com#23)\n" +
                "lazy continuation\n" +
                "[Changed](https://replace.com#abc)\n" +
                "\n" +
                "[reference not changed]: https://example.com#23\n" +
                "[reference changed]: https://replace.com#abc\n" +
                "\n" +
                "";
        System.out.println("original\n");
        System.out.println(original);

        Node document = PARSER.parse(original);
        LinkNodeVisitor visitor = new LinkNodeVisitor();
        visitor.replaceUrl(document);

        String commonmark = RENDERER.render(document);

        System.out.println("\n\nCommonMark\n");
        System.out.println(commonmark);
    }
}
