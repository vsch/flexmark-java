package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.CustomBlock;

public class YamlFrontMatterBlock extends CustomBlock {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
