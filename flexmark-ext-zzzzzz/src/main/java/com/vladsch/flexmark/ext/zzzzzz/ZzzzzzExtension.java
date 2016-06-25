package com.vladsch.flexmark.ext.zzzzzz;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.zzzzzz.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataKey;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for zzzzzzs
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link Zzzzzz} nodes.
 * </p>
 */
public class ZzzzzzExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static DataKey<ZzzzzzRepository> ZZZZZZS = new DataKey<>("ZZZZZZS", ZzzzzzRepository::new);
    public final static DataKey<KeepType> ZZZZZZS_KEEP = new DataKey<>("ZZZZZZS_KEEP", KeepType.FIRST);
    final public static DataKey<Boolean> ZZZZZZ_OPTION1 = new DataKey<>("ZZZZZZ_OPTION1", false);
    final public static DataKey<String> ZZZZZZ_OPTION2 = new DataKey<>("ZZZZZZ_OPTION2", "default");
    final public static DataKey<Integer> ZZZZZZ_OPTION3 = new DataKey<>("ZZZZZZ_OPTION3", Integer.MAX_VALUE);

    private ZzzzzzExtension() {
    }

    public static Extension create() {
        return new ZzzzzzExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new ZzzzzzBlockParser.Factory());
        parserBuilder.customDelimiterProcessor(new ZzzzzzDelimiterProcessor());
        parserBuilder.linkRefProcessor(new ZzzzzzLinkRefProcessor(parserBuilder));
        parserBuilder.postProcessor(new ZzzzzzPostProcessor());
        parserBuilder.blockPreProcessorFactory(new ZzzzzzBlockPreProcessorFactory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(ZzzzzzNodeRenderer::new);
    }
}
