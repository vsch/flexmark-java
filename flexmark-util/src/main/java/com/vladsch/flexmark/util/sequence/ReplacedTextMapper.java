package com.vladsch.flexmark.util.sequence;

import java.util.ArrayList;

public class ReplacedTextMapper {
    private final BasedSequence original;
    private ArrayList<ReplacedTextRegion> regions = new ArrayList<ReplacedTextRegion>();
    private ArrayList<BasedSequence> replacedSegments = new ArrayList<BasedSequence>();
    private int replacedLength = 0;

    public ReplacedTextMapper(BasedSequence original) {
        this.original = original;
    }

    public void addReplacedText(int startIndex, int endIndex, BasedSequence replacedSequence) {
        regions.add(new ReplacedTextRegion(original.subSequence(startIndex, endIndex).getSourceRange(), new Range(startIndex, endIndex), new Range(replacedLength, replacedLength + replacedSequence.length())));
        replacedLength += replacedSequence.length();
        replacedSegments.add(replacedSequence);
    }

    public void addOriginalText(int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            BasedSequence originalSegment = original.subSequence(startIndex, endIndex);
            regions.add(new ReplacedTextRegion(originalSegment.getSourceRange(), new Range(startIndex, endIndex), new Range(replacedLength, replacedLength + originalSegment.length())));
            replacedLength += originalSegment.length();
            replacedSegments.add(originalSegment);
        }
    }

    public ArrayList<ReplacedTextRegion> getRegions() {
        return regions;
    }

    public ArrayList<BasedSequence> getReplacedSegments() {
        return replacedSegments;
    }

    public BasedSequence getReplacedSequence() {
        return SegmentedSequence.of(replacedSegments, original.subSequence(0, 0));
    }

    public int getReplacedLength() {
        return replacedLength;
    }

    public int originalOffset(int replacedIndex) {
        if (regions.isEmpty()) return replacedIndex;
        if (replacedIndex == replacedLength) return original.length();

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

        return originalIndex;
    }
}
