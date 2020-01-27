package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.core.test.util.TranslationFormatterSpecTest;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.*;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;
import java.util.Map;

public class ComboCoreTranslationFormatterSpecTest extends TranslationFormatterSpecTest {
    final private static String SPEC_RESOURCE = "/core_translation_formatter_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    final private static Map<String, DataHolder> optionsMap = placementAndSortOptions(
            Parser.REFERENCES_KEEP,
            Formatter.REFERENCE_PLACEMENT,
            Formatter.REFERENCE_SORT
    );
    static {
        optionsMap.put("atx-space-as-is", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.AS_IS));
        optionsMap.put("atx-space-add", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.ADD));
        optionsMap.put("atx-space-remove", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.REMOVE));
        optionsMap.put("setext-no-equalize", new MutableDataSet().set(Formatter.SETEXT_HEADING_EQUALIZE_MARKER, false));
        optionsMap.put("atx-trailing-as-is", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.AS_IS));
        optionsMap.put("atx-trailing-add", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.ADD));
        optionsMap.put("atx-trailing-equalize", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.EQUALIZE));
        optionsMap.put("atx-trailing-remove", new MutableDataSet().set(Formatter.ATX_HEADING_TRAILING_MARKER, EqualizeTrailingMarker.REMOVE));
        optionsMap.put("thematic-break", new MutableDataSet().set(Formatter.THEMATIC_BREAK, "*** ** * ** ***"));
        optionsMap.put("no-block-quote-blank-lines", new MutableDataSet().set(Formatter.BLOCK_QUOTE_BLANK_LINES, false));
        optionsMap.put("block-quote-compact", new MutableDataSet().set(Formatter.BLOCK_QUOTE_MARKERS, BlockQuoteMarker.ADD_COMPACT));
        optionsMap.put("block-quote-compact-with-space", new MutableDataSet().set(Formatter.BLOCK_QUOTE_MARKERS, BlockQuoteMarker.ADD_COMPACT_WITH_SPACE));
        optionsMap.put("block-quote-spaced", new MutableDataSet().set(Formatter.BLOCK_QUOTE_MARKERS, BlockQuoteMarker.ADD_SPACED));
        optionsMap.put("indented-code-minimize", new MutableDataSet().set(Formatter.INDENTED_CODE_MINIMIZE_INDENT, true));
        optionsMap.put("fenced-code-minimize", new MutableDataSet().set(Formatter.FENCED_CODE_MINIMIZE_INDENT, true));
        optionsMap.put("fenced-code-match-closing", new MutableDataSet().set(Formatter.FENCED_CODE_MATCH_CLOSING_MARKER, true));
        optionsMap.put("fenced-code-spaced-info", new MutableDataSet().set(Formatter.FENCED_CODE_SPACE_BEFORE_INFO, true));
        optionsMap.put("fenced-code-marker-length", new MutableDataSet().set(Formatter.FENCED_CODE_MARKER_LENGTH, 6));
        optionsMap.put("fenced-code-marker-backtick", new MutableDataSet().set(Formatter.FENCED_CODE_MARKER_TYPE, CodeFenceMarker.BACK_TICK));
        optionsMap.put("fenced-code-marker-tilde", new MutableDataSet().set(Formatter.FENCED_CODE_MARKER_TYPE, CodeFenceMarker.TILDE));
        optionsMap.put("list-add-blank-line-before", new MutableDataSet().set(Formatter.LIST_ADD_BLANK_LINE_BEFORE, true));
        optionsMap.put("list-no-renumber-items", new MutableDataSet().set(Formatter.LIST_RENUMBER_ITEMS, false));
        optionsMap.put("list-bullet-dash", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.DASH));
        optionsMap.put("list-bullet-asterisk", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.ASTERISK));
        optionsMap.put("list-bullet-plus", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.PLUS));
        optionsMap.put("list-numbered-dot", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.DOT));
        optionsMap.put("list-numbered-paren", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.PAREN));
        optionsMap.put("list-spacing-loosen", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.LOOSEN));
        optionsMap.put("list-spacing-tighten", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.TIGHTEN));
        optionsMap.put("list-spacing-loose", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.LOOSE));
        optionsMap.put("list-spacing-tight", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.TIGHT));
        optionsMap.put("references-keep-last", new MutableDataSet().set(Parser.REFERENCES_KEEP, KeepType.LAST));
        optionsMap.put("image-links-at-start", new MutableDataSet().set(Formatter.KEEP_IMAGE_LINKS_AT_START, true));
        optionsMap.put("explicit-links-at-start", new MutableDataSet().set(Formatter.KEEP_EXPLICIT_LINKS_AT_START, true));
        optionsMap.put("remove-empty-items", new MutableDataSet().set(Formatter.LIST_REMOVE_EMPTY_ITEMS, true));
    }
    public ComboCoreTranslationFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
