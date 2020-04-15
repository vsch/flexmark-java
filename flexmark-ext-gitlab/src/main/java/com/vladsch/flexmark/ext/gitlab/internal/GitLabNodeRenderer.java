package com.vladsch.flexmark.ext.gitlab.internal;

import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.ext.gitlab.GitLabBlockQuote;
import com.vladsch.flexmark.ext.gitlab.GitLabDel;
import com.vladsch.flexmark.ext.gitlab.GitLabInlineMath;
import com.vladsch.flexmark.ext.gitlab.GitLabIns;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRendererOptions;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GitLabNodeRenderer implements NodeRenderer
        // , PhasedNodeRenderer
{
    final public static AttributablePart VIDEO = new AttributablePart("VIDEO");
    final public static AttributablePart VIDEO_LINK = new AttributablePart("VIDEO_LINK");

    final GitLabOptions options;
    final private boolean codeContentBlock;
    final private ReferenceRepository referenceRepository;
    final private boolean recheckUndefinedReferences;

    public GitLabNodeRenderer(DataHolder options) {
        this.options = new GitLabOptions(options);
        this.codeContentBlock = Parser.FENCED_CODE_CONTENT_BLOCK.get(options);
        this.referenceRepository = Parser.REFERENCES.get(options);
        this.recheckUndefinedReferences = HtmlRenderer.RECHECK_UNDEFINED_REFERENCES.get(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        Set<NodeRenderingHandler<?>> set = new HashSet<>();
        // @formatter:off
        set.add(new NodeRenderingHandler<>(GitLabIns.class, GitLabNodeRenderer.this::render));
        set.add(new NodeRenderingHandler<>(GitLabDel.class, GitLabNodeRenderer.this::render));
        set.add(new NodeRenderingHandler<>(GitLabInlineMath.class, GitLabNodeRenderer.this::render));
        set.add(new NodeRenderingHandler<>(GitLabBlockQuote.class, GitLabNodeRenderer.this::render));// ,// zzzoptionszzz(CUSTOM_NODE)
        if (options.renderBlockMath || options.renderBlockMermaid) {
            set.add(new NodeRenderingHandler<>(FencedCodeBlock.class, GitLabNodeRenderer.this::render));// ,// zzzoptionszzz(CUSTOM_NODE)
        }
        if (options.renderVideoImages) {
            set.add(new NodeRenderingHandler<>(Image.class, GitLabNodeRenderer.this::render));// ,// zzzoptionszzz(CUSTOM_NODE)
            set.add(new NodeRenderingHandler<>(ImageRef.class, GitLabNodeRenderer.this::render));// ,// zzzoptionszzz(CUSTOM_NODE)
        }
        // @formatter:on
        return set;
    }

    private void render(GitLabIns node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("ins", false, false, () -> context.renderChildren(node));
    }

    private void render(GitLabDel node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tag("del", false, false, () -> context.renderChildren(node));
    }

    private void render(GitLabInlineMath node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().attr(Attribute.CLASS_ATTR, options.inlineMathClass).withAttr().tag("span");
        html.text(node.getText());
        html.tag("/span");
    }

    private void render(GitLabBlockQuote node, NodeRendererContext context, HtmlWriter html) {
        html.withAttr().tagLineIndent("blockquote", () -> context.renderChildren(node));
    }

    private void render(FencedCodeBlock node, NodeRendererContext context, HtmlWriter html) {
        HtmlRendererOptions htmlOptions = context.getHtmlOptions();
        BasedSequence language = node.getInfoDelimitedByAny(htmlOptions.languageDelimiterSet);

        if (this.options.renderBlockMath && language.isIn(options.mathLanguages)) {
            html.line();
            html.srcPosWithTrailingEOL(node.getChars()).attr(Attribute.CLASS_ATTR, this.options.blockMathClass).withAttr().tag("div").line().openPre();
            if (codeContentBlock) {
                context.renderChildren(node);
            } else {
                html.text(node.getContentChars().normalizeEOL());
            }
            html.closePre().tag("/div");

            html.lineIf(htmlOptions.htmlBlockCloseTagEol);
        } else if (this.options.renderBlockMermaid && language.isIn(options.mermaidLanguages)) {
            html.line();
            html.srcPosWithTrailingEOL(node.getChars()).attr(Attribute.CLASS_ATTR, this.options.blockMermaidClass).withAttr().tag("div").line().openPre();
            if (codeContentBlock) {
                context.renderChildren(node);
            } else {
                html.text(node.getContentChars().normalizeEOL());
            }
            html.closePre().tag("/div");

            html.lineIf(htmlOptions.htmlBlockCloseTagEol);
        } else {
            context.delegateRender();
        }
    }

    private boolean renderVideoImage(Node srcNode, String url, String altText, Attributes attributes, HtmlWriter html) {
        String bareUrl = url;
        int pos = url.indexOf('?');
        if (pos != -1) {
            bareUrl = url.substring(0, pos);
        }

        pos = bareUrl.lastIndexOf('.');
        if (pos != -1) {
            String extension = bareUrl.substring(pos + 1);
            if (options.videoImageExtensionSet.contains(extension)) {
                //<div class="video-container">
                //<video src="video.mp4" width="400" controls="true"></video>
                //<p><a href="video.mp4" target="_blank" rel="noopener noreferrer" title="Download 'Sample Video'">Sample Video</a></p>
                //</div>
                html.attr(Attribute.CLASS_ATTR, options.videoImageClass).attr(attributes).withAttr().tagLineIndent("div", () -> {
                    // need to take attributes for reference definition, then overlay them with ours
                    html.srcPos(srcNode.getChars())
                            .attr("src", url)
                            .attr("width", "400")
                            .attr("controls", "true")
                            .withAttr(VIDEO)
                            .tag("video")
                            .tag("/video")
                            .line();

                    if (options.renderVideoLink) {
                        html.tag("p")
                                .attr("href", url)
                                .attr("target", "_blank")
                                .attr("rel", "noopener noreferrer")
                                .attr("title", String.format(options.videoImageLinkTextFormat, altText))
                                .withAttr(VIDEO_LINK)
                                .tag("a")
                                .text(altText)
                                .tag("/a")
                                .tag("/p")
                                .line();
                    }
                });

                return true;
            }
        }
        return false;
    }

    private void render(Image node, NodeRendererContext context, HtmlWriter html) {
        if (!(context.isDoNotRenderLinks() || CoreNodeRenderer.isSuppressedLinkPrefix(node.getUrl(), context))) {
            String altText = new TextCollectingVisitor().collectAndGetText(node);
            ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, node.getUrl().unescape(), null, null);
            String url = resolvedLink.getUrl();

            if (node.getUrlContent().isEmpty()) {
                Attributes attributes = resolvedLink.getNonNullAttributes();

                // need to take attributes for reference definition, then overlay them with ours
                attributes = context.extendRenderingNodeAttributes(node, AttributablePart.NODE, attributes);

                if (renderVideoImage(node, url, altText, attributes, html)) {
                    return;
                }
            }

            context.delegateRender();
        }
    }

    private void render(ImageRef node, NodeRendererContext context, HtmlWriter html) {
        ResolvedLink resolvedLink = null;
        boolean isSuppressed = false;

        if (!node.isDefined() && recheckUndefinedReferences) {
            if (node.getReferenceNode(referenceRepository) != null) {
                node.setDefined(true);
            }
        }

        Reference reference = null;

        if (node.isDefined()) {
            reference = node.getReferenceNode(referenceRepository);
            String url = reference.getUrl().unescape();
            isSuppressed = CoreNodeRenderer.isSuppressedLinkPrefix(url, context);

            resolvedLink = context.resolveLink(LinkType.IMAGE, url, null, null);
            if (reference.getTitle().isNotNull()) {
                resolvedLink.getNonNullAttributes().replaceValue(Attribute.TITLE_ATTR, reference.getTitle().unescape());
            } else {
                resolvedLink.getNonNullAttributes().remove(Attribute.TITLE_ATTR);
            }
        } else {
            // see if have reference resolver and this is resolved
            String normalizeRef = referenceRepository.normalizeKey(node.getReference());
            resolvedLink = context.resolveLink(LinkType.IMAGE_REF, normalizeRef, null, null);
            if (resolvedLink.getStatus() == LinkStatus.UNKNOWN) {
                resolvedLink = null;
            }
        }

        if (resolvedLink != null) {
            if (!(context.isDoNotRenderLinks() || isSuppressed)) {
                String altText = new TextCollectingVisitor().collectAndGetText(node);
                String url = resolvedLink.getUrl();
                Attributes attributes = resolvedLink.getNonNullAttributes();
                if (renderVideoImage(node, url, altText, attributes, html)) {
                    return;
                }
            }
        }

        context.delegateRender();
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new GitLabNodeRenderer(options);
        }
    }
}
