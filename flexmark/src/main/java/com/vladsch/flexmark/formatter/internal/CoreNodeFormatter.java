package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.*;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.LineAppendable;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.formatter.FormattingPhase.DOCUMENT_BOTTOM;
import static com.vladsch.flexmark.formatter.RenderPurpose.*;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.ADD;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.AS_IS;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

@SuppressWarnings("WeakerAccess")
public class CoreNodeFormatter extends NodeRepositoryFormatter<ReferenceRepository, Reference, RefNode> {
    public static final DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<>("LIST_ITEM_NUMBER", 0);
    public static final NullableDataKey<ListSpacing> LIST_ITEM_SPACING = new NullableDataKey<>("LIST_ITEM_SPACING");
    public static final DataKey<Map<String, String>> UNIQUIFICATION_MAP = new DataKey<>("REFERENCES_UNIQUIFICATION_MAP", HashMap::new);
    public static final DataKey<Map<String, String>> ATTRIBUTE_UNIQUIFICATION_ID_MAP = new DataKey<>("ATTRIBUTE_UNIQUIFICATION_ID_MAP", HashMap::new);

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new CoreNodeFormatter(options);
        }
    }

    private final FormatterOptions formatterOptions;
    private final ListOptions listOptions;
    private final String myHtmlBlockPrefix;
    private final String myHtmlInlinePrefix;
    private final String myTranslationAutolinkPrefix;
    private int blankLines;
    MutableDataHolder myTranslationStore;
    private Map<String, String> attributeUniquificationIdMap;
    private Map<String, String> headingOriginalTextMap;

    public CoreNodeFormatter(DataHolder options) {
        super(options, null, UNIQUIFICATION_MAP);
        this.formatterOptions = new FormatterOptions(options);
        this.listOptions = ListOptions.get(options);
        blankLines = 0;
        myHtmlBlockPrefix = "<" + this.formatterOptions.translationHtmlBlockPrefix;
        myHtmlInlinePrefix = this.formatterOptions.translationHtmlInlinePrefix;
        myTranslationAutolinkPrefix = this.formatterOptions.translationAutolinkPrefix;
    }

    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<>(Arrays.asList(
                // Generic unknown node formatter
                new NodeFormattingHandler<>(Node.class, CoreNodeFormatter.this::render),

                // specific nodes
                new NodeFormattingHandler<>(AutoLink.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(BlankLine.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(BlockQuote.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Code.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Document.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Emphasis.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(FencedCodeBlock.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HardLineBreak.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Heading.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlBlock.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlCommentBlock.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlInnerBlock.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlInnerBlockComment.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlEntity.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlInline.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(HtmlInlineComment.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Image.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(ImageRef.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(IndentedCodeBlock.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Link.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(LinkRef.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(BulletList.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(OrderedList.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(BulletListItem.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(OrderedListItem.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(MailLink.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Paragraph.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Reference.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(SoftLineBreak.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(StrongEmphasis.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(Text.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(TextBase.class, CoreNodeFormatter.this::render),
                new NodeFormattingHandler<>(ThematicBreak.class, CoreNodeFormatter.this::render)
        ));
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        if (formatterOptions.referencePlacement != ElementPlacement.AS_IS && formatterOptions.referenceSort != ElementPlacementSort.SORT_UNUSED_LAST) return null;
        // noinspection ArraysAsListWithZeroOrOneArgument
        return new HashSet<>(Arrays.asList(
                RefNode.class
        ));
    }

    @Override
    public ReferenceRepository getRepository(DataHolder options) {
        return Parser.REFERENCES.get(options);
    }

    @Override
    public ElementPlacement getReferencePlacement() {
        return formatterOptions.referencePlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort() {
        return formatterOptions.referenceSort;
    }

    void appendReference(CharSequence id, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText() && context.getRenderPurpose() == TRANSLATED && context.getMergeContext() != null) {
            // may need to map references
            String reference = String.valueOf(context.transformTranslating(null, id, null, null));
            String uniquifiedReference = referenceUniqificationMap.getOrDefault(reference, reference);
            markdown.append(uniquifiedReference);
        } else {
            markdown.appendTranslating(id);
        }
    }

    @Override
    public void renderReferenceBlock(Reference node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            markdown.append(node.getOpeningMarker());
            appendReference(node.getReference(), context, markdown);
            markdown.append(node.getClosingMarker());

            markdown.append(' ');

            markdown.append(node.getUrlOpeningMarker());

            if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl(), false);
                markdown.appendNonTranslating(resolvedLink.getPageRef());

                if (resolvedLink.getAnchorRef() != null) {
                    markdown.append("#");
                    CharSequence anchorRef = context.transformAnchorRef(resolvedLink.getPageRef(), resolvedLink.getAnchorRef());
                    if (attributeUniquificationIdMap != null && resolvedLink.getPageRef().isEmpty() && context.isTransformingText() && context.getMergeContext() != null) {
                        String stringAnchorRef = String.valueOf(anchorRef);
                        String uniquifiedAnchorRef = attributeUniquificationIdMap.getOrDefault(stringAnchorRef, stringAnchorRef);
                        markdown.append(uniquifiedAnchorRef);
                    } else {
                        markdown.append(anchorRef);
                    }
                    markdown.append(anchorRef);
                }
            } else {
                markdown.appendNonTranslating(node.getPageRef());

                markdown.append(node.getAnchorMarker());
                if (node.getAnchorRef().isNotNull()) {
                    CharSequence anchorRef = context.transformAnchorRef(node.getPageRef(), node.getAnchorRef());
                    markdown.append(anchorRef);
                }
            }

            if (node.getTitleOpeningMarker().isNotNull()) {
                markdown.append(' ');
                markdown.append(node.getTitleOpeningMarker());
                if (node.getTitle().isNotNull()) markdown.appendTranslating(node.getTitle());
                markdown.append(node.getTitleClosingMarker());
            }

            markdown.append(node.getUrlClosingMarker()).line();
        } else {
            markdown.append(node.getChars()).line();
        }
    }

    @Override
    public void renderDocument(@NotNull NodeFormatterContext context, @NotNull MarkdownWriter markdown, @NotNull Document document, @NotNull FormattingPhase phase) {
        super.renderDocument(context, markdown, document, phase);

        attributeUniquificationIdMap = ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(context.getTranslationStore());

        if (phase == DOCUMENT_BOTTOM) {
            if (context.getFormatterOptions().appendTransferredReferences) {
                // we will transfer all references which were not part of our document
                ArrayList<DataKeyBase<?>> keys = new ArrayList<>();

                for (DataKeyBase<?> key : document.getAll().keySet()) {
                    if (key.get(document) instanceof NodeRepository) {
                        keys.add(key);
                    }
                }

                Collections.sort(keys, (o1, o2) -> o1.getName().compareTo(o2.getName()));

                boolean firstAppend = true;

                for (DataKeyBase<?> key : keys) {
                    if (key.get(document) instanceof NodeRepository) {
                        NodeRepository<?> repository = (NodeRepository<?>) key.get((DataHolder) document);
                        Set<?> nodes = repository.getReferencedElements(document);

                        for (Object value : nodes) {
                            if (value instanceof Node) {
                                Node node = (Node) value;
                                if (node.getDocument() != document) {
                                    // need to add this one
                                    if (firstAppend) {
                                        firstAppend = false;
                                        markdown.blankLine();
                                    }
                                    context.render(node);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
        BasedSequence chars = node.getChars();
        if (node instanceof Block) {
            BasedSequence contentChars = ((Block) node).getContentChars();
            if (chars.isNotNull()) {
                BasedSequence prefix = chars.prefixOf(contentChars);
                if (!prefix.isEmpty()) {
                    markdown.append(prefix);
                }
            }
            context.renderChildren(node);
            if (chars.isNotNull()) {
                BasedSequence suffix = chars.suffixOf(contentChars);
                if (!suffix.isEmpty()) {
                    markdown.append(suffix);
                }
            }
        } else {
            if (formatterOptions.keepSoftLineBreaks) {
                markdown.append(chars);
            } else {
                markdown.append(stripSoftLineBreak(chars));
            }
        }
    }

    private static CharSequence stripSoftLineBreak(CharSequence chars) {
        StringBuffer sb = null;
        Matcher matcher = Pattern.compile("\\s*(?:\r\n|\r|\n)\\s*").matcher(chars);
        while (matcher.find()) {
            if (sb == null) sb = new StringBuffer();
            matcher.appendReplacement(sb, " ");
        }
        if (sb != null) {
            matcher.appendTail(sb);
            return sb;
        }
        return chars;
    }

    private void render(BlankLine node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (LIST_ITEM_SPACING.get(context.getDocument()) == null && markdown.offsetWithPending() > 0) {
            if (!(node.getPrevious() == null || node.getPrevious() instanceof BlankLine)) {
                blankLines = 0;
            }
            blankLines++;
            if (blankLines <= formatterOptions.maxBlankLines) markdown.blankLine(blankLines);
        }
    }

    private void render(Document node, NodeFormatterContext context, MarkdownWriter markdown) {
        // No rendering itself
        myTranslationStore = context.getTranslationStore();
        context.renderChildren(node);
    }

    private void render(Heading node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        if (node.isAtxHeading()) {
            markdown.append(node.getOpeningMarker());
            boolean spaceAfterAtx = formatterOptions.spaceAfterAtxMarker == ADD
                    || formatterOptions.spaceAfterAtxMarker == AS_IS && node.getOpeningMarker().getEndOffset() < node.getText().getStartOffset();

            if (spaceAfterAtx) markdown.append(' ');

            context.translatingRefTargetSpan(node, (context12, writer) -> context12.renderChildren(node));

            switch (formatterOptions.atxHeaderTrailingMarker) {
                case EQUALIZE:
                    if (node.getOpeningMarker().isNull()) break;
                    // fall through
                case ADD:
                    if (spaceAfterAtx) markdown.append(' ');
                    markdown.append(node.getOpeningMarker());
                    break;

                case REMOVE:
                    break;

                case AS_IS:
                default:
                    if (node.getClosingMarker().isNotNull()) {
                        if (spaceAfterAtx) markdown.append(' ');
                        markdown.append(node.getClosingMarker());
                    }
                    break;
            }

            // add uniquification id attribute if needed
            HtmlIdGenerator generator = context.getIdGenerator();
            if (generator != null) {
                context.addExplicitId(node, generator.getId(node), context, markdown);
            }
        } else {
            int lastOffset = markdown.offsetWithPending() + 1;
            context.translatingRefTargetSpan(node, (context1, writer) -> context1.renderChildren(node));

            // add uniquification id attribute if needed
            HtmlIdGenerator generator = context.getIdGenerator();
            int extraText = 0;

            if (generator != null) {
                context.addExplicitId(node, generator.getId(node), context, markdown);
            }

            markdown.line();

            if (formatterOptions.setextHeaderEqualizeMarker) {
                markdown.append(node.getClosingMarker().charAt(0), Utils.minLimit(markdown.offset() - lastOffset, formatterOptions.minSetextMarkerLength));
            } else {
                markdown.append(node.getClosingMarker());
            }
        }
        markdown.tailBlankLine();
    }

    private void render(BlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
        String prefix = node.getOpeningMarker().toString();
        boolean compactPrefix = false;

        switch (formatterOptions.blockQuoteMarkers) {
            case AS_IS:
                prefix = node.baseSubSequence(node.getOpeningMarker().getStartOffset(), node.getFirstChild().getStartOffset()).toString();
                break;
            case ADD_COMPACT:
                prefix = ">";
                break;
            case ADD_COMPACT_WITH_SPACE:
                prefix = "> ";
                compactPrefix = true;
                break;
            case ADD_SPACED:
                prefix = "> ";
                break;
        }

        if (formatterOptions.blockQuoteBlankLines) markdown.blankLine();
        markdown.pushPrefix();

        // create combined prefix, compact if needed
        String combinedPrefix = markdown.getPrefix().toString();
        if (compactPrefix && combinedPrefix.endsWith("> ")) {
            combinedPrefix = combinedPrefix.substring(0, combinedPrefix.length() - 1) + prefix;
        } else {
            combinedPrefix += prefix;
        }

        markdown.setPrefix(combinedPrefix, false);
        context.renderChildren(node);
        markdown.popPrefix();
        if (formatterOptions.blockQuoteBlankLines) markdown.blankLine();
    }

    private void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        if (formatterOptions.thematicBreak != null) {
            markdown.append(formatterOptions.thematicBreak);
        } else {
            markdown.append(node.getChars());
        }
        markdown.tailBlankLine();
    }

    private void render(FencedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();

        CharSequence openingMarker = node.getOpeningMarker();
        CharSequence closingMarker = node.getClosingMarker();
        char openingMarkerChar = openingMarker.charAt(0);
        char closingMarkerChar = closingMarker.length() > 0 ? closingMarker.charAt(0) : SequenceUtils.NUL;
        int openingMarkerLen = openingMarker.length();
        int closingMarkerLen = closingMarker.length();

        switch (formatterOptions.fencedCodeMarkerType) {
            case ANY:
                break;
            case BACK_TICK:
                openingMarkerChar = '`';
                closingMarkerChar = openingMarkerChar;
                break;
            case TILDE:
                openingMarkerChar = '~';
                closingMarkerChar = openingMarkerChar;
                break;
        }

        if (openingMarkerLen < formatterOptions.fencedCodeMarkerLength) openingMarkerLen = formatterOptions.fencedCodeMarkerLength;
        if (closingMarkerLen < formatterOptions.fencedCodeMarkerLength) closingMarkerLen = formatterOptions.fencedCodeMarkerLength;

        openingMarker = RepeatedSequence.repeatOf(String.valueOf(openingMarkerChar), openingMarkerLen);
        if (formatterOptions.fencedCodeMatchClosingMarker || closingMarkerChar == SequenceUtils.NUL) { closingMarker = openingMarker; } else closingMarker = RepeatedSequence.repeatOf(String.valueOf(closingMarkerChar), closingMarkerLen);

        markdown.append(openingMarker);
        if (formatterOptions.fencedCodeSpaceBeforeInfo) markdown.append(' ');
        markdown.appendNonTranslating(node.getInfo());
        markdown.line();

        markdown.openPreFormatted(true);
        if (context.isTransformingText()) {
            markdown.appendNonTranslating(node.getContentChars());
        } else {
            if (formatterOptions.fencedCodeMinimizeIndent) {
                List<BasedSequence> lines = node.getContentLines();
                int[] leadColumns = new int[lines.size()];
                int minSpaces = Integer.MAX_VALUE;
                int i = 0;
                for (BasedSequence line : lines) {
                    leadColumns[i] = line.countLeadingColumns(0, SequenceUtils.SPACE_TAB_SET);
                    minSpaces = Utils.min(minSpaces, leadColumns[i]);
                    i++;
                }
                ArrayList<BasedSequence> trimmedLines = new ArrayList<>();
                if (minSpaces > 0) {
                    i = 0;
                    //StringBuilder out = new StringBuilder();
                    //for (BasedSequence line : lines) {
                    //    if (leadColumns[i] > minSpaces) out.append(RepeatedCharSequence.of(' ', leadColumns[i] - minSpaces));
                    //    out.append(line.trimStart());
                    //    i++;
                    //}
                    //markdown.append(out);
                    for (BasedSequence line : lines) {
                        if (leadColumns[i] > minSpaces) markdown.append(' ', leadColumns[i] - minSpaces);
                        markdown.append(line.trimStart());
                        i++;
                    }
                } else {
                    markdown.append(node.getContentChars());
                }
            } else {
                markdown.append(node.getContentChars());
            }
        }
        markdown.closePreFormatted();
        markdown.line().append(closingMarker).line();
        markdown.tailBlankLine();
    }

    private void render(IndentedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();

        if (context.isTransformingText()) {
            // here we need actual prefix in the code or the generated partial doc will not be accurate and cause translated AST to be wrong
            BasedSequence contentChars = node.getContentChars();
            String prefix = getActualAdditionalPrefix(contentChars, markdown);

            // need to always have EOL at the end and make sure no leading spaces on id at translated collection
            if (context.getRenderPurpose() == TRANSLATED) {
                contentChars = contentChars.trimStart();
            }

            markdown.pushPrefix().addPrefix(prefix);
            markdown.openPreFormatted(true);
            markdown.appendNonTranslating(Utils.suffixWith(contentChars.toString(), '\n'));
        } else {
            String prefix = RepeatedSequence.repeatOf(" ", listOptions.getCodeIndent()).toString();

            if (formatterOptions.emulationProfile == ParserEmulationProfile.GITHUB_DOC) {
                if (node.getParent() instanceof ListItem) {
                    BasedSequence marker = ((ListItem) node.getParent()).getOpeningMarker();
                    prefix = RepeatedSequence.repeatOf(" ", Utils.minLimit(8 - marker.length() - 1, 4)).toString();
                }
            }

            markdown.pushPrefix().addPrefix(prefix);
            markdown.openPreFormatted(true);

            if (formatterOptions.indentedCodeMinimizeIndent) {
                List<BasedSequence> lines = node.getContentLines();
                int[] leadColumns = new int[lines.size()];
                int minSpaces = Integer.MAX_VALUE;
                int i = 0;
                for (BasedSequence line : lines) {
                    leadColumns[i] = line.countLeadingColumns(0, SequenceUtils.SPACE_TAB_SET);
                    minSpaces = Utils.min(minSpaces, leadColumns[i]);
                    i++;
                }
                if (minSpaces > 0) {
                    i = 0;
                    //StringBuilder out = new StringBuilder();
                    //for (BasedSequence line : lines) {
                    //    if (leadColumns[i] > minSpaces) out.append(RepeatedCharSequence.of(' ', leadColumns[i] - minSpaces));
                    //    out.append(line.trimStart());
                    //    i++;
                    //}
                    //markdown.append(out);
                    for (BasedSequence line : lines) {
                        if (leadColumns[i] > minSpaces) markdown.append(' ', leadColumns[i] - minSpaces);
                        markdown.append(line.trimStart());
                        i++;
                    }
                } else {
                    markdown.append(node.getContentChars());
                }
            } else {
                markdown.append(node.getContentChars());
            }
        }

        markdown.closePreFormatted();
        markdown.popPrefix(true);
        markdown.tailBlankLine();
    }

    private void render(BulletList node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown);
    }

    private void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown);
    }

    private void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderListItem(node, context, markdown, listOptions, node.getMarkerSuffix(), false);
    }

    private void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderListItem(node, context, markdown, listOptions, node.getMarkerSuffix(), false);
    }

    public static void renderList(ListBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            // CAUTION: during translation no formatting should be done
            context.renderChildren(node);
        } else {
            ArrayList<Node> itemList = new ArrayList<>();
            Node item = node.getFirstChild();
            while (item != null) {
                itemList.add(item);
                item = item.getNext();
            }
            renderList(node, context, markdown, itemList);
        }
    }

    public static void renderList(ListBlock node, NodeFormatterContext context, MarkdownWriter markdown, List<Node> itemList) {
        if (context.getFormatterOptions().listAddBlankLineBefore && !node.isOrDescendantOfType(ListItem.class)) {
            markdown.blankLine();
        }

        Document document = context.getDocument();
        ListSpacing listSpacing = LIST_ITEM_SPACING.get(document);
        int listItemNumber = LIST_ITEM_NUMBER.get(document);
        document.set(LIST_ITEM_NUMBER, node instanceof OrderedList ? ((OrderedList) node).getStartNumber() : 1);

        ListSpacing itemSpacing = null;
        switch (context.getFormatterOptions().listSpacing) {
            case AS_IS:
                break;
            case LOOSE:
                itemSpacing = ListSpacing.LOOSE;
                break;
            case TIGHT:
                itemSpacing = ListSpacing.TIGHT;
                break;
            case LOOSEN:
                itemSpacing = node.isLoose() ? ListSpacing.LOOSE : ListSpacing.TIGHT;
                break;
            case TIGHTEN: {
                itemSpacing = ListSpacing.LOOSE;
                for (Node item : itemList) {
                    if (item instanceof ListItem) {
                        if (((ListItem) item).isOwnTight() && item.getNext() != null) {
                            itemSpacing = ListSpacing.TIGHT;
                            break;
                        }
                    }
                }
                break;
            }
        }

        document.set(LIST_ITEM_SPACING, itemSpacing == ListSpacing.LOOSE && (listSpacing == null || listSpacing == ListSpacing.LOOSE) ? ListSpacing.LOOSE : itemSpacing);
        for (Node item : itemList) {
            if (itemSpacing == ListSpacing.LOOSE && (listSpacing == null || listSpacing == ListSpacing.LOOSE)) markdown.blankLine();
            context.render(item);
        }
        document.set(LIST_ITEM_SPACING, listSpacing);
        document.set(LIST_ITEM_NUMBER, listItemNumber);

        if (!node.isOrDescendantOfType(ListItem.class)) {
            markdown.tailBlankLine();
        }
    }

    @NotNull
    public static String getActualAdditionalPrefix(BasedSequence contentChars, MarkdownWriter markdown) {
        String prefix;
        int parentPrefix = markdown.getPrefix().length();
        int column = contentChars.baseColumnAtStart();

        prefix = RepeatedSequence.repeatOf(" ", Utils.minLimit(0, column - parentPrefix)).toString();
        return prefix;
    }

    @NotNull
    public static String getAdditionalPrefix(BasedSequence fromChars, BasedSequence toChars) {
        String prefix;
        int parentPrefix = fromChars.getStartOffset();
        int column = toChars.getStartOffset();

        prefix = RepeatedSequence.repeatOf(" ", Utils.minLimit(0, column - parentPrefix)).toString();
        return prefix;
    }

    public static void renderListItem(
            ListItem node,
            NodeFormatterContext context,
            MarkdownWriter markdown,
            ListOptions listOptions,
            BasedSequence markerSuffix,
            boolean addBlankLineLooseItems
    ) {
        FormatterOptions options = context.getFormatterOptions();

        if (context.isTransformingText()) {
            BasedSequence openingMarker = node.getOpeningMarker();
            String itemContentPrefix;
            String itemContentSpacer;
            String prefix;
            String additionalPrefix = getActualAdditionalPrefix(openingMarker, markdown);

            if (node.getFirstChild() == null) {
                // TEST: not sure if this works, need an empty list item with no children to test
                int count = openingMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1;
                itemContentPrefix = RepeatedSequence.repeatOf(' ', count).toString();
                prefix = additionalPrefix + itemContentPrefix;

                itemContentSpacer = " ";
            } else {
                BasedSequence childContent = node.getFirstChild().getChars();
                itemContentPrefix = getAdditionalPrefix(markerSuffix.isEmpty() ? openingMarker : markerSuffix, childContent);
                prefix = additionalPrefix + itemContentPrefix;

                itemContentSpacer = getAdditionalPrefix(markerSuffix.isEmpty() ? openingMarker.getEmptySuffix() : markerSuffix.getEmptySuffix(), childContent);
            }

            markdown.pushPrefix().addPrefix(prefix, true);
            markdown.append(additionalPrefix).append(openingMarker);

            if (!markerSuffix.isEmpty()) {
                String markerSuffixIndent = getAdditionalPrefix(openingMarker.getEmptySuffix(), markerSuffix);
                markdown.append(markerSuffixIndent).append(markerSuffix);
            }

            markdown.append(itemContentSpacer);

            // if have no item text and followed by eol then add EOL
            if (!(node.getFirstChild() instanceof Paragraph)) {
                if (node.getFirstChild() == null) {
                    markdown.append("\n");
                } else {
                    int posEOL = node.endOfLine(openingMarker.getEndOffset());
                    if (posEOL < node.getFirstChild().getStartOffset()) {
                        // output EOL
                        markdown.append("\n");
                    }
                }
            }

            context.renderChildren(node);
            markdown.popPrefix();
        } else {
            if (options.listRemoveEmptyItems && !(node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null)) {
                return;
            }

            CharSequence openingMarker = node.getOpeningMarker();
            if (node instanceof OrderedListItem) {
                char delimiter = openingMarker.charAt(openingMarker.length() - 1);
                CharSequence number = openingMarker.subSequence(0, openingMarker.length() - 1);

                switch (options.listNumberedMarker) {
                    case ANY:
                        break;
                    case DOT:
                        delimiter = '.';
                        break;
                    case PAREN:
                        delimiter = ')';
                        break;
                    default:
                        throw new IllegalStateException("Missing case for ListNumberedMarker " + options.listNumberedMarker.name());
                }

                if (options.listRenumberItems) {
                    Document document = context.getDocument();
                    Integer itemNumber = LIST_ITEM_NUMBER.get(document);
                    openingMarker = String.format(Locale.US, "%d%c", itemNumber++, delimiter);
                    document.set(LIST_ITEM_NUMBER, itemNumber);
                } else {
                    openingMarker = String.format("%s%c", number, delimiter);
                }
            } else {
                switch (options.listBulletMarker) {
                    case ANY:
                        break;
                    case DASH:
                        openingMarker = "-";
                        break;
                    case ASTERISK:
                        openingMarker = "*";
                        break;
                    case PLUS:
                        openingMarker = "+";
                        break;
                    default:
                        throw new IllegalStateException("Missing case for ListBulletMarker " + options.listBulletMarker.name());
                }
            }

            int count = openingMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1;
            CharSequence prefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', count)
                    : RepeatedSequence.repeatOf(" ", listOptions.getItemIndent()).toString();

            markdown.pushPrefix().addPrefix(prefix, true);

            markdown.append(openingMarker).append(' ').append(markerSuffix);

            if (node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null) {
                context.renderChildren(node);
                if (addBlankLineLooseItems && (node.isLoose() || LIST_ITEM_SPACING.get(node.getDocument()) == ListSpacing.LOOSE)) {
                    markdown.tailBlankLine();
                }
            } else {
                if (node.isLoose()) {
                    markdown.tailBlankLine();
                } else {
                    markdown.line();
                }
            }
            markdown.popPrefix();
        }
    }

    private void render(Emphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.append(node.getOpeningMarker());
    }

    private void render(StrongEmphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        context.renderChildren(node);
        markdown.append(node.getOpeningMarker());
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderTextBlockParagraphLines(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.translatingSpan((context1, writer) -> context1.renderChildren(node));
        markdown.line();
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseParagraph(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseItemParagraph(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    private void render(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            // leave all as is
            renderTextBlockParagraphLines(node, context, markdown);
            if (node.isTrailingBlankLine()) {
                markdown.tailBlankLine();
            }
        } else {
            if (node.getParent() instanceof ParagraphContainer) {
                boolean startWrappingDisabled = ((ParagraphContainer) node.getParent()).isParagraphStartWrappingDisabled(node);
                boolean endWrappingDisabled = ((ParagraphContainer) node.getParent()).isParagraphEndWrappingDisabled(node);
                if (startWrappingDisabled || endWrappingDisabled) {
                    if (!startWrappingDisabled) markdown.blankLine();
                    renderTextBlockParagraphLines(node, context, markdown);
                    if (!endWrappingDisabled) markdown.blankLine();
                } else {
                    renderLooseParagraph(node, context, markdown);
                }
            } else {
                if (!(node.getParent() instanceof ParagraphItemContainer)) {
                    if (!node.isTrailingBlankLine() && (node.getNext() == null || node.getNext() instanceof ListBlock)) {
                        renderTextBlockParagraphLines(node, context, markdown);
                    } else {
                        renderLooseParagraph(node, context, markdown);
                    }
                } else {
                    boolean isItemParagraph = ((ParagraphItemContainer) node.getParent()).isItemParagraph(node);
                    if (isItemParagraph) {
                        ListSpacing itemSpacing = LIST_ITEM_SPACING.get(context.getDocument());
                        if (itemSpacing == ListSpacing.TIGHT) {
                            renderTextBlockParagraphLines(node, context, markdown);
                        } else if (itemSpacing == ListSpacing.LOOSE) {
                            if (node.getParent().getNextAnyNot(BlankLine.class) == null) {
                                renderTextBlockParagraphLines(node, context, markdown);
                            } else {
                                renderLooseItemParagraph(node, context, markdown);
                            }
                        } else {
                            if (!((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions, context.getOptions())) {
                                renderLooseItemParagraph(node, context, markdown);
                            } else {
                                renderTextBlockParagraphLines(node, context, markdown);
                            }
                        }
                    } else {
                        renderLooseParagraph(node, context, markdown);
                    }
                }
            }
        }
    }

    public static BasedSequence getSoftLineBreakSpan(Node node) {
        if (node == null) return NULL;

        Node lastNode = node;
        Node nextNode = node.getNext();

        while (nextNode != null && !(nextNode instanceof SoftLineBreak)) {
            lastNode = nextNode;
            nextNode = nextNode.getNext();
        }

        return Node.spanningChars(node.getChars(), lastNode.getChars());
    }

    private void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (formatterOptions.keepSoftLineBreaks) {
            markdown.append(node.getChars());
        } else if (!markdown.isPendingSpace()) {
            // need to add a space
            markdown.append(' ');
        }
    }

    private void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (formatterOptions.keepHardLineBreaks) {
            if (context.getRenderPurpose() == FORMAT) {
                markdown.append(node.getChars());
            } else {
                markdown.append(node.getChars());
            }
        } else if (!markdown.isPendingSpace()) {
            // need to add a space
            markdown.append(' ');
        }
    }

    static final TranslationPlaceholderGenerator htmlEntityPlaceholderGenerator = index -> String.format(Locale.US, "&#%d;", index);

    private void render(HtmlEntity node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            markdown.append(node.getChars());
        } else {
            context.customPlaceholderFormat(htmlEntityPlaceholderGenerator, (context1, markdown1) -> markdown1.appendNonTranslating(node.getChars()));
        }
    }

    private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (formatterOptions.keepSoftLineBreaks) {
            markdown.append(node.getChars());
        } else {
            markdown.append(stripSoftLineBreak(node.getChars()));
        }
    }

    private void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(Code node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        if (formatterOptions.keepSoftLineBreaks) {
            markdown.appendNonTranslating(node.getText());
        } else {
            markdown.append(stripSoftLineBreak(node.getText()));
        }
        markdown.append(node.getOpeningMarker());
    }

    private void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (node.hasChildren()) {
            // inner blocks handle rendering
            context.renderChildren(node);
        } else {
            markdown.blankLine();
            // TODO: this really needs to be parsed but we won't do it
            switch (context.getRenderPurpose()) {
                case TRANSLATION_SPANS:
                case TRANSLATED_SPANS:
                    markdown.appendNonTranslating(myHtmlBlockPrefix, node.getChars().trimEOL(), ">", node.getChars().trimmedEOL());
                    break;

                case TRANSLATED:
                    markdown.openPreFormatted(true);
                    markdown.appendNonTranslating(node.getChars());
                    markdown.closePreFormatted();
                    break;

                case FORMAT:
                default:
                    markdown.append(node.getChars());
            }
            markdown.blankLine();
        }
    }

    private void render(HtmlCommentBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        // here we need to make it translating, it is a comment
        BasedSequence trimmed = node.getChars().trimEOL();
        BasedSequence text = trimmed.subSequence(4, trimmed.length() - 3);
        BasedSequence trimmedEOL = BasedSequence.EOL;
        markdown.appendTranslating("<!--", text, "-->", trimmedEOL);
        //markdown.append(node.getChars());
    }

    private void render(HtmlInnerBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        switch (context.getRenderPurpose()) {
            case TRANSLATION_SPANS:
            case TRANSLATED_SPANS:
                markdown.appendNonTranslating(myHtmlBlockPrefix, node.getChars().trimEOL(), ">", node.getChars().trimmedEOL());
                break;

            case TRANSLATED:
                markdown.openPreFormatted(true);
                markdown.appendNonTranslating(node.getChars());
                markdown.closePreFormatted();
                break;

            case FORMAT:
            default:
                markdown.append(node.getChars());
        }
    }

    private void render(HtmlInnerBlockComment node, NodeFormatterContext context, MarkdownWriter markdown) {
        // here we need to make it translating, it is a comment
        BasedSequence text = node.getChars().subSequence(4, node.getChars().length() - 3);
        markdown.appendTranslating("<!--", text, "-->");
        //markdown.append(node.getChars());
    }

    private void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown) {
        switch (context.getRenderPurpose()) {
            case TRANSLATED_SPANS:
            case TRANSLATION_SPANS: {
                String prefix = node.getChars().startsWith("</") ? "</" : "<";
                markdown.appendNonTranslating(prefix + myHtmlInlinePrefix, node.getChars(), ">");
            }
            break;

            case TRANSLATED:
                markdown.appendNonTranslating(node.getChars());
                break;

            case FORMAT:
            default:
                markdown.append(node.getChars());
        }
    }

    private void render(HtmlInlineComment node, NodeFormatterContext context, MarkdownWriter markdown) {
        // TODO: this really needs to be parsed but we won't do it
        BasedSequence text = node.getChars().subSequence(4, node.getChars().length() - 3);
        markdown.appendTranslating("<!--", text, "-->");
    }

    private void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderReference(node, context, markdown);
    }

    public static final DataKey<Boolean> UNWRAPPED_AUTO_LINKS = new DataKey<>("UNWRAPPED_AUTO_LINKS", false);
    public static final DataKey<HashSet<String>> UNWRAPPED_AUTO_LINKS_MAP = new DataKey<>("UNWRAPPED_AUTO_LINKS_MAP", HashSet::new);

    private void render(AutoLink node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderAutoLink(node, context, markdown, myTranslationAutolinkPrefix, null);
    }

    private void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderAutoLink(node, context, markdown, myTranslationAutolinkPrefix, null);
    }

    private void renderAutoLink(DelimitedLinkNode node, NodeFormatterContext context, MarkdownWriter markdown, String prefix, String suffix) {
        if (context.isTransformingText()) {
            switch (context.getRenderPurpose()) {
                case TRANSLATION_SPANS:
                    if (node.getOpeningMarker().isNull()) {
                        // unwrapped, need to store that fact, placeholder is used to allow same url wrapped/unwrapped preservation
                        myTranslationStore.set(UNWRAPPED_AUTO_LINKS, true);

                        context.postProcessNonTranslating((s) -> {
                            UNWRAPPED_AUTO_LINKS_MAP.get(myTranslationStore).add(s);
                            return s;
                        }, () -> {
                            markdown.append("<");
                            markdown.appendNonTranslating(prefix, node.getText(), suffix);
                            markdown.append(">");
                        });
                    } else {
                        markdown.append("<");
                        markdown.appendNonTranslating(prefix, node.getText(), suffix);
                        markdown.append(">");
                    }
                    break;
                case TRANSLATED_SPANS:
                    markdown.append("<");
                    markdown.appendNonTranslating(prefix, node.getText(), suffix);
                    markdown.append(">");
                    break;

                case TRANSLATED:
                    // NOTE: on entry the node text is a placeholder, so we can check if it is wrapped or unwrapped
                    if (UNWRAPPED_AUTO_LINKS.get(myTranslationStore) && UNWRAPPED_AUTO_LINKS_MAP.get(myTranslationStore).contains(node.getText().toString())) {
                        markdown.appendNonTranslating(prefix, node.getText(), suffix);
                    } else {
                        markdown.append("<");
                        markdown.appendNonTranslating(prefix, node.getText(), suffix);
                        markdown.append(">");
                    }
                    break;

                case FORMAT:
                default:
                    markdown.append(node.getChars());
                    break;
            }
        } else {
            markdown.append(node.getChars());
        }
    }

    public static void appendWhiteSpaceBetween(
            MarkdownWriter markdown,
            Node prev,
            Node next,
            boolean preserve,
            boolean collapse,
            boolean collapseToEOL
    ) {
        if (next != null && prev != null && (preserve || collapse)) {
            appendWhiteSpaceBetween(markdown, prev.getChars(), next.getChars(), preserve, collapse, collapseToEOL);
        }
    }

    public static void appendWhiteSpaceBetween(
            MarkdownWriter markdown,
            BasedSequence prev,
            BasedSequence next,
            boolean preserve,
            boolean collapse,
            boolean collapseToEOL
    ) {
        if (next != null && prev != null && (preserve || collapse)) {
            if (prev.getEndOffset() <= next.getStartOffset()) {
                BasedSequence sequence = prev.baseSubSequence(prev.getEndOffset(), next.getStartOffset());
                if (!sequence.isEmpty() && sequence.isBlank()) {
                    if (!preserve) {
                        if (collapseToEOL && sequence.indexOfAny(BasedSequence.ANY_EOL_SET) != -1) {
                            markdown.append('\n');
                        } else {
                            markdown.append(' ');
                        }
                    } else {
                        // need to set pre-formatted or spaces after eol are ignored assuming prefixes are used
                        int saved = markdown.getOptions();
                        markdown.setOptions(saved | LineAppendable.F_ALLOW_LEADING_WHITESPACE);
                        markdown.append(sequence);
                        markdown.setOptions(saved);
                    }
                }
            } else {
                // nodes reversed due to children being rendered before the parent
            }
        }
    }

    private void render(Image node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.lineIf(formatterOptions.keepImageLinksAtStart);
        if (!formatterOptions.optimizedInlineRendering || context.isTransformingText()) {
            markdown.append(node.getTextOpeningMarker());
            if (!context.isTransformingText() || node.getFirstChildAny(HtmlInline.class) != null) {
                context.renderChildren(node);
            } else {
                markdown.appendTranslating(node.getText());
            }
            markdown.append(node.getTextClosingMarker());

            markdown.append(node.getLinkOpeningMarker());

            markdown.append(node.getUrlOpeningMarker());

            if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl(), false);
                markdown.appendNonTranslating(resolvedLink.getPageRef());
            } else {
                markdown.append(node.getUrlOpeningMarker());
                markdown.appendNonTranslating(node.getPageRef());
            }

            markdown.append(node.getUrlClosingMarker());

            if (node.getTitleOpeningMarker().isNotNull()) {
                markdown.append(' ');
                markdown.append(node.getTitleOpeningMarker());
                if (node.getTitle().isNotNull()) markdown.appendTranslating(node.getTitle());
                markdown.append(node.getTitleClosingMarker());
            }

            markdown.append(node.getLinkClosingMarker());
        } else {
            markdown.append(node.getChars());
        }
    }

    private void render(Link node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.lineIf(formatterOptions.keepExplicitLinksAtStart);
        if (!formatterOptions.optimizedInlineRendering || context.isTransformingText()) {
            markdown.append(node.getTextOpeningMarker());
            if (!context.isTransformingText() || node.getFirstChildAny(HtmlInline.class) != null) {
                context.renderChildren(node);
            } else {
                markdown.appendTranslating(node.getText());
            }
            markdown.append(node.getTextClosingMarker());

            markdown.append(node.getLinkOpeningMarker());
            markdown.append(node.getUrlOpeningMarker());

            if (context.getRenderPurpose() == TRANSLATION_SPANS) {
                ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl(), false);
                markdown.appendNonTranslating(resolvedLink.getPageRef());

                if (resolvedLink.getAnchorRef() != null) {
                    markdown.append("#");
                    CharSequence anchorRef = context.transformAnchorRef(resolvedLink.getPageRef(), resolvedLink.getAnchorRef());
                    markdown.append(anchorRef);
                }
            } else {
                CharSequence pageRef = context.transformNonTranslating(null, node.getPageRef(), null, null);
                markdown.append(pageRef);

                markdown.append(node.getAnchorMarker());

                if (node.getAnchorRef().isNotNull()) {
                    CharSequence anchorRef = context.transformAnchorRef(node.getPageRef(), node.getAnchorRef());
                    if (attributeUniquificationIdMap != null && context.getRenderPurpose() == TRANSLATED && context.getMergeContext() != null) {
                        String uniquifiedAnchorRef = String.valueOf(anchorRef);
                        if (pageRef.length() == 0) {
                            uniquifiedAnchorRef = attributeUniquificationIdMap.getOrDefault(uniquifiedAnchorRef, uniquifiedAnchorRef);
                        }
                        markdown.append(uniquifiedAnchorRef);
                    } else {
                        markdown.append(anchorRef);
                    }
                }
            }

            markdown.append(node.getUrlClosingMarker());

            if (node.getTitleOpeningMarker().isNotNull()) {
                markdown.append(' ');
                markdown.append(node.getTitleOpeningMarker());
                if (node.getTitle().isNotNull()) markdown.appendTranslating(node.getTitle());
                markdown.append(node.getTitleClosingMarker());
            }

            markdown.append(node.getLinkClosingMarker());
        } else {
            markdown.append(node.getChars());
        }
    }

    private void render(ImageRef node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!formatterOptions.optimizedInlineRendering || context.isTransformingText()) {
            if (node.isReferenceTextCombined()) {
                markdown.append(node.getReferenceOpeningMarker());
                markdown.appendTranslating(node.getReference());
                markdown.append(node.getReferenceClosingMarker());

                markdown.append(node.getTextOpeningMarker());
                markdown.append(node.getTextClosingMarker());
            } else {
                markdown.append(node.getTextOpeningMarker());
                if (!context.isTransformingText()) {
                    context.renderChildren(node);
                } else {
                    appendReference(node.getText(), context, markdown);
                }
                markdown.append(node.getTextClosingMarker());

                markdown.append(node.getReferenceOpeningMarker());
                markdown.appendTranslating(node.getReference());
                markdown.append(node.getReferenceClosingMarker());
            }
        } else {
            markdown.append(node.getChars());
        }
    }

    private void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!formatterOptions.optimizedInlineRendering || context.isTransformingText()) {
            if (node.isReferenceTextCombined()) {
                markdown.append(node.getReferenceOpeningMarker());
                appendWhiteSpaceBetween(markdown, node.getReferenceOpeningMarker(), node.getReference(), true, false, false);
                appendReference(node.getReference(), context, markdown);
                markdown.append(node.getReferenceClosingMarker());

                markdown.append(node.getTextOpeningMarker());
                markdown.append(node.getTextClosingMarker());
            } else {
                markdown.append(node.getTextOpeningMarker());
                if (!context.isTransformingText() || node.getFirstChildAny(HtmlInline.class) != null) {
                    context.renderChildren(node);
                } else {
                    appendReference(node.getText(), context, markdown);
                }
                markdown.append(node.getTextClosingMarker());

                markdown.append(node.getReferenceOpeningMarker());
                appendWhiteSpaceBetween(markdown, node.getReferenceOpeningMarker(), node.getReference(), true, false, false);
                markdown.appendTranslating(node.getReference());
                markdown.append(node.getReferenceClosingMarker());
            }
        } else {
            markdown.append(node.getChars());
        }
    }
}
