package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitorBase;
import org.jetbrains.annotations.NotNull;

public class AstCollectingVisitor extends NodeVisitorBase {
    final public static String EOL = "\n";
    protected StringBuilder output = new StringBuilder();
    protected int indent = 0;
    protected boolean eolPending = false;

    public String getAst() {
        return output.toString();
    }

    public void clear() {
        output = new StringBuilder();
        indent = 0;
        eolPending = false;
    }

    protected void appendIndent() {
        for (int i = 0; i < indent * 2; i++) {
            output.append(' ');
        }
        eolPending = true;
    }

    protected void appendEOL() {
        output.append(EOL);
        eolPending = false;
    }

    protected void appendPendingEOL() {
        if (eolPending) appendEOL();
    }

    public String collectAndGetAstText(Node node) {
        visit(node);
        return getAst();
    }

    public void collect(Node node) {
        visit(node);
    }

    @Override
    protected void visit(@NotNull Node node) {
        appendIndent();
        node.astString(output, true);
        output.append(EOL);
        indent++;

        try {
            super.visitChildren(node);
        } finally {
            indent--;
        }
    }
}
