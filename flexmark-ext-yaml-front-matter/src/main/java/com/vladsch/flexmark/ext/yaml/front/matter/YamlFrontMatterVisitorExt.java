package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class YamlFrontMatterVisitorExt {
    public static <V extends YamlFrontMatterVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<YamlFrontMatterNode>(YamlFrontMatterNode.class, new Visitor<YamlFrontMatterNode>() {
                    @Override
                    public void visit(YamlFrontMatterNode node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<YamlFrontMatterBlock>(YamlFrontMatterBlock.class, new Visitor<YamlFrontMatterBlock>() {
                    @Override
                    public void visit(YamlFrontMatterBlock node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
