package com.vladsch.flexmark.internal.util;

import java.util.ArrayList;

public class ReplacedTextMapper {
    final private BasedSequence original;
    private ArrayList<ReplacedTextRegion> regions = new ArrayList<>();
    private ArrayList<BasedSequence> replacedSegments = new ArrayList<>();
    private int replacedLength = 0;

    public ReplacedTextMapper(BasedSequence original) {
        this.original = original;
    }

    public void addReplacedText(BasedSequence originalSegment, BasedSequence replacedSequence) {
        regions.add(new ReplacedTextRegion(originalSegment.getSourceRange(), new SourceRange(replacedLength, replacedLength + replacedSequence.length())));
        replacedLength += replacedSequence.length();
        replacedSegments.add(replacedSequence);
    }

    public void addOriginalText(BasedSequence originalSegment) {
        if (originalSegment.length() > 0) {
            regions.add(new ReplacedTextRegion(originalSegment.getSourceRange(), new SourceRange(replacedLength, replacedLength + originalSegment.length())));
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
        return SegmentedSequence.of(replacedSegments, SubSequence.NULL);
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
                originalIndex = region.getOriginal().startOffset + replacedIndex - region.getReplaced().startOffset;
                if (originalIndex > region.getOriginal().getEndOffset()) {
                    originalIndex = region.getOriginal().getEndOffset();
                }

                originalIndex -= original.getStartOffset();
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
