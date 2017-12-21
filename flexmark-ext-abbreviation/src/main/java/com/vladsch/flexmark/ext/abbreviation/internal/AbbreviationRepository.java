package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ast.NodeRepository;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;

@SuppressWarnings("WeakerAccess")
public class AbbreviationRepository extends NodeRepository<AbbreviationBlock> {

    public AbbreviationRepository(DataHolder options) {
        super(AbbreviationExtension.ABBREVIATIONS_KEEP.getFrom(options));
    }

    @Override
    public DataKey<AbbreviationRepository> getDataKey() {
        return AbbreviationExtension.ABBREVIATIONS;
    }

    @Override
    public DataKey<KeepType> getKeepDataKey() {
        return AbbreviationExtension.ABBREVIATIONS_KEEP;
    }
}
