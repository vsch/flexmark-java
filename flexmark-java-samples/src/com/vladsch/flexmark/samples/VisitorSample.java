package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeVisitor;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.parser.Parser;

@SuppressWarnings({ "unchecked", "WeakerAccess" })
public class VisitorSample {
    int wordCount;

    // example of visitor for a node or nodes, just add VisitHandlers<> to the list
    // any node type not handled by the visitor will default to visiting its children
    NodeVisitor visitor = new NodeVisitor(
            new VisitHandler<>(Text.class, this::visit)
    );

    public void visit(Text text) {
        // This is called for all Text nodes. Override other visit methods for other node types.
        wordCount += text.getChars().unescape().split("\\W+").length;

        // Descending into children
        visitor.visitChildren(text);

        // Count words (this is just an example, don't actually do it this way for various reasons).
    }

    void countWords() {
        Parser parser = Parser.builder().build();
        Node document = parser.parse("Example\n=======\n\nSome more text");

        System.out.println(wordCount);  // 4
    }
}
