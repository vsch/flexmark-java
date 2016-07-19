
package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterBlockParser;
import com.vladsch.flexmark.ext.jekyll.front.matter.internal.JekyllFrontMatterNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;

/**
 * Extension for jekyll_front_matters
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link com.vladsch.flexmark.parser.Parser.Builder#extensions(Iterable)},
 * {@link com.vladsch.flexmark.html.HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed jekyll_front_matter text is turned into {@link JekyllFrontMatterBlock} nodes.
 * </p>
 */
public class JekyllFrontMatterExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    // final public static DataKey<JekyllFrontMatterRepository> JEKYLL_FRONT_MATTERS = new DataKey<>("JEKYLL_FRONT_MATTERS", JekyllFrontMatterRepository::new); 
    // final public static DataKey<KeepType> JEKYLL_FRONT_MATTERS_KEEP = new DataKey<>("JEKYLL_FRONT_MATTERS_KEEP", KeepType.FIRST); // standard option to allow control over how to handle duplicates 
    //final public static DataKey<Boolean> ENABLE_JEKYLL_FRONT_MATTER = new DataKey<>("ENABLE_JEKYLL_FRONT_MATTER", true); 
    //final public static DataKey<Boolean> ENABLE_FLEXMARK_FRONT_MATTER = new DataKey<>("ENABLE_FLEXMARK_FRONT_MATTER", true); 
    //final public static DataKey<String> JEKYLL_FRONT_MATTER_OPTION2 = new DataKey<>("JEKYLL_FRONT_MATTER_OPTION2", "default"); 
    //final public static DataKey<Integer> JEKYLL_FRONT_MATTER_OPTION3 = new DataKey<>("JEKYLL_FRONT_MATTER_OPTION3", Integer.MAX_VALUE); 

    private JekyllFrontMatterExtension() {
    }

    public static Extension create() {
        return new JekyllFrontMatterExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customBlockParserFactory(new JekyllFrontMatterBlockParser.Factory());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(JekyllFrontMatterNodeRenderer::new);
        // rendererBuilder.linkResolverFactory(new JekyllFrontMatterLinkResolver.Factory());
    }
}
