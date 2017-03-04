package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ast.NodeRepository;
import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class FootnoteRepository extends NodeRepository<FootnoteBlock> {
    private ArrayList<FootnoteBlock> referencedFootnoteBlocks = new ArrayList<FootnoteBlock>();

    public void addFootnoteReference(FootnoteBlock footnoteBlock, Footnote footnote) {
        if (!footnoteBlock.isReferenced()) {
            referencedFootnoteBlocks.add(footnoteBlock);
        }

        footnoteBlock.setFirstReferenceOffset(footnote.getStartOffset());
    }

    public void resolveFootnoteOrdinals() {
        // need to sort by first referenced offset then set each to its ordinal position in the array+1
        Collections.sort(referencedFootnoteBlocks, new Comparator<FootnoteBlock>() {
            @Override
            public int compare(FootnoteBlock f1, FootnoteBlock f2) {
                return f1.getFirstReferenceOffset() - f2.getFirstReferenceOffset();
            }
        });

        int ordinal = 0;
        for (FootnoteBlock footnoteBlock : referencedFootnoteBlocks) {
            footnoteBlock.setFootnoteOrdinal(++ordinal);
        }
    }

    public List<FootnoteBlock> getReferencedFootnoteBlocks() {
        return referencedFootnoteBlocks;
    }

    public FootnoteRepository(DataHolder options) {
        super(options);
    }

    @Override
    public DataKey<FootnoteRepository> getDataKey() {
        return FootnoteExtension.FOOTNOTES;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return FootnoteExtension.FOOTNOTES_KEEP;
    }
}
