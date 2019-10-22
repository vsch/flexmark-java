package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSetter;

import java.util.HashSet;

public class GitLabOptions implements MutableDataSetter {
    public final boolean insParser;
    public final boolean delParser;
    public final boolean inlineMathParser;
    public final boolean blockQuoteParser;
    public final boolean nestedBlockQuotes;
    public final boolean renderBlockMath;
    public final boolean renderBlockMermaid;
    public final boolean renderVideoImages;
    public final boolean renderVideoLink;
    public final String inlineMathClass;
    public final String blockMathClass;
    public final String blockMermaidClass;
    public final String blockInfoDelimiters;
    public final String videoImageClass;
    public final String videoImageLinkTextFormat;
    public final String videoImageExtensions;
    public final HashSet<String> videoImageExtensionSet;

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
        blockInfoDelimiters = GitLabExtension.BLOCK_INFO_DELIMITERS.get(options);
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

    @Override
    public MutableDataHolder setIn(MutableDataHolder dataHolder) {
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
        dataHolder.set(GitLabExtension.BLOCK_INFO_DELIMITERS, blockInfoDelimiters);
        dataHolder.set(GitLabExtension.VIDEO_IMAGE_CLASS, videoImageClass);
        dataHolder.set(GitLabExtension.VIDEO_IMAGE_LINK_TEXT_FORMAT, videoImageLinkTextFormat);
        dataHolder.set(GitLabExtension.VIDEO_IMAGE_EXTENSIONS, videoImageExtensions);
        return dataHolder;
    }
}
