package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public interface ReferencingNode<R extends NodeRepository<B>, B extends ReferenceNode> {
    boolean isDefined();
    @NotNull BasedSequence getReference();
    B getReferenceNode(Document document);
    B getReferenceNode(R repository);
}
