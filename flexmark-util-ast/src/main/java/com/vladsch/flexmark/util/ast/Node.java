package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Range;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import com.vladsch.flexmark.util.visitor.AstNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public abstract class Node {
    final public static BasedSequence[] EMPTY_SEGMENTS = BasedSequence.EMPTY_ARRAY;
    final public static String SPLICE = " … ";

    final public static AstNode<Node> AST_ADAPTER = new AstNode<Node>() {
        @Override
        public @Nullable Node getFirstChild(@NotNull Node node) {
            return node.firstChild;
        }

        @Override
        public @Nullable Node getNext(@NotNull Node node) {
            return node.next;
        }
    };

    private @Nullable Node parent = null;
    @Nullable Node firstChild = null;
    private @Nullable Node lastChild = null;
    private @Nullable Node prev = null;
    @Nullable Node next = null;
    private @NotNull BasedSequence chars = BasedSequence.NULL;

    public Node() {
    }

    public Node(@NotNull BasedSequence chars) {
        this.chars = chars;
    }

    /*
     * getChars() convenience delegates
     */
    // @formatter:off
    public int getStartOffset() { return chars.getStartOffset();}
    public int getEndOffset() { return chars.getEndOffset();}
    public int getTextLength() { return chars.length();}
    public BasedSequence getBaseSequence() {return chars.getBaseSequence();}
    public Range getSourceRange() {return chars.getSourceRange();}
    public BasedSequence baseSubSequence(int startIndex, int endIndex) {return chars.baseSubSequence(startIndex, endIndex);}
    public BasedSequence baseSubSequence(int startIndex) {return chars.baseSubSequence(startIndex);}
    public BasedSequence getEmptyPrefix() {return chars.getEmptyPrefix();}
    public BasedSequence getEmptySuffix() {return chars.getEmptySuffix();}

    public int getStartOfLine() { return chars.baseStartOfLine(); }
    public int getEndOfLine() { return chars.baseEndOfLine(); }
    public int startOfLine(int index) { return chars.baseStartOfLine(index); }
    public int endOfLine(int index) { return chars.baseEndOfLine(index); }
    public Pair<Integer, Integer> lineColumnAtIndex(int index) { return chars.baseLineColumnAtIndex(index); }
    public Pair<Integer, Integer> lineColumnAtStart() { return chars.baseLineColumnAtStart(); }
    public Pair<Integer, Integer> getLineColumnAtEnd() { return chars.baseLineColumnAtEnd(); }
    // @formatter:on

    public @Nullable Node getAncestorOfType(@NotNull Class<?>... classes) {
        Node parent = getParent();
        while (parent != null) {
            for (Class<?> nodeType : classes) {
                if (nodeType.isInstance(parent)) return parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public int countAncestorsOfType(@NotNull Class<?>... classes) {
        Node parent = getParent();
        int count = 0;
        while (parent != null) {
            for (Class<?> nodeType : classes) {
                if (nodeType.isInstance(parent)) {
                    count++;
                    break;
                }
            }
            parent = parent.getParent();
        }
        return count;
    }

    public int countDirectAncestorsOfType(@Nullable Class<?> skip, @NotNull Class<?>... classes) {
        Node parent = getParent();
        int count = 0;
        while (parent != null) {
            boolean hadMatch = false;
            for (Class<?> nodeType : classes) {
                if (nodeType.isInstance(parent)) {
                    count++;
                    hadMatch = true;
                    break;
                }
                if (skip != null && skip.isInstance(parent)) {
                    hadMatch = true;
                    break;
                }
            }
            if (!hadMatch) {
                break;
            }
            parent = parent.getParent();
        }
        return count;
    }

    public @Nullable Node getOldestAncestorOfTypeAfter(@NotNull Class<?> ancestor, @NotNull Class<?> after) {
        Node parent = getParent();
        Node oldestAncestor = null;
        while (parent != null) {
            if (ancestor.isInstance(parent)) oldestAncestor = parent;
            else if (after.isInstance(parent)) break;
            parent = parent.getParent();
        }
        return oldestAncestor;
    }

    public @Nullable Node getChildOfType(@NotNull Class<?>... classes) {
        Node child = getFirstChild();
        while (child != null) {
            for (Class<?> nodeType : classes) {
                if (nodeType.isInstance(child)) return child;
            }
            child = child.getNext();
        }
        return null;
    }

    public static int getNodeOfTypeIndex(@NotNull Node node, @NotNull Class<?>... classes) {
        int i = 0;
        for (Class<?> nodeType : classes) {
            if (nodeType.isInstance(node)) return i;
            i++;
        }
        return -1;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isOrDescendantOfType(@NotNull Class<?>... classes) {
        Node node = this;
        while (node != null) {
            if (node.getNodeOfTypeIndex(classes) != -1) return true;
            node = node.getParent();
        }
        return false;
    }

    public int getNodeOfTypeIndex(@NotNull Class<?>... classes) {
        return Node.getNodeOfTypeIndex(this, classes);
    }

    /**
     * Overridden by ListBlock and any others whose children propagate their blank line to parent
     *
     * @return return a child block that can contain the parent's last blank line
     */
    public @Nullable Node getLastBlankLineChild() {
        return null;
    }

    public @NotNull ReversiblePeekingIterable<Node> getChildren() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new NodeIterable(firstChild, lastChild, false);
    }

    public @NotNull ReversiblePeekingIterable<Node> getReversedChildren() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new NodeIterable(firstChild, lastChild, true);
    }

    public @NotNull ReversiblePeekingIterable<Node> getDescendants() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new DescendantNodeIterable(getChildren());
    }

    public @NotNull ReversiblePeekingIterable<Node> getReversedDescendants() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new DescendantNodeIterable(getReversedChildren());
    }

    public @NotNull ReversiblePeekingIterator<Node> getChildIterator() {
        if (firstChild == null) {
            return NodeIterator.EMPTY;
        }
        return new NodeIterator(firstChild, lastChild, false);
    }

    public @NotNull ReversiblePeekingIterator<Node> getReversedChildIterator() {
        if (firstChild == null) {
            return NodeIterator.EMPTY;
        }
        return new NodeIterator(firstChild, lastChild, true);
    }

    // full document char sequence
    public @NotNull BasedSequence getChars() {
        return chars;
    }

    public void removeChildren() {
        Node child = firstChild;
        while (child != null) {
            Node nextChild = child.getNext();
            child.unlink();
            child = nextChild;
        }
    }

    public boolean hasChildren() {
        return firstChild != null;
    }

    public boolean hasOrMoreChildren(int childCount) {
        if (firstChild != null) {
            int count = 0;
            for (Node child : getChildren()) {
                count++;
                if (count >= childCount) return true;
            }
        }
        return false;
    }

    public @NotNull Document getDocument() {
        Node node = this;
        while (node != null && !(node instanceof Document)) {
            node = node.getParent();
        }
        assert node != null : "Node should always have Document ancestor";
        return (Document) node;
    }

    public void setChars(@NotNull BasedSequence chars) {
        this.chars = chars;
    }

    @Nullable
    public Node getNext() {
        return next;
    }

    @NotNull
    public Node getLastInChain() {
        Node lastNode = this;
        while (this.getClass().isInstance(lastNode.getNext())) lastNode = lastNode.getNext();
        return lastNode;
    }

    public @Nullable Node getPrevious() {
        return prev;
    }

    public void extractToFirstInChain(@NotNull Node node) {
        getFirstInChain().extractChainTo(node);
    }

    public void extractChainTo(@NotNull Node node) {
        Node lastNode = this;
        do {
            Node next = lastNode.getNext();
            node.appendChild(lastNode);
            lastNode = next;
        } while (this.getClass().isInstance(lastNode));
    }

    public @NotNull Node getFirstInChain() {
        Node lastNode = this;
        while (this.getClass().isInstance(lastNode.getPrevious())) lastNode = lastNode.getPrevious();
        return lastNode;
    }

    public @Nullable Node getPreviousAnyNot(@NotNull Class<?>... classes) {
        Node node = prev;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) != -1) {
                node = node.prev;
            }
        }
        return node;
    }

    public @Nullable Node getPreviousAny(@NotNull Class<?>... classes) {
        Node node = prev;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) == -1) {
                node = node.prev;
            }
        }
        return node;
    }

    public @Nullable Node getNextAnyNot(@NotNull Class<?>... classes) {
        Node node = next;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) != -1) {
                node = node.next;
            }
        }
        return node;
    }

    public @Nullable Node getNextAny(@NotNull Class<?>... classes) {
        Node node = next;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) == -1) {
                node = node.next;
            }
        }
        return node;
    }

    public @Nullable Node getFirstChild() {
        return firstChild;
    }

    public @Nullable Node getFirstChildAnyNot(@NotNull Class<?>... classes) {
        Node node = firstChild;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) != -1) {
                node = node.next;
            }
        }
        return node;
    }

    public @Nullable Node getFirstChildAny(@NotNull Class<?>... classes) {
        Node node = firstChild;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) == -1) {
                node = node.next;
            }
        }
        return node;
    }

    public @Nullable Node getLastChild() {
        return lastChild;
    }

    public @Nullable Node getLastChildAnyNot(@NotNull Class<?>... classes) {
        Node node = lastChild;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) != -1) {
                node = node.prev;
            }
        }
        return node;
    }

    public @Nullable Node getLastChildAny(@NotNull Class<?>... classes) {
        Node node = lastChild;
        if (classes.length > 0) {
            while (node != null && getNodeOfTypeIndex(node, classes) == -1) {
                node = node.prev;
            }
        }
        return node;
    }

    public @Nullable Node getParent() {
        return parent;
    }

    public @Nullable Node getGrandParent() {
        return parent == null ? null : parent.getParent();
    }

    protected void setParent(@Nullable Node parent) {
        this.parent = parent;
    }

    public void appendChild(Node child) {
        child.unlink();
        child.setParent(this);
        if (this.lastChild != null) {
            this.lastChild.next = child;
            child.prev = this.lastChild;
            this.lastChild = child;
        } else {
            this.firstChild = child;
            this.lastChild = child;
        }
    }

    public void prependChild(@NotNull Node child) {
        child.unlink();
        child.setParent(this);
        if (this.firstChild != null) {
            this.firstChild.prev = child;
            child.next = this.firstChild;
            this.firstChild = child;
        } else {
            this.firstChild = child;
            this.lastChild = child;
        }
    }

    public void unlink() {
        if (this.prev != null) {
            this.prev.next = this.next;
        } else if (this.parent != null) {
            this.parent.firstChild = this.next;
        }
        if (this.next != null) {
            this.next.prev = this.prev;
        } else if (this.parent != null) {
            this.parent.lastChild = this.prev;
        }
        this.parent = null;
        this.next = null;
        this.prev = null;
    }

    public void insertAfter(@NotNull Node sibling) {
        sibling.unlink();

        sibling.next = this.next;
        if (sibling.next != null) {
            sibling.next.prev = sibling;
        }

        sibling.prev = this;
        this.next = sibling;
        sibling.parent = this.parent;
        if (sibling.next == null) {
            assert sibling.parent != null;
            sibling.parent.lastChild = sibling;
        }
    }

    public void insertBefore(Node sibling) {
        sibling.unlink();
        sibling.prev = this.prev;
        if (sibling.prev != null) {
            sibling.prev.next = sibling;
        }
        sibling.next = this;
        this.prev = sibling;
        sibling.parent = this.parent;
        if (sibling.prev == null) {
            assert sibling.parent != null;
            sibling.parent.firstChild = sibling;
        }
    }

    @Override
    public String toString() {
        return getClass().getName().substring(getClass().getPackage().getName().length() + 1) + "{" + toStringAttributes() + "}";
    }

    public void getAstExtra(@NotNull StringBuilder out) {

    }

    public void astExtraChars(@NotNull StringBuilder out) {
        if (getChars().length() > 0) {
            if (getChars().length() <= 10) {
                segmentSpanChars(out, getChars(), "chars");
            } else {
                // give the first 5 and last 5
                segmentSpanChars(out, getChars().getStartOffset(), getChars().getEndOffset(), "chars", getChars().subSequence(0, 5).toVisibleWhitespaceString(), SPLICE, getChars().subSequence(getChars().length() - 5).toVisibleWhitespaceString());
            }
        }
    }

    public static void astChars(@NotNull StringBuilder out, @NotNull CharSequence chars, @NotNull String name) {
        if (chars.length() > 0) {
            if (chars.length() <= 10) {
                out.append(' ').append(name).append(" \"").append(chars).append("\"");
            } else {
                // give the first 5 and last 5
                out.append(' ').append(name).append(" \"").append(chars.subSequence(0, 5)).append(SPLICE).append(chars.subSequence(chars.length() - 5, chars.length())).append("\"");
            }
        }
    }

    @NotNull
    protected String toStringAttributes() {
        return "";
    }

    @NotNull
    public abstract BasedSequence[] getSegments();

    @NotNull
    public static BasedSequence getLeadSegment(@NotNull BasedSequence[] segments) {
        for (BasedSequence segment : segments) {
            if (segment != BasedSequence.NULL) return segment;
        }

        return BasedSequence.NULL;
    }

    @NotNull
    public static BasedSequence getTrailSegment(BasedSequence[] segments) {
        int iMax = segments.length;

        for (int i = iMax; i-- > 0; ) {
            BasedSequence segment = segments[i];
            if (segment != BasedSequence.NULL) return segment;
        }

        return BasedSequence.NULL;
    }

    @NotNull
    public static BasedSequence spanningChars(BasedSequence... segments) {
        int startOffset = Integer.MAX_VALUE;
        int endOffset = -1;
        BasedSequence firstSequence = null;
        BasedSequence lastSequence = null;
        for (BasedSequence segment : segments) {
            if (segment != BasedSequence.NULL) {
                if (startOffset > segment.getStartOffset()) {
                    startOffset = segment.getStartOffset();
                    firstSequence = segment;
                }

                if (endOffset <= segment.getEndOffset()) {
                    endOffset = segment.getEndOffset();
                    lastSequence = segment;
                }
            }
        }

        if (firstSequence != null && lastSequence != null) {
            return firstSequence.baseSubSequence(firstSequence.getStartOffset(), lastSequence.getEndOffset());
        } else {
            return BasedSequence.NULL;
        }
    }

    public void setCharsFromContentOnly() {
        chars = BasedSequence.NULL;
        setCharsFromContent();
    }

    public void setCharsFromContent() {
        BasedSequence[] segments = getSegments();
        BasedSequence spanningChars = null;

        if (segments.length > 0) {
            BasedSequence leadSegment = getLeadSegment(segments);
            BasedSequence trailSegment = getTrailSegment(segments);

            if (firstChild == null || lastChild == null) {
                BasedSequence[] sequences = new BasedSequence[] {
                        leadSegment,
                        trailSegment
                };

                spanningChars = spanningChars(sequences);
            } else {
                BasedSequence[] sequences = new BasedSequence[] {
                        leadSegment,
                        trailSegment,
                        firstChild.chars,
                        lastChild.chars
                };

                spanningChars = spanningChars(sequences);
            }
        } else if (firstChild != null && lastChild != null) {
            BasedSequence[] sequences = new BasedSequence[] {
                    firstChild.chars,
                    lastChild.chars
            };

            spanningChars = spanningChars(sequences);
        }

        if (spanningChars != null) {
            //see if these are greater than already assigned chars
            if (chars.isNull()) {
                setChars(spanningChars);
            } else {
                int start = Utils.min(chars.getStartOffset(), spanningChars.getStartOffset());
                int end = Utils.max(chars.getEndOffset(), spanningChars.getEndOffset());
                setChars(chars.baseSubSequence(start, end));
            }
        }
    }

    public static void segmentSpan(@NotNull StringBuilder out, int startOffset, int endOffset, @Nullable String name) {
        if (name != null && !name.trim().isEmpty()) out.append(" ").append(name).append(":");
        out.append("[").append(startOffset).append(", ").append(endOffset).append("]");
    }

    public static void segmentSpanChars(@NotNull StringBuilder out, int startOffset, int endOffset, @Nullable String name, @NotNull String chars) {
        segmentSpanChars(out, startOffset, endOffset, name, chars, "", "");
    }

    public static void segmentSpanChars(
            @NotNull StringBuilder out,
            int startOffset,
            int endOffset,
            @Nullable String name,
            @NotNull String chars1,
            @NotNull String splice,
            @NotNull String chars2
    ) {
        if (name != null && !name.trim().isEmpty()) out.append(" ").append(name).append(":");
        out.append("[").append(startOffset).append(", ").append(endOffset);
        if (!chars1.isEmpty() || !chars2.isEmpty()) {
            out.append(", \"");
            Utils.escapeJavaString(out, chars1);
            out.append(splice);
            Utils.escapeJavaString(out, chars2);
            out.append("\"");
        }
        out.append("]");
    }

    public static void segmentSpan(@NotNull StringBuilder out, @NotNull BasedSequence sequence, @Nullable String name) {
        if (sequence.isNotNull()) segmentSpan(out, sequence.getStartOffset(), sequence.getEndOffset(), name);
    }

    public static void segmentSpanChars(@NotNull StringBuilder out, @NotNull BasedSequence sequence, @NotNull String name) {
        if (sequence.isNotNull())
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toString());
    }

    public static void segmentSpanCharsToVisible(@NotNull StringBuilder out, @NotNull BasedSequence sequence, @NotNull String name) {
        if (sequence.isNotNull()) {
            if (sequence.length() <= 10) {
                segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toVisibleWhitespaceString());
            } else {
                // give the first 5 and last 5
                segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.subSequence(0, 5).toVisibleWhitespaceString(), SPLICE, sequence.endSequence(sequence.length() - 5).toVisibleWhitespaceString());
            }
        }
    }

    public static void delimitedSegmentSpan(
            @NotNull StringBuilder out,
            @NotNull BasedSequence openingSequence,
            @NotNull BasedSequence sequence,
            @NotNull BasedSequence closingSequence,
            @NotNull String name
    ) {
        segmentSpanChars(out, openingSequence.getStartOffset(), openingSequence.getEndOffset(), name + "Open", openingSequence.toString());
        if (sequence.length() <= 10) {
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toVisibleWhitespaceString());
        } else {
            // give the first 5 and last 5
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.subSequence(0, 5).toVisibleWhitespaceString(), SPLICE, sequence.endSequence(sequence.length() - 5).toVisibleWhitespaceString());
        }
        segmentSpanChars(out, closingSequence.getStartOffset(), closingSequence.getEndOffset(), name + "Close", closingSequence.toString());
    }

    public static void delimitedSegmentSpanChars(
            @NotNull StringBuilder out,
            @NotNull BasedSequence openingSequence,
            @NotNull BasedSequence sequence,
            @NotNull BasedSequence closingSequence,
            @NotNull String name
    ) {
        if (openingSequence.isNotNull())
            segmentSpanChars(out, openingSequence.getStartOffset(), openingSequence.getEndOffset(), name + "Open", openingSequence.toString());
        if (sequence.isNotNull())
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toVisibleWhitespaceString());
        if (closingSequence.isNotNull())
            segmentSpanChars(out, closingSequence.getStartOffset(), closingSequence.getEndOffset(), name + "Close", closingSequence.toString());
    }

    public void takeChildren(@NotNull Node node) {
        if (node.firstChild != null) {
            Node firstChild = node.firstChild;
            Node lastChild = node.lastChild;
            assert lastChild != null;

            if (lastChild != firstChild) {
                node.firstChild = null;
                node.lastChild = null;

                firstChild.parent = this;
                lastChild.parent = this;

                if (this.lastChild != null) {
                    this.lastChild.next = firstChild;
                    firstChild.prev = this.lastChild;
                } else {
                    this.firstChild = firstChild;
                }

                this.lastChild = lastChild;
            } else {
                // just a single child
                appendChild(firstChild);
            }
        }
    }

    public @NotNull String getNodeName() {
        return getClass().getName().substring(getClass().getPackage().getName().length() + 1);
    }

    public void astString(@NotNull StringBuilder out, boolean withExtra) {
        out.append(getNodeName());
        out.append("[").append(getStartOffset()).append(", ").append(getEndOffset()).append("]");
        if (withExtra) getAstExtra(out);
    }

    public @NotNull String toAstString(boolean withExtra) {
        StringBuilder sb = new StringBuilder();
        astString(sb, withExtra);
        return sb.toString();
    }

    public static @NotNull String toSegmentSpan(@NotNull BasedSequence sequence, @Nullable String name) {
        StringBuilder out = new StringBuilder();
        segmentSpan(out, sequence, name);
        return out.toString();
    }

    public BasedSequence getChildChars() {
        if (firstChild == null || lastChild == null) {
            return BasedSequence.NULL;
        }

        return firstChild.baseSubSequence(firstChild.getStartOffset(), lastChild.getEndOffset());
    }

    public BasedSequence getExactChildChars() {
        if (firstChild == null || lastChild == null) {
            return BasedSequence.NULL;
        }

        // this is not just base sequence between first and last child,
        // which will not include any out-of base chars if they are present, this builds a segmented sequence of child chars
        Node child = getFirstChild();
        SequenceBuilder segments = SequenceBuilder.emptyBuilder(getChars());

        while (child != null) {
            child.getChars().addSegments(segments.getSegmentBuilder());
            child = child.getNext();
        }

        return segments.toSequence();
    }

    public @NotNull Node getBlankLineSibling() {
        // need to find the first node that can contain a blank line that is not the last non-blank line of its parent
        assert parent != null;

        Node parent = this.parent;
        Node lastBlankLineSibling = this;
        Node nextBlankLineSibling = this;

        while (parent.parent != null) {
            boolean wasLastItem = parent == parent.parent.getLastChildAnyNot(BlankLine.class);
            if (!wasLastItem) break;

            lastBlankLineSibling = nextBlankLineSibling;
            if (parent instanceof BlankLineContainer) {
                nextBlankLineSibling = parent;
            }

            parent = parent.parent;
            if (parent == null) break;
        }

        return lastBlankLineSibling;
    }

    public void moveTrailingBlankLines() {
        Node blankLine = getLastChild();
        if (blankLine instanceof BlankLine) {
            Node blankLinePos = getBlankLineSibling();
            blankLine = blankLine.getFirstInChain();
            blankLinePos.insertChainAfter(blankLine);

            Node parent = this;
            do {
                parent.setCharsFromContentOnly();
                parent = parent.parent;
            }
            while (parent != null && parent != blankLinePos.getParent());
        }
    }

    public int getLineNumber() {
        return getStartLineNumber();
    }

    public int getStartLineNumber() {
        return getDocument().getLineNumber(chars.getStartOffset());
    }

    public int getEndLineNumber() {
        int endOffset = chars.getEndOffset();
        return getDocument().getLineNumber(endOffset > 0 ? endOffset - 1 : endOffset);
    }

    /**
     * Get the segments making up the node's characters.
     * <p>
     * Used to get segments after the some of the node's elements were modified
     *
     * @return array of segments
     */
    public @NotNull BasedSequence[] getSegmentsForChars() {
        return getSegments();
    }

    /**
     * Get the char sequence from segments making up the node's characters.
     * <p>
     * Used to get segments after the some of the node's elements were modified
     *
     * @return concatenated string of all segments
     */
    public @NotNull BasedSequence getCharsFromSegments() {
        @NotNull BasedSequence[] segments = getSegmentsForChars();
        return segments.length == 0 ? BasedSequence.NULL : SegmentedSequence.create(segments[0], Arrays.asList(segments));
    }

    /**
     * Set the node's char string from segments making up the node's characters.
     * <p>
     * Used to get segments after the some of the node's elements were modified
     */
    public void setCharsFromSegments() {
        setChars(getCharsFromSegments());
    }

    /**
     * Append all from child to end of chain to this node
     *
     * @param firstNode first child in chain
     */
    public void appendChain(@NotNull Node firstNode) {
        Node node = firstNode;
        while (node != null) {
            Node next = node.next;
            appendChild(node);
            node = next;
        }
    }

    /**
     * Append all from child to end of chain to this node
     *
     * @param firstNode first child in chain
     */
    public void insertChainAfter(@NotNull Node firstNode) {
        Node posNode = this;
        Node node = firstNode;
        while (node != null) {
            Node next = node.next;
            posNode.insertAfter(node);
            posNode = node;
            node = next;
        }
    }

    /**
     * Append all from child to end of chain to this node
     *
     * @param firstNode first child in chain
     */
    public void insertChainBefore(@NotNull Node firstNode) {
        Node posNode = this;
        Node node = firstNode;
        while (node != null) {
            Node next = node.next;
            posNode.insertBefore(node);
            node = next;
        }
    }
}
