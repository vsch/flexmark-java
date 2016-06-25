package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzBlock;
import com.vladsch.flexmark.ext.zzzzzz.ZzzzzzExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZzzzzzPostProcessor implements PostProcessor {
    private Pattern zzzzzzs = null;
    private HashMap<String, String> zzzzzzMap = null;

    private void initializeNode(Node node) {
        Document document = node.getDocument();
        ZzzzzzRepository repository = document.get(ZzzzzzExtension.ZZZZZZS);
        

        if (!repository.isEmpty()) {
            zzzzzzMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            for (String zzzzzz : repository.keySet()) {
                ZzzzzzBlock zzzzzzBlock = repository.get(zzzzzz);
                BasedSequence zzzzzzText = zzzzzzBlock.getZzzzzz();
                if (!zzzzzz.isEmpty()) {
                    zzzzzzMap.put(zzzzzz, zzzzzzText.toString());

                    if (sb.length() > 0) sb.append("|");

                    if (Character.isLetterOrDigit(zzzzzz.charAt(0))) sb.append("\\b");
                    sb.append("\\Q").append(zzzzzz).append("\\E");
                    if (Character.isLetterOrDigit(zzzzzz.charAt(zzzzzz.length()-1))) sb.append("\\b");
                }
            }

            if (sb.length() > 0) zzzzzzs = Pattern.compile(sb.toString());
        }
    }

    public Node process(Node node) {
        initializeNode(node);

        if (zzzzzzs != null) {
            ZzzzzzVisitor visitor = new ZzzzzzVisitor();
            node.accept(visitor);
        }

        finalizeNode(node);
        return node;
    }

    private void finalizeNode(Node node) {
        zzzzzzs = null;
        zzzzzzMap = null;
    }

    private void linkify(Text node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        Matcher m = zzzzzzs.matcher(literal);
        Node lastNode = node;
        int lastEscaped = 0;

        while (m.find()) {
            //String found = m.group();
            if (zzzzzzMap.containsKey(m.group(0))) {
                String zzzzzzBlockText = zzzzzzMap.get(m.group(0));

                BasedSequence zzzzzzText = literal.subSequence(m.start(0), m.end(0));
                int startOffset = textMapper.originalOffset(m.start(0));
                int endOffset = textMapper.originalOffset(m.end(0));

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    lastNode = insertNode(new Text(escapedChars), lastNode);
                }

                BasedSequence origZzzzzzText = original.subSequence(startOffset, endOffset);
                Zzzzzz zzzzzzNode = new Zzzzzz(origZzzzzzText, zzzzzzBlockText);
                lastNode = insertNode(zzzzzzNode, lastNode);

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

    private class ZzzzzzVisitor extends AbstractVisitor {
        @Override
        public void visit(Text text) {
            if (!isVisiting(text, DoNotLinkify.class)) {
                linkify(text);
            }
        }
    }
}
