package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.vladsch.flexmark.util.misc.BitFieldSet.any;

public class IndentedCodeBlock extends Block implements TextContainer {

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public IndentedCodeBlock() {
    }

    public IndentedCodeBlock(BasedSequence chars) {
        super(chars);
    }

    public IndentedCodeBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public IndentedCodeBlock(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        final BasedSequence chars = getContentChars();
        if (any(flags, F_NODE_TEXT)) {
            out.append(chars);
        } else {
            ReplacedTextMapper textMapper = new ReplacedTextMapper(chars);
            BasedSequence unescaped = Escaping.unescape(chars, textMapper);
            if (!unescaped.isEmpty()) {
                out.append(unescaped);
            }
        }
        return false;
    }
}
