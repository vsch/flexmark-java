package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.*;

public class MarkdownParagraphTest {

    @Test
    public void test_wrapIndentedLines() {
        BasedSequence input = BasedSequence.of("Add: configuration for repeated prefixes in items, which would `be #2` copied when adding/splitting an item. In other words they\n" +
                "    would be treated equivalent to task item marker prefix. That way\n" +
                "    standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes would be automatically copied.");

        String expected = "Add: configuration for repeated prefixes in items, which would `be\n" +
                "#2` copied when adding/splitting an item. In other words they\n" +
                "would be treated equivalent to task item marker prefix. That way\n" +
                "standard: `Add: `, `Fix: `, `Break: ` and `Deprecate: ` prefixes\n" +
                "would be automatically copied.";

        MarkdownParagraph formatter = new MarkdownParagraph(input, CharWidthProvider.NULL);
        formatter.setFirstIndent(0);
        formatter.setWidth(66);
        formatter.setFirstWidthOffset(0);
        formatter.setKeepLineBreaks(false); // cannot keep line breaks when formatting as you type
        formatter.setKeepHardBreaks(true);

        BasedSequence actual = formatter.computeLeftAlignedSequence();
        assertEquals(expected, actual.toString());
    }
}
