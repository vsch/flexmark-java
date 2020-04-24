package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTag;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagBlock;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.*;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.dependency.DependencyResolver;
import com.vladsch.flexmark.util.dependency.FirstDependent;
import com.vladsch.flexmark.util.misc.FileUtil;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class IncludeNodePostProcessor extends NodePostProcessor {
    final HashMap<JekyllTagBlock, String> includedDocuments = new HashMap<>();
    final HashMap<String, ResolvedLink> resolvedLinks = new HashMap<>();
    final Parser parser;
    final List<LinkResolver> linkResolvers;
    final List<UriContentResolver> contentResolvers;
    final boolean isIncluding = false;
    final Document document;
    final LinkResolverBasicContext context;
    final private boolean embedIncludedContent;
    final private Map<String, String> includedHtml;

    public IncludeNodePostProcessor(@NotNull Document document) {
        this.document = document;
        parser = Parser.builder(document).build();
        context = new LinkResolverBasicContext() {
            @Override
            public @NotNull DataHolder getOptions() {
                return document;
            }

            @Override
            public @NotNull Document getDocument() {
                return document;
            }
        };
        List<LinkResolverFactory> linkResolverFactories = DependencyResolver.resolveFlatDependencies(JekyllTagExtension.LINK_RESOLVER_FACTORIES.get(document), null, null);
        linkResolvers = new ArrayList<>(linkResolverFactories.size());
        for (LinkResolverFactory resolverFactory : linkResolverFactories) {
            linkResolvers.add(resolverFactory.apply(context));
        }

        List<UriContentResolverFactory> resolverFactories = JekyllTagExtension.CONTENT_RESOLVER_FACTORIES.get(document);
        if (resolverFactories.isEmpty()) {
            resolverFactories = Collections.singletonList(new FileUriContentResolver.Factory());
        }
        List<UriContentResolverFactory> contentResolverFactories = DependencyResolver.resolveFlatDependencies(resolverFactories, null, null);
        contentResolvers = new ArrayList<>(contentResolverFactories.size());
        for (UriContentResolverFactory resolverFactory : contentResolverFactories) {
            contentResolvers.add(resolverFactory.apply(context));
        }

        this.embedIncludedContent = JekyllTagExtension.EMBED_INCLUDED_CONTENT.get(document);
        this.includedHtml = JekyllTagExtension.INCLUDED_HTML.get(document);
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
        if (node instanceof JekyllTagBlock && !includedDocuments.containsKey(node)) {
            for (Node tag : node.getChildren()) {
                if (tag instanceof JekyllTag) {
                    JekyllTag jekyllTag = (JekyllTag) tag;
                    //noinspection EqualsBetweenInconvertibleTypes
                    if (embedIncludedContent && jekyllTag.getTag().equals("include")) {
                        // see if can find file
                        BasedSequence parameters = jekyllTag.getParameters();

                        String rawUrl = parameters.unescape();
                        String fileContent = null;

                        if (includedHtml != null && includedHtml.containsKey(rawUrl)) {
                            fileContent = includedHtml.get(rawUrl);
                        } else {
                            ResolvedLink resolvedLink = resolvedLinks.get(rawUrl);

                            if (resolvedLink == null) {
                                resolvedLink = new ResolvedLink(LinkType.LINK, rawUrl);
                                for (LinkResolver linkResolver : linkResolvers) {
                                    resolvedLink = linkResolver.resolveLink(node, context, resolvedLink);
                                    if (resolvedLink.getStatus() != LinkStatus.UNKNOWN) break;
                                }

                                resolvedLinks.put(rawUrl, resolvedLink);
                            }

                            if (resolvedLink.getStatus() == LinkStatus.VALID) {
                                ResolvedContent resolvedContent = new ResolvedContent(resolvedLink, LinkStatus.UNKNOWN, null);
                                for (UriContentResolver contentResolver : contentResolvers) {
                                    resolvedContent = contentResolver.resolveContent(node, context, resolvedContent);
                                    if (resolvedContent.getStatus() != LinkStatus.UNKNOWN) break;
                                }

                                if (resolvedContent.getStatus() == LinkStatus.VALID) {
                                    try {
                                        fileContent = new String(resolvedContent.getContent(), "UTF-8");
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        if (fileContent != null && !fileContent.isEmpty()) {
                            includedDocuments.put((JekyllTagBlock) node, fileContent);

                            Document includedDoc = parser.parse(fileContent);
                            parser.transferReferences(document, includedDoc, null);

                            if (includedDoc.contains(Parser.REFERENCES)) {
                                // NOTE: if included doc has reference definitions then we need to re-evaluate ones which are missing
                                document.set(HtmlRenderer.RECHECK_UNDEFINED_REFERENCES, true);
                            }

                            // insert children of included documents after jekyll tag node
                            Node child = includedDoc.getFirstChild();
                            while (child != null) {
                                Node next = child.getNext();
                                node.appendChild(child);
                                state.nodeAddedWithDescendants(child);

                                child = next;
                            }
                        }
                    }
                }
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            addNodes(JekyllTagBlock.class);
        }

        @Override
        public @Nullable Set<Class<?>> getBeforeDependents() {
            // NOTE: add this as the first node post processor
            return Collections.singleton(FirstDependent.class);
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new IncludeNodePostProcessor(document);
        }
    }
}
