package com.vladsch.flexmark.node;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.List;

/**
 * HTML block
 *
 * @see <a href="http://spec.commonmark.org/0.18/#html-blocks">CommonMark Spec</a>
 */
public class HtmlCommentBlock extends HtmlBlock {
    public HtmlCommentBlock() {
    }

    public HtmlCommentBlock(BasedSequence chars) {
        super(chars);
    }

    public HtmlCommentBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public HtmlCommentBlock(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
