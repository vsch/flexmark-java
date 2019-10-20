package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.DocumentTest;
import com.vladsch.flexmark.util.collection.*;
import com.vladsch.flexmark.util.html.HtmlFormattingAppendableBaseTest;
import com.vladsch.flexmark.util.html.ui.HtmlBuilderTest;
import com.vladsch.flexmark.util.html.ui.HtmlHelpersTest;
import com.vladsch.flexmark.util.options.AttributeTest;
import com.vladsch.flexmark.util.options.AttributesTest;
import com.vladsch.flexmark.util.options.MutableAttributeTest;
import com.vladsch.flexmark.util.sequence.BasedSequenceImplTest;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequenceTest;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequenceTest;
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
        RepeatedCharSequenceTest.class,
        TemplateUtilTest.class,
        UtilsTest.class,
})
public class UtilsTestSuite {
}
