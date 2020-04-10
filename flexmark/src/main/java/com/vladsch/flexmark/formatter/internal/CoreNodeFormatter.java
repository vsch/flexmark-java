package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.html.renderer.HtmlIdGenerator;
import com.vladsch.flexmark.html.renderer.LinkType;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataKeyBase;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.format.options.HeadingStyle;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.mappers.SpaceMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

import static com.vladsch.flexmark.formatter.FormatterUtils.LIST_ITEM_SPACING;
import static com.vladsch.flexmark.formatter.FormatterUtils.isLastOfItem;
import static com.vladsch.flexmark.formatter.FormattingPhase.DOCUMENT_BOTTOM;
import static com.vladsch.flexmark.formatter.RenderPurpose.*;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.ADD;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.AS_IS;

@SuppressWarnings("WeakerAccess")
public class CoreNodeFormatter extends NodeRepositoryFormatter<ReferenceRepository, Reference, RefNode> {
    /**
     * 2020-04-10
     * @deprecated use {@link Formatter#UNIQUIFICATION_MAP}
     */
    @Deprecated
    final public static DataKey<Map<String, String>> UNIQUIFICATION_MAP = Formatter.UNIQUIFICATION_MAP;
    /**
     * 2020-04-10
     * @deprecated use {@link Formatter#ATTRIBUTE_UNIQUIFICATION_ID_MAP}
     */
    @Deprecated
    final public static DataKey<Map<String, String>> ATTRIBUTE_UNIQUIFICATION_ID_MAP = Formatter.ATTRIBUTE_UNIQUIFICATION_ID_MAP;
    
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
        super(options, null, Formatter.UNIQUIFICATION_MAP);
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

        attributeUniquificationIdMap = Formatter.ATTRIBUTE_UNIQUIFICATION_ID_MAP.get(context.getTranslationStore());

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
                                // NOTE: here the node.getDocument() is necessary to test if this was appended reference
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
                        if (node.getClosingMarker().isNull()) break;
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
                context.translatingRefTargetSpan(node, (context1, writer) -> context1.renderChildren(node));

                // add uniquification id attribute if needed
                HtmlIdGenerator generator = context.getIdGenerator();

                if (generator != null) {
                    context.addExplicitId(node, generator.getId(node), context, markdown);
                }

                markdown.line();

