package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.internal.util.ModifyBehavior;
import com.vladsch.flexmark.internal.util.NodeRepository;
import com.vladsch.flexmark.internal.util.PropertyKey;
import com.vladsch.flexmark.internal.util.ValueFactory;

public class AbbreviationRepository extends NodeRepository<AbbreviationBlock> {
    public final static AbbreviationRepository NULL = new AbbreviationRepository(ModifyBehavior.LOCKED);
    public final static PropertyKey<AbbreviationRepository> PROPERTY_KEY = new PropertyKey<>("ABBREVIATIONS", NULL, new ValueFactory<AbbreviationRepository>() {
        @Override
        public AbbreviationRepository value() {
            return new AbbreviationRepository(ModifyBehavior.DEFAULT);
        }
    });

    public AbbreviationRepository(ModifyBehavior modifyBehavior) {
        super(modifyBehavior);
    }

    @Override
    public PropertyKey<AbbreviationRepository> getPropertyKey() {
        return PROPERTY_KEY;
    }
}
