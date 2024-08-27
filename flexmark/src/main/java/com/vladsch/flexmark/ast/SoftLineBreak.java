package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

public class SoftLineBreak extends Node implements DoNotAttributeDecorate, DoNotTrim, TextContainer {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public SoftLineBreak() {
    }

    public SoftLineBreak(BasedSequence chars) {
        super(chars);
    }

    @Override
    public void setChars(@NotNull BasedSequence chars) {
        super.setChars(chars);
    }

    @Override
    public void setCharsFromContentOnly() {
        super.setCharsFromContentOnly();
    }

    @Override
    public void setCharsFromContent() {
        super.setCharsFromContent();
    }

    @Override
    public void setCharsFromSegments() {
        super.setCharsFromSegments();
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        out.add(getChars());
        return false;
    }
}
