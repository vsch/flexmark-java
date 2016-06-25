package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.wikilink.internal.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.internal.util.DataKey;
import com.vladsch.flexmark.internal.util.KeepType;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for modules
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed emoji shortcuts text regions are turned into {@link Module} nodes.
 * </p>
 */
public class ModuleExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    final public static DataKey<ModuleRepository> MODULES = new DataKey<>("MODULES", ModuleRepository::new);
    public final static DataKey<KeepType> MODULES_KEEP = new DataKey<>("MODULES_KEEP", KeepType.FIRST);
    final public static DataKey<Boolean> MODULE_OPTION1 = new DataKey<>("MODULE_OPTION1", false);
    final public static DataKey<String> MODULE_OPTION2 = new DataKey<>("MODULE_OPTION2", "default");
    final public static DataKey<Integer> MODULE_OPTION3 = new DataKey<>("MODULE_OPTION3", Integer.MAX_VALUE);

    private ModuleExtension() {
    }

    public static Extension create() {
        return new ModuleExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new ModuleBlockParser.Factory());
        parserBuilder.customDelimiterProcessor(new ModuleDelimiterProcessor());
        parserBuilder.linkRefProcessor(new ModuleLinkRefProcessor(parserBuilder));
        parserBuilder.postProcessor(new ModulePostProcessor());
        parserBuilder.blockPreProcessorFactory(new ModuleBlockPreProcessorFactory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(ModuleNodeRenderer::new);
    }
}
