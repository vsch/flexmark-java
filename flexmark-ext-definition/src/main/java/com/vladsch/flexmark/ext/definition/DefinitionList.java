package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

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

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
