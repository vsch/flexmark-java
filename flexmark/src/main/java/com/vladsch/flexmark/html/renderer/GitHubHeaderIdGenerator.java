package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.internal.util.ast.AnchorRefTargetBlockVisitor;
import com.vladsch.flexmark.node.AnchorRefTarget;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.HashMap;

public class GitHubHeaderIdGenerator implements HtmlIdGenerator {
    @Override
    public void generateIds(Document document) {
        HashMap<String, Integer> headerBaseIds = new HashMap<>();
        new AnchorRefTargetBlockVisitor() {
            @Override
            public void visit(AnchorRefTarget node) {
                String text = node.getAnchorRefText();
                
                if (!text.isEmpty()) {
                    String baseRefId = generateId(text);

                    if (headerBaseIds.containsKey(baseRefId)) {
                        int index = headerBaseIds.get(baseRefId);

                        index++;
                        headerBaseIds.put(baseRefId, index);
                        baseRefId += "-" + index;
                    } else {
                        headerBaseIds.put(baseRefId, 0);
                    }

                    node.setAnchorRefId(baseRefId);
                }
            }
        }.visit(document);
    }

    @Override
    public String getId(Node node) {
        return node instanceof AnchorRefTarget ? ((AnchorRefTarget) node).getAnchorRefId() : null;
    }

    public static String generateId(CharSequence headerText) {
        int iMax = headerText.length();
        StringBuilder baseRefId = new StringBuilder(iMax);

        for (int i = 0; i < iMax; i++) {
            char c = headerText.charAt(i);
            switch (c) {
                case ' ':
                    baseRefId.append('-');
                    break;
                case '-':
                    baseRefId.append('-');
                    break;
                case '_':
                    baseRefId.append('_');
                    break;

                default:
                    if (Character.isAlphabetic(c)) baseRefId.append(Character.toLowerCase(c));
                    else if (Character.isDigit(c)) baseRefId.append(c);
                    break;
            }
        }
        return baseRefId.toString();
    }

    public static class Factory implements HeaderIdGeneratorFactory {
        @Override
        public HtmlIdGenerator create(NodeRendererContext context) {
            return new GitHubHeaderIdGenerator();
        }
    }
}
