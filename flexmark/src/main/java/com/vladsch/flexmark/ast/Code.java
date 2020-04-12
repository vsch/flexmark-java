package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

public class Code extends DelimitedNodeImpl implements DoNotLinkDecorate {
    public Code() {
    }

    public Code(BasedSequence chars) {
        super(chars);
    }

    public Code(BasedSequence openingMarker, BasedSequence content, BasedSequence closingMarker) {
        super(openingMarker, content, closingMarker);
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        delimitedSegmentSpan(out, openingMarker, text, closingMarker, "text");
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        out.append(getText());
        return false;
    }
}
