package com.vladsch.flexmark.core.test.util;

import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class FormatterSpecTest extends FormatterTranslationSpecTestBase {
    public FormatterSpecTest(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, optionMap, defaultOptions);
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, Parser.builder(combinedOptions).build(), Formatter.builder(combinedOptions).build(), true) {
            ArrayList<TrackedOffset> trackedOffsets;

            @Override
            public void parse(CharSequence input) {
                Pair<BasedSequence, int[]> extractMarkup = TestUtils.extractMarkup(BasedSequence.of(input));
                super.parse(extractMarkup.getFirst());

                if (extractMarkup.getSecond().length > 0) {
                    trackedOffsets = new ArrayList<>(extractMarkup.getSecond().length);

                    for (int offset : extractMarkup.getSecond()) {
                        trackedOffsets.add(TrackedOffset.track(offset));
                    }

                    Formatter.TRACKED_OFFSETS.set(getDocument().getDocument(), trackedOffsets);
                }
            }

            @Override
            protected @NotNull String renderHtml() {
                if (trackedOffsets == null) {
                    return getRenderer().render(getDocument());
                } else {
                    String html = ((Formatter)getRenderer()).render(getDocument(), getDocument().getDocument().getChars().getBuilder());
                    List<TrackedOffset> trackedOffsetList = Formatter.TRACKED_OFFSETS.get(getDocument().getDocument());
                    assert trackedOffsetList == trackedOffsets;

                    int[] offsets = new int[trackedOffsets.size()];
                    int i = 0;

                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (trackedOffset.isResolved()) {
                            offsets[i++] = trackedOffset.getIndex();
                        }
                    }

                    if (i < offsets.length) {
                        offsets = Arrays.copyOf(offsets, i);
                    }

                    return TestUtils.insertCaretMarkup(BasedSequence.of(html), offsets).toString();
                }
            }
        };
    }
}
