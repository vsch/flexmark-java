package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.AstTestSuite;
import com.vladsch.flexmark.util.collection.CollectionTestSuite;
import com.vladsch.flexmark.util.format.FormatTestSuite;
import com.vladsch.flexmark.util.html.HtmlTestSuite;
import com.vladsch.flexmark.util.misc.MiscTestSuite;
import com.vladsch.flexmark.util.options.OptionsTestSuite;
import com.vladsch.flexmark.util.sequence.SequenceTestSuite;
import com.vladsch.flexmark.util.visitor.VisitorTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CollectionTestSuite.class,
        MiscTestSuite.class,
        AstTestSuite.class,
        HtmlTestSuite.class,
        FormatTestSuite.class,
        OptionsTestSuite.class,
        VisitorTestSuite.class,
        SequenceTestSuite.class,
})
public class UtilTestSuite {
}
