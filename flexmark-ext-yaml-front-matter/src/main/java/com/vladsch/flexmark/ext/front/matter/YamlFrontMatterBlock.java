package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.CustomBlock;

public class YamlFrontMatterBlock extends CustomBlock<YamlFrontMatterVisitor> {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void accept(YamlFrontMatterVisitor visitor) {
        visitor.visit(this);
    }
}
