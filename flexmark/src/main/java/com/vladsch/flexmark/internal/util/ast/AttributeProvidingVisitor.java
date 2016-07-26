package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.internal.util.options.Attributes;
import com.vladsch.flexmark.node.Node;

public interface AttributeProvidingVisitor<N extends Node> extends NodeAdaptingVisitor<N> {
    void setAttributes(N node, AttributablePart part, Attributes attributes);
}
