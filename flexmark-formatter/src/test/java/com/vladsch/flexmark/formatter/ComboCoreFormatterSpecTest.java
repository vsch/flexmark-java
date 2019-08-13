package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.*;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboCoreFormatterSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/core_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            //.set(FormattingRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            //.set(Parser.EXTENSIONS, Collections.singleton(FormatterExtension.create()))
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.HEADING_NO_ATX_SPACE, true);

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        //optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        //optionsMap.put("option1", new MutableDataSet().set(FormatterExtension.FORMATTER_OPTION1, true));
        optionsMap.put("format-fixed-indent", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        optionsMap.put("parse-fixed-indent", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.FIXED_INDENT));
        optionsMap.put("format-github", new MutableDataSet().set(Formatter.FORMATTER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("parse-github", new MutableDataSet().set(Parser.PARSER_EMULATION_PROFILE, ParserEmulationProfile.GITHUB_DOC));
        optionsMap.put("max-blank-lines-1", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 1));
        optionsMap.put("max-blank-lines-2", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 2));
        optionsMap.put("max-blank-lines-3", new MutableDataSet().set(Formatter.MAX_BLANK_LINES, 3));
        optionsMap.put("no-tailing-blanks", new MutableDataSet().set(Formatter.MAX_TRAILING_BLANK_LINES, 0));
        optionsMap.put("atx-space-as-is", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.AS_IS));
        optionsMap.put("atx-space-add", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.ADD));
        optionsMap.put("atx-space-remove", new MutableDataSet().set(Formatter.SPACE_AFTER_ATX_MARKER, DiscretionaryText.REMOVE));
        optionsMap.put("setext-no-equalize", new MutableDataSet().set(Formatter.SETEXT_HEADER_EQUALIZE_MARKER, false));
        optionsMap.put("atx-trailing-as-is", new MutableDataSet().set(Formatter.ATX_HEADER_TRAILING_MARKER, EqualizeTrailingMarker.AS_IS));
        optionsMap.put("atx-trailing-add", new MutableDataSet().set(Formatter.ATX_HEADER_TRAILING_MARKER, EqualizeTrailingMarker.ADD));
        optionsMap.put("atx-trailing-equalize", new MutableDataSet().set(Formatter.ATX_HEADER_TRAILING_MARKER, EqualizeTrailingMarker.EQUALIZE));
        optionsMap.put("atx-trailing-remove", new MutableDataSet().set(Formatter.ATX_HEADER_TRAILING_MARKER, EqualizeTrailingMarker.REMOVE));
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
        //optionsMap.put("list-align-first-line-text", new MutableDataSet().set(Formatter.LIST_ALIGN_FIRST_LINE_TEXT, true));
        //optionsMap.put("list-no-align-child-blocks", new MutableDataSet().set(Formatter.LIST_ALIGN_CHILD_BLOCKS, false));
        optionsMap.put("list-no-renumber-items", new MutableDataSet().set(Formatter.LIST_RENUMBER_ITEMS, false));
        //optionsMap.put("list-bullet-any", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.ANY));
        optionsMap.put("list-bullet-dash", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.DASH));
        optionsMap.put("list-bullet-asterisk", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.ASTERISK));
        optionsMap.put("list-bullet-plus", new MutableDataSet().set(Formatter.LIST_BULLET_MARKER, ListBulletMarker.PLUS));
        //optionsMap.put("list-numbered-any", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.ANY));
        optionsMap.put("list-numbered-dot", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.DOT));
        optionsMap.put("list-numbered-paren", new MutableDataSet().set(Formatter.LIST_NUMBERED_MARKER, ListNumberedMarker.PAREN));
        //optionsMap.put("list-spacing-as-is", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.AS_IS));
        optionsMap.put("list-spacing-loosen", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.LOOSEN));
        optionsMap.put("list-spacing-tighten", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.TIGHTEN));
        optionsMap.put("list-spacing-loose", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.LOOSE));
        optionsMap.put("list-spacing-tight", new MutableDataSet().set(Formatter.LIST_SPACING, ListSpacing.TIGHT));
        optionsMap.put("references-as-is", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.AS_IS));
        optionsMap.put("references-document-top", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_TOP));
        optionsMap.put("references-group-with-first", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.GROUP_WITH_FIRST));
        optionsMap.put("references-group-with-last", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.GROUP_WITH_LAST));
        optionsMap.put("references-document-bottom", new MutableDataSet().set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_BOTTOM));
        //optionsMap.put("references-no-sort", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.AS_IS));
        optionsMap.put("references-sort", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.SORT));
        optionsMap.put("references-sort-unused-last", new MutableDataSet().set(Formatter.REFERENCE_SORT, ElementPlacementSort.SORT_UNUSED_LAST));
        //optionsMap.put("references-keep-first", new MutableDataSet().set(Parser.REFERENCES_KEEP, KeepType.FIRST));
        optionsMap.put("references-keep-last", new MutableDataSet().set(Parser.REFERENCES_KEEP, KeepType.LAST));
        optionsMap.put("image-links-at-start", new MutableDataSet().set(Formatter.KEEP_IMAGE_LINKS_AT_START, true));
        optionsMap.put("explicit-links-at-start", new MutableDataSet().set(Formatter.KEEP_EXPLICIT_LINKS_AT_START, true));
        optionsMap.put("remove-empty-items", new MutableDataSet().set(Formatter.LIST_REMOVE_EMPTY_ITEMS, true));
        optionsMap.put("no-hard-breaks", new MutableDataSet().set(Formatter.KEEP_HARD_LINE_BREAKS, false));
        optionsMap.put("no-soft-breaks", new MutableDataSet().set(Formatter.KEEP_SOFT_LINE_BREAKS, false));
        //optionsMap.put("trailing-spaces-keep-all", new MutableDataSet().set(Formatter.KEEP_TRAILING_SPACES, TrailingSpaces.KEEP_ALL));
        //optionsMap.put("trailing-spaces-keep-line-break", new MutableDataSet().set(Formatter.KEEP_TRAILING_SPACES, TrailingSpaces.KEEP_LINE_BREAK));
        //optionsMap.put("trailing-spaces-keep-none", new MutableDataSet().set(Formatter.KEEP_TRAILING_SPACES, TrailingSpaces.KEEP_NONE));
        //optionsMap.put("code-trailing-spaces-keep-all", new MutableDataSet().set(Formatter.CODE_KEEP_TRAILING_SPACES, TrailingSpaces.KEEP_ALL));
        //optionsMap.put("code-trailing-spaces-keep-line-break", new MutableDataSet().set(Formatter.CODE_KEEP_TRAILING_SPACES, TrailingSpaces.KEEP_LINE_BREAK));
        //optionsMap.put("code-trailing-spaces-keep-none", new MutableDataSet().set(Formatter.CODE_KEEP_TRAILING_SPACES, TrailingSpaces.KEEP_NONE));
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final Formatter RENDERER = Formatter.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboCoreFormatterSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public Formatter renderer() {
        return RENDERER;
    }
}
