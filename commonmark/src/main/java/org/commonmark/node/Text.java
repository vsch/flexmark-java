package org.commonmark.node;

public class Text extends Node {
    public Text() {
    }

    public Text(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    protected String toStringAttributes() {
        CharSequence charSequence = getCharSequence();
        return "text=" + getText(charSequence);
    }
}
