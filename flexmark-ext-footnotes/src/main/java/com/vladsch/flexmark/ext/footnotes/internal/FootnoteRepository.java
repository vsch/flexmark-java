package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.internal.util.DataKey;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.NodeRepository;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class FootnoteRepository extends NodeRepository<FootnoteBlock> {

    private ArrayList<FootnoteBlock> referencedFootnoteBlocks = new ArrayList<>();

    public int addFootnoteReference(FootnoteBlock footnoteBlock) {
        if (footnoteBlock.getFootnoteOrdinal() == 0) {
            referencedFootnoteBlocks.add(footnoteBlock);
            footnoteBlock.setFootnoteOrdinal(referencedFootnoteBlocks.size());
        }
        return footnoteBlock.getFootnoteOrdinal();
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
