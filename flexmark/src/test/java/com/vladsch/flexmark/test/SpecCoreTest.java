package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.node.AbstractVisitor;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.node.Text;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class SpecCoreTest extends SpecTestCase {

    private static final Parser PARSER = Parser.builder().build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().percentEncodeUrls(true).build();

    public SpecCoreTest(SpecExample example) {
        super(example);
    }

    @Test
    public void testTextNodesContiguous() {
        final String source = example.getSource();
        Node node = PARSER.parse(source);
        node.accept(new AbstractVisitor() {
            @Override
            protected void visitChildren(Node parent) {
                if (parent instanceof Text && parent.getFirstChild() != null) {
                    fail("Text node is not allowed to have children, literal is \"" + ((Text) parent).getChars() + "\"");
                }
                boolean lastText = false;
                Node node = parent.getFirstChild();
                while (node != null) {
                    if (node instanceof Text) {
                        if (lastText) {
                            fail("Adjacent text nodes found, second node literal is \"" + ((Text) node).getChars() + "\", source:\n" + source);
                        }
                        lastText = true;
                    } else {
                        lastText = false;
                    }
                    node = node.getNext();
                }
                super.visitChildren(parent);
            }
        });
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples("/ast_spec.txt");
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    protected Node parse(String source) {
        return PARSER.parse(source);
    }

    @Override
    protected String render(Node node) {
        return RENDERER.render(node);
    }
}
