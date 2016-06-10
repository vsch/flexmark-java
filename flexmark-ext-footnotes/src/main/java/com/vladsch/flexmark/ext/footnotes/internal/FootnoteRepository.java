package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.internal.util.ModifyBehavior;
import com.vladsch.flexmark.internal.util.NodeRepository;
import com.vladsch.flexmark.internal.util.PropertyKey;
import com.vladsch.flexmark.internal.util.ValueFactory;

import java.util.ArrayList;
import java.util.List;

public class FootnoteRepository extends NodeRepository<FootnoteBlock> {
    public final static FootnoteRepository NULL = new FootnoteRepository(ModifyBehavior.LOCKED);
    public final static PropertyKey<FootnoteRepository> PROPERTY_KEY = new PropertyKey<>("FOOTNOTES", NULL, new ValueFactory<FootnoteRepository>() {
        @Override
        public FootnoteRepository value() {
            return new FootnoteRepository(ModifyBehavior.DEFAULT);
        }
    });

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

    public FootnoteRepository(ModifyBehavior modifyBehavior) {
        super(modifyBehavior);
    }

    @Override
    public PropertyKey<FootnoteRepository> getPropertyKey() {
        return PROPERTY_KEY;
    }
}
