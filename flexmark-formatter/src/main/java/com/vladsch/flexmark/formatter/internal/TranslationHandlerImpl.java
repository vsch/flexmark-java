package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.formatter.RenderPurpose;
import com.vladsch.flexmark.formatter.TranslatingSpanRender;
import com.vladsch.flexmark.formatter.TranslationHandler;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.html.renderer.HtmlIdGeneratorFactory;
import com.vladsch.flexmark.util.Consumer;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;

import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Character.isWhitespace;

public class TranslationHandlerImpl implements TranslationHandler {
    final FormatterOptions myFormatterOptions;
    final HashMap<String, String> myNonTranslatingTexts;      // map placeholder to non-translating text replaced before translation so it can be replaced after translation
    final HashMap<String, String> myTranslatingTexts;         // map placeholder to translating original text which is to be translated separately from its context and is replaced with placeholder for main context translation
    final HashMap<String, String> myTranslatedTexts;          // map placeholder to translated text which is to be translated separately from its context and was replaced with placeholder for main context translation
    final ArrayList<String> myTranslatingPlaceholders;        // list of placeholders to index in translating and translated texts
    final ArrayList<String> myTranslatingSpans;
    final ArrayList<String> myTranslatedSpans;
    final HtmlIdGeneratorFactory myIdGeneratorFactory;
    final Pattern myPlaceHolderMarkerPattern;
    final MutableDataSet myTranslationStore;

    final HashMap<String, Integer> myOriginalRefTargets;      // map ref target id to translation index
    final HashMap<Integer, String> myTranslatedRefTargets;    // map translation index to translated ref target id
    final HashMap<String, String> myOriginalAnchors;          // map placeholder id to original ref id

    final HashMap<String, String> myTranslatedAnchors;        // map placeholder id to translated ref target id

    private int myPlaceholderId = 0;
    private int myAnchorId = 0;
    private int myTranslatingSpanId = 0;
    private RenderPurpose myRenderPurpose;
    private MarkdownWriter myWriter;
    private int myInTranslationSpan;
    private HtmlIdGenerator myIdGenerator;

    public TranslationHandlerImpl(final DataHolder options, final FormatterOptions formatterOptions, HtmlIdGeneratorFactory idGeneratorFactory) {
        myFormatterOptions = formatterOptions;
        myIdGeneratorFactory = idGeneratorFactory;
        myNonTranslatingTexts = new HashMap<>();
        myTranslatingTexts = new HashMap<>();
        myTranslatedTexts = new HashMap<>();
        myOriginalAnchors = new HashMap<>();
        myTranslatedAnchors = new HashMap<>();
        myTranslatedRefTargets = new HashMap<>();
        myOriginalRefTargets = new HashMap<>();
        myTranslatingSpans = new ArrayList<>();
        myTranslatedSpans = new ArrayList<>();
        myTranslatingPlaceholders = new ArrayList<>();
        myPlaceHolderMarkerPattern = Pattern.compile(formatterOptions.translationExcludePattern); //Pattern.compile("^[\\[\\](){}<>]*_{1,2}\\d+_[\\[\\](){}<>]*$");
        myTranslationStore = new MutableDataSet();
    }

    @Override
    public MutableDataSet getTranslationStore() {
        return myTranslationStore;
    }

    @Override
    public void beginRendering(final Document node, NodeFormatterContext context, MarkdownWriter appendable) {
        // collect anchor ref ids
        myWriter = appendable;
        myIdGenerator = myIdGeneratorFactory.create();
        myIdGenerator.generateIds(node);
    }

    static boolean isBlank(CharSequence csq) {
        int iMax = csq.length();
        for (int i = 0; i < iMax; i++) {
            if (!isWhitespace(csq.charAt(i))) return false;
        }
        return true;
    }

