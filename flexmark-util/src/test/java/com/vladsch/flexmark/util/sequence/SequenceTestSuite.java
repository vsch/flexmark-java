package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilderTest;
import com.vladsch.flexmark.util.sequence.builder.PlainSegmentBuilderTest;
import com.vladsch.flexmark.util.sequence.builder.SegmentedSequenceStatsTest;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilderTest;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTrackerTest;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentOffsetTreeTest;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTest;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTreeTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CharPredicateTest.class,
        PlaceholderReplacerTest.class,
        SequenceUtilsTest.class,
        PlainSegmentBuilderTest.class,
        MappedRichSequenceTest.class,
        RepeatedSequenceTest.class,
        IRichSequenceBaseTest.class,
        MappedBasedSequenceTest.class,
        PrefixedSubSequenceTest.class,
        SegmentTest.class,
        BasedSegmentBuilderTest.class,
        SegmentTreeTest.class,
        SequenceBuilderTest.class,
        SegmentedSequenceStatsTest.class,
        BasedSequenceFullImplTest.class,
        SegmentedSequenceTreeTest.class,
        SegmentOffsetTreeTest.class,
        BasedOffsetTrackerTest.class,
        LineAppendableImplTest.class,
})
public class SequenceTestSuite {
}
