package com.vladsch.flexmark;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationRepository;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.ext.spec.example.SpecExampleExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComboFormatterIssueSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/formatter_issue_test_suite_spec.md";
    private static final DataHolder OPTIONS = ParserEmulationProfile.FIXED_INDENT.getProfileOptions()
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AbbreviationExtension.create(),
                    AnchorLinkExtension.create(),
                    AsideExtension.create(),
                    AttributesExtension.create(),
                    AutolinkExtension.create(),
                    DefinitionExtension.create(),
                    EmojiExtension.create(),
                    EnumeratedReferenceExtension.create(),
                    EscapedCharacterExtension.create(),
                    FootnoteExtension.create(),
                    InsExtension.create(),
                    JekyllFrontMatterExtension.create(),
                    JekyllTagExtension.create(),
                    MacrosExtension.create(),
                    SimTocExtension.create(),
                    SpecExampleExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    SuperscriptExtension.create(),
                    TablesExtension.create(),
                    TaskListExtension.create(),
                    TocExtension.create(),
                    TypographicExtension.create(),
                    WikiLinkExtension.create(),
                    YamlFrontMatterExtension.create()
            ));

    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        //optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        //optionsMap.put("option1", new MutableDataSet().set(RendererExtension.FORMATTER_OPTION1, true));
        optionsMap.put("item-indent-1", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT, 1));
        optionsMap.put("item-indent-2", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT, 2));
        optionsMap.put("no-soft-breaks", new MutableDataSet().set(Formatter.KEEP_SOFT_LINE_BREAKS, false));

        optionsMap.put("no-append-references", new MutableDataSet().set(Formatter.APPEND_TRANSFERRED_REFERENCES, false)
                .set(INCLUDED_DOCUMENT, "" +
                        "[^footnote]: Included footnote\n" +
                        "    with some extras\n" +
                        "\n" +
                        "*[abbr]: Abbreviation\n" +
                        "\n" +
                        "[ref]: ./link.md\n" +
                        "[image]: ./included.png\n" +
                        "\n" +
                        "[@enum]: Enumerated Reference [#]\n" +
                        "\n" +
                        ">>>macro1\n" +
                        "macro text\n" +
                        "<<<\n" +
                        "\n" +
                        "")
        );

        optionsMap.put("append-references", new MutableDataSet().set(Formatter.APPEND_TRANSFERRED_REFERENCES, true)
                .set(INCLUDED_DOCUMENT, "" +
                        "[^footnote]: Included footnote\n" +
                        "    with some extras\n" +
                        "\n" +
                        "*[abbr]: Abbreviation\n" +
                        "\n" +
                        "[ref]: ./link.md\n" +
                        "[image]: ./included.png\n" +
                        "\n" +
                        "[@enum]: Enumerated Reference [#]\n" +
                        "\n" +
                        ">>>macro1\n" +
                        "macro text\n" +
                        "<<<\n" +
                        "\n" +
                        "")
                .set(Formatter.REFERENCE_PLACEMENT, ElementPlacement.DOCUMENT_BOTTOM)
                .set(Formatter.REFERENCE_SORT, ElementPlacementSort.SORT)
        );
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final Formatter RENDERER = Formatter.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboFormatterIssueSpecTest(SpecExample example) {
        super(example);
    }

    @Override
    protected IParse adjustParserForInclusion(IParse parserWithOptions, Document includedDocument) {
        AbbreviationRepository abbreviationRepository = includedDocument.get(AbbreviationExtension.ABBREVIATIONS);
        if (!abbreviationRepository.isEmpty()) {
            // need to transfer it to parser
            return parserWithOptions.withOptions(new MutableDataSet().set(AbbreviationExtension.ABBREVIATIONS, abbreviationRepository));
        }
        return parserWithOptions;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
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