    @Override
    public List<String> getTranslatingTexts() {
        myTranslatingPlaceholders.clear();
        myTranslatingPlaceholders.ensureCapacity(myTranslatedSpans.size() + myTranslatedTexts.size());
        ArrayList<String> translatingSnippets = new ArrayList<>(myTranslatedSpans.size() + myTranslatedTexts.size());
        HashMap<String, Integer> repeatedTranslatingIndices = new HashMap<>();

        // collect all the translating snippets first
        for (Map.Entry<String, String> entry : myTranslatingTexts.entrySet()) {
            if (!isBlank(entry.getValue()) && !myPlaceHolderMarkerPattern.matcher(entry.getValue()).matches()) {
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
            if (!isBlank(text) && !myPlaceHolderMarkerPattern.matcher(text).matches()) {
                translatingSnippets.add(text.toString());
            }
        }

        return translatingSnippets;
    }

    @Override
    public void setTranslatedTexts(List<CharSequence> translatedTexts) {
        myTranslatedTexts.clear();
        myTranslatedTexts.putAll(myTranslatingTexts);
        myTranslatedSpans.clear();
        myTranslatedSpans.ensureCapacity(myTranslatingSpans.size());

        // collect all the translating snippets first
        int i = 0;
        int iMax = translatedTexts.size();
        final int placeholderSize = myTranslatingPlaceholders.size();
        HashMap<String, Integer> repeatedTranslatingIndices = new HashMap<>();

        for (Map.Entry<String, String> entry : myTranslatingTexts.entrySet()) {
            if (!isBlank(entry.getValue()) && !myPlaceHolderMarkerPattern.matcher(entry.getValue()).matches()) {
                final Integer index = repeatedTranslatingIndices.get(entry.getValue());
                if (index == null) {
                    if (i >= placeholderSize) break;
                    // new, index
                    repeatedTranslatingIndices.put(entry.getValue(), i);
                    myTranslatedTexts.put(entry.getKey(), translatedTexts.get(i).toString());
                    i++;
                } else {
                    myTranslatedTexts.put(entry.getKey(), translatedTexts.get(index).toString());
                }
            } else {
                // already has the same value
            }
        }

        for (CharSequence text : myTranslatingSpans) {
            if (!isBlank(text) && !myPlaceHolderMarkerPattern.matcher(text).matches()) {
                myTranslatedSpans.add(translatedTexts.get(i).toString());
                i++;
            } else {
                // add original blank sequence
                myTranslatedSpans.add(text.toString());
            }
        }
    }

    @Override
    public void setRenderPurpose(final RenderPurpose renderPurpose) {
        if (myInTranslationSpan > 0) {
            throw new IllegalStateException("setRendererPurpose changing with nested translationSpan");
        }

        myAnchorId = 0;
        myTranslatingSpanId = 0;
        myPlaceholderId = 0;
        myInTranslationSpan = 0;
        myRenderPurpose = renderPurpose;
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
    public CharSequence transformAnchorRef(final CharSequence pageRef, final CharSequence anchorRef) {
        switch (myRenderPurpose) {
            case TRANSLATION_SPANS:
                String replacedTextId = String.format(myFormatterOptions.translationIdFormat, ++myAnchorId);
                myNonTranslatingTexts.put(replacedTextId, anchorRef.toString());
                return replacedTextId;

            case TRANSLATED_SPANS:
                return String.format(myFormatterOptions.translationIdFormat, ++myAnchorId);

            case TRANSLATED:
                final String placeholderId = String.format(myFormatterOptions.translationIdFormat, ++myAnchorId);
                final String resolvedPageRef = myNonTranslatingTexts.get(pageRef.toString());

                if (resolvedPageRef  != null && resolvedPageRef.length() == 0) {
                    // self reference, add it to the list
                    String refId = myNonTranslatingTexts.get(placeholderId);
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
                }

            case FORMAT:
            default:
                return anchorRef;
        }
    }

    @Override
    public void translatingSpan(TranslatingSpanRender render) {
        translatingRefTargetSpan(null, render);
    }

    @Override
    public void translatingRefTargetSpan(Node target, TranslatingSpanRender render) {
        if (myInTranslationSpan == 0) {
            switch (myRenderPurpose) {
                case TRANSLATION_SPANS: {
                    myInTranslationSpan++;
                    StringBuilder span = new StringBuilder();
                    final NodeFormatterContext subContext = myWriter.getContext().getSubContext(span);
                    final MarkdownWriter writer = subContext.getMarkdown();

                    render.render(subContext, writer);

                    int pendingEOL = writer.getPendingEOL();
                    writer.flush(-1);
                    String spanText = writer.getText();

                    if (target != null) {
                        final String id = myIdGenerator.getId(target);
                        myOriginalRefTargets.put(id, myTranslatingSpans.size());
                    }

                    myTranslatingSpans.add(spanText);
                    myWriter.append(spanText);
                    myWriter.blankLine(pendingEOL);

                    myInTranslationSpan--;
                    return;
                }

                case TRANSLATED_SPANS: {
                    // we output translated text instead of render
                    StringBuilder span = new StringBuilder();
                    final NodeFormatterContext subContext = myWriter.getContext().getSubContext(span);
                    final MarkdownWriter writer = subContext.getMarkdown();

                    render.render(subContext, writer);

                    final String translated = myTranslatedSpans.get(myTranslatingSpanId);

                    if (target != null) {
                        final String id = myIdGenerator.getId(translated);
                        myTranslatedRefTargets.put(myTranslatingSpanId, id);
                    }

                    myTranslatingSpanId++;

                    //myWriter.setSuppressOutput(true);
                    //render.render(myWriter.getContext(), myWriter);
                    //myWriter.setSuppressOutput(false);
                    myWriter.append(translated);
                    return;
                }

                case TRANSLATED:
                    if (target != null) {
                        final String id = myIdGenerator.getId(target);
                        myTranslatedRefTargets.put(myTranslatingSpanId, id);
                    }
                    myTranslatingSpanId++;

                case FORMAT:
                default:
                    render.render(myWriter.getContext(), myWriter);
                    return;
            }
        }
        throw new IllegalStateException("Nested translatingSpan calls");
    }

    public static String getPlaceholderId(String format, int placeholderId, final CharSequence prefix, final CharSequence suffix, final CharSequence suffix2) {
        String replacedTextId = String.format(format, placeholderId);
        if (prefix == null && suffix == null && suffix2 == null) return replacedTextId;

        return addPrefixSuffix(replacedTextId, prefix, suffix, suffix2);
    }

    public static String addPrefixSuffix(CharSequence placeholderId, final CharSequence prefix, final CharSequence suffix, final CharSequence suffix2) {
        if (prefix == null && suffix == null && suffix2 == null) return placeholderId.toString();

        StringBuilder sb = new StringBuilder();
        if (prefix != null) sb.append(prefix);
        sb.append(placeholderId);
        if (suffix != null) sb.append(suffix);
        if (suffix2 != null) sb.append(suffix2);
        return sb.toString();
    }

    @Override
    public CharSequence transformNonTranslating(final CharSequence prefix, final CharSequence nonTranslatingText, final CharSequence suffix, final CharSequence suffix2, final Consumer<String> placeholderConsumer) {
        switch (myRenderPurpose) {
            case TRANSLATION_SPANS:
                // need to transfer trailing EOLs to id
                CharSequence trimmedEOL;
                if (suffix2 != null) {
                    trimmedEOL = suffix2;
                } else {
                    final BasedSequence basedSequence = BasedSequenceImpl.of(nonTranslatingText);
                    trimmedEOL = basedSequence.trimmedEOL();
                }
                String replacedTextId = getPlaceholderId(myFormatterOptions.translationIdFormat, ++myPlaceholderId, prefix, suffix, trimmedEOL);
                myNonTranslatingTexts.put(replacedTextId, nonTranslatingText.toString());
                return replacedTextId;

            case TRANSLATED_SPANS:
                return getPlaceholderId(myFormatterOptions.translationIdFormat, ++myPlaceholderId, prefix, suffix, suffix2);

            case TRANSLATED:
                if (nonTranslatingText.length() > 0) {
                    String text = myNonTranslatingTexts.get(nonTranslatingText.toString());
                    if (text == null) {
                        int tmp = 0;
                        text = "";
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
    public CharSequence transformTranslating(final CharSequence prefix, final CharSequence translatingText, final CharSequence suffix, final CharSequence suffix2) {
        switch (myRenderPurpose) {
            case TRANSLATION_SPANS:
                String replacedTextId = getPlaceholderId(myFormatterOptions.translationIdFormat, ++myPlaceholderId, prefix, suffix, suffix2);
                myTranslatingTexts.put(replacedTextId, translatingText.toString());
                return replacedTextId;

            case TRANSLATED_SPANS:
                return getPlaceholderId(myFormatterOptions.translationIdFormat, ++myPlaceholderId, prefix, suffix, suffix2);

            case TRANSLATED:
                CharSequence replacedText = prefix == null && suffix == null && suffix2 == null ? translatingText : addPrefixSuffix(translatingText, prefix, suffix, suffix2);
                CharSequence text = myTranslatedTexts.get(replacedText);
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
