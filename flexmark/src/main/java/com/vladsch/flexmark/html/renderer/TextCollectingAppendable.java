package com.vladsch.flexmark.html.renderer;

import java.io.IOException;

public class TextCollectingAppendable implements Appendable {
    private final StringBuilder out;

    public TextCollectingAppendable() {
        this(new StringBuilder());
    }

    public TextCollectingAppendable(StringBuilder out) {
        this.out = out;
    }

    public String getHtml() {
        return out.toString();
    }

    @Override
    public Appendable append(CharSequence sequence) throws IOException {
        return out.append(sequence);
    }

    @Override
    public Appendable append(CharSequence sequence, int i, int i1) throws IOException {
        return out.append(sequence, i, i1);
    }

    @Override
    public Appendable append(char c) throws IOException {
        return out.append(c);
    }
}
