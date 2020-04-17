package com.vladsch.flexmark.ext.tables;

import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.format.ColumnSort;
import com.vladsch.flexmark.util.format.MarkdownTable;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MarkdownSortTableTest extends MarkdownTableTestBase {
    @Test
    public void test_basic1A() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, false, false) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic1ALinkText() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, false, false) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|          Header1           | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------------------------|----------|----------|----------|----------|----------|\n" +
                "| column11                   | column28 | column31 | 11       | 028      | 31       |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13                   | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14                   | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15                   | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17                   | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18                   | column21 | column35 | 018      | 21       | 043      |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic1ALinkPageRef() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 |     11 |      028  |       31 |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, false, false) }, TextContainer.F_LINK_PAGE_REF, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|          Header1           | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------------------------|----------|----------|----------|----------|----------|\n" +
                "| column11                   | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column13                   | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14                   | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15                   | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column17                   | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18                   | column21 | column35 | 018      | 21       | 043      |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic1ALinkAnchor() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, false, false) }, TextContainer.F_LINK_ANCHOR, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|          Header1           | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------------------------|----------|----------|----------|----------|----------|\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column11                   | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column13                   | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14                   | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15                   | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column17                   | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18                   | column21 | column35 | 018      | 21       | 043      |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic1ALinkUrl() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, false, false) }, TextContainer.F_LINK_URL, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|          Header1           | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------------------------|----------|----------|----------|----------|----------|\n" +
                "| column11                   | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column13                   | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14                   | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15                   | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column17                   | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18                   | column21 | column35 | 018      | 21       | 043      |\n" +
                "| [column12](url12#anchor12) | column27 | column32 | 12       | column27 | 32       |\n" +
                "| [column16](url16#anchor16) | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic1D() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, true, false, false) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic2A() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(1, false, false, false) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "", out.toString());
    }

    @Test
    public void test_basic2D() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(1, true, false, false) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1AF() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(3, false, true, false) }, 0, MarkdownTable.ALL_SUFFIXES_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1AFSuffix() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 11NonNum |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, true, false) }, 0, MarkdownTable.ALL_SUFFIXES_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| 11        |\n" +
                "| 11Another |\n" +
                "| 11NonNum  |\n" +
                "| 12        |\n" +
                "| 16        |\n" +
                "| column15  |\n" +
                "| column17  |\n" +
                "| column18  |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1DFSuffix() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 11NonNum |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, true, true, false) }, 0, MarkdownTable.ALL_SUFFIXES_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| 16        |\n" +
                "| 12        |\n" +
                "| 11        |\n" +
                "| 11NonNum  |\n" +
                "| 11Another |\n" +
                "| column18  |\n" +
                "| column17  |\n" +
                "| column15  |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1ALSuffix() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 11NonNum |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, true, true) }, 0, MarkdownTable.ALL_SUFFIXES_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| column15  |\n" +
                "| column17  |\n" +
                "| column18  |\n" +
                "| 11Another |\n" +
                "| 11NonNum  |\n" +
                "| 11        |\n" +
                "| 12        |\n" +
                "| 16        |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1DLSuffix() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 11NonNum |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, true, true, true) }, 0, MarkdownTable.ALL_SUFFIXES_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| column18  |\n" +
                "| column17  |\n" +
                "| column15  |\n" +
                "| 16        |\n" +
                "| 12        |\n" +
                "| 11NonNum  |\n" +
                "| 11Another |\n" +
                "| 11        |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1AFSuffixNoSort() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11NonNum |\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, true, false) }, 0, MarkdownTable.ALL_SUFFIXES_NO_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| 11NonNum  |\n" +
                "| 11        |\n" +
                "| 11Another |\n" +
                "| 12        |\n" +
                "| 16        |\n" +
                "| column15  |\n" +
                "| column17  |\n" +
                "| column18  |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1DFSuffixNoSort() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11NonNum |\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, true, true, false) }, 0, MarkdownTable.ALL_SUFFIXES_NO_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| 16        |\n" +
                "| 12        |\n" +
                "| 11NonNum  |\n" +
                "| 11        |\n" +
                "| 11Another |\n" +
                "| column18  |\n" +
                "| column17  |\n" +
                "| column15  |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1ALSuffixNoSort() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11NonNum |\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, false, true, true) }, 0, MarkdownTable.ALL_SUFFIXES_NO_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| column15  |\n" +
                "| column17  |\n" +
                "| column18  |\n" +
                "| 11NonNum  |\n" +
                "| 11        |\n" +
                "| 11Another |\n" +
                "| 12        |\n" +
                "| 16        |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1DLSuffixNoSort() {
        MarkdownTable table = getTable("" +
                "| Header1  |\n" +
                "|----------|\n" +
                "| 11NonNum |\n" +
                "| 11 |\n" +
                "| 11Another |\n" +
                "| 12 |\n" +
                "| column15 |\n" +
                "| 16 |\n" +
                "| column17 |\n" +
                "| column18 |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(0, true, true, true) }, 0, MarkdownTable.ALL_SUFFIXES_NO_SORT);
        sorted.appendTable(out);

        assertEquals("", "" +
                "|  Header1  |\n" +
                "|-----------|\n" +
                "| column18  |\n" +
                "| column17  |\n" +
                "| column15  |\n" +
                "| 16        |\n" +
                "| 12        |\n" +
                "| 11NonNum  |\n" +
                "| 11        |\n" +
                "| 11Another |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1AL() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(3, false, true, true) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1DF() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(3, true, true, false) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicN1DL() {
        MarkdownTable table = getTable("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(3, true, true, true) }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicSpanA() {
        MarkdownTable table = getTable("" +
                "| Header1 | Header2 | Header3 |\n" +
                "|---------|---------|---------|\n" +
                "| span13  | span24  | span36  |\n" +
                "| span11                    |||\n" +
                "| span13  | span24  | span35  |\n" +
                "| span12  | span22           ||\n" +
                "| span11  | value2  |         |\n" +
                "| span13  | span23  | span34  |\n" +
                "| span11  | value1  |         |\n" +
                "| span13  | span23  | span33  |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] {
                ColumnSort.columnSort(0, false, false, false),
                ColumnSort.columnSort(1, false, false, false),
                ColumnSort.columnSort(2, false, false, false),
        }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1 | Header2 | Header3 |\n" +
                "|---------|---------|---------|\n" +
                "| span11                    |||\n" +
                "| span11  | value1  |         |\n" +
                "| span11  | value2  |         |\n" +
                "| span12  | span22           ||\n" +
                "| span13  | span23  | span33  |\n" +
                "| span13  | span23  | span34  |\n" +
                "| span13  | span24  | span35  |\n" +
                "| span13  | span24  | span36  |\n" +
                "", out.toString());
    }

    @Test
    public void test_basicSpanD() {
        MarkdownTable table = getTable("" +
                "| Header1 | Header2 | Header3 |\n" +
                "|---------|---------|---------|\n" +
                "| span13  | span24  | span36  |\n" +
                "| span11                    |||\n" +
                "| span13  | span24  | span35  |\n" +
                "| span12  | span22           ||\n" +
                "| span11  | value2  |         |\n" +
                "| span13  | span23  | span34  |\n" +
                "| span11  | value1  |         |\n" +
                "| span13  | span23  | span33  |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] {
                ColumnSort.columnSort(0, true, false, false),
                ColumnSort.columnSort(1, true, false, false),
                ColumnSort.columnSort(2, true, false, false),
        }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1 | Header2 | Header3 |\n" +
                "|---------|---------|---------|\n" +
                "| span13  | span24  | span36  |\n" +
                "| span13  | span24  | span35  |\n" +
                "| span13  | span23  | span34  |\n" +
                "| span13  | span23  | span33  |\n" +
                "| span12  | span22           ||\n" +
                "| span11  | value2  |         |\n" +
                "| span11  | value1  |         |\n" +
                "| span11                    |||\n" +
                "", out.toString());
    }

    @Test
    public void test_basicSpan1A2D3A() {
        MarkdownTable table = getTable("" +
                "| Header1 | Header2 | Header3 |\n" +
                "|---------|---------|---------|\n" +
                "| span13  | span24  | span36  |\n" +
                "| span11                    |||\n" +
                "| span13  | span24  | span35  |\n" +
                "| span12  | span22           ||\n" +
                "| span11  | value2  |         |\n" +
                "| span13  | span23  | span34  |\n" +
                "| span11  | value1  |         |\n" +
                "| span13  | span23  | span33  |\n" +
                "", formatOptionsAsIs("", null));

        MarkdownWriter out = new MarkdownWriter(MarkdownWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] {
                ColumnSort.columnSort(0, false, false, false),
                ColumnSort.columnSort(1, true, false, false),
                ColumnSort.columnSort(2, false, false, false),
        }, 0, null);
        sorted.appendTable(out);

        assertEquals("", "" +
                "| Header1 | Header2 | Header3 |\n" +
                "|---------|---------|---------|\n" +
                "| span11  | value2  |         |\n" +
                "| span11  | value1  |         |\n" +
                "| span11                    |||\n" +
                "| span12  | span22           ||\n" +
                "| span13  | span24  | span35  |\n" +
                "| span13  | span24  | span36  |\n" +
                "| span13  | span23  | span33  |\n" +
                "| span13  | span23  | span34  |\n" +
                "", out.toString());
    }

    @Test
    public void test_trackingOffset1() {
        String markdown = "" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column15 | column24 | column38 | 017^      | 0b11000  | 038      |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "";

        int pos = markdown.indexOf("^");
        CharSequence charSequence = markdown.substring(0, pos) + markdown.substring(pos + 1);
        BasedSequence source = BasedSequence.of(charSequence);
        MarkdownTable table = getTable(source, formatOptionsAsIs("", null));
        assertTrue(table.addTrackedOffset(TrackedOffset.track(pos, null, false)));
        HtmlWriter out = new HtmlWriter(0, HtmlWriter.F_FORMAT_ALL);
        MarkdownTable sorted = table.sorted(new ColumnSort[] { ColumnSort.columnSort(1, false, false, false) }, 0, null);
        sorted.appendTable(out);
        String sortedTable = out.toString(0, 0);
        int offset = sorted.getTrackedOffsetIndex(pos);

        assertEquals("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column15 | column24 | column38 | 017      | 0b11000  | 038      |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "", sortedTable);
        assertEquals("" +
                "| Header1  | Header2  | Header3  | Numeric1 | Numeric2 | Numeric3 |\n" +
                "|----------|----------|----------|----------|----------|----------|\n" +
                "| column18 | column21 | column35 | 018      | 21       | 043      |\n" +
                "| column17 | column22 | column36 | column17 | 22       | column36 |\n" +
                "| column16 | column23 | column37 | column16 | 0x17     | column37 |\n" +
                "| column15 | column24 | column38 | 017^      | 0b11000  | 038      |\n" +
                "| column14 | column25 | column34 | 0b1110   | 031      | 0x100010 |\n" +
                "| column13 | column26 | column33 | 0xD      | column26 | 0x21     |\n" +
                "| column12 | column27 | column32 | 12       | column27 | 32       |\n" +
                "| column11 | column28 | column31 | 11       | 028      | 31       |\n" +
                "", sortedTable.substring(0, offset) + "^" + sortedTable.substring(offset));
    }
}

