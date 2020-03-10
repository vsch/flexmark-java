package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.InlineLinkNode;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.AstCollectingVisitor;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FormatterModifiedAST {

    final static DataHolder OPTIONS = new MutableDataSet();

    final static MutableDataSet FORMAT_OPTIONS = new MutableDataSet();
    static {
        // copy extensions from Pegdown compatible to Formatting
        FORMAT_OPTIONS.set(Parser.EXTENSIONS, Parser.EXTENSIONS.get(OPTIONS));
    }

    final static Parser PARSER = Parser.builder(OPTIONS).build();
    final static Formatter RENDERER = Formatter.builder(FORMAT_OPTIONS).build();

    @Test
    public void test_HtmlBlock1() throws Exception {
        final String input = "" +
                "line 1\n" +
                "\n" +
                "<img src=\"i.jpg\">\n" +
                "\n" +
                "line 2" +
                "\n";
        final String expected = "" +
                "line 1\n" +
                "\n" +
                "<img src=\"replaced.png\">\n" +
                "\n" +
                "line 2" +
                "\n";
        Node document = PARSER.parse(input);

        Node html = document.getFirstChild().getNext();
        html.setChars(PrefixedSubSequence.prefixOf("<img src=\"replaced.png\">\n", html.getChars(), 0, 0));

        String formatted = RENDERER.render(document);

        assertEquals(expected, formatted);

        //System.out.println("input\n");
        //System.out.println(input);
        //System.out.println("\n\nFormatted\n");
        //System.out.println(formatted);
    }

    public void visit(InlineLinkNode linkNode, final String replacemnt) {
        // This is called for all Text nodes. Override other visit methods for other node types.
        linkNode.setUrlChars(PrefixedSubSequence.prefixOf(replacemnt, linkNode.getUrl(), 0, 0));
        linkNode.setCharsFromSegments();
    }

    @Test
    public void test_ListItemNoMods() throws Exception {
        final String input = "" +
                "- [link](link.txt)\n" +
                "\n" +
                "next line\n" +
                "\n";
        final String expected = "" +
                "- [link](link.txt)\n" +
                "\n" +
                "next line\n" +
                "\n";
        Node document = PARSER.parse(input);
        String formatted = RENDERER.render(document);

        assertEquals(expected, formatted);

        //System.out.println("input\n");
        //System.out.println(input);
        //System.out.println("\n\nFormatted\n");
        //System.out.println(formatted);
    }

    @Test
    public void test_ListItem() throws Exception {
        final String input = "" +
                "- [link](link.txt) and ![image](img.jpg)\n" +
                "\n" +
                "next line\n" +
                "\n";
        final String expected = "" +
                "- [link](replaced.txt) and ![image](replaced.png)\n" +
                "\n" +
                "next line\n" +
                "\n";
        Node document = PARSER.parse(input);

        NodeVisitor[] visitor = new NodeVisitor[1];

        visitor[0] = new NodeVisitor(
                new VisitHandler<>(Link.class, node -> {
                    FormatterModifiedAST.this.visit(node, "replaced.txt");
                    visitor[0].visitChildren(node);
                }),
                new VisitHandler<>(Image.class, node -> {
                    FormatterModifiedAST.this.visit(node, "replaced.png");
                    visitor[0].visitChildren(node);
                })
        );

        visitor[0].visit(document);

        String formatted = RENDERER.render(document);

        assertEquals(expected, formatted);

        //System.out.println("input\n");
        //System.out.println(input);
        //System.out.println("\n\nFormatted\n");
        //System.out.println(formatted);
    }

    @Test
    public void test_LinkAnchors() throws Exception {
        final String input = "" +
                "- [link](link.txt) and ![image](img.jpg)\n" +
                "\n" +
                "next line\n" +
                "\n";
        final String expected = "" +
                "- [link](replaced.txt#anchor) and ![image](replaced.png)\n" +
                "\n" +
                "next line\n" +
                "\n";
        Node document = PARSER.parse(input);

        final NodeVisitor[] visitor = new NodeVisitor[1];

        visitor[0] = new NodeVisitor(
                new VisitHandler<>(Link.class, node -> {
                    FormatterModifiedAST.this.visit(node, "replaced.txt#anchor");
                    visitor[0].visitChildren(node);
                }),
                new VisitHandler<>(Image.class, node -> {
                    FormatterModifiedAST.this.visit(node, "replaced.png");
                    visitor[0].visitChildren(node);
                })
        );

        visitor[0].visit(document);

        String ast = new AstCollectingVisitor().collectAndGetAstText(document);
        assertEquals("Document[0, 53]\n" +
                "  BulletList[0, 41] isTight\n" +
                "    BulletListItem[0, 41] open:[0, 1, \"-\"] isTight hadBlankLineAfter\n" +
                "      Paragraph[2, 41] isTrailingBlankLine\n" +
                "        Link[2, 18] textOpen:[2, 3, \"[\"] text:[3, 7, \"link\"] textClose:[7, 8, \"]\"] linkOpen:[8, 9, \"(\"] url:[9, 9, \"replaced.txt#anchor\"] pageRef:[9, 9, \"replaced.txt\"] anchorMarker:[9, 9, \"#\"] anchorRef:[9, 9, \"anchor\"] linkClose:[17, 18, \")\"]\n" +
                "          Text[3, 7] chars:[3, 7, \"link\"]\n" +
                "        Text[18, 23] chars:[18, 23, \" and \"]\n" +
                "        Image[23, 40] textOpen:[23, 25, \"![\"] text:[25, 30, \"image\"] textClose:[30, 31, \"]\"] linkOpen:[31, 32, \"(\"] url:[32, 32, \"replaced.png\"] pageRef:[32, 32, \"replaced.png\"] linkClose:[39, 40, \")\"]\n" +
                "          Text[25, 30] chars:[25, 30, \"image\"]\n" +
                "  Paragraph[42, 52] isTrailingBlankLine\n" +
                "    Text[42, 51] chars:[42, 51, \"next line\"]\n", ast);

        String formatted = RENDERER.render(document);

        assertEquals(expected, formatted);

        //System.out.println("input\n");
        //System.out.println(input);
        //System.out.println("\n\nFormatted\n");
        //System.out.println(formatted);
    }

    @Test
    public void test_LinkText() throws Exception {
        MutableDataSet options = new MutableDataSet(OPTIONS).set(Formatter.OPTIMIZED_INLINE_RENDERING, false);
        Parser PARSER = Parser.builder(options).build();
        Formatter RENDERER = Formatter.builder(FORMAT_OPTIONS).build();

        final String input = "" +
                "- [link](link.txt)\n" +
                "\n" +
                "next line\n" +
                "\n";
        final String expected = "" +
                "- [LINK](link.txt)\n" +
                "\n" +
                "next line\n" +
                "\n";
        Node document = PARSER.parse(input);

        final NodeVisitor[] visitor = new NodeVisitor[1];

        visitor[0] = new NodeVisitor(
                new VisitHandler<>(Link.class, node -> {
                    ReversiblePeekingIterator<Node> childIte = node.getChildren().iterator();
                    while (childIte.hasNext()) {
                        Node child = childIte.next();
                        if (child instanceof Text) {
                            Text text = new Text("LINK");

                            // have to update the parent link text to reflect changes to AST
                            //node.setText(text.getChars());

                            child.insertAfter(text);
                            childIte.remove();
                        }
                    }
                    visitor[0].visitChildren(node);
                })
        );

        visitor[0].visit(document);

        String ast = new AstCollectingVisitor().collectAndGetAstText(document);
        assertEquals("Document[0, 31]\n" +
                "  BulletList[0, 19] isTight\n" +
                "    BulletListItem[0, 19] open:[0, 1, \"-\"] isTight hadBlankLineAfter\n" +
                "      Paragraph[2, 19] isTrailingBlankLine\n" +
                "        Link[2, 18] textOpen:[2, 3, \"[\"] text:[3, 7, \"link\"] textClose:[7, 8, \"]\"] linkOpen:[8, 9, \"(\"] url:[9, 17, \"link.txt\"] pageRef:[9, 17, \"link.txt\"] linkClose:[17, 18, \")\"]\n" +
                "          Text[0, 4] chars:[0, 4, \"LINK\"]\n" +
                "  Paragraph[20, 30] isTrailingBlankLine\n" +
                "    Text[20, 29] chars:[20, 29, \"next line\"]\n", ast);

        String formatted = RENDERER.render(document);

        assertEquals(expected, formatted);

        //System.out.println("input\n");
        //System.out.println(input);
        //System.out.println("\n\nFormatted\n");
        //System.out.println(formatted);
    }

    @Test
    public void test_CRLFInFencedCodeBlock() throws Exception {

        final String input =
                "following sample:\n" +
                "\n" +
                "```xml\n" +
                "<dependency>\r\n" +
                "    <groupId>com.vladsch.flexmark</groupId>\r\n" +
                "    <artifactId>flexmark-all</artifactId>\r\n" +
                "    <version>0.60.2</version>\r\n" +
                "</dependency>\r\n" +
                "```\n\n";

        final String expected =
                "following sample:\n" +
                "\n" +
                "```xml\n" +
                "<dependency>\r\n" +
                "    <groupId>com.vladsch.flexmark</groupId>\r\n" +
                "    <artifactId>flexmark-all</artifactId>\r\n" +
                "    <version>0.60.2</version>\r\n" +
                "</dependency>\r\n" +
                "```\n\n";

        Node document = PARSER.parse(input);

        String formatted = RENDERER.render(document);

        assertEquals(expected,formatted);
    }
}
