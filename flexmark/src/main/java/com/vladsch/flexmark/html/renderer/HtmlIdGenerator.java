package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;

public interface HtmlIdGenerator {
    HtmlIdGenerator NULL = new HtmlIdGenerator() {
        @Override
        public void generateIds(final Document document) {

        }

        @Override
        public String getId(Node node) {
            return null;
        }

        @Override
        public String getId(CharSequence text) {
            return null;
        }
    };

    void generateIds(final Document document);
    String getId(Node node);
    String getId(CharSequence text);
}
