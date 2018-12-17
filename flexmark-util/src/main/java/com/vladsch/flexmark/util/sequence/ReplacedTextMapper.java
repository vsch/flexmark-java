package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;

/**
 * Class which tracks text replacements to provide original offset from modified offset.
 *
 * This is needed when the original based sequence needs to be un-escaped but offsets to original escaped text
 * are needed.
 *
 * These replacements can be nested so that you can track replacements of replaced text.
 * To add nested replacements use startNestedReplacement()
 *
 * when isModified() returns true then the text mapper is already used and nested replacements need to be applied
 *
 */
public class ReplacedTextMapper {
    private ReplacedTextMapper parent;
    private BasedSequence original;
    private ArrayList<ReplacedTextRegion> regions = new ArrayList<ReplacedTextRegion>();
    private ArrayList<BasedSequence> replacedSegments = new ArrayList<BasedSequence>();
    private int replacedLength = 0;
    private BasedSequence replacedSequence = null;

    public ReplacedTextMapper(BasedSequence original) {
        this.original = original;
        this.parent = null;
    }

    private ReplacedTextMapper(ReplacedTextMapper other) {
        this.parent = other.parent;
        this.original = other.original;
        this.regions = other.regions;
        this.replacedSegments = other.replacedSegments;
        this.replacedLength = other.replacedLength;
        this.replacedSequence = other.getReplacedSequence();
    }

    public void startNestedReplacement(BasedSequence sequence) {
        assert sequence.equals(this.getReplacedSequence());

        // create parent from our data and re-initialize
        this.parent = new ReplacedTextMapper(this);
        this.original = sequence;
        this.regions = new ArrayList<>();
        this.replacedSegments = new ArrayList<>();
        this.replacedLength = 0;
        this.replacedSequence = null;
    }

    public boolean isModified() {
        return replacedLength > 0;
    }

    public boolean isFinalized() {
        return replacedSequence != null;
    }

    private void finalizeMods() {
        if (replacedSequence == null) {
            replacedSequence = SegmentedSequence.of(replacedSegments);
        }
    }

    public ReplacedTextMapper getParent() {
        return parent;
    }

    public void addReplacedText(int startIndex, int endIndex, BasedSequence replacedSequence) {
        if (isFinalized()) throw new IllegalStateException("Cannot modify finalized ReplacedTextMapper");

        regions.add(new ReplacedTextRegion(original.subSequence(startIndex, endIndex).getSourceRange(), new Range(startIndex, endIndex), new Range(replacedLength, replacedLength + replacedSequence.length())));
        replacedLength += replacedSequence.length();
        replacedSegments.add(replacedSequence);
    }

    public void addOriginalText(int startIndex, int endIndex) {
        if (isFinalized()) throw new IllegalStateException("Cannot modify finalized ReplacedTextMapper");

        if (startIndex < endIndex) {
            BasedSequence originalSegment = original.subSequence(startIndex, endIndex);
            regions.add(new ReplacedTextRegion(originalSegment.getSourceRange(), new Range(startIndex, endIndex), new Range(replacedLength, replacedLength + originalSegment.length())));
            replacedLength += originalSegment.length();
            replacedSegments.add(originalSegment);
        }
    }

    public ArrayList<ReplacedTextRegion> getRegions() {
        finalizeMods();
        return regions;
    }

    public ArrayList<BasedSequence> getReplacedSegments() {
        finalizeMods();
        return replacedSegments;
    }

    public BasedSequence getReplacedSequence() {
        finalizeMods();
        return replacedSequence;
    }

    public int getReplacedLength() {
        finalizeMods();
        return replacedLength;
    }

    private int parentOriginalOffset(int originalIndex) {
        return parent != null ? parent.originalOffset(originalIndex) : originalIndex;
    }

    public int originalOffset(int replacedIndex) {
        finalizeMods();

        if (regions.isEmpty()) return parentOriginalOffset(replacedIndex);
        if (replacedIndex == replacedLength) return parentOriginalOffset(original.length());

        int originalIndex = replacedIndex;

        for (ReplacedTextRegion region : regions) {
            if (region.containsReplacedIndex(replacedIndex)) {
                originalIndex = region.getOriginalRange().getStart() + replacedIndex - region.getReplacedRange().getStart();
                if (originalIndex > region.getOriginalRange().getEnd()) {
                    originalIndex = region.getOriginalRange().getEnd();
                }

                //originalIndex -= original.getStartOffset();
                break;
            }

            //if (region == regions.get(regions.size() - 1) && region.getReplaced().getEndOffset() == replacedIndex) {
            //    originalIndex = region.getOriginal().getEndOffset() - original.getStartOffset();
            //    break;
            //}
        }

        return parentOriginalOffset(originalIndex);
    }
}
