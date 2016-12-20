package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class SimTocVisitorExt {
    static <V extends SimTocVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(SimTocBlock.class, new Visitor<SimTocBlock>() {
                    @Override
                    public void visit(SimTocBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SimTocOptionList.class, new Visitor<SimTocOptionList>() {
                    @Override
                    public void visit(SimTocOptionList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SimTocOption.class, new Visitor<SimTocOption>() {
                    @Override
                    public void visit(SimTocOption node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SimTocContent.class, new Visitor<SimTocContent>() {
                    @Override
                    public void visit(SimTocContent node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
