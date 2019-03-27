package com.vladsch.flexmark.ext.enumerated.reference;

public interface EnumeratedOrdinalRenderer {
    /**
     * Start of rendering for all renderings
     * 
     * @param renderings renderings which will be rendered
     */
    void startRendering(EnumeratedReferenceRendering[] renderings);
    
    /**
     * Render final reference format
     *
     * @param referenceOrdinal ordinal for the reference
     * @param referenceFormat  reference format or null
     * @param defaultText      default text to use if referenceFormat is null or not being used
     * @param needSeparator    true if need to add separator character before adding text or rendering reference format
     */
    void render(final int referenceOrdinal, EnumeratedReferenceBlock referenceFormat, final String defaultText, final boolean needSeparator);

    /**
     * After Rendering is complete 
     */
    void endRendering();
}
