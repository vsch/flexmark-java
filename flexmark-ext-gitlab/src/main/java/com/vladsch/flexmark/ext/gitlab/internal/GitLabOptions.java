package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;
import com.vladsch.flexmark.util.misc.CharPredicate;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

public class GitLabOptions implements MutableDataSetter {
    final public boolean insParser;
    final public boolean delParser;
    final public boolean inlineMathParser;
    final public boolean blockQuoteParser;
    final public boolean nestedBlockQuotes;
    final public boolean renderBlockMath;
    final public boolean renderBlockMermaid;
    final public boolean renderVideoImages;
    final public boolean renderVideoLink;
    final public String inlineMathClass;
    final public String blockMathClass;
    final public String blockMermaidClass;
    final public String[] mathLanguages;
    final public String[] mermaidLanguages;
    final public String videoImageClass;
    final public String videoImageLinkTextFormat;
    final public String videoImageExtensions;
    final public HashSet<String> videoImageExtensionSet;

    /**
     * @deprecated use {@link com.vladsch.flexmark.html.HtmlRendererOptions#languageDelimiters} instead
     */
    @Deprecated
    final public String blockInfoDelimiters;
    /**
     * @deprecated use {@link com.vladsch.flexmark.html.HtmlRendererOptions#languageDelimiterSet} instead
     */
    @Deprecated
    final public CharPredicate blockInfoDelimiterSet;
    
    public GitLabOptions(DataHolder options) {
        insParser = GitLabExtension.INS_PARSER.get(options);
        delParser = GitLabExtension.DEL_PARSER.get(options);
        inlineMathParser = GitLabExtension.INLINE_MATH_PARSER.get(options);
        blockQuoteParser = GitLabExtension.BLOCK_QUOTE_PARSER.get(options);
        nestedBlockQuotes = GitLabExtension.NESTED_BLOCK_QUOTES.get(options);
        inlineMathClass = GitLabExtension.INLINE_MATH_CLASS.get(options);
        renderBlockMath = GitLabExtension.RENDER_BLOCK_MATH.get(options);
        renderBlockMermaid = GitLabExtension.RENDER_BLOCK_MERMAID.get(options);
        renderVideoImages = GitLabExtension.RENDER_VIDEO_IMAGES.get(options);
        renderVideoLink = GitLabExtension.RENDER_VIDEO_LINK.get(options);
        blockMathClass = GitLabExtension.BLOCK_MATH_CLASS.get(options);
        blockMermaidClass = GitLabExtension.BLOCK_MERMAID_CLASS.get(options);
        blockInfoDelimiters = HtmlRenderer.FENCED_CODE_LANGUAGE_DELIMITERS.get(options);
        blockInfoDelimiterSet = CharPredicate.anyOf(blockInfoDelimiters);
        mathLanguages = GitLabExtension.MATH_LANGUAGES.get(options);
        mermaidLanguages = GitLabExtension.MERMAID_LANGUAGES.get(options);
        videoImageClass = GitLabExtension.VIDEO_IMAGE_CLASS.get(options);
        videoImageLinkTextFormat = GitLabExtension.VIDEO_IMAGE_LINK_TEXT_FORMAT.get(options);
        videoImageExtensions = GitLabExtension.VIDEO_IMAGE_EXTENSIONS.get(options);
        videoImageExtensionSet = new HashSet<>();
        String[] extensions = videoImageExtensions.split(",");
        for (String ext : extensions) {
            String trimmed = ext.trim();
            if (!trimmed.isEmpty()) {
                videoImageExtensionSet.add(trimmed);
            }
        }
    }

    @NotNull
    @Override
    public MutableDataHolder setIn(@NotNull MutableDataHolder dataHolder) {
        dataHolder.set(GitLabExtension.INS_PARSER, insParser);
        dataHolder.set(GitLabExtension.DEL_PARSER, delParser);
        dataHolder.set(GitLabExtension.INLINE_MATH_PARSER, inlineMathParser);
        dataHolder.set(GitLabExtension.BLOCK_QUOTE_PARSER, blockQuoteParser);
        dataHolder.set(GitLabExtension.NESTED_BLOCK_QUOTES, nestedBlockQuotes);
        dataHolder.set(GitLabExtension.INLINE_MATH_CLASS, inlineMathClass);
        dataHolder.set(GitLabExtension.RENDER_BLOCK_MATH, renderBlockMath);
        dataHolder.set(GitLabExtension.RENDER_BLOCK_MERMAID, renderBlockMermaid);
        dataHolder.set(GitLabExtension.RENDER_VIDEO_IMAGES, renderVideoImages);
        dataHolder.set(GitLabExtension.RENDER_VIDEO_LINK, renderVideoLink);
        dataHolder.set(GitLabExtension.BLOCK_MATH_CLASS, blockMathClass);
        dataHolder.set(GitLabExtension.BLOCK_MERMAID_CLASS, blockMermaidClass);
        dataHolder.set(HtmlRenderer.FENCED_CODE_LANGUAGE_DELIMITERS, blockInfoDelimiters);
        dataHolder.set(GitLabExtension.VIDEO_IMAGE_CLASS, videoImageClass);
        dataHolder.set(GitLabExtension.VIDEO_IMAGE_LINK_TEXT_FORMAT, videoImageLinkTextFormat);
        dataHolder.set(GitLabExtension.VIDEO_IMAGE_EXTENSIONS, videoImageExtensions);
        return dataHolder;
    }
}
