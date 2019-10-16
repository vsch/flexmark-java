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
import com.vladsch.flexmark.util.sequence.RepeatedCharSequenceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegerBitSetTest.class,
        OrderedSetTest.class,
        OrderedMapTest.class,
        DocumentTest.class,
        OrderedMultiMapTest.class,
        ClassificationBagTest.class,
        BasedSequenceImplTest.class,
        AttributeTest.class,
        MutableAttributeTest.class,
        AttributesTest.class,
        LineFormattingAppendableImplTest.class,
        RepeatedCharSequenceTest.class,
        HtmlFormattingAppendableBaseTest.class,
        MaxAggregatorTest.class,
        MinAggregatorTest.class,
        BoundedMaxAggregatorTest.class,
        BoundedMinAggregatorTest.class,
        HtmlBuilderTest.class,
        HtmlHelpersTest.class,
        TemplateUtilTest.class,
        UtilsTest.class,
})
public class UtilsTestSuite {
}
