package com.vladsch.flexmark.html.parser;

import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.html.HtmlFormattingAppendableBase;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import java.util.*;

public class FlexmarkHtmlParser {
    // public static final DataKey<FlexmarkHtmlParserRepository> FLEXMARK_HTML_PARSERS = new DataKey<>("FLEXMARK_HTML_PARSERS", new DataValueFactory<FlexmarkHtmlParserRepository>() { @Override public FlexmarkHtmlParserRepository create(DataHolder options) { return new FlexmarkHtmlParserRepository(options); } });
    // public static final DataKey<KeepType> FLEXMARK_HTML_PARSERS_KEEP = new DataKey<>("FLEXMARK_HTML_PARSERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates
    // public static final DataKey<Boolean> FLEXMARK_HTML_PARSER_OPTION1 = new DataKey<>("FLEXMARK_HTML_PARSER_OPTION1", false);
    // public static final DataKey<String> FLEXMARK_HTML_PARSER_OPTION2 = new DataKey<>("FLEXMARK_HTML_PARSER_OPTION2", "default");
    // public static final DataKey<Integer> FLEXMARK_HTML_PARSER_OPTION3 = new DataKey<>("FLEXMARK_HTML_PARSER_OPTION3", Integer.MAX_VALUE);
    // public static final DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");
    // public static final DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");
    //public static final LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");
    private final Stack<CharSequence> myIndentStack;
    private final Stack<State> myElementStack;
    private final Map<String, String> myAbbreviations;

    private State myState;

    private FlexmarkHtmlParser() {
        myIndentStack = new Stack<>();
        myElementStack = new Stack<>();
        myAbbreviations = new HashMap<>();
    }

    private static class State {
        Element myParent;
        List<Element> myElements;
        int myIndex;

        public State(final Element parent) {
            myParent = parent;
            myElements = parent.children();
            myIndex = 0;
        }
    }

    public void parse(FormattingAppendable out, String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();
        processHtmlTree(out, body);
    }

    private void processHtmlTree(FormattingAppendable out, Element parent) {
        State oldState = myState;
        myState = new State(parent);

        Element element;

        while ((element = peek()) != null) {
            processElement(out, element);
        }

        if (oldState != myState) {
            throw new IllegalStateException("State not equal after process");
        }
    }

    private void processElement(FormattingAppendable out, Element element) {
        TagParam tagParam = getTagParam(element);
        boolean processed = false;

        if (tagParam != null) {
            switch (tagParam.tagType) {
                // @formatter:off
                case A : processA(out, element); break;
                case ABBR : processed = processAbbr(out, element); break;
                case ASIDE : processed = processAside(out, element); break;
                case BLOCKQUOTE : processed = processBlockquote(out, element); break;
                case CODE : processed = processCode(out, element); break;
                case DEL : processed = processDel(out, element); break;
                case DIV : processed = processDiv(out, element); break;
                case DL : processed = processDl(out, element); break;
                case EMPHASIS : processed = processEmphasis(out, element); break;
                case HEADING : processed = processHeading(out, element); break;
                case HR : processed = processHr(out, element); break;
                case IMG : processed = processImg(out, element); break;
                case INS : processed = processIns(out, element); break;
                case LI : processed = processLi(out, element); break;
                case OL : processed = processOl(out, element); break;
                case P : processed = processP(out, element); break;
                case PRE : processed = processPre(out, element); break;
                case STRONG : processed = processStrong(out, element); break;
                case SUB : processed = processSub(out, element); break;
                case SUP : processed = processSup(out, element); break;
                case  UL : processed = processUl(out, element); break;
                case TABLE : processed = processTable(out, element); break;
                // @formatter:on
            }
        }

        if (!processed) {
            // wrap markdown in HTML tag
            if (element.isBlock()) {
                String s = element.outerHtml();
                int pos = s.indexOf(">");
                out.append(s.substring(0, pos));
                next();

                processHtmlTree(out, element);

                int endPos = s.lastIndexOf("<");
                out.append(s.substring(endPos));
            } else {
                out.append(element.toString());
                next();
            }
        }
    }

    private void processTextNodes(FormattingAppendable out, Element element) {
        for (Node child : element.childNodes()) {
            if (child instanceof TextNode) {
                TextNode textNode = (TextNode) child;
                out.append(((TextNode) child).getWholeText());
            } else if (child instanceof Element) {
                processElement(out, element);
            }
        }
    }

