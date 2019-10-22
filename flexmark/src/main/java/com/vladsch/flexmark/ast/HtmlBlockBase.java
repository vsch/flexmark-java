package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * HTML block
 *
 * @see <a href="http://spec.commonmark.org/0.18/#html-blocks">CommonMark Spec</a>
 */
public abstract class HtmlBlockBase extends Block {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public HtmlBlockBase() {
    }

    public HtmlBlockBase(BasedSequence chars) {
        super(chars);
    }

    public HtmlBlockBase(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public HtmlBlockBase(BlockContent blockContent) {
        super(blockContent);
    }
}
