package com.vladsch.flexmark.ext.resizable.image;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class ResizableImage extends Node implements DoNotDecorate {
    protected BasedSequence source = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence width = BasedSequence.NULL;
    protected BasedSequence height = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { text, source, width, height };
    }

    public ResizableImage(BasedSequence text, BasedSequence source, BasedSequence width, BasedSequence height) {
        super(spanningChars(text, source, width, height));
        this.source = source;
        this.text = text;
        this.width = width;
        this.height = height;
    }

    public BasedSequence getText() {
        return text;
    }

    public BasedSequence getSource() {
        return source;
    }

    public BasedSequence getWidth() {
        return width;
    }

    public BasedSequence getHeight() {
        return height;
    }
}
