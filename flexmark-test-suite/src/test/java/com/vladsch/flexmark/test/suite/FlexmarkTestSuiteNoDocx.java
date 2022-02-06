package com.vladsch.flexmark.test.suite;

import com.vladsch.flexmark.core.test.util.CoreTestSuite;
import com.vladsch.flexmark.core.test.util.formatter.CoreFormatterTestSuite;
import com.vladsch.flexmark.core.test.util.renderer.CoreRendererTestSuite;
import com.vladsch.flexmark.experimental.util.ExperimentalUtilsTestSuite;
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
import com.vladsch.flexmark.ext.gfm.tasklist.ExtGfmTaskListTestSuite;
import com.vladsch.flexmark.ext.gfm.users.ExtGfmUsersTestSuite;
import com.vladsch.flexmark.ext.gitlab.ExtGitLabTestSuite;
import com.vladsch.flexmark.ext.ins.ExtInsTestSuite;
import com.vladsch.flexmark.ext.jekyll.front.matter.ExtJekyllFrontMatterTestSuite;
import com.vladsch.flexmark.ext.jekyll.tag.ExtJekyllTagTestSuite;
import com.vladsch.flexmark.ext.macros.ExtMacrosTestSuite;
import com.vladsch.flexmark.ext.media.tags.ExtMediaTagsTestSuite;
import com.vladsch.flexmark.ext.resizable.image.ExtResizableImageTestSuite;
import com.vladsch.flexmark.ext.spec.example.ExtSpecExampleTestSuite;
import com.vladsch.flexmark.ext.superscript.ExtSuperscriptTestSuite;
import com.vladsch.flexmark.ext.tables.ExtTablesTestSuite;
import com.vladsch.flexmark.ext.toc.ExtTocTestSuite;
import com.vladsch.flexmark.ext.typographic.ExtTypographicTestSuite;
import com.vladsch.flexmark.ext.wikilink.ExtWikiLinkTestSuite;
import com.vladsch.flexmark.ext.xwiki.macros.ExtXWikiMacroTestSuite;
import com.vladsch.flexmark.ext.yaml.front.matter.ExtYamlFrontMatterTestSuite;
import com.vladsch.flexmark.ext.youtube.embedded.ExtYouTubeLinkTestSuite;
import com.vladsch.flexmark.ext.zzzzzz.ExtZzzzzzTestSuite;
import com.vladsch.flexmark.formatter.test.suite.FlexmarkFormatterTestSuite;
import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverterTestSuite;
import com.vladsch.flexmark.integration.test.IntegrationTestSuite;
import com.vladsch.flexmark.jira.converter.JiraConverterTestSuite;
import com.vladsch.flexmark.pdf.converter.PdfConverterTestSuite;
import com.vladsch.flexmark.profile.pegdown.PegdownProfileTestSuite;
import com.vladsch.flexmark.test.util.TestUtilsTestSuite;
import com.vladsch.flexmark.util.UtilTestSuite;
import com.vladsch.flexmark.youtrack.converter.YouTrackConverterTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CoreFormatterTestSuite.class,
        CoreRendererTestSuite.class,
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
        ExtGfmTaskListTestSuite.class,
        ExtGfmUsersTestSuite.class,
        ExtGitLabTestSuite.class,
        ExtInsTestSuite.class,
        ExtJekyllFrontMatterTestSuite.class,
        ExtJekyllTagTestSuite.class,
        ExtMacrosTestSuite.class,
        ExtMediaTagsTestSuite.class,
        ExtResizableImageTestSuite.class,
        ExtSpecExampleTestSuite.class,
        ExtSuperscriptTestSuite.class,
        ExtTablesTestSuite.class,
        ExtTocTestSuite.class,
//        TreeIterationTestSuite.class, // has no tests for now
        ExtTypographicTestSuite.class,
        ExtWikiLinkTestSuite.class,
        ExtXWikiMacroTestSuite.class,
        ExtYamlFrontMatterTestSuite.class,
        ExtYouTubeLinkTestSuite.class,
        ExtZzzzzzTestSuite.class,
        FlexmarkFormatterTestSuite.class,
        FlexmarkHtmlConverterTestSuite.class,
        IntegrationTestSuite.class,
        JiraConverterTestSuite.class,
        PdfConverterTestSuite.class,
        PegdownProfileTestSuite.class,
        UtilTestSuite.class,
        TestUtilsTestSuite.class,
        YouTrackConverterTestSuite.class,
        ExperimentalUtilsTestSuite.class,
})
public class FlexmarkTestSuiteNoDocx {

}
