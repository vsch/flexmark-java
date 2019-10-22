package com.vladsch.flexmark.ext.toc;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A sim toc contents node containing all text that came after the sim toc node
 */
public class SimTocOptionList extends Node implements DoNotDecorate {
    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return EMPTY_SEGMENTS;
    }

    public SimTocOptionList() {
    }

    public SimTocOptionList(BasedSequence chars) {
        super(chars);
    }
}
