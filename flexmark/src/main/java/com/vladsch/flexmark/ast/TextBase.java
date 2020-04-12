package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.misc.BitFieldSet.any;

public class TextBase extends Node implements TextContainer {
    public TextBase() {
    }

    public TextBase(BasedSequence chars) {
        super(chars);
    }

    public TextBase(String chars) {
        super(BasedSequence.of(chars));
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

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
