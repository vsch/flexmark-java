package com.vladsch.flexmark.formatter.internal;

import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATED_SPANS;
import static java.lang.Character.isWhitespace;

import com.vladsch.flexmark.ast.AnchorRefTarget;
import com.vladsch.flexmark.formatter.FormatterOptions;
import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.formatter.MergeContext;
import com.vladsch.flexmark.formatter.NodeFormatterContext;
import com.vladsch.flexmark.formatter.RenderPurpose;
import com.vladsch.flexmark.formatter.TranslatingSpanRender;
import com.vladsch.flexmark.formatter.TranslationHandler;
import com.vladsch.flexmark.formatter.TranslationPlaceholderGenerator;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.html.renderer.HtmlIdGeneratorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

public class TranslationHandlerImpl implements TranslationHandler {
  private final FormatterOptions myFormatterOptions;
  private final Map<String, String>
      myNonTranslatingTexts; // map placeholder to non-translating text replaced before translation
  // so it can be replaced after translation
  private final Map<String, String>
      myAnchorTexts; // map anchor id to non-translating text replaced before translation so it can
  // be replaced after translation
  private final Map<String, String>
      myTranslatingTexts; // map placeholder to translating original text which is to be translated
  // separately from its context and is replaced with placeholder for main
  // context translation
  private final Map<String, String>
      myTranslatedTexts; // map placeholder to translated text which is to be translated separately
  // from its context and was replaced with placeholder for main context
  // translation
  private final ArrayList<String>
      myTranslatingPlaceholders; // list of placeholders to index in translating and translated
  // texts
  private final List<String> myTranslatingSpans;
  private final List<String> myNonTranslatingSpans;
  private final ArrayList<String> myTranslatedSpans;
  private final HtmlIdGeneratorFactory myIdGeneratorFactory;
  private final Pattern myPlaceHolderMarkerPattern;
  private final MutableDataSet myTranslationStore;

  private final Map<String, Integer> myOriginalRefTargets; // map ref target id to translation index
  private final Map<Integer, String>
      myTranslatedRefTargets; // map translation index to translated ref target id
  private int myPlaceholderId = 0;
  private int myAnchorId = 0;
  private int myTranslatingSpanId = 0;
  private int myNonTranslatingSpanId = 0;
  private RenderPurpose myRenderPurpose;
  private MarkdownWriter myWriter;
  private HtmlIdGenerator myIdGenerator;
  private TranslationPlaceholderGenerator myPlaceholderGenerator;
  private Function<String, CharSequence> myNonTranslatingPostProcessor = null;
  private MergeContext myMergeContext = null;

  public TranslationHandlerImpl(DataHolder options, HtmlIdGeneratorFactory idGeneratorFactory) {
    myFormatterOptions = new FormatterOptions(options);
    myIdGeneratorFactory = idGeneratorFactory;
    myNonTranslatingTexts = new HashMap<>();
    myAnchorTexts = new HashMap<>();
    myTranslatingTexts = new HashMap<>();
    myTranslatedTexts = new HashMap<>();
    myTranslatedRefTargets = new HashMap<>();
    myOriginalRefTargets = new HashMap<>();
    myTranslatingSpans = new ArrayList<>();
    myTranslatedSpans = new ArrayList<>();
    myTranslatingPlaceholders = new ArrayList<>();
    myNonTranslatingSpans = new ArrayList<>();
    myPlaceHolderMarkerPattern = Pattern.compile(myFormatterOptions.translationExcludePattern);
    myTranslationStore = new MutableDataSet();
  }

  @Override
  public MergeContext getMergeContext() {
    return myMergeContext;
  }

  @Override
  public void setMergeContext(MergeContext context) {
    myMergeContext = context;
  }

  @Override
  public MutableDataSet getTranslationStore() {
    return myTranslationStore;
  }

  @Override
  public HtmlIdGenerator getIdGenerator() {
    return myIdGenerator;
  }

  @Override
  public void beginRendering(
      Document node, NodeFormatterContext context, MarkdownWriter appendable) {
    // collect anchor ref ids
    myWriter = appendable;
    myIdGenerator = myIdGeneratorFactory.create();
    myIdGenerator.generateIds(node);
  }

