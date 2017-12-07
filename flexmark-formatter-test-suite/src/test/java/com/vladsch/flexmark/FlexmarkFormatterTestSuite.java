package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.ComboAbbreviationFormatterSpecTest;
import com.vladsch.flexmark.ext.definition.ComboDefinitionFormatterSpecTest;
import com.vladsch.flexmark.ext.footnotes.ComboFootnotesFormatterSpecTest;
import com.vladsch.flexmark.ext.gfm.tasklist.ComboGfmTaskListFormatterSpecTest;
import com.vladsch.flexmark.ext.jekyll.front.matter.ComboJekyllFrontMatterFormatterSpecTest;
import com.vladsch.flexmark.ext.tables.ComboTableFormatterSpecTest;
import com.vladsch.flexmark.ext.yaml.front.matter.ComboYamlFrontMatterFormatterSpecTest;
import com.vladsch.flexmark.formatter.ComboCoreFormatterSpecTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
@Suite.SuiteClasses({
        //UtilsTestSuite.class,
        //CoreTestSuite.class,
        ComboAbbreviationFormatterSpecTest.class,
        //ComboAnchorLinkFormatterSpecTest.class,
        //ComboAsideFormatterSpecTest.class,
        //ComboAutolinkFormatterSpecTest.class,
        ComboDefinitionFormatterSpecTest.class,
        //ComboEmojiFormatterSpecTest.class,
        //ComboEscapedCharacterFormatterSpecTest.class,
        ComboFootnotesFormatterSpecTest.class,
        //ComboGfmStrikethroughFormatterSpecTest.class,
        //ComboGfmTablesFormatterSpecTest.class,
        ComboGfmTaskListFormatterSpecTest.class,
        ComboJekyllFrontMatterFormatterSpecTest.class,
        //ComboJekyllTagFormatterSpecTest.class,
        //ComboInsFormatterSpecTest.class,
        //ComboSuperscriptFormatterSpecTest.class,
        //ComboSpecExampleFormatterSpecTest.class,
        ComboTableFormatterSpecTest.class,
        //ComboTocFormatterSpecTest.class,
        //ComboTypographicFormatterSpecTest.class,
        //ComboWikiLinkFormatterSpecTest.class,
        //ComboXWikiMacroFormatterSpecTest.class,
        ComboYamlFrontMatterFormatterSpecTest.class,
        ComboCoreFormatterSpecTest.class,
        ComboFormatterTestSuiteSpecTest.class,
        ComboFormatterIssueSpecTest.class,
})
public class FlexmarkFormatterTestSuite {
}
