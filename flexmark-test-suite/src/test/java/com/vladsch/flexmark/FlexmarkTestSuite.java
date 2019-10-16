package com.vladsch.flexmark;

import com.vladsch.flexmark.docx.converter.DocxConverterTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //UtilsTestSuite.class,
        //CoreTestSuite.class,
        //ExtAbbreviationTestSuite.class,
        //ExtAdmonitionTestSuite.class,
        //ExtAnchorLinkTestSuite.class,
        //ExtAsideTestSuite.class,
        //ExtAttributesTestSuite.class,
        //ExtAutolinkTestSuite.class,
        //ExtDefinitionTestSuite.class,
        //ExtEmojiTestSuite.class,
        //ExtEnumeratedReferenceTestSuite.class,
        //ExtEscapedCharacterTestSuite.class,
        //ExtFootnotesTestSuite.class,
        //ExtGfmIssuesTestSuite.class,
        //ExtGfmStrikethroughTestSuite.class,
        //ExtGfmTablesTestSuite.class,
        //ExtGfmTaskListTestSuite.class,
        //ExtGfmUsersTestSuite.class,
        //ExtGitLabTestSuite.class,
        //ExtJekyllFrontMatterTestSuite.class,
        //ExtJekyllTagTestSuite.class,
        //ExtInsTestSuite.class,
        //ExtMacrosTestSuite.class,
        //ExtMediaTagsTestSuite.class,
        //ExtSuperscriptTestSuite.class,
        //ExtSpecExampleTestSuite.class,
        //ExtTablesTestSuite.class,
        //ExtTocTestSuite.class,
        //ExtTypographicTestSuite.class,
        //ExtWikiLinkTestSuite.class,
        //ExtXWikiMacroTestSuite.class,
        //ExtYamlFrontMatterTestSuite.class,
        //ExtYouTubeLinkTestSuite.class,
        //ExtZzzzzzTestSuite.class,
        //FlexmarkHtmlParserTestSuite.class,
        //JiraConverterTestSuite.class,
        //YouTrackConverterTestSuite.class,
        //IntegrationTestSuite.class,
        //PegdownProfileTestSuite.class,
        //FlexmarkFormatterTestSuite.class,
        FlexmarkTestSuiteNoDocx.class,
        DocxConverterTestSuite.class,
        //ComboDocxUserSpecDisabled.class
})
public class FlexmarkTestSuite {
}
