package com.vladsch.flexmark.docx.converter.internal;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.CTBorder;
import org.docx4j.wml.CTShd;
import org.docx4j.wml.CTTblCellMar;
import org.docx4j.wml.CTTblLook;
import org.docx4j.wml.Jc;
import org.docx4j.wml.P;
import org.docx4j.wml.PPr;
import org.docx4j.wml.PPrBase;
import org.docx4j.wml.PPrBase.PStyle;
import org.docx4j.wml.ParaRPr;
import org.docx4j.wml.R;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.TblBorders;
import org.docx4j.wml.TblGrid;
import org.docx4j.wml.TblGridCol;
import org.docx4j.wml.TblPr;
import org.docx4j.wml.TblWidth;
import org.docx4j.wml.Tc;
import org.docx4j.wml.TcMar;
import org.docx4j.wml.TcPr;
import org.docx4j.wml.TcPrInner;
import org.docx4j.wml.TcPrInner.GridSpan;
import org.docx4j.wml.TcPrInner.TcBorders;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.docx4j.wml.TrPr;


public class GenTbl {
    public static JAXBElement createIt() {

        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();

        Tbl tbl = wmlObjectFactory.createTbl();
        JAXBElement<org.docx4j.wml.Tbl> tblWrapped = wmlObjectFactory.createHdrTbl(tbl);
        // Create object for tblPr
        TblPr tblpr = wmlObjectFactory.createTblPr();
        tbl.setTblPr(tblpr);
        // Create object for jc
        Jc jc = wmlObjectFactory.createJc();
        tblpr.setJc(jc);
        jc.setVal(org.docx4j.wml.JcEnumeration.LEFT);
        // Create object for tblW
        TblWidth tblwidth = wmlObjectFactory.createTblWidth();
        tblpr.setTblW(tblwidth);
        tblwidth.setType( "dxa");
        tblwidth.setW( BigInteger.valueOf( 9972) );
        // Create object for tblInd
        TblWidth tblwidth2 = wmlObjectFactory.createTblWidth();
        tblpr.setTblInd(tblwidth2);
        tblwidth2.setType( "dxa");
        tblwidth2.setW( BigInteger.valueOf( 30) );
        // Create object for tblBorders
        TblBorders tblborders = wmlObjectFactory.createTblBorders();
        tblpr.setTblBorders(tblborders);
        // Create object for left
        CTBorder border = wmlObjectFactory.createCTBorder();
        tblborders.setLeft(border);
        border.setVal(org.docx4j.wml.STBorder.SINGLE);
        border.setSz( BigInteger.valueOf( 2) );
        border.setColor( "000001");
        border.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border2 = wmlObjectFactory.createCTBorder();
        tblborders.setRight(border2);
        border2.setVal(org.docx4j.wml.STBorder.SINGLE);
        border2.setSz( BigInteger.valueOf( 2) );
        border2.setColor( "000001");
        border2.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border3 = wmlObjectFactory.createCTBorder();
        tblborders.setTop(border3);
        border3.setVal(org.docx4j.wml.STBorder.SINGLE);
        border3.setSz( BigInteger.valueOf( 2) );
        border3.setColor( "000001");
        border3.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border4 = wmlObjectFactory.createCTBorder();
        tblborders.setBottom(border4);
        border4.setVal(org.docx4j.wml.STBorder.SINGLE);
        border4.setSz( BigInteger.valueOf( 2) );
        border4.setColor( "000001");
        border4.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border5 = wmlObjectFactory.createCTBorder();
        tblborders.setInsideH(border5);
        border5.setVal(org.docx4j.wml.STBorder.SINGLE);
        border5.setSz( BigInteger.valueOf( 2) );
        border5.setColor( "000001");
        border5.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border6 = wmlObjectFactory.createCTBorder();
        tblborders.setInsideV(border6);
        border6.setVal(org.docx4j.wml.STBorder.SINGLE);
        border6.setSz( BigInteger.valueOf( 2) );
        border6.setColor( "000001");
        border6.setSpace( BigInteger.valueOf( 0) );
        // Create object for tblCellMar
        CTTblCellMar tblcellmar = wmlObjectFactory.createCTTblCellMar();
        tblpr.setTblCellMar(tblcellmar);
        // Create object for left
        TblWidth tblwidth3 = wmlObjectFactory.createTblWidth();
        tblcellmar.setLeft(tblwidth3);
        tblwidth3.setType( "dxa");
        tblwidth3.setW( BigInteger.valueOf( 21) );
        // Create object for right
        TblWidth tblwidth4 = wmlObjectFactory.createTblWidth();
        tblcellmar.setRight(tblwidth4);
        tblwidth4.setType( "dxa");
        tblwidth4.setW( BigInteger.valueOf( 55) );
        // Create object for top
        TblWidth tblwidth5 = wmlObjectFactory.createTblWidth();
        tblcellmar.setTop(tblwidth5);
        tblwidth5.setType( "dxa");
        tblwidth5.setW( BigInteger.valueOf( 55) );
        // Create object for bottom
        TblWidth tblwidth6 = wmlObjectFactory.createTblWidth();
        tblcellmar.setBottom(tblwidth6);
        tblwidth6.setType( "dxa");
        tblwidth6.setW( BigInteger.valueOf( 55) );
        // Create object for tblLook
        CTTblLook tbllook = wmlObjectFactory.createCTTblLook();
        tblpr.setTblLook(tbllook);
        tbllook.setVal( "04a0");
        tbllook.setLastRow(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setLastColumn(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setNoHBand(org.docx4j.sharedtypes.STOnOff.ZERO);
        tbllook.setNoVBand(org.docx4j.sharedtypes.STOnOff.ONE);
        tbllook.setFirstRow(org.docx4j.sharedtypes.STOnOff.ONE);
        tbllook.setFirstColumn(org.docx4j.sharedtypes.STOnOff.ONE);
        // Create object for tblGrid
        TblGrid tblgrid = wmlObjectFactory.createTblGrid();
        tbl.setTblGrid(tblgrid);
        // Create object for gridCol
        TblGridCol tblgridcol = wmlObjectFactory.createTblGridCol();
        tblgrid.getGridCol().add( tblgridcol);
        tblgridcol.setW( BigInteger.valueOf( 4986) );
        // Create object for gridCol
        TblGridCol tblgridcol2 = wmlObjectFactory.createTblGridCol();
        tblgrid.getGridCol().add( tblgridcol2);
        tblgridcol2.setW( BigInteger.valueOf( 4985) );

        // Create object for tr
        Tr tr = wmlObjectFactory.createTr();
        tbl.getContent().add( tr);
        // Create object for trPr
        TrPr trpr = wmlObjectFactory.createTrPr();
        tr.setTrPr(trpr);

        // Create object for tblHeader (wrapped in JAXBElement)
        BooleanDefaultTrue booleandefaulttrue = wmlObjectFactory.createBooleanDefaultTrue();
        JAXBElement<org.docx4j.wml.BooleanDefaultTrue> booleandefaulttrueWrapped = wmlObjectFactory.createCTTrPrBaseTblHeader(booleandefaulttrue);
        trpr.getCnfStyleOrDivIdOrGridBefore().add( booleandefaulttrueWrapped);
        // Create object for tc (wrapped in JAXBElement)
        Tc tc = wmlObjectFactory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped = wmlObjectFactory.createTrTc(tc);
        tr.getContent().add( tcWrapped);
        // Create object for tcPr
        TcPr tcpr = wmlObjectFactory.createTcPr();
        tc.setTcPr(tcpr);
        // Create object for shd
        CTShd shd = wmlObjectFactory.createCTShd();
        tcpr.setShd(shd);
        shd.setVal(org.docx4j.wml.STShd.CLEAR);
        shd.setColor( "auto");
        shd.setFill( "FFFFFF");
        // Create object for tcW
        TblWidth tblwidth7 = wmlObjectFactory.createTblWidth();
        tcpr.setTcW(tblwidth7);
        tblwidth7.setType( "dxa");
        tblwidth7.setW( BigInteger.valueOf( 9971) );

        // Create object for gridSpan
        TcPrInner.GridSpan tcprinnergridspan = wmlObjectFactory.createTcPrInnerGridSpan();
        tcpr.setGridSpan(tcprinnergridspan);
        tcprinnergridspan.setVal( BigInteger.valueOf( 2) );

        // Create object for tcBorders
        TcPrInner.TcBorders tcprinnertcborders = wmlObjectFactory.createTcPrInnerTcBorders();
        tcpr.setTcBorders(tcprinnertcborders);
        // Create object for left
        CTBorder border7 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders.setLeft(border7);
        border7.setVal(org.docx4j.wml.STBorder.SINGLE);
        border7.setSz( BigInteger.valueOf( 2) );
        border7.setColor( "000001");
        border7.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border8 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders.setRight(border8);
        border8.setVal(org.docx4j.wml.STBorder.SINGLE);
        border8.setSz( BigInteger.valueOf( 2) );
        border8.setColor( "000001");
        border8.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border9 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders.setTop(border9);
        border9.setVal(org.docx4j.wml.STBorder.SINGLE);
        border9.setSz( BigInteger.valueOf( 2) );
        border9.setColor( "000001");
        border9.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border10 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders.setBottom(border10);
        border10.setVal(org.docx4j.wml.STBorder.SINGLE);
        border10.setSz( BigInteger.valueOf( 2) );
        border10.setColor( "000001");
        border10.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border11 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders.setInsideH(border11);
        border11.setVal(org.docx4j.wml.STBorder.SINGLE);
        border11.setSz( BigInteger.valueOf( 2) );
        border11.setColor( "000001");
        border11.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border12 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders.setInsideV(border12);
        border12.setVal(org.docx4j.wml.STBorder.SINGLE);
        border12.setSz( BigInteger.valueOf( 2) );
        border12.setColor( "000001");
        border12.setSpace( BigInteger.valueOf( 0) );
        // Create object for tcMar
        TcMar tcmar = wmlObjectFactory.createTcMar();
        tcpr.setTcMar(tcmar);
        // Create object for left
        TblWidth tblwidth8 = wmlObjectFactory.createTblWidth();
        tcmar.setLeft(tblwidth8);
        tblwidth8.setType( "dxa");
        tblwidth8.setW( BigInteger.valueOf( 21) );
        // Create object for p
        P p = wmlObjectFactory.createP();
        tc.getContent().add( p);
        // Create object for pPr
        PPr ppr = wmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = wmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle = wmlObjectFactory.createPPrBasePStyle();
        ppr.setPStyle(pprbasepstyle);
        pprbasepstyle.setVal( "TableHeading");
        // Create object for r
        R r = wmlObjectFactory.createR();
        p.getContent().add( r);
        // Create object for rPr
        RPr rpr = wmlObjectFactory.createRPr();
        r.setRPr(rpr);
        // Create object for t (wrapped in JAXBElement)
        Text text = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped = wmlObjectFactory.createRT(text);
        r.getContent().add( textWrapped);
        text.setValue( "Combined header ");
        text.setSpace( "preserve");

        // Create object for tr
        Tr tr2 = wmlObjectFactory.createTr();
        tbl.getContent().add( tr2);
        // Create object for trPr
        TrPr trpr2 = wmlObjectFactory.createTrPr();
        tr2.setTrPr(trpr2);
        // Create object for tblHeader (wrapped in JAXBElement)
        BooleanDefaultTrue booleandefaulttrue2 = wmlObjectFactory.createBooleanDefaultTrue();
        JAXBElement<org.docx4j.wml.BooleanDefaultTrue> booleandefaulttrueWrapped2 = wmlObjectFactory.createCTTrPrBaseTblHeader(booleandefaulttrue2);
        trpr2.getCnfStyleOrDivIdOrGridBefore().add( booleandefaulttrueWrapped2);
        // Create object for tc (wrapped in JAXBElement)
        Tc tc2 = wmlObjectFactory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped2 = wmlObjectFactory.createTrTc(tc2);
        tr2.getContent().add( tcWrapped2);
        // Create object for tcPr
        TcPr tcpr2 = wmlObjectFactory.createTcPr();
        tc2.setTcPr(tcpr2);
        // Create object for shd
        CTShd shd2 = wmlObjectFactory.createCTShd();
        tcpr2.setShd(shd2);
        shd2.setVal(org.docx4j.wml.STShd.CLEAR);
        shd2.setColor( "auto");
        shd2.setFill( "FFFFFF");
        // Create object for tcW
        TblWidth tblwidth9 = wmlObjectFactory.createTblWidth();
        tcpr2.setTcW(tblwidth9);
        tblwidth9.setType( "dxa");
        tblwidth9.setW( BigInteger.valueOf( 4986) );
        // Create object for tcBorders
        TcPrInner.TcBorders tcprinnertcborders2 = wmlObjectFactory.createTcPrInnerTcBorders();
        tcpr2.setTcBorders(tcprinnertcborders2);
        // Create object for left
        CTBorder border13 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders2.setLeft(border13);
        border13.setVal(org.docx4j.wml.STBorder.SINGLE);
        border13.setSz( BigInteger.valueOf( 2) );
        border13.setColor( "000001");
        border13.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border14 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders2.setRight(border14);
        border14.setVal(org.docx4j.wml.STBorder.SINGLE);
        border14.setSz( BigInteger.valueOf( 2) );
        border14.setColor( "000001");
        border14.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border15 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders2.setTop(border15);
        border15.setVal(org.docx4j.wml.STBorder.SINGLE);
        border15.setSz( BigInteger.valueOf( 2) );
        border15.setColor( "000001");
        border15.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border16 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders2.setBottom(border16);
        border16.setVal(org.docx4j.wml.STBorder.SINGLE);
        border16.setSz( BigInteger.valueOf( 2) );
        border16.setColor( "000001");
        border16.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border17 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders2.setInsideH(border17);
        border17.setVal(org.docx4j.wml.STBorder.SINGLE);
        border17.setSz( BigInteger.valueOf( 2) );
        border17.setColor( "000001");
        border17.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border18 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders2.setInsideV(border18);
        border18.setVal(org.docx4j.wml.STBorder.SINGLE);
        border18.setSz( BigInteger.valueOf( 2) );
        border18.setColor( "000001");
        border18.setSpace( BigInteger.valueOf( 0) );
        // Create object for tcMar
        TcMar tcmar2 = wmlObjectFactory.createTcMar();
        tcpr2.setTcMar(tcmar2);
        // Create object for left
        TblWidth tblwidth10 = wmlObjectFactory.createTblWidth();
        tcmar2.setLeft(tblwidth10);
        tblwidth10.setType( "dxa");
        tblwidth10.setW( BigInteger.valueOf( 21) );
        // Create object for p
        P p2 = wmlObjectFactory.createP();
        tc2.getContent().add( p2);
        // Create object for pPr
        PPr ppr2 = wmlObjectFactory.createPPr();
        p2.setPPr(ppr2);
        // Create object for rPr
        ParaRPr pararpr2 = wmlObjectFactory.createParaRPr();
        ppr2.setRPr(pararpr2);
        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle2 = wmlObjectFactory.createPPrBasePStyle();
        ppr2.setPStyle(pprbasepstyle2);
        pprbasepstyle2.setVal( "TableHeading");
        // Create object for r
        R r2 = wmlObjectFactory.createR();
        p2.getContent().add( r2);
        // Create object for rPr
        RPr rpr2 = wmlObjectFactory.createRPr();
        r2.setRPr(rpr2);
        // Create object for t (wrapped in JAXBElement)
        Text text2 = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped2 = wmlObjectFactory.createRT(text2);
        r2.getContent().add( textWrapped2);
        text2.setValue( "Header 1");
        // Create object for tc (wrapped in JAXBElement)
        Tc tc3 = wmlObjectFactory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped3 = wmlObjectFactory.createTrTc(tc3);
        tr2.getContent().add( tcWrapped3);
        // Create object for tcPr
        TcPr tcpr3 = wmlObjectFactory.createTcPr();
        tc3.setTcPr(tcpr3);
        // Create object for shd
        CTShd shd3 = wmlObjectFactory.createCTShd();
        tcpr3.setShd(shd3);
        shd3.setVal(org.docx4j.wml.STShd.CLEAR);
        shd3.setColor( "auto");
        shd3.setFill( "FFFFFF");
        // Create object for tcW
        TblWidth tblwidth11 = wmlObjectFactory.createTblWidth();
        tcpr3.setTcW(tblwidth11);
        tblwidth11.setType( "dxa");
        tblwidth11.setW( BigInteger.valueOf( 4985) );
        // Create object for tcBorders
        TcPrInner.TcBorders tcprinnertcborders3 = wmlObjectFactory.createTcPrInnerTcBorders();
        tcpr3.setTcBorders(tcprinnertcborders3);
        // Create object for left
        CTBorder border19 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders3.setLeft(border19);
        border19.setVal(org.docx4j.wml.STBorder.SINGLE);
        border19.setSz( BigInteger.valueOf( 2) );
        border19.setColor( "000001");
        border19.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border20 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders3.setRight(border20);
        border20.setVal(org.docx4j.wml.STBorder.SINGLE);
        border20.setSz( BigInteger.valueOf( 2) );
        border20.setColor( "000001");
        border20.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border21 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders3.setTop(border21);
        border21.setVal(org.docx4j.wml.STBorder.SINGLE);
        border21.setSz( BigInteger.valueOf( 2) );
        border21.setColor( "000001");
        border21.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border22 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders3.setBottom(border22);
        border22.setVal(org.docx4j.wml.STBorder.SINGLE);
        border22.setSz( BigInteger.valueOf( 2) );
        border22.setColor( "000001");
        border22.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border23 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders3.setInsideH(border23);
        border23.setVal(org.docx4j.wml.STBorder.SINGLE);
        border23.setSz( BigInteger.valueOf( 2) );
        border23.setColor( "000001");
        border23.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border24 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders3.setInsideV(border24);
        border24.setVal(org.docx4j.wml.STBorder.SINGLE);
        border24.setSz( BigInteger.valueOf( 2) );
        border24.setColor( "000001");
        border24.setSpace( BigInteger.valueOf( 0) );
        // Create object for tcMar
        TcMar tcmar3 = wmlObjectFactory.createTcMar();
        tcpr3.setTcMar(tcmar3);
        // Create object for left
        TblWidth tblwidth12 = wmlObjectFactory.createTblWidth();
        tcmar3.setLeft(tblwidth12);
        tblwidth12.setType( "dxa");
        tblwidth12.setW( BigInteger.valueOf( 21) );
        // Create object for p
        P p3 = wmlObjectFactory.createP();
        tc3.getContent().add( p3);
        // Create object for pPr
        PPr ppr3 = wmlObjectFactory.createPPr();
        p3.setPPr(ppr3);
        // Create object for rPr
        ParaRPr pararpr3 = wmlObjectFactory.createParaRPr();
        ppr3.setRPr(pararpr3);
        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle3 = wmlObjectFactory.createPPrBasePStyle();
        ppr3.setPStyle(pprbasepstyle3);
        pprbasepstyle3.setVal( "TableHeading");
        // Create object for r
        R r3 = wmlObjectFactory.createR();
        p3.getContent().add( r3);
        // Create object for rPr
        RPr rpr3 = wmlObjectFactory.createRPr();
        r3.setRPr(rpr3);
        // Create object for t (wrapped in JAXBElement)
        Text text3 = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped3 = wmlObjectFactory.createRT(text3);
        r3.getContent().add( textWrapped3);
        text3.setValue( "Header 2");
        // Create object for tr
        Tr tr3 = wmlObjectFactory.createTr();
        tbl.getContent().add( tr3);
        // Create object for trPr
        TrPr trpr3 = wmlObjectFactory.createTrPr();
        tr3.setTrPr(trpr3);
        // Create object for tc (wrapped in JAXBElement)
        Tc tc4 = wmlObjectFactory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped4 = wmlObjectFactory.createTrTc(tc4);
        tr3.getContent().add( tcWrapped4);
        // Create object for tcPr
        TcPr tcpr4 = wmlObjectFactory.createTcPr();
        tc4.setTcPr(tcpr4);
        // Create object for shd
        CTShd shd4 = wmlObjectFactory.createCTShd();
        tcpr4.setShd(shd4);
        shd4.setVal(org.docx4j.wml.STShd.CLEAR);
        shd4.setColor( "auto");
        shd4.setFill( "FFFFFF");
        // Create object for tcW
        TblWidth tblwidth13 = wmlObjectFactory.createTblWidth();
        tcpr4.setTcW(tblwidth13);
        tblwidth13.setType( "dxa");
        tblwidth13.setW( BigInteger.valueOf( 4986) );
        // Create object for tcBorders
        TcPrInner.TcBorders tcprinnertcborders4 = wmlObjectFactory.createTcPrInnerTcBorders();
        tcpr4.setTcBorders(tcprinnertcborders4);
        // Create object for left
        CTBorder border25 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders4.setLeft(border25);
        border25.setVal(org.docx4j.wml.STBorder.SINGLE);
        border25.setSz( BigInteger.valueOf( 2) );
        border25.setColor( "000001");
        border25.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border26 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders4.setRight(border26);
        border26.setVal(org.docx4j.wml.STBorder.SINGLE);
        border26.setSz( BigInteger.valueOf( 2) );
        border26.setColor( "000001");
        border26.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border27 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders4.setTop(border27);
        border27.setVal(org.docx4j.wml.STBorder.SINGLE);
        border27.setSz( BigInteger.valueOf( 2) );
        border27.setColor( "000001");
        border27.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border28 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders4.setBottom(border28);
        border28.setVal(org.docx4j.wml.STBorder.SINGLE);
        border28.setSz( BigInteger.valueOf( 2) );
        border28.setColor( "000001");
        border28.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border29 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders4.setInsideH(border29);
        border29.setVal(org.docx4j.wml.STBorder.SINGLE);
        border29.setSz( BigInteger.valueOf( 2) );
        border29.setColor( "000001");
        border29.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border30 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders4.setInsideV(border30);
        border30.setVal(org.docx4j.wml.STBorder.SINGLE);
        border30.setSz( BigInteger.valueOf( 2) );
        border30.setColor( "000001");
        border30.setSpace( BigInteger.valueOf( 0) );
        // Create object for tcMar
        TcMar tcmar4 = wmlObjectFactory.createTcMar();
        tcpr4.setTcMar(tcmar4);
        // Create object for left
        TblWidth tblwidth14 = wmlObjectFactory.createTblWidth();
        tcmar4.setLeft(tblwidth14);
        tblwidth14.setType( "dxa");
        tblwidth14.setW( BigInteger.valueOf( 21) );
        // Create object for p
        P p4 = wmlObjectFactory.createP();
        tc4.getContent().add( p4);
        // Create object for pPr
        PPr ppr4 = wmlObjectFactory.createPPr();
        p4.setPPr(ppr4);
        // Create object for rPr
        ParaRPr pararpr4 = wmlObjectFactory.createParaRPr();
        ppr4.setRPr(pararpr4);
        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle4 = wmlObjectFactory.createPPrBasePStyle();
        ppr4.setPStyle(pprbasepstyle4);
        pprbasepstyle4.setVal( "TableContents");
        // Create object for r
        R r4 = wmlObjectFactory.createR();
        p4.getContent().add( r4);
        // Create object for rPr
        RPr rpr4 = wmlObjectFactory.createRPr();
        r4.setRPr(rpr4);
        // Create object for t (wrapped in JAXBElement)
        Text text4 = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped4 = wmlObjectFactory.createRT(text4);
        r4.getContent().add( textWrapped4);
        text4.setValue( "Data 1");
        // Create object for tc (wrapped in JAXBElement)
        Tc tc5 = wmlObjectFactory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped5 = wmlObjectFactory.createTrTc(tc5);
        tr3.getContent().add( tcWrapped5);
        // Create object for tcPr
        TcPr tcpr5 = wmlObjectFactory.createTcPr();
        tc5.setTcPr(tcpr5);
        // Create object for shd
        CTShd shd5 = wmlObjectFactory.createCTShd();
        tcpr5.setShd(shd5);
        shd5.setVal(org.docx4j.wml.STShd.CLEAR);
        shd5.setColor( "auto");
        shd5.setFill( "FFFFFF");
        // Create object for tcW
        TblWidth tblwidth15 = wmlObjectFactory.createTblWidth();
        tcpr5.setTcW(tblwidth15);
        tblwidth15.setType( "dxa");
        tblwidth15.setW( BigInteger.valueOf( 4985) );
        // Create object for tcBorders
        TcPrInner.TcBorders tcprinnertcborders5 = wmlObjectFactory.createTcPrInnerTcBorders();
        tcpr5.setTcBorders(tcprinnertcborders5);
        // Create object for left
        CTBorder border31 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders5.setLeft(border31);
        border31.setVal(org.docx4j.wml.STBorder.SINGLE);
        border31.setSz( BigInteger.valueOf( 2) );
        border31.setColor( "000001");
        border31.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border32 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders5.setRight(border32);
        border32.setVal(org.docx4j.wml.STBorder.SINGLE);
        border32.setSz( BigInteger.valueOf( 2) );
        border32.setColor( "000001");
        border32.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border33 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders5.setTop(border33);
        border33.setVal(org.docx4j.wml.STBorder.SINGLE);
        border33.setSz( BigInteger.valueOf( 2) );
        border33.setColor( "000001");
        border33.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border34 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders5.setBottom(border34);
        border34.setVal(org.docx4j.wml.STBorder.SINGLE);
        border34.setSz( BigInteger.valueOf( 2) );
        border34.setColor( "000001");
        border34.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border35 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders5.setInsideH(border35);
        border35.setVal(org.docx4j.wml.STBorder.SINGLE);
        border35.setSz( BigInteger.valueOf( 2) );
        border35.setColor( "000001");
        border35.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border36 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders5.setInsideV(border36);
        border36.setVal(org.docx4j.wml.STBorder.SINGLE);
        border36.setSz( BigInteger.valueOf( 2) );
        border36.setColor( "000001");
        border36.setSpace( BigInteger.valueOf( 0) );
        // Create object for tcMar
        TcMar tcmar5 = wmlObjectFactory.createTcMar();
        tcpr5.setTcMar(tcmar5);
        // Create object for left
        TblWidth tblwidth16 = wmlObjectFactory.createTblWidth();
        tcmar5.setLeft(tblwidth16);
        tblwidth16.setType( "dxa");
        tblwidth16.setW( BigInteger.valueOf( 21) );
        // Create object for p
        P p5 = wmlObjectFactory.createP();
        tc5.getContent().add( p5);
        // Create object for pPr
        PPr ppr5 = wmlObjectFactory.createPPr();
        p5.setPPr(ppr5);
        // Create object for rPr
        ParaRPr pararpr5 = wmlObjectFactory.createParaRPr();
        ppr5.setRPr(pararpr5);
        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle5 = wmlObjectFactory.createPPrBasePStyle();
        ppr5.setPStyle(pprbasepstyle5);
        pprbasepstyle5.setVal( "TableContents");
        // Create object for r
        R r5 = wmlObjectFactory.createR();
        p5.getContent().add( r5);
        // Create object for rPr
        RPr rpr5 = wmlObjectFactory.createRPr();
        r5.setRPr(rpr5);
        // Create object for t (wrapped in JAXBElement)
        Text text5 = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped5 = wmlObjectFactory.createRT(text5);
        r5.getContent().add( textWrapped5);
        text5.setValue( "Data 2");
        // Create object for tr
        Tr tr4 = wmlObjectFactory.createTr();
        tbl.getContent().add( tr4);
        // Create object for trPr
        TrPr trpr4 = wmlObjectFactory.createTrPr();
        tr4.setTrPr(trpr4);
        // Create object for tc (wrapped in JAXBElement)
        Tc tc6 = wmlObjectFactory.createTc();
        JAXBElement<org.docx4j.wml.Tc> tcWrapped6 = wmlObjectFactory.createTrTc(tc6);
        tr4.getContent().add( tcWrapped6);
        // Create object for tcPr
        TcPr tcpr6 = wmlObjectFactory.createTcPr();
        tc6.setTcPr(tcpr6);
        // Create object for shd
        CTShd shd6 = wmlObjectFactory.createCTShd();
        tcpr6.setShd(shd6);
        shd6.setVal(org.docx4j.wml.STShd.CLEAR);
        shd6.setColor( "auto");
        shd6.setFill( "FFFFFF");
        // Create object for tcW
        TblWidth tblwidth17 = wmlObjectFactory.createTblWidth();
        tcpr6.setTcW(tblwidth17);
        tblwidth17.setType( "dxa");
        tblwidth17.setW( BigInteger.valueOf( 9971) );
        // Create object for gridSpan
        TcPrInner.GridSpan tcprinnergridspan2 = wmlObjectFactory.createTcPrInnerGridSpan();
        tcpr6.setGridSpan(tcprinnergridspan2);
        tcprinnergridspan2.setVal( BigInteger.valueOf( 2) );
        // Create object for tcBorders
        TcPrInner.TcBorders tcprinnertcborders6 = wmlObjectFactory.createTcPrInnerTcBorders();
        tcpr6.setTcBorders(tcprinnertcborders6);
        // Create object for left
        CTBorder border37 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders6.setLeft(border37);
        border37.setVal(org.docx4j.wml.STBorder.SINGLE);
        border37.setSz( BigInteger.valueOf( 2) );
        border37.setColor( "000001");
        border37.setSpace( BigInteger.valueOf( 0) );
        // Create object for right
        CTBorder border38 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders6.setRight(border38);
        border38.setVal(org.docx4j.wml.STBorder.SINGLE);
        border38.setSz( BigInteger.valueOf( 2) );
        border38.setColor( "000001");
        border38.setSpace( BigInteger.valueOf( 0) );
        // Create object for top
        CTBorder border39 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders6.setTop(border39);
        border39.setVal(org.docx4j.wml.STBorder.SINGLE);
        border39.setSz( BigInteger.valueOf( 2) );
        border39.setColor( "000001");
        border39.setSpace( BigInteger.valueOf( 0) );
        // Create object for bottom
        CTBorder border40 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders6.setBottom(border40);
        border40.setVal(org.docx4j.wml.STBorder.SINGLE);
        border40.setSz( BigInteger.valueOf( 2) );
        border40.setColor( "000001");
        border40.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideH
        CTBorder border41 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders6.setInsideH(border41);
        border41.setVal(org.docx4j.wml.STBorder.SINGLE);
        border41.setSz( BigInteger.valueOf( 2) );
        border41.setColor( "000001");
        border41.setSpace( BigInteger.valueOf( 0) );
        // Create object for insideV
        CTBorder border42 = wmlObjectFactory.createCTBorder();
        tcprinnertcborders6.setInsideV(border42);
        border42.setVal(org.docx4j.wml.STBorder.SINGLE);
        border42.setSz( BigInteger.valueOf( 2) );
        border42.setColor( "000001");
        border42.setSpace( BigInteger.valueOf( 0) );
        // Create object for tcMar
        TcMar tcmar6 = wmlObjectFactory.createTcMar();
        tcpr6.setTcMar(tcmar6);
        // Create object for left
        TblWidth tblwidth18 = wmlObjectFactory.createTblWidth();
        tcmar6.setLeft(tblwidth18);
        tblwidth18.setType( "dxa");
        tblwidth18.setW( BigInteger.valueOf( 21) );
        // Create object for p
        P p6 = wmlObjectFactory.createP();
        tc6.getContent().add( p6);
        // Create object for pPr
        PPr ppr6 = wmlObjectFactory.createPPr();
        p6.setPPr(ppr6);
        // Create object for rPr
        ParaRPr pararpr6 = wmlObjectFactory.createParaRPr();
        ppr6.setRPr(pararpr6);
        // Create object for pStyle
        PPrBase.PStyle pprbasepstyle6 = wmlObjectFactory.createPPrBasePStyle();
        ppr6.setPStyle(pprbasepstyle6);
        pprbasepstyle6.setVal( "TableContents");
        // Create object for r
        R r6 = wmlObjectFactory.createR();
        p6.getContent().add( r6);
        // Create object for rPr
        RPr rpr6 = wmlObjectFactory.createRPr();
        r6.setRPr(rpr6);
        // Create object for t (wrapped in JAXBElement)
        Text text6 = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped6 = wmlObjectFactory.createRT(text6);
        r6.getContent().add( textWrapped6);
        text6.setValue( "Combined data");

        return tblWrapped;
    }
}
