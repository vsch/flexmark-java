package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTree;
import org.jetbrains.annotations.Nullable;

/**
 * Interface implemented by sequences which do not contain contiguous base characters
 * from startOffset to endOffset
 */
public interface ReplacedBasedSequence extends BasedSequence {

}
