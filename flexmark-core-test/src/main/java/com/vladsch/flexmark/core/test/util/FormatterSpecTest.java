package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.vladsch.flexmark.formatter.Formatter.RESTORE_TRACKED_SPACES;

public abstract class FormatterSpecTest extends FormatterTranslationSpecTestBase {
    public FormatterSpecTest(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, optionMap, defaultOptions);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);

        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), Formatter.builder(combinedOptions).build(), true) {
            @NotNull List<TrackedOffset> trackedOffsets = Collections.emptyList();
            BasedSequence trackedSequence;
            BasedSequence originalSequence;

            @Override
            public void parse(CharSequence input) {
                originalSequence = BasedSequence.of(input);
                Pair<BasedSequence, int[]> extractMarkup = TestUtils.extractMarkup(originalSequence);
                trackedSequence = extractMarkup.getFirst();
                super.parse(trackedSequence.toString());

                if (extractMarkup.getSecond().length > 0) {
                    trackedOffsets = new ArrayList<>(extractMarkup.getSecond().length);
                    char c = EDIT_OP_CHAR.get(myOptions);
                    int editOp = EDIT_OP.get(myOptions);

                    for (int offset : extractMarkup.getSecond()) {
                        TrackedOffset trackedOffset = TrackedOffset.track(offset, editOp != 0 && c == ' ', editOp > 0, editOp < 0);

                        trackedOffset.setSpacesBefore(trackedSequence.getBaseSequence().countTrailingSpaceTab(offset));
                        trackedOffset.setSpacesAfter(trackedSequence.getBaseSequence().countLeadingSpaceTab(offset));

                        trackedOffsets.add(trackedOffset);
                    }

                    Formatter.TRACKED_SEQUENCE.set(getDocument().getDocument(), trackedSequence);
                    Formatter.TRACKED_OFFSETS.set(getDocument().getDocument(), trackedOffsets);
                    RESTORE_TRACKED_SPACES.set(getDocument().getDocument(), RESTORE_TRACKED_SPACES.get(myOptions));
                }

                Formatter.DOCUMENT_FIRST_PREFIX.set(getDocument().getDocument(), Formatter.DOCUMENT_FIRST_PREFIX.get(myOptions));
                Formatter.DOCUMENT_PREFIX.set(getDocument().getDocument(), Formatter.DOCUMENT_PREFIX.get(myOptions));
            }

            @Override
            protected @NotNull String renderHtml() {
                if (trackedOffsets.isEmpty() && !SHOW_LINE_RANGES.get(myOptions)) {
                    return getRenderer().render(getDocument());
                } else {
                    if (SharedDataKeys.RUNNING_TESTS.get(myOptions)) {
                        System.out.printf("%s:%d%n", myExample.getSection(), myExample.getExampleNumber());
                    }

                    SequenceBuilder builder = getDocument().getChars().getBuilder();
                    getRenderer().render(getDocument(), builder);
                    String html = builder.toString();

                    // NOTE: need to get document since the top node is not guaranteed to be a document for generic renderer
                    List<TrackedOffset> trackedOffsetList = Formatter.TRACKED_OFFSETS.get(getDocument().getDocument());
                    assert trackedOffsetList.isEmpty() || trackedOffsetList == trackedOffsets;

                    int[] offsets = new int[trackedOffsets.size()];
                    int i = 0;

                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (trackedOffset.isResolved()) {
                            offsets[i++] = trackedOffset.getIndex();
                        } else {
                            System.out.println(String.format("Offset %s is not resolved", trackedOffset.toString()));
                        }
                    }

                    if (i < offsets.length) {
                        offsets = Arrays.copyOf(offsets, i);
                    }

                    String result = TestUtils.insertCaretMarkup(BasedSequence.of(html), offsets).toString();

                    if (SHOW_LINE_RANGES.get(myOptions)) {
                        StringBuilder out = new StringBuilder();
                        out.append(result);
                        if (trackedSequence == getDocument().getDocument().getChars()) {
                            TestUtils.appendBanner(out, TestUtils.bannerText("Ranges"), false);
                            out.append(builder.toStringWithRanges(false));
                        } else {
                            if (!trackedOffsets.isEmpty()) {
                                TestUtils.appendBanner(out, TestUtils.bannerText("Tracked Offsets"), false);
                                int i1 = 0;
                                for (TrackedOffset trackedOffset1 : trackedOffsets) {
                                    out.append("[").append(i1).append("]: ").append(trackedOffset1.toString()).append("\n");
                                    i1++;
                                }
                            }
                            TestUtils.appendBanner(out, TestUtils.bannerText("Ranges"), !trackedOffsets.isEmpty());
                            BasedSequence sequence = builder.toSequence(trackedSequence);
                            out.append(sequence.getBuilder().append(sequence).toStringWithRanges(false)).append("\n");
                            TestUtils.appendBanner(out, TestUtils.bannerText("Segments"), false);
                            out.append(sequence.getBuilder().append(sequence).getSegmentBuilder().toString());
                        }
                        result = out.toString();
                    }
                    return result;
                }
            }
        };
    }
}
