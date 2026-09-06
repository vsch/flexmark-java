package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;

public class SoftLineBreak extends Node implements DoNotAttributeDecorate, DoNotTrim, TextContainer {
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
    public void setChars(BasedSequence chars) {
        super.setChars(chars);
        assert getChars().isNotEmpty();
    }

    @Override
    public void setCharsFromContentOnly() {
        super.setCharsFromContentOnly();
        assert getChars().isNotEmpty();
    }

    @Override
    public void setCharsFromContent() {
        super.setCharsFromContent();
        assert getChars().isNotEmpty();
    }

    @Override
    public void setCharsFromSegments() {
        super.setCharsFromSegments();
        assert getChars().isNotEmpty();
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        out.add(getChars());
        return false;
    }
}
