package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.ExtAbbreviationTestSuite;
import com.vladsch.flexmark.ext.anchorlink.ExtAnchorLinkTestSuite;
import com.vladsch.flexmark.ext.aside.ExtAsideTestSuite;
import com.vladsch.flexmark.ext.autolink.ExtAutolinkTestSuite;
import com.vladsch.flexmark.ext.definition.ExtDefinitionTestSuite;
import com.vladsch.flexmark.ext.emoji.ExtEmojiTestSuite;
import com.vladsch.flexmark.ext.escaped.character.ExtEscapedCharacterTestSuite;
import com.vladsch.flexmark.ext.footnotes.ExtFootnotesTestSuite;
import com.vladsch.flexmark.ext.front.matter.ExtYamlFrontMatterTestSuite;
import com.vladsch.flexmark.ext.gfm.strikethrough.ExtGfmStrikethroughTestSuite;
import com.vladsch.flexmark.ext.gfm.tables.ExtGfmTablesTestSuite;
import com.vladsch.flexmark.ext.gfm.tasklist.ExtGfmTaskListTestSuite;
import com.vladsch.flexmark.ext.ins.ExtInsTestSuite;
import com.vladsch.flexmark.ext.jekyll.front.matter.ExtJekyllFrontMatterTestSuite;
import com.vladsch.flexmark.ext.spec.example.ExtSpecExampleTestSuite;
import com.vladsch.flexmark.ext.tables.ExtTablesTestSuite;
import com.vladsch.flexmark.ext.toc.ExtTocTestSuite;
import com.vladsch.flexmark.ext.typographic.ExtTypographicTestSuite;
import com.vladsch.flexmark.ext.wikilink.ExtWikiLinkTestSuite;
import com.vladsch.flexmark.ext.zzzzzz.ExtZzzzzzTestSuite;
import com.vladsch.flexmark.integration.IntegrationTestSuite;
import com.vladsch.flexmark.jira.converter.JiraConverterTestSuite;
import com.vladsch.flexmark.superscript.ExtSuperscriptTestSuite;
import com.vladsch.flexmark.test.CoreTestSuite;
import com.vladsch.flexmark.util.UtilsTestSuite;
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterTestSuite;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        UtilsTestSuite.class,
        CoreTestSuite.class,
        ExtAbbreviationTestSuite.class,
        ExtAnchorLinkTestSuite.class,
        ExtAsideTestSuite.class,
        ExtAutolinkTestSuite.class,
        ExtDefinitionTestSuite.class,
        ExtEmojiTestSuite.class,
        ExtEscapedCharacterTestSuite.class,
        ExtFootnotesTestSuite.class,
        ExtGfmStrikethroughTestSuite.class,
        ExtGfmTablesTestSuite.class,
        ExtGfmTaskListTestSuite.class,
        ExtJekyllFrontMatterTestSuite.class,
        ExtInsTestSuite.class,
        ExtSuperscriptTestSuite.class,
        ExtSpecExampleTestSuite.class,
        ExtTablesTestSuite.class,
        ExtTocTestSuite.class,
        ExtTypographicTestSuite.class,
        ExtWikiLinkTestSuite.class,
        ExtYamlFrontMatterTestSuite.class,
        ExtZzzzzzTestSuite.class,
        JiraConverterTestSuite.class,
        YouTrackConverterTestSuite.class,
        IntegrationTestSuite.class,
})
public class FlexmarkTestSuite {
}
