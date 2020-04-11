package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.TrackedOffsetList;
import com.vladsch.flexmark.util.format.options.BlockQuoteMarker;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.LineInfo;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.util.format.options.ListSpacing.*;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;
import static com.vladsch.flexmark.util.sequence.LineAppendable.F_TRIM_LEADING_WHITESPACE;
import static com.vladsch.flexmark.util.sequence.LineAppendable.F_TRIM_TRAILING_WHITESPACE;

public class FormatterUtils {

    final public static DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<>("LIST_ITEM_NUMBER", 0);
    final public static DataKey<Boolean> FIRST_LIST_ITEM_CHILD = new DataKey<>("FIRST_LIST_ITEM_CHILD", false); // Set to true for first block list item child of an empty list item
    final public static Function<CharSequence, Pair<Integer, Integer>> NULL_PADDING = sequence -> Pair.of(0, 0);
    final public static DataKey<Function<CharSequence, Pair<Integer, Integer>>> LIST_ALIGN_NUMERIC = new DataKey<>("LIST_ITEM_NUMBER", NULL_PADDING); // function takes ordered marker and returns Pair LeftPad,RightPad
    final public static NullableDataKey<ListSpacing> LIST_ITEM_SPACING = new NullableDataKey<>("LIST_ITEM_SPACING");

    public static String getBlockLikePrefix(BlockQuoteLike node, NodeFormatterContext context, BlockQuoteMarker blockQuoteMarkers, @NotNull BasedSequence prefix) {
        String prefixChars = node.getOpeningMarker().toString();
        String usePrefix;
        boolean compactPrefix = false;

        switch (blockQuoteMarkers) {
            case AS_IS:
                if (node.getFirstChild() != null) {
                    usePrefix = node.getChars().baseSubSequence(node.getOpeningMarker().getStartOffset(), node.getFirstChild().getStartOffset()).toString();
                } else {
                    usePrefix = prefixChars;
                }
                break;

            case ADD_COMPACT:
                usePrefix = prefixChars.trim();
                break;

            case ADD_COMPACT_WITH_SPACE:
                compactPrefix = true;
                usePrefix = prefixChars.trim() + " ";
                break;

            case ADD_SPACED:
                usePrefix = prefixChars.trim() + " ";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + blockQuoteMarkers);
        }

        // create a combined prefix, compact if needed
        CharPredicate quoteLikePrefixPredicate = context.getBlockQuoteLikePrefixPredicate();

        String combinedPrefix = prefix.toString();
        if (compactPrefix && combinedPrefix.endsWith(" ") && combinedPrefix.length() >= 2 && quoteLikePrefixPredicate.test(combinedPrefix.charAt(combinedPrefix.length() - 2))) {
            combinedPrefix = combinedPrefix.substring(0, combinedPrefix.length() - 1) + usePrefix;
        } else {
            combinedPrefix += usePrefix;
        }

