package com.vladsch.flexmark.ext.zzzzzz;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.zzzzzz.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.internal.util.options.DataKey;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for zzzzzzs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed zzzzzz text is turned into {@link Zzzzzz} nodes.
 * </p>
 */
public class ZzzzzzExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static DataKey<ZzzzzzRepository> ZZZZZZS = new DataKey<>("ZZZZZZS", ZzzzzzRepository::new); //zzzoptionszzz(CUSTOM_NODE_REPOSITORY)
    final public static DataKey<KeepType> ZZZZZZS_KEEP = new DataKey<>("ZZZZZZS_KEEP", KeepType.FIRST); //zzzoptionszzz(CUSTOM_NODE_REPOSITORY) standard option to allow control over how to handle duplicates 
    final public static DataKey<Boolean> ZZZZZZ_OPTION1 = new DataKey<>("ZZZZZZ_OPTION1", false); //zzzoptionszzz(CUSTOM_PROPERTIES)
    final public static DataKey<String> ZZZZZZ_OPTION2 = new DataKey<>("ZZZZZZ_OPTION2", "default"); //zzzoptionszzz(CUSTOM_PROPERTIES)
    final public static DataKey<Integer> ZZZZZZ_OPTION3 = new DataKey<>("ZZZZZZ_OPTION3", Integer.MAX_VALUE); //zzzoptionszzz(CUSTOM_PROPERTIES)

    private ZzzzzzExtension() {
    }

    public static Extension create() {
        return new ZzzzzzExtension();
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
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(ZzzzzzNodeRenderer::new);// zzzoptionszzz(NODE_RENDERER, PHASED_NODE_RENDERER)
        rendererBuilder.linkResolverFactory(new ZzzzzzLinkResolver.Factory());// zzzoptionszzz(LINK_RESOLVER)
    }
}
