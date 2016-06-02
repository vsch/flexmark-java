package org.commonmark.node;

import org.commonmark.internal.BlockContent;
import org.commonmark.internal.SourceText;

import java.util.List;

public abstract class Block extends SegmentedNode {
    private static int[] lineSegments(BlockContent blockContent, int offsetInParent) {
        int[] segments = new int[blockContent.getLineCount()];

        List<SourceText> contentLines = blockContent.getLines();
        if (contentLines.size() > 0) {
            if (offsetInParent < 0) offsetInParent = contentLines.get(0).getStartOffset();
            int iMax = contentLines.size();

            for (int i = 0; i < iMax; i++) {
                segments[i * 2] = contentLines.get(i).getStartOffset() - offsetInParent;
                segments[i * 2 + 1] = contentLines.get(i).getEndOffset() - offsetInParent;
            }
        }

        return segments;
    }

    public Block() {

    }

    public Block(int offsetInParent, int textLength) {
        super(offsetInParent, textLength);
    }

    public Block(int offsetInParent, int textLength, int... segmentOffsets) {
        super(offsetInParent, textLength, segmentOffsets);
    }

    public Block(BlockContent blockContent) {
        super(blockContent.getStartOffset(), blockContent.getEndOffset(), lineSegments(blockContent, blockContent.getStartOffset()));
    }

    public Block(int offsetInParent, int textLength, BlockContent blockContent) {
        super(offsetInParent, textLength, lineSegments(blockContent, offsetInParent));
    }

    public void setSourcePos(int offsetInParent, int textLength, BlockContent blockContent) {
        setSourcePos(offsetInParent, textLength);
        setSegmentOffsets(lineSegments(blockContent, offsetInParent));
    }

    public void setSourcePos(BlockContent blockContent) {
        setSourcePos(blockContent.getStartOffset(), textLength);
        setSegmentOffsets(lineSegments(blockContent, blockContent.getStartOffset()));
    }

    public CharSequence getContentLineChars(CharSequence charSequence, int line) {
        return getSegmentChars(charSequence, 1 + line * 2);
    }

    public CharSequence getContentChars(CharSequence charSequence) {
        return charSequence.subSequence(getSegmentStartOffset(1), getSegmentEndOffset(segmentOffsets.length - 1));
    }

    public Block getParent() {
        return (Block) super.getParent();
    }

    @Override
    protected void setParent(Node parent) {
        if (!(parent instanceof Block)) {
            throw new IllegalArgumentException("Parent of block must also be block (can not be inline)");
        }
        super.setParent(parent);
    }
}
