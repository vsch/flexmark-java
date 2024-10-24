package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.ast.AnchorRefTarget;
import com.vladsch.flexmark.ast.util.AnchorRefTargetBlockPreVisitor;
import com.vladsch.flexmark.ast.util.AnchorRefTargetBlockVisitor;
import com.vladsch.flexmark.html.Disposable;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.HashMap;
import java.util.Map;

public class HeaderIdGenerator implements HtmlIdGenerator, Disposable {
  private Map<String, Integer> headerBaseIds = new HashMap<>();
  private boolean resolveDupes;
  private String toDashChars;
  private String nonDashChars;
  private boolean noDupedDashes;
  private boolean nonAsciiToLowercase;

  public HeaderIdGenerator() {
    this(null);
  }

  private HeaderIdGenerator(DataHolder options) {
    resolveDupes = HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES.get(options);
    toDashChars = HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS.get(options);
    nonDashChars = HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS.get(options);
    noDupedDashes = HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES.get(options);
    nonAsciiToLowercase = HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE.get(options);
  }

  @Override
  public void dispose() {
    headerBaseIds = null;
  }

  public boolean isResolveDupes() {
    return resolveDupes;
  }

  public void setResolveDupes(boolean resolveDupes) {
    this.resolveDupes = resolveDupes;
  }

  public String getToDashChars() {
    return toDashChars;
  }

  public void setToDashChars(String toDashChars) {
    this.toDashChars = toDashChars;
  }

  public String getNonDashChars() {
    return nonDashChars;
  }

  public void setNonDashChars(String nonDashChars) {
    this.nonDashChars = nonDashChars;
  }

  public boolean isNoDupedDashes() {
    return noDupedDashes;
  }

  public void setNoDupedDashes(boolean noDupedDashes) {
    this.noDupedDashes = noDupedDashes;
  }

  public boolean isNonAsciiToLowercase() {
    return nonAsciiToLowercase;
  }

  public void setNonAsciiToLowercase(boolean nonAsciiToLowercase) {
    this.nonAsciiToLowercase = nonAsciiToLowercase;
  }

  @Override
  public void generateIds(Document document) {
    generateIds(document, null);
  }

  @Override
  public void generateIds(Document document, AnchorRefTargetBlockPreVisitor preVisitor) {
    headerBaseIds.clear();

    resolveDupes = HtmlRenderer.HEADER_ID_GENERATOR_RESOLVE_DUPES.get(document);
    toDashChars = HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS.get(document);
    nonDashChars = HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS.get(document);
    noDupedDashes = HtmlRenderer.HEADER_ID_GENERATOR_NO_DUPED_DASHES.get(document);
    nonAsciiToLowercase = HtmlRenderer.HEADER_ID_GENERATOR_NON_ASCII_TO_LOWERCASE.get(document);

    new AnchorRefTargetBlockVisitor() {
      @Override
      protected boolean preVisit(Node node) {
        return preVisitor == null || preVisitor.preVisit(node, this);
      }

      @Override
      protected void visit(AnchorRefTarget node) {
        if (node.getAnchorRefId().isEmpty()) {
          String text = node.getAnchorRefText();
          String refId = null;

          refId = generateId(text);

          if (refId != null) {
            node.setAnchorRefId(refId);
          }
        }
      }
    }.visit(document);
  }

  private String generateId(String text) {
    if (!text.isEmpty()) {
      String baseRefId =
          generateId(text, toDashChars, nonDashChars, noDupedDashes, nonAsciiToLowercase);

      if (resolveDupes) {
        if (headerBaseIds.containsKey(baseRefId)) {
          int index = headerBaseIds.get(baseRefId);

          index++;
          headerBaseIds.put(baseRefId, index);
          baseRefId += "-" + index;
        } else {
          headerBaseIds.put(baseRefId, 0);
        }
      }

      return baseRefId;
    }
    return null;
  }

  @Override
  public String getId(Node node) {
    return node instanceof AnchorRefTarget ? ((AnchorRefTarget) node).getAnchorRefId() : null;
  }

  @Override
  public String getId(CharSequence text) {
    return generateId(text.toString());
  }

  private static String generateId(
      CharSequence headerText,
      String toDashChars,
      String nonDashChars,
      boolean noDupedDashes,
      boolean nonAsciiToLowercase) {
    int iMax = headerText.length();
    StringBuilder baseRefId = new StringBuilder(iMax);
    if (toDashChars == null) toDashChars = HtmlRenderer.HEADER_ID_GENERATOR_TO_DASH_CHARS.get(null);
    if (nonDashChars == null)
      nonDashChars = HtmlRenderer.HEADER_ID_GENERATOR_NON_DASH_CHARS.get(null);

    for (int i = 0; i < iMax; i++) {
      char c = headerText.charAt(i);
      if (isAlphabetic(c)) {
        if (!nonAsciiToLowercase && !(c >= 'A' && c <= 'Z')) {
          baseRefId.append(c);
        } else {
          baseRefId.append(Character.toLowerCase(c));
        }
      } else if (Character.isDigit(c)) {
        baseRefId.append(c);
      } else if (nonDashChars.indexOf(c) != -1) {
        baseRefId.append(c);
      } else if (toDashChars.indexOf(c) != -1
          && (!noDupedDashes
              || ((c == '-' && baseRefId.length() == 0)
                  || baseRefId.length() != 0 && baseRefId.charAt(baseRefId.length() - 1) != '-')))
        baseRefId.append('-');
    }
    return baseRefId.toString();
  }

  private static boolean isAlphabetic(char c) {
    return (((((1 << Character.UPPERCASE_LETTER)
                    | (1 << Character.LOWERCASE_LETTER)
                    | (1 << Character.TITLECASE_LETTER)
                    | (1 << Character.MODIFIER_LETTER)
                    | (1 << Character.OTHER_LETTER)
                    | (1 << Character.LETTER_NUMBER))
                >> Character.getType((int) c))
            & 1)
        != 0);
  }

  public static class Factory implements HeaderIdGeneratorFactory {

    @Override
    public HeaderIdGenerator create(LinkResolverContext context) {
      return new HeaderIdGenerator();
    }

    @Override
    public HeaderIdGenerator create() {
      return new HeaderIdGenerator();
    }
  }
}
