package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class YamlFrontMatterValue extends Node {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    public YamlFrontMatterValue() {
    }

    public YamlFrontMatterValue(BasedSequence chars) {
        super(chars);
    }
}
