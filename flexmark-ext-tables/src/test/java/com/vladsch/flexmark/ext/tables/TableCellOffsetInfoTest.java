package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TableCellOffsetInfo;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.format.options.DiscretionaryText;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TableCellOffsetInfoTest extends MarkdownTableTestBase {

    @Test
    public void test_nextOffsetStop1() {
        String markdown = "" +
                "|            Default             | Lefts                          |    Right | Centered in a column^ |   |\n" +
                "|            Default             | Lefts                          |    Right |        Center        |   |\n" +
                "|--------------------------------|:-------------------------------|---------:|:--------------------:|---|\n" +
                "| item 1                         | item 1                         |   125.30 |          a           |   |\n" +
                "| item 2                         | item 2                         | 1,234.00 |          bc          |   |\n" +
                "| item 3 much longer description | item 3 much longer description |    10.50 |         def          |   |\n" +
                "| item 4 short                   | item 4 short                   |    34.10 |          h           |   |\n" +
                "[ cap**tion** ]\n" +
                "";

        int pos = markdown.indexOf("^");
        CharSequence charSequence = markdown.substring(0, pos) + markdown.substring(pos + 1);
        BasedSequence source = BasedSequence.of(charSequence);
        MarkdownTable table = getTable(source, formatOptions("", null).toMutable().set(TableFormatOptions.FORMAT_TABLE_LEFT_ALIGN_MARKER, DiscretionaryText.AS_IS));
        HtmlWriter out = new HtmlWriter(0, HtmlWriter.F_FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.toString(0, 0);
        TableCellOffsetInfo offsetInfo = table.getCellOffsetInfo(pos);

        assertEquals("" +
                "|            Default             | Lefts                          |    Right | Centered in a column^ |   |\n" +
                "|            Default             | Lefts                          |    Right |        Center        |   |\n" +
                "|--------------------------------|:-------------------------------|---------:|:--------------------:|---|\n" +
                "| item 1                         | item 1                         |   125.30 |          a           |   |\n" +
                "| item 2                         | item 2                         | 1,234.00 |          bc          |   |\n" +
                "| item 3 much longer description | item 3 much longer description |    10.50 |         def          |   |\n" +
                "| item 4 short                   | item 4 short                   |    34.10 |          h           |   |\n" +
                "[ cap**tion** ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "|            Default             | Lefts                          |    Right | Centered in a column | ^  |\n" +
                "|            Default             | Lefts                          |    Right |        Center        |   |\n" +
                "|--------------------------------|:-------------------------------|---------:|:--------------------:|---|\n" +
                "| item 1                         | item 1                         |   125.30 |          a           |   |\n" +
                "| item 2                         | item 2                         | 1,234.00 |          bc          |   |\n" +
                "| item 3 much longer description | item 3 much longer description |    10.50 |         def          |   |\n" +
                "| item 4 short                   | item 4 short                   |    34.10 |          h           |   |\n" +
                "[ cap**tion** ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));
    }

    @Test
    public void test_nextOffsetStop() {
        String markdown = "" +
                "^| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "";

        int pos = markdown.indexOf("^");
        CharSequence charSequence = markdown.substring(0, pos) + markdown.substring(pos + 1);
        BasedSequence source = BasedSequence.of(charSequence);
        MarkdownTable table = getTable(source, formatOptions("", null));
        HtmlWriter out = new HtmlWriter(0, HtmlWriter.F_FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.toString(0, 0);
        TableCellOffsetInfo offsetInfo = table.getCellOffsetInfo(pos);

        assertEquals("" +
                "^| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features^                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic^ | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced^ |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced | ^   |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|^:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|^:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:^|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|^:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:^|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|^:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---^|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4^                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X^   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X^     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     | ^   |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub.^ |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X^   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X^     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     | ^   |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting^                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X^   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X^     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     | ^   |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing^ ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.nextOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n^" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));
    }

    @Test
    public void test_previousOffsetStop() {
        String markdown = "" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n^" +
                "";

        int pos = markdown.indexOf("^");
        CharSequence charSequence = markdown.substring(0, pos) + markdown.substring(pos + 1);
        BasedSequence source = BasedSequence.of(charSequence);
        MarkdownTable table = getTable(source, formatOptions("", null));
        HtmlWriter out = new HtmlWriter(0, HtmlWriter.F_FORMAT_ALL);
        table.appendTable(out);
        String formattedTable = out.toString(0, 0);
        TableCellOffsetInfo offsetInfo = table.getCellOffsetInfo(pos);

        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n^" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing^ ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     | ^   |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X^     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X^   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting^                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     | ^   |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X^     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X^   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub.^ |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     | ^   |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X^     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X^   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4^                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---^|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|^:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:^|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|^:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:^|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|^:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------^|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced |    |\n" +
                "|^:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced | ^   |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic | Enhanced^ |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features                                                                        | Basic^ | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "| Features^                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));

        offsetInfo = offsetInfo.previousOffsetStop(null);
        assertEquals("" +
                "^| Features                                                                        | Basic | Enhanced |    |\n" +
                "|:--------------------------------------------------------------------------------|:-----:|:--------:|:---|\n" +
                "| Works with builds 143.2730 or newer, product version IDEA 14.1.4                |   X   |    X     |    |\n" +
                "| Preview Tab so you can see what the rendered markdown will look like on GitHub. |   X   |    X     |    |\n" +
                "| Syntax highlighting                                                             |   X   |    X     |    |\n" +
                "[ testing ]\n" +
                "", formattedTable.substring(0, offsetInfo.offset) + "^" + formattedTable.substring(offsetInfo.offset));
    }
}
