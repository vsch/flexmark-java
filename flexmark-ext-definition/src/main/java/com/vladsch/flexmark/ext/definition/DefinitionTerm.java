
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A Definition block node
 */
public class DefinitionTerm extends CustomBlock {

    @Override
    public void getAstExtra(StringBuilder out) {
    }

    @Override
    public BasedSequence[] getSegments() {
        return Node.EMPTY_SEGMENTS;
    }

    public DefinitionTerm() {
    }

    public DefinitionTerm(BasedSequence chars) {
        super(chars);
    }

    public DefinitionTerm(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
    }
}
