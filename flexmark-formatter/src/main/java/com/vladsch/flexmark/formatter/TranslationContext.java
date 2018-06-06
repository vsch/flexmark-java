package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.ast.Node;

public interface TranslationContext {
    /**
     * Get the reason this format rendering is being performed
     * @return RenderPurpose for current rendering
     */
    RenderPurpose getRenderPurpose();

    /**
     * Returns false if special translation functions are no-ops
     *
     * During {@link RenderPurpose#TRANSLATION_SPANS} this is true
     * During {@link RenderPurpose#TRANSLATED_FOR_PARSER} this is true
     * During {@link RenderPurpose#TRANSLATED} this is true
     *
     * @return true if need to call translation related functions
     */
    boolean isTransformingText();

    ///**
    // * Returns true if prefix and indent perform usual duty, false if they should use empty strings
    // *
    // * During {@link RenderPurpose#TRANSLATION_SPANS} this is false
    // * During {@link RenderPurpose#TRANSLATED_FOR_PARSER} this is true
    // * During {@link RenderPurpose#TRANSLATED} this is true
    // *
    // * @return true if need to real prefix/indent operations in output
    // */
    //boolean isPrefixedIndented();

    /**
     * Transform non-translating text
     *
     * During {@link RenderPurpose#TRANSLATION_SPANS} this converts text to non-translating placeholder based on ordinal id
     * During {@link RenderPurpose#TRANSLATED_FOR_PARSER} this returns the non-translating placeholder based on ordinal id
     * During {@link RenderPurpose#TRANSLATED} this returns the original non-translating text for the nonTranslatingText (placeholder)
     *
     * @param prefix              prefix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param nonTranslatingText  non-rendering text of the node (content will depend on translation phase)
     * @param suffix              suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param suffix2             suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @return text to be used in rendering for this phase
     */
    CharSequence transformNonTranslating(final CharSequence prefix, final CharSequence nonTranslatingText, final CharSequence suffix, final CharSequence suffix2);

    /**
     * Transform translating text but which is contextually isolated from the text block in which it is located ie. link reference or image reference
     *
     * During {@link RenderPurpose#TRANSLATION_SPANS} this converts text to non-translating placeholder based on ordinal id and adds it to translation snippets
     * During {@link RenderPurpose#TRANSLATED_FOR_PARSER} this returns the non-translating placeholder based on ordinal id
     * During {@link RenderPurpose#TRANSLATED} this returns the translated text for the translatingText (placeholder)
     *
     * @param prefix              prefix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param translatingText     translating but isolated text of the node (content will depend on translation phase)
     * @param suffix              suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param suffix2             suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @return text to be used in rendering for this phase
     */
    CharSequence transformTranslating(final CharSequence prefix, final CharSequence translatingText, final CharSequence suffix, final CharSequence suffix2);

    /**
     * During {@link RenderPurpose#TRANSLATION_SPANS} this converts anchorRef to ordinal placeholder id
     * During {@link RenderPurpose#TRANSLATED_FOR_PARSER} this returns the ordinal placeholder
     * During {@link RenderPurpose#TRANSLATED} this returns new anchorRef for the AnchorRefTarget original was referring to
     *
     * @param pageRef   url part without the anchor ref to resolve reference
     * @param anchorRef anchor ref
     * @return anchorRef for the phase to be used for rendering
     */
    CharSequence transformAnchorRef(CharSequence pageRef, CharSequence anchorRef);

    /**
     * Separate translation span, cannot be nested. Will generate a paragraph of text which should be translated as one piece
     *
     * During {@link RenderPurpose#TRANSLATION_SPANS} this adds the generated output to translation spans
     * During {@link RenderPurpose#TRANSLATED_FOR_PARSER} output from renderer is suppressed, instead outputs corresponding translated span
     * During {@link RenderPurpose#TRANSLATED} calls render
     *
     */
    void translatingSpan(TranslatingSpanRender render);

    /**
     * Separate translation span which is also a ref target
     * @param target
     * @param render
     */
    void translatingRefTargetSpan(Node target, TranslatingSpanRender render);
}
