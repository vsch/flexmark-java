package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.internal.util.ModificationBehavior;
import com.vladsch.flexmark.internal.util.NodeRepository;
import com.vladsch.flexmark.internal.util.PropertyKey;
import com.vladsch.flexmark.internal.util.ValueFactory;

public class AbbreviationRepository extends NodeRepository<AbbreviationBlock> {
    public final static AbbreviationRepository NULL = new AbbreviationRepository(ModificationBehavior.LOCKED);
    public final static PropertyKey<AbbreviationRepository> PROPERTY_KEY = new PropertyKey<>("ABBREVIATIONS", NULL, new ValueFactory<AbbreviationRepository>() {
        @Override
        public AbbreviationRepository value() {
            return new AbbreviationRepository(ModificationBehavior.DEFAULT);
        }
    });

    public AbbreviationRepository(ModificationBehavior modifyBehavior) {
        super(modifyBehavior);
    }

    @Override
    public PropertyKey<AbbreviationRepository> getPropertyKey() {
        return PROPERTY_KEY;
    }
}
