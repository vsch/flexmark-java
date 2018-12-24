package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * HTML block
 *
 * @see <a href="http://spec.commonmark.org/0.18/#html-blocks">CommonMark Spec</a>
 */
public class HtmlCommentBlock extends HtmlBlockBase {
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
}
