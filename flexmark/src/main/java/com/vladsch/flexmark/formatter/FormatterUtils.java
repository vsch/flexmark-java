package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.ListItem;
import com.vladsch.flexmark.ast.OrderedList;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.SoftLineBreak;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.BlockQuoteLike;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.TrackedOffsetList;
import com.vladsch.flexmark.util.format.options.BlockQuoteContinuationMarker;
import com.vladsch.flexmark.util.format.options.BlockQuoteMarker;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.mappers.SpaceMapper;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharPredicate;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.util.html.LineAppendable.F_TRIM_LEADING_WHITESPACE;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

public class FormatterUtils {

    public static final DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<>("LIST_ITEM_NUMBER", 0);
    public static final DataKey<Boolean> FIRST_LIST_ITEM_CHILD = new DataKey<>("FIRST_LIST_ITEM_CHILD", false); // Set to true for first block list item child of an empty list item
    public static final Function<CharSequence, Pair<Integer, Integer>> NULL_PADDING = sequence -> Pair.of(0, 0);
    public static final DataKey<Function<CharSequence, Pair<Integer, Integer>>> LIST_ALIGN_NUMERIC = new DataKey<>("LIST_ITEM_NUMBER", NULL_PADDING); // function takes ordered marker and returns Pair LeftPad,RightPad
    public static final NullableDataKey<ListSpacing> LIST_ITEM_SPACING = new NullableDataKey<>("LIST_ITEM_SPACING");

    public static Pair<String, String> getBlockLikePrefix(BlockQuoteLike node, NodeFormatterContext context, MarkdownWriter markdown, BlockQuoteMarker blockQuoteMarkers, BlockQuoteContinuationMarker blockQuoteContinuationMarkers) {
        String prefixChars = node.getOpeningMarker().toString();
        String prefix;
        boolean compactPrefix = false;
        boolean compactContinuationPrefix = false;

        switch (blockQuoteMarkers) {
            case AS_IS:
                if (node.getFirstChild() != null) {
                    prefix = node.getChars().baseSubSequence(node.getOpeningMarker().getStartOffset(), node.getFirstChild().getStartOffset()).toString();
                } else {
                    prefix = prefixChars;
                }
                break;

            case ADD_COMPACT:
                prefix = prefixChars.trim();
                break;

            case ADD_COMPACT_WITH_SPACE:
                compactPrefix = true;
                prefix = prefixChars.trim() + " ";
                break;

            case ADD_SPACED:
                prefix = prefixChars.trim() + " ";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + blockQuoteMarkers);
        }

        String continuationPrefix;
        compactContinuationPrefix = compactPrefix;

