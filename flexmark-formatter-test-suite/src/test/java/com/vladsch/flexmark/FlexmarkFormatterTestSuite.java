package com.vladsch.flexmark;

import com.vladsch.flexmark.convert.html.FlexmarkHtmlParser;
import com.vladsch.flexmark.convert.html.FlexmarkHtmlParserTestSuite;
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
import com.vladsch.flexmark.ext.gfm.tasklist.ComboGfmTaskListFormatterSpecTest;
import com.vladsch.flexmark.ext.gfm.tasklist.ExtGfmTaskListTestSuite;
import com.vladsch.flexmark.ext.ins.ExtInsTestSuite;
import com.vladsch.flexmark.ext.jekyll.front.matter.ExtJekyllFrontMatterTestSuite;
import com.vladsch.flexmark.ext.jekyll.tag.ExtJekyllTagTestSuite;
import com.vladsch.flexmark.ext.xwiki.macros.ExtXWikiMacroTestSuite;
import com.vladsch.flexmark.ext.spec.example.ExtSpecExampleTestSuite;
import com.vladsch.flexmark.ext.tables.ExtTablesTestSuite;
import com.vladsch.flexmark.ext.toc.ExtTocTestSuite;
import com.vladsch.flexmark.ext.typographic.ExtTypographicTestSuite;
import com.vladsch.flexmark.ext.wikilink.ExtWikiLinkTestSuite;
import com.vladsch.flexmark.ext.zzzzzz.ExtZzzzzzTestSuite;
import com.vladsch.flexmark.formatter.ComboCoreFormatterSpecTest;
import com.vladsch.flexmark.integration.IntegrationTestSuite;
import com.vladsch.flexmark.jira.converter.JiraConverterTestSuite;
import com.vladsch.flexmark.profiles.pegdown.PegdownProfileTestSuite;
import com.vladsch.flexmark.superscript.ExtSuperscriptTestSuite;
import com.vladsch.flexmark.test.CoreTestSuite;
import com.vladsch.flexmark.util.UtilsTestSuite;
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterTestSuite;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        //UtilsTestSuite.class,
        //CoreTestSuite.class,
        //ComboAbbreviationFormatterSpecTest.class,
        //ComboAnchorLinkFormatterSpecTest.class,
        //ComboAsideFormatterSpecTest.class,
        //ComboAutolinkFormatterSpecTest.class,
        //ComboDefinitionFormatterSpecTest.class,
        //ComboEmojiFormatterSpecTest.class,
        //ComboEscapedCharacterFormatterSpecTest.class,
        //ComboFootnotesFormatterSpecTest.class,
        //ComboGfmStrikethroughFormatterSpecTest.class,
        //ComboGfmTablesFormatterSpecTest.class,
        ComboGfmTaskListFormatterSpecTest.class,
        //ComboJekyllFrontMatterFormatterSpecTest.class,
        //ComboJekyllTagFormatterSpecTest.class,
        //ComboInsFormatterSpecTest.class,
        //ComboSuperscriptFormatterSpecTest.class,
        //ComboSpecExampleFormatterSpecTest.class,
        //ComboTablesFormatterSpecTest.class,
        //ComboTocFormatterSpecTest.class,
        //ComboTypographicFormatterSpecTest.class,
        //ComboWikiLinkFormatterSpecTest.class,
        //ComboXWikiMacroFormatterSpecTest.class,
        //ComboYamlFrontMatterFormatterSpecTest.class,
        ComboCoreFormatterSpecTest.class,
        ComboFormatterTestSuiteSpecTest.class,
})
public class FlexmarkFormatterTestSuite {
}
