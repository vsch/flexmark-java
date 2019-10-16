package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.MergeAbbreviationsTest;
import com.vladsch.flexmark.ext.attributes.MergeAttributesTest;
import com.vladsch.flexmark.ext.enumerated.reference.MergeEnumeratedReferenceTest;
import com.vladsch.flexmark.ext.footnotes.MergeFootnotesTest;
import com.vladsch.flexmark.ext.macros.MergeMacrosTest;
import com.vladsch.flexmark.formatter.MergeFormatterCoreTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        //UtilsTestSuite.class,
        //CoreTestSuite.class,
        //ComboAdmonitionFormatterSpecTest.class,
        //ComboAdmonitionTranslationFormatterSpecTest.class,
        MergeAbbreviationsTest.class,
        //ComboAbbreviationTranslationFormatterSpecTest.class,
        //ComboAnchorLinkFormatterSpecTest.class,
        MergeAttributesTest.class,
        //ComboAsideFormatterSpecTest.class,
        //ComboAutolinkFormatterSpecTest.class,
        //ComboDefinitionFormatterSpecTest.class,
        //ComboEmojiTranslationFormatterSpecTest.class,
        //ComboEscapedCharacterFormatterSpecTest.class,
        MergeEnumeratedReferenceTest.class,
        //ComboEnumeratedReferenceTranslationFormatterSpecTest.class,
        MergeFootnotesTest.class,
        //ComboFootnotesTranslationFormatterSpecTest.class,
        //ComboGfmStrikethroughFormatterSpecTest.class,
        //ComboGfmTablesFormatterSpecTest.class,
        //ComboGfmTaskListFormatterSpecTest.class,
        //ComboJekyllFrontMatterFormatterSpecTest.class,
        //ComboJekyllTagFormatterSpecTest.class,
        //ComboInsFormatterSpecTest.class,
        MergeMacrosTest.class,
        //ComboMacrosTranslationFormatterSpecTest.class,
        //ComboSuperscriptFormatterSpecTest.class,
        //ComboSpecExampleFormatterSpecTest.class,
        //ComboTableFormatterSpecTest.class,
        //ComboTableTranslationFormatterSpecTest.class,
        //ComboTocFormatterSpecTest.class,
        //ComboTypographicFormatterSpecTest.class,
        //ComboWikiLinkFormatterSpecTest.class,
        //ComboXWikiMacroFormatterSpecTest.class,
        //ComboYamlFrontMatterFormatterSpecTest.class,
        MergeFormatterCoreTest.class,
        //ComboCoreTranslationFormatterSpecTest.class,
        //ComboFormatterTestSuiteSpecTest.class,
        //ComboFormatterIssueSpecTest.class,
})
public class FlexmarkMergeFormatterTestSuite {
}
