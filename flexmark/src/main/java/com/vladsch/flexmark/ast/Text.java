package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.util.misc.BitFieldSet.any;

final public class Text extends Node implements TextContainer {
    public Text() {
    }

    public Text(BasedSequence chars) {
        super(chars);
    }

    public Text(String chars) {
        super(BasedSequence.of(chars));
    }

    public Text(String chars, BasedSequence baseSeq) {
        super(PrefixedSubSequence.prefixOf(chars, baseSeq));
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        if (any(flags, F_NODE_TEXT)) {
            out.append(getChars());
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(getChars());
            BasedSequence unescaped = Escaping.unescape(getChars(), textMapper);
            if (!unescaped.isEmpty()) out.append(unescaped);
        }
        return false;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        astExtraChars(out);
        if (getChars() instanceof PrefixedSubSequence) {
            astChars(out, getChars(), "text");
        }
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "text=" + getChars();
    }
}
