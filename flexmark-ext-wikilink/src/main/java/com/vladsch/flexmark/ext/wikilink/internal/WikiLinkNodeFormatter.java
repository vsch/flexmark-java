package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiNode;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATED;
import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATION_SPANS;

public class WikiLinkNodeFormatter implements PhasedNodeFormatter {
    final public static HashSet<FormattingPhase> FORMATTING_PHASES = new HashSet<>(Arrays.asList(
            FormattingPhase.COLLECT,
            FormattingPhase.DOCUMENT_TOP
    ));

    private Map<String, String> attributeUniquificationIdMap;
    private WikiLinkOptions options;

    public WikiLinkNodeFormatter(DataHolder options) {

    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeFormattingHandler<>(WikiLink.class, this::render),
                new NodeFormattingHandler<>(WikiImage.class, this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return new HashSet<>(Arrays.asList(
                WikiLink.class,
                WikiImage.class
        ));
    }

    @Nullable
    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return FORMATTING_PHASES;
    }

    @Override
    public void renderDocument(@NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown, @NotNull Document document, @NotNull FormattingPhase phase) {
        attributeUniquificationIdMap = Formatter.ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(context.getTranslationStore());
        options = new WikiLinkOptions(document);
    }

    private void render(WikiLink node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        if (node.isLinkIsFirst()) {
            renderLink(node, context, markdown);
            renderText(node, context, markdown);
        } else {
            renderText(node, context, markdown);
            renderLink(node, context, markdown);
        }
        markdown.append(node.getClosingMarker());
    }

    private void render(WikiImage node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        if (node.isLinkIsFirst()) {
            renderLink(node, context, markdown);
            renderText(node, context, markdown);
        } else {
            renderText(node, context, markdown);
            renderLink(node, context, markdown);
        }
        markdown.append(node.getClosingMarker());
    }

    private void renderText(WikiNode node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.isTransformingText()) {
            if (node.getText().isNotNull()) {
                if (node.isLinkIsFirst()) {
                    markdown.append(node.getTextSeparatorMarker());
                }

                if (context.getFormatterOptions().rightMargin > 0) {
                    // no wrapping of link text
                    markdown.append(node.getText().toMapped(SpaceMapper.toNonBreakSpace));
                } else {
                    context.renderChildren(node);
                }

                if (!node.isLinkIsFirst()) {
                    markdown.append(node.getTextSeparatorMarker());
                }
            }
        } else {
            switch (context.getRenderPurpose()) {
                case TRANSLATION_SPANS:
                case TRANSLATED_SPANS:
                    if (node.isLinkIsFirst()) {
                        markdown.append(WikiNode.SEPARATOR_CHAR);
                    }

                    BasedSequence text = node.getText().isNull() ? node.getPageRef() : node.getText();
                    if (options.allowInlines) {
                        context.renderChildren(node);
                    } else {
                        markdown.appendTranslating(text.unescape());
                    }

                    if (!node.isLinkIsFirst()) {
                        markdown.append(WikiNode.SEPARATOR_CHAR);
                    }
                    break;

                case TRANSLATED:
                    if (node.isLinkIsFirst()) {
                        markdown.append(node.getTextSeparatorMarker());
                    }

                    if (options.allowInlines) {
                        context.renderChildren(node);
                    } else {
                        CharSequence translated = context.transformTranslating(null, node.getText(), null, null);
                        markdown.append(escapePipeAnchors(translated));
                    } 
                    
                    if (!node.isLinkIsFirst()) {
                        markdown.append(node.getTextSeparatorMarker());
                    }
                    break;

                case FORMAT:
                default:
                    throw new IllegalStateException("Unexpected renderer purpose");
            }
        }
    }

    // Need to escape un-escaped \, |, and # 
    private CharSequence escapeUnescapedPipeAnchors(CharSequence chars) {
        boolean isEscaped = false;
        int iMax = chars.length();
        StringBuilder text = new StringBuilder();
        
        for (int i = 0; i < iMax; i++) {
            char c = chars.charAt(i);

            switch (c) {
                case '\\':
                    isEscaped = !isEscaped;
                    break;

                case '|':
                    if (!isEscaped && options.allowPipeEscape) text.append('\\'); 
                    break;

                case '#':
                    if (!isEscaped && options.allowAnchors && options.allowAnchorEscape) text.append('\\'); 
                    break;

                default:
                    isEscaped = false;
                    break;
            }
            
            text.append(c);
        }

        if (isEscaped) {
            // dangling \
            text.append('\\');
        }
            
        return text;
    }

    // Need to escape un-escaped \, |, and # 
    private CharSequence escapePipeAnchors(CharSequence chars) {
        int iMax = chars.length();
        StringBuilder text = new StringBuilder();
        
        for (int i = 0; i < iMax; i++) {
            char c = chars.charAt(i);

            switch (c) {
                case '\\':
                    text.append('\\'); 
                    break;

                case '|':
                    if (options.allowPipeEscape) text.append('\\'); 
                    break;

                case '#':
                    if (options.allowAnchors && options.allowAnchorEscape) text.append('\\'); 
                    break;

                default:
                    break;
            }
            
            text.append(c);
        }

        return text;
    }

    private void renderLink(WikiNode node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.isTransformingText()) {
            if (context.getFormatterOptions().rightMargin > 0) {
                // no wrapping of link text
                markdown.append(node.getLink().toMapped(SpaceMapper.toNonBreakSpace));
            } else {
                markdown.append(node.getLink());
            }
        } else {
            if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                markdown.appendNonTranslating(node.getPageRef());
                markdown.append(node.getAnchorMarker());
                if (node.getAnchorRef().isNotNull()) {
                    CharSequence anchorRef = context.transformAnchorRef(node.getPageRef(), node.getAnchorRef());
                    markdown.append(anchorRef);
                }
            } else {
                CharSequence pageRef = context.transformNonTranslating(null, node.getPageRef(), null, null);
                // NOTE: need to escape pipes and hashes in page refs
                markdown.append(escapeUnescapedPipeAnchors(pageRef));
                markdown.append(node.getAnchorMarker());
                if (node.getAnchorRef().isNotNull()) {
                    CharSequence anchorRef = context.transformAnchorRef(node.getPageRef(), node.getAnchorRef());
                    if (attributeUniquificationIdMap != null && context.getRenderPurpose() == TRANSLATED && context.getMergeContext() != null) {
                        String uniquifiedAnchorRef = String.valueOf(anchorRef);
                        if (pageRef.length() == 0) {
                            uniquifiedAnchorRef = attributeUniquificationIdMap.getOrDefault(uniquifiedAnchorRef, uniquifiedAnchorRef);
                        }
                        markdown.append(uniquifiedAnchorRef);
                    } else {
                        markdown.append(anchorRef);
                    }
                }
            }
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new WikiLinkNodeFormatter(options);
        }
    }
}
