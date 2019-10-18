package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.ComboAbbreviationFormatterSpecTest;
import com.vladsch.flexmark.ext.abbreviation.ComboAbbreviationTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.admonition.ComboAdmonitionFormatterSpecTest;
import com.vladsch.flexmark.ext.admonition.ComboAdmonitionTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.attributes.ComboAttributesFormatterSpecTest;
import com.vladsch.flexmark.ext.attributes.ComboAttributesTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.definition.ComboDefinitionFormatterSpecTest;
import com.vladsch.flexmark.ext.emoji.ComboEmojiTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.enumerated.reference.ComboEnumeratedReferenceFormatterSpecTest;
import com.vladsch.flexmark.ext.enumerated.reference.ComboEnumeratedReferenceTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.footnotes.ComboFootnotesFormatterSpecTest;
import com.vladsch.flexmark.ext.footnotes.ComboFootnotesTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.gfm.tasklist.ComboGfmTaskListFormatterSpecTest;
import com.vladsch.flexmark.ext.jekyll.front.matter.ComboJekyllFrontMatterFormatterSpecTest;
import com.vladsch.flexmark.ext.macros.ComboMacrosFormatterSpecTest;
import com.vladsch.flexmark.ext.macros.ComboMacrosTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.tables.ComboTableFormatterSpecTest;
import com.vladsch.flexmark.ext.tables.ComboTableTranslationFormatterSpecTest;
import com.vladsch.flexmark.ext.yaml.front.matter.ComboYamlFrontMatterFormatterSpecTest;
import com.vladsch.flexmark.formatter.test.ComboCoreFormatterSpecTest;
import com.vladsch.flexmark.formatter.test.ComboCoreTranslationFormatterSpecTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ComboAdmonitionFormatterSpecTest.class,
        ComboAdmonitionTranslationFormatterSpecTest.class,
        ComboAbbreviationFormatterSpecTest.class,
        ComboAbbreviationTranslationFormatterSpecTest.class,
        ComboAttributesFormatterSpecTest.class,
        ComboAttributesTranslationFormatterSpecTest.class,
        ComboDefinitionFormatterSpecTest.class,
        ComboEmojiTranslationFormatterSpecTest.class,
        ComboEnumeratedReferenceFormatterSpecTest.class,
        ComboEnumeratedReferenceTranslationFormatterSpecTest.class,
        ComboFootnotesFormatterSpecTest.class,
        ComboFootnotesTranslationFormatterSpecTest.class,
        ComboGfmTaskListFormatterSpecTest.class,
        ComboJekyllFrontMatterFormatterSpecTest.class,
        ComboMacrosFormatterSpecTest.class,
        ComboMacrosTranslationFormatterSpecTest.class,
        ComboTableFormatterSpecTest.class,
        ComboTableTranslationFormatterSpecTest.class,
        ComboYamlFrontMatterFormatterSpecTest.class,
        ComboCoreFormatterSpecTest.class,
        ComboCoreTranslationFormatterSpecTest.class,
        ComboFormatterTestSuiteSpecTest.class,
        ComboFormatterIssueSpecTest.class,
        FlexmarkMergeFormatterTestSuite.class,
})
public class FlexmarkFormatterTestSuite {
}
