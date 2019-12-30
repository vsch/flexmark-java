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
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ast.BlankLine;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeRepository;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.format.MarkdownParagraph;
import com.vladsch.flexmark.util.format.TrackedOffset;
import com.vladsch.flexmark.util.format.options.ContinuationIndent;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.format.options.HeadingStyle;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.LineAppendable;
import com.vladsch.flexmark.util.mappers.SpaceMapper;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharPredicate;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import static com.vladsch.flexmark.formatter.FormattingPhase.DOCUMENT_BOTTOM;
import static com.vladsch.flexmark.formatter.RenderPurpose.FORMAT;
import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATED;
import static com.vladsch.flexmark.formatter.RenderPurpose.TRANSLATION_SPANS;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.ADD;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.AS_IS;

@SuppressWarnings("WeakerAccess")
public class CoreNodeFormatter extends NodeRepositoryFormatter<ReferenceRepository, Reference, RefNode> {
    public static final DataKey<Map<String, String>> UNIQUIFICATION_MAP = new DataKey<>("REFERENCES_UNIQUIFICATION_MAP", HashMap::new);
    public static final DataKey<Map<String, String>> ATTRIBUTE_UNIQUIFICATION_ID_MAP = new DataKey<>("ATTRIBUTE_UNIQUIFICATION_ID_MAP", HashMap::new);

