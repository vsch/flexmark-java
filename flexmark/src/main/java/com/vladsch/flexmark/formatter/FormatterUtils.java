package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.SoftLineBreak;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ast.BlockQuoteLike;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.NullableDataKey;
import com.vladsch.flexmark.util.format.options.BlockQuoteContinuationMarker;
import com.vladsch.flexmark.util.format.options.BlockQuoteMarker;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharPredicate;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import org.jetbrains.annotations.NotNull;

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
}
