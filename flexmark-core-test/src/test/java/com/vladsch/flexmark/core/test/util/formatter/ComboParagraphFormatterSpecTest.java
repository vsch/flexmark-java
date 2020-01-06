package com.vladsch.flexmark.core.test.util.formatter;

import com.vladsch.flexmark.formatter.Formatter;
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

    public static final DataKey<Integer> FIRST_WIDTH_DELTA = new DataKey<>("FIRST_WIDTH_DELTA", 0);
    private static final Map<String, DataHolder> optionsMap = new HashMap<>();
    static {
        optionsMap.put("first-width-delta", new MutableDataSet().set(TestUtils.CUSTOM_OPTION, (option, params) -> TestUtils.customIntOption(option, params, ComboParagraphFormatterSpecTest::firstWidthDeltaOption)));
    }
    public ComboParagraphFormatterSpecTest(@NotNull SpecExample example) {
        super(example, optionsMap);
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

            if (options.contains(Formatter.DOCUMENT_PREFIX)) formatter.setIndent(Formatter.DOCUMENT_PREFIX.get(options));
            if (options.contains(Formatter.DOCUMENT_FIRST_PREFIX)) formatter.setFirstIndent(Formatter.DOCUMENT_FIRST_PREFIX.get(options));
            if (options.contains(FIRST_WIDTH_DELTA)) formatter.setFirstWidthOffset(FIRST_WIDTH_DELTA.get(options));
            if (options.contains(Formatter.RIGHT_MARGIN)) formatter.setWidth(Formatter.RIGHT_MARGIN.get(options));
            if (options.contains(Formatter.RESTORE_TRACKED_SPACES)) formatter.setRestoreTrackedSpaces(Formatter.RESTORE_TRACKED_SPACES.get(options));
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
