package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class SimTocVisitorExt {
    public static <V extends SimTocVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<SimTocBlock>(SimTocBlock.class, new Visitor<SimTocBlock>() {
                    @Override
                    public void visit(SimTocBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SimTocOptionList>(SimTocOptionList.class, new Visitor<SimTocOptionList>() {
                    @Override
                    public void visit(SimTocOptionList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SimTocOption>(SimTocOption.class, new Visitor<SimTocOption>() {
                    @Override
                    public void visit(SimTocOption node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SimTocContent>(SimTocContent.class, new Visitor<SimTocContent>() {
                    @Override
                    public void visit(SimTocContent node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
