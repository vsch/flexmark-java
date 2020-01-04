package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.ast.util.ReferenceRepository;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferenceNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Reference extends LinkNodeBase implements ReferenceNode<ReferenceRepository, Reference, RefNode> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence reference = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                reference,
                closingMarker,
                urlOpeningMarker,
                url,
                pageRef,
                anchorMarker,
                anchorRef,
                urlClosingMarker,
                titleOpeningMarker,
                title,
                titleClosingMarker
        };
    }

    @NotNull
    @Override
    public BasedSequence[] getSegmentsForChars() {
        return new BasedSequence[] {
                openingMarker,
                reference,
                closingMarker,
                PrefixedSubSequence.prefixOf(" ", closingMarker.getEmptySuffix()),
                urlOpeningMarker,
                pageRef,
                anchorMarker,
                anchorRef,
                urlClosingMarker,
                titleOpeningMarker,
                title,
                titleClosingMarker
        };
    }

    @Override
    public int compareTo(Reference other) {
        return SequenceUtils.compare(getReference(), other.getReference(), true);
    }

    @Nullable
    @Override
    public RefNode getReferencingNode(@NotNull Node node) {
        return node instanceof RefNode ? (RefNode) node : null;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, reference, closingMarker, "ref");
        delimitedSegmentSpanChars(out, urlOpeningMarker, url, urlClosingMarker, "url");
        delimitedSegmentSpanChars(out, titleOpeningMarker, title, titleClosingMarker, "title");
    }

    public Reference(BasedSequence label, BasedSequence url, BasedSequence title) {
        super(BasedSequence.NULL);

        this.openingMarker = label.subSequence(0, 1);
        this.reference = label.subSequence(1, label.length() - 2).trim();
        this.closingMarker = label.subSequence(label.length() - 2, label.length());

        setUrlChars(url);

        if (title != null) {
            this.titleOpeningMarker = title.subSequence(0, 1);
            this.title = title.subSequence(1, title.length() - 1);
            this.titleClosingMarker = title.subSequence(title.length() - 1, title.length());
        }
        setCharsFromContent();
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getUrlOpeningMarker() {
        return urlOpeningMarker;
    }

    public void setUrlOpeningMarker(BasedSequence urlOpeningMarker) {
        this.urlOpeningMarker = urlOpeningMarker;
    }

    public BasedSequence getUrlClosingMarker() {
        return urlClosingMarker;
    }

    public void setUrlClosingMarker(BasedSequence urlClosingMarker) {
        this.urlClosingMarker = urlClosingMarker;
    }

    public BasedSequence getTitleOpeningMarker() {
        return titleOpeningMarker;
    }

    public void setTitleOpeningMarker(BasedSequence titleOpeningMarker) {
        this.titleOpeningMarker = titleOpeningMarker;
    }

    public BasedSequence getTitleClosingMarker() {
        return titleClosingMarker;
    }

    public void setTitleClosingMarker(BasedSequence titleClosingMarker) {
        this.titleClosingMarker = titleClosingMarker;
    }

    public BasedSequence getReference() {
        return reference;
    }

    public void setReference(BasedSequence reference) {
        this.reference = reference;
    }

    public BasedSequence getUrl() {
        return url;
    }

    public void setUrl(BasedSequence url) {
        this.url = url;
    }

    public BasedSequence getPageRef() {
        return pageRef;
    }

    public void setPageRef(BasedSequence pageRef) {
        this.pageRef = pageRef;
    }

    public BasedSequence getAnchorMarker() {
        return anchorMarker;
    }

    public void setAnchorMarker(BasedSequence anchorMarker) {
        this.anchorMarker = anchorMarker;
    }

    public BasedSequence getAnchorRef() {
        return anchorRef;
    }

    public void setAnchorRef(BasedSequence anchorRef) {
        this.anchorRef = anchorRef;
    }

    public BasedSequence getTitle() {
        return title;
    }

    public void setTitle(BasedSequence title) {
        this.title = title;
    }

    @NotNull
    @Override
    protected String toStringAttributes() {
        return "reference=" + reference + ", url=" + url;
    }
}
