package org.commonmark.node;

public class Document extends Block {
    private final CharSequence charSequence;

    public Document(CharSequence charSequence) {
        super(0, charSequence.length());
        this.charSequence = charSequence;
    }

    @Override
    public CharSequence getCharSequence() {
        return charSequence;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
