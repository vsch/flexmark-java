package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeAdaptingVisitor;

public interface CustomNodeDocxRenderer<N extends Node> extends NodeAdaptingVisitor<N> {
    void render(N node, DocxRendererContext context);
}
