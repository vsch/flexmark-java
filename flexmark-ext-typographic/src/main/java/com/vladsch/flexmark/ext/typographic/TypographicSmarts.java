package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.util.ast.DoNotAttributeDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TypographicText;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.misc.BitFieldSet.any;

/**
 * A TypographicSmarts node
 */
public class TypographicSmarts extends Node implements DoNotAttributeDecorate, TypographicText {
    private String typographicText;

    public TypographicSmarts() {
    }

    public TypographicSmarts(BasedSequence chars) {
        super(chars);
    }

    public TypographicSmarts(String typographicText) {
        this.typographicText = typographicText;
    }

    public TypographicSmarts(BasedSequence chars, String typographicText) {
        super(chars);
        this.typographicText = typographicText;
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        if (any(flags, F_NODE_TEXT)) {
            out.append(getChars());
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(getChars());
            BasedSequence unescaped = Escaping.unescape(getChars(), textMapper);
            out.append(unescaped);
        }
        return false;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        out.append(" typographic: ").append(typographicText).append(" ");
    }

    public String getTypographicText() {
        return typographicText;
    }

    public void setTypographicText(String typographicText) {
        this.typographicText = typographicText;
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
