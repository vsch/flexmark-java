package com.vladsch.flexmark.ext.zzzzzz;

import com.vladsch.flexmark.ext.zzzzzz.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.LinkStatus;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

/**
 * Extension for zzzzzzs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * <p>
 * The parsed zzzzzz text is turned into {@link Zzzzzz} nodes.
 */
public class ZzzzzzExtension implements Parser.ParserExtension
        , HtmlRenderer.HtmlRendererExtension // zzzoptionszzz(NODE_RENDERER, PHASED_NODE_RENDERER)
        , Parser.ReferenceHoldingExtension //zzzoptionszzz(CUSTOM_NODE_REPOSITORY)
{
    final public static DataKey<KeepType> ZZZZZZS_KEEP = new DataKey<>("ZZZZZZS_KEEP", KeepType.FIRST); //zzzoptionszzz(CUSTOM_NODE_REPOSITORY) standard option to allow control over how to handle duplicates
    final public static DataKey<ZzzzzzRepository> ZZZZZZS = new DataKey<>("ZZZZZZS", new ZzzzzzRepository(null), ZzzzzzRepository::new); //zzzoptionszzz(CUSTOM_NODE_REPOSITORY)
    final public static DataKey<Boolean> ZZZZZZ_OPTION1 = new DataKey<>("ZZZZZZ_OPTION1", false); //zzzoptionszzz(CUSTOM_PROPERTIES)
    final public static DataKey<String> ZZZZZZ_OPTION2 = new DataKey<>("ZZZZZZ_OPTION2", "default"); //zzzoptionszzz(CUSTOM_PROPERTIES)
    final public static DataKey<Integer> ZZZZZZ_OPTION3 = new DataKey<>("ZZZZZZ_OPTION3", Integer.MAX_VALUE); //zzzoptionszzz(CUSTOM_PROPERTIES)
    final public static DataKey<String> LOCAL_ONLY_TARGET_CLASS = new DataKey<>("LOCAL_ONLY_TARGET_CLASS", "local-only");//zzzoptionszzz(LINK_RESOLVER, ATTRIBUTE_PROVIDER)
    final public static DataKey<String> MISSING_TARGET_CLASS = new DataKey<>("MISSING_TARGET_CLASS", "absent");//zzzoptionszzz(LINK_RESOLVER, ATTRIBUTE_PROVIDER)
    final public static LinkStatus LOCAL_ONLY = new LinkStatus("LOCAL_ONLY");

    private ZzzzzzExtension() {
    }

    public static ZzzzzzExtension create() {
        return new ZzzzzzExtension();
    }

    @Override
    public void rendererOptions(@NotNull MutableDataHolder options) {

    }

    @Override
    public void parserOptions(MutableDataHolder options) {

    }

    @Override
    public boolean transferReferences(MutableDataHolder document, DataHolder included) {
        if (document.contains(ZZZZZZS) && included.contains(ZZZZZZS)) {
            return Parser.transferReferences(ZZZZZZS.get(document), ZZZZZZS.get(included), ZZZZZZS_KEEP.get(document) == KeepType.FIRST);
        }
        return false;
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        // zzzoptionszzz(REMOVE, BLOCK_PARSER)
        // zzzoptionszzz(BLOCK_PRE_PROCESSOR)
        // zzzoptionszzz(REMOVE, DELIMITER_PROCESSOR)
        // zzzoptionszzz(REMOVE, LINK_REF_PROCESSOR)
        // zzzoptionszzz(NODE_RENDERER)
        // zzzoptionszzz(LINK_RESOLVER)
        // zzzoptionszzz(CUSTOM_PROPERTIES)
        // zzzoptionszzz(PARAGRAPH_PRE_PROCESSOR)
        // zzzoptionszzz(DOCUMENT_POST_PROCESSOR)
        // zzzoptionszzz(NODE_POST_PROCESSOR)
        // zzzoptionszzz(CUSTOM_NODE_REPOSITORY)
        // zzzoptionszzz(CUSTOM_NODE)
        // zzzoptionszzz(CUSTOM_BLOCK_NODE)
        parserBuilder.customBlockParserFactory(new ZzzzzzBlockParser.Factory());//zzzoptionszzz(REMOVE, BLOCK_PARSER)
        parserBuilder.paragraphPreProcessorFactory(ZzzzzzParagraphPreProcessor.Factory());//zzzoptionszzz(REMOVE, PARAGRAPH_PRE_PROCESSOR)
        parserBuilder.blockPreProcessorFactory(new ZzzzzzBlockPreProcessor.Factory());//zzzoptionszzz(REMOVE, BLOCK_PRE_PROCESSOR)
        parserBuilder.customDelimiterProcessor(new ZzzzzzDelimiterProcessor());//zzzoptionszzz(REMOVE, DELIMITER_PROCESSOR)
        parserBuilder.linkRefProcessorFactory(new ZzzzzzLinkRefProcessor.Factory());//zzzoptionszzz(REMOVE, LINK_REF_PROCESSOR)
        parserBuilder.postProcessorFactory(new ZzzzzzNodePostProcessor.Factory());//zzzoptionszzz(REMOVE, NODE_POST_PROCESSOR)
        parserBuilder.postProcessorFactory(new ZzzzzzDocumentPostProcessor.Factory());//zzzoptionszzz(REMOVE, DOCUMENT_POST_PROCESSOR)
        parserBuilder.customInlineParserExtensionFactory(new ZzzzzzInlineParserExtension.Factory());//zzzoptionszzz(REMOVE, INLINE_PARSER_EXTENSION)
    }

    @Override
    public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
        if (htmlRendererBuilder.isRendererType("HTML")) {
            htmlRendererBuilder.nodeRendererFactory(new ZzzzzzNodeRenderer.Factory());// zzzoptionszzz(NODE_RENDERER, PHASED_NODE_RENDERER)
            htmlRendererBuilder.linkResolverFactory(new ZzzzzzLinkResolver.Factory());// zzzoptionszzz(LINK_RESOLVER, NODE_RENDERER, PHASED_NODE_RENDERER)
            htmlRendererBuilder.attributeProviderFactory(new ZzzzzzAttributeProvider.Factory());// zzzoptionszzz(ATTRIBUTE_PROVIDER, NODE_RENDERER, PHASED_NODE_RENDERER)
        } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            htmlRendererBuilder.nodeRendererFactory(new ZzzzzzJiraRenderer.Factory());// zzzoptionszzz(NODE_RENDERER, PHASED_NODE_RENDERER)
            htmlRendererBuilder.linkResolverFactory(new ZzzzzzLinkResolver.Factory());// zzzoptionszzz(LINK_RESOLVER, NODE_RENDERER, PHASED_NODE_RENDERER)
            htmlRendererBuilder.attributeProviderFactory(new ZzzzzzAttributeProvider.Factory());// zzzoptionszzz(ATTRIBUTE_PROVIDER, NODE_RENDERER, PHASED_NODE_RENDERER)
        }
    }
}
