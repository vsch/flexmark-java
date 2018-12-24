package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

import java.util.*;

@SuppressWarnings("WeakerAccess")
public class FootnoteRepository extends NodeRepository<FootnoteBlock> {
    private ArrayList<FootnoteBlock> referencedFootnoteBlocks = new ArrayList<FootnoteBlock>();

    public void addFootnoteReference(FootnoteBlock footnoteBlock, Footnote footnote) {
        if (!footnoteBlock.isReferenced()) {
            referencedFootnoteBlocks.add(footnoteBlock);
        }

        footnoteBlock.setFirstReferenceOffset(footnote.getStartOffset());

        int referenceOrdinal = footnoteBlock.getFootnoteReferences();
        footnoteBlock.setFootnoteReferences(referenceOrdinal + 1);
        footnote.setReferenceOrdinal(referenceOrdinal);
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
        super(FootnoteExtension.FOOTNOTES_KEEP.getFrom(options));
    }

    @Override
    public DataKey<FootnoteRepository> getDataKey() {
        return FootnoteExtension.FOOTNOTES;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return FootnoteExtension.FOOTNOTES_KEEP;
    }

    @Override
    public Set<FootnoteBlock> getReferencedElements(final Node parent) {
        final HashSet<FootnoteBlock> references = new HashSet<>();
        visitNodes(parent, new ValueRunnable<Node>() {
            @Override
            public void run(final Node value) {
                if (value instanceof Footnote) {
                    FootnoteBlock reference = ((Footnote) value).getReferenceNode(FootnoteRepository.this);
                    if (reference != null) {
                        references.add(reference);
                    }
                }
            }
        }, Footnote.class);
        return references;
    }
}
