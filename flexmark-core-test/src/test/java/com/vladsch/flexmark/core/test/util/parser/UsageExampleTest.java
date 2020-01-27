package com.vladsch.flexmark.core.test.util.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

final public class UsageExampleTest {

    @Test
    public void parseAndRender() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("This is *Sparta*");
        HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();
        assertEquals("<p>This is <em>Sparta</em></p>\n", renderer.render(document));
    }

    @Test
    public void visitor() {
        Parser parser = Parser.builder().build();
        Node node = parser.parse("Example\n=======\n\nSome more text");

        WordCountVisitor visitor = new WordCountVisitor();
        visitor.countWords(node);
        assertEquals(4, visitor.wordCount);
    }

    static class WordCountVisitor {
        int wordCount = 0;

        final private NodeVisitor myVisitor;

        public WordCountVisitor() {
            myVisitor = new NodeVisitor(
                    new VisitHandler<>(Text.class, WordCountVisitor.this::visit)
            );
        }

        public void countWords(Node node) {
            myVisitor.visit(node);
        }

        private void visit(Text text) {
            // This is called for all Text nodes. Override other visit methods for other node types.

            // Count words (this is just an example, don't actually do it this way for various reasons).
            wordCount += text.getChars().toString().split("\\W+").length;

            // Descend into children (could be omitted in this case because Text nodes don't have children).
            myVisitor.visitChildren(text);
        }
    }
}