    public static class Factory implements NodeFormatterFactory {
        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new CoreNodeFormatter(options);
        }
    }

    final FormatterOptions formatterOptions;
    final private ListOptions listOptions;
    final private String myHtmlBlockPrefix;
    final private String myHtmlInlinePrefix;
    final private String myTranslationAutolinkPrefix;
    private int blankLines;
    MutableDataHolder myTranslationStore;
    private Map<String, String> attributeUniquificationIdMap;

    public CoreNodeFormatter(DataHolder options) {
        super(options, null, UNIQUIFICATION_MAP);
        formatterOptions = new FormatterOptions(options);
        this.listOptions = ListOptions.get(options);
        blankLines = 0;
        myHtmlBlockPrefix = "<" + formatterOptions.translationHtmlBlockPrefix;
        myHtmlInlinePrefix = formatterOptions.translationHtmlInlinePrefix;
        myTranslationAutolinkPrefix = formatterOptions.translationAutolinkPrefix;
    }

    @Override
    public char getBlockQuoteLikePrefixChar() {
        return '>';
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
        if (formatterOptions.referencePlacement.isNoChange() || !formatterOptions.referenceSort.isUnused()) return null;
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
            Node next = node.getNext();
            if (next instanceof HtmlCommentBlock || next instanceof HtmlInnerBlockComment) {
                BasedSequence text = next.getChars().trim().midSequence(4, -3);
                if (formatterOptions.linkMarkerCommentPattern != null && formatterOptions.linkMarkerCommentPattern.matcher(text).matches()) {
                    // if after ref then output nothing, the ref takes care of this
                    markdown.append("<!--").append(String.valueOf(text)).append("-->");
                }
            }
            markdown.line();
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

                keys.sort(Comparator.comparing(DataKeyBase::getName));

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
            if (context.getFormatterOptions().keepSoftLineBreaks) {
                markdown.append(chars);
            } else {
                markdown.append(FormatterUtils.stripSoftLineBreak(chars, " "));
            }
        }
    }

    private void render(BlankLine node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (FormatterUtils.LIST_ITEM_SPACING.get(context.getDocument()) == null && markdown.offsetWithPending() > 0) {
            if (!(node.getPrevious() == null || node.getPrevious() instanceof BlankLine)) {
                blankLines = 0;
            }
            blankLines++;
            if (blankLines <= context.getFormatterOptions().maxBlankLines) markdown.blankLine(blankLines);
        }
    }

    private void render(Document node, NodeFormatterContext context, MarkdownWriter markdown) {
        // No rendering itself
        myTranslationStore = context.getTranslationStore();
        context.renderChildren(node);
    }

    private void render(Heading node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        HeadingStyle headingPreference = formatterOptions.headingStyle;
        FormatterOptions formatterOptions = context.getFormatterOptions();
        if (context.isTransformingText() || headingPreference.isNoChange(node.isSetextHeading(), node.getLevel())) {
            if (node.isAtxHeading()) {
                markdown.append(node.getOpeningMarker());
                boolean spaceAfterAtx = formatterOptions.spaceAfterAtxMarker == ADD
                        || formatterOptions.spaceAfterAtxMarker == AS_IS && node.getOpeningMarker().getEndOffset() < node.getText().getStartOffset();

                if (spaceAfterAtx) markdown.append(' ');

                context.translatingRefTargetSpan(node, (context12, writer) -> context12.renderChildren(node));

                switch (formatterOptions.atxHeadingTrailingMarker) {
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

                if (formatterOptions.setextHeadingEqualizeMarker) {
                    markdown.append(node.getClosingMarker().charAt(0), Utils.minLimit(markdown.offset() - lastOffset, formatterOptions.minSetextMarkerLength));
                } else {
                    markdown.append(node.getClosingMarker());
                }
            }
        } else if (headingPreference.isSetext()) {
            // change to setext
            int lastOffset = markdown.offsetWithPending() + 1;
            context.renderChildren(node);
            markdown.line();
            char closingMarker = node.getLevel() == 1 ? '=' : '-';

            if (formatterOptions.setextHeadingEqualizeMarker) {
                markdown.append(closingMarker, Utils.minLimit(markdown.offset() - lastOffset, formatterOptions.minSetextMarkerLength));
            } else {
                markdown.append(RepeatedSequence.repeatOf(closingMarker, formatterOptions.minSetextMarkerLength));
            }
        } else {
            // change to atx
            assert headingPreference.isAtx();

            CharSequence openingMarker = RepeatedSequence.repeatOf('#', node.getLevel());
            markdown.append(openingMarker);

            boolean spaceAfterAtx = formatterOptions.spaceAfterAtxMarker == ADD
                    || formatterOptions.spaceAfterAtxMarker == AS_IS && !Parser.HEADING_NO_ATX_SPACE.get(context.getOptions());

            if (spaceAfterAtx) markdown.append(' ');

            context.renderChildren(node);

            switch (formatterOptions.atxHeadingTrailingMarker) {
                case EQUALIZE:
                case ADD:
                    if (spaceAfterAtx) markdown.append(' ');
                    markdown.append(openingMarker);
                    break;

                default:
                case AS_IS:
                case REMOVE:
                    break;
            }
        }

        markdown.tailBlankLine();
    }

    private void render(BlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterOptions formatterOptions = context.getFormatterOptions();

        Pair<String, String> blockLikePrefix = FormatterUtils.getBlockLikePrefix(node, context, markdown, formatterOptions.blockQuoteMarkers, formatterOptions.blockQuoteContinuationMarkers);
        String combinedPrefix = blockLikePrefix.getFirst();
        String combinedContinuationPrefix = blockLikePrefix.getSecond();

        markdown.pushPrefix();

        if (!FormatterUtils.FIRST_LIST_ITEM_CHILD.get(node.getDocument())) {
            if (formatterOptions.blockQuoteBlankLines) {
                markdown.blankLine();
            }

            markdown.setPrefix(combinedPrefix, false);
        } else {
            markdown.pushOptions().removeOptions(LineAppendable.F_WHITESPACE_REMOVAL).append(combinedPrefix).popOptions();
        }

        markdown.setPrefix(combinedContinuationPrefix, true);
        int lines = markdown.getLineCount();
        context.renderChildren(node);
        markdown.popPrefix();
        if (formatterOptions.blockQuoteBlankLines && (lines < markdown.getLineCount() || !FormatterUtils.FIRST_LIST_ITEM_CHILD.get(node.getDocument()))) markdown.blankLine();
    }

    private void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.blankLine();
        if (context.getFormatterOptions().thematicBreak != null) {
            markdown.append(context.getFormatterOptions().thematicBreak);
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

        switch (context.getFormatterOptions().fencedCodeMarkerType) {
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

        if (openingMarkerLen < context.getFormatterOptions().fencedCodeMarkerLength) openingMarkerLen = context.getFormatterOptions().fencedCodeMarkerLength;
        if (closingMarkerLen < context.getFormatterOptions().fencedCodeMarkerLength) closingMarkerLen = context.getFormatterOptions().fencedCodeMarkerLength;

        openingMarker = RepeatedSequence.repeatOf(String.valueOf(openingMarkerChar), openingMarkerLen);
        if (context.getFormatterOptions().fencedCodeMatchClosingMarker || closingMarkerChar == SequenceUtils.NUL) { closingMarker = openingMarker; } else closingMarker = RepeatedSequence.repeatOf(String.valueOf(closingMarkerChar), closingMarkerLen);

        markdown.append(openingMarker);
        if (context.getFormatterOptions().fencedCodeSpaceBeforeInfo) markdown.append(' ');
        markdown.appendNonTranslating(node.getInfo());
        markdown.line();

        markdown.openPreFormatted(true);
        if (context.isTransformingText()) {
            markdown.appendNonTranslating(node.getContentChars());
        } else {
            if (context.getFormatterOptions().fencedCodeMinimizeIndent) {
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
            String prefix = FormatterUtils.getActualAdditionalPrefix(contentChars, markdown);

            // need to always have EOL at the end and make sure no leading spaces on id at translated collection
            if (context.getRenderPurpose() == TRANSLATED) {
                contentChars = contentChars.trimStart();
            }

            markdown.pushPrefix().addPrefix(prefix);
            markdown.openPreFormatted(true);
            markdown.appendNonTranslating(Utils.suffixWith(contentChars.toString(), '\n'));
        } else {
            String prefix = RepeatedSequence.repeatOf(" ", listOptions.getCodeIndent()).toString();

            if (context.getFormatterOptions().emulationProfile == ParserEmulationProfile.GITHUB_DOC) {
                if (node.getParent() instanceof ListItem) {
                    BasedSequence marker = ((ListItem) node.getParent()).getOpeningMarker();
                    prefix = RepeatedSequence.repeatOf(" ", Utils.minLimit(8 - marker.length() - 1, 4)).toString();
                }
            }

            markdown.pushPrefix().addPrefix(prefix);
            markdown.openPreFormatted(true);

            if (context.getFormatterOptions().indentedCodeMinimizeIndent) {
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
        FormatterOptions formatterOptions = context.getFormatterOptions();
        if (formatterOptions.listAddBlankLineBefore && !node.isOrDescendantOfType(ListItem.class)) {
            markdown.blankLine();
        }

        Document document = context.getDocument();
        ListSpacing listSpacing = FormatterUtils.LIST_ITEM_SPACING.get(document);
        int listItemNumber = FormatterUtils.LIST_ITEM_NUMBER.get(document);
        int startingNumber = node instanceof OrderedList ? formatterOptions.listRenumberItems && formatterOptions.listResetFirstItemNumber ? 1 : ((OrderedList) node).getStartNumber() : 1;
        Function<CharSequence, Pair<Integer, Integer>> listAlignNumeric = FormatterUtils.LIST_ALIGN_NUMERIC.get(document);
        document.set(FormatterUtils.LIST_ITEM_NUMBER, startingNumber);

        ListSpacing itemSpacing = null;
        switch (formatterOptions.listSpacing) {
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

        document.remove(FormatterUtils.LIST_ALIGN_NUMERIC);

        if (!formatterOptions.listAlignNumeric.isNoChange() && node instanceof OrderedList) {
            int maxLen = Integer.MIN_VALUE;
            int minLen = Integer.MAX_VALUE;
            int i = startingNumber;
            for (Node item : itemList) {
                if (!formatterOptions.listRemoveEmptyItems || item.hasChildren() && item.getFirstChildAnyNot(BlankLine.class) != null) {
                    int length = formatterOptions.listRenumberItems ? Integer.toString(i).length() + 1 : ((ListItem) item).getOpeningMarker().length();
                    maxLen = Math.max(maxLen, length);
                    minLen = Math.min(minLen, length);
                    i++;
                }
            }

            if (maxLen != minLen) {
                int finalMaxLen = maxLen;
                document.set(FormatterUtils.LIST_ALIGN_NUMERIC, formatterOptions.listAlignNumeric.isLeft()
                        ? sequence -> Pair.of(0, Math.min(4, Math.max(0, finalMaxLen - sequence.length())))
                        : sequence -> Pair.of(Math.min(4, Math.max(0, finalMaxLen - sequence.length())), 0)
                );
            }
        }

        document.set(FormatterUtils.LIST_ITEM_SPACING, itemSpacing == ListSpacing.LOOSE && (listSpacing == null || listSpacing == ListSpacing.LOOSE) ? ListSpacing.LOOSE : itemSpacing);
        for (Node item : itemList) {
            if (itemSpacing == ListSpacing.LOOSE && (listSpacing == null || listSpacing == ListSpacing.LOOSE)) markdown.blankLine();
            context.render(item);
        }
        document.set(FormatterUtils.LIST_ITEM_SPACING, listSpacing);
        document.set(FormatterUtils.LIST_ITEM_NUMBER, listItemNumber);
        document.set(FormatterUtils.LIST_ALIGN_NUMERIC, listAlignNumeric);

        if (!node.isOrDescendantOfType(ListItem.class)) {
            markdown.tailBlankLine();
        }
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
        boolean savedFirstListItemChild = FormatterUtils.FIRST_LIST_ITEM_CHILD.get(node.getDocument());

        if (context.isTransformingText()) {
            BasedSequence openingMarker = node.getOpeningMarker();
            String itemContentPrefix;
            String itemContentSpacer;
            String prefix;
            String additionalPrefix = FormatterUtils.getActualAdditionalPrefix(openingMarker, markdown);

            if (node.getFirstChild() == null) {
                // TEST: not sure if this works, need an empty list item with no children to test
                int count = openingMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1;
                itemContentPrefix = RepeatedSequence.repeatOf(' ', count).toString();
                prefix = additionalPrefix + itemContentPrefix;

                itemContentSpacer = " ";
            } else {
                BasedSequence childContent = node.getFirstChild().getChars();
                itemContentPrefix = FormatterUtils.getAdditionalPrefix(markerSuffix.isEmpty() ? openingMarker : markerSuffix, childContent);
                prefix = additionalPrefix + itemContentPrefix;

                itemContentSpacer = FormatterUtils.getAdditionalPrefix(markerSuffix.isEmpty() ? openingMarker.getEmptySuffix() : markerSuffix.getEmptySuffix(), childContent);
            }

            markdown.pushPrefix().addPrefix(prefix, true);
            markdown.append(additionalPrefix).append(openingMarker);

            if (!markerSuffix.isEmpty()) {
                String markerSuffixIndent = FormatterUtils.getAdditionalPrefix(openingMarker.getEmptySuffix(), markerSuffix);
                markdown.append(markerSuffixIndent).append(markerSuffix);
            }

            markdown.append(itemContentSpacer);

            // if have no item text and followed by eol then add EOL
            if (!(node.getFirstChild() instanceof Paragraph)) {
                if (node.getFirstChild() == null) {
                    if (!savedFirstListItemChild) {
                        markdown.append("\n");
                    }
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
            if (node.isOrderedItem()) {
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

                Document document = context.getDocument();

                if (options.listRenumberItems) {
                    Integer itemNumber = FormatterUtils.LIST_ITEM_NUMBER.get(document);
                    openingMarker = String.format(Locale.US, "%d%c", itemNumber++, delimiter);
                    document.set(FormatterUtils.LIST_ITEM_NUMBER, itemNumber);
                } else {
                    openingMarker = String.format("%s%c", number, delimiter);
                }

                Pair<Integer, Integer> padding = FormatterUtils.LIST_ALIGN_NUMERIC.get(document).apply(openingMarker);
                if (padding.getFirst() > 0) openingMarker = RepeatedSequence.ofSpaces(padding.getFirst()).toString() + openingMarker.toString();
                if (padding.getSecond() > 0) openingMarker = openingMarker.toString() + RepeatedSequence.ofSpaces(padding.getSecond()).toString();
            } else {
                if (node.canChangeMarker()) {
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
            }

            // NOTE: if list item content after suffix is set in the parser, then sub-items are indented after suffix
            //    otherwise only the item's lazy continuation for the paragraph can be indented after suffix, child items are normally indented
            int itemContinuationCount = (listOptions.isItemContentAfterSuffix() || options.listsItemContentAfterSuffix ? markerSuffix.length() : 0);
            int continuationCount = openingMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1;
            CharSequence itemPrefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', itemContinuationCount)
                    : "";

            CharSequence childPrefix = options.itemContentIndent ? RepeatedSequence.repeatOf(' ', continuationCount)
                    : RepeatedSequence.repeatOf(" ", listOptions.getItemIndent()).toString();

            markdown.pushPrefix().addPrefix(childPrefix, true);

            markdown.pushOptions()
                    .preserveSpaces().append(openingMarker).append(' ').append(markerSuffix)
                    .popOptions();

            if (node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null) {
                // NOTE: depends on first child
                Node childNode = node.getFirstChild();

                if (childNode instanceof Paragraph) {
                    markdown.pushPrefix().addPrefix(itemPrefix, true);
                    context.render(childNode);
                    markdown.popPrefix();
                } else if (childNode != null) {
                    // NOTE: item is empty, followed immediately by child block element
                    FormatterUtils.FIRST_LIST_ITEM_CHILD.set(node.getDocument(), true);
                    markdown.pushPrefix().addPrefix(itemPrefix, true);
                    context.render(childNode);
                    markdown.popPrefix();
                }

                FormatterUtils.FIRST_LIST_ITEM_CHILD.set(node.getDocument(), false);

                while (childNode != null) {
                    childNode = childNode.getNext();
                    if (childNode == null) {
                        break;
                    }
                    context.render(childNode);
                }

                if (addBlankLineLooseItems && (node.isLoose() || FormatterUtils.LIST_ITEM_SPACING.get(node.getDocument()) == ListSpacing.LOOSE)) {
                    markdown.tailBlankLine();
                }
            } else {
                // NOTE: empty list item
                if (node.isLoose()) {
                    markdown.tailBlankLine();
                } else {
                    if (!savedFirstListItemChild) {
                        markdown.line();
                    }
                }
            }
            markdown.popPrefix();
        }

        FormatterUtils.FIRST_LIST_ITEM_CHILD.set(node.getDocument(), savedFirstListItemChild);
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
        if (context.isTransformingText()) {
            context.translatingSpan((context1, writer) -> context1.renderChildren(node));
            markdown.line();
        } else {
            FormatterOptions formatterOptions = context.getFormatterOptions();
            if (formatterOptions.rightMargin > 0) {
                MutableDataHolder subContextOptions = context.getOptions().toMutable().set(Formatter.KEEP_SOFT_LINE_BREAKS, true).set(Formatter.KEEP_HARD_LINE_BREAKS, true);
                SequenceBuilder builder = node.getChars().getBuilder();
                NodeFormatterContext subContext = context.getSubContext(subContextOptions, builder.getBuilder());
                subContext.renderChildren(node);

                MarkdownWriter subContextMarkdown = subContext.getMarkdown();
                subContextMarkdown.toBuilder(builder, 0);

                MarkdownParagraph formatter = new MarkdownParagraph(builder.toSequence(), formatterOptions.charWidthProvider);
                List<TrackedOffset> trackedOffsets = Formatter.TRACKED_OFFSETS.get(context.getDocument());

                if (formatterOptions.paragraphContinuationIndent == ContinuationIndent.ALIGN_TO_FIRST) {
                    formatter.setWidth(formatterOptions.rightMargin - markdown.getPrefix().length());
                    formatter.setKeepSoftBreaks(false);
                    formatter.setKeepHardBreaks(formatterOptions.keepHardLineBreaks);
                    formatter.setRestoreTrackedSpaces(false);
                    formatter.setFirstIndent("");
                    formatter.setIndent("");

                    // adjust first line width, based on change in prefix after the first line EOL
                    formatter.setFirstWidthOffset(markdown.column() - markdown.getAfterEolPrefixDelta());

                    if (formatterOptions.applySpecialLeadInHandlers) {
                        formatter.setLeadInHandlers(Parser.SPECIAL_LEAD_IN_HANDLERS.get(context.getDocument()));
                    }

                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset()) {
                            formatter.addTrackedOffset(trackedOffset);
                        }
                    }

                    int paragraphOffset = markdown.offsetWithPending();
                    BasedSequence wrappedText = formatter.wrapText();
                    markdown.append(wrappedText.toMapped(SpaceMapper.fromNonBreakSpace));

                    // get the indent used for new lines so that index can be adjusted by added indent
                    int lineIndent = markdown.getPrefix().length();
                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset()) {
                            if (trackedOffset.isResolved()) {
                                int interveningLines = wrappedText.countOfAny(CharPredicate.EOL, 0, trackedOffset.getIndex());
                                trackedOffset.setIndex(trackedOffset.getIndex() + paragraphOffset + interveningLines * lineIndent);
                            }
                        }
                    }

                    markdown.line();
                } else {
                    int indent;

                    switch (formatterOptions.paragraphContinuationIndent) {
                        // @formatter:off
                        case INDENT_1: indent = 4; break;
                        case INDENT_2: indent = 8; break;
                        case INDENT_3: indent = 12; break;

                        default:
                        case NONE: indent = 0; break;
                        // @formatter:on
                    }

                    formatter.setWidth(formatterOptions.rightMargin);
                    formatter.setKeepSoftBreaks(false);
                    formatter.setKeepHardBreaks(formatterOptions.keepHardLineBreaks);
                    CharSequence firstIndent = markdown.getPrefix().subSequence(0, Math.max(0, markdown.getPrefix().length() - markdown.getAfterEolPrefixDelta()));
                    formatter.setFirstIndent(firstIndent);
                    formatter.setIndent(RepeatedSequence.ofSpaces(indent));
                    formatter.setRestoreTrackedSpaces(false);

                    // adjust first line width, based on change in prefix after the first line EOL
                    formatter.setFirstWidthOffset(-markdown.column());

                    if (formatterOptions.applySpecialLeadInHandlers) {
                        formatter.setLeadInHandlers(Parser.SPECIAL_LEAD_IN_HANDLERS.get(context.getDocument()));
                    }

                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset()) {
                            formatter.addTrackedOffset(trackedOffset);
                        }
                    }

                    BasedSequence wrapped = formatter.wrapText().toMapped(SpaceMapper.fromNonBreakSpace);

                    int paragraphOffset = markdown.offset();
                    for (TrackedOffset trackedOffset : trackedOffsets) {
                        if (trackedOffset.getOffset() >= node.getStartOffset() && trackedOffset.getOffset() <= node.getEndOffset()) {
                            if (trackedOffset.isResolved()) {
                                trackedOffset.setIndex(trackedOffset.getIndex() + paragraphOffset);
                            }
                        }
                    }

                    markdown.openPreFormatted(false).pushPrefix().setPrefix("", false)
                            .append(wrapped).line()
                            .popPrefix().closePreFormatted();
                }
            } else {
                context.renderChildren(node);
                markdown.line();
            }
        }
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
                        ListSpacing itemSpacing = FormatterUtils.LIST_ITEM_SPACING.get(context.getDocument());
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

    private void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getFormatterOptions().keepSoftLineBreaks) {
            markdown.append(node.getChars());
        } else if (!markdown.isPendingSpace()) {
            // need to add a space
            markdown.append(' ');
        }
    }

    private void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getFormatterOptions().keepHardLineBreaks) {
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
        if (context.getFormatterOptions().keepSoftLineBreaks) {
            markdown.append(node.getChars());
        } else {
            markdown.append(FormatterUtils.stripSoftLineBreak(node.getChars(), " "));
        }
    }

    private void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(Code node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        if (context.isTransformingText() || formatterOptions.rightMargin == 0) {
            if (context.getFormatterOptions().keepSoftLineBreaks) {
                markdown.appendNonTranslating(node.getText());
            } else {
                markdown.appendNonTranslating(FormatterUtils.stripSoftLineBreak(node.getText(), " "));
            }
        } else {
            // wrapping text
            if (context.getFormatterOptions().keepSoftLineBreaks) {
                markdown.append(node.getText());
            } else {
                markdown.append(FormatterUtils.stripSoftLineBreak(node.getText(), " "));
            }
        }
        markdown.append(node.getClosingMarker());
    }

    private void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (node.hasChildren()) {
            // inner blocks handle rendering
            context.renderChildren(node);
        } else {
            markdown.blankLine();
            // FIX: this really needs to be parsed but we won't do it
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
        BasedSequence text = node.getChars().trim().midSequence(4, -3);
        BasedSequence trimmedEOL = BasedSequence.EOL;

        if (!context.isTransformingText() && formatterOptions.linkMarkerCommentPattern != null && formatterOptions.linkMarkerCommentPattern.matcher(text).matches()) {
            // if after ref then output nothing, the ref takes care of this
            if (!(node.getPrevious() instanceof Reference)) {
                markdown.append("<!--").append(String.valueOf(text.toMapped(SpaceMapper.toNonBreakSpace))).append("-->");
            }
        } else {
            markdown.appendTranslating("<!--", text, "-->", trimmedEOL);
        }

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
        BasedSequence text = node.getChars().trim().midSequence(4, -3);
        if (!context.isTransformingText() && formatterOptions.linkMarkerCommentPattern != null && formatterOptions.linkMarkerCommentPattern.matcher(text).matches()) {
            // if after ref then output nothing, the ref takes care of this
            if (!(node.getPrevious() instanceof Reference)) {
                markdown.append("<!--").append(String.valueOf(text.toMapped(SpaceMapper.toNonBreakSpace))).append("-->");
            }
        } else {
            markdown.appendTranslating("<!--", text, "-->");
        }
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
        BasedSequence text = node.getChars().trim().midSequence(4, -3);
        if (!context.isTransformingText() && formatterOptions.linkMarkerCommentPattern != null && formatterOptions.linkMarkerCommentPattern.matcher(text).matches()) {
            markdown.append("<!--").append(String.valueOf(text.toMapped(SpaceMapper.toNonBreakSpace))).append("-->");
        } else {
            markdown.appendTranslating("<!--", text, "-->");
        }
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

    private void render(Image node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.isTransformingText() && context.getFormatterOptions().rightMargin > 0 && context.getFormatterOptions().keepImageLinksAtStart) {
            markdown.append(SequenceUtils.LS);
        } else {
            markdown.lineIf(context.getFormatterOptions().keepImageLinksAtStart);
        }

        if (!context.getFormatterOptions().optimizedInlineRendering || context.isTransformingText()) {
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
        if (!context.isTransformingText() && context.getFormatterOptions().rightMargin > 0 && context.getFormatterOptions().keepExplicitLinksAtStart) {
            markdown.append(SequenceUtils.LS);
        } else {
            markdown.lineIf(context.getFormatterOptions().keepExplicitLinksAtStart);
        }

        if (!context.getFormatterOptions().optimizedInlineRendering || context.isTransformingText()) {
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
        if (!context.getFormatterOptions().optimizedInlineRendering || context.isTransformingText()) {
            if (context.isTransformingText() || formatterOptions.rightMargin == 0) {
                if (node.isReferenceTextCombined()) {
                    markdown.append(node.getReferenceOpeningMarker());
                    markdown.appendTranslating(node.getReference());
                    markdown.append(node.getReferenceClosingMarker());

                    markdown.append(node.getTextOpeningMarker());
                    markdown.append(node.getTextClosingMarker());
                } else {
                    markdown.append(node.getTextOpeningMarker());
                    appendReference(node.getText(), context, markdown);
                    markdown.append(node.getTextClosingMarker());

                    markdown.append(node.getReferenceOpeningMarker());
                    markdown.appendTranslating(node.getReference());
                    markdown.append(node.getReferenceClosingMarker());
                }
            } else {
                if (node.isReferenceTextCombined()) {
                    markdown.append(node.getReferenceOpeningMarker());
                    markdown.append(node.getReference().toMapped(SpaceMapper.toNonBreakSpace));
                    markdown.append(node.getReferenceClosingMarker());

                    markdown.append(node.getTextOpeningMarker());
                    markdown.append(node.getTextClosingMarker());
                } else {
                    markdown.append(node.getTextOpeningMarker());
                    context.renderChildren(node);
                    markdown.append(node.getTextClosingMarker());

                    markdown.append(node.getReferenceOpeningMarker());
                    markdown.append(node.getReference());
                    markdown.append(node.getReferenceClosingMarker());
                }
            }
        } else {
            markdown.append(node.getChars());
        }
    }

    private void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.getFormatterOptions().optimizedInlineRendering || context.isTransformingText()) {
            if (context.isTransformingText() || formatterOptions.rightMargin == 0) {
                if (node.isReferenceTextCombined()) {
                    markdown.append(node.getReferenceOpeningMarker());
                    FormatterUtils.appendWhiteSpaceBetween(markdown, node.getReferenceOpeningMarker(), node.getReference(), true, false, false);
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
                    FormatterUtils.appendWhiteSpaceBetween(markdown, node.getReferenceOpeningMarker(), node.getReference(), true, false, false);
                    markdown.appendTranslating(node.getReference());
                    markdown.append(node.getReferenceClosingMarker());
                }
            } else {
                if (node.isReferenceTextCombined()) {
                    markdown.append(node.getReferenceOpeningMarker());
                    markdown.append(node.getReference().toMapped(SpaceMapper.toNonBreakSpace));
                    markdown.append(node.getReferenceClosingMarker());

                    markdown.append(node.getTextOpeningMarker());
                    markdown.append(node.getTextClosingMarker());
                } else {
                    markdown.append(node.getTextOpeningMarker());
                    context.renderChildren(node);
                    markdown.append(node.getTextClosingMarker());

                    markdown.append(node.getReferenceOpeningMarker());
                    markdown.append(node.getReference());
                    markdown.append(node.getReferenceClosingMarker());
                }
            }
        } else {
            markdown.append(node.getChars());
        }
    }
}
