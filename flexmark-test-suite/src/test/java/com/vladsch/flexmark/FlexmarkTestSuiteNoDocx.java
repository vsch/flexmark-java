package com.vladsch.flexmark;

import com.vladsch.flexmark.convert.html.FlexmarkHtmlParserTestSuite;
import com.vladsch.flexmark.ext.abbreviation.ExtAbbreviationTestSuite;
import com.vladsch.flexmark.ext.admonition.ExtAdmonitionTestSuite;
import com.vladsch.flexmark.ext.anchorlink.ExtAnchorLinkTestSuite;
import com.vladsch.flexmark.ext.aside.ExtAsideTestSuite;
import com.vladsch.flexmark.ext.attributes.ExtAttributesTestSuite;
import com.vladsch.flexmark.ext.autolink.ExtAutolinkTestSuite;
import com.vladsch.flexmark.ext.definition.ExtDefinitionTestSuite;
import com.vladsch.flexmark.ext.emoji.ExtEmojiTestSuite;
import com.vladsch.flexmark.ext.enumerated.reference.ExtEnumeratedReferenceTestSuite;
import com.vladsch.flexmark.ext.escaped.character.ExtEscapedCharacterTestSuite;
import com.vladsch.flexmark.ext.footnotes.ExtFootnotesTestSuite;
import com.vladsch.flexmark.ext.gfm.issues.ExtGfmIssuesTestSuite;
import com.vladsch.flexmark.ext.gfm.strikethrough.ExtGfmStrikethroughTestSuite;
import com.vladsch.flexmark.ext.gfm.tables.ExtGfmTablesTestSuite;
import com.vladsch.flexmark.ext.gfm.tasklist.ExtGfmTaskListTestSuite;
import com.vladsch.flexmark.ext.gfm.users.ExtGfmUsersTestSuite;
import com.vladsch.flexmark.ext.gitlab.ExtGitLabTestSuite;
import com.vladsch.flexmark.ext.ins.ExtInsTestSuite;
import com.vladsch.flexmark.ext.jekyll.front.matter.ExtJekyllFrontMatterTestSuite;
import com.vladsch.flexmark.ext.jekyll.tag.ExtJekyllTagTestSuite;
import com.vladsch.flexmark.ext.macros.ExtMacrosTestSuite;
import com.vladsch.flexmark.ext.media.tags.ExtMediaTagsTestSuite;
import com.vladsch.flexmark.ext.spec.example.ExtSpecExampleTestSuite;
import com.vladsch.flexmark.ext.tables.ExtTablesTestSuite;
import com.vladsch.flexmark.ext.toc.ExtTocTestSuite;
import com.vladsch.flexmark.ext.typographic.ExtTypographicTestSuite;
import com.vladsch.flexmark.ext.wikilink.ExtWikiLinkTestSuite;
import com.vladsch.flexmark.ext.xwiki.macros.ExtXWikiMacroTestSuite;
import com.vladsch.flexmark.ext.yaml.front.matter.ExtYamlFrontMatterTestSuite;
import com.vladsch.flexmark.ext.youtube.embedded.ExtYouTubeLinkTestSuite;
import com.vladsch.flexmark.ext.zzzzzz.ExtZzzzzzTestSuite;
import com.vladsch.flexmark.formatter.CoreFormatterTestSuite;
import com.vladsch.flexmark.html.converter.FlexmarkHtmlConverterTestSuite;
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
        UtilsTestSuite.class,
        CoreTestSuite.class,
        ExtAbbreviationTestSuite.class,
        ExtAdmonitionTestSuite.class,
        ExtAnchorLinkTestSuite.class,
        ExtAsideTestSuite.class,
        ExtAttributesTestSuite.class,
        ExtAutolinkTestSuite.class,
        ExtDefinitionTestSuite.class,
        ExtEmojiTestSuite.class,
        ExtEnumeratedReferenceTestSuite.class,
        ExtEscapedCharacterTestSuite.class,
        ExtFootnotesTestSuite.class,
        ExtGfmIssuesTestSuite.class,
        ExtGfmStrikethroughTestSuite.class,
        ExtGfmTablesTestSuite.class,
        ExtGfmTaskListTestSuite.class,
        ExtGfmUsersTestSuite.class,
        ExtGitLabTestSuite.class,
        ExtJekyllFrontMatterTestSuite.class,
        ExtJekyllTagTestSuite.class,
        ExtInsTestSuite.class,
        ExtMacrosTestSuite.class,
        ExtMediaTagsTestSuite.class,
        ExtSuperscriptTestSuite.class,
        ExtSpecExampleTestSuite.class,
        ExtTablesTestSuite.class,
        ExtTocTestSuite.class,
        ExtTypographicTestSuite.class,
        ExtWikiLinkTestSuite.class,
        ExtXWikiMacroTestSuite.class,
        ExtYamlFrontMatterTestSuite.class,
        ExtYouTubeLinkTestSuite.class,
        ExtZzzzzzTestSuite.class,
        FlexmarkHtmlParserTestSuite.class,
        FlexmarkHtmlConverterTestSuite.class,
        JiraConverterTestSuite.class,
        YouTrackConverterTestSuite.class,
        IntegrationTestSuite.class,
        PegdownProfileTestSuite.class,
        CoreFormatterTestSuite.class,
        FlexmarkFormatterTestSuite.class,
        //DocxConverterTestSuite.class,
        //ComboDocxUserSpecDisabled.class
})
public class FlexmarkTestSuiteNoDocx {
}
