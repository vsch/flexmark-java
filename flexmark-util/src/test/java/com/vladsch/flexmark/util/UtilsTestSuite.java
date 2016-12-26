package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.collection.*;
import com.vladsch.flexmark.util.html.HtmlFormattingAppendableBaseTest;
import com.vladsch.flexmark.util.options.AttributeTest;
import com.vladsch.flexmark.util.options.AttributesTest;
import com.vladsch.flexmark.util.options.MutableAttributeTest;
import com.vladsch.flexmark.util.sequence.BasedSequenceImplTest;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequenceTest;
import com.vladsch.flexmark.util.sequence.ReversedCharSequenceTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        IntegerBitSetTest.class,
        OrderedSetTest.class,
        OrderedMapTest.class,
        OrderedMultiMapTest.class,
        ClassificationBagTest.class,
        BasedSequenceImplTest.class,
        AttributeTest.class,
        MutableAttributeTest.class,
        AttributesTest.class,
        FormattingAppendableImplTest.class,
        RepeatedCharSequenceTest.class,
        ReversedCharSequenceTest.class,
        HtmlFormattingAppendableBaseTest.class,
})
public class UtilsTestSuite {
}