        return combinedPrefix;
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
                        if (collapseToEOL && sequence.indexOfAny(CharPredicate.ANY_EOL) != -1) {
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
                itemSpacing = LOOSE;
                break;
            case TIGHT:
                itemSpacing = ListSpacing.TIGHT;
                break;
            case LOOSEN:
                itemSpacing = hasLooseItems(itemList) ? LOOSE : ListSpacing.TIGHT;
                break;
            case TIGHTEN:
                itemSpacing = hasLooseItems(itemList) ? AS_IS : ListSpacing.TIGHT;
                break;
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

        document.set(LIST_ITEM_SPACING, itemSpacing == LOOSE && (listSpacing == null || listSpacing == LOOSE) ? LOOSE : itemSpacing);
        for (Node item : itemList) {
            if (itemSpacing == LOOSE && (listSpacing == null || listSpacing == LOOSE)) {
                markdown.blankLine();
            }
            context.render(item);
        }
        document.set(LIST_ITEM_SPACING, listSpacing);
        document.set(LIST_ITEM_NUMBER, listItemNumber);
        document.set(LIST_ALIGN_NUMERIC, listAlignNumeric);

        if (!node.isOrDescendantOfType(ListItem.class)) {
            markdown.tailBlankLine();
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseParagraph(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        renderLooseItemParagraph(node, context, markdown);
    }

    public static boolean isFollowedByBlankLine(@Nullable Node node) {
        while (node != null) {
            if (node.getNextAnyNot(HtmlCommentBlock.class, HtmlInnerBlockComment.class, HtmlInlineComment.class) instanceof BlankLine) return true;
            if (node.getNextAnyNot(BlankLine.class, HtmlCommentBlock.class, HtmlInnerBlockComment.class, HtmlInlineComment.class) != null) return false;
            node = node.getParent();
        }
        return false;
    }

    public static boolean isNotLastItem(@Nullable Node node) {
        while (node != null && !(node instanceof Document)) {
            if (node.getNextAnyNot(BlankLine.class, HtmlCommentBlock.class, HtmlInnerBlockComment.class, HtmlInlineComment.class) != null) return true;
            node = node.getParent();
        }
        return false;
    }

    public static boolean isLastOfItem(@Nullable Node node) {
        return node != null && node.getNextAnyNot(BlankLine.class, HtmlCommentBlock.class, HtmlInnerBlockComment.class, HtmlInlineComment.class) == null;
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseItemParagraph(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderTextBlockParagraphLines(node, context, markdown);
        Block parent = node.getParent();
        if (parent instanceof ListItem) {
            if (context.getFormatterOptions().blankLinesInAst) {
                boolean addBlankLine;
                boolean canAddTailBlankLine = !((ParagraphContainer) parent).isParagraphEndWrappingDisabled(node);

                ListItem listItem = (ListItem) parent;
                switch (context.getFormatterOptions().listSpacing) {
                    case LOOSEN:
                        addBlankLine = parent.getParent() instanceof ListBlock && ((ListBlock) parent.getParent()).isLoose() && hasLooseItems(parent.getParent().getChildren())
                                && (isFollowedByBlankLine(node) && isNotLastItem(parent)
                                || !listItem.isOwnTight()
                                || listItem.isItemParagraph(node) && parent.getFirstChild() != null && parent.getFirstChild().getNext() != null)
                        ;
                        break;

                    case LOOSE:
                        addBlankLine = true;
                        break;

                    case TIGHTEN:
                        addBlankLine = canAddTailBlankLine && (listItem.isItemParagraph(node) ? isFollowedByBlankLine(node) && isNotLastItem(node) : isNotLastItem(node));
                        break;

                    case AS_IS:
                        addBlankLine = isFollowedByBlankLine(node) && isNotLastItem(parent);
                        break;

                    case TIGHT:
                    default:
                        addBlankLine = false;
                        break;
                }

                if (addBlankLine) {
                    markdown.tailBlankLine();
                }
            } else {
                if (context.getFormatterOptions().listSpacing != TIGHTEN || parent.getNext() != null) {
                    markdown.tailBlankLine();
                }
            }
        } else {
            markdown.tailBlankLine();
        }
    }

    static boolean hasLooseItems(Iterable<Node> itemList) {
        for (Node item : itemList) {
            if (item instanceof ListItem) {
                if (!((ListItem) item).isOwnTight() && item.getNext() != null) {
                    return true;
                }
            }
        }
        return false;
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
        boolean savedFirstListItemChild = FIRST_LIST_ITEM_CHILD.get(context.getDocument());

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
            CharSequence additionalItemPrefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', itemContinuationCount)
                    : "";

            CharSequence childPrefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', continuationCount)
                    : RepeatedSequence.repeatOf(" ", listOptions.getItemIndent()).toString();

            BasedSequence openingMarker = node.getOpeningMarker();
            BasedSequence replacedOpenMarker = openingMarker.getBuilder().append(openingMarker.getEmptyPrefix()).append(useOpeningMarker).append(openingMarker.getEmptySuffix()).toSequence();

            markdown.pushOptions()
                    .preserveSpaces()
                    .append(replacedOpenMarker)
                    .append(' ')
                    .append(markerSuffix)
                    .popOptions();

            markdown.pushPrefix().addPrefix(childPrefix, true);

            Node childNode = node.getFirstChild();
            if (childNode != null && node.getFirstChildAnyNot(BlankLine.class) != null) {
                markdown.pushPrefix().addPrefix(additionalItemPrefix, true);
                // NOTE: depends on first child
                FIRST_LIST_ITEM_CHILD.set(context.getDocument(), true);
                context.render(childNode);
                FIRST_LIST_ITEM_CHILD.set(context.getDocument(), false);
                markdown.popPrefix();

                while (true) {
                    childNode = childNode.getNext();
                    if (childNode == null) break;
                    context.render(childNode);
                }

                if (addBlankLineLooseItems && (node.isLoose() && context.getFormatterOptions().listSpacing == LOOSEN || context.getFormatterOptions().listSpacing == LOOSE)) {
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

        FIRST_LIST_ITEM_CHILD.set(context.getDocument(), savedFirstListItemChild);
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
                SequenceBuilder builder = context.getDocument().getChars().getBuilder();
                NodeFormatterContext subContext = context.getSubContext(subContextOptions, builder.getBuilder());
                MarkdownWriter subContextMarkdown = subContext.getMarkdown();
                subContextMarkdown.removeOptions(F_TRIM_TRAILING_WHITESPACE);
                subContext.renderChildren(node);

                BasedSequence nodeLessEol = node.getChars().trimEOL();
                BasedSequence trailingSpaces = node.getChars().trimmedEnd();
                if (trailingSpaces.isNotEmpty() && !subContextMarkdown.endsWithEOL()) {
                    // add these so our tracked offsets at end of paragraph after whitespaces are not outside the sequence
                    subContextMarkdown.append(trailingSpaces);
                }
                subContextMarkdown.line();
                subContextMarkdown.appendToSilently(builder, 0, -1);

                BasedSequence paragraphChars = builder.toSequence();
                BasedSequence altParagraphChars = builder.toSequence(context.getTrackedSequence());
                boolean haveAltSequence = paragraphChars != altParagraphChars;
                int startOffset;
                int endOffset;

                TrackedOffsetList trackedOffsets = context.getTrackedOffsets();
                if (haveAltSequence) {
                    // NOTE: this is only needed to find offset in trackedOffsets for the paragraph
                    BasedSequence charsLessEol = altParagraphChars.trimEnd();
                    startOffset = charsLessEol.getStartOffset();
                    int endOffsetDelta = nodeLessEol.countTrailingWhitespace() - charsLessEol.countTrailingWhitespace();
                    endOffset = charsLessEol.getEndOffset() + endOffsetDelta + 1;
                } else {
                    startOffset = nodeLessEol.getStartOffset();
                    endOffset = nodeLessEol.getEndOffset();
                }

                TrackedOffsetList paragraphTrackedOffsets = trackedOffsets.getTrackedOffsets(startOffset, endOffset);

                MarkdownParagraph formatter = new MarkdownParagraph(paragraphChars, altParagraphChars, formatterOptions.charWidthProvider);
                formatter.setOptions(context.getOptions());

                formatter.setWidth(formatterOptions.rightMargin - markdown.getPrefix().length());
                formatter.setKeepSoftBreaks(false);
                formatter.setKeepHardBreaks(formatterOptions.keepHardLineBreaks);
                formatter.setRestoreTrackedSpaces(context.isRestoreTrackedSpaces());
                formatter.setFirstIndent(NULL);
                formatter.setIndent(NULL);

                // adjust first line width, based on change in prefix after the first line EOL
                formatter.setFirstWidthOffset(-markdown.column() + markdown.getAfterEolPrefixDelta());

                if (formatterOptions.applySpecialLeadInHandlers) {
                    formatter.setLeadInHandlers(Parser.SPECIAL_LEAD_IN_HANDLERS.get(context.getDocument()));
                }

                for (TrackedOffset trackedOffset : paragraphTrackedOffsets) {
                    formatter.addTrackedOffset(trackedOffset);
                }

                BasedSequence wrappedText = formatter.wrapText().toMapped(SpaceMapper.fromNonBreakSpace);
                int startLine = markdown.getLineCount();
                int firstLineOffset = markdown.column();
                markdown.pushOptions().preserveSpaces().append(wrappedText).line().popOptions();

                if (!paragraphTrackedOffsets.isEmpty()) {
                    LineInfo startLineInfo = markdown.getLineInfo(startLine);

                    for (TrackedOffset trackedOffset : paragraphTrackedOffsets) {
                        if (trackedOffset.isResolved()) {
                            int offsetIndex = trackedOffset.getIndex();
                            @NotNull Pair<Integer, Integer> lineColumn = wrappedText.lineColumnAtIndex(offsetIndex);
                            int trackedLine = lineColumn.getFirst();
                            LineInfo lineInfo = markdown.getLineInfo(startLine + trackedLine);

                            int lengthOffset = startLineInfo.sumLength - startLineInfo.length;
                            int prefixDelta = lineInfo.sumPrefixLength - startLineInfo.sumPrefixLength + startLineInfo.prefixLength;
                            int delta = firstLineOffset + lengthOffset + prefixDelta;
                            trackedOffset.setIndex(offsetIndex + delta);
//                            System.out.println(String.format("Wrap Resolved %d -> %d + %d = %d in line[%d]: '%s'", trackedOffset.getOffset(), offsetIndex, delta, offsetIndex + delta, lineInfo.index + 1, line.getBuilder().append(line).toStringWithRanges(true)));
//                            System.out.println(String.format("      delta: %d = firstLineOffset: %d + lengthOffset: %d + prefixDelta: %d", delta, firstLineOffset, lengthOffset, prefixDelta));
//                            System.out.println(String.format("      startLineInfo: %s", lineInfo));
//                            System.out.println(String.format("      lineInfo: %s", lineInfo));
//                        } else {
//                            System.out.println(String.format("Wrap Unresolved %d", trackedOffset.getOffset()));
                        }
                    }
                }
            } else {
                context.renderChildren(node);
                markdown.line();
            }
        }
    }

    public static void renderBlockQuoteLike(BlockQuoteLike node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterOptions formatterOptions = context.getFormatterOptions();

        String combinedPrefix = getBlockLikePrefix(node, context, formatterOptions.blockQuoteMarkers, markdown.getPrefix());

        markdown.pushPrefix();

        if (!FIRST_LIST_ITEM_CHILD.get(context.getDocument())) {
            if (formatterOptions.blockQuoteBlankLines) {
                markdown.blankLine();
            }
            markdown.setPrefix(combinedPrefix, false);
        } else {
            String firstPrefix = getBlockLikePrefix(node, context, formatterOptions.blockQuoteMarkers, NULL);
            markdown.pushOptions().removeOptions(LineAppendable.F_WHITESPACE_REMOVAL).append(firstPrefix).popOptions();
            markdown.setPrefix(combinedPrefix, true);
        }

        int lines = markdown.getLineCountWithPending();
        context.renderChildren((Node) node);
        markdown.popPrefix();

        if (formatterOptions.blockQuoteBlankLines && (lines < markdown.getLineCountWithPending() && !FIRST_LIST_ITEM_CHILD.get(context.getDocument())))
            markdown.tailBlankLine();
    }
}
