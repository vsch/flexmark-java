package com.vladsch.flexmark.util.sequence.edit;

import com.vladsch.flexmark.util.collection.iteration.PositionAnchor;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class BasedCharRecoveryOptimizer extends CharRecoveryOptimizer<BasedSequence> implements BasedSegmentOptimizer {
    public BasedCharRecoveryOptimizer(PositionAnchor anchor) {
        super(anchor);
    }
}
