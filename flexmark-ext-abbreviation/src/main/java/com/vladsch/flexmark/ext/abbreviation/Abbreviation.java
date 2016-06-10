package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Text;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class Abbreviation extends Text implements DoNotLinkify {
    protected final String abbreviation;

    public Abbreviation(BasedSequence chars, String abbreviation) {
        super(chars);
        this.abbreviation = abbreviation;
    }

    public Abbreviation(String chars, String abbreviation) {
        super(chars);
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
