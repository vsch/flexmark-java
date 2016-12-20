package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class YamlFrontMatterVisitorExt {
    static <V extends YamlFrontMatterVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(YamlFrontMatterNode.class, new Visitor<YamlFrontMatterNode>() {
                    @Override
                    public void visit(YamlFrontMatterNode node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(YamlFrontMatterBlock.class, new Visitor<YamlFrontMatterBlock>() {
                    @Override
                    public void visit(YamlFrontMatterBlock node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