    private boolean processA(FormattingAppendable out, Element element) {
        skip();

        // see if it is an anchor or a link
        if (element.hasAttr("href")) {
            out.append('[');
            processTextNodes(out, element);
            out.append(']');
            out.append('(').append(element.attr("href")).append(")");
        }
        return true;
    }

    private boolean processAbbr(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processAside(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processBlockquote(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processCode(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processDel(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processDiv(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processDl(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processEmphasis(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processHeading(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processHr(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processImg(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processIns(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processLi(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processOl(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processP(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processPre(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processStrong(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processSub(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processSup(FormattingAppendable out, Element element) {
        return true;
    }

    private boolean processUl(FormattingAppendable out, Element element) {
        return true;
    }



    private TagParam getTagParam(final Element element) {
        return ourTagProcessors.get(element.tagName().toLowerCase());
    }

    private enum CellAlignment {
        NONE,
        LEFT,
        CENTER,
        RIGHT;

        static CellAlignment getAlignment(String alignment) {
            for (CellAlignment cellAlignment : values()) {
                if (cellAlignment.name().equalsIgnoreCase(alignment)) {
                    return cellAlignment;
                }
            }
            return NONE;
        }
    }

    private static class TableCell {
        final String text;
        final int columnSpan;
        final CellAlignment alignment;

        public TableCell(final String text, final int columnSpan, final CellAlignment alignment) {
            this.text = text;
            this.columnSpan = columnSpan;
            this.alignment = alignment != null ? alignment : CellAlignment.NONE;
        }
    }

    private static class TableRow {
        final List<TableCell> cells = new ArrayList<>();
        int columns = 0;

        void addCell(TableCell cell) {
            cells.add(cell);
            columns += cell.columnSpan;
        }

        int getColumns() {
            return cells.size();
        }

        int getSpannedColumns() {
            int columns = 0;
            for (TableCell cell : cells) {
                columns += cell.columnSpan;
            }
            return columns;
        }
    }

    private static class Table {
        List<TableRow> heading = new ArrayList<>();
        List<TableRow> body = new ArrayList<>();
        boolean isHeading;
        String caption;

        int getHeadingRows() {
            return heading.size();
        }

        int getBodyRows() {
            return body.size();
        }

        int getHeadingColumns() {
            return getMaxColumns(heading);
        }

        int getBodyColumns() {
            return getMaxColumns(body);
        }

        int getMinColumns() {
            int headingColumns = getMinColumns(heading);
            int bodyColumns = getMinColumns(body);
            return headingColumns < bodyColumns ? headingColumns : bodyColumns;
        }

        int getMaxColumns() {
            int headingColumns = getMaxColumns(heading);
            int bodyColumns = getMaxColumns(body);
            return headingColumns < bodyColumns ? headingColumns : bodyColumns;
        }

        int getMaxColumns(List<TableRow> rows) {
            int columns = 0;
            for (TableRow row : rows) {
                int spans = row.getSpannedColumns();
                if (columns < spans) columns = spans;
            }
            return columns;
        }

        int getMinColumns(List<TableRow> rows) {
            int columns = 0;
            for (TableRow row : rows) {
                int spans = row.getSpannedColumns();
                if (columns > spans || columns == 0) columns = spans;
            }
            return columns;
        }
    }

    private Table myTable;

    private boolean processTable(FormattingAppendable out, Element table) {
        Table oldTable = myTable;

        skip();
        pushState(table);

        myTable = new Table();

        Element element;
        while ((element = next()) != null) {
            TagParam tagParam = getTagParam(element);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    // @formatter:off
                    case CAPTION : ; break;
                    case TBODY : processTableSection(out, false, element); break;
                    case THEAD : processTableSection(out, true, element); break;
                    case TR : {
                        myTable.isHeading = false;
                        processTableRow(out, element);
                    }
                    break;
                    // @formatter:on
                }
            } else {
                // nothing, skip it
            }
        }

        int sepColumns = myTable.getMaxColumns();

        if (sepColumns > 0) {
            // we will prepare the separator based on max columns
            CellAlignment[] alignments = new CellAlignment[sepColumns];
            int[] maxColumns = new int[sepColumns];

            if (myTable.heading.size() > 0) {
                int i = 0;
                for (TableRow row : myTable.heading) {
                    int j = 0;
                    int jSpan = 0;
                    for (TableCell cell : row.cells) {
                        if (i == 0 && cell.alignment != CellAlignment.NONE) {
                            alignments[jSpan] = cell.alignment;
                        }

                        if (j == 0) out.append('|');
                        out.append(cell.text);
                        out.repeat('|', cell.columnSpan);
                        if (maxColumns[jSpan] < cell.text.length()) maxColumns[jSpan] = cell.text.length();

                        j++;
                        jSpan += cell.columnSpan;
                    }

                    if (j > 0) out.append('\n');
                    i++;
                }
            }

            {
                int j = 0;
                for (CellAlignment alignment : alignments) {
                    int colonCount = alignment == CellAlignment.LEFT || alignment == CellAlignment.RIGHT ? 1 : alignment == CellAlignment.CENTER ? 2 : 0;
                    int dashCount = maxColumns[j] - colonCount;

                    if (dashCount < 1) dashCount = 1;

                    if (j == 0) out.append('|');
                    out.append(':');
                    out.repeat('-', dashCount);
                    out.append(':');
                    j++;
                }
                out.append('\n');
            }

            if (myTable.body.size() > 0) {
                int i = 0;
                for (TableRow row : myTable.body) {
                    int j = 0;
                    int jSpan = 0;
                    for (TableCell cell : row.cells) {
                        if (j == 0) out.append('|');
                        out.append(cell.text);
                        out.repeat('|', cell.columnSpan);
                        if (maxColumns[jSpan] < cell.text.length()) maxColumns[jSpan] = cell.text.length();

                        j++;
                        jSpan += cell.columnSpan;
                    }

                    if (j > 0) out.append('\n');
                    i++;
                }
            }
        }

        myTable = oldTable;
        popState();
        return true;
    }

    private boolean processTableSection(FormattingAppendable out, boolean isHeading, Element element) {
        pushState(element);

        myTable.isHeading = isHeading;

        while ((element = next()) != null) {
            TagParam tagParam = getTagParam(element);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    // @formatter:off
                    case TR : processTableRow(out, element); break;
                    // @formatter:on
                }
            } else {
                // nothing, skip it
            }
        }

        popState();
        return true;
    }

    private boolean processTableRow(FormattingAppendable out, Element element) {
        pushState(element);

        while ((element = next()) != null) {
            TagParam tagParam = getTagParam(element);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    // @formatter:off
                    case TH : processTableCell(out, element); break;
                    case TD : processTableCell(out, element); break;
                    // @formatter:on
                }
            } else {
                // nothing, skip it
            }
        }

        popState();
        return true;
    }

    private boolean processTableCell(FormattingAppendable out, Element element) {
        FormattingAppendable cell = new HtmlFormattingAppendableBase<>(new StringBuilder());
        processHtmlTree(cell, element);
        TableRow tableRow = myTable.isHeading ? myTable.heading.get(myTable.heading.size() - 1) : myTable.body.get(myTable.body.size() - 1);
        int span = 1;
        CellAlignment alignment = null;

        if (element.hasAttr("colSpan")) {
            try {
                span = Integer.parseInt(element.attr("colSpan"));
            } catch (NumberFormatException ignored) {

            }
        }

        if (element.hasAttr("align")) {
            alignment = CellAlignment.getAlignment(element.attr("align"));
        }

        tableRow.addCell(new TableCell(cell.toString(), span, alignment));
        return true;
    }

    private void pushState(Element parent) {
        myElementStack.push(myState);
        myState = new State(parent);
    }

    private void popState() {
        if (myElementStack.isEmpty()) throw new IllegalStateException("popState with an empty stack");
        myState = myElementStack.pop();
    }

    private void addPrefix(FormattingAppendable out, CharSequence prefix) {
        StringBuilder sb = new StringBuilder(out.getPrefix().length() + prefix.length());
        myIndentStack.push(out.getPrefix());
        sb.append(out.getPrefix());
        sb.append(prefix);
        out.setPrefix(sb.toString());
    }

    private void popPrefix(FormattingAppendable out) {
        if (myIndentStack.isEmpty()) throw new IllegalStateException("popPrefix with an empty stack");

        CharSequence prefix = myIndentStack.pop();
        out.setPrefix(prefix);
    }

    private Element peek() {
        if (myState.myIndex < myState.myElements.size()) myState.myElements.get(myState.myIndex);
        return null;
    }

    private Element peek(int skip) {
        if (myState.myIndex + skip < myState.myElements.size()) myState.myElements.get(myState.myIndex + skip);
        return null;
    }

    private Element next() {
        Element next = peek();
        if (next != null) myState.myIndex++;
        return next;
    }

    private Element skip() {
        Element next = peek();
        if (next != null) myState.myIndex++;
        return peek();
    }

    private Element next(int skip) {
        if (skip > 0) {
            Element next = peek(skip - 1);
            if (next != null) myState.myIndex += skip;
            return next;
        }
        return peek();
    }

    private Element skip(int skip) {
        if (skip > 0) {
            Element next = peek(skip - 1);
            if (next != null) myState.myIndex += skip;
            return peek();
        }
        return peek();
    }

    enum TagType {
        A,
        ABBR,
        ASIDE,
        BLOCKQUOTE,
        CAPTION,
        CODE,
        DEL,
        DIV,
        DL,
        EMPHASIS,
        HEADING,
        HR,
        IMG,
        INS,
        LI,
        OL,
        P,
        PRE,
        STRONG,
        SUB,
        SUP,
        TABLE,
        TBODY,
        TD,
        TH,
        THEAD,
        TR,
        UL,
    }

    private static class TagParam {
        final TagType tagType;
        final Object param;

        TagParam(final TagType tagType, final Object param) {
            this.tagType = tagType;
            this.param = param;
        }

        static TagParam tag(TagType tagType, Object param) {
            return new TagParam(tagType, param);
        }
    }

    private final static Map<String, TagParam> ourTagProcessors = new HashMap<>();
    static {
        ourTagProcessors.put("a", TagParam.tag(TagType.A, null));
        ourTagProcessors.put("abbr", TagParam.tag(TagType.ABBR, null));
        ourTagProcessors.put("aside", TagParam.tag(TagType.ASIDE, null));
        ourTagProcessors.put("blockquote", TagParam.tag(TagType.BLOCKQUOTE, null));
        ourTagProcessors.put("caption", TagParam.tag(TagType.CAPTION, null));
        ourTagProcessors.put("code", TagParam.tag(TagType.CODE, null));
        ourTagProcessors.put("del", TagParam.tag(TagType.DEL, null));
        ourTagProcessors.put("div", TagParam.tag(TagType.DIV, null));
        ourTagProcessors.put("dl", TagParam.tag(TagType.DL, null));
        ourTagProcessors.put("emphasis", TagParam.tag(TagType.EMPHASIS, null));
        ourTagProcessors.put("h1", TagParam.tag(TagType.HEADING, 1));
        ourTagProcessors.put("h2", TagParam.tag(TagType.HEADING, 2));
        ourTagProcessors.put("h3", TagParam.tag(TagType.HEADING, 3));
        ourTagProcessors.put("h4", TagParam.tag(TagType.HEADING, 4));
        ourTagProcessors.put("h5", TagParam.tag(TagType.HEADING, 5));
        ourTagProcessors.put("h6", TagParam.tag(TagType.HEADING, 6));
        ourTagProcessors.put("hr", TagParam.tag(TagType.HR, null));
        ourTagProcessors.put("img", TagParam.tag(TagType.IMG, null));
        ourTagProcessors.put("ins", TagParam.tag(TagType.INS, null));
        ourTagProcessors.put("li", TagParam.tag(TagType.LI, null));
        ourTagProcessors.put("ol", TagParam.tag(TagType.OL, null));
        ourTagProcessors.put("p", TagParam.tag(TagType.P, null));
        ourTagProcessors.put("strong", TagParam.tag(TagType.STRONG, null));
        ourTagProcessors.put("sub", TagParam.tag(TagType.SUB, null));
        ourTagProcessors.put("sup", TagParam.tag(TagType.SUP, null));
        ourTagProcessors.put("table", TagParam.tag(TagType.TABLE, null));
        ourTagProcessors.put("tbody", TagParam.tag(TagType.TBODY, null));
        ourTagProcessors.put("td", TagParam.tag(TagType.TD, null));
        ourTagProcessors.put("th", TagParam.tag(TagType.TH, null));
        ourTagProcessors.put("thead", TagParam.tag(TagType.THEAD, null));
        ourTagProcessors.put("tr", TagParam.tag(TagType.TR, null));
        ourTagProcessors.put("ul", TagParam.tag(TagType.UL, null));
    }
}
