package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.xwiki.macros.*;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Collections;
import java.util.Map;

@SuppressWarnings({ "WeakerAccess" })
public class XWikiMacrosSample implements MacroVisitor {
    // Sample XWiki processing
    // NOTE: using raw text content of the node means that any nested macros will be taken as is
    // to handle nested macros would require more logic to allow nested macros to provide their content
    // to parent macros

    NodeVisitor visitor = new NodeVisitor(
            MacroVisitorExt.VISIT_HANDLERS(this)
    );

    @Override
    public void visit(Macro node) {
        if (!node.isBlockMacro()) {
            // collect attributes
            Map<String, String> attributes = node.getAttributes();
            String content = node.getMacroContentChars().toString();
            boolean isBlock = false;

            // use content, attributes, isBlock
        }
    }

    @Override
    public void visit(MacroClose node) {

    }

    @Override
    public void visit(MacroBlock node) {
        // collect attributes
        Map<String, String> attributes = node.getAttributes();
        String content = node.getMacroContentChars().toString();
        boolean isBlock = true;

        // use content, attributes, isBlock
    }

    public static void main(String[] args) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Collections.singletonList(MacroExtension.create()));

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        String markdown = "markdown content here";
        Node document = parser.parse(markdown);

        XWikiMacrosSample macroProcessor = new XWikiMacrosSample();
        macroProcessor.visitor.visitChildren(document);
    }
}
