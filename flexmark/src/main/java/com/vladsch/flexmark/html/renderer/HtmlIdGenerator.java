package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;

public interface HtmlIdGenerator {
    HtmlIdGenerator NULL = new HtmlIdGenerator() {
        @Override
        public void generateIds(Document document) {
            
        }

        @Override
        public String getId(Node node) {
            return null;
        }
    };
    
    void generateIds(Document document);
    String getId(Node node);
}
