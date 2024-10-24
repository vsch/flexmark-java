package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferenceNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;

public class Reference extends LinkNodeBase implements ReferenceNode<Reference, RefNode> {
  private BasedSequence openingMarker = BasedSequence.NULL;
  private BasedSequence reference = BasedSequence.NULL;
  private BasedSequence closingMarker = BasedSequence.NULL;

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

  @Override
  public RefNode getReferencingNode(Node node) {
    return node instanceof RefNode ? (RefNode) node : null;
  }

  @Override
  public void getAstExtra(StringBuilder out) {
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

  @Override
  public BasedSequence getUrlOpeningMarker() {
    return urlOpeningMarker;
  }

  @Override
  public void setUrlOpeningMarker(BasedSequence urlOpeningMarker) {
    this.urlOpeningMarker = urlOpeningMarker;
  }

  @Override
  public BasedSequence getUrlClosingMarker() {
    return urlClosingMarker;
  }

  @Override
  public void setUrlClosingMarker(BasedSequence urlClosingMarker) {
    this.urlClosingMarker = urlClosingMarker;
  }

  @Override
  public BasedSequence getTitleOpeningMarker() {
    return titleOpeningMarker;
  }

  @Override
  public void setTitleOpeningMarker(BasedSequence titleOpeningMarker) {
    this.titleOpeningMarker = titleOpeningMarker;
  }

  @Override
  public BasedSequence getTitleClosingMarker() {
    return titleClosingMarker;
  }

  @Override
  public void setTitleClosingMarker(BasedSequence titleClosingMarker) {
    this.titleClosingMarker = titleClosingMarker;
  }

  public BasedSequence getReference() {
    return reference;
  }

  public void setReference(BasedSequence reference) {
    this.reference = reference;
  }

  @Override
  public BasedSequence getUrl() {
    return url;
  }

  @Override
  public void setUrl(BasedSequence url) {
    this.url = url;
  }

  @Override
  public BasedSequence getPageRef() {
    return pageRef;
  }

  @Override
  public void setPageRef(BasedSequence pageRef) {
    this.pageRef = pageRef;
  }

  @Override
  public BasedSequence getAnchorMarker() {
    return anchorMarker;
  }

  @Override
  public void setAnchorMarker(BasedSequence anchorMarker) {
    this.anchorMarker = anchorMarker;
  }

  @Override
  public BasedSequence getAnchorRef() {
    return anchorRef;
  }

  @Override
  public void setAnchorRef(BasedSequence anchorRef) {
    this.anchorRef = anchorRef;
  }

  @Override
  public BasedSequence getTitle() {
    return title;
  }

  @Override
  public void setTitle(BasedSequence title) {
    this.title = title;
  }

  @Override
  protected String toStringAttributes() {
    return "reference=" + reference + ", url=" + url;
  }
}
