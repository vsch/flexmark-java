package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class YamlFrontMatterValue extends Node {
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public YamlFrontMatterValue() {
    }

    public YamlFrontMatterValue(final BasedSequence chars) {
        super(chars);
    }
}
