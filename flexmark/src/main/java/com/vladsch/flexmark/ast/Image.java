package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class Image extends InlineLinkNode {
  private BasedSequence urlContent = BasedSequence.NULL;

  @NotNull
  @Override
  public BasedSequence[] getSegments() {
    return new BasedSequence[] {
      textOpeningMarker,
      text,
      textClosingMarker,
      linkOpeningMarker,
      urlOpeningMarker,
      url,
      pageRef,
      anchorMarker,
      anchorRef,
      urlClosingMarker,
      urlContent,
      titleOpeningMarker,
      titleOpeningMarker,
      title,
      titleClosingMarker,
      linkClosingMarker
    };
  }

  @Override
  public void getAstExtra(@NotNull StringBuilder out) {
    delimitedSegmentSpanChars(out, textOpeningMarker, text, textClosingMarker, "text");
    segmentSpanChars(out, linkOpeningMarker, "linkOpen");
    delimitedSegmentSpanChars(out, urlOpeningMarker, url, urlClosingMarker, "url");
    if (pageRef.isNotNull()) segmentSpanChars(out, pageRef, "pageRef");
    if (anchorMarker.isNotNull()) segmentSpanChars(out, anchorMarker, "anchorMarker");
    if (anchorRef.isNotNull()) segmentSpanChars(out, anchorRef, "anchorRef");
    if (urlContent.isNotNull()) segmentSpanChars(out, urlContent, "urlContent");
    delimitedSegmentSpanChars(out, titleOpeningMarker, title, titleClosingMarker, "title");
    segmentSpanChars(out, linkClosingMarker, "linkClose");
  }

  public Image() {}

  @Override
  public void setTextChars(BasedSequence textChars) {
    int textCharsLength = textChars.length();
    this.textOpeningMarker = textChars.subSequence(0, 2);
    this.text = textChars.subSequence(2, textCharsLength - 1).trim();
    this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
  }

  public void setUrlContent(BasedSequence urlContent) {
    this.urlContent = urlContent;
  }

  public BasedSequence getUrlContent() {
    return urlContent;
  }
}
