package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.DocumentTest;
import com.vladsch.flexmark.util.collection.*;
import com.vladsch.flexmark.util.format.MarkdownParagraphTest;
import com.vladsch.flexmark.util.format.TrackedOffsetListTest;
import com.vladsch.flexmark.util.html.HtmlFormattingAppendableBaseTest;
import com.vladsch.flexmark.util.html.LineFormattingAppendableImplTest;
import com.vladsch.flexmark.util.html.ui.HtmlBuilderTest;
import com.vladsch.flexmark.util.html.ui.HtmlHelpersTest;
import com.vladsch.flexmark.util.options.AttributeTest;
import com.vladsch.flexmark.util.options.AttributesTest;
import com.vladsch.flexmark.util.options.MutableAttributeTest;
import com.vladsch.flexmark.util.sequence.*;
import com.vladsch.flexmark.util.sequence.builder.BasedSegmentBuilderTest;
import com.vladsch.flexmark.util.sequence.builder.PlainSegmentBuilderTest;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilderTest;
import com.vladsch.flexmark.util.sequence.builder.tree.BasedOffsetTrackerTest;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentOffsetTreeTest;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTest;
import com.vladsch.flexmark.util.sequence.builder.tree.SegmentTreeTest;
import com.vladsch.flexmark.util.sequence.managed.BaseSequenceEntryTest;
import com.vladsch.flexmark.util.sequence.managed.BaseSequenceManagerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        OrderedSetTest.class,
        ClassificationBagTest.class,
        CharPredicateTest.class,
        SequenceUtilsTest.class,
        MaxAggregatorTest.class,
        MinAggregatorTest.class,
        MutableAttributeTest.class,
        BoundedMaxAggregatorTest.class,
        BoundedMinAggregatorTest.class,
        UtilsTest.class,
        ArrayUtilsTest.class,
        BitFieldSetTest.class,
        PlainSegmentBuilderTest.class,
        MappedRichSequenceTest.class,
        RepeatedSequenceTest.class,
        BaseSequenceEntryTest.class,
        MappedBasedSequenceTest.class,
        PrefixedSubSequenceTest.class,
        SegmentTest.class,
        BasedSegmentBuilderTest.class,
        SegmentTreeTest.class,
        SequenceBuilderTest.class,
        BasedSequenceFullImplTest.class,
        BasedSequenceTreeImplTest.class,
        SegmentOffsetTreeTest.class,
        BasedOffsetTrackerTest.class,
        TrackedOffsetListTest.class,
        BaseSequenceManagerTest.class,
        MarkdownParagraphTest.class,
        TemplateUtilTest.class,
        AttributesTest.class,
        AttributeTest.class,
        DocumentTest.class,
        HtmlBuilderTest.class,
        HtmlFormattingAppendableBaseTest.class,
        HtmlHelpersTest.class,
        LineFormattingAppendableImplTest.class,
//        LineAppendableImplTest.class,
})
public class UtilsTestSuite {
}
