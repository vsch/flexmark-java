package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.formatter.MergeContext;
import com.vladsch.flexmark.formatter.MergeContextConsumer;
import com.vladsch.flexmark.formatter.TranslationContext;
import com.vladsch.flexmark.formatter.TranslationHandler;
import com.vladsch.flexmark.util.ast.Document;
import java.util.HashMap;
import java.util.Map;

public class MergeContextImpl implements MergeContext {
  private Document[] myDocuments;
  private TranslationHandler[] myTranslationHandlers;
  private final Map<TranslationContext, Document> myTranslationHandlerDocumentMap;

  public MergeContextImpl(Document[] documents, TranslationHandler[] translationHandlers) {
    myDocuments = documents;
    myTranslationHandlers = translationHandlers;
    myTranslationHandlerDocumentMap = new HashMap<>();
    updateDocumentMap();
    for (TranslationHandler handler : myTranslationHandlers) {
      handler.setMergeContext(this);
    }
  }

  private void updateDocumentMap() {
    int iMax = myDocuments.length;
    for (int i = 0; i < iMax; i++) {
      myTranslationHandlerDocumentMap.put(myTranslationHandlers[i], myDocuments[i]);
    }
  }

  public Document[] getDocuments() {
    return myDocuments;
  }

  public void setDocuments(Document[] documents) {
    myDocuments = documents;
    updateDocumentMap();
  }

  public TranslationHandler[] getTranslationHandlers() {
    return myTranslationHandlers;
  }

  @Override
  public Document getDocument(TranslationContext context) {
    return myTranslationHandlerDocumentMap.get(context);
  }

  @Override
  public void forEachPrecedingDocument(Document document, MergeContextConsumer consumer) {
    int iMax = myDocuments.length;
    for (int i = 0; i < iMax; i++) {
      if (myDocuments[i] == document) {
        break;
      }
      consumer.accept(myTranslationHandlers[i], myDocuments[i], i);
    }
  }
}
