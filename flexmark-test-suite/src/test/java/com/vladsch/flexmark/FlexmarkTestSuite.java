package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.ExtAbbreviationTestSuite;
import com.vladsch.flexmark.ext.anchorlink.ExtAnchorLinkTestSuite;
import com.vladsch.flexmark.ext.autolink.ExtAutolinkTestSuite;
import com.vladsch.flexmark.ext.emoji.ExtEmojiTestSuite;
import com.vladsch.flexmark.ext.footnotes.ExtFootnotesTestSuite;
import com.vladsch.flexmark.ext.front.matter.ExtYamlFrontMatterTestSuite;
import com.vladsch.flexmark.ext.gfm.strikethrough.ExtGfmStrikethroughTestSuite;
import com.vladsch.flexmark.ext.gfm.tables.ExtGfmTablesTestSuite;
import com.vladsch.flexmark.ext.gfm.tasklist.ExtGfmTaskListTestSuite;
import com.vladsch.flexmark.ext.simtoc.ExtSimTocTestSuite;
import com.vladsch.flexmark.ext.tables.ExtTablesTestSuite;
import com.vladsch.flexmark.ext.toc.ExtTocTestSuite;
import com.vladsch.flexmark.ext.wikilink.ExtWikiLinkTestSuite;
import com.vladsch.flexmark.ext.zzzzzz.ExtZzzzzzTestSuite;
import com.vladsch.flexmark.integration.IntegrationTestSuite;
import com.vladsch.flexmark.test.CoreTestSuite;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        CoreTestSuite.class,
        ExtAbbreviationTestSuite.class,
        ExtAutolinkTestSuite.class,
        ExtEmojiTestSuite.class,
        ExtFootnotesTestSuite.class,
        ExtGfmStrikethroughTestSuite.class,
        ExtGfmTablesTestSuite.class,
        ExtGfmTaskListTestSuite.class,
        ExtTablesTestSuite.class,
        ExtWikiLinkTestSuite.class,
        ExtYamlFrontMatterTestSuite.class,
        IntegrationTestSuite.class,
        ExtAnchorLinkTestSuite.class,
        ExtTocTestSuite.class,
        ExtSimTocTestSuite.class,
        ExtZzzzzzTestSuite.class,
})
public class FlexmarkTestSuite {
}
