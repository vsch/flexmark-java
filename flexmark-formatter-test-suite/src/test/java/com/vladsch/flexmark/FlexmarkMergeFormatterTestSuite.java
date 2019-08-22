package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.ComboAbbreviationFormatterSpecTest;
import com.vladsch.flexmark.ext.abbreviation.ComboAbbreviationTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.abbreviation.MergeAbbreviationsTest;
import com.vladsch.flexmark.ext.admonition.ComboAdmonitionFormatterSpecTest;
import com.vladsch.flexmark.ext.admonition.ComboAdmonitionTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.attributes.ComboAttributesTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.attributes.MergeAttributesTest;
import com.vladsch.flexmark.ext.definition.ComboDefinitionFormatterSpecTest;
import com.vladsch.flexmark.ext.emoji.ComboEmojiTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.enumerated.reference.ComboEnumeratedReferenceFormatterSpecTest;
import com.vladsch.flexmark.ext.enumerated.reference.ComboEnumeratedReferenceTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.enumerated.reference.MergeEnumeratedReferenceTest;
import com.vladsch.flexmark.ext.footnotes.ComboFootnotesFormatterSpecTest;
import com.vladsch.flexmark.ext.footnotes.ComboFootnotesTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.footnotes.MergeFootnotesTest;
import com.vladsch.flexmark.ext.gfm.tasklist.ComboGfmTaskListFormatterSpecTest;
import com.vladsch.flexmark.ext.jekyll.front.matter.ComboJekyllFrontMatterFormatterSpecTest;
import com.vladsch.flexmark.ext.macros.ComboMacrosFormatterSpecTest;
import com.vladsch.flexmark.ext.macros.ComboMacrosTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.macros.MergeMacrosTest;
import com.vladsch.flexmark.ext.tables.ComboTableFormatterSpecTest;
import com.vladsch.flexmark.ext.tables.ComboTableTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.yaml.front.matter.ComboYamlFrontMatterFormatterSpecTest;
import com.vladsch.flexmark.formatter.ComboCoreFormatterSpecTest;
import com.vladsch.flexmark.formatter.ComboCoreTranslationFormatterSpecTest;
import com.vladsch.flexmark.formatter.MergeFormatterCoreTest;
import org.junit.runners.Suite;

@org.junit.runner.RunWith(Suite.class)
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
