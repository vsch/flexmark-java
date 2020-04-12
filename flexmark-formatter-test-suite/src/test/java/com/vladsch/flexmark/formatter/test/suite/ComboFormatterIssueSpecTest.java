package com.vladsch.flexmark.formatter.test.suite;

import com.vladsch.flexmark.core.test.util.FormatterSpecTest;
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
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboFormatterIssueSpecTest extends FormatterSpecTest {
    final private static String SPEC_RESOURCE = "/formatter_issue_test_suite_spec.md";
    final public static @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);
    final private static DataHolder OPTIONS = ParserEmulationProfile.FIXED_INDENT.getProfileOptions()
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

    final private static DataKey<Boolean> USE_BUILDER = new DataKey<>("USE_BUILDER", false);

    final private static Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("item-indent-1", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT, 1));
        optionsMap.put("item-indent-2", new MutableDataSet().set(Parser.LISTS_ITEM_INDENT, 2));
        optionsMap.put("no-soft-breaks", new MutableDataSet().set(Formatter.KEEP_SOFT_LINE_BREAKS, false));
        optionsMap.put("use-builder", new MutableDataSet().set(USE_BUILDER, true));
        optionsMap.put("spacer", new MutableDataSet().set(TocExtension.BLANK_LINE_SPACER, true));
        optionsMap.put("text-only", new MutableDataSet().set(TocExtension.IS_TEXT_ONLY, true));
        optionsMap.put("no-anchor-links", new MutableDataSet().set(UNLOAD_EXTENSIONS, Collections.singletonList(AnchorLinkExtension.class)));

        optionsMap.put("no-append-references", new MutableDataSet().set(Formatter.APPEND_TRANSFERRED_REFERENCES, false)
                .set(TestUtils.INCLUDED_DOCUMENT, "" +
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
                .set(TestUtils.INCLUDED_DOCUMENT, "" +
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
    public ComboFormatterIssueSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap, OPTIONS);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = aggregate(OPTIONS, exampleOptions);

        if (USE_BUILDER.get(combinedOptions)) {
            return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), Formatter.builder(combinedOptions).build(), true) {
                @Override
                protected @NotNull String renderHtml() {
                    SequenceBuilder builder = SequenceBuilder.emptyBuilder(getDocument().getChars());
                    getRenderer().render(getDocument(), builder);
                    return builder.toString();
                }

                @Override
                protected void adjustParserForInclusion() {
                    super.adjustParserForInclusion();

                    AbbreviationRepository abbreviationRepository = AbbreviationExtension.ABBREVIATIONS.get((MutableDataHolder) ((Document) getIncludedDocument()));
                    if (!abbreviationRepository.isEmpty()) {
                        DataHolder withAbbreviations = getOptions().toMutable().set(AbbreviationExtension.ABBREVIATIONS, abbreviationRepository).toImmutable();
                        // need to transfer it to new instance of parser
                        setParser(Parser.builder(withAbbreviations).build());
                    }
                }
            };
        } else {
            return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), Formatter.builder(combinedOptions).build(), true) {
                @Override
                protected void adjustParserForInclusion() {
                    super.adjustParserForInclusion();

                    AbbreviationRepository abbreviationRepository = AbbreviationExtension.ABBREVIATIONS.get((MutableDataHolder) ((Document) getIncludedDocument()));
                    if (!abbreviationRepository.isEmpty()) {
                        DataHolder withAbbreviations = getOptions().toMutable().set(AbbreviationExtension.ABBREVIATIONS, abbreviationRepository).toImmutable();
                        // need to transfer it to new instance of parser
                        setParser(Parser.builder(withAbbreviations).build());
                    }
                }
            };
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }
}
