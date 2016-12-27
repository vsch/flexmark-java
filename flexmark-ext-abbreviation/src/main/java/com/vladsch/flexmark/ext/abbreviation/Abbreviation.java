package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A node containing the abbreviated text that will be rendered as an abbr tag or a link with title attribute
 */
public class Abbreviation extends Node implements DoNotDecorate {
    protected final BasedSequence abbreviation;

    public Abbreviation(BasedSequence chars, BasedSequence abbreviation) {
        super(chars);
        this.abbreviation = abbreviation;
    }

    public BasedSequence getAbbreviation() {
        return abbreviation;
    }

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
