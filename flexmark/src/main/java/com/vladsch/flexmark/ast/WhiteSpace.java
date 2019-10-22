package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * Only generated for CharacterNodeFactory custom parsing
 */
public class WhiteSpace extends Node {
    public WhiteSpace() {
    }

    public WhiteSpace(BasedSequence chars) {
        super(chars);
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        astExtraChars(out);
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
