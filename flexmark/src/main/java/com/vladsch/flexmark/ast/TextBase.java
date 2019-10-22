package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import org.jetbrains.annotations.NotNull;

public class TextBase extends Node {
    public TextBase() {
    }

    public TextBase(BasedSequence chars) {
        super(chars);
    }

    public TextBase(String chars) {
        super(CharSubSequence.of(chars));
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        astExtraChars(out);
    }

    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
