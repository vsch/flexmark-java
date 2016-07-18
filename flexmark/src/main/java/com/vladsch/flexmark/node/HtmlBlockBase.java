package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.List;

/**
 * HTML block
 *
 * @see <a href="http://spec.commonmark.org/0.18/#html-blocks">CommonMark Spec</a>
 */
public abstract class HtmlBlockBase extends Block {
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
