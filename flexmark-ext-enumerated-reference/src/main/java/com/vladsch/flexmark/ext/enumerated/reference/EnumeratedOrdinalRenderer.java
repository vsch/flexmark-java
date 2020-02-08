package com.vladsch.flexmark.ext.enumerated.reference;

public interface EnumeratedOrdinalRenderer {
    /**
     * Start of rendering for all renderings
     *
     * @param renderings renderings which will be rendered
     */
    void startRendering(EnumeratedReferenceRendering[] renderings);

    /**
     * Execute this runnable when empty enum text or link is encountered
     *
     * @param runnable runnable
     */
    void setEnumOrdinalRunnable(Runnable runnable);

    /**
     * Return current enum ordinal runnable, used to save previous state
     *
     * @return current empty enum runnable
     */
    Runnable getEnumOrdinalRunnable();

    /**
     * Render individual reference format
     *
     * @param referenceOrdinal ordinal for the reference
     * @param referenceFormat  reference format or null
     * @param defaultText      default text to use if referenceFormat is null or not being used
     * @param needSeparator    true if need to add separator character after output of referenceOrdinal
     *                         <p>
     *                         Should set current enum ordinal runnable to output the given referenceOrdinal if referenceFormat is not null
     *                         the runnable is saved before this call and restored after so there is no need to save its value.
     *                         <p>
     *                         NOTE: if referenceFormat is null and the current runnable is not null then it should be run
     *                         after output of default text and before output of referenceOrdinal, to make sure that parent compound
     *                         ordinal formats are output.
     */
    void render(int referenceOrdinal, EnumeratedReferenceBlock referenceFormat, String defaultText, boolean needSeparator);

    /**
     * After Rendering is complete
     */
    void endRendering();
}