  private static boolean isNotBlank(CharSequence csq) {
    int iMax = csq.length();
    for (int i = 0; i < iMax; i++) {
      if (!isWhitespace(csq.charAt(i))) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<String> getTranslatingTexts() {
    myTranslatingPlaceholders.clear();
    myTranslatingPlaceholders.ensureCapacity(myTranslatedSpans.size() + myTranslatedTexts.size());
    List<String> translatingSnippets =
        new ArrayList<>(myTranslatedSpans.size() + myTranslatedTexts.size());
    Map<String, Integer> repeatedTranslatingIndices = new HashMap<>();

    // collect all the translating snippets first
    for (Map.Entry<String, String> entry : myTranslatingTexts.entrySet()) {
      if (isNotBlank(entry.getValue())
          && !myPlaceHolderMarkerPattern.matcher(entry.getValue()).matches()) {
        // see if it is repeating
        if (!repeatedTranslatingIndices.containsKey(entry.getValue())) {
          // new, index
          repeatedTranslatingIndices.put(entry.getValue(), translatingSnippets.size());
          translatingSnippets.add(entry.getValue());
          myTranslatingPlaceholders.add(entry.getKey());
        }
      }
    }

    for (CharSequence text : myTranslatingSpans) {
      if (isNotBlank(text) && !myPlaceHolderMarkerPattern.matcher(text).matches()) {
        translatingSnippets.add(text.toString());
      }
    }

    return translatingSnippets;
  }

  @Override
  public void setTranslatedTexts(List<? extends CharSequence> translatedTexts) {
    myTranslatedTexts.clear();
    myTranslatedTexts.putAll(myTranslatingTexts);
    myTranslatedSpans.clear();
    myTranslatedSpans.ensureCapacity(myTranslatingSpans.size());

    // collect all the translating snippets first
    int i = 0;
    int placeholderSize = myTranslatingPlaceholders.size();
    Map<String, Integer> repeatedTranslatingIndices = new HashMap<>();

    for (Map.Entry<String, String> entry : myTranslatingTexts.entrySet()) {
      if (isNotBlank(entry.getValue())
          && !myPlaceHolderMarkerPattern.matcher(entry.getValue()).matches()) {
        Integer index = repeatedTranslatingIndices.get(entry.getValue());
        if (index == null) {
          if (i >= placeholderSize) {
            break;
          }
          // new, index
          repeatedTranslatingIndices.put(entry.getValue(), i);
          myTranslatedTexts.put(entry.getKey(), translatedTexts.get(i).toString());
          i++;
        } else {
          myTranslatedTexts.put(entry.getKey(), translatedTexts.get(index).toString());
        }
      }
    }

    for (CharSequence text : myTranslatingSpans) {
      if (isNotBlank(text) && !myPlaceHolderMarkerPattern.matcher(text).matches()) {
        myTranslatedSpans.add(translatedTexts.get(i).toString());
        i++;
      } else {
        // add original blank sequence
        myTranslatedSpans.add(text.toString());
      }
    }
  }

  @Override
  public void setRenderPurpose(RenderPurpose renderPurpose) {
    myAnchorId = 0;
    myTranslatingSpanId = 0;
    myPlaceholderId = 0;
    myRenderPurpose = renderPurpose;
    myNonTranslatingSpanId = 0;
  }

  @Override
  public RenderPurpose getRenderPurpose() {
    return myRenderPurpose;
  }

  @Override
  public boolean isTransformingText() {
    return myRenderPurpose != RenderPurpose.FORMAT;
  }

  @Override
  public CharSequence transformAnchorRef(CharSequence pageRef, CharSequence anchorRef) {
    switch (myRenderPurpose) {
      case TRANSLATION_SPANS:
        String replacedTextId = String.format(myFormatterOptions.translationIdFormat, ++myAnchorId);
        myAnchorTexts.put(replacedTextId, anchorRef.toString());
        return replacedTextId;

      case TRANSLATED_SPANS:
        return String.format(myFormatterOptions.translationIdFormat, ++myAnchorId);

      case TRANSLATED:
        String anchorIdText = String.format(myFormatterOptions.translationIdFormat, ++myAnchorId);
        String resolvedPageRef = myNonTranslatingTexts.get(pageRef.toString());

        if (resolvedPageRef != null && resolvedPageRef.length() == 0) {
          // self reference, add it to the list
          String refId = myAnchorTexts.get(anchorIdText);
          if (refId != null) {
            // original ref id for the heading we should have them all
            Integer spanIndex = myOriginalRefTargets.get(refId);
            if (spanIndex != null) {
              // have the index to translatingSpans
              String translatedRefId = myTranslatedRefTargets.get(spanIndex);
              if (translatedRefId != null) {
                return translatedRefId;
              }
            }
            return refId;
          }
        } else {
          String resolvedAnchorRef = myAnchorTexts.get(anchorIdText);
          if (resolvedAnchorRef != null) {
            return resolvedAnchorRef;
          }
        }

      case FORMAT:
      default:
        return anchorRef;
    }
  }

  @Override
  public void customPlaceholderFormat(
      TranslationPlaceholderGenerator generator, TranslatingSpanRender render) {
    if (myRenderPurpose != TRANSLATED_SPANS) {
      TranslationPlaceholderGenerator savedGenerator = myPlaceholderGenerator;
      myPlaceholderGenerator = generator;
      render.render(myWriter.getContext(), myWriter);
      myPlaceholderGenerator = savedGenerator;
    }
  }

  @Override
  public void translatingSpan(TranslatingSpanRender render) {
    translatingRefTargetSpan(null, render);
  }

  private String renderInSubContext(TranslatingSpanRender render, boolean copyToMain) {
    MarkdownWriter savedMarkdown = myWriter;
    NodeFormatterContext subContext = myWriter.getContext().getSubContext();
    MarkdownWriter writer = subContext.getMarkdown();
    myWriter = writer;

    render.render(subContext, writer);

    // trim off eol added by toString(0)
    String spanText = writer.toString(2, -1);

    myWriter = savedMarkdown;
    if (copyToMain) {
      myWriter.append(spanText);
    }
    return spanText;
  }

  @Override
  public void translatingRefTargetSpan(Node target, TranslatingSpanRender render) {
    switch (myRenderPurpose) {
      case TRANSLATION_SPANS:
        {
          String spanText = renderInSubContext(render, true);
          if (target != null) {
            if (!(target instanceof AnchorRefTarget)
                || !((AnchorRefTarget) target).isExplicitAnchorRefId()) {
              String id = myIdGenerator.getId(target);
              myOriginalRefTargets.put(id, myTranslatingSpans.size());
            }
          }

          myTranslatingSpans.add(spanText);
          return;
        }

      case TRANSLATED_SPANS:
        {
          // we output translated text instead of render
          renderInSubContext(render, false);

          String translated = myTranslatedSpans.get(myTranslatingSpanId);

          if (target != null) {
            if (!(target instanceof AnchorRefTarget)
                || !((AnchorRefTarget) target).isExplicitAnchorRefId()) {
              // only if does not have an explicit id then map to translated text id
              String id = myIdGenerator.getId(translated);
              myTranslatedRefTargets.put(myTranslatingSpanId, id);
            }
          }

          myTranslatingSpanId++;

          myWriter.append(translated);
          return;
        }

      case TRANSLATED:
        if (target != null) {
          if (!(target instanceof AnchorRefTarget)
              || !((AnchorRefTarget) target).isExplicitAnchorRefId()) {
            // only if does not have an explicit id then map to translated text id
            String id = myIdGenerator.getId(target);
            myTranslatedRefTargets.put(myTranslatingSpanId, id);
          }
        }

        myTranslatingSpanId++;
        renderInSubContext(render, true);
        return;

      case FORMAT:
      default:
        render.render(myWriter.getContext(), myWriter);
    }
  }

  @Override
  public void nonTranslatingSpan(TranslatingSpanRender render) {
    switch (myRenderPurpose) {
      case TRANSLATION_SPANS:
        {
          String spanText = renderInSubContext(render, false);

          myNonTranslatingSpans.add(spanText);
          myNonTranslatingSpanId++;

          String replacedTextId =
              getPlaceholderId(
                  myFormatterOptions.translationIdFormat, myNonTranslatingSpanId, null, null, null);
          myWriter.append(replacedTextId);
          return;
        }

      case TRANSLATED_SPANS:
        {
          // we output translated text instead of render
          renderInSubContext(render, false);

          String translated = myNonTranslatingSpans.get(myNonTranslatingSpanId);
          myNonTranslatingSpanId++;
          myWriter.append(translated);
          return;
        }

      case TRANSLATED:
        {
          // we output translated text instead of render
          renderInSubContext(render, true);
          myNonTranslatingSpanId++;
          return;
        }

      case FORMAT:
      default:
        render.render(myWriter.getContext(), myWriter);
    }
  }

  private String getPlaceholderId(
      String format,
      int placeholderId,
      CharSequence prefix,
      CharSequence suffix,
      CharSequence suffix2) {
    String replacedTextId =
        myPlaceholderGenerator != null
            ? myPlaceholderGenerator.getPlaceholder(placeholderId)
            : String.format(format, placeholderId);
    if (prefix == null && suffix == null && suffix2 == null) {
      return replacedTextId;
    }

    return addPrefixSuffix(replacedTextId, prefix, suffix, suffix2);
  }

  private static String addPrefixSuffix(
      CharSequence placeholderId, CharSequence prefix, CharSequence suffix, CharSequence suffix2) {
    if (prefix == null && suffix == null && suffix2 == null) {
      return placeholderId.toString();
    }

    StringBuilder sb = new StringBuilder();
    if (prefix != null) {
      sb.append(prefix);
    }
    sb.append(placeholderId);
    if (suffix != null) {
      sb.append(suffix);
    }
    if (suffix2 != null) {
      sb.append(suffix2);
    }
    return sb.toString();
  }

  @Override
  public void postProcessNonTranslating(
      Function<String, CharSequence> postProcessor, Runnable scope) {
    Function<String, CharSequence> savedValue = myNonTranslatingPostProcessor;
    try {
      myNonTranslatingPostProcessor = postProcessor;
      scope.run();
    } finally {
      myNonTranslatingPostProcessor = savedValue;
    }
  }

  @Override
  public <T> T postProcessNonTranslating(
      Function<String, CharSequence> postProcessor, Supplier<T> scope) {
    Function<String, CharSequence> savedValue = myNonTranslatingPostProcessor;
    try {
      myNonTranslatingPostProcessor = postProcessor;
      return scope.get();
    } finally {
      myNonTranslatingPostProcessor = savedValue;
    }
  }

  @Override
  public boolean isPostProcessingNonTranslating() {
    return myNonTranslatingPostProcessor != null;
  }

  @Override
  public CharSequence transformNonTranslating(
      CharSequence prefix,
      CharSequence nonTranslatingText,
      CharSequence suffix,
      CharSequence suffix2) {
    // need to transfer trailing EOLs to id
    CharSequence trimmedEOL;
    if (suffix2 != null) {
      trimmedEOL = suffix2;
    } else {
      BasedSequence basedSequence = BasedSequence.of(nonTranslatingText);
      trimmedEOL = basedSequence.trimmedEOL();
    }

    switch (myRenderPurpose) {
      case TRANSLATION_SPANS:
        String replacedTextId =
            getPlaceholderId(
                myFormatterOptions.translationIdFormat,
                ++myPlaceholderId,
                prefix,
                suffix,
                trimmedEOL);
        String useReplacedTextId = replacedTextId;

        if (myNonTranslatingPostProcessor != null) {
          useReplacedTextId = myNonTranslatingPostProcessor.apply(replacedTextId).toString();
        }

        myNonTranslatingTexts.put(useReplacedTextId, nonTranslatingText.toString());
        return useReplacedTextId;

      case TRANSLATED_SPANS:
        String placeholderId =
            getPlaceholderId(
                myFormatterOptions.translationIdFormat,
                ++myPlaceholderId,
                prefix,
                suffix,
                trimmedEOL);
        if (myNonTranslatingPostProcessor != null) {
          return myNonTranslatingPostProcessor.apply(placeholderId);
        }

        return placeholderId;

      case TRANSLATED:
        if (nonTranslatingText.length() > 0) {
          String text = myNonTranslatingTexts.get(nonTranslatingText.toString());
          if (text == null) {
            text = "";
          }

          if (myNonTranslatingPostProcessor != null) {
            return myNonTranslatingPostProcessor.apply(text);
          }
          return text;
        }
        return nonTranslatingText;

      case FORMAT:
      default:
        return nonTranslatingText;
    }
  }

  @Override
  public CharSequence transformTranslating(
      CharSequence prefix,
      CharSequence translatingText,
      CharSequence suffix,
      CharSequence suffix2) {
    switch (myRenderPurpose) {
      case TRANSLATION_SPANS:
        String replacedTextId =
            getPlaceholderId(
                myFormatterOptions.translationIdFormat, ++myPlaceholderId, prefix, suffix, suffix2);
        myTranslatingTexts.put(replacedTextId, translatingText.toString());
        return replacedTextId;

      case TRANSLATED_SPANS:
        return getPlaceholderId(
            myFormatterOptions.translationIdFormat, ++myPlaceholderId, prefix, suffix, suffix2);

      case TRANSLATED:
        CharSequence replacedText =
            prefix == null && suffix == null && suffix2 == null
                ? translatingText
                : addPrefixSuffix(translatingText, prefix, suffix, suffix2);
        CharSequence text = myTranslatedTexts.get(replacedText.toString());
        if (text != null && !(prefix == null && suffix == null && suffix2 == null)) {
          return addPrefixSuffix(text, prefix, suffix, suffix2);
        }
        if (text != null) {
          return text;
        }

      case FORMAT:
      default:
        return translatingText;
    }
  }
}
