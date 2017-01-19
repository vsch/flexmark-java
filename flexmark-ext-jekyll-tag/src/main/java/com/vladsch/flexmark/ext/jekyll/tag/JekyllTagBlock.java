package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * A JekyllTag block node
 */
public class JekyllTagBlock extends CustomBlock {

    @Override
    public void getAstExtra(StringBuilder out) {
    }

    @Override
    public BasedSequence[] getSegments() {
        return Node.EMPTY_SEGMENTS;
    }

    public JekyllTagBlock() {
    }

    public JekyllTagBlock(BasedSequence chars) {
        super(chars);
    }

    public JekyllTagBlock(final BasedSequence chars, final List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public JekyllTagBlock(final List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public JekyllTagBlock(final BlockContent blockContent) {
        super(blockContent);
    }

    public JekyllTagBlock(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }
}
