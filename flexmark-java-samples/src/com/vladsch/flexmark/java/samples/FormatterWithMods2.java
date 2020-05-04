package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkNodeBase;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;

import java.util.Arrays;

public class FormatterWithMods2 {
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
            if (node.getPageRef().startsWith("/")) {
                node.setUrlChars(PrefixedSubSequence.prefixOf("https:", node.getPageRef()));
                node.setChars(node.getChars().getBuilder().addAll(Arrays.asList(node.getSegmentsForChars())).toSequence());
            }
        }
    }

    // use the PARSER to parse and RENDERER to parse pegdown indentation rules and render CommonMark
    public static void main(String[] args) {
        String html = "<img src=\"//img.alicdn.com/tfscom/TB1mR4xPpXXXXXvapXXXXXXXXXX.jpg\" >";
        String markdown = FlexmarkHtmlConverter.builder().build().convert(html);

        System.out.println("html\n");
        System.out.println(html);

        System.out.println("markdown\n");
        System.out.println(markdown);

        Node document = PARSER.parse(markdown);
        LinkNodeVisitor visitor = new LinkNodeVisitor();
        visitor.replaceUrl(document);

        String commonmark = RENDERER.render(document);

        System.out.println("\n\nCommonMark\n");
        System.out.println(commonmark);
    }
}
