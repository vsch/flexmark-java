package com.vladsch.flexmark.convert.html;

import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.html.FormattingAppendableImpl;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;
import com.vladsch.flexmark.util.sequence.SubSequence;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

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
    private final Stack<State> myStateStack;
    private final Map<String, String> myAbbreviations;

    private State myState;

    private FlexmarkHtmlParser() {
        myIndentStack = new Stack<>();
        myStateStack = new Stack<>();
        myAbbreviations = new HashMap<>();
    }

    public static String parse(String html) {
        FormattingAppendableImpl out = new FormattingAppendableImpl(FormattingAppendable.SUPPRESS_TRAILING_WHITESPACE | FormattingAppendable.COLLAPSE_WHITESPACE);
        FlexmarkHtmlParser parser = new FlexmarkHtmlParser();
        parser.parse(out, html);
        return out.getText();
    }

    private static class State {
        Node myParent;
        List<Node> myElements;
        int myIndex;

        State(final Node parent) {
            myParent = parent;
            myElements = parent.childNodes();
            myIndex = 0;
        }

        @Override
        public String toString() {
            return "State{" +
                    "myParent=" + myParent +
                    ", myElements=" + myElements +
                    ", myIndex=" + myIndex +
                    '}';
        }
    }

    public void parse(FormattingAppendable out, String html) {
        Document document = Jsoup.parse(html);
        Element body = document.body();
        processHtmlTree(out, body);

        // output abbreviations if any
        out.blankLine();
        if (!myAbbreviations.isEmpty()) {
            for (Map.Entry<String, String> entry : myAbbreviations.entrySet()) {
                out.line().append("*[").append(entry.getKey()).append("]: ").append(entry.getValue()).line();
            }
        }
        out.blankLine();
    }

    private String dumpState() {
        if (!myStateStack.isEmpty()) {
            StringBuilder sb = new StringBuilder();

            while (!myStateStack.isEmpty()) {
                State state = myStateStack.pop();
                sb.append("\n").append(state.toString());
            }

            return sb.toString();
        }
        return "";
    }

    private void processHtmlTree(FormattingAppendable out, Node parent) {
        pushState(parent);

        State oldState = myState;
        Node node;

        while ((node = peek()) != null) {
            processElement(out, node);
        }

        if (oldState != myState) {
            throw new IllegalStateException("State not equal after process " + dumpState());
        }

        popState();
    }

    private void processElement(FormattingAppendable out, Node node) {
        TagParam tagParam = getTagParam(node);
        boolean processed = false;

        if (tagParam != null) {
            switch (tagParam.tagType) {
                // @formatter:off
                case A              : processed = processA(out, (Element) node); break;
                case ABBR           : processed = processAbbr(out, (Element) node); break;
                case ASIDE          : processed = processAside(out, (Element) node); break;
                case BLOCKQUOTE     : processed = processBlockquote(out, (Element) node); break;
                case CODE           : processed = processCode(out, (Element) node); break;
                case DEL            : processed = processDel(out, (Element) node); break;
                case DIV            : processed = processDiv(out, (Element) node); break;
                case DL             : processed = processDl(out, (Element) node); break;
                case _EMPHASIS       : processed = processEmphasis(out, (Element) node); break;
                case HR             : processed = processHr(out, (Element) node); break;
                case IMG            : processed = processImg(out, (Element) node); break;
                case INS            : processed = processIns(out, (Element) node); break;
                case OL             : processed = processOl(out, (Element) node); break;
                case P              : processed = processP(out, (Element) node); break;
                case PRE            : processed = processPre(out, (Element) node); break;
                case _STRONG_EMPHASIS         : processed = processStrong(out, (Element) node); break;
                case SUB            : processed = processSub(out, (Element) node); break;
                case SUP            : processed = processSup(out, (Element) node); break;
                case UL             : processed = processUl(out, (Element) node); break;
                case TABLE          : processed = processTable(out, (Element) node); break;
                case _UNWRAPPED      : processed = processUnwrapped(out, (Element) node); break;
                case _COMMENT        : processed = processComment(out, (Comment)node); break;
                case _HEADING        : processed = processHeading(out, (Element) node); break;
                case _TEXT           : processed = processText(out, (TextNode)node); break;
                // @formatter:on
            }
        }

        if (!processed) {
            // wrap markdown in HTML tag
            if (node instanceof Element && ((Element) node).isBlock()) {
                String s = node.outerHtml();
                int pos = s.indexOf(">");
                out.append(s.substring(0, pos));
                next();

                processHtmlTree(out, node);

                int endPos = s.lastIndexOf("<");
                out.append(s.substring(endPos));
            } else {
                out.append(node.toString());
                next();
            }
        }
    }

    private boolean processText(FormattingAppendable out, TextNode node) {
        skip();
        out.append(node.text());
        return true;
    }

    private void processTextNodes(FormattingAppendable out, Node node) {
        processTextNodes(out, node, null, null);
    }

    private void processTextNodes(FormattingAppendable out, Node node, CharSequence wrapText) {
        processTextNodes(out, node, wrapText, wrapText);
    }

    private void processTextNodes(FormattingAppendable out, Node node, CharSequence textPrefix, CharSequence textSuffix) {
        pushState(node);

        Node child;
        while ((child = peek()) != null) {
            if (child instanceof TextNode) {
                TextNode textNode = (TextNode) child;
                if (textPrefix != null && textPrefix.length() > 0) out.append(textPrefix);
                out.append(((TextNode) child).text());
                if (textSuffix != null && textSuffix.length() > 0) out.append(textSuffix);
                skip();
            } else if (child instanceof Element) {
                processElement(out, child);
            }
        }

        popState();
    }

    private boolean processA(FormattingAppendable out, Element element) {
        skip();

        // see if it is an anchor or a link
        if (element.hasAttr("href")) {
            out.append('[');
            processTextNodes(out, element);
            out.append(']');
            out.append('(').append(element.attr("href"));
            if (element.hasAttr("title")) out.append(" \"").append(element.attr("title").replace("\"", "\\\"")).append('"');
            out.append(")");
        } else {
            processTextNodes(out, element);
        }
        return true;
    }

    private boolean processAbbr(FormattingAppendable out, Element element) {
        skip();

        // see if it is an anchor or a link
        if (element.hasAttr("title")) {
            FormattingAppendable text = new FormattingAppendableImpl(out.getOptions());
            processTextNodes(text, element);
            myAbbreviations.put(text.getText(), element.attr("title"));
        }
        return true;
    }

    private boolean processAside(FormattingAppendable out, Element element) {
        skip();
        addPrefix(out, "| ");
        processHtmlTree(out, element);
        popPrefix(out);
        return true;
    }

    private boolean processBlockquote(FormattingAppendable out, Element element) {
        skip();
        addPrefix(out, "> ");
        processHtmlTree(out, element);
        popPrefix(out);
        return true;
    }

    private boolean processCode(FormattingAppendable out, Element element) {
        skip();
        BasedSequence text = SubSequence.of(element.ownText());
        int backTickCount = 1;
        int lastPos = 0;
        while (lastPos < text.length()) {
            int pos = text.indexOf('`', lastPos);
            if (pos < 0) break;
            int count = text.countChars('`', pos);
            if (backTickCount <= count) backTickCount = count + 1;
            lastPos = pos + count;
        }
        CharSequence backTicks = RepeatedCharSequence.of("`", backTickCount);
        processTextNodes(out, element, backTicks);
        return true;
    }

    private boolean processDel(FormattingAppendable out, Element element) {
        skip();
        processTextNodes(out, element, "~~");
        return true;
    }

    private boolean processEmphasis(FormattingAppendable out, Element element) {
        skip();
        processTextNodes(out, element, "*");
        return true;
    }

    private boolean processHeading(FormattingAppendable out, Element element) {
        skip();

        TagParam tagParam = getTagParam(element);
        if (tagParam != null) {
            int level = (int) tagParam.param;
            if (level >= 1 && level <= 6) {
                if (level <= 2) {
                    out.line();
                    int start = out.getOffsetBefore();
                    processTextNodes(out, element);
                    int end = out.getOffsetAfter();
                    out.line().repeat(level == 1 ? '=' : '-', Utils.minLimit(end - start, 3));
                    out.blankLine();
                } else {
                    out.line();
                    out.repeat('#', level).append(' ');
                    processTextNodes(out, element);
                    out.blankLine();
                }
            }
        }
        return true;
    }

    private boolean processHr(FormattingAppendable out, Element element) {
        skip();
        out.line().append("*** * ** * ***").blankLine();
        return true;
    }

    private boolean processImg(FormattingAppendable out, Element element) {
        skip();

        // see if it is an anchor or a link
        if (element.hasAttr("src")) {
            out.append("![");
            if (element.hasAttr("alt")) out.append(element.attr("alt"));
            out.append(']');
            out.append('(');
            String src = element.attr("src");
            int pos = src.indexOf('?');
            if (pos > 0) {
                out.append(src, 0, pos + 1);
                String decoded = Utils.urlDecode(src.substring(pos + 1).replace("+", "%2B"), "UTF8");
                out.line().append(decoded);
            } else {
                out.append(src);
            }
            if (element.hasAttr("title")) out.append(" \"").append(element.attr("title").replace("\"", "\\\"")).append('"');
            out.append(")");
        }
        return true;
    }

    private boolean processP(FormattingAppendable out, Element element) {
        skip();
        boolean isItemParagraph = false;

        Element firstElementSibling = element.firstElementSibling();
        if (firstElementSibling == null || element == firstElementSibling) {
            String tagName = element.parent().tagName();
            if (tagName.equalsIgnoreCase("li") || tagName.equalsIgnoreCase("dd")) {
                isItemParagraph = true;
            }
        }
        out.blankLineIf(!isItemParagraph);
        processTextNodes(out, element);
        out.blankLineIf(isItemParagraph).line();
        return true;
    }

    private boolean processIns(FormattingAppendable out, Element element) {
        skip();
        processTextNodes(out, element, "++");
        return true;
    }

    private boolean processStrong(FormattingAppendable out, Element element) {
        skip();
        processTextNodes(out, element, "**");
        return true;
    }

    private boolean processSub(FormattingAppendable out, Element element) {
        skip();
        processTextNodes(out, element, "~");
        return true;
    }

    private boolean processSup(FormattingAppendable out, Element element) {
        skip();
        processTextNodes(out, element, "^");
        return true;
    }

    private boolean processPre(FormattingAppendable out, Element element) {
        skip();
        Node next = peek(1);

        if (next != null && next.nodeName().equalsIgnoreCase("code")) {
            skip(2);
            Element code = (Element) next;

            String text = code.text();
            out.blankLine().append("```");
            String className = element.className();
            if (!className.isEmpty()) {
                out.append(className);
            }
            out.line();
            out.openPreFormatted(true);
            out.append(text);
            out.closePreFormatted();
            out.line().append("```").blankLine();
        } else {
            processHtmlTree(out, element);
        }

        return true;
    }

    private static class ListState {
        boolean isNumbered;
        int itemCount;

        ListState(final boolean isNumbered) {
            this.isNumbered = isNumbered;
            itemCount = 0;
        }

        String getItemPrefix() {
            if (isNumbered) {
                return String.format("%d. ", itemCount);
            } else {
                return "* ";
            }
        }
    }

    private void processListItem(FormattingAppendable out, Element item, ListState listState) {
        skip();
        pushState(item);

        listState.itemCount++;
        CharSequence itemPrefix = listState.getItemPrefix();
        CharSequence childPrefix = RepeatedCharSequence.of(" ", itemPrefix.length());

        out.line().append(itemPrefix);
        addPrefix(out, childPrefix);
        processHtmlTree(out, item);
        out.line();
        popPrefix(out);
        popState();
    }

    private boolean processList(FormattingAppendable out, Element element, boolean isNumbered) {
        skip();
        pushState(element);

        ListState listState = new ListState(isNumbered);

        Node item;
        while ((item = next()) != null) {
            TagParam tagParam = getTagParam(item);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    case LI:
                        processListItem(out, (Element) item, listState);
                        break;
                }
            } else {
                // nothing, skip it
            }
        }

        popState();
        return true;
    }

    private boolean processOl(FormattingAppendable out, Element element) {
        return processList(out, element, true);
    }

    private boolean processUl(FormattingAppendable out, Element element) {
        return processList(out, element, false);
    }

    private void processDefinition(FormattingAppendable out, Element item) {
        skip();
        pushState(item);
        int options = out.getOptions();
        out.line().setOptions(options & ~FormattingAppendable.COLLAPSE_WHITESPACE).append(":   ");
        addPrefix(out, "    ");
        out.setOptions(options);
        processHtmlTree(out, item);
        popPrefix(out);
        popState();
    }

    private boolean processDl(FormattingAppendable out, Element element) {
        skip();
        pushState(element);

        Node item;
        boolean lastWasDefinition = true;

        while ((item = next()) != null) {
            TagParam tagParam = getTagParam(item);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    case DT:
                        out.blankLineIf(lastWasDefinition).line();
                        processTextNodes(out, item);
                        out.line();
                        lastWasDefinition = false;
                        break;
                    case DD:
                        processDefinition(out, (Element) item);
                        lastWasDefinition = true;
                        break;
                }
            } else {
                // nothing, skip it
            }
        }

        popState();
        return true;
    }

    private boolean processDiv(FormattingAppendable out, Element element) {
        // unwrap and process content
        processHtmlTree(out, element);
        return true;
    }

    private boolean processUnwrapped(FormattingAppendable out, Element element) {
        // unwrap and process content
        processHtmlTree(out, element);
        return true;
    }

    private boolean processComment(FormattingAppendable out, Comment element) {
        out.append("<!--").append(element.getData()).append("-->");
        return true;
    }

    private TagParam getTagParam(final Node node) {
        return ourTagProcessors.get(node.nodeName().toLowerCase());
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
            this.text = text.isEmpty() ? " " : text;
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
            return headingColumns > bodyColumns ? headingColumns : bodyColumns;
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

    private String cellText(String text, int width, CellAlignment alignment) {
        text = text.trim();
        String s = Escaping.unescapeString("&nbsp;");
        text = text.replace("\\", "\\\\");
        text = text.replace(s.substring(0, 1), "&nbsp;");

        if (text.length() < width) {
            if (alignment == null || alignment == CellAlignment.NONE) alignment = CellAlignment.LEFT;
            int diff = width - text.length();

            switch (alignment) {
                case LEFT:
                    text = text + RepeatedCharSequence.of(" ", diff).toString();
                    break;
                case RIGHT:
                    text = RepeatedCharSequence.of(" ", diff).toString() + text;
                    break;
                case CENTER:
                    String leftPad = RepeatedCharSequence.of(" ", diff / 2).toString();
                    String rightPad = RepeatedCharSequence.of(" ", diff - leftPad.length()).toString();
                    text = leftPad + text + rightPad;
                    break;
            }
        }

        return text;
    }

    private int spanWidth(int[] maxWidth, int col, int colSpan, int colPad) {
        if (colSpan > 1) {
            int width = 0;
            for (int i = 0; i < colSpan; i++) {
                width += maxWidth[i + col] + colPad;
            }
            return width - colPad;
        } else {
            return maxWidth[col];
        }
    }

    private boolean processTable(FormattingAppendable out, Element table) {
        Table oldTable = myTable;

        skip();
        pushState(table);

        myTable = new Table();

        Node item;
        while ((item = next()) != null) {
            TagParam tagParam = getTagParam(item);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    // @formatter:off
                    case CAPTION : processTableCaption(out, (Element) item); break;
                    case TBODY : processTableSection(out, false, (Element) item); break;
                    case THEAD : processTableSection(out, true, (Element) item); break;
                    case TR : {
                        myTable.isHeading = false;
                        processTableRow(out, (Element) item);
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
            out.blankLine();

            // we will prepare the separator based on max columns
            CellAlignment[] alignments = new CellAlignment[sepColumns];
            int[] maxColumns = new int[sepColumns];
            int options = out.getOptions();
            out.setOptions(options & ~FormattingAppendable.COLLAPSE_WHITESPACE);

            if (myTable.heading.size() > 0) {
                int i = 0;
                for (TableRow row : myTable.heading) {
                    int j = 0;
                    int jSpan = 0;
                    for (TableCell cell : row.cells) {
                        if (i == 0 && cell.alignment != CellAlignment.NONE) {
                            alignments[jSpan] = cell.alignment;
                        }

                        String cellText = cellText(cell.text, 0, null);
                        if (maxColumns[jSpan] < cellText.length()) maxColumns[jSpan] = cellText.length();

                        j++;
                        jSpan += cell.columnSpan;
                    }
                    i++;
                }
            }

            if (myTable.body.size() > 0) {
                int i = 0;
                for (TableRow row : myTable.body) {
                    int j = 0;
                    int jSpan = 0;
                    for (TableCell cell : row.cells) {
                        String cellText = cellText(cell.text, 0, null);
                        if (maxColumns[jSpan] < cellText.length()) maxColumns[jSpan] = cellText.length();

                        j++;
                        jSpan += cell.columnSpan;
                    }
                    i++;
                }
            }

            if (myTable.heading.size() > 0) {
                for (TableRow row : myTable.heading) {
                    int j = 0;
                    int jSpan = 0;
                    for (TableCell cell : row.cells) {
                        if (j == 0) out.append("| ");
                        out.append(cellText(cell.text, spanWidth(maxColumns, jSpan, cell.columnSpan, 2), alignments[jSpan]));
                        out.append(' ').repeat('|', cell.columnSpan).append(' ');

                        j++;
                        jSpan += cell.columnSpan;
                    }

                    if (j > 0) out.line();
                }
            }

            {
                int j = 0;
                for (CellAlignment alignment : alignments) {
                    int colonCount = alignment == CellAlignment.LEFT || alignment == CellAlignment.RIGHT ? 1 : alignment == CellAlignment.CENTER ? 2 : 0;
                    int dashCount = maxColumns[j] + 2 - colonCount;

                    if (dashCount < 3 - colonCount) dashCount = 3 - dashCount - colonCount;

                    if (j == 0) out.append('|');
                    if (alignment == CellAlignment.LEFT || alignment == CellAlignment.CENTER) out.append(':');
                    out.repeat('-', dashCount);
                    if (alignment == CellAlignment.RIGHT || alignment == CellAlignment.CENTER) out.append(':');
                    out.append('|');
                    j++;
                }
                out.line();
            }

            if (myTable.body.size() > 0) {
                for (TableRow row : myTable.body) {
                    int j = 0;
                    int jSpan = 0;
                    for (TableCell cell : row.cells) {
                        if (j == 0) out.append("| ");
                        out.append(cellText(cell.text, spanWidth(maxColumns, jSpan, cell.columnSpan, 2), alignments[jSpan]));
                        out.append(' ').repeat('|', cell.columnSpan).append(' ');

                        j++;
                        jSpan += cell.columnSpan;
                    }

                    if (j > 0) out.line();
                }
            }

            out.setOptions(options);

            if (myTable.caption != null) {
                out.line().append('[').append(myTable.caption).append(']').line();
            }
        }

        myTable = oldTable;
        popState();
        return true;
    }

    private void processTableSection(FormattingAppendable out, boolean isHeading, Element element) {
        pushState(element);

        myTable.isHeading = isHeading;

        Node node;
        while ((node = next()) != null) {
            TagParam tagParam = getTagParam(node);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    // @formatter:off
                    case TR : processTableRow(out, (Element) node); break;
                    // @formatter:on
                }
            } else {
                // nothing, skip it
            }
        }

        popState();
    }

    private void processTableRow(FormattingAppendable out, Element element) {
        pushState(element);

        if (myTable.isHeading) {
            myTable.heading.add(new TableRow());
        } else {
            myTable.body.add(new TableRow());
        }

        Node node;
        while ((node = next()) != null) {
            TagParam tagParam = getTagParam(node);
            if (tagParam != null) {
                switch (tagParam.tagType) {
                    // @formatter:off
                    case TH : processTableCell(out, (Element) node); break;
                    case TD : processTableCell(out, (Element) node); break;
                    // @formatter:on
                }
            } else {
                // nothing, skip it
            }
        }

        popState();
    }

    private void processTableCaption(FormattingAppendable out, Element element) {
        FormattingAppendable cell = new FormattingAppendableImpl(out.getOptions());
        processTextNodes(cell, element);
        myTable.caption = Utils.removeEnd(cell.getText(), '\n');
    }

    private void processTableCell(FormattingAppendable out, Element element) {
        FormattingAppendable cell = new FormattingAppendableImpl(out.getOptions());
        processTextNodes(cell, element);
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

        String cellText = Utils.removeEnd(cell.getText(), '\n');
        tableRow.addCell(new TableCell(cellText, span, alignment));
    }

    private void pushState(Node parent) {
        myStateStack.push(myState);
        myState = new State(parent);
    }

    private void popState() {
        if (myStateStack.isEmpty()) throw new IllegalStateException("popState with an empty stack");
        myState = myStateStack.pop();
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

    private Node peek() {
        if (myState.myIndex < myState.myElements.size()) return myState.myElements.get(myState.myIndex);
        return null;
    }

    private Node peek(int skip) {
        if (myState.myIndex + skip < myState.myElements.size()) myState.myElements.get(myState.myIndex + skip);
        return null;
    }

    private Node next() {
        Node next = peek();
        if (next != null) myState.myIndex++;
        return next;
    }

    private void skip() {
        Node next = peek();
        if (next != null) myState.myIndex++;
    }

    private Node next(int skip) {
        if (skip > 0) {
            Node next = peek(skip - 1);
            if (next != null) myState.myIndex += skip;
            return next;
        }
        return peek();
    }

    @SuppressWarnings("SameParameterValue")
    private void skip(int skip) {
        if (skip > 0) {
            Node next = peek(skip - 1);
            if (next != null) myState.myIndex += skip;
        }
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
        DD,
        DT,
        _EMPHASIS,
        HR,
        IMG,
        INS,
        LI,
        OL,
        P,
        PRE,
        _STRONG_EMPHASIS,
        SUB,
        SUP,
        TABLE,
        TBODY,
        TD,
        TH,
        THEAD,
        TR,
        UL,
        _HEADING,
        _UNWRAPPED,
        _COMMENT,
        _TEXT,
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
        ourTagProcessors.put("dd", TagParam.tag(TagType.DD, null));
        ourTagProcessors.put("dl", TagParam.tag(TagType.DL, null));
        ourTagProcessors.put("dt", TagParam.tag(TagType.DT, null));
        ourTagProcessors.put("i", TagParam.tag(TagType._EMPHASIS, null));
        ourTagProcessors.put("em", TagParam.tag(TagType._EMPHASIS, null));
        ourTagProcessors.put("h1", TagParam.tag(TagType._HEADING, 1));
        ourTagProcessors.put("h2", TagParam.tag(TagType._HEADING, 2));
        ourTagProcessors.put("h3", TagParam.tag(TagType._HEADING, 3));
        ourTagProcessors.put("h4", TagParam.tag(TagType._HEADING, 4));
        ourTagProcessors.put("h5", TagParam.tag(TagType._HEADING, 5));
        ourTagProcessors.put("h6", TagParam.tag(TagType._HEADING, 6));
        ourTagProcessors.put("hr", TagParam.tag(TagType.HR, null));
        ourTagProcessors.put("img", TagParam.tag(TagType.IMG, null));
        ourTagProcessors.put("ins", TagParam.tag(TagType.INS, null));
        ourTagProcessors.put("li", TagParam.tag(TagType.LI, null));
        ourTagProcessors.put("ol", TagParam.tag(TagType.OL, null));
        ourTagProcessors.put("p", TagParam.tag(TagType.P, null));
        ourTagProcessors.put("b", TagParam.tag(TagType._STRONG_EMPHASIS, null));
        ourTagProcessors.put("strong", TagParam.tag(TagType._STRONG_EMPHASIS, null));
        ourTagProcessors.put("sub", TagParam.tag(TagType.SUB, null));
        ourTagProcessors.put("sup", TagParam.tag(TagType.SUP, null));
        ourTagProcessors.put("table", TagParam.tag(TagType.TABLE, null));
        ourTagProcessors.put("tbody", TagParam.tag(TagType.TBODY, null));
        ourTagProcessors.put("td", TagParam.tag(TagType.TD, null));
        ourTagProcessors.put("th", TagParam.tag(TagType.TH, null));
        ourTagProcessors.put("thead", TagParam.tag(TagType.THEAD, null));
        ourTagProcessors.put("tr", TagParam.tag(TagType.TR, null));
        ourTagProcessors.put("ul", TagParam.tag(TagType.UL, null));
        ourTagProcessors.put("#comment", TagParam.tag(TagType._COMMENT, null));
        ourTagProcessors.put("#text", TagParam.tag(TagType._TEXT, null));

        // tags ignored, contents processed
        ourTagProcessors.put("article", TagParam.tag(TagType._UNWRAPPED, null));
        ourTagProcessors.put("address", TagParam.tag(TagType._UNWRAPPED, null));
        ourTagProcessors.put("article", TagParam.tag(TagType._UNWRAPPED, null));
        ourTagProcessors.put("frameset", TagParam.tag(TagType._UNWRAPPED, null));
        ourTagProcessors.put("section", TagParam.tag(TagType._UNWRAPPED, null));
        ourTagProcessors.put("span", TagParam.tag(TagType._UNWRAPPED, null));
    }
}
