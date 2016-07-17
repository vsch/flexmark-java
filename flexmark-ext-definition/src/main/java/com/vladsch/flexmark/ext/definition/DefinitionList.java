
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.internal.BlockContent;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.ListBlock;

import java.util.List;

/**
 * A DefinitionList block node
 */
public class DefinitionList extends ListBlock {
    public DefinitionList() {
    }

    public DefinitionList(BasedSequence chars) {
        super(chars);
    }

    public DefinitionList(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public DefinitionList(BlockContent blockContent) {
        super(blockContent);
    }

    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
