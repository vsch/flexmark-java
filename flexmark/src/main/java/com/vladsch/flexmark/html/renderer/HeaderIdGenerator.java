package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.AnchorRefTarget;
import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.AnchorRefTargetBlockVisitor;
import com.vladsch.flexmark.html.HtmlRenderer;

import java.util.HashMap;

public class HeaderIdGenerator implements HtmlIdGenerator {
    @Override
    public void generateIds(Document document) {
        final HashMap<String, Integer> headerBaseIds = new HashMap<>();
        final boolean resolveDupes = HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES.getFrom(document);
        final String toDashChars = HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS.getFrom(document);
        final boolean noDupedDashes = HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES.getFrom(document);

        new AnchorRefTargetBlockVisitor() {
            @Override
            protected void visit(AnchorRefTarget node) {
                String text = node.getAnchorRefText();

                if (!text.isEmpty()) {
                    String baseRefId = generateId(text, toDashChars, noDupedDashes);

                    if (resolveDupes) {
                        if (headerBaseIds.containsKey(baseRefId)) {
                            int index = headerBaseIds.get(baseRefId);

                            index++;
                            headerBaseIds.put(baseRefId, index);
                            baseRefId += "-" + index;
                        } else {
                            headerBaseIds.put(baseRefId, 0);
                        }
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

    @SuppressWarnings("WeakerAccess")
    public static String generateId(CharSequence headerText, String toDashChars, boolean noDupedDashes) {
        int iMax = headerText.length();
        StringBuilder baseRefId = new StringBuilder(iMax);
        if (toDashChars == null) toDashChars = " -_";

        for (int i = 0; i < iMax; i++) {
            char c = headerText.charAt(i);
            if (Character.isAlphabetic(c)) baseRefId.append(Character.toLowerCase(c));
            else if (Character.isDigit(c)) baseRefId.append(c);
            else if (toDashChars.indexOf(c) != -1 && (!noDupedDashes
                    || ((c == '-' && baseRefId.length() == 0)
                    || baseRefId.length() != 0 && baseRefId.charAt(baseRefId.length() - 1) != '-'))
                    ) baseRefId.append('-');
        }
        return baseRefId.toString();
    }

    public static class Factory implements HeaderIdGeneratorFactory {
        @Override
        public HtmlIdGenerator create(NodeRendererContext context) {
            return new HeaderIdGenerator();
        }
    }
}
