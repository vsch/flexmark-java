package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.NodeAdaptingVisitor;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.util.html.Attributes;

public interface AttributeProvidingVisitor<N extends Node> extends NodeAdaptingVisitor<N> {
    void setAttributes(N node, AttributablePart part, Attributes attributes);
}