        switch (blockQuoteContinuationMarkers) {
            case ADD_AS_FIRST:
                continuationPrefix = prefix;
                compactContinuationPrefix = compactPrefix;
                break;

            case ADD_COMPACT:
                continuationPrefix = prefixChars.trim();
                break;

            case ADD_COMPACT_WITH_SPACE:
                continuationPrefix = prefixChars.trim() + " ";
                compactContinuationPrefix = true;
                break;
            case ADD_SPACED:
                continuationPrefix = prefixChars.trim() + " ";
                break;

            case REMOVE:
                continuationPrefix = "";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + blockQuoteContinuationMarkers);
        }

        // create combined prefix, compact if needed
        CharPredicate quoteLikePrefixPredicate = context.getBlockQuoteLikePrefixPredicate();

        String combinedPrefix = FormatterUtils.FIRST_LIST_ITEM_CHILD.get(node.getDocument()) ? "" : markdown.getPrefix().toString();
        if (compactPrefix && combinedPrefix.endsWith(" ") && combinedPrefix.length() >= 2 && quoteLikePrefixPredicate.test(combinedPrefix.charAt(combinedPrefix.length() - 2))) {
            combinedPrefix = combinedPrefix.substring(0, combinedPrefix.length() - 1) + prefix;
        } else {
            combinedPrefix += prefix;
        }

        String combinedContinuationPrefix = markdown.getBeforeEolPrefix().toString();
        if (compactContinuationPrefix && combinedContinuationPrefix.endsWith(" ") && combinedContinuationPrefix.length() >= 2 && quoteLikePrefixPredicate.test(combinedContinuationPrefix.charAt(combinedContinuationPrefix.length() - 2))) {
            combinedContinuationPrefix = combinedContinuationPrefix.substring(0, combinedContinuationPrefix.length() - 1) + continuationPrefix;
        } else {
            combinedContinuationPrefix += continuationPrefix;
        }

        return Pair.of(combinedPrefix, combinedContinuationPrefix);
    }

    @SuppressWarnings("SameParameterValue")
    public static CharSequence stripSoftLineBreak(CharSequence chars, CharSequence spaceChar) {
        StringBuffer sb = null;
        Matcher matcher = Pattern.compile("\\s*(?:\r\n|\r|\n)\\s*").matcher(chars);
        while (matcher.find()) {
            if (sb == null) sb = new StringBuffer();
            matcher.appendReplacement(sb, spaceChar.toString());
        }
        if (sb != null) {
            matcher.appendTail(sb);
            return sb;
        }
        return chars;
    }

    @NotNull
    public static String getActualAdditionalPrefix(BasedSequence contentChars, MarkdownWriter markdown) {
        String prefix;
        int parentPrefix = markdown.getPrefix().length();
        int column = contentChars.baseColumnAtStart();

        prefix = RepeatedSequence.repeatOf(" ", Utils.minLimit(0, column - parentPrefix)).toString();
        return prefix;
    }

    @NotNull
    public static String getAdditionalPrefix(BasedSequence fromChars, BasedSequence toChars) {
        String prefix;
        int parentPrefix = fromChars.getStartOffset();
        int column = toChars.getStartOffset();

        prefix = RepeatedSequence.repeatOf(" ", Utils.minLimit(0, column - parentPrefix)).toString();
        return prefix;
    }

    public static BasedSequence getSoftLineBreakSpan(Node node) {
        if (node == null) return NULL;

        Node lastNode = node;
        Node nextNode = node.getNext();

        while (nextNode != null && !(nextNode instanceof SoftLineBreak)) {
            lastNode = nextNode;
            nextNode = nextNode.getNext();
        }

        return Node.spanningChars(node.getChars(), lastNode.getChars());
    }

    public static void appendWhiteSpaceBetween(
            MarkdownWriter markdown,
            Node prev,
            Node next,
            boolean preserve,
            boolean collapse,
            boolean collapseToEOL
    ) {
        if (next != null && prev != null && (preserve || collapse)) {
            appendWhiteSpaceBetween(markdown, prev.getChars(), next.getChars(), preserve, collapse, collapseToEOL);
        }
    }

    public static void appendWhiteSpaceBetween(
            MarkdownWriter markdown,
            BasedSequence prev,
            BasedSequence next,
            boolean preserve,
            boolean collapse,
            boolean collapseToEOL
    ) {
        if (next != null && prev != null && (preserve || collapse)) {
            if (prev.getEndOffset() <= next.getStartOffset()) {
                BasedSequence sequence = prev.baseSubSequence(prev.getEndOffset(), next.getStartOffset());
                if (!sequence.isEmpty() && sequence.isBlank()) {
                    if (!preserve) {
                        if (collapseToEOL && sequence.indexOfAny(BasedSequence.ANY_EOL_SET) != -1) {
                            markdown.append('\n');
                        } else {
                            markdown.append(' ');
                        }
                    } else {
                        // need to set pre-formatted or spaces after eol are ignored assuming prefixes are used
                        int saved = markdown.getOptions();
                        markdown.setOptions(saved & ~F_TRIM_LEADING_WHITESPACE);
                        markdown.append(sequence);
                        markdown.setOptions(saved);
                    }
                }
            } else {
                // nodes reversed due to children being rendered before the parent
            }
        }
    }

    public static void renderList(ListBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            // CAUTION: during translation no formatting should be done
            context.renderChildren(node);
        } else {
            ArrayList<Node> itemList = new ArrayList<>();
            Node item = node.getFirstChild();
            while (item != null) {
                itemList.add(item);
                item = item.getNext();
            }
            renderList(node, context, markdown, itemList);
        }
    }

    public static void renderList(ListBlock node, NodeFormatterContext context, MarkdownWriter markdown, List<Node> itemList) {
        FormatterOptions formatterOptions = context.getFormatterOptions();
        if (formatterOptions.listAddBlankLineBefore && !node.isOrDescendantOfType(ListItem.class)) {
            markdown.blankLine();
        }

        Document document = context.getDocument();
        ListSpacing listSpacing = LIST_ITEM_SPACING.get(document);
        int listItemNumber = LIST_ITEM_NUMBER.get(document);
        int startingNumber = node instanceof OrderedList ? formatterOptions.listRenumberItems && formatterOptions.listResetFirstItemNumber ? 1 : ((OrderedList) node).getStartNumber() : 1;
        Function<CharSequence, Pair<Integer, Integer>> listAlignNumeric = LIST_ALIGN_NUMERIC.get(document);
        document.set(LIST_ITEM_NUMBER, startingNumber);

        ListSpacing itemSpacing = null;
        switch (formatterOptions.listSpacing) {
            case AS_IS:
                break;
            case LOOSE:
                itemSpacing = ListSpacing.LOOSE;
                break;
            case TIGHT:
                itemSpacing = ListSpacing.TIGHT;
                break;
            case LOOSEN:
                itemSpacing = node.isLoose() ? ListSpacing.LOOSE : ListSpacing.TIGHT;
                break;
            case TIGHTEN: {
                itemSpacing = ListSpacing.LOOSE;
                for (Node item : itemList) {
                    if (item instanceof ListItem) {
                        if (((ListItem) item).isOwnTight() && item.getNext() != null) {
                            itemSpacing = ListSpacing.TIGHT;
                            break;
                        }
                    }
                }
                break;
            }
        }

        document.remove(LIST_ALIGN_NUMERIC);

        if (!formatterOptions.listAlignNumeric.isNoChange() && node instanceof OrderedList) {
            int maxLen = Integer.MIN_VALUE;
            int minLen = Integer.MAX_VALUE;
            int i = startingNumber;
            for (Node item : itemList) {
                if (!formatterOptions.listRemoveEmptyItems || item.hasChildren() && item.getFirstChildAnyNot(BlankLine.class) != null) {
                    int length = formatterOptions.listRenumberItems ? Integer.toString(i).length() + 1 : ((ListItem) item).getOpeningMarker().length();
                    maxLen = Math.max(maxLen, length);
                    minLen = Math.min(minLen, length);
                    i++;
                }
            }

            if (maxLen != minLen) {
                int finalMaxLen = maxLen;
                document.set(LIST_ALIGN_NUMERIC, formatterOptions.listAlignNumeric.isLeft()
                        ? sequence -> Pair.of(0, Math.min(4, Math.max(0, finalMaxLen - sequence.length())))
                        : sequence -> Pair.of(Math.min(4, Math.max(0, finalMaxLen - sequence.length())), 0)
                );
            }
        }

        document.set(LIST_ITEM_SPACING, itemSpacing == ListSpacing.LOOSE && (listSpacing == null || listSpacing == ListSpacing.LOOSE) ? ListSpacing.LOOSE : itemSpacing);
        for (Node item : itemList) {
            if (itemSpacing == ListSpacing.LOOSE && (listSpacing == null || listSpacing == ListSpacing.LOOSE)) markdown.blankLine();
            context.render(item);
        }
        document.set(LIST_ITEM_SPACING, listSpacing);
        document.set(LIST_ITEM_NUMBER, listItemNumber);
        document.set(LIST_ALIGN_NUMERIC, listAlignNumeric);

        if (!node.isOrDescendantOfType(ListItem.class)) {
            markdown.tailBlankLine();
        }
    }

    public static void renderListItem(
            ListItem node,
            NodeFormatterContext context,
            MarkdownWriter markdown,
            ListOptions listOptions,
            BasedSequence markerSuffix,
            boolean addBlankLineLooseItems
    ) {
        FormatterOptions options = context.getFormatterOptions();
        boolean savedFirstListItemChild = FIRST_LIST_ITEM_CHILD.get(node.getDocument());

        if (context.isTransformingText()) {
            BasedSequence openingMarker = node.getOpeningMarker();
            String itemContentPrefix;
            String itemContentSpacer;
            String prefix;
            String additionalPrefix = getActualAdditionalPrefix(openingMarker, markdown);

            if (node.getFirstChild() == null) {
                // TEST: not sure if this works, need an empty list item with no children to test
                int count = openingMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1;
                itemContentPrefix = RepeatedSequence.repeatOf(' ', count).toString();
                prefix = additionalPrefix + itemContentPrefix;

                itemContentSpacer = " ";
            } else {
                BasedSequence childContent = node.getFirstChild().getChars();
                itemContentPrefix = getAdditionalPrefix(markerSuffix.isEmpty() ? openingMarker : markerSuffix, childContent);
                prefix = additionalPrefix + itemContentPrefix;

                itemContentSpacer = getAdditionalPrefix(markerSuffix.isEmpty() ? openingMarker.getEmptySuffix() : markerSuffix.getEmptySuffix(), childContent);
            }

            markdown.pushPrefix().addPrefix(prefix, true);
            markdown.append(additionalPrefix).append(openingMarker);

            if (!markerSuffix.isEmpty()) {
                String markerSuffixIndent = getAdditionalPrefix(openingMarker.getEmptySuffix(), markerSuffix);
                markdown.append(markerSuffixIndent).append(markerSuffix);
            }

            markdown.append(itemContentSpacer);

            // if have no item text and followed by eol then add EOL
            if (!(node.getFirstChild() instanceof Paragraph)) {
                if (node.getFirstChild() == null) {
                    if (!savedFirstListItemChild) {
                        markdown.append("\n");
                    }
                } else {
                    int posEOL = node.endOfLine(openingMarker.getEndOffset());
                    if (posEOL < node.getFirstChild().getStartOffset()) {
                        // output EOL
                        markdown.append("\n");
                    }
                }
            }

            context.renderChildren(node);
            markdown.popPrefix();
        } else {
            if (options.listRemoveEmptyItems && !(node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null)) {
                return;
            }

            CharSequence useOpeningMarker = node.getOpeningMarker();
            if (node.isOrderedItem()) {
                char delimiter = useOpeningMarker.charAt(useOpeningMarker.length() - 1);
                CharSequence number = useOpeningMarker.subSequence(0, useOpeningMarker.length() - 1);

                switch (options.listNumberedMarker) {
                    case ANY:
                        break;
                    case DOT:
                        delimiter = '.';
                        break;
                    case PAREN:
                        delimiter = ')';
                        break;
                    default:
                        throw new IllegalStateException("Missing case for ListNumberedMarker " + options.listNumberedMarker.name());
                }

                Document document = context.getDocument();

                if (options.listRenumberItems) {
                    Integer itemNumber = LIST_ITEM_NUMBER.get(document);
                    useOpeningMarker = String.format(Locale.US, "%d%c", itemNumber++, delimiter);
                    document.set(LIST_ITEM_NUMBER, itemNumber);
                } else {
                    useOpeningMarker = String.format("%s%c", number, delimiter);
                }

                Pair<Integer, Integer> padding = LIST_ALIGN_NUMERIC.get(document).apply(useOpeningMarker);
                if (padding.getFirst() > 0) useOpeningMarker = RepeatedSequence.ofSpaces(padding.getFirst()).toString() + useOpeningMarker.toString();
                if (padding.getSecond() > 0) useOpeningMarker = useOpeningMarker.toString() + RepeatedSequence.ofSpaces(padding.getSecond()).toString();
            } else {
                if (node.canChangeMarker()) {
                    switch (options.listBulletMarker) {
                        case ANY:
                            break;
                        case DASH:
                            useOpeningMarker = "-";
                            break;
                        case ASTERISK:
                            useOpeningMarker = "*";
                            break;
                        case PLUS:
                            useOpeningMarker = "+";
                            break;
                        default:
                            throw new IllegalStateException("Missing case for ListBulletMarker " + options.listBulletMarker.name());
                    }
                }
            }

            // NOTE: if list item content after suffix is set in the parser, then sub-items are indented after suffix
            //    otherwise only the item's lazy continuation for the paragraph can be indented after suffix, child items are normally indented
            int itemContinuationCount = (listOptions.isItemContentAfterSuffix() || options.listsItemContentAfterSuffix ? markerSuffix.length() : 0);
            int continuationCount = useOpeningMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1;
            CharSequence itemPrefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', itemContinuationCount)
                    : "";

            CharSequence childPrefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', continuationCount)
                    : RepeatedSequence.repeatOf(" ", listOptions.getItemIndent()).toString();

            markdown.pushPrefix().addPrefix(childPrefix, true);

            BasedSequence openingMarker = node.getOpeningMarker();
//            BasedSequence openingMarkerSpace = openingMarker.baseSubSequence(openingMarker.getEndOffset(), openingMarker.getEndOffset() + 1);
//            BasedSequence replacedOpenMarker = openingMarker.getBuilder().append(openingMarker.getEmptyPrefix()).append(useOpeningMarker).append(' ').append(openingMarker.getEmptySuffix()).toSequence();
            BasedSequence replacedOpenMarker = openingMarker.getBuilder().append(openingMarker.getEmptyPrefix()).append(useOpeningMarker).append(openingMarker.getEmptySuffix()).toSequence();

            markdown.pushOptions()
                    .preserveSpaces()
                    .append(replacedOpenMarker)
                    .append(' ')
                    .append(markerSuffix)
                    .popOptions();

            if (node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null) {
                // NOTE: depends on first child
                Node childNode = node.getFirstChild();

                if (childNode instanceof Paragraph) {
                    markdown.pushPrefix().addPrefix(itemPrefix, true);
                    context.render(childNode);
                    markdown.popPrefix();
                } else if (childNode != null) {
                    // NOTE: item is empty, followed immediately by child block element
                    FIRST_LIST_ITEM_CHILD.set(node.getDocument(), true);
                    markdown.pushPrefix().addPrefix(itemPrefix, true);
                    context.render(childNode);
                    markdown.popPrefix();
                }

                FIRST_LIST_ITEM_CHILD.set(node.getDocument(), false);

                while (childNode != null) {
                    childNode = childNode.getNext();
                    if (childNode == null) {
                        break;
                    }
                    context.render(childNode);
                }

                if (addBlankLineLooseItems && (node.isLoose() || LIST_ITEM_SPACING.get(node.getDocument()) == ListSpacing.LOOSE)) {
                    markdown.tailBlankLine();
                }
            } else {
                // NOTE: empty list item
                if (node.isLoose()) {
                    markdown.tailBlankLine();
                } else {
                    if (!savedFirstListItemChild) {
                        markdown.line();
                    }
                }
            }
            markdown.popPrefix();
        }

        FIRST_LIST_ITEM_CHILD.set(node.getDocument(), savedFirstListItemChild);
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderTextBlockParagraphLines(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            context.translatingSpan((context1, writer) -> context1.renderChildren(node));
            markdown.line();
        } else {
            FormatterOptions formatterOptions = context.getFormatterOptions();
            if (formatterOptions.rightMargin > 0) {
                MutableDataHolder subContextOptions = context.getOptions().toMutable().set(Formatter.KEEP_SOFT_LINE_BREAKS, true).set(Formatter.KEEP_HARD_LINE_BREAKS, true);
                SequenceBuilder builder = node.getChars().getBuilder();
                NodeFormatterContext subContext = context.getSubContext(subContextOptions, builder.getBuilder());
                subContext.renderChildren(node);

                MarkdownWriter subContextMarkdown = subContext.getMarkdown();
                subContextMarkdown.toBuilder(builder, 0);

                MarkdownParagraph formatter = new MarkdownParagraph(builder.toSequence(), formatterOptions.charWidthProvider);
                TrackedOffsetList trackedOffsets = context.getTrackedOffsets();

                BasedSequence nodeLessEol = node.getChars().trimEOL();
                TrackedOffsetList paragraphTrackedOffsets = trackedOffsets.getTrackedOffsets(nodeLessEol.getStartOffset(), nodeLessEol.getEndOffset());

                formatter.setWidth(formatterOptions.rightMargin - markdown.getPrefix().length());
                formatter.setKeepSoftBreaks(false);
                formatter.setKeepHardBreaks(formatterOptions.keepHardLineBreaks);
                formatter.setRestoreTrackedSpaces(false);
                formatter.setFirstIndent("");
                formatter.setIndent("");

                // adjust first line width, based on change in prefix after the first line EOL
                formatter.setFirstWidthOffset(-markdown.column() + markdown.getAfterEolPrefixDelta());

                if (formatterOptions.applySpecialLeadInHandlers) {
                    formatter.setLeadInHandlers(Parser.SPECIAL_LEAD_IN_HANDLERS.get(context.getDocument()));
                }

                for (TrackedOffset trackedOffset : paragraphTrackedOffsets) {
                    assert (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset());
                    formatter.addTrackedOffset(trackedOffset);
                }

                BasedSequence wrappedText = formatter.wrapText().toMapped(SpaceMapper.fromNonBreakSpace);
                int startLine = markdown.getLineCount();
                int firstLineOffset = markdown.column();
                markdown.append(wrappedText).line();

                if (!paragraphTrackedOffsets.isEmpty()) {
                    final int[] trackedOffsetCount = { paragraphTrackedOffsets.size() };
                    final int[] length = { 0 };
                    final int[] startLineSumLength = { 0 };
                    final int[] startLineSumPrefix = { 0 };
                    final int[] startLineSumText = { 0 };
                    markdown.forAllLines(0, (line, index, textStart, textEnd, sumPrefix, sumText, sumLength) -> {
                        System.out.println(String.format("Line[%d] textStart: %d, textEnd: %d, sumPrefix: %d, sumText: %d, sumLength: %d in '%s'", index+1, textStart, textEnd, sumPrefix, sumText, sumLength, line.getBuilder().append(line).toStringWithRanges(true)));
                        assert length[0] == sumLength;
                        if (index >= startLine) {
                            if (index == startLine) {
                                startLineSumLength[0] = sumLength;
                                startLineSumPrefix[0] = sumPrefix;
                                startLineSumText[0] = sumText;
                            }

                            BasedSequence lineText = line.subSequence(0, textEnd);
                            int startIndex = sumText - startLineSumText[0];
                            int endIndex = startIndex + textEnd - textStart;
                            for (TrackedOffset trackedOffset : paragraphTrackedOffsets) {
                                int offsetIndex = trackedOffset.getIndex();
                                if (trackedOffset.isResolved() && offsetIndex >= startIndex && offsetIndex <= endIndex) {
                                    int delta = sumLength + textStart + sumPrefix;
                                    trackedOffset.setIndex(offsetIndex + delta);
                                    System.out.println(String.format("Wrap Resolved %d to %d, delta: %d in line[%d]: '%s'", trackedOffset.getOffset(), offsetIndex, delta, index + 1, lineText.getBuilder().append(lineText).toStringWithRanges(true)));
                                } else {
                                    System.out.println(String.format("Wrap Unresolved %d in line[%d]: '%s'", trackedOffset.getOffset(), index + 1, lineText.getBuilder().append(lineText).toStringWithRanges(true)));
                                }

                                trackedOffsetCount[0]--;
                            }
                        }
                        length[0] += line.length();
                        return trackedOffsetCount[0] > 0;
                    });
                }
            } else {
                context.renderChildren(node);
                markdown.line();
            }
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseParagraph(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseItemParagraph(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }
}
