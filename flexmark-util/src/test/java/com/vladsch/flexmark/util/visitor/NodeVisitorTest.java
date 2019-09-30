package com.vladsch.flexmark.util.visitor;

import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import org.junit.Test;

public class NodeVisitorTest {
    @Test
    public void test_basic() {
    }

    static class VisitTester {

        void test() {
            NodeVisitor myVisitor = new NodeVisitor(
                    new VisitHandler<>(Node.class, this::visit),
                    new VisitHandler<>(Block.class, this::visit),
                    new VisitHandler<>(Document.class, this::visit),
                    new VisitHandler<>(BlankLine.class, this::visit)
            );

        }

        private void visit(Node node) {

        }

        private void visit(Block node) {

        }

        private void visit(BlankLine node) {

        }

        private void visit(Document node) {

        }


    }
}
