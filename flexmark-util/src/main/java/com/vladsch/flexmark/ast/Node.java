package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterable;
import com.vladsch.flexmark.util.collection.iteration.ReversiblePeekingIterator;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public abstract class Node {
    public static final BasedSequence[] EMPTY_SEGMENTS = BasedSequence.EMPTY_ARRAY;
    public static final String SPLICE = " … ";

    private Node parent = null;
    private Node firstChild = null;
    private Node lastChild = null;
    private Node prev = null;
    private Node next = null;
    private BasedSequence chars = BasedSequence.NULL;

    public Node() {
    }

    public Node(BasedSequence chars) {
        this.chars = chars;
    }

    public Node getAncestorOfType(Class... classes) {
        Node parent = getParent();
        while (parent != null) {
            for (Class nodeType : classes) {
                if (nodeType.isInstance(parent)) return parent;
            }
            parent = parent.getParent();
        }
        return null;
    }

    public Node getOldestAncestorOfTypeAfter(final Class ancestor, final Class after) {
        Node parent = getParent();
        Node oldestAncestor = null;
        while (parent != null) {
            if (ancestor.isInstance(parent)) oldestAncestor = parent;
            else if (after.isInstance(parent)) break;
            parent = parent.getParent();
        }
        return oldestAncestor;
    }

    public Node getChildOfType(Class... classes) {
        Node child = getFirstChild();
        while (child != null) {
            for (Class nodeType : classes) {
                if (nodeType.isInstance(parent)) return child;
            }
            child = child.getNext();
        }
        return null;
    }

    public static int getNodeOfTypeIndex(Node node, Class... classes) {
        int i = 0;
        for (Class nodeType : classes) {
            if (nodeType.isInstance(node)) return i;
            i++;
        }
        return -1;
    }

    public boolean isOrDescendantOfType(Class... classes) {
        Node node = this;
        while (node != null) {
            if (node.getNodeOfTypeIndex(classes) != -1) return true;
            node = node.getParent();
        }
        return false;
    }

    public int getNodeOfTypeIndex(Class... classes) {
        return Node.getNodeOfTypeIndex(this, classes);
    }

    /**
     * Overridden by ListBlock and any others whose children propagate their blank line to parent
     *
     * @return return a child block that can contain the parent's last blank line
     */
    public Node getLastBlankLineChild() {
        return null;
    }

    public ReversiblePeekingIterable<Node> getChildren() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new NodeIterable(firstChild, lastChild, false);
    }

    public ReversiblePeekingIterable<Node> getReversedChildren() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new NodeIterable(firstChild, lastChild, true);
    }

    public ReversiblePeekingIterable<Node> getDescendants() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new DescendantNodeIterable(getChildren());
    }

    public ReversiblePeekingIterable<Node> getReversedDescendants() {
        if (firstChild == null) {
            return NodeIterable.EMPTY;
        }
        return new DescendantNodeIterable(getReversedChildren());
    }

    public ReversiblePeekingIterator<Node> getChildIterator() {
        if (firstChild == null) {
            return NodeIterator.EMPTY;
        }
        return new NodeIterator(firstChild, lastChild, false);
    }

    public ReversiblePeekingIterator<Node> getReversedChildIterator() {
        if (firstChild == null) {
            return NodeIterator.EMPTY;
        }
        return new NodeIterator(firstChild, lastChild, true);
    }

    // full document char sequence
    public BasedSequence getChars() {
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

    public Document getDocument() {
        Node node = this;
        while (node != null && !(node instanceof Document)) {
            node = node.getParent();
        }

        return (Document) node;
    }

    public void setChars(BasedSequence chars) {
        this.chars = chars == null ? BasedSequence.NULL : chars;
    }

    public Node getNext() {
        return next;
    }

    public int getStartOffset() {
        return chars == null ? 0 : chars.getStartOffset();
    }

    public int getEndOffset() {
        return chars == null ? 0 : chars.getEndOffset();
    }

    public int getTextLength() {
        return chars == null ? 0 : chars.length();
    }

    public Node getPrevious() {
        return prev;
    }

    public Node getFirstChild() {
        return firstChild;
    }

    public Node getLastChild() {
        return lastChild;
    }

    public Node getParent() {
        return parent;
    }

    protected void setParent(Node parent) {
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

    public void prependChild(Node child) {
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

    public void insertAfter(Node sibling) {
        sibling.unlink();
        sibling.next = this.next;
        if (sibling.next != null) {
            sibling.next.prev = sibling;
        }
        sibling.prev = this;
        this.next = sibling;
        sibling.parent = this.parent;
        if (sibling.next == null) {
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
            sibling.parent.firstChild = sibling;
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" + toStringAttributes() + "}";
    }

    public void getAstExtra(StringBuilder out) {

    }

    public void astExtraChars(StringBuilder out) {
        if (getChars().length() > 0) {
            if (getChars().length() <= 10) {
                segmentSpanChars(out, getChars(), "chars");
            } else {
                // give the first 5 and last 5
                segmentSpanChars(out, getChars().getStartOffset(), getChars().getEndOffset(), "chars", getChars().subSequence(0, 5).toVisibleWhitespaceString(), SPLICE, getChars().subSequence(getChars().length() - 5).toVisibleWhitespaceString());
            }
        }
    }

    protected String toStringAttributes() {
        return "";
    }

    public abstract BasedSequence[] getSegments();

    public static BasedSequence getLeadSegment(BasedSequence[] segments) {
        for (BasedSequence segment : segments) {
            if (segment != null && segment != BasedSequence.NULL) return segment;
        }

        return BasedSequence.NULL;
    }

    public static BasedSequence getTrailSegment(BasedSequence[] segments) {
        int iMax = segments.length;

        for (int i = iMax; i-- > 0; ) {
            BasedSequence segment = segments[i];
            if (segment != null && segment != BasedSequence.NULL) return segment;
        }

        return BasedSequence.NULL;
    }

    public static BasedSequence spanningChars(BasedSequence... segments) {
        int startOffset = Integer.MAX_VALUE;
        int endOffset = -1;
        BasedSequence firstSequence = null;
        BasedSequence lastSequence = null;
        for (BasedSequence segment : segments) {
            if (segment != null && segment != BasedSequence.NULL) {
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
            // see if these are greater than already assigned chars
            if (chars.isNull()) {
                setChars(spanningChars);
            } else {
                int start = Integer.min(chars.getStartOffset(), spanningChars.getStartOffset());
                int end = Integer.max(chars.getEndOffset(), spanningChars.getEndOffset());
                setChars(chars.baseSubSequence(start, end));
            }
        }
    }

    protected BasedSequence deNullify(BasedSequence nullable) {
        return nullable == null ? BasedSequence.NULL : nullable;
    }

    public static void segmentSpan(StringBuilder out, int startOffset, int endOffset, String name) {
        if (name != null && !name.trim().isEmpty()) out.append(" ").append(name).append(":");
        out.append("[").append(startOffset).append(", ").append(endOffset).append("]");
    }

    public static void segmentSpanChars(StringBuilder out, int startOffset, int endOffset, String name, String chars) {
        segmentSpanChars(out, startOffset, endOffset, name, chars, "", "");
    }

    public static void segmentSpanChars(StringBuilder out, int startOffset, int endOffset, String name, String chars1, String splice, String chars2) {
        if (name != null && !name.trim().isEmpty()) out.append(" ").append(name).append(":");
        out.append("[").append(startOffset).append(", ").append(endOffset);
        if (startOffset < endOffset) {
            out.append(", \"");
            escapeJavaString(out, chars1);
            out.append(splice);
            escapeJavaString(out, chars2);
            out.append("\"");
        }
        out.append("]");
    }

    private static void escapeJavaString(StringBuilder out, String chars) {
        int iMax = chars.length();
        for (int i = 0; i < iMax; i++) {
            char c = chars.charAt(i);
            switch (c) {
                case '"':
                    out.append("\\\"");
                    break;
                case '\n':
                    out.append("\\n");
                    break;
                case '\r':
                    out.append("\\r");
                    break;
                case '\t':
                    out.append("\\t");
                    break;
                case '\b':
                    out.append("\\b");
                    break;
                case '\f':
                    out.append("\\f");
                    break;
                case '\0':
                    out.append("\\0");
                    break;
                default:
                    if (c < ' ') {
                        out.append('%').append(String.format("%02x", (int) c));
                    } else {
                        out.append(c);
                    }
                    break;
            }
        }
    }

    public static void segmentSpan(StringBuilder out, BasedSequence sequence, String name) {
        if (sequence.isNotNull()) segmentSpan(out, sequence.getStartOffset(), sequence.getEndOffset(), name);
    }

    public static void segmentSpanChars(StringBuilder out, BasedSequence sequence, String name) {
        if (sequence.isNotNull()) segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toString());
    }

    public static void delimitedSegmentSpan(StringBuilder out, BasedSequence openingSequence, BasedSequence sequence, BasedSequence closingSequence, String name) {
        segmentSpanChars(out, openingSequence.getStartOffset(), openingSequence.getEndOffset(), name + "Open", openingSequence.toString());
        if (sequence.length() <= 10) {
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toVisibleWhitespaceString());
        } else {
            // give the first 5 and last 5
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.subSequence(0, 5).toVisibleWhitespaceString(), SPLICE, sequence.endSequence(sequence.length() - 5).toVisibleWhitespaceString());
        }
        segmentSpanChars(out, closingSequence.getStartOffset(), closingSequence.getEndOffset(), name + "Close", closingSequence.toString());
    }

    public static void delimitedSegmentSpanChars(StringBuilder out, BasedSequence openingSequence, BasedSequence sequence, BasedSequence closingSequence, String name) {
        if (openingSequence.isNotNull())
            segmentSpanChars(out, openingSequence.getStartOffset(), openingSequence.getEndOffset(), name + "Open", openingSequence.toString());
        if (sequence.isNotNull())
            segmentSpanChars(out, sequence.getStartOffset(), sequence.getEndOffset(), name, sequence.toVisibleWhitespaceString());
        if (closingSequence.isNotNull())
            segmentSpanChars(out, closingSequence.getStartOffset(), closingSequence.getEndOffset(), name + "Close", closingSequence.toString());
    }

    public void takeChildren(Node node) {
        if (node.firstChild != null) {
            Node firstChild = node.firstChild;
            Node lastChild = node.lastChild;

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

    public String getNodeName() {
        return getClass().getName().substring(getClass().getPackage().getName().length() + 1);
    }

    public void astString(StringBuilder out, boolean withExtra) {
        out.append(getNodeName());
        out.append("[").append(getStartOffset()).append(", ").append(getEndOffset()).append("]");
        if (withExtra) getAstExtra(out);
    }

    public String toAstString(boolean withExtra) {
        StringBuilder sb = new StringBuilder();
        astString(sb, withExtra);
        return sb.toString();
    }

    public static String toSegmentSpan(BasedSequence sequence, String name) {
        StringBuilder out = new StringBuilder();
        segmentSpan(out, sequence, name);
        return out.toString();
    }

    public BasedSequence childChars() {
        if (firstChild == null || lastChild == null) {
            return BasedSequence.NULL;
        }

        return firstChild.getChars().baseSubSequence(firstChild.getStartOffset(), lastChild.getEndOffset());
    }
}
