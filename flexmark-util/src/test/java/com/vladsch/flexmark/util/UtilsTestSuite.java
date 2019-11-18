package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.DocumentTest;
import com.vladsch.flexmark.util.collection.*;
import com.vladsch.flexmark.util.collection.iteration.PositionListTest;
import com.vladsch.flexmark.util.html.HtmlFormattingAppendableBaseTest;
import com.vladsch.flexmark.util.html.ui.HtmlBuilderTest;
import com.vladsch.flexmark.util.html.ui.HtmlHelpersTest;
import com.vladsch.flexmark.util.options.AttributeTest;
import com.vladsch.flexmark.util.options.AttributesTest;
import com.vladsch.flexmark.util.options.MutableAttributeTest;
import com.vladsch.flexmark.util.sequence.*;
import com.vladsch.flexmark.util.sequence.edit.BasedSegmentBuilder;
import com.vladsch.flexmark.util.sequence.edit.BasedSegmentBuilderTest;
import com.vladsch.flexmark.util.sequence.edit.BasedSequenceBuilderTest;
import com.vladsch.flexmark.util.sequence.edit.SegmentBuilderTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AttributesTest.class,
        AttributeTest.class,
        BasedSequenceImplTest.class,
        BoundedMaxAggregatorTest.class,
        BoundedMinAggregatorTest.class,
        ClassificationBagTest.class,
        DocumentTest.class,
        HtmlBuilderTest.class,
        HtmlFormattingAppendableBaseTest.class,
        HtmlHelpersTest.class,
        IntegerBitSetTest.class,
        LineFormattingAppendableImplTest.class,
        MaxAggregatorTest.class,
        MinAggregatorTest.class,
        MutableAttributeTest.class,
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        OrderedSetTest.class,
        PrefixedSubSequenceTest.class,
        RepeatedSequenceTest.class,
        TemplateUtilTest.class,
        UtilsTest.class,
        ArrayUtilsTest.class,
        MappedBasedSequenceTest.class,
        MappedRichSequenceTest.class,
        BaseSequenceEntryTest.class,
        BaseSequenceManagerTest.class,
        BasedSegmentBuilderTest.class,
        BasedSequenceBuilderTest.class,
        SegmentBuilderTest.class,
        PositionListTest.class,
        CharPredicateTest.class,
})
public class UtilsTestSuite {
}
