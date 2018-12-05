package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.formatter.internal.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.format.options.TableCaptionHandling;
import com.vladsch.flexmark.util.mappers.CharWidthProvider;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.junit.runners.Parameterized;

import java.util.*;

public class ComboTableFormatterSpecTest extends ComboSpecTestCase {
    private static final String SPEC_RESOURCE = "/ext_tables_formatter_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            //.set(FormattingRenderer.INDENT_SIZE, 2)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            .set(Parser.EXTENSIONS, Collections.singleton(TablesExtension.create()))
            .set(Parser.LISTS_AUTO_LOOSE, false)
            .set(Parser.BLANK_LINES_IN_AST, true)
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    static {
        optionsMap.put("gfm", new MutableDataSet()
                .set(TablesExtension.COLUMN_SPANS, false)
                .set(TablesExtension.APPEND_MISSING_COLUMNS, true)
                .set(TablesExtension.DISCARD_EXTRA_COLUMNS, true)
                .set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, true)
        );
        optionsMap.put("no-caption", new MutableDataSet().set(TablesExtension.FORMAT_REMOVE_CAPTION, true));
        optionsMap.put("no-alignment", new MutableDataSet().set(TablesExtension.FORMAT_APPLY_COLUMN_ALIGNMENT, false));
        optionsMap.put("no-width", new MutableDataSet().set(TablesExtension.FORMAT_ADJUST_COLUMN_WIDTH, false));
        optionsMap.put("keep-whitespace", new MutableDataSet().set(TablesExtension.TRIM_CELL_WHITESPACE, false));
        optionsMap.put("lead-trail-pipes", new MutableDataSet().set(TablesExtension.FORMAT_LEAD_TRAIL_PIPES, false));
        optionsMap.put("space-around-pipe", new MutableDataSet().set(TablesExtension.FORMAT_SPACE_AROUND_PIPES, false));
        optionsMap.put("adjust-column-width", new MutableDataSet().set(TablesExtension.FORMAT_ADJUST_COLUMN_WIDTH, false));
        optionsMap.put("apply-column-alignment", new MutableDataSet().set(TablesExtension.FORMAT_APPLY_COLUMN_ALIGNMENT, false));
        optionsMap.put("fill-missing-columns", new MutableDataSet().set(TablesExtension.FORMAT_FILL_MISSING_COLUMNS, true));
        optionsMap.put("left-align-marker-as-is", new MutableDataSet().set(TablesExtension.FORMAT_LEFT_ALIGN_MARKER, DiscretionaryText.AS_IS));
        optionsMap.put("left-align-marker-add", new MutableDataSet().set(TablesExtension.FORMAT_LEFT_ALIGN_MARKER, DiscretionaryText.ADD));
        optionsMap.put("left-align-marker-remove", new MutableDataSet().set(TablesExtension.FORMAT_LEFT_ALIGN_MARKER, DiscretionaryText.REMOVE));
        optionsMap.put("dummy-identifier", new MutableDataSet().set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true));
        optionsMap.put("line-prefix", new MutableDataSet().set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, ">   "));
        optionsMap.put("markdown-navigator", new MutableDataSet()
                .set(TablesExtension.FORMAT_TABLE_INDENT_PREFIX, "")
                .set(TablesExtension.FORMAT_MIN_SEPARATOR_COLUMN_WIDTH, 3)
                .set(TablesExtension.FORMAT_LEAD_TRAIL_PIPES, true)
                .set(TablesExtension.FORMAT_ADJUST_COLUMN_WIDTH, true)
                .set(TablesExtension.FORMAT_FILL_MISSING_COLUMNS, true)
                .set(TablesExtension.FORMAT_LEFT_ALIGN_MARKER, DiscretionaryText.ADD)
                .set(TablesExtension.FORMAT_TABLE_CAPTION_SPACES, DiscretionaryText.AS_IS)
                .set(TablesExtension.FORMAT_SPACE_AROUND_PIPES, true)
                .set(TablesExtension.FORMAT_TABLE_CAPTION, TableCaptionHandling.AS_IS)
                .set(TablesExtension.FORMAT_APPLY_COLUMN_ALIGNMENT, true)
                .set(TablesExtension.MIN_SEPARATOR_DASHES, 3)
                .set(TablesExtension.FORMAT_REMOVE_CAPTION, false)
                .set(TablesExtension.TRIM_CELL_WHITESPACE, true)
.set(TablesExtension.FORMAT_CHAR_WIDTH_PROVIDER, new CharWidthProvider() {
                    @Override
                    public int spaceWidth() {
                        return 1;
                    }

                    @Override
                    public int charWidth(final char c) {
                        return c == Parsing.INTELLIJ_DUMMY_IDENTIFIER.charAt(0) ? 0: 1;
                    }

                    @Override
                    public int charWidth(final CharSequence s) {
                        return BasedSequenceImpl.of(s).countNotChars(Parsing.INTELLIJ_DUMMY_IDENTIFIER.charAt(0));
                    }
                })
        );
    }

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final Formatter RENDERER = Formatter.builder(OPTIONS).build();

    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboTableFormatterSpecTest(SpecExample example) {
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
