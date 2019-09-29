package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class YamlFrontMatterVisitorExt {
    public static <V extends YamlFrontMatterVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<YamlFrontMatterNode>(YamlFrontMatterNode.class, visitor::visit),
                new VisitHandler<YamlFrontMatterBlock>(YamlFrontMatterBlock.class, visitor::visit),
        };
    }
}
