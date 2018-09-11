package com.vladsch.flexmark.formatter.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.RenderPurpose;
import com.vladsch.flexmark.formatter.TranslatingSpanRender;
import com.vladsch.flexmark.formatter.TranslationPlaceholderGenerator;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.Consumer;
import com.vladsch.flexmark.util.Ref;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.format.options.ElementPlacement;
import com.vladsch.flexmark.util.format.options.ElementPlacementSort;
import com.vladsch.flexmark.util.format.options.ListSpacing;
import com.vladsch.flexmark.util.html.FormattingAppendable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RepeatedCharSequence;

import java.util.*;

import static com.vladsch.flexmark.formatter.RenderPurpose.FORMAT;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.ADD;
import static com.vladsch.flexmark.util.format.options.DiscretionaryText.AS_IS;
import static com.vladsch.flexmark.util.sequence.BasedSequence.NULL;

@SuppressWarnings("WeakerAccess")
public class CoreNodeFormatter extends NodeRepositoryFormatter<ReferenceRepository, Reference, RefNode> {
    public static final DataKey<Integer> LIST_ITEM_NUMBER = new DataKey<Integer>("LIST_ITEM_NUMBER", 0);
    public static final DataKey<ListSpacing> LIST_ITEM_SPACING = new DataKey<ListSpacing>("LIST_ITEM_SPACING", (ListSpacing) null);

    private final FormatterOptions formatterOptions;
    private final ListOptions listOptions;
    private final String myHtmlBlockPrefix;
    private int blankLines;
    MutableDataHolder myTranslationStore;

    public CoreNodeFormatter(DataHolder options) {
        super(options, null);
        this.formatterOptions = new FormatterOptions(options);
        this.listOptions = ListOptions.getFrom(options);
        blankLines = 0;
        myHtmlBlockPrefix = "<" + this.formatterOptions.translationHtmlBlockPrefix;
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        if (formatterOptions.referencePlacement != ElementPlacement.AS_IS && formatterOptions.referenceSort != ElementPlacementSort.SORT_UNUSED_LAST) return null;
        //noinspection unchecked,ArraysAsListWithZeroOrOneArgument
        return new HashSet<Class<?>>(Arrays.asList(
                RefNode.class
        ));
    }

    @Override
    public ReferenceRepository getRepository(final DataHolder options) {
        return options.get(Parser.REFERENCES);
    }

    @Override
    public ElementPlacement getReferencePlacement() {
        return formatterOptions.referencePlacement;
    }

    @Override
    public ElementPlacementSort getReferenceSort() {
        return formatterOptions.referenceSort;
    }

