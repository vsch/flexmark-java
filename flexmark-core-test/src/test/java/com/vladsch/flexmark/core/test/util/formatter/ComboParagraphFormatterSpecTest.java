package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.IParseBase;
import com.vladsch.flexmark.test.util.spec.IRenderBase;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.format.CharWidthProvider;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.runners.Parameterized;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.vladsch.flexmark.util.sequence.SequenceUtils.EOL;

public class ComboParagraphFormatterSpecTest extends ComboCoreFormatterSpecTestBase {
    private static final String SPEC_RESOURCE = "/core_paragraph_formatter_spec.md";
    public static final @NotNull ResourceLocation RESOURCE_LOCATION = ResourceLocation.of(SPEC_RESOURCE);

    public static final DataKey<Integer> RIGHT_MARGIN = new DataKey<>("RIGHT_MARGIN", 0);
    public static final DataKey<String> FIRST_INDENT = new DataKey<>("FIRST_INDENT", "");
    public static final DataKey<String> INDENT = new DataKey<>("INDENT", "");
    public static final DataKey<Integer> FIRST_WIDTH_DELTA = new DataKey<>("FIRST_WIDTH_DELTA", 0);
    public static final DataKey<Character> EDIT_OP_CHAR = new DataKey<>("EDIT_OP_CHAR", SequenceUtils.NUL);
    public static final DataKey<Integer> EDIT_OP = new DataKey<>("EDIT_OP", 0);
    public static final DataKey<Boolean> RESTORE_TRACKED_SPACES = new DataKey<>("RESTORE_END_SPACES", false);
    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("margin", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customIntOption(option, params, ComboParagraphFormatterSpecTest::rightMarginOption)));
        optionsMap.put("first-indent", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customStringOption(option, params, ComboParagraphFormatterSpecTest::firstIndentOption)));
        optionsMap.put("indent", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customStringOption(option, params, ComboParagraphFormatterSpecTest::indentOption)));
        optionsMap.put("first-width-delta", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customIntOption(option, params, ComboParagraphFormatterSpecTest::firstWidthDeltaOption)));
        optionsMap.put("insert-char", new MutableDataSet().set(EDIT_OP, 1).set(EDIT_OP_CHAR, '\0'));
        optionsMap.put("insert-space", new MutableDataSet().set(EDIT_OP, 1).set(EDIT_OP_CHAR, ' '));
        optionsMap.put("delete-char", new MutableDataSet().set(EDIT_OP, -1).set(EDIT_OP_CHAR, '\0'));
        optionsMap.put("delete-space", new MutableDataSet().set(EDIT_OP, -1).set(EDIT_OP_CHAR, ' '));
        optionsMap.put("restore-tracked-spaces", new MutableDataSet().set(RESTORE_TRACKED_SPACES, true));
    }
    public ComboParagraphFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap);
    }

    static DataHolder rightMarginOption(@Nullable Integer params) {
        int value = params != null ? params : -1;
        return new MutableDataSet().set(RIGHT_MARGIN, value);
    }

    static DataHolder firstIndentOption(@Nullable String params) {
        String value = params != null ? params : "";
        return new MutableDataSet().set(FIRST_INDENT, value);
    }

    static DataHolder indentOption(@Nullable String params) {
        String value = params != null ? params : "";
        return new MutableDataSet().set(INDENT, value);
    }

    static DataHolder firstWidthDeltaOption(@Nullable Integer params) {
        int value = params != null ? params : -1;
        return new MutableDataSet().set(FIRST_WIDTH_DELTA, value);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(RESOURCE_LOCATION);
    }

    static class ParagraphTextNode extends Node {
        public ParagraphTextNode(@NotNull BasedSequence chars) {
            super(chars);
        }

        @NotNull
        @Override
        public BasedSequence[] getSegments() {
            return EMPTY_SEGMENTS;
        }
    }

    static class ParagraphParser extends IParseBase {
        public ParagraphParser() {
        }

        public ParagraphParser(DataHolder options) {
            super(options);
        }

        @Override
        public @NotNull Node parse(@NotNull BasedSequence input) {
            return new ParagraphTextNode(input);
        }
    }

    static class ParagraphFormatter extends IRenderBase {
        public ParagraphFormatter() {
        }

        public ParagraphFormatter(DataHolder options) {
            super(options);
        }

        final static String BANNER_TRACKED_OFFSETS = TestUtils.bannerText("Tracked Offsets");
        final static String BANNER_WITH_RANGES = TestUtils.bannerText("Ranges");
        final static String BANNER_RESULT = TestUtils.bannerText("Result");

        @Override
        public void render(@NotNull Node document, @NotNull Appendable output) {
            BasedSequence input = document.getChars();
            StringBuilder out = new StringBuilder();

            Pair<BasedSequence, int[]> info = TestUtils.extractMarkup(input);
            BasedSequence sequence = BasedSequence.of(info.getFirst());

            MarkdownParagraph formatter = new MarkdownParagraph(sequence, CharWidthProvider.NULL);
            DataHolder options = getOptions() == null ? DataHolder.NULL : getOptions();

            if (options.contains(INDENT)) formatter.setIndent(INDENT.get(options));
            if (options.contains(FIRST_INDENT)) formatter.setFirstIndent(FIRST_INDENT.get(options));
            if (options.contains(FIRST_WIDTH_DELTA)) formatter.setFirstWidthOffset(FIRST_WIDTH_DELTA.get(options));
            if (options.contains(RIGHT_MARGIN)) formatter.setWidth(RIGHT_MARGIN.get(options));
            if (options.contains(RESTORE_TRACKED_SPACES)) formatter.setRestoreTrackedSpaces(RESTORE_TRACKED_SPACES.get(options));
            formatter.setKeepSoftBreaks(false); // cannot keep line breaks when formatting as you type
            formatter.setKeepHardBreaks(true);

            int[] offsets = info.getSecond();
            SequenceBuilder builder1 = TestUtils.insertCaretMarkup(sequence, offsets);

            System.out.println(builder1.toStringWithRanges());

            for (int offset : offsets) {
                char c = EDIT_OP_CHAR.get(options);
                int editOp = EDIT_OP.get(options);
                formatter.addTrackedOffset(TrackedOffset.track(offset, editOp != 0 && c == ' ', editOp > 0, editOp < 0));
            }

            BasedSequence actual = formatter.wrapText();

            SequenceBuilder builder = sequence.getBuilder();
            actual.addSegments(builder.getSegmentBuilder());

            System.out.println(builder.toStringWithRanges());

            List<TrackedOffset> trackedOffsets = formatter.getTrackedOffsets();
            assert trackedOffsets.size() == offsets.length;
            int[] resultOffsets = new int[offsets.length];

            if (!trackedOffsets.isEmpty()) {
                TestUtils.appendBanner(out, BANNER_TRACKED_OFFSETS);
                int r = 0;
                for (TrackedOffset trackedOffset : trackedOffsets) {
                    int offset = trackedOffset.getIndex();
                    assert offset != -1;
                    out.append("[").append(Integer.toString(r)).append("]: ").append(trackedOffset.toString()).append(" --> ").append(offset).append("\n");
                    resultOffsets[r++] = offset;
                }
            }

            TestUtils.appendBannerIfNeeded(out, BANNER_WITH_RANGES);
            out.append(builder.toStringWithRanges().replace("\\n", "\n")).append(EOL);
            TestUtils.appendBannerIfNeeded(out, BANNER_RESULT);
            out.append(TestUtils.insertCaretMarkup(actual, resultOffsets).toSequence());

            try {
                output.append(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    final public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, new ParagraphParser(combinedOptions), new ParagraphFormatter(combinedOptions), true) {

            @Override
            protected @NotNull String renderHtml() {
                return super.renderHtml();
            }

            @Override
            protected @NotNull String renderAst() {
                return TestUtils.ast(getDocument());
            }
        };
    }
}
