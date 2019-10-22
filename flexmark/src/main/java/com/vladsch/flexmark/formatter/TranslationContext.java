package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Supplier;

public interface TranslationContext {
    @Nullable HtmlIdGenerator getIdGenerator();
    /**
     * Get the reason this format rendering is being performed
     *
     * @return RenderPurpose for current rendering
     */
    @NotNull RenderPurpose getRenderPurpose();

    /**
     * Get MutableDataHolder for storing this translation run values across render purpose phases
     */

    @NotNull MutableDataHolder getTranslationStore();

    /**
     * Returns false if special translation functions are no-ops
     * <p>
     * During {@link RenderPurpose#TRANSLATION_SPANS} this is true
     * During {@link RenderPurpose#TRANSLATED_SPANS} this is true
     * During {@link RenderPurpose#TRANSLATED} this is true
     *
     * @return true if need to call translation related functions
     */
    boolean isTransformingText();

    ///**
    // * Returns true if prefix and indent perform usual duty, false if they should use empty strings
    // *
    // * During {@link RenderPurpose#TRANSLATION_SPANS} this is false
    // * During {@link RenderPurpose#TRANSLATED_SPANS} this is true
    // * During {@link RenderPurpose#TRANSLATED} this is true
    // *
    // * @return true if need to real prefix/indent operations in output
    // */
    //boolean isPrefixedIndented();

    /**
     * Transform non-translating text
     * <p>
     * During {@link RenderPurpose#TRANSLATION_SPANS} this converts text to non-translating placeholder based on ordinal id
     * During {@link RenderPurpose#TRANSLATED_SPANS} this returns the non-translating placeholder based on ordinal id
     * During {@link RenderPurpose#TRANSLATED} this returns the original non-translating text for the nonTranslatingText (placeholder)
     *
     * @param prefix             prefix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param nonTranslatingText non-rendering text of the node (content will depend on translation phase)
     * @param suffix             suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param suffix2            suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @return text to be used in rendering for this phase
     */
    @NotNull CharSequence transformNonTranslating(@Nullable CharSequence prefix, @NotNull CharSequence nonTranslatingText, @Nullable CharSequence suffix, @Nullable CharSequence suffix2);

    /**
     * @param postProcessor id post processor for TRANSLATED purpose
     * @param scope         code to which the post processor applies
     */
    void postProcessNonTranslating(@NotNull Function<String, CharSequence> postProcessor, @NotNull Runnable scope);

    /**
     * @param postProcessor id post processor for TRANSLATED purpose
     * @param scope         code to which the post processor applies
     */
    <T> @NotNull T postProcessNonTranslating(@NotNull Function<String, CharSequence> postProcessor, @NotNull Supplier<T> scope);

    /**
     * @return true if non-translating post processor is set
     */
    boolean isPostProcessingNonTranslating();

    /**
     * Transform translating text but which is contextually isolated from the text block in which it is located ie. link reference or image reference
     * <p>
     * During {@link RenderPurpose#TRANSLATION_SPANS} this converts text to non-translating placeholder based on ordinal id and adds it to translation snippets
     * During {@link RenderPurpose#TRANSLATED_SPANS} this returns the non-translating placeholder based on ordinal id
     * During {@link RenderPurpose#TRANSLATED} this returns the translated text for the translatingText (placeholder)
     *
     * @param prefix          prefix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param translatingText translating but isolated text of the node (content will depend on translation phase)
     * @param suffix          suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @param suffix2         suffix to use on non-translating placeholder so it is interpreted as a proper element during parsing
     * @return text to be used in rendering for this phase
     */
    @NotNull CharSequence transformTranslating(@Nullable CharSequence prefix, @NotNull CharSequence translatingText, @Nullable CharSequence suffix, @Nullable CharSequence suffix2);

    /**
     * During {@link RenderPurpose#TRANSLATION_SPANS} this converts anchorRef to ordinal placeholder id
     * During {@link RenderPurpose#TRANSLATED_SPANS} this returns the ordinal placeholder
     * During {@link RenderPurpose#TRANSLATED} this returns new anchorRef for the AnchorRefTarget original was referring to
     *
     * @param pageRef   url part without the anchor ref to resolve reference
     * @param anchorRef anchor ref
     * @return anchorRef for the phase to be used for rendering
     */
    @NotNull CharSequence transformAnchorRef(@NotNull CharSequence pageRef, @NotNull CharSequence anchorRef);

    /**
     * Separate translation span. Will generate a paragraph of text which should be translated as one piece
     * <p>
     * During {@link RenderPurpose#TRANSLATION_SPANS} this adds the generated output to translation spans
     * During {@link RenderPurpose#TRANSLATED_SPANS} output from renderer is suppressed, instead outputs corresponding translated span
     * During {@link RenderPurpose#TRANSLATED} calls render
     */
    void translatingSpan(@NotNull TranslatingSpanRender render);

    /**
     * Separate non-translation span. Will generate a paragraph of text which will not be translated
     * <p>
     * During {@link RenderPurpose#TRANSLATION_SPANS} this adds the generated output to translation spans
     * During {@link RenderPurpose#TRANSLATED_SPANS} output from renderer is suppressed, instead outputs corresponding translated span
     * During {@link RenderPurpose#TRANSLATED} calls render
     */
    void nonTranslatingSpan(@NotNull TranslatingSpanRender render);

    /**
     * Separate translation span which is also a ref target
     *
     * @param target target node,
     * @param render
     */
    void translatingRefTargetSpan(@Nullable Node target, @NotNull TranslatingSpanRender render);

    /**
     * Temporarily change the format for placeholders
     *
     * @param generator placeholder generator
     * @param render    render which will be used with the custom generator
     */
    void customPlaceholderFormat(@NotNull TranslationPlaceholderGenerator generator, @NotNull TranslatingSpanRender render);
    @Nullable MergeContext getMergeContext();
}
