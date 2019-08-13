package com.vladsch.flexmark.ext.admonition.internal;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("WeakerAccess")
public class AdmonitionRepository {
    private HashSet<String> referencedAdmonitionQualifiers = new LinkedHashSet<>();

    public Set<String> getReferencedAdmonitionQualifiers() {
        return referencedAdmonitionQualifiers;
    }

    public void addAdmonitionQualifier(String qualifier) {
        referencedAdmonitionQualifiers.add(qualifier);
    }

    public AdmonitionRepository() {

    }
}
