package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A macros block node
 */
public class MacroBlock extends CustomBlock {

    @Override
    public void getAstExtra(StringBuilder out) {
    }

    @Override
    public BasedSequence[] getSegments() {
        return Node.EMPTY_SEGMENTS;
    }

    public MacroBlock() {
    }

    public MacroBlock(BasedSequence chars) {
        super(chars);
    }

    public MacroBlock(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }
}
