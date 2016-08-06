package com.vladsch.flexmark.ext.front.matter;

import com.vladsch.flexmark.ast.CustomBlock;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class YamlFrontMatterBlock extends CustomBlock {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }
}
