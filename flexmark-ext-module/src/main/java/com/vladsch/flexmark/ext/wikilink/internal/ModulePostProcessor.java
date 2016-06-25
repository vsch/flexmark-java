package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.Module;
import com.vladsch.flexmark.ext.wikilink.ModuleBlock;
import com.vladsch.flexmark.ext.wikilink.ModuleExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModulePostProcessor implements PostProcessor {
    private Pattern modules = null;
    private HashMap<String, String> moduleMap = null;

    private void initializeNode(Node node) {
        Document document = node.getDocument();
        ModuleRepository repository = document.get(ModuleExtension.MODULES);
        

        if (!repository.isEmpty()) {
            moduleMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            for (String module : repository.keySet()) {
                ModuleBlock moduleBlock = repository.get(module);
                BasedSequence moduleText = moduleBlock.getModule();
                if (!module.isEmpty()) {
                    moduleMap.put(module, moduleText.toString());

                    if (sb.length() > 0) sb.append("|");

                    if (Character.isLetterOrDigit(module.charAt(0))) sb.append("\\b");
                    sb.append("\\Q").append(module).append("\\E");
                    if (Character.isLetterOrDigit(module.charAt(module.length()-1))) sb.append("\\b");
                }
            }

            if (sb.length() > 0) modules = Pattern.compile(sb.toString());
        }
    }

    public Node process(Node node) {
        initializeNode(node);

        if (modules != null) {
            ModuleVisitor visitor = new ModuleVisitor();
            node.accept(visitor);
        }

        finalizeNode(node);
        return node;
    }

    private void finalizeNode(Node node) {
        modules = null;
        moduleMap = null;
    }

    private void linkify(Text node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        Matcher m = modules.matcher(literal);
        Node lastNode = node;
        int lastEscaped = 0;

        while (m.find()) {
            //String found = m.group();
            if (moduleMap.containsKey(m.group(0))) {
                String moduleBlockText = moduleMap.get(m.group(0));

                BasedSequence moduleText = literal.subSequence(m.start(0), m.end(0));
                int startOffset = textMapper.originalOffset(m.start(0));
                int endOffset = textMapper.originalOffset(m.end(0));

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    lastNode = insertNode(new Text(escapedChars), lastNode);
                }

                BasedSequence origModuleText = original.subSequence(startOffset, endOffset);
                Module moduleNode = new Module(origModuleText, moduleBlockText);
                lastNode = insertNode(moduleNode, lastNode);

                lastEscaped = endOffset;
            }
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            insertNode(new Text(escapedChars), lastNode);
        }
        node.unlink();
    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class ModuleVisitor extends AbstractVisitor {
        @Override
        public void visit(Text text) {
            if (!isVisiting(text, DoNotLinkify.class)) {
                linkify(text);
            }
        }
    }
}
