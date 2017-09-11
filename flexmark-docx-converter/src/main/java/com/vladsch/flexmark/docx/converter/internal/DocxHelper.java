package com.vladsch.flexmark.docx.converter.internal;

import com.vladsch.flexmark.docx.converter.DocxRendererContext;
import com.vladsch.flexmark.util.Function;
import org.docx4j.model.PropertyResolver;
import org.docx4j.model.styles.StyleUtil;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.openpackaging.parts.WordprocessingML.NumberingDefinitionsPart;
import org.docx4j.wml.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;

import static java.math.BigInteger.ZERO;

public class DocxHelper {
    protected final WordprocessingMLPackage myPackage;
    protected final MainDocumentPart myDocumentPart;
    protected final ObjectFactory myFactory;
    protected PropertyResolver myResolver;

    public DocxHelper(final WordprocessingMLPackage mlPackage, final ObjectFactory factory) {
        myPackage = mlPackage;
        myFactory = factory;
        myDocumentPart = mlPackage.getMainDocumentPart();
    }

    public Style getStyle(String styleId) {
        return myDocumentPart.getStyleDefinitionsPart().getStyleById(styleId);
    }

    public PropertyResolver getResolver() {
        if (myResolver == null) {
            try {
                myResolver = new PropertyResolver(myPackage);
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }
        return myResolver;
    }

    public static BigInteger safeBigInt(BigInteger value) {
        return value == null ? ZERO : value;
    }

    public static BigInteger safeBigInt(BigInteger value, long nullValue) {
        return value == null ? BigInteger.valueOf(nullValue) : value;
    }

    public ObjectFactory getFactory() {
        return myFactory;
    }

    public enum CombineBigInt {
        NONE, // pass through
        ADD,  // add copy and orig
        MAX,  // max of copy and orig
        MIN,  // min of copy and orig
        ADD_OTHER,
        MAX_OTHER,
        MIN_OTHER,
        RANGE,;

        BigInteger combine(BigInteger orig, BigInteger copy, BigInteger other) {
            if (this == ADD) {
                return copy == null && orig == null ? null : safeBigInt(copy).add(safeBigInt(orig));
            } else if (this == MAX) {
                return copy == null && orig == null ? null : safeBigInt(copy).max(safeBigInt(orig));
            } else if (this == MIN) {
                return copy == null && orig == null ? null : safeBigInt(copy).max(safeBigInt(orig));
            } else if (this == ADD_OTHER) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).add(safeBigInt(orig).add(safeBigInt(other)));
            } else if (this == MAX_OTHER) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).max(safeBigInt(orig).max(safeBigInt(other)));
            } else if (this == MIN_OTHER) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).min(safeBigInt(orig).min(safeBigInt(other)));
            } else if (this == RANGE) {
                return copy == null && orig == null && other == null ? null : safeBigInt(copy).max(safeBigInt(orig).min(safeBigInt(other)));
            } else {
                return copy;
            }
        }
    }

    public void combine(PPrBase.Ind child, PPrBase.Ind parent, CombineBigInt left, CombineBigInt right) {
        if (parent != null) {
            child.setLeft(left.combine(child.getLeft(), parent.getLeft(), parent.getHanging()));
            child.setRight(right.combine(child.getRight(), parent.getRight(), parent.getHanging()));
        }
    }

    public void inheritInd(PPrBase child, PPrBase parent, CombineBigInt left, CombineBigInt right) {
        combine(child.getInd(), parent.getInd(), left, right);
    }

    public boolean has(Object value) {
        return value != null;
    }

    public BigInteger safeIndLeft(PPrBase pPrBase) {
        return pPrBase == null ? ZERO : safeIndLeft(pPrBase.getInd());
    }

    public BigInteger safeIndLeft(PPrBase pPrBase, long nullValue) {
        return pPrBase == null ? BigInteger.valueOf(nullValue) : safeIndLeft(pPrBase.getInd());
    }

    public BigInteger safeIndLeft(PPrBase.Ind ind) {
        return ind == null || ind.getLeft() == null ? ZERO : ind.getLeft();
    }

    public BigInteger safeIndLeft(PPrBase.Ind ind, long nullValue) {
        return ind == null || ind.getLeft() == null ? BigInteger.valueOf(nullValue) : ind.getLeft();
    }

    public BigInteger safeIndRight(PPrBase pPrBase) {
        return pPrBase == null ? ZERO : safeIndRight(pPrBase.getInd());
    }

    public BigInteger safeIndRight(PPrBase pPrBase, long nullValue) {
        return pPrBase == null ? BigInteger.valueOf(nullValue) : safeIndRight(pPrBase.getInd());
    }

    public BigInteger safeIndRight(PPrBase.Ind ind) {
        return ind == null || ind.getRight() == null ? ZERO : ind.getRight();
    }

    public BigInteger safeIndRight(PPrBase.Ind ind, long nullValue) {
        return ind == null || ind.getRight() == null ? BigInteger.valueOf(nullValue) : ind.getRight();
    }

    public BigInteger safeIndHanging(PPrBase pPrBase) {
        return pPrBase == null ? ZERO : safeIndHanging(pPrBase.getInd());
    }

    public BigInteger safeIndHanging(PPrBase pPrBase, long nullValue) {
        return pPrBase == null ? BigInteger.valueOf(nullValue) : safeIndHanging(pPrBase.getInd());
    }

    public BigInteger safeIndHanging(PPrBase.Ind ind) {
        return ind == null || ind.getHanging() == null ? ZERO : ind.getHanging();
    }

    public BigInteger safeIndHanging(PPrBase.Ind ind, long nullValue) {
        return ind == null || ind.getHanging() == null ? BigInteger.valueOf(nullValue) : ind.getHanging();
    }

    /*
    Spacing access
     */
    public BigInteger safeSpacingBefore(PPr pPr) {
        return pPr == null ? ZERO : safeSpacingBefore(pPr.getSpacing());
    }

    public BigInteger safeSpacingBefore(PPr pPr, long nullValue) {
        return pPr == null ? BigInteger.valueOf(nullValue) : safeSpacingBefore(pPr.getSpacing());
    }

    public BigInteger safeSpacingBefore(PPrBase.Spacing spacing) {
        return spacing == null || spacing.getBefore() == null ? ZERO : spacing.getBefore();
    }

    public BigInteger safeSpacingBefore(PPrBase.Spacing spacing, long nullValue) {
        return spacing == null || spacing.getBefore() == null ? BigInteger.valueOf(nullValue) : spacing.getBefore();
    }

    public BigInteger safeSpacingAfter(PPr pPr) {
        return pPr == null ? ZERO : safeSpacingAfter(pPr.getSpacing());
    }

    public BigInteger safeSpacingAfter(PPr pPr, long nullValue) {
        return pPr == null ? BigInteger.valueOf(nullValue) : safeSpacingAfter(pPr.getSpacing());
    }

    public BigInteger safeSpacingAfter(PPrBase.Spacing spacing) {
        return spacing == null || spacing.getAfter() == null ? ZERO : spacing.getAfter();
    }

    public BigInteger safeSpacingAfter(PPrBase.Spacing spacing, long nullValue) {
        return spacing == null || spacing.getAfter() == null ? BigInteger.valueOf(nullValue) : spacing.getAfter();
    }

    public BigInteger safeSpacingLine(PPr pPr) {
        return pPr == null ? ZERO : safeSpacingLine(pPr.getSpacing());
    }

    public BigInteger safeSpacingLine(PPr pPr, long nullValue) {
        return pPr == null ? BigInteger.valueOf(nullValue) : safeSpacingLine(pPr.getSpacing());
    }

    public BigInteger safeSpacingLine(PPrBase.Spacing spacing) {
        return spacing == null || spacing.getLine() == null ? ZERO : spacing.getLine();
    }

    public BigInteger safeSpacingLine(PPrBase.Spacing spacing, long nullValue) {
        return spacing == null || spacing.getLine() == null ? BigInteger.valueOf(nullValue) : spacing.getLine();
    }

    public PPrBase.PBdr ensurePBdr(PPrBase pPrBase) {
        if (pPrBase.getPBdr() == null) {
            PPrBase.PBdr pBdr = myFactory.createPPrBasePBdr();
            pPrBase.setPBdr(pBdr);
        }
        return pPrBase.getPBdr();
    }

    public PPrBase.Ind ensureInd(PPrBase pPrBase) {
        if (pPrBase.getInd() == null) {
            PPrBase.Ind ind = myFactory.createPPrBaseInd();
            pPrBase.setInd(ind);
        }
        return pPrBase.getInd();
    }

    public PPrBase.Spacing ensureSpacing(PPrBase pPrBase) {
        if (pPrBase.getSpacing() == null) {
            PPrBase.Spacing spacing = myFactory.createPPrBaseSpacing();
            pPrBase.setSpacing(spacing);
        }
        return pPrBase.getSpacing();
    }

    /**
     * Try to keep the border of the parent and offset the left border by the difference in indentation between the two so the border stays
     * aligned with the parent. Max offset for border is 31pt or 620tw
     * <p>
     * if the child has its own left border then nothing is done
     * <p>
     * other borders are not affected
     * <p>
     * Must be called after the child indent is set
     */
    public void inheritPBdr(PPr child, PPr parent) {
        parent = getResolver().getEffectivePPr(parent);
        PPr styledChild = getResolver().getEffectivePPr(child);

        if (has(parent.getPBdr()) && (!has(styledChild.getPBdr()) || !has(styledChild.getPBdr().getLeft()) && has(parent.getPBdr().getLeft()))) {
            PPrBase.Ind cInd = getCopy(styledChild.getInd(), true);
            PPrBase.Ind pInd = getCopy(parent.getInd(), false);
            CTBorder leftBorder = getCopy(parent.getPBdr().getLeft(), true);

            final PPrBase.NumPr numPr = styledChild.getNumPr();
            if (numPr != null) {
                // need to check that too, it may have settings we don't have
                NumberingDefinitionsPart ndp = myDocumentPart.getNumberingDefinitionsPart();
                if (ndp != null) {
                    final PPrBase.Ind ndpInd = ndp.getInd(numPr);
                    if (cInd.getLeft() == null) {
                        cInd.setLeft(ndpInd.getLeft());
                    }
                    if (cInd.getRight() == null) {
                        cInd.setRight(ndpInd.getRight());
                    }
                    if (cInd.getHanging() == null) {
                        cInd.setHanging(ndpInd.getHanging());
                    }
                }
            }

            // now add difference between the left indents
            final BigInteger indentDiff = safeIndLeft(cInd).subtract(safeIndHanging(cInd)).subtract(safeIndLeft(pInd));
            if (indentDiff.compareTo(ZERO) > 0) {
                final BigInteger[] divideAndRemainder = indentDiff.divideAndRemainder(BigInteger.valueOf(20));
                // convert to points and add to space
                BigInteger space = safeBigInt(leftBorder.getSpace()).add(divideAndRemainder[0]).min(BigInteger.valueOf(31));
                leftBorder.setSpace(space);

                int remainder = divideAndRemainder[1].intValue();
                if (remainder > 0) {
                    // need to adjust indent since we could not adjust space exactly or it will appear off to the eye
                    ensureInd(child);
                    child.getInd().setLeft(ZERO.max(safeIndLeft(cInd).subtract(BigInteger.valueOf(remainder))));
                }
            }

            ensurePBdr(child).setLeft(leftBorder);
        }
    }

    /**
     * @param child  PPr of child element to inherit indent
     * @param parent PPr of parent element, must be explicit or effective ppr
     */
    public void inheritInd(PPr child, PPr parent) {
        if (has(parent.getInd())) {
            PPr styledChild = getResolver().getEffectivePPr(child);

            PPrBase.Ind cInd = getCopy(styledChild.getInd(), true);
            PPrBase.Ind pInd = parent.getInd();

            final PPrBase.NumPr numPr = styledChild.getNumPr();
            if (numPr != null) {
                // need to check that too, it may have settings we don't have
                NumberingDefinitionsPart ndp = myDocumentPart.getNumberingDefinitionsPart();
                if (ndp != null) {
                    final PPrBase.Ind ndpInd = ndp.getInd(numPr);
                    if (cInd.getLeft() == null) {
                        cInd.setLeft(ndpInd.getLeft());
                    }
                    if (cInd.getRight() == null) {
                        cInd.setRight(ndpInd.getRight());
                    }
                    if (cInd.getHanging() == null) {
                        cInd.setHanging(ndpInd.getHanging());
                    }
                }
            }

            combine(cInd, pInd, CombineBigInt.ADD_OTHER, CombineBigInt.NONE);
            cInd = keepDiff(cInd, styledChild.getInd());
            child.setInd(cInd);
        }
    }

    public PPrBase.Ind getCopy(PPrBase.Ind copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.Ind result = myFactory.createPPrBaseInd();

            result.setLeft(copy.getLeft());
            result.setLeftChars(copy.getLeftChars());
            result.setRight(copy.getRight());
            result.setRightChars(copy.getRightChars());
            result.setHanging(copy.getHanging());
            result.setHangingChars(copy.getHangingChars());
            result.setFirstLine(copy.getFirstLine());
            result.setFirstLineChars(copy.getFirstLineChars());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseInd();
        }
        return null;
    }

    public PPrBase.Spacing getCopy(PPrBase.Spacing copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.Spacing result = myFactory.createPPrBaseSpacing();
            result.setAfter(copy.getAfter());
            result.setAfterLines(copy.getAfterLines());
            result.setBefore(copy.getBefore());
            result.setBeforeLines(copy.getBeforeLines());
            result.setLine(copy.getLine());
            result.setAfterAutospacing(copy.isAfterAutospacing());
            result.setBeforeAutospacing(copy.isBeforeAutospacing());
            result.setLineRule(copy.getLineRule());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseSpacing();
        }
        return null;
    }

    public PPrBase.NumPr.Ilvl getCopy(PPrBase.NumPr.Ilvl copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.NumPr.Ilvl result = myFactory.createPPrBaseNumPrIlvl();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseNumPrIlvl();
        }
        return null;
    }

    public PPrBase.NumPr.NumId getCopy(PPrBase.NumPr.NumId copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.NumPr.NumId result = myFactory.createPPrBaseNumPrNumId();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseNumPrNumId();
        }
        return null;
    }

    public CTTrackChangeNumbering getCopy(CTTrackChangeNumbering copy, boolean whenNull) {
        if (copy != null) {
            CTTrackChangeNumbering result = myFactory.createCTTrackChangeNumbering();
            result.setOriginal(copy.getOriginal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTrackChangeNumbering();
        }
        return null;
    }

    public CTTrackChange getCopy(CTTrackChange copy, boolean whenNull) {
        if (copy != null) {
            CTTrackChange result = myFactory.createCTTrackChange();
            result.setDate((XMLGregorianCalendar) copy.getDate().clone());
            result.setAuthor(copy.getAuthor());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTrackChange();
        }
        return null;
    }

    public PPrBase.NumPr getCopy(PPrBase.NumPr copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.NumPr result = myFactory.createPPrBaseNumPr();
            result.setIlvl(getCopy(copy.getIlvl(), whenNull));
            result.setNumId(getCopy(copy.getNumId(), whenNull));
            result.setNumberingChange(getCopy(copy.getNumberingChange(), whenNull));
            result.setIns(getCopy(copy.getIns(), whenNull));
            return result;
        }
        if (whenNull) {
            PPrBase.NumPr result = myFactory.createPPrBaseNumPr();
            result.setIlvl(getCopy((PPrBase.NumPr.Ilvl) null, whenNull));
            result.setNumId(getCopy((PPrBase.NumPr.NumId) null, whenNull));
            result.setNumberingChange(getCopy((CTTrackChangeNumbering) null, whenNull));
            result.setIns(getCopy((CTTrackChange) null, whenNull));
            return result;
        }
        return null;
    }

    public CTBorder getCopy(CTBorder copy, boolean whenNull) {
        if (copy != null) {
            CTBorder result = myFactory.createCTBorder();
            result.setVal(copy.getVal());
            result.setThemeColor(copy.getThemeColor());
            result.setSpace(copy.getSpace());
            result.setSz(copy.getSz());
            result.setFrame(copy.isFrame());
            result.setShadow(copy.isShadow());
            result.setColor(copy.getColor());
            result.setThemeShade(copy.getThemeShade());
            result.setThemeTint(copy.getThemeTint());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTBorder();
        }
        return null;
    }

    public PPrBase.PBdr getCopy(PPrBase.PBdr copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.PBdr result = myFactory.createPPrBasePBdr();
            result.setTop(getCopy(copy.getTop(), whenNull));
            result.setLeft(getCopy(copy.getLeft(), whenNull));
            result.setBottom(getCopy(copy.getBottom(), whenNull));
            result.setRight(getCopy(copy.getRight(), whenNull));
            result.setBetween(getCopy(copy.getBetween(), whenNull));
            result.setBar(getCopy(copy.getBar(), whenNull));
            return result;
        }
        if (whenNull) {
            PPrBase.PBdr result = myFactory.createPPrBasePBdr();
            result.setTop(getCopy((CTBorder) null, whenNull));
            result.setLeft(getCopy((CTBorder) null, whenNull));
            result.setBottom(getCopy((CTBorder) null, whenNull));
            result.setRight(getCopy((CTBorder) null, whenNull));
            result.setBetween(getCopy((CTBorder) null, whenNull));
            result.setBar(getCopy((CTBorder) null, whenNull));
            return result;
        }
        return null;
    }

    public PPrBase.PStyle getCopy(PPrBase.PStyle copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.PStyle result = myFactory.createPPrBasePStyle();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBasePStyle();
        }
        return null;
    }

    public CTFramePr getCopy(CTFramePr copy, boolean whenNull) {
        if (copy != null) {
            CTFramePr result = myFactory.createCTFramePr();
            result.setDropCap(copy.getDropCap());
            result.setLines(copy.getLines());
            result.setW(copy.getW());
            result.setH(copy.getH());
            result.setVSpace(copy.getVSpace());
            result.setHSpace(copy.getHSpace());
            result.setWrap(copy.getWrap());
            result.setHAnchor(copy.getHAnchor());
            result.setVAnchor(copy.getVAnchor());
            result.setX(copy.getX());
            result.setXAlign(copy.getXAlign());
            result.setY(copy.getY());
            result.setYAlign(copy.getYAlign());
            result.setHRule(copy.getHRule());
            result.setAnchorLock(copy.isAnchorLock());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTFramePr();
        }
        return null;
    }

    public CTShd getCopy(CTShd copy, boolean whenNull) {
        if (copy != null) {
            CTShd result = myFactory.createCTShd();
            result.setVal(copy.getVal());
            result.setColor(copy.getColor());
            result.setThemeColor(copy.getThemeColor());
            result.setThemeTint(copy.getThemeTint());
            result.setThemeShade(copy.getThemeShade());
            result.setFill(copy.getFill());
            result.setThemeFill(copy.getThemeFill());
            result.setThemeFillTint(copy.getThemeFillTint());
            result.setThemeFillShade(copy.getThemeFillShade());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTShd();
        }
        return null;
    }

    public Tabs getCopy(Tabs copy, boolean whenNull) {
        if (copy != null) {
            Tabs result = myFactory.createTabs();
            result.getTab().addAll(copy.getTab());
            return result;
        }
        if (whenNull) {
            return myFactory.createTabs();
        }
        return null;
    }

    public Jc getCopy(Jc copy, boolean whenNull) {
        if (copy != null) {
            Jc result = myFactory.createJc();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createJc();
        }
        return null;
    }

    public TextDirection getCopy(TextDirection copy, boolean whenNull) {
        if (copy != null) {
            TextDirection result = myFactory.createTextDirection();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createTextDirection();
        }
        return null;
    }

    public PPrBase.TextAlignment getCopy(PPrBase.TextAlignment copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.TextAlignment result = myFactory.createPPrBaseTextAlignment();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseTextAlignment();
        }
        return null;
    }

    public CTTextboxTightWrap getCopy(CTTextboxTightWrap copy, boolean whenNull) {
        if (copy != null) {
            CTTextboxTightWrap result = myFactory.createCTTextboxTightWrap();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTextboxTightWrap();
        }
        return null;
    }

    public PPrBase.OutlineLvl getCopy(PPrBase.OutlineLvl copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.OutlineLvl result = myFactory.createPPrBaseOutlineLvl();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseOutlineLvl();
        }
        return null;
    }

    public PPrBase.DivId getCopy(PPrBase.DivId copy, boolean whenNull) {
        if (copy != null) {
            PPrBase.DivId result = myFactory.createPPrBaseDivId();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createPPrBaseDivId();
        }
        return null;
    }

    public CTCnf getCopy(CTCnf copy, boolean whenNull) {
        if (copy != null) {
            CTCnf result = myFactory.createCTCnf();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTCnf();
        }
        return null;
    }

    public PPrBase getCopy(PPrBase copy, boolean whenNull) {
        if (copy != null) {
            PPrBase result = myFactory.createPPrBase();
            setPPrBase(result, copy, whenNull);
            return result;
        }
        if (whenNull) {
            PPrBase result = myFactory.createPPrBase();
            result.setPStyle(getCopy((PPrBase.PStyle) null, whenNull));
            result.setFramePr(getCopy((CTFramePr) null, whenNull));
            result.setNumPr(getCopy((PPrBase.NumPr) null, whenNull));
            result.setPBdr(getCopy((PPrBase.PBdr) null, whenNull));
            result.setShd(getCopy((CTShd) null, whenNull));
            result.setTabs(getCopy((Tabs) null, whenNull));
            result.setSpacing(getCopy((PPrBase.Spacing) null, whenNull));
            result.setInd(getCopy((PPrBase.Ind) null, whenNull));
            result.setJc(getCopy((Jc) null, whenNull));
            result.setTextDirection(getCopy((TextDirection) null, whenNull));
            result.setTextAlignment(getCopy((PPrBase.TextAlignment) null, whenNull));
            result.setTextboxTightWrap(getCopy((CTTextboxTightWrap) null, whenNull));
            result.setOutlineLvl(getCopy((PPrBase.OutlineLvl) null, whenNull));
            result.setDivId(getCopy((PPrBase.DivId) null, whenNull));
            result.setCnfStyle(getCopy((CTCnf) null, whenNull));
            return result;
        }
        return null;
    }

    public ParaRPr getCopy(ParaRPr copy, boolean whenNull) {
        if (copy != null) {
            ParaRPr result = myFactory.createParaRPr();
            result.setIns(getCopy(copy.getIns(), whenNull));
            result.setDel(getCopy(copy.getDel(), whenNull));
            result.setMoveFrom(getCopy(copy.getMoveFrom(), whenNull));
            result.setMoveTo(getCopy(copy.getMoveTo(), whenNull));
            setRPr(result, copy, whenNull);
            return result;
        }
        if (whenNull) {
            ParaRPr result = myFactory.createParaRPr();
            result.setIns(getCopy((CTTrackChange) null, whenNull));
            result.setDel(getCopy((CTTrackChange) null, whenNull));
            result.setMoveFrom(getCopy((CTTrackChange) null, whenNull));
            result.setMoveTo(getCopy((CTTrackChange) null, whenNull));
            setRPr(result, copy, whenNull);
            return result;
        }
        return null;
    }

    public RPr getCopy(RPr copy, boolean whenNull) {
        if (copy != null) {
            RPr result = myFactory.createRPr();
            setRPr(result, copy, whenNull);
            return result;
        }
        if (whenNull) {
            RPr result = myFactory.createRPr();
            setRPr(result, copy, whenNull);
            return result;
        }
        return null;
    }

    public RStyle getCopy(RStyle copy, boolean whenNull) {
        if (copy != null) {
            RStyle result = myFactory.createRStyle();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createRStyle();
        }
        return null;
    }

    public RFonts getCopy(RFonts copy, boolean whenNull) {
        if (copy != null) {
            RFonts result = myFactory.createRFonts();
            result.setHint(copy.getHint());
            result.setAscii(copy.getAscii());
            result.setHAnsi(copy.getHAnsi());
            result.setEastAsia(copy.getEastAsia());
            result.setCs(copy.getCs());
            result.setAsciiTheme(copy.getAsciiTheme());
            result.setHAnsiTheme(copy.getHAnsiTheme());
            result.setEastAsiaTheme(copy.getEastAsiaTheme());
            result.setCstheme(copy.getCstheme());
            return result;
        }
        if (whenNull) {
            return myFactory.createRFonts();
        }
        return null;
    }

    public Color getCopy(Color copy, boolean whenNull) {
        if (copy != null) {
            Color result = myFactory.createColor();
            result.setVal(copy.getVal());
            result.setThemeColor(copy.getThemeColor());
            result.setThemeTint(copy.getThemeTint());
            result.setThemeShade(copy.getThemeShade());
            return result;
        }
        if (whenNull) {
            return myFactory.createColor();
        }
        return null;
    }

    public CTSignedTwipsMeasure getCopy(CTSignedTwipsMeasure copy, boolean whenNull) {
        if (copy != null) {
            CTSignedTwipsMeasure result = myFactory.createCTSignedTwipsMeasure();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTSignedTwipsMeasure();
        }
        return null;
    }

    public CTTextScale getCopy(CTTextScale copy, boolean whenNull) {
        if (copy != null) {
            CTTextScale result = myFactory.createCTTextScale();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTextScale();
        }
        return null;
    }

    public HpsMeasure getCopy(HpsMeasure copy, boolean whenNull) {
        if (copy != null) {
            HpsMeasure result = myFactory.createHpsMeasure();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createHpsMeasure();
        }
        return null;
    }

    public CTSignedHpsMeasure getCopy(CTSignedHpsMeasure copy, boolean whenNull) {
        if (copy != null) {
            CTSignedHpsMeasure result = myFactory.createCTSignedHpsMeasure();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTSignedHpsMeasure();
        }
        return null;
    }

    public Highlight getCopy(Highlight copy, boolean whenNull) {
        if (copy != null) {
            Highlight result = myFactory.createHighlight();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createHighlight();
        }
        return null;
    }

    public U getCopy(U copy, boolean whenNull) {
        if (copy != null) {
            U result = myFactory.createU();
            result.setVal(copy.getVal());
            result.setColor(copy.getColor());
            result.setThemeColor(copy.getThemeColor());
            result.setThemeTint(copy.getThemeTint());
            result.setThemeShade(copy.getThemeShade());
            return result;
        }
        if (whenNull) {
            return myFactory.createU();
        }
        return null;
    }

    public CTTextEffect getCopy(CTTextEffect copy, boolean whenNull) {
        if (copy != null) {
            CTTextEffect result = myFactory.createCTTextEffect();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTTextEffect();
        }
        return null;
    }

    public CTFitText getCopy(CTFitText copy, boolean whenNull) {
        if (copy != null) {
            CTFitText result = myFactory.createCTFitText();
            result.setVal(copy.getVal());
            result.setId(copy.getId());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTFitText();
        }
        return null;
    }

    public CTVerticalAlignRun getCopy(CTVerticalAlignRun copy, boolean whenNull) {
        if (copy != null) {
            CTVerticalAlignRun result = myFactory.createCTVerticalAlignRun();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTVerticalAlignRun();
        }
        return null;
    }

    public CTEm getCopy(CTEm copy, boolean whenNull) {
        if (copy != null) {
            CTEm result = myFactory.createCTEm();
            result.setVal(copy.getVal());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTEm();
        }
        return null;
    }

    public CTLanguage getCopy(CTLanguage copy, boolean whenNull) {
        if (copy != null) {
            CTLanguage result = myFactory.createCTLanguage();
            result.setVal(copy.getVal());
            result.setEastAsia(copy.getEastAsia());
            result.setBidi(copy.getBidi());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTLanguage();
        }
        return null;
    }

    public CTEastAsianLayout getCopy(CTEastAsianLayout copy, boolean whenNull) {
        if (copy != null) {
            CTEastAsianLayout result = myFactory.createCTEastAsianLayout();
            result.setId(copy.getId());
            result.setCombine(copy.isCombine());
            result.setCombineBrackets(copy.getCombineBrackets());
            result.setVert(copy.isVert());
            result.setVertCompress(copy.isVertCompress());
            return result;
        }
        if (whenNull) {
            return myFactory.createCTEastAsianLayout();
        }
        return null;
    }

    public void setRPr(final RPrAbstract result, final RPrAbstract from, final boolean whenNull) {
        result.setRStyle(getCopy(from == null ? null : from.getRStyle(), whenNull));
        result.setRFonts(getCopy(from == null ? null : from.getRFonts(), whenNull));
        result.setColor(getCopy(from == null ? null : from.getColor(), whenNull));
        result.setSpacing(getCopy(from == null ? null : from.getSpacing(), whenNull));
        result.setW(getCopy(from == null ? null : from.getW(), whenNull));
        result.setKern(getCopy(from == null ? null : from.getKern(), whenNull));
        result.setPosition(getCopy(from == null ? null : from.getPosition(), whenNull));
        result.setSz(getCopy(from == null ? null : from.getSz(), whenNull));
        result.setSzCs(getCopy(from == null ? null : from.getSzCs(), whenNull));
        result.setHighlight(getCopy(from == null ? null : from.getHighlight(), whenNull));
        result.setU(getCopy(from == null ? null : from.getU(), whenNull));
        result.setEffect(getCopy(from == null ? null : from.getEffect(), whenNull));
        result.setBdr(getCopy(from == null ? null : from.getBdr(), whenNull));
        result.setShd(getCopy(from == null ? null : from.getShd(), whenNull));
        result.setFitText(getCopy(from == null ? null : from.getFitText(), whenNull));
        result.setVertAlign(getCopy(from == null ? null : from.getVertAlign(), whenNull));
        result.setEm(getCopy(from == null ? null : from.getEm(), whenNull));
        result.setLang(getCopy(from == null ? null : from.getLang(), whenNull));
        result.setEastAsianLayout(getCopy(from == null ? null : from.getEastAsianLayout(), whenNull));
        //result.setRPrChange(getCopy(from == null ? null : from.getRPrChange(), whenNull));

        if (from != null) {
            result.setB(from.getB());
            result.setBCs(from.getBCs());
            result.setI(from.getI());
            result.setICs(from.getICs());
            result.setCaps(from.getCaps());
            result.setSmallCaps(from.getSmallCaps());
            result.setStrike(from.getStrike());
            result.setDstrike(from.getDstrike());
            result.setOutline(from.getOutline());
            result.setShadow(from.getShadow());
            result.setEmboss(from.getEmboss());
            result.setImprint(from.getImprint());
            result.setNoProof(from.getNoProof());
            result.setSnapToGrid(from.getSnapToGrid());
            result.setVanish(from.getVanish());
            result.setWebHidden(from.getWebHidden());
            result.setRtl(from.getRtl());
            result.setCs(from.getCs());
            result.setSpecVanish(from.getSpecVanish());
            result.setOMath(from.getOMath());
        }
    }

    public void setPPrBase(final PPrBase result, final PPrBase from, final boolean whenNull) {
        result.setPStyle(from == null ? null : getCopy(from.getPStyle(), whenNull));
        result.setFramePr(from == null ? null : getCopy(from.getFramePr(), whenNull));
        result.setNumPr(from == null ? null : getCopy(from.getNumPr(), whenNull));
        result.setPBdr(from == null ? null : getCopy(from.getPBdr(), whenNull));
        result.setShd(from == null ? null : getCopy(from.getShd(), whenNull));
        result.setTabs(from == null ? null : getCopy(from.getTabs(), whenNull));
        result.setSpacing(from == null ? null : getCopy(from.getSpacing(), whenNull));
        result.setInd(from == null ? null : getCopy(from.getInd(), whenNull));
        result.setJc(from == null ? null : getCopy(from.getJc(), whenNull));
        result.setTextDirection(from == null ? null : getCopy(from.getTextDirection(), whenNull));
        result.setTextAlignment(from == null ? null : getCopy(from.getTextAlignment(), whenNull));
        result.setTextboxTightWrap(from == null ? null : getCopy(from.getTextboxTightWrap(), whenNull));
        result.setOutlineLvl(from == null ? null : getCopy(from.getOutlineLvl(), whenNull));
        result.setDivId(from == null ? null : getCopy(from.getDivId(), whenNull));
        result.setCnfStyle(from == null ? null : getCopy(from.getCnfStyle(), whenNull));

        if (from != null) {
            result.setKeepNext(from.getKeepNext());
            result.setKeepLines(from.getKeepLines());
            result.setPageBreakBefore(from.getPageBreakBefore());
            result.setWidowControl(from.getWidowControl());
            result.setSuppressLineNumbers(from.getSuppressLineNumbers());
            result.setSuppressAutoHyphens(from.getSuppressAutoHyphens());
            result.setKinsoku(from.getKinsoku());
            result.setWordWrap(from.getWordWrap());
            result.setOverflowPunct(from.getOverflowPunct());
            result.setTopLinePunct(from.getTopLinePunct());
            result.setAutoSpaceDE(from.getAutoSpaceDE());
            result.setAutoSpaceDN(from.getAutoSpaceDN());
            result.setBidi(from.getBidi());
            result.setAdjustRightInd(from.getAdjustRightInd());
            result.setSnapToGrid(from.getSnapToGrid());
            result.setContextualSpacing(from.getContextualSpacing());
            result.setMirrorIndents(from.getMirrorIndents());
            result.setSuppressOverlap(from.getSuppressOverlap());
            result.setCollapsed(from.getCollapsed());
        }
    }

    /***************************************************************
     *
     * Deep copy only properties which are set
     *
     ***************************************************************/

    //public void applyCopy(PPrBase.Ind orig, PPrBase.Ind copy) {
    //    if (copy != null) {
    //        if (copy.getLeft() != null) orig.setLeft(copy.getLeft());
    //        if (copy.getLeftChars() != null) orig.setLeftChars(copy.getLeftChars());
    //        if (copy.getRight() != null) orig.setRight(copy.getRight());
    //        if (copy.getRightChars() != null) orig.setRightChars(copy.getRightChars());
    //        if (copy.getHanging() != null) orig.setHanging(copy.getHanging());
    //        if (copy.getHangingChars() != null) orig.setHangingChars(copy.getHangingChars());
    //        if (copy.getFirstLine() != null) orig.setFirstLine(copy.getFirstLine());
    //        if (copy.getFirstLineChars() != null) orig.setFirstLineChars(copy.getFirstLineChars());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.Spacing orig, PPrBase.Spacing copy) {
    //    if (copy != null) {
    //        if (copy.getAfter() != null) orig.setAfter(copy.getAfter());
    //        if (copy.getAfterLines() != null) orig.setAfterLines(copy.getAfterLines());
    //        if (copy.getBefore() != null) orig.setBefore(copy.getBefore());
    //        if (copy.getBeforeLines() != null) orig.setBeforeLines(copy.getBeforeLines());
    //        if (copy.getLine() != null) orig.setLine(copy.getLine());
    //        if (copy.isBeforeAutospacing()) orig.setBeforeAutospacing(true);
    //        if (copy.getLineRule() != null) orig.setLineRule(copy.getLineRule());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.NumPr.Ilvl orig, PPrBase.NumPr.Ilvl copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.NumPr.NumId orig, PPrBase.NumPr.NumId copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTTrackChangeNumbering orig, CTTrackChangeNumbering copy) {
    //
    //}
    //
    //public void applyCopy(CTTrackChange orig, CTTrackChange copy) {
    //
    //}
    //
    //public void applyCopy(PPrBase.NumPr orig, PPrBase.NumPr copy) {
    //    if (copy != null) {
    //        if (copy.getIlvl() != null) orig.setIlvl(copy.getIlvl());
    //        if (copy.getNumId() != null) orig.setNumId(copy.getNumId());
    //        if (copy.getNumberingChange() != null) orig.setNumberingChange(copy.getNumberingChange());
    //        if (copy.getIns() != null) orig.setIns(copy.getIns());
    //    }
    //}
    //
    //public void applyCopy(CTBorder orig, CTBorder copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //        if (copy.getThemeColor() != null) orig.setThemeColor(copy.getThemeColor());
    //        if (copy.getSpace() != null) orig.setSpace(copy.getSpace());
    //        if (copy.getSz() != null) orig.setSz(copy.getSz());
    //        if (copy.isFrame()) orig.setFrame(copy.isFrame());
    //        if (copy.isShadow()) orig.setShadow(copy.isShadow());
    //        if (copy.getColor() != null) orig.setColor(copy.getColor());
    //        if (copy.getThemeShade() != null) orig.setThemeShade(copy.getThemeShade());
    //        if (copy.getThemeTint() != null) orig.setThemeTint(copy.getThemeTint());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.PBdr orig, PPrBase.PBdr copy) {
    //    if (copy != null) {
    //        if (copy.getTop() != null) orig.setTop(copy.getTop());
    //        if (copy.getLeft() != null) orig.setLeft(copy.getLeft());
    //        if (copy.getBottom() != null) orig.setBottom(copy.getBottom());
    //        if (copy.getRight() != null) orig.setRight(copy.getRight());
    //        if (copy.getBetween() != null) orig.setBetween(copy.getBetween());
    //        if (copy.getBar() != null) orig.setBar(copy.getBar());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.PStyle orig, PPrBase.PStyle copy) {
    //    if (copy != null) {
    //        PPrBase.PStyle result = myFactory.createPPrBasePStyle();
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTFramePr orig, CTFramePr copy) {
    //    if (copy != null) {
    //        if (copy.getDropCap() != null) orig.setDropCap(copy.getDropCap());
    //        if (copy.getLines() != null) orig.setLines(copy.getLines());
    //        if (copy.getW() != null) orig.setW(copy.getW());
    //        if (copy.getH() != null) orig.setH(copy.getH());
    //        if (copy.getVSpace() != null) orig.setVSpace(copy.getVSpace());
    //        if (copy.getHSpace() != null) orig.setHSpace(copy.getHSpace());
    //        if (copy.getWrap() != null) orig.setWrap(copy.getWrap());
    //        if (copy.getHAnchor() != null) orig.setHAnchor(copy.getHAnchor());
    //        if (copy.getVAnchor() != null) orig.setVAnchor(copy.getVAnchor());
    //        if (copy.getX() != null) orig.setX(copy.getX());
    //        if (copy.getXAlign() != null) orig.setXAlign(copy.getXAlign());
    //        if (copy.getY() != null) orig.setY(copy.getY());
    //        if (copy.getYAlign() != null) orig.setYAlign(copy.getYAlign());
    //        if (copy.getHRule() != null) orig.setHRule(copy.getHRule());
    //    }
    //}
    //
    //public void applyCopy(CTShd orig, CTShd copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //        if (copy.getColor() != null) orig.setColor(copy.getColor());
    //        if (copy.getThemeColor() != null) orig.setThemeColor(copy.getThemeColor());
    //        if (copy.getThemeTint() != null) orig.setThemeTint(copy.getThemeTint());
    //        if (copy.getThemeShade() != null) orig.setThemeShade(copy.getThemeShade());
    //        if (copy.getFill() != null) orig.setFill(copy.getFill());
    //        if (copy.getThemeFill() != null) orig.setThemeFill(copy.getThemeFill());
    //        if (copy.getThemeFillTint() != null) orig.setThemeFillTint(copy.getThemeFillTint());
    //        if (copy.getThemeFillShade() != null) orig.setThemeFillShade(copy.getThemeFillShade());
    //    }
    //}
    //
    //public void applyCopy(Tabs orig, Tabs copy) {
    //    if (copy != null) {
    //        orig.getTab().addAll(copy.getTab());
    //    }
    //}
    //
    //public void applyCopy(Jc orig, Jc copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(TextDirection orig, TextDirection copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.TextAlignment orig, PPrBase.TextAlignment copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTTextboxTightWrap orig, CTTextboxTightWrap copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.OutlineLvl orig, PPrBase.OutlineLvl copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(PPrBase.DivId orig, PPrBase.DivId copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTCnf orig, CTCnf copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(RStyle orig, RStyle copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(RFonts orig, RFonts copy) {
    //    if (copy != null) {
    //        if (copy.getHint() != null) orig.setHint(copy.getHint());
    //        if (copy.getAscii() != null) orig.setAscii(copy.getAscii());
    //        if (copy.getHAnsi() != null) orig.setHAnsi(copy.getHAnsi());
    //        if (copy.getEastAsia() != null) orig.setEastAsia(copy.getEastAsia());
    //        if (copy.getCs() != null) orig.setCs(copy.getCs());
    //        if (copy.getAsciiTheme() != null) orig.setAsciiTheme(copy.getAsciiTheme());
    //        if (copy.getHAnsiTheme() != null) orig.setHAnsiTheme(copy.getHAnsiTheme());
    //        if (copy.getEastAsiaTheme() != null) orig.setEastAsiaTheme(copy.getEastAsiaTheme());
    //        if (copy.getCstheme() != null) orig.setCstheme(copy.getCstheme());
    //    }
    //}
    //
    //public void applyCopy(Color orig, Color copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //        if (copy.getThemeColor() != null) orig.setThemeColor(copy.getThemeColor());
    //        if (copy.getThemeTint() != null) orig.setThemeTint(copy.getThemeTint());
    //        if (copy.getThemeShade() != null) orig.setThemeShade(copy.getThemeShade());
    //    }
    //}
    //
    //public void applyCopy(CTSignedTwipsMeasure orig, CTSignedTwipsMeasure copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTTextScale orig, CTTextScale copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(HpsMeasure orig, HpsMeasure copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTSignedHpsMeasure orig, CTSignedHpsMeasure copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(Highlight orig, Highlight copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(U orig, U copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //        if (copy.getColor() != null) orig.setColor(copy.getColor());
    //        if (copy.getThemeColor() != null) orig.setThemeColor(copy.getThemeColor());
    //        if (copy.getThemeTint() != null) orig.setThemeTint(copy.getThemeTint());
    //        if (copy.getThemeShade() != null) orig.setThemeShade(copy.getThemeShade());
    //    }
    //}
    //
    //public void applyCopy(CTTextEffect orig, CTTextEffect copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTFitText orig, CTFitText copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //        if (copy.getId() != null) orig.setId(copy.getId());
    //    }
    //}
    //
    //public void applyCopy(CTVerticalAlignRun orig, CTVerticalAlignRun copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTEm orig, CTEm copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //    }
    //}
    //
    //public void applyCopy(CTLanguage orig, CTLanguage copy) {
    //    if (copy != null) {
    //        if (copy.getVal() != null) orig.setVal(copy.getVal());
    //        if (copy.getEastAsia() != null) orig.setEastAsia(copy.getEastAsia());
    //        if (copy.getBidi() != null) orig.setBidi(copy.getBidi());
    //    }
    //}
    //
    //public void applyCopy(CTEastAsianLayout orig, CTEastAsianLayout copy) {
    //    if (copy != null) {
    //        if (copy.getId() != null) orig.setId(copy.getId());
    //        if (copy.getCombineBrackets() != null) orig.setCombineBrackets(copy.getCombineBrackets());
    //        if (copy.isCombine()) orig.setCombine(copy.isCombine());
    //        if (copy.isVert()) orig.setVert(copy.isVert());
    //        if (copy.isVertCompress()) orig.setVertCompress(copy.isVertCompress());
    //    }
    //}
    //
    //public void applyCopy(final RPrAbstract orig, final RPrAbstract copy) {
    //    if (copy != null) {
    //        if (copy.getRStyle() != null) applyCopy(orig.getRStyle(), copy.getRStyle());
    //        if (copy.getRFonts() != null) applyCopy(orig.getRFonts(), copy.getRFonts());
    //        if (copy.getColor() != null) applyCopy(orig.getColor(), copy.getColor());
    //        if (copy.getSpacing() != null) applyCopy(orig.getSpacing(), copy.getSpacing());
    //        if (copy.getW() != null) applyCopy(orig.getW(), copy.getW());
    //        if (copy.getKern() != null) applyCopy(orig.getKern(), copy.getKern());
    //        if (copy.getPosition() != null) applyCopy(orig.getPosition(), copy.getPosition());
    //        if (copy.getSz() != null) applyCopy(orig.getSz(), copy.getSz());
    //        if (copy.getSzCs() != null) applyCopy(orig.getSzCs(), copy.getSzCs());
    //        if (copy.getHighlight() != null) applyCopy(orig.getHighlight(), copy.getHighlight());
    //        if (copy.getU() != null) applyCopy(orig.getU(), copy.getU());
    //        if (copy.getEffect() != null) applyCopy(orig.getEffect(), copy.getEffect());
    //        if (copy.getBdr() != null) applyCopy(orig.getBdr(), copy.getBdr());
    //        if (copy.getShd() != null) applyCopy(orig.getShd(), copy.getShd());
    //        if (copy.getFitText() != null) applyCopy(orig.getFitText(), copy.getFitText());
    //        if (copy.getVertAlign() != null) applyCopy(orig.getVertAlign(), copy.getVertAlign());
    //        if (copy.getEm() != null) applyCopy(orig.getEm(), copy.getEm());
    //        if (copy.getLang() != null) applyCopy(orig.getLang(), copy.getLang());
    //        if (copy.getEastAsianLayout() != null) applyCopy(orig.getEastAsianLayout(), copy.getEastAsianLayout());
    //        //result.setRPrChange(getCopy(from == null ? null : from.getRPrChange(), whenNull));
    //
    //        if (copy.getB() != null) orig.setB(myFactory.createBooleanDefaultTrue());
    //        if (copy.getBCs() != null) orig.setBCs(myFactory.createBooleanDefaultTrue());
    //        if (copy.getI() != null) orig.setI(myFactory.createBooleanDefaultTrue());
    //        if (copy.getICs() != null) orig.setICs(myFactory.createBooleanDefaultTrue());
    //        if (copy.getCaps() != null) orig.setCaps(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSmallCaps() != null) orig.setSmallCaps(myFactory.createBooleanDefaultTrue());
    //        if (copy.getStrike() != null) orig.setStrike(myFactory.createBooleanDefaultTrue());
    //        if (copy.getDstrike() != null) orig.setDstrike(myFactory.createBooleanDefaultTrue());
    //        if (copy.getOutline() != null) orig.setOutline(myFactory.createBooleanDefaultTrue());
    //        if (copy.getShadow() != null) orig.setShadow(myFactory.createBooleanDefaultTrue());
    //        if (copy.getEmboss() != null) orig.setEmboss(myFactory.createBooleanDefaultTrue());
    //        if (copy.getImprint() != null) orig.setImprint(myFactory.createBooleanDefaultTrue());
    //        if (copy.getNoProof() != null) orig.setNoProof(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSnapToGrid() != null) orig.setSnapToGrid(myFactory.createBooleanDefaultTrue());
    //        if (copy.getVanish() != null) orig.setVanish(myFactory.createBooleanDefaultTrue());
    //        if (copy.getWebHidden() != null) orig.setWebHidden(myFactory.createBooleanDefaultTrue());
    //        if (copy.getRtl() != null) orig.setRtl(myFactory.createBooleanDefaultTrue());
    //        if (copy.getCs() != null) orig.setCs(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSpecVanish() != null) orig.setSpecVanish(myFactory.createBooleanDefaultTrue());
    //        if (copy.getOMath() != null) orig.setOMath(myFactory.createBooleanDefaultTrue());
    //    }
    //}
    //
    //public void applyCopy(final PPrBase orig, final PPrBase copy) {
    //    if (copy != null) {
    //        if (copy.getPStyle() != null) applyCopy(orig.getPStyle(), copy.getPStyle());
    //        if (copy.getFramePr() != null) applyCopy(orig.getFramePr(), copy.getFramePr());
    //        if (copy.getNumPr() != null) applyCopy(orig.getNumPr(), copy.getNumPr());
    //        if (copy.getPBdr() != null) applyCopy(orig.getPBdr(), copy.getPBdr());
    //        if (copy.getShd() != null) applyCopy(orig.getShd(), copy.getShd());
    //        if (copy.getTabs() != null) applyCopy(orig.getTabs(), copy.getTabs());
    //        if (copy.getSpacing() != null) applyCopy(orig.getSpacing(), copy.getSpacing());
    //        if (copy.getInd() != null) applyCopy(orig.getInd(), copy.getInd());
    //        if (copy.getJc() != null) applyCopy(orig.getJc(), copy.getJc());
    //        if (copy.getTextDirection() != null) applyCopy(orig.getTextDirection(), copy.getTextDirection());
    //        if (copy.getTextAlignment() != null) applyCopy(orig.getTextAlignment(), copy.getTextAlignment());
    //        if (copy.getTextboxTightWrap() != null) applyCopy(orig.getTextboxTightWrap(), copy.getTextboxTightWrap());
    //        if (copy.getOutlineLvl() != null) applyCopy(orig.getOutlineLvl(), copy.getOutlineLvl());
    //        if (copy.getDivId() != null) applyCopy(orig.getDivId(), copy.getDivId());
    //        if (copy.getCnfStyle() != null) applyCopy(orig.getCnfStyle(), copy.getCnfStyle());
    //
    //        if (copy.getKeepNext() != null) orig.setKeepNext(myFactory.createBooleanDefaultTrue());
    //        if (copy.getKeepLines() != null) orig.setKeepLines(myFactory.createBooleanDefaultTrue());
    //        if (copy.getPageBreakBefore() != null) orig.setPageBreakBefore(myFactory.createBooleanDefaultTrue());
    //        if (copy.getWidowControl() != null) orig.setWidowControl(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSuppressLineNumbers() != null) orig.setSuppressLineNumbers(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSuppressAutoHyphens() != null) orig.setSuppressAutoHyphens(myFactory.createBooleanDefaultTrue());
    //        if (copy.getKinsoku() != null) orig.setKinsoku(myFactory.createBooleanDefaultTrue());
    //        if (copy.getWordWrap() != null) orig.setWordWrap(myFactory.createBooleanDefaultTrue());
    //        if (copy.getOverflowPunct() != null) orig.setOverflowPunct(myFactory.createBooleanDefaultTrue());
    //        if (copy.getTopLinePunct() != null) orig.setTopLinePunct(myFactory.createBooleanDefaultTrue());
    //        if (copy.getAutoSpaceDE() != null) orig.setAutoSpaceDE(myFactory.createBooleanDefaultTrue());
    //        if (copy.getAutoSpaceDN() != null) orig.setAutoSpaceDN(myFactory.createBooleanDefaultTrue());
    //        if (copy.getBidi() != null) orig.setBidi(myFactory.createBooleanDefaultTrue());
    //        if (copy.getAdjustRightInd() != null) orig.setAdjustRightInd(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSnapToGrid() != null) orig.setSnapToGrid(myFactory.createBooleanDefaultTrue());
    //        if (copy.getContextualSpacing() != null) orig.setContextualSpacing(myFactory.createBooleanDefaultTrue());
    //        if (copy.getMirrorIndents() != null) orig.setMirrorIndents(myFactory.createBooleanDefaultTrue());
    //        if (copy.getSuppressOverlap() != null) orig.setSuppressOverlap(myFactory.createBooleanDefaultTrue());
    //        if (copy.getCollapsed() != null) orig.setCollapsed(myFactory.createBooleanDefaultTrue());
    //    }
    //}

    public PPr getExplicitPPr(final PPr pPr) {
        return getResolver().getEffectivePPr(pPr);
    }

    public RPr getExplicitRPr(final RPrAbstract rPr, final PPr pPr) {
        RPr copyRPr = myFactory.createRPr();
        setRPr(copyRPr, rPr, false);
        return getResolver().getEffectiveRPr(copyRPr, pPr);
    }

    /**
     * Only gets character style properties and basedOn style tree
     * <p>
     * does not use doc defaults or ppr properties.
     * <p>
     * Use this for rPr cleaning
     *
     * @param rPr rpr to resolve fully
     * @return fully resolved rpr properties
     */
    public RPr getExplicitRPr(final RPr rPr) {
        RPr styledRPr = myFactory.createRPr();
        ArrayList<Style> styles = new ArrayList<Style>();
        final RStyle pStyle = rPr.getRStyle();
        if (pStyle != null) {
            String styleId = pStyle.getVal();
            Style style = getStyle(styleId);
            while (style != null) {
                styles.add(style);
                Style.BasedOn basedOn = style.getBasedOn();
                style = null;
                if (basedOn != null) {
                    styleId = basedOn.getVal();
                    if (styleId != null) {
                        style = getStyle(basedOn.getVal());
                    }
                }
            }
        }

        // copy out rPr from styles
        int iMax = styles.size();
        for (int i = iMax; i-- > 0; ) {
            Style style = styles.get(i);
            //applyCopy(styledRPr, style.getRPr());
            StyleUtil.apply(style.getRPr(), styledRPr);
        }
        StyleUtil.apply(rPr, styledRPr);
        return styledRPr;
    }

    /*******************************************************************************
     *
     * diff helpers
     *
     *******************************************************************************/
    public PPrBase.Ind keepDiff(PPrBase.Ind orig, PPrBase.Ind from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getLeft() != null && from.getLeft() != null && orig.getLeft().compareTo(from.getLeft()) == 0) orig.setLeft(null);
            else hadSome |= orig.getLeft() != null;
            if (orig.getLeft() != null && from.getLeft() != null && orig.getLeft().compareTo(from.getLeft()) == 0) orig.setLeft(null);
            else hadSome |= orig.getLeft() != null;
            if (orig.getLeftChars() != null && from.getLeftChars() != null && orig.getLeftChars().compareTo(from.getLeftChars()) == 0) orig.setLeftChars(null);
            else hadSome |= orig.getLeftChars() != null;
            if (orig.getRight() != null && from.getRight() != null && orig.getRight().compareTo(from.getRight()) == 0) orig.setRight(null);
            else hadSome |= orig.getRight() != null;
            if (orig.getRightChars() != null && from.getRightChars() != null && orig.getRightChars().compareTo(from.getRightChars()) == 0) orig.setRightChars(null);
            else hadSome |= orig.getRightChars() != null;
            if (orig.getHanging() != null && from.getHanging() != null && orig.getHanging().compareTo(from.getHanging()) == 0) orig.setHanging(null);
            else hadSome |= orig.getHanging() != null;
            if (orig.getHangingChars() != null && from.getHangingChars() != null && orig.getHangingChars().compareTo(from.getHangingChars()) == 0) orig.setHangingChars(null);
            else hadSome |= orig.getHangingChars() != null;
            if (orig.getFirstLine() != null && from.getFirstLine() != null && orig.getFirstLine().compareTo(from.getFirstLine()) == 0) orig.setFirstLine(null);
            else hadSome |= orig.getFirstLine() != null;
            if (orig.getFirstLineChars() != null && from.getFirstLineChars() != null && orig.getFirstLineChars().compareTo(from.getFirstLineChars()) == 0)
                orig.setFirstLineChars(null);
            else hadSome |= orig.getFirstLineChars() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.Spacing keepDiff(PPrBase.Spacing orig, PPrBase.Spacing from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getAfter() != null && from.getAfter() != null && orig.getAfter().compareTo(from.getAfter()) == 0) orig.setAfter(null);
            else hadSome |= orig.getAfter() != null;
            if (orig.getAfterLines() != null && from.getAfterLines() != null && orig.getAfterLines().compareTo(from.getAfterLines()) == 0) orig.setAfterLines(null);
            else hadSome |= orig.getAfterLines() != null;
            if (orig.getBefore() != null && from.getBefore() != null && orig.getBefore().compareTo(from.getBefore()) == 0) orig.setBefore(null);
            else hadSome |= orig.getBefore() != null;
            if (orig.getBeforeLines() != null && from.getBeforeLines() != null && orig.getBeforeLines().compareTo(from.getBeforeLines()) == 0) orig.setBeforeLines(null);
            else hadSome |= orig.getBeforeLines() != null;
            if (orig.getLine() != null && from.getLine() != null && orig.getLine().compareTo(from.getLine()) == 0) orig.setLine(null);
            else hadSome |= orig.getLine() != null;
            if (orig.getLineRule() != null && from.getLineRule() != null && orig.getLineRule().compareTo(from.getLineRule()) == 0) orig.setLineRule(null);
            else hadSome |= orig.getLineRule() != null;

            if (orig.isAfterAutospacing() == from.isAfterAutospacing()) orig.setAfterAutospacing(null);
            else hadSome = true;
            if (orig.isBeforeAutospacing() == from.isBeforeAutospacing()) orig.setBeforeAutospacing(null);
            else hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.NumPr.Ilvl keepDiff(PPrBase.NumPr.Ilvl orig, PPrBase.NumPr.Ilvl from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.NumPr.NumId keepDiff(PPrBase.NumPr.NumId orig, PPrBase.NumPr.NumId from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.NumPr keepDiff(PPrBase.NumPr orig, PPrBase.NumPr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getIlvl() != null) orig.setIlvl(keepDiff(orig.getIlvl(), from.getIlvl()));
            hadSome |= orig.getIlvl() != null;
            if (orig.getNumId() != null) orig.setNumId(keepDiff(orig.getNumId(), from.getNumId()));
            hadSome |= orig.getNumId() != null;
            if (orig.getNumberingChange() != null) hadSome = true;
            if (orig.getIns() != null) hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTBorder keepDiff(CTBorder orig, CTBorder from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;
            if (orig.getThemeColor() != null && from.getThemeColor() != null && orig.getThemeColor().compareTo(from.getThemeColor()) == 0) orig.setThemeColor(null);
            else hadSome |= orig.getThemeColor() != null;
            if (orig.getSpace() != null && from.getSpace() != null && orig.getSpace().compareTo(from.getSpace()) == 0) orig.setSpace(null);
            else hadSome |= orig.getSpace() != null;
            if (orig.getSz() != null && from.getSz() != null && orig.getSz().compareTo(from.getSz()) == 0) orig.setSz(null);
            else hadSome |= orig.getSz() != null;
            if (orig.getColor() != null && from.getColor() != null && orig.getColor().compareTo(from.getColor()) == 0) orig.setColor(null);
            else hadSome |= orig.getColor() != null;
            if (orig.getThemeShade() != null && from.getThemeShade() != null && orig.getThemeShade().compareTo(from.getThemeShade()) == 0) orig.setThemeShade(null);
            else hadSome |= orig.getThemeShade() != null;
            if (orig.getThemeTint() != null && from.getThemeTint() != null && orig.getThemeTint().compareTo(from.getThemeTint()) == 0) orig.setThemeTint(null);
            else hadSome |= orig.getThemeTint() != null;

            if (orig.isFrame() == from.isFrame()) orig.setFrame(null);
            else hadSome = true;
            if (orig.isShadow() == from.isShadow()) orig.setShadow(null);
            else hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.PBdr keepDiff(PPrBase.PBdr orig, PPrBase.PBdr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getTop() != null) orig.setTop(keepDiff(orig.getTop(), from.getTop()));
            hadSome |= orig.getTop() != null;
            if (orig.getLeft() != null) orig.setLeft(keepDiff(orig.getLeft(), from.getLeft()));
            hadSome |= orig.getLeft() != null;
            if (orig.getBottom() != null) orig.setBottom(keepDiff(orig.getBottom(), from.getBottom()));
            hadSome |= orig.getBottom() != null;
            if (orig.getRight() != null) orig.setRight(keepDiff(orig.getRight(), from.getRight()));
            hadSome |= orig.getRight() != null;
            if (orig.getBetween() != null) orig.setBetween(keepDiff(orig.getBetween(), from.getBetween()));
            hadSome |= orig.getBetween() != null;
            if (orig.getBar() != null) orig.setBar(keepDiff(orig.getBar(), from.getBar()));
            hadSome |= orig.getBar() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.PStyle keepDiff(PPrBase.PStyle orig, PPrBase.PStyle from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTFramePr keepDiff(CTFramePr orig, CTFramePr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getDropCap() != null && from.getDropCap() != null && orig.getDropCap().compareTo(from.getDropCap()) == 0) orig.setDropCap(null);
            else hadSome |= orig.getDropCap() != null;
            if (orig.getLines() != null && from.getLines() != null && orig.getLines().compareTo(from.getLines()) == 0) orig.setLines(null);
            else hadSome |= orig.getLines() != null;
            if (orig.getW() != null && from.getW() != null && orig.getW().compareTo(from.getW()) == 0) orig.setW(null);
            else hadSome |= orig.getW() != null;
            if (orig.getH() != null && from.getH() != null && orig.getH().compareTo(from.getH()) == 0) orig.setH(null);
            else hadSome |= orig.getH() != null;
            if (orig.getVSpace() != null && from.getVSpace() != null && orig.getVSpace().compareTo(from.getVSpace()) == 0) orig.setVSpace(null);
            else hadSome |= orig.getVSpace() != null;
            if (orig.getHSpace() != null && from.getHSpace() != null && orig.getHSpace().compareTo(from.getHSpace()) == 0) orig.setHSpace(null);
            else hadSome |= orig.getHSpace() != null;
            if (orig.getWrap() != null && from.getWrap() != null && orig.getWrap().compareTo(from.getWrap()) == 0) orig.setWrap(null);
            else hadSome |= orig.getWrap() != null;
            if (orig.getHAnchor() != null && from.getHAnchor() != null && orig.getHAnchor().compareTo(from.getHAnchor()) == 0) orig.setHAnchor(null);
            else hadSome |= orig.getHAnchor() != null;
            if (orig.getVAnchor() != null && from.getVAnchor() != null && orig.getVAnchor().compareTo(from.getVAnchor()) == 0) orig.setVAnchor(null);
            else hadSome |= orig.getVAnchor() != null;
            if (orig.getX() != null && from.getX() != null && orig.getX().compareTo(from.getX()) == 0) orig.setX(null);
            else hadSome |= orig.getX() != null;
            if (orig.getXAlign() != null && from.getXAlign() != null && orig.getXAlign().compareTo(from.getXAlign()) == 0) orig.setXAlign(null);
            else hadSome |= orig.getXAlign() != null;
            if (orig.getY() != null && from.getY() != null && orig.getY().compareTo(from.getY()) == 0) orig.setY(null);
            else hadSome |= orig.getY() != null;
            if (orig.getYAlign() != null && from.getYAlign() != null && orig.getYAlign().compareTo(from.getYAlign()) == 0) orig.setYAlign(null);
            else hadSome |= orig.getYAlign() != null;
            if (orig.getHRule() != null && from.getHRule() != null && orig.getHRule().compareTo(from.getHRule()) == 0) orig.setHRule(null);
            else hadSome |= orig.getHRule() != null;

            if (orig.isAnchorLock() == from.isAnchorLock()) orig.setAnchorLock(null);
            else hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTShd keepDiff(CTShd orig, CTShd from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;
            if (orig.getColor() != null && from.getColor() != null && orig.getColor().compareTo(from.getColor()) == 0) orig.setColor(null);
            else hadSome |= orig.getColor() != null;
            if (orig.getThemeColor() != null && from.getThemeColor() != null && orig.getThemeColor().compareTo(from.getThemeColor()) == 0) orig.setThemeColor(null);
            else hadSome |= orig.getThemeColor() != null;
            if (orig.getThemeTint() != null && from.getThemeTint() != null && orig.getThemeTint().compareTo(from.getThemeTint()) == 0) orig.setThemeTint(null);
            else hadSome |= orig.getThemeTint() != null;
            if (orig.getThemeShade() != null && from.getThemeShade() != null && orig.getThemeShade().compareTo(from.getThemeShade()) == 0) orig.setThemeShade(null);
            else hadSome |= orig.getThemeShade() != null;
            if (orig.getFill() != null && from.getFill() != null && orig.getFill().compareTo(from.getFill()) == 0) orig.setFill(null);
            else hadSome |= orig.getFill() != null;
            if (orig.getThemeFill() != null && from.getThemeFill() != null && orig.getThemeFill().compareTo(from.getThemeFill()) == 0) orig.setThemeFill(null);
            else hadSome |= orig.getThemeFill() != null;
            if (orig.getThemeFillTint() != null && from.getThemeFillTint() != null && orig.getThemeFillTint().compareTo(from.getThemeFillTint()) == 0) orig.setThemeFillTint(null);
            else hadSome |= orig.getThemeFillTint() != null;
            if (orig.getThemeFillShade() != null && from.getThemeFillShade() != null && orig.getThemeFillShade().compareTo(from.getThemeFillShade()) == 0)
                orig.setThemeFillShade(null);
            else hadSome |= orig.getThemeFillShade() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public Tabs keepDiff(Tabs orig, Tabs from) {
        return orig;
    }

    public Jc keepDiff(Jc orig, Jc from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public TextDirection keepDiff(TextDirection orig, TextDirection from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.TextAlignment keepDiff(PPrBase.TextAlignment orig, PPrBase.TextAlignment from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTTextboxTightWrap keepDiff(CTTextboxTightWrap orig, CTTextboxTightWrap from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.OutlineLvl keepDiff(PPrBase.OutlineLvl orig, PPrBase.OutlineLvl from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public PPrBase.DivId keepDiff(PPrBase.DivId orig, PPrBase.DivId from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTCnf keepDiff(CTCnf orig, CTCnf from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public ParaRPr keepDiff(ParaRPr orig, ParaRPr from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getIns() != null) hadSome = true;
            if (orig.getDel() != null) hadSome = true;
            if (orig.getMoveFrom() != null) hadSome = true;
            if (orig.getMoveTo() != null) hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public RStyle keepDiff(RStyle orig, RStyle from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public RFonts keepDiff(RFonts orig, RFonts from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getHint() != null && from.getHint() != null && orig.getHint().compareTo(from.getHint()) == 0) orig.setHint(null);
            else hadSome |= orig.getHint() != null;
            if (orig.getAscii() != null && from.getAscii() != null && orig.getAscii().compareTo(from.getAscii()) == 0) orig.setAscii(null);
            else hadSome |= orig.getAscii() != null;
            if (orig.getHAnsi() != null && from.getHAnsi() != null && orig.getHAnsi().compareTo(from.getHAnsi()) == 0) orig.setHAnsi(null);
            else hadSome |= orig.getHAnsi() != null;
            if (orig.getEastAsia() != null && from.getEastAsia() != null && orig.getEastAsia().compareTo(from.getEastAsia()) == 0) orig.setEastAsia(null);
            else hadSome |= orig.getEastAsia() != null;
            if (orig.getCs() != null && from.getCs() != null && orig.getCs().compareTo(from.getCs()) == 0) orig.setCs(null);
            else hadSome |= orig.getCs() != null;
            if (orig.getAsciiTheme() != null && from.getAsciiTheme() != null && orig.getAsciiTheme().compareTo(from.getAsciiTheme()) == 0) orig.setAsciiTheme(null);
            else hadSome |= orig.getAsciiTheme() != null;
            if (orig.getHAnsiTheme() != null && from.getHAnsiTheme() != null && orig.getHAnsiTheme().compareTo(from.getHAnsiTheme()) == 0) orig.setHAnsiTheme(null);
            else hadSome |= orig.getHAnsiTheme() != null;
            if (orig.getEastAsiaTheme() != null && from.getEastAsiaTheme() != null && orig.getEastAsiaTheme().compareTo(from.getEastAsiaTheme()) == 0) orig.setEastAsiaTheme(null);
            else hadSome |= orig.getEastAsiaTheme() != null;
            if (orig.getCstheme() != null && from.getCstheme() != null && orig.getCstheme().compareTo(from.getCstheme()) == 0) orig.setCstheme(null);
            else hadSome |= orig.getCstheme() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public Color keepDiff(Color orig, Color from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;
            if (orig.getThemeColor() != null && from.getThemeColor() != null && orig.getThemeColor().compareTo(from.getThemeColor()) == 0) orig.setThemeColor(null);
            else hadSome |= orig.getThemeColor() != null;
            if (orig.getThemeTint() != null && from.getThemeTint() != null && orig.getThemeTint().compareTo(from.getThemeTint()) == 0) orig.setThemeTint(null);
            else hadSome |= orig.getThemeTint() != null;
            if (orig.getThemeShade() != null && from.getThemeShade() != null && orig.getThemeShade().compareTo(from.getThemeShade()) == 0) orig.setThemeShade(null);
            else hadSome |= orig.getThemeShade() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTSignedTwipsMeasure keepDiff(CTSignedTwipsMeasure orig, CTSignedTwipsMeasure from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTTextScale keepDiff(CTTextScale orig, CTTextScale from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public HpsMeasure keepDiff(HpsMeasure orig, HpsMeasure from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTSignedHpsMeasure keepDiff(CTSignedHpsMeasure orig, CTSignedHpsMeasure from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public Highlight keepDiff(Highlight orig, Highlight from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public U keepDiff(U orig, U from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;
            if (orig.getColor() != null && from.getColor() != null && orig.getColor().compareTo(from.getColor()) == 0) orig.setColor(null);
            else hadSome |= orig.getColor() != null;
            if (orig.getThemeColor() != null && from.getThemeColor() != null && orig.getThemeColor().compareTo(from.getThemeColor()) == 0) orig.setThemeColor(null);
            else hadSome |= orig.getThemeColor() != null;
            if (orig.getThemeTint() != null && from.getThemeTint() != null && orig.getThemeTint().compareTo(from.getThemeTint()) == 0) orig.setThemeTint(null);
            else hadSome |= orig.getThemeTint() != null;
            if (orig.getThemeShade() != null && from.getThemeShade() != null && orig.getThemeShade().compareTo(from.getThemeShade()) == 0) orig.setThemeShade(null);
            else hadSome |= orig.getThemeShade() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTTextEffect keepDiff(CTTextEffect orig, CTTextEffect from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTFitText keepDiff(CTFitText orig, CTFitText from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;
            if (orig.getId() != null && from.getId() != null && orig.getId().compareTo(from.getId()) == 0) orig.setId(null);
            else hadSome |= orig.getId() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTVerticalAlignRun keepDiff(CTVerticalAlignRun orig, CTVerticalAlignRun from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTEm keepDiff(CTEm orig, CTEm from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTLanguage keepDiff(CTLanguage orig, CTLanguage from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getVal() != null && from.getVal() != null && orig.getVal().compareTo(from.getVal()) == 0) orig.setVal(null);
            else hadSome |= orig.getVal() != null;
            if (orig.getEastAsia() != null && from.getEastAsia() != null && orig.getEastAsia().compareTo(from.getEastAsia()) == 0) orig.setEastAsia(null);
            else hadSome |= orig.getEastAsia() != null;
            if (orig.getBidi() != null && from.getBidi() != null && orig.getBidi().compareTo(from.getBidi()) == 0) orig.setBidi(null);
            else hadSome |= orig.getBidi() != null;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public CTEastAsianLayout keepDiff(CTEastAsianLayout orig, CTEastAsianLayout from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            if (orig.getId() != null && orig.getId().compareTo(from.getId()) == 0) orig.setId(null);
            else hadSome |= orig.getId() != null;
            if (orig.getCombineBrackets() != null && from.getCombineBrackets() != null && orig.getCombineBrackets().compareTo(from.getCombineBrackets()) == 0)
                orig.setCombineBrackets(null);
            else hadSome |= orig.getCombineBrackets() != null;

            if (orig.isCombine() == from.isCombine()) orig.setCombine(null);
            else hadSome = true;
            if (orig.isVert() == from.isVert()) orig.setVert(null);
            else hadSome = true;
            if (orig.isVertCompress() == from.isVertCompress()) orig.setVertCompress(null);
            else hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public <T extends RPrAbstract> T keepDiff(final T orig, final RPrAbstract from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            // keep diffs on these
            //RFonts rFonts;
            //Color color;
            //CTBorder bdr;
            //U u;
            //CTShd shd;
            //CTFitText fitText;
            //CTLanguage lang;
            //CTEastAsianLayout eastAsianLayout;
            //HpsMeasure kern;
            //HpsMeasure sz;
            //HpsMeasure szCs;
            //CTSignedHpsMeasure position;
            //CTSignedTwipsMeasure spacing;
            //RStyle rStyle;
            //Highlight highlight;
            //CTTextEffect effect;
            //CTTextScale w;
            //CTVerticalAlignRun vertAlign;
            //CTEm em;
            if (orig.getRFonts() != null) orig.setRFonts(keepDiff(orig.getRFonts(), from.getRFonts()));
            hadSome |= orig.getRFonts() != null;
            if (orig.getColor() != null) orig.setColor(keepDiff(orig.getColor(), from.getColor()));
            hadSome |= orig.getColor() != null;
            if (orig.getBdr() != null) orig.setBdr(keepDiff(orig.getBdr(), from.getBdr()));
            hadSome |= orig.getBdr() != null;
            if (orig.getU() != null) orig.setU(keepDiff(orig.getU(), from.getU()));
            hadSome |= orig.getU() != null;
            if (orig.getShd() != null) orig.setShd(keepDiff(orig.getShd(), from.getShd()));
            hadSome |= orig.getShd() != null;
            if (orig.getFitText() != null) orig.setFitText(keepDiff(orig.getFitText(), from.getFitText()));
            hadSome |= orig.getFitText() != null;
            if (orig.getLang() != null) orig.setLang(keepDiff(orig.getLang(), from.getLang()));
            hadSome |= orig.getLang() != null;
            if (orig.getEastAsianLayout() != null) orig.setEastAsianLayout(keepDiff(orig.getEastAsianLayout(), from.getEastAsianLayout()));
            hadSome |= orig.getEastAsianLayout() != null;
            if (orig.getKern() != null) orig.setKern(keepDiff(orig.getKern(), from.getKern()));
            hadSome |= orig.getKern() != null;
            if (orig.getSz() != null) orig.setSz(keepDiff(orig.getSz(), from.getSz()));
            hadSome |= orig.getSz() != null;
            if (orig.getSzCs() != null) orig.setSzCs(keepDiff(orig.getSzCs(), from.getSzCs()));
            hadSome |= orig.getSzCs() != null;
            if (orig.getPosition() != null) orig.setPosition(keepDiff(orig.getPosition(), from.getPosition()));
            hadSome |= orig.getPosition() != null;
            if (orig.getSpacing() != null) orig.setSpacing(keepDiff(orig.getSpacing(), from.getSpacing()));
            hadSome |= orig.getSpacing() != null;
            if (orig.getRStyle() != null) orig.setRStyle(keepDiff(orig.getRStyle(), from.getRStyle()));
            hadSome |= orig.getRStyle() != null;
            if (orig.getHighlight() != null) orig.setHighlight(keepDiff(orig.getHighlight(), from.getHighlight()));
            hadSome |= orig.getHighlight() != null;
            if (orig.getEffect() != null) orig.setEffect(keepDiff(orig.getEffect(), from.getEffect()));
            hadSome |= orig.getEffect() != null;
            if (orig.getW() != null) orig.setW(keepDiff(orig.getW(), from.getW()));
            hadSome |= orig.getW() != null;
            if (orig.getVertAlign() != null) orig.setVertAlign(keepDiff(orig.getVertAlign(), from.getVertAlign()));
            hadSome |= orig.getVertAlign() != null;
            if (orig.getEm() != null) orig.setEm(keepDiff(orig.getEm(), from.getEm()));
            hadSome |= orig.getEm() != null;

            // non null keep
            //CTRPrChange rPrChange;
            if (orig.getRPrChange() != null) hadSome = true;

            // non-null == non-null
            //BooleanDefaultTrue b;
            //BooleanDefaultTrue bCs;
            //BooleanDefaultTrue i;
            //BooleanDefaultTrue iCs;
            //BooleanDefaultTrue caps;
            //BooleanDefaultTrue smallCaps;
            //BooleanDefaultTrue strike;
            //BooleanDefaultTrue dstrike;
            //BooleanDefaultTrue outline;
            //BooleanDefaultTrue shadow;
            //BooleanDefaultTrue emboss;
            //BooleanDefaultTrue imprint;
            //BooleanDefaultTrue noProof;
            //BooleanDefaultTrue snapToGrid;
            //BooleanDefaultTrue vanish;
            //BooleanDefaultTrue webHidden;
            //BooleanDefaultTrue rtl;
            //BooleanDefaultTrue cs;
            //BooleanDefaultTrue specVanish;
            //BooleanDefaultTrue oMath;
            if ((orig.getB() != null) == (from.getB() != null)) orig.setB(null);
            else hadSome = true;
            if ((orig.getBCs() != null) == (from.getBCs() != null)) orig.setBCs(null);
            else hadSome = true;
            if ((orig.getI() != null) == (from.getI() != null)) orig.setI(null);
            else hadSome = true;
            if ((orig.getICs() != null) == (from.getICs() != null)) orig.setICs(null);
            else hadSome = true;
            if ((orig.getCaps() != null) == (from.getCaps() != null)) orig.setCaps(null);
            else hadSome = true;
            if ((orig.getSmallCaps() != null) == (from.getSmallCaps() != null)) orig.setSmallCaps(null);
            else hadSome = true;
            if ((orig.getStrike() != null) == (from.getStrike() != null)) orig.setStrike(null);
            else hadSome = true;
            if ((orig.getDstrike() != null) == (from.getDstrike() != null)) orig.setDstrike(null);
            else hadSome = true;
            if ((orig.getOutline() != null) == (from.getOutline() != null)) orig.setOutline(null);
            else hadSome = true;
            if ((orig.getShadow() != null) == (from.getShadow() != null)) orig.setShadow(null);
            else hadSome = true;
            if ((orig.getEmboss() != null) == (from.getEmboss() != null)) orig.setEmboss(null);
            else hadSome = true;
            if ((orig.getImprint() != null) == (from.getImprint() != null)) orig.setImprint(null);
            else hadSome = true;
            if ((orig.getNoProof() != null) == (from.getNoProof() != null)) orig.setNoProof(null);
            else hadSome = true;
            if ((orig.getSnapToGrid() != null) == (from.getSnapToGrid() != null)) orig.setSnapToGrid(null);
            else hadSome = true;
            if ((orig.getVanish() != null) == (from.getVanish() != null)) orig.setVanish(null);
            else hadSome = true;
            if ((orig.getWebHidden() != null) == (from.getWebHidden() != null)) orig.setWebHidden(null);
            else hadSome = true;
            if ((orig.getRtl() != null) == (from.getRtl() != null)) orig.setRtl(null);
            else hadSome = true;
            if ((orig.getCs() != null) == (from.getCs() != null)) orig.setCs(null);
            else hadSome = true;
            if ((orig.getSpecVanish() != null) == (from.getSpecVanish() != null)) orig.setSpecVanish(null);
            else hadSome = true;
            if ((orig.getOMath() != null) == (from.getOMath() != null)) orig.setOMath(null);
            else hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }

    public <T extends PPrBase> T keepDiff(final T orig, final PPrBase from) {
        if (from != null && orig != null) {
            boolean hadSome = false;

            // keep diff
            //PPrBase.PStyle pStyle;
            //CTCnf cnfStyle;
            //CTFramePr framePr;
            //CTShd shd;
            //CTTextboxTightWrap textboxTightWrap;
            //Jc jc;
            //PPrBase.Ind ind;
            //PPrBase.NumPr numPr;
            //PPrBase.PBdr pBdr;
            //PPrBase.Spacing spacing;
            //PPrBase.TextAlignment textAlignment;
            //Tabs tabs;
            //TextDirection textDirection;
            //PPrBase.DivId divId;
            //PPrBase.OutlineLvl outlineLvl;
            if (orig.getPStyle() != null) orig.setPStyle(keepDiff(orig.getPStyle(), from.getPStyle()));
            hadSome |= orig.getPStyle() != null;
            if (orig.getCnfStyle() != null) orig.setCnfStyle(keepDiff(orig.getCnfStyle(), from.getCnfStyle()));
            hadSome |= orig.getCnfStyle() != null;
            if (orig.getFramePr() != null) orig.setFramePr(keepDiff(orig.getFramePr(), from.getFramePr()));
            hadSome |= orig.getFramePr() != null;
            if (orig.getShd() != null) orig.setShd(keepDiff(orig.getShd(), from.getShd()));
            hadSome |= orig.getShd() != null;
            if (orig.getTextboxTightWrap() != null) orig.setTextboxTightWrap(keepDiff(orig.getTextboxTightWrap(), from.getTextboxTightWrap()));
            hadSome |= orig.getTextboxTightWrap() != null;
            if (orig.getJc() != null) orig.setJc(keepDiff(orig.getJc(), from.getJc()));
            hadSome |= orig.getJc() != null;
            if (orig.getInd() != null) orig.setInd(keepDiff(orig.getInd(), from.getInd()));
            hadSome |= orig.getInd() != null;
            if (orig.getNumPr() != null) orig.setNumPr(keepDiff(orig.getNumPr(), from.getNumPr()));
            hadSome |= orig.getNumPr() != null;
            if (orig.getPBdr() != null) orig.setPBdr(keepDiff(orig.getPBdr(), from.getPBdr()));
            hadSome |= orig.getPBdr() != null;
            if (orig.getSpacing() != null) orig.setSpacing(keepDiff(orig.getSpacing(), from.getSpacing()));
            hadSome |= orig.getSpacing() != null;
            if (orig.getTextAlignment() != null) orig.setTextAlignment(keepDiff(orig.getTextAlignment(), from.getTextAlignment()));
            hadSome |= orig.getTextAlignment() != null;
            if (orig.getTabs() != null) orig.setTabs(keepDiff(orig.getTabs(), from.getTabs()));
            hadSome |= orig.getTabs() != null;
            if (orig.getTextDirection() != null) orig.setTextDirection(keepDiff(orig.getTextDirection(), from.getTextDirection()));
            hadSome |= orig.getTextDirection() != null;
            if (orig.getDivId() != null) orig.setDivId(keepDiff(orig.getDivId(), from.getDivId()));
            hadSome |= orig.getDivId() != null;
            if (orig.getOutlineLvl() != null) orig.setOutlineLvl(keepDiff(orig.getOutlineLvl(), from.getOutlineLvl()));
            hadSome |= orig.getOutlineLvl() != null;

            // non-null == non-null
            //BooleanDefaultTrue adjustRightInd;
            //BooleanDefaultTrue autoSpaceDE;
            //BooleanDefaultTrue autoSpaceDN;
            //BooleanDefaultTrue bidi;
            //BooleanDefaultTrue contextualSpacing;
            //BooleanDefaultTrue keepLines;
            //BooleanDefaultTrue keepNext;
            //BooleanDefaultTrue kinsoku;
            //BooleanDefaultTrue mirrorIndents;
            //BooleanDefaultTrue overflowPunct;
            //BooleanDefaultTrue pageBreakBefore;
            //BooleanDefaultTrue snapToGrid;
            //BooleanDefaultTrue suppressAutoHyphens;
            //BooleanDefaultTrue suppressLineNumbers;
            //BooleanDefaultTrue suppressOverlap;
            //BooleanDefaultTrue topLinePunct;
            //BooleanDefaultTrue widowControl;
            //BooleanDefaultTrue wordWrap;
            if ((orig.getAdjustRightInd() != null) == (from.getAdjustRightInd() != null)) orig.setAdjustRightInd(null);
            else hadSome = true;
            if ((orig.getAutoSpaceDE() != null) == (from.getAutoSpaceDE() != null)) orig.setAutoSpaceDE(null);
            else hadSome = true;
            if ((orig.getAutoSpaceDN() != null) == (from.getAutoSpaceDN() != null)) orig.setAutoSpaceDN(null);
            else hadSome = true;
            if ((orig.getBidi() != null) == (from.getBidi() != null)) orig.setBidi(null);
            else hadSome = true;
            if ((orig.getContextualSpacing() != null) == (from.getContextualSpacing() != null)) orig.setContextualSpacing(null);
            else hadSome = true;
            if ((orig.getKeepLines() != null) == (from.getKeepLines() != null)) orig.setKeepLines(null);
            else hadSome = true;
            if ((orig.getKeepNext() != null) == (from.getKeepNext() != null)) orig.setKeepNext(null);
            else hadSome = true;
            if ((orig.getKinsoku() != null) == (from.getKinsoku() != null)) orig.setKinsoku(null);
            else hadSome = true;
            if ((orig.getMirrorIndents() != null) == (from.getMirrorIndents() != null)) orig.setMirrorIndents(null);
            else hadSome = true;
            if ((orig.getOverflowPunct() != null) == (from.getOverflowPunct() != null)) orig.setOverflowPunct(null);
            else hadSome = true;
            if ((orig.getPageBreakBefore() != null) == (from.getPageBreakBefore() != null)) orig.setPageBreakBefore(null);
            else hadSome = true;
            if ((orig.getSnapToGrid() != null) == (from.getSnapToGrid() != null)) orig.setSnapToGrid(null);
            else hadSome = true;
            if ((orig.getSuppressAutoHyphens() != null) == (from.getSuppressAutoHyphens() != null)) orig.setSuppressAutoHyphens(null);
            else hadSome = true;
            if ((orig.getSuppressLineNumbers() != null) == (from.getSuppressLineNumbers() != null)) orig.setSuppressLineNumbers(null);
            else hadSome = true;
            if ((orig.getSuppressOverlap() != null) == (from.getSuppressOverlap() != null)) orig.setSuppressOverlap(null);
            else hadSome = true;
            if ((orig.getTopLinePunct() != null) == (from.getTopLinePunct() != null)) orig.setTopLinePunct(null);
            else hadSome = true;
            if ((orig.getWidowControl() != null) == (from.getWidowControl() != null)) orig.setWidowControl(null);
            else hadSome = true;
            if ((orig.getWordWrap() != null) == (from.getWordWrap() != null)) orig.setWordWrap(null);
            else hadSome = true;

            if (hadSome) return orig;
            return null;
        }
        return orig;
    }
}