    @Override
    public void renderReferenceBlock(final Reference node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            markdown.append(node.getOpeningMarker());
            markdown.appendTranslating(node.getReference());
            markdown.append(node.getClosingMarker());

            markdown.append(' ');

            markdown.append(node.getUrlOpeningMarker());
            markdown.appendNonTranslating(node.getPageRef());

            markdown.append(node.getAnchorMarker());
            if (node.getAnchorRef().isNotNull()) {
                CharSequence anchorRef = context.transformAnchorRef(node.getPageRef(), node.getAnchorRef());
                markdown.append(anchorRef);
            }
            markdown.append(node.getUrlClosingMarker());

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
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<? extends Node>>(Arrays.asList(
                // Generic unknown node formatter
                new NodeFormattingHandler<Node>(Node.class, new CustomNodeFormatter<Node>() {
                    @Override
                    public void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),

                new NodeFormattingHandler<AutoLink>(AutoLink.class, new CustomNodeFormatter<AutoLink>() {
                    @Override
                    public void render(AutoLink node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<BlankLine>(BlankLine.class, new CustomNodeFormatter<BlankLine>() {
                    @Override
                    public void render(BlankLine node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<BlockQuote>(BlockQuote.class, new CustomNodeFormatter<BlockQuote>() {
                    @Override
                    public void render(BlockQuote node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Code>(Code.class, new CustomNodeFormatter<Code>() {
                    @Override
                    public void render(Code node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Document>(Document.class, new CustomNodeFormatter<Document>() {
                    @Override
                    public void render(Document node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Emphasis>(Emphasis.class, new CustomNodeFormatter<Emphasis>() {
                    @Override
                    public void render(Emphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<FencedCodeBlock>(FencedCodeBlock.class, new CustomNodeFormatter<FencedCodeBlock>() {
                    @Override
                    public void render(FencedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HardLineBreak>(HardLineBreak.class, new CustomNodeFormatter<HardLineBreak>() {
                    @Override
                    public void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Heading>(Heading.class, new CustomNodeFormatter<Heading>() {
                    @Override
                    public void render(Heading node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlBlock>(HtmlBlock.class, new CustomNodeFormatter<HtmlBlock>() {
                    @Override
                    public void render(HtmlBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlCommentBlock>(HtmlCommentBlock.class, new CustomNodeFormatter<HtmlCommentBlock>() {
                    @Override
                    public void render(HtmlCommentBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlInnerBlock>(HtmlInnerBlock.class, new CustomNodeFormatter<HtmlInnerBlock>() {
                    @Override
                    public void render(HtmlInnerBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlInnerBlockComment>(HtmlInnerBlockComment.class, new CustomNodeFormatter<HtmlInnerBlockComment>() {
                    @Override
                    public void render(HtmlInnerBlockComment node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlEntity>(HtmlEntity.class, new CustomNodeFormatter<HtmlEntity>() {
                    @Override
                    public void render(HtmlEntity node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlInline>(HtmlInline.class, new CustomNodeFormatter<HtmlInline>() {
                    @Override
                    public void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<HtmlInlineComment>(HtmlInlineComment.class, new CustomNodeFormatter<HtmlInlineComment>() {
                    @Override
                    public void render(HtmlInlineComment node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Image>(Image.class, new CustomNodeFormatter<Image>() {
                    @Override
                    public void render(Image node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<ImageRef>(ImageRef.class, new CustomNodeFormatter<ImageRef>() {
                    @Override
                    public void render(ImageRef node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<IndentedCodeBlock>(IndentedCodeBlock.class, new CustomNodeFormatter<IndentedCodeBlock>() {
                    @Override
                    public void render(IndentedCodeBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Link>(Link.class, new CustomNodeFormatter<Link>() {
                    @Override
                    public void render(Link node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<LinkRef>(LinkRef.class, new CustomNodeFormatter<LinkRef>() {
                    @Override
                    public void render(LinkRef node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<BulletList>(BulletList.class, new CustomNodeFormatter<BulletList>() {
                    @Override
                    public void render(BulletList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<OrderedList>(OrderedList.class, new CustomNodeFormatter<OrderedList>() {
                    @Override
                    public void render(OrderedList node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<BulletListItem>(BulletListItem.class, new CustomNodeFormatter<BulletListItem>() {
                    @Override
                    public void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<OrderedListItem>(OrderedListItem.class, new CustomNodeFormatter<OrderedListItem>() {
                    @Override
                    public void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<MailLink>(MailLink.class, new CustomNodeFormatter<MailLink>() {
                    @Override
                    public void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Paragraph>(Paragraph.class, new CustomNodeFormatter<Paragraph>() {
                    @Override
                    public void render(Paragraph node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Reference>(Reference.class, new CustomNodeFormatter<Reference>() {
                    @Override
                    public void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<SoftLineBreak>(SoftLineBreak.class, new CustomNodeFormatter<SoftLineBreak>() {
                    @Override
                    public void render(SoftLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<StrongEmphasis>(StrongEmphasis.class, new CustomNodeFormatter<StrongEmphasis>() {
                    @Override
                    public void render(StrongEmphasis node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<Text>(Text.class, new CustomNodeFormatter<Text>() {
                    @Override
                    public void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<TextBase>(TextBase.class, new CustomNodeFormatter<TextBase>() {
                    @Override
                    public void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                }),
                new NodeFormattingHandler<ThematicBreak>(ThematicBreak.class, new CustomNodeFormatter<ThematicBreak>() {
                    @Override
                    public void render(ThematicBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
                        CoreNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
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
            markdown.append(chars);
        }
    }

    private void render(BlankLine node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getDocument().get(LIST_ITEM_SPACING) == null) {
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

    private void render(final Heading node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.blankLine();
        if (node.isAtxHeading()) {
            markdown.append(node.getOpeningMarker());
            boolean spaceAfterAtx = formatterOptions.spaceAfterAtxMarker == ADD
                    || formatterOptions.spaceAfterAtxMarker == AS_IS && node.getOpeningMarker().getEndOffset() < node.getText().getStartOffset();

            if (spaceAfterAtx) markdown.append(' ');

            context.translatingRefTargetSpan(node, new TranslatingSpanRender() {
                @Override
                public void render(final NodeFormatterContext context, final MarkdownWriter writer) {
                    context.renderChildren(node);
                }
            });

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
        } else {
            Ref<Integer> ref = new Ref<Integer>(markdown.offset());
            markdown.lastOffset(ref);
            context.translatingRefTargetSpan(node, new TranslatingSpanRender() {
                @Override
                public void render(final NodeFormatterContext context, final MarkdownWriter writer) {
                    context.renderChildren(node);
                }
            });
            markdown.line();

            if (formatterOptions.setextHeaderEqualizeMarker) {
                markdown.repeat(node.getClosingMarker().charAt(0), Utils.minLimit(markdown.offset() - ref.value, formatterOptions.minSetextMarkerLength));
            } else {
                markdown.append(node.getClosingMarker());
            }
        }
        markdown.tailBlankLine();
    }

    private void render(final BlockQuote node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        String prefix = node.getOpeningMarker().toString();
        boolean compactPrefix = false;

        switch (formatterOptions.blockQuoteMarkers) {
            case AS_IS:
                prefix = node.getChars().baseSubSequence(node.getOpeningMarker().getStartOffset(), node.getFirstChild().getStartOffset()).toString();
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

        // delay prefix after EOL
        int markdownOptions = markdown.getOptions();
        markdown.setOptions(markdownOptions | FormattingAppendable.PREFIX_AFTER_PENDING_EOL);
        markdown.setPrefix(combinedPrefix);
        markdown.setOptions(markdownOptions);

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
        char closingMarkerChar = closingMarker.length() > 0 ? closingMarker.charAt(0) : '\0';
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

        openingMarker = RepeatedCharSequence.of(String.valueOf(openingMarkerChar), openingMarkerLen);
        if (formatterOptions.fencedCodeMatchClosingMarker || closingMarkerChar == '\0') closingMarker = openingMarker;
        else closingMarker = RepeatedCharSequence.of(String.valueOf(closingMarkerChar), closingMarkerLen);

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
                    leadColumns[i] = line.countLeadingColumns(0, " \t");
                    minSpaces = Utils.min(minSpaces, leadColumns[i]);
                    i++;
                }
                ArrayList<BasedSequence> trimmedLines = new ArrayList<BasedSequence>();
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
                        if (leadColumns[i] > minSpaces) markdown.repeat(' ', leadColumns[i] - minSpaces);
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
        String prefix = RepeatedCharSequence.of(" ", listOptions.getCodeIndent()).toString();

        if (formatterOptions.emulationProfile == ParserEmulationProfile.GITHUB_DOC) {
            if (node.getParent() instanceof ListItem) {
                BasedSequence marker = ((ListItem) node.getParent()).getOpeningMarker();
                prefix = RepeatedCharSequence.of(" ", Utils.minLimit(8 - marker.length() - 1, 4)).toString();
            }
        }

        markdown.pushPrefix().addPrefix(prefix);
        markdown.openPreFormatted(true);
        if (context.isTransformingText()) {
            markdown.appendNonTranslating(node.getContentChars());
        } else {
            if (formatterOptions.indentedCodeMinimizeIndent) {
                List<BasedSequence> lines = node.getContentLines();
                int[] leadColumns = new int[lines.size()];
                int minSpaces = Integer.MAX_VALUE;
                int i = 0;
                for (BasedSequence line : lines) {
                    leadColumns[i] = line.countLeadingColumns(0, " \t");
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
                        if (leadColumns[i] > minSpaces) markdown.repeat(' ', leadColumns[i] - minSpaces);
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
        markdown.popPrefix();
        markdown.tailBlankLine();
    }

    private void render(final BulletList node, final NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown);
    }

    private void render(final OrderedList node, final NodeFormatterContext context, MarkdownWriter markdown) {
        renderList(node, context, markdown);
    }

    private void render(BulletListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderListItem(node, context, markdown, listOptions, "", false);
    }

    private void render(OrderedListItem node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderListItem(node, context, markdown, listOptions, "", false);
    }

    public static void renderList(final ListBlock node, final NodeFormatterContext context, MarkdownWriter markdown) {
        ArrayList<Node> itemList = new ArrayList<Node>();
        Node item = node.getFirstChild();
        while (item != null) {
            itemList.add(item);
            item = item.getNext();
        }
        renderList(node, context, markdown, itemList);
    }

    public static void renderList(final ListBlock node, final NodeFormatterContext context, MarkdownWriter markdown, List<Node> itemList) {
        if (context.getFormatterOptions().listAddBlankLineBefore && !node.isOrDescendantOfType(ListItem.class)) {
            markdown.blankLine();
        }

        Document document = context.getDocument();
        ListSpacing listSpacing = document.get(LIST_ITEM_SPACING);
        int listItemNumber = document.get(LIST_ITEM_NUMBER);
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
            markdown.blankLine();
        }
    }

    public static void renderListItem(
            final ListItem node,
            final NodeFormatterContext context,
            final MarkdownWriter markdown,
            final ListOptions listOptions, CharSequence markerSuffix,
            final boolean addBlankLineLooseItems
    ) {
        final FormatterOptions options = context.getFormatterOptions();

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
                Integer itemNumber = document.get(LIST_ITEM_NUMBER);
                openingMarker = String.format("%d%c", itemNumber++, delimiter);
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
        markdown.append(openingMarker).append(' ').append(markerSuffix);
        markdown.pushPrefix().addPrefix(options.itemContentIndent ? RepeatedCharSequence.of(' ', openingMarker.length() + (listOptions.isItemContentAfterSuffix() ? markerSuffix.length() : 0) + 1)
                : RepeatedCharSequence.of(" ", listOptions.getItemIndent()).toString());

        if (node.hasChildren() && node.getFirstChildAnyNot(BlankLine.class) != null) {
            context.renderChildren(node);
            if (addBlankLineLooseItems && (node.isLoose() || node.getDocument().get(LIST_ITEM_SPACING) == ListSpacing.LOOSE)) {
                markdown.blankLine();
            }
        } else {
            if (node.isLoose()) {
                markdown.blankLine();
            } else {
                markdown.addLine();
            }
        }
        markdown.popPrefix();
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
    public static void renderTextBlockParagraphLines(final Node node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        context.translatingSpan(new TranslatingSpanRender() {
            @Override
            public void render(final NodeFormatterContext context, final MarkdownWriter writer) {
                context.renderChildren(node);
            }
        });
        markdown.line();
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseParagraph(final Paragraph node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        markdown.blankLine();
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    @SuppressWarnings("WeakerAccess")
    public static void renderLooseItemParagraph(final Paragraph node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        renderTextBlockParagraphLines(node, context, markdown);
        markdown.tailBlankLine();
    }

    private void render(final Paragraph node, final NodeFormatterContext context, final MarkdownWriter markdown) {
        if (node.getParent() instanceof ParagraphContainer) {
            final boolean startWrappingDisabled = ((ParagraphContainer) node.getParent()).isParagraphStartWrappingDisabled(node);
            final boolean endWrappingDisabled = ((ParagraphContainer) node.getParent()).isParagraphEndWrappingDisabled(node);
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
                    ListSpacing itemSpacing = context.getDocument().get(LIST_ITEM_SPACING);
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
        markdown.append(node.getChars());
    }

    private void render(HardLineBreak node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            markdown.append(node.getChars());
        } else {
            markdown.append(node.getChars());
        }
    }

    static final TranslationPlaceholderGenerator htmlEntityPlaceholderGenerator = new TranslationPlaceholderGenerator() {
        @Override
        public String getPlaceholder(final int index) {
            return String.format("&#%d;", index);
        }
    };

    private void render(final HtmlEntity node, NodeFormatterContext context, final MarkdownWriter markdown) {
        if (context.getRenderPurpose() == FORMAT) {
            markdown.append(node.getChars());
        } else {
            context.customPlaceholderFormat(htmlEntityPlaceholderGenerator, new TranslatingSpanRender() {
                @Override
                public void render(final NodeFormatterContext context, final MarkdownWriter markdown) {
                    markdown.appendNonTranslating(node.getChars());
                }
            });
        }
    }

    private void render(Text node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getChars());
    }

    private void render(TextBase node, NodeFormatterContext context, MarkdownWriter markdown) {
        context.renderChildren(node);
    }

    private void render(Code node, NodeFormatterContext context, MarkdownWriter markdown) {
        markdown.append(node.getOpeningMarker());
        markdown.appendNonTranslating(node.getText());
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
        final BasedSequence text = node.getChars().subSequence(4, node.getChars().length() - 4);
        markdown.appendTranslating("<!--", text, "-->", node.getChars().trimmedEOL());
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
        final BasedSequence text = node.getChars().subSequence(4, node.getChars().length() - 3);
        markdown.appendTranslating("<!--", text, "-->");
        //markdown.append(node.getChars());
    }

    private void render(HtmlInline node, NodeFormatterContext context, MarkdownWriter markdown) {
        switch (context.getRenderPurpose()) {
            case TRANSLATION_SPANS:
            case TRANSLATED_SPANS:
                String prefix = node.getChars().startsWith("</") ? "</" : "<";
                markdown.appendNonTranslating(prefix, node.getChars(), ">");
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
        final BasedSequence text = node.getChars().subSequence(4, node.getChars().length() - 3);
        markdown.appendTranslating("<!--", text, "-->");
    }

    private void render(Reference node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderReference(node, context, markdown);
    }

    public static final DataKey<Boolean> UNWRAPPED_AUTO_LINKS = new DataKey<>("UNWRAPPED_AUTO_LINKS", false);
    public static final DataKey<HashSet<String>> UNWRAPPED_AUTO_LINKS_MAP = new DataKey<>("UNWRAPPED_AUTO_LINKS_MAP", new HashSet<String>());

    private void render(AutoLink node, NodeFormatterContext context, final MarkdownWriter markdown) {
        renderAutoLink(node, context, markdown, "hh://", ".h");
    }

    private void render(MailLink node, NodeFormatterContext context, MarkdownWriter markdown) {
        renderAutoLink(node, context, markdown, null,"@1.h");
    }

    private void renderAutoLink(final DelimitedLinkNode node, final NodeFormatterContext context, final MarkdownWriter markdown, final String prefix, final String suffix) {
        if (context.isTransformingText()) {
            switch (context.getRenderPurpose()) {
                case TRANSLATION_SPANS:
                    if (node.getOpeningMarker().isNull()) {
                        // unwrapped, need to store it
                        myTranslationStore.set(UNWRAPPED_AUTO_LINKS, true);
                        markdown.append("<");
                        markdown.appendNonTranslating(prefix, node.getText(), suffix, null, new Consumer<String>() {
                            @Override
                            public void accept(final String s) {
                                myTranslationStore.get(UNWRAPPED_AUTO_LINKS_MAP).add(s);
                            }
                        });
                        markdown.append(">");
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
                    if (myTranslationStore.get(UNWRAPPED_AUTO_LINKS) && myTranslationStore.get(UNWRAPPED_AUTO_LINKS_MAP).contains(node.getText().toString())) {
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
        markdown.lineIf(formatterOptions.keepImageLinksAtStart);
        if (context.isTransformingText()) {
            markdown.append(node.getTextOpeningMarker());
            markdown.appendTranslating(node.getText());
            markdown.append(node.getTextClosingMarker());

            markdown.append(node.getLinkOpeningMarker());

            markdown.append(node.getUrlOpeningMarker());
            markdown.appendNonTranslating(node.getPageRef());
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
        if (context.isTransformingText()) {
            markdown.append(node.getTextOpeningMarker());
            if (node.getText().isNotNull()) {
                if (node.getFirstChildAny(HtmlInline.class) != null) {
                    context.renderChildren(node);
                } else {
                    markdown.appendTranslating(node.getText());
                }
            }
            markdown.append(node.getTextClosingMarker());

            markdown.append(node.getLinkOpeningMarker());

            markdown.append(node.getUrlOpeningMarker());
            markdown.appendNonTranslating(node.getPageRef());

            markdown.append(node.getAnchorMarker());
            if (node.getAnchorRef().isNotNull()) {
                CharSequence anchorRef = context.transformAnchorRef(node.getPageRef(), node.getAnchorRef());
                markdown.append(anchorRef);
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
        if (context.isTransformingText()) {
            if (node.isReferenceTextCombined()) {
                markdown.append(node.getReferenceOpeningMarker());
                markdown.appendTranslating(node.getReference());
                markdown.append(node.getReferenceClosingMarker());

                markdown.append(node.getTextOpeningMarker());
                if (node.getText().isNotNull()) markdown.appendTranslating(node.getText());
                markdown.append(node.getTextClosingMarker());
            } else {
                markdown.append(node.getTextOpeningMarker());
                markdown.appendTranslating(node.getText());
                markdown.append(node.getTextClosingMarker());

                markdown.append(node.getReferenceOpeningMarker());
                markdown.appendTranslating(node.getReference());
                markdown.append(node.getReferenceClosingMarker());
            }
        } else {
            markdown.append(node.getChars());
        }
    }

    private void render(final LinkRef node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (context.isTransformingText()) {
            if (node.isReferenceTextCombined()) {
                markdown.append(node.getReferenceOpeningMarker());
                markdown.appendTranslating(node.getReference());
                markdown.append(node.getReferenceClosingMarker());

                markdown.append(node.getTextOpeningMarker());
                if (node.getText().isNotNull()) {
                    if (node.getFirstChildAny(HtmlInline.class) != null) {
                        context.renderChildren(node);
                    } else {
                        markdown.appendTranslating(node.getText());
                    }
                }
                markdown.append(node.getTextClosingMarker());
            } else {
                markdown.append(node.getTextOpeningMarker());
                if (node.getText().isNotNull()) {
                    if (node.getFirstChildAny(HtmlInline.class) != null) {
                        context.renderChildren(node);
                    } else {
                        markdown.appendTranslating(node.getText());
                    }
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
}