                if (formatterOptions.setextHeadingEqualizeMarker) {
                    markdown.append(node.getClosingMarker().charAt(0), Utils.minLimit(markdown.getLineInfo(markdown.getLineCountWithPending() - 1).textLength, formatterOptions.minSetextMarkerLength));
                } else {
                    markdown.append(node.getClosingMarker());
                }
            }
        } else if (headingPreference.isSetext()) {
            // change to setext
            context.renderChildren(node);
            markdown.line();
            char closingMarker = node.getLevel() == 1 ? '=' : '-';

            if (formatterOptions.setextHeadingEqualizeMarker) {
                markdown.append(closingMarker, Utils.minLimit(markdown.getLineInfo(markdown.getLineCountWithPending() - 1).textLength, formatterOptions.minSetextMarkerLength));
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
        FormatterUtils.renderBlockQuoteLike(node, context, markdown);
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
                    leadColumns[i] = line.countLeadingColumns(0, CharPredicate.SPACE_TAB);
                    minSpaces = Utils.min(minSpaces, leadColumns[i]);
                    i++;
                }
                if (minSpaces > 0) {
                    i = 0;
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

        if (!(node.getParent() instanceof ListItem) || !isLastOfItem(node) || LIST_ITEM_SPACING.get(context.getDocument()) == ListSpacing.LOOSE) {
            markdown.tailBlankLine();
        }
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
                    leadColumns[i] = line.countLeadingColumns(0, CharPredicate.SPACE_TAB);
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
        FormatterUtils.renderList(node, context, markdown);
    }

    private void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterUtils.renderList(node, context, markdown);
    }

    private void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterUtils.renderListItem(node, context, markdown, listOptions, node.getMarkerSuffix(), false);
    }

    private void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        FormatterUtils.renderListItem(node, context, markdown, listOptions, node.getMarkerSuffix(), false);
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

    private void render(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            // leave all as is
            FormatterUtils.renderTextBlockParagraphLines(node, context, markdown);
            if (node.isTrailingBlankLine()) {
                markdown.tailBlankLine();
            }
        } else {
            if (!(node.getParent() instanceof ParagraphItemContainer)) {
                if (node.getParent() instanceof ParagraphContainer) {
                    boolean startWrappingDisabled = ((ParagraphContainer) node.getParent()).isParagraphStartWrappingDisabled(node);
                    boolean endWrappingDisabled = ((ParagraphContainer) node.getParent()).isParagraphEndWrappingDisabled(node);
                    if (startWrappingDisabled || endWrappingDisabled) {
                        if (!startWrappingDisabled) markdown.blankLine();
                        FormatterUtils.renderTextBlockParagraphLines(node, context, markdown);
                        if (!endWrappingDisabled) markdown.tailBlankLine();
                    } else {
                        FormatterUtils.renderLooseParagraph(node, context, markdown);
                    }
                } else {
                    if (!node.isTrailingBlankLine() && (node.getNext() == null || node.getNext() instanceof ListBlock)) {
                        FormatterUtils.renderTextBlockParagraphLines(node, context, markdown);
                    } else {
                        FormatterUtils.renderLooseParagraph(node, context, markdown);
                    }
                }
            } else {
                boolean isItemParagraph = ((ParagraphItemContainer) node.getParent()).isItemParagraph(node);
                if (isItemParagraph) {
                    if (formatterOptions.blankLinesInAst) {
                        FormatterUtils.renderLooseItemParagraph(node, context, markdown);
                    } else {
                        ListSpacing itemSpacing = FormatterUtils.LIST_ITEM_SPACING.get(context.getDocument());
                        if (itemSpacing == ListSpacing.TIGHT) {
                            FormatterUtils.renderTextBlockParagraphLines(node, context, markdown);
                        } else if (itemSpacing == ListSpacing.LOOSE) {
                            if (node.getParent().getNextAnyNot(BlankLine.class) == null) {
                                FormatterUtils.renderTextBlockParagraphLines(node, context, markdown);
                            } else {
                                FormatterUtils.renderLooseItemParagraph(node, context, markdown);
                            }
                        } else {
                            if (!((ParagraphItemContainer) node.getParent()).isParagraphWrappingDisabled(node, listOptions, context.getOptions())) {
                                FormatterUtils.renderLooseItemParagraph(node, context, markdown);
                            } else {
                                FormatterUtils.renderTextBlockParagraphLines(node, context, markdown);
                            }
                        }
                    }
                } else {
                    FormatterUtils.renderLooseParagraph(node, context, markdown);
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
            // FIX: this really needs to be parsed, but it is not done in the parser
            render((HtmlBlockBase) node, context, markdown);
            markdown.tailBlankLine();
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

    private void render(HtmlBlockBase node, NodeFormatterContext context, MarkdownWriter markdown) {
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
                // NOTE: to allow changing node chars before formatting, need to make sure the node's chars were not changed before using content lines
                markdown.openPreFormatted(true);
                BasedSequence spanningChars = node.getSpanningChars();
                if (spanningChars.equals(node.getChars())) {
                    for (BasedSequence line : node.getContentLines()) {
                        markdown.append(line);
                    }
                } else {
                    markdown.append(node.getChars());
                }
                markdown.line().closePreFormatted();
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

    final public static DataKey<Boolean> UNWRAPPED_AUTO_LINKS = new DataKey<>("UNWRAPPED_AUTO_LINKS", false);
    final public static DataKey<HashSet<String>> UNWRAPPED_AUTO_LINKS_MAP = new DataKey<>("UNWRAPPED_AUTO_LINKS_MAP", HashSet::new);

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
                if (formatterOptions.rightMargin > 0) {
                    // no wrapping of link text
                    markdown.append(node.getText().toMapped(SpaceMapper.toNonBreakSpace));
                } else {
                    context.renderChildren(node);
                }
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
                if (formatterOptions.rightMargin > 0) {
                    // no wrapping of link text
                    markdown.append(node.getText().toMapped(SpaceMapper.toNonBreakSpace));
                } else {
                    context.renderChildren(node);
                }
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
