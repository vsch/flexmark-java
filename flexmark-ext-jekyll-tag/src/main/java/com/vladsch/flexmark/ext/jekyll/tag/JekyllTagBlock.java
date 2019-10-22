package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A JekyllTag block node
 */
public class JekyllTagBlock extends Block {

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return Node.EMPTY_SEGMENTS;
    }

    public JekyllTagBlock() {
    }

    public JekyllTagBlock(BasedSequence chars) {
        super(chars);
    }

    public JekyllTagBlock(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars, lineSegments);
    }

    public JekyllTagBlock(List<BasedSequence> lineSegments) {
        super(lineSegments);
    }

    public JekyllTagBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public JekyllTagBlock(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }
}
