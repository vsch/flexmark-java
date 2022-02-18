---
title: DocxConverter Extension Spec
author: Vladimir Schneider
version: 1.0
date: '2017-09-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## DocxConverter

Converts markdown to docx

# Headings

All headings

```````````````````````````````` example(Headings: 1) options(full-render)
# Heading 1
## Heading 2
### Heading 3
#### Heading 4
##### Heading 5
###### Heading 6
    
.
<?xml version="1.0" encoding="UTF-8"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
    <pkg:part pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:name="/_rels/.rels">
        <pkg:xmlData>
            <rel:Relationships xmlns:rel="http://schemas.openxmlformats.org/package/2006/relationships">
                <rel:Relationship Id="rId3" Target="docProps/app.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties"/>
                <rel:Relationship Id="rId2" Target="docProps/core.xml" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties"/>
                <rel:Relationship Id="rId1" Target="word/document.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"/>
            </rel:Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:name="/word/_rels/document.xml.rels">
        <pkg:xmlData>
            <rel:Relationships xmlns:rel="http://schemas.openxmlformats.org/package/2006/relationships">
                <rel:Relationship Id="rId5" Target="fontTable.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable"/>
                <rel:Relationship Id="rId1" Target="numbering.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering"/>
                <rel:Relationship Id="rId3" Target="settings.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings"/>
                <rel:Relationship Id="rId2" Target="styles.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"/>
                <rel:Relationship Id="rId6" Target="theme/theme1.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"/>
                <rel:Relationship Id="rId4" Target="webSettings.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings"/>
            </rel:Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml" pkg:name="/docProps/core.xml">
        <pkg:xmlData>
            <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties">
                <dcterms:created xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="dcterms:W3CDTF">2019-08-29T17:05:00Z</dcterms:created>
                <cp:lastModifiedBy>Vladimir Schneider</cp:lastModifiedBy>
                <dcterms:modified xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="dcterms:W3CDTF">2019-08-29T20:18:00Z</dcterms:modified>
                <cp:revision>6</cp:revision>
            </cp:coreProperties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml" pkg:name="/docProps/app.xml">
        <pkg:xmlData>
            <properties:Properties xmlns:properties="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties">
                <properties:AppVersion>16.0000</properties:AppVersion>
                <properties:Application>Microsoft Office Word</properties:Application>
                <properties:Characters>0</properties:Characters>
                <properties:CharactersWithSpaces>0</properties:CharactersWithSpaces>
                <properties:Company/>
                <properties:DocSecurity>0</properties:DocSecurity>
                <properties:HyperlinksChanged>false</properties:HyperlinksChanged>
                <properties:Lines>0</properties:Lines>
                <properties:LinksUpToDate>false</properties:LinksUpToDate>
                <properties:Pages>1</properties:Pages>
                <properties:Paragraphs>0</properties:Paragraphs>
                <properties:ScaleCrop>false</properties:ScaleCrop>
                <properties:SharedDoc>false</properties:SharedDoc>
                <properties:Template>Normal.dotm</properties:Template>
                <properties:TotalTime>4</properties:TotalTime>
                <properties:Words>0</properties:Words>
            </properties:Properties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml" pkg:name="/word/document.xml">
        <pkg:xmlData>
            <w:document mc:Ignorable="w14 w15 w16se w16cid wp14" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex" xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing">
                <w:body>
                    <w:bookmarkStart w:id="1" w:name="heading-1"/>
                    <w:bookmarkEnd w:id="1"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading1"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading 1</w:t>
                        </w:r>
                    </w:p>
                    <w:bookmarkStart w:id="2" w:name="heading-2"/>
                    <w:bookmarkEnd w:id="2"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading2"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading 2</w:t>
                        </w:r>
                    </w:p>
                    <w:bookmarkStart w:id="3" w:name="heading-3"/>
                    <w:bookmarkEnd w:id="3"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading3"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading 3</w:t>
                        </w:r>
                    </w:p>
                    <w:bookmarkStart w:id="4" w:name="heading-4"/>
                    <w:bookmarkEnd w:id="4"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading4"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading 4</w:t>
                        </w:r>
                    </w:p>
                    <w:bookmarkStart w:id="5" w:name="heading-5"/>
                    <w:bookmarkEnd w:id="5"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading5"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading 5</w:t>
                        </w:r>
                    </w:p>
                    <w:bookmarkStart w:id="6" w:name="heading-6"/>
                    <w:bookmarkEnd w:id="6"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading6"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading 6</w:t>
                        </w:r>
                    </w:p>
                    <w:sectPr w:rsidR="00AE6612">
                        <w:pgSz w:h="15840" w:w="12240"/>
                        <w:pgMar w:bottom="1134" w:footer="0" w:gutter="0" w:header="0" w:left="1134" w:right="1134" w:top="1134"/>
                        <w:cols w:space="720"/>
                        <w:formProt w:val="false"/>
                        <w:docGrid w:charSpace="-6145" w:linePitch="240"/>
                    </w:sectPr>
                </w:body>
            </w:document>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml" pkg:name="/word/settings.xml">
        <pkg:xmlData>
            <w:settings mc:Ignorable="w14 w15 w16se w16cid" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex">
                <w:zoom w:percent="81"/>
                <w:proofState w:grammar="clean" w:spelling="clean"/>
                <w:stylePaneFormatFilter w:val="1004"/>
                <w:defaultTabStop w:val="720"/>
                <w:characterSpacingControl w:val="doNotCompress"/>
                <w:compat>
                    <w:useFELayout/>
                    <w:compatSetting w:name="compatibilityMode" w:uri="http://schemas.microsoft.com/office/word" w:val="12"/>
                    <w:compatSetting w:name="useWord2013TrackBottomHyphenation" w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
                </w:compat>
                <w:rsids>
                    <w:rsidRoot w:val="00127E74"/>
                    <w:rsid w:val="00127E74"/>
                    <w:rsid w:val="00274FED"/>
                    <w:rsid w:val="0052043B"/>
                    <w:rsid w:val="00611C6B"/>
                    <w:rsid w:val="008857A9"/>
                    <w:rsid w:val="008E3D84"/>
                    <w:rsid w:val="009863D4"/>
                    <w:rsid w:val="00AE6612"/>
                    <w:rsid w:val="00EA0C5C"/>
                </w:rsids>
                <m:mathPr xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math">
                    <m:mathFont m:val="Cambria Math"/>
                    <m:brkBin m:val="before"/>
                    <m:brkBinSub m:val="--"/>
                    <m:smallFrac m:val="0"/>
                    <m:dispDef/>
                    <m:lMargin m:val="0"/>
                    <m:rMargin m:val="0"/>
                    <m:defJc m:val="centerGroup"/>
                    <m:wrapIndent m:val="1440"/>
                    <m:intLim m:val="subSup"/>
                    <m:naryLim m:val="undOvr"/>
                </m:mathPr>
                <w:themeFontLang w:eastAsia="zh-CN" w:val="en-CA"/>
                <w:clrSchemeMapping w:accent1="accent1" w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5" w:accent6="accent6" w:bg1="light1" w:bg2="light2" w:followedHyperlink="followedHyperlink" w:hyperlink="hyperlink" w:t1="dark1" w:t2="dark2"/>
                <w:decimalSymbol w:val="."/>
                <w:listSeparator w:val=","/>
                <w14:docId w14:val="0BBC9FB3"/>
                <w15:docId w15:val="{3EA718AD-8036-0543-B8ED-229FB08F78D4}"/>
            </w:settings>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml" pkg:name="/word/webSettings.xml">
        <pkg:xmlData>
            <w:webSettings xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"/>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml" pkg:name="/word/styles.xml">
        <pkg:xmlData>
            <w:styles mc:Ignorable="w14 w15 w16se w16cid" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex">
                <w:docDefaults>
                    <w:rPrDefault>
                        <w:rPr>
                            <w:rFonts w:ascii="Liberation Serif" w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:sz w:val="24"/>
                            <w:szCs w:val="24"/>
                            <w:lang w:bidi="hi-IN" w:eastAsia="zh-CN" w:val="en-CA"/>
                        </w:rPr>
                    </w:rPrDefault>
                    <w:pPrDefault/>
                </w:docDefaults>
                <w:style w:default="true" w:styleId="Normal" w:type="paragraph">
                    <w:name w:val="Normal"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="Calibri" w:cs="Times New Roman" w:eastAsia="Calibri" w:hAnsi="Calibri"/>
                        <w:color w:val="00000A"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading1" w:type="paragraph">
                    <w:name w:val="heading 1"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:qFormat/>
                    <w:rsid w:val="00611C6B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:pBdr>
                            <w:bottom w:color="000001" w:space="1" w:sz="2" w:val="single"/>
                        </w:pBdr>
                        <w:outlineLvl w:val="0"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="48"/>
                        <w:szCs w:val="48"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading2" w:type="paragraph">
                    <w:name w:val="heading 2"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="1"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:pBdr>
                            <w:bottom w:color="000001" w:space="1" w:sz="2" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:before="200"/>
                        <w:ind w:hanging="431" w:left="431"/>
                        <w:outlineLvl w:val="1"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="42"/>
                        <w:szCs w:val="42"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading3" w:type="paragraph">
                    <w:name w:val="heading 3"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="2"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:before="140"/>
                        <w:ind w:hanging="505" w:left="505"/>
                        <w:outlineLvl w:val="2"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="36"/>
                        <w:szCs w:val="36"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading4" w:type="paragraph">
                    <w:name w:val="heading 4"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="3"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:before="120"/>
                        <w:ind w:hanging="646" w:left="646"/>
                        <w:outlineLvl w:val="3"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="30"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading5" w:type="paragraph">
                    <w:name w:val="heading 5"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="4"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:after="60" w:before="120"/>
                        <w:ind w:hanging="794" w:left="794"/>
                        <w:outlineLvl w:val="4"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading6" w:type="paragraph">
                    <w:name w:val="heading 6"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="5"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:after="60" w:before="60"/>
                        <w:ind w:left="936"/>
                        <w:outlineLvl w:val="5"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:i/>
                        <w:color w:val="666666"/>
                    </w:rPr>
                </w:style>
                <w:style w:default="true" w:styleId="DefaultParagraphFont" w:type="character">
                    <w:name w:val="Default Paragraph Font"/>
                    <w:uiPriority w:val="1"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                </w:style>
                <w:style w:default="true" w:styleId="TableNormal" w:type="table">
                    <w:name w:val="Normal Table"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:tblPr>
                        <w:tblInd w:type="dxa" w:w="0"/>
                        <w:tblCellMar>
                            <w:top w:type="dxa" w:w="0"/>
                            <w:left w:type="dxa" w:w="108"/>
                            <w:bottom w:type="dxa" w:w="0"/>
                            <w:right w:type="dxa" w:w="108"/>
                        </w:tblCellMar>
                    </w:tblPr>
                </w:style>
                <w:style w:default="true" w:styleId="NoList" w:type="numbering">
                    <w:name w:val="No List"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Default" w:type="paragraph">
                    <w:name w:val="Default"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Heading" w:type="paragraph">
                    <w:name w:val="Heading"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                </w:style>
                <w:style w:styleId="TOCHeading" w:type="paragraph">
                    <w:name w:val="TOC Heading"/>
                    <w:basedOn w:val="Heading3"/>
                    <w:uiPriority w:val="39"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:keepLines/>
                        <w:numPr>
                            <w:numId w:val="0"/>
                        </w:numPr>
                        <w:spacing w:before="480"/>
                        <w:outlineLvl w:val="9"/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="StrongEmphasis" w:type="character">
                    <w:name w:val="Strong Emphasis"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Emphasis" w:type="character">
                    <w:name w:val="Emphasis"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:i/>
                        <w:iCs/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Superscript" w:type="character">
                    <w:name w:val="Superscript"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:position w:val="8"/>
                        <w:sz w:val="19"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Subscript" w:type="character">
                    <w:name w:val="Subscript"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:position w:val="-4"/>
                        <w:sz w:val="19"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Strikethrough" w:type="character">
                    <w:name w:val="Strikethrough"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:strike/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Underlined" w:type="character">
                    <w:name w:val="Underlined"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:u w:val="single"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="SourceText" w:type="character">
                    <w:name w:val="Source Text"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                        <w:color w:val="BB002F"/>
                        <w:bdr w:color="EEC5E1" w:frame="true" w:space="1" w:sz="2" w:val="single"/>
                        <w:shd w:color="auto" w:fill="FFF8E6" w:val="clear"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Bullets" w:type="character">
                    <w:name w:val="Bullets"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="OpenSymbol" w:cs="OpenSymbol" w:eastAsia="OpenSymbol" w:hAnsi="OpenSymbol"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="NumberingSymbols" w:type="character">
                    <w:name w:val="Numbering Symbols"/>
                    <w:qFormat/>
                </w:style>
                <w:style w:styleId="List" w:type="paragraph">
                    <w:name w:val="List"/>
                    <w:basedOn w:val="BodyText"/>
                    <w:rPr>
                        <w:rFonts w:cs="Lucida Sans"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Caption" w:type="paragraph">
                    <w:name w:val="caption"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                        <w:spacing w:after="120" w:before="120"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:cs="Lucida Sans"/>
                        <w:i/>
                        <w:iCs/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="HorizontalLine" w:type="paragraph">
                    <w:name w:val="Horizontal Line"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                        <w:pBdr>
                            <w:bottom w:color="808080" w:space="0" w:sz="6" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:after="283"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="12"/>
                        <w:szCs w:val="12"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Quotations" w:type="paragraph">
                    <w:name w:val="Quotations"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:pBdr>
                            <w:left w:color="CCCCCC" w:space="9" w:sz="16" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:after="140" w:before="140"/>
                        <w:ind w:left="240"/>
                    </w:pPr>
                    <w:rPr>
                        <w:color w:val="666666"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="AsideBlock" w:type="paragraph">
                    <w:name w:val="AsideBlock"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:pBdr>
                            <w:left w:color="3366FF" w:space="9" w:sz="16" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:after="140" w:before="140"/>
                        <w:ind w:left="240"/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="PreformattedText" w:type="paragraph">
                    <w:name w:val="Preformatted Text"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:pBdr>
                            <w:top w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                            <w:left w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                            <w:bottom w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                            <w:right w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                        </w:pBdr>
                        <w:shd w:color="auto" w:fill="EEEEEE" w:val="clear"/>
                        <w:spacing w:after="240" w:before="240"/>
                        <w:contextualSpacing/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                        <w:sz w:val="20"/>
                        <w:szCs w:val="20"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="TableContents" w:type="paragraph">
                    <w:name w:val="Table Contents"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="TableHeading" w:type="paragraph">
                    <w:name w:val="Table Heading"/>
                    <w:basedOn w:val="TableContents"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="TableCaption" w:type="paragraph">
                    <w:name w:val="Table Caption"/>
                    <w:basedOn w:val="TableContents"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:after="240" w:before="240"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="ParagraphTextBody" w:type="paragraph">
                    <w:name w:val="Paragraph Text Body"/>
                    <w:basedOn w:val="BodyText"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:after="120" w:before="240"/>
                    </w:pPr>
                </w:style>
                <w:style w:styleId="FootnoteReference" w:type="character">
                    <w:name w:val="footnote reference"/>
                    <w:rPr>
                        <w:vertAlign w:val="superscript"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="EndnoteReference" w:type="character">
                    <w:name w:val="endnote reference"/>
                    <w:unhideWhenUsed/>
                    <w:rPr>
                        <w:vertAlign w:val="superscript"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Index0" w:type="paragraph">
                    <w:name w:val="Index"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                    </w:pPr>
                </w:style>
                <w:style w:styleId="FootnoteText" w:type="paragraph">
                    <w:name w:val="footnote text"/>
                    <w:basedOn w:val="Normal"/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                        <w:ind w:hanging="240" w:left="240"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="20"/>
                        <w:szCs w:val="20"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Hyperlink" w:type="character">
                    <w:name w:val="Hyperlink"/>
                    <w:basedOn w:val="DefaultParagraphFont"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:rsid w:val="00FC1E75"/>
                    <w:rPr>
                        <w:color w:themeColor="hyperlink" w:val="0563C1"/>
                        <w:u w:val="single"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="ListNumber" w:type="paragraph">
                    <w:name w:val="List Number"/>
                    <w:basedOn w:val="Normal"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="00400E20"/>
                    <w:pPr>
                        <w:numPr>
                            <w:numId w:val="3"/>
                        </w:numPr>
                        <w:contextualSpacing/>
                    </w:pPr>
                </w:style>
                <w:style w:styleId="ListBullet" w:type="paragraph">
                    <w:name w:val="List Bullet"/>
                    <w:basedOn w:val="Normal"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="00C31C4B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:numId w:val="2"/>
                        </w:numPr>
                        <w:contextualSpacing/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="BodyTextChar" w:type="character">
                    <w:name w:val="Body Text Char"/>
                    <w:basedOn w:val="DefaultParagraphFont"/>
                    <w:link w:val="BodyText"/>
                    <w:rsid w:val="008E3D84"/>
                    <w:rPr>
                        <w:rFonts w:ascii="Calibri" w:cs="Times New Roman" w:eastAsia="Calibri" w:hAnsi="Calibri"/>
                        <w:color w:val="00000A"/>
                    </w:rPr>
                </w:style>
            </w:styles>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml" pkg:name="/word/numbering.xml">
        <pkg:xmlData>
            <w:numbering mc:Ignorable="w14 w15 w16se w16cid wp14" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex" xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing">
                <w:abstractNum w:abstractNumId="0">
                    <w:nsid w:val="FFFFFF7C"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="5E322D64"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1492" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1492"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="1">
                    <w:nsid w:val="FFFFFF7D"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="D9AC233C"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1209" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1209"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="2">
                    <w:nsid w:val="FFFFFF7E"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="F7E80D9E"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="926" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="926"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="3">
                    <w:nsid w:val="FFFFFF7F"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="2D125BD8"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="643" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="643"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="4">
                    <w:nsid w:val="FFFFFF80"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="ED3CC714"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1492" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1492"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="5">
                    <w:nsid w:val="FFFFFF81"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="AA7CD81E"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1209" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1209"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="6">
                    <w:nsid w:val="FFFFFF82"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="C324D35A"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="926" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="926"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="7">
                    <w:nsid w:val="FFFFFF83"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="0B1A59D4"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="643" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="643"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="8">
                    <w:nsid w:val="3746254C"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="16701846"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="ListNumber"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="283" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="283"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="567" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="567"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="850" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="850"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1134" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1134"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1417" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1417"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1701" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1701"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1984" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1984"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="2268" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="2268"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="2551" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="2551"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="9">
                    <w:nsid w:val="3AFF00F5"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="EFD689D6"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading1"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading2"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="4544"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading3"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading4"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading5"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading6"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="10">
                    <w:nsid w:val="42FC10CD"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="6292EA86"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="11">
                    <w:nsid w:val="471B1516"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="8AE615F6"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="12">
                    <w:nsid w:val="4A770700"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="C8F8452A"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="13">
                    <w:nsid w:val="52223094"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="B3FEC886"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:pStyle w:val="ListBullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="227" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="227"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="454" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="454"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="680" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="680"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="907" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="907"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1134" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1134"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1361" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1361"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1587" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1587"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1814" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1814"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="2041" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="2041"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="14">
                    <w:nsid w:val="57AE0420"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="F0AEE870"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="15">
                    <w:nsid w:val="59310431"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="0409001F"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="16">
                    <w:nsid w:val="76DE6E9D"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="8D7C4498"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:num w:numId="1">
                    <w:abstractNumId w:val="10"/>
                </w:num>
                <w:num w:numId="2">
                    <w:abstractNumId w:val="13"/>
                </w:num>
                <w:num w:numId="3">
                    <w:abstractNumId w:val="8"/>
                </w:num>
                <w:num w:numId="4">
                    <w:abstractNumId w:val="15"/>
                </w:num>
                <w:num w:numId="5">
                    <w:abstractNumId w:val="11"/>
                </w:num>
                <w:num w:numId="6">
                    <w:abstractNumId w:val="12"/>
                </w:num>
                <w:num w:numId="7">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="8">
                    <w:abstractNumId w:val="14"/>
                </w:num>
                <w:num w:numId="9">
                    <w:abstractNumId w:val="16"/>
                </w:num>
                <w:num w:numId="10">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="11">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="12">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="13">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="14">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="15">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="16">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="17">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="18">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="19">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="20">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="21">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="22">
                    <w:abstractNumId w:val="0"/>
                </w:num>
                <w:num w:numId="23">
                    <w:abstractNumId w:val="1"/>
                </w:num>
                <w:num w:numId="24">
                    <w:abstractNumId w:val="2"/>
                </w:num>
                <w:num w:numId="25">
                    <w:abstractNumId w:val="3"/>
                </w:num>
                <w:num w:numId="26">
                    <w:abstractNumId w:val="4"/>
                </w:num>
                <w:num w:numId="27">
                    <w:abstractNumId w:val="5"/>
                </w:num>
                <w:num w:numId="28">
                    <w:abstractNumId w:val="6"/>
                </w:num>
                <w:num w:numId="29">
                    <w:abstractNumId w:val="7"/>
                </w:num>
            </w:numbering>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml" pkg:name="/word/fontTable.xml">
        <pkg:xmlData>
            <w:fonts xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
                <w:font w:name="Symbol">
                    <w:panose1 w:val="05050102010706020507"/>
                    <w:charset w:val="02"/>
                    <w:family w:val="decorative"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="80000000" w:csb1="00000000" w:usb0="00000000" w:usb1="10000000" w:usb2="00000000" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Times New Roman">
                    <w:panose1 w:val="02020603050405020304"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="OpenSymbol">
                    <w:altName w:val="Cambria"/>
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:notTrueType/>
                    <w:pitch w:val="default"/>
                </w:font>
                <w:font w:name="Liberation Serif">
                    <w:altName w:val="Times New Roman"/>
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:notTrueType/>
                    <w:pitch w:val="default"/>
                </w:font>
                <w:font w:name="SimSun">
                    <w:altName w:val="宋体"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="00040001" w:csb1="00000000" w:usb0="00000003" w:usb1="288F0000" w:usb2="00000016" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Lucida Sans">
                    <w:panose1 w:val="020B0602030504020204"/>
                    <w:charset w:val="4D"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="00000001" w:csb1="00000000" w:usb0="00000003" w:usb1="00000000" w:usb2="00000000" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Calibri">
                    <w:panose1 w:val="020F0502020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Courier New">
                    <w:panose1 w:val="02070309020205020404"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="modern"/>
                    <w:pitch w:val="fixed"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002AFF" w:usb1="C0007843" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Liberation Mono">
                    <w:altName w:val="Cambria"/>
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:notTrueType/>
                    <w:pitch w:val="default"/>
                </w:font>
                <w:font w:name="DengXian Light">
                    <w:altName w:val="等线 Light"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="0004000F" w:csb1="00000000" w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Calibri Light">
                    <w:panose1 w:val="020F0302020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="DengXian">
                    <w:altName w:val="等线"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="0004000F" w:csb1="00000000" w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000"/>
                </w:font>
            </w:fonts>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml" pkg:name="/word/theme/theme1.xml">
        <pkg:xmlData>
            <a:theme name="Office Theme" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                <a:themeElements>
                    <a:clrScheme name="Office">
                        <a:dk1>
                            <a:sysClr lastClr="000000" val="windowText"/>
                        </a:dk1>
                        <a:lt1>
                            <a:sysClr lastClr="FFFFFF" val="window"/>
                        </a:lt1>
                        <a:dk2>
                            <a:srgbClr val="44546A"/>
                        </a:dk2>
                        <a:lt2>
                            <a:srgbClr val="E7E6E6"/>
                        </a:lt2>
                        <a:accent1>
                            <a:srgbClr val="4472C4"/>
                        </a:accent1>
                        <a:accent2>
                            <a:srgbClr val="ED7D31"/>
                        </a:accent2>
                        <a:accent3>
                            <a:srgbClr val="A5A5A5"/>
                        </a:accent3>
                        <a:accent4>
                            <a:srgbClr val="FFC000"/>
                        </a:accent4>
                        <a:accent5>
                            <a:srgbClr val="5B9BD5"/>
                        </a:accent5>
                        <a:accent6>
                            <a:srgbClr val="70AD47"/>
                        </a:accent6>
                        <a:hlink>
                            <a:srgbClr val="0563C1"/>
                        </a:hlink>
                        <a:folHlink>
                            <a:srgbClr val="954F72"/>
                        </a:folHlink>
                    </a:clrScheme>
                    <a:fontScheme name="Office">
                        <a:majorFont>
                            <a:latin panose="020F0302020204030204" typeface="Calibri Light"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游ゴシック Light"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线 Light"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Times New Roman"/>
                            <a:font script="Hebr" typeface="Times New Roman"/>
                            <a:font script="Thai" typeface="Angsana New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="MoolBoran"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Times New Roman"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                            <a:font script="Armn" typeface="Arial"/>
                            <a:font script="Bugi" typeface="Leelawadee UI"/>
                            <a:font script="Bopo" typeface="Microsoft JhengHei"/>
                            <a:font script="Java" typeface="Javanese Text"/>
                            <a:font script="Lisu" typeface="Segoe UI"/>
                            <a:font script="Mymr" typeface="Myanmar Text"/>
                            <a:font script="Nkoo" typeface="Ebrima"/>
                            <a:font script="Olck" typeface="Nirmala UI"/>
                            <a:font script="Osma" typeface="Ebrima"/>
                            <a:font script="Phag" typeface="Phagspa"/>
                            <a:font script="Syrn" typeface="Estrangelo Edessa"/>
                            <a:font script="Syrj" typeface="Estrangelo Edessa"/>
                            <a:font script="Syre" typeface="Estrangelo Edessa"/>
                            <a:font script="Sora" typeface="Nirmala UI"/>
                            <a:font script="Tale" typeface="Microsoft Tai Le"/>
                            <a:font script="Talu" typeface="Microsoft New Tai Lue"/>
                            <a:font script="Tfng" typeface="Ebrima"/>
                        </a:majorFont>
                        <a:minorFont>
                            <a:latin panose="020F0502020204030204" typeface="Calibri"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游明朝"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Arial"/>
                            <a:font script="Hebr" typeface="Arial"/>
                            <a:font script="Thai" typeface="Cordia New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="DaunPenh"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Arial"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                            <a:font script="Armn" typeface="Arial"/>
                            <a:font script="Bugi" typeface="Leelawadee UI"/>
                            <a:font script="Bopo" typeface="Microsoft JhengHei"/>
                            <a:font script="Java" typeface="Javanese Text"/>
                            <a:font script="Lisu" typeface="Segoe UI"/>
                            <a:font script="Mymr" typeface="Myanmar Text"/>
                            <a:font script="Nkoo" typeface="Ebrima"/>
                            <a:font script="Olck" typeface="Nirmala UI"/>
                            <a:font script="Osma" typeface="Ebrima"/>
                            <a:font script="Phag" typeface="Phagspa"/>
                            <a:font script="Syrn" typeface="Estrangelo Edessa"/>
                            <a:font script="Syrj" typeface="Estrangelo Edessa"/>
                            <a:font script="Syre" typeface="Estrangelo Edessa"/>
                            <a:font script="Sora" typeface="Nirmala UI"/>
                            <a:font script="Tale" typeface="Microsoft Tai Le"/>
                            <a:font script="Talu" typeface="Microsoft New Tai Lue"/>
                            <a:font script="Tfng" typeface="Ebrima"/>
                        </a:minorFont>
                    </a:fontScheme>
                    <a:fmtScheme name="Office">
                        <a:fillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:gradFill rotWithShape="true">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="110000"/>
                                            <a:satMod val="105000"/>
                                            <a:tint val="67000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="103000"/>
                                            <a:tint val="73000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="109000"/>
                                            <a:tint val="81000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="false"/>
                            </a:gradFill>
                            <a:gradFill rotWithShape="true">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="103000"/>
                                            <a:lumMod val="102000"/>
                                            <a:tint val="94000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="110000"/>
                                            <a:lumMod val="100000"/>
                                            <a:shade val="100000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="99000"/>
                                            <a:satMod val="120000"/>
                                            <a:shade val="78000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="false"/>
                            </a:gradFill>
                        </a:fillStyleLst>
                        <a:lnStyleLst>
                            <a:ln algn="ctr" cap="flat" cmpd="sng" w="6350">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln algn="ctr" cap="flat" cmpd="sng" w="12700">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln algn="ctr" cap="flat" cmpd="sng" w="19050">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                        </a:lnStyleLst>
                        <a:effectStyleLst>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst>
                                    <a:outerShdw algn="ctr" blurRad="57150" dir="5400000" dist="19050" rotWithShape="false">
                                        <a:srgbClr val="000000">
                                            <a:alpha val="63000"/>
                                        </a:srgbClr>
                                    </a:outerShdw>
                                </a:effectLst>
                            </a:effectStyle>
                        </a:effectStyleLst>
                        <a:bgFillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:solidFill>
                                <a:schemeClr val="phClr">
                                    <a:tint val="95000"/>
                                    <a:satMod val="170000"/>
                                </a:schemeClr>
                            </a:solidFill>
                            <a:gradFill rotWithShape="true">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="93000"/>
                                            <a:satMod val="150000"/>
                                            <a:shade val="98000"/>
                                            <a:lumMod val="102000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="98000"/>
                                            <a:satMod val="130000"/>
                                            <a:shade val="90000"/>
                                            <a:lumMod val="103000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:shade val="63000"/>
                                            <a:satMod val="120000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="false"/>
                            </a:gradFill>
                        </a:bgFillStyleLst>
                    </a:fmtScheme>
                </a:themeElements>
                <a:objectDefaults/>
                <a:extraClrSchemeLst/>
                <a:extLst>
                    <a:ext uri="{05A4C25C-085E-4340-85A3-A5531E510DB2}">
                        <thm15:themeFamily id="{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}" name="Office Theme" vid="{4A3C46E8-61CC-4603-A589-7422A47A8E4A}" xmlns:thm15="http://schemas.microsoft.com/office/thememl/2012/main"/>
                    </a:ext>
                </a:extLst>
            </a:theme>
        </pkg:xmlData>
    </pkg:part>
</pkg:package>
````````````````````````````````


```````````````````````````````` example(Headings: 2) options(full-render)
# Heading
.
<?xml version="1.0" encoding="UTF-8"?>
<pkg:package xmlns:pkg="http://schemas.microsoft.com/office/2006/xmlPackage">
    <pkg:part pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:name="/_rels/.rels">
        <pkg:xmlData>
            <rel:Relationships xmlns:rel="http://schemas.openxmlformats.org/package/2006/relationships">
                <rel:Relationship Id="rId3" Target="docProps/app.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/extended-properties"/>
                <rel:Relationship Id="rId2" Target="docProps/core.xml" Type="http://schemas.openxmlformats.org/package/2006/relationships/metadata/core-properties"/>
                <rel:Relationship Id="rId1" Target="word/document.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument"/>
            </rel:Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-package.relationships+xml" pkg:name="/word/_rels/document.xml.rels">
        <pkg:xmlData>
            <rel:Relationships xmlns:rel="http://schemas.openxmlformats.org/package/2006/relationships">
                <rel:Relationship Id="rId5" Target="fontTable.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/fontTable"/>
                <rel:Relationship Id="rId1" Target="numbering.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/numbering"/>
                <rel:Relationship Id="rId3" Target="settings.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/settings"/>
                <rel:Relationship Id="rId2" Target="styles.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles"/>
                <rel:Relationship Id="rId6" Target="theme/theme1.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/theme"/>
                <rel:Relationship Id="rId4" Target="webSettings.xml" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/webSettings"/>
            </rel:Relationships>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-package.core-properties+xml" pkg:name="/docProps/core.xml">
        <pkg:xmlData>
            <cp:coreProperties xmlns:cp="http://schemas.openxmlformats.org/package/2006/metadata/core-properties">
                <dcterms:created xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="dcterms:W3CDTF">2019-08-29T17:05:00Z</dcterms:created>
                <cp:lastModifiedBy>Vladimir Schneider</cp:lastModifiedBy>
                <dcterms:modified xmlns:dcterms="http://purl.org/dc/terms/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:type="dcterms:W3CDTF">2019-08-29T20:18:00Z</dcterms:modified>
                <cp:revision>6</cp:revision>
            </cp:coreProperties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.extended-properties+xml" pkg:name="/docProps/app.xml">
        <pkg:xmlData>
            <properties:Properties xmlns:properties="http://schemas.openxmlformats.org/officeDocument/2006/extended-properties">
                <properties:AppVersion>16.0000</properties:AppVersion>
                <properties:Application>Microsoft Office Word</properties:Application>
                <properties:Characters>0</properties:Characters>
                <properties:CharactersWithSpaces>0</properties:CharactersWithSpaces>
                <properties:Company/>
                <properties:DocSecurity>0</properties:DocSecurity>
                <properties:HyperlinksChanged>false</properties:HyperlinksChanged>
                <properties:Lines>0</properties:Lines>
                <properties:LinksUpToDate>false</properties:LinksUpToDate>
                <properties:Pages>1</properties:Pages>
                <properties:Paragraphs>0</properties:Paragraphs>
                <properties:ScaleCrop>false</properties:ScaleCrop>
                <properties:SharedDoc>false</properties:SharedDoc>
                <properties:Template>Normal.dotm</properties:Template>
                <properties:TotalTime>4</properties:TotalTime>
                <properties:Words>0</properties:Words>
            </properties:Properties>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.document.main+xml" pkg:name="/word/document.xml">
        <pkg:xmlData>
            <w:document mc:Ignorable="w14 w15 w16se w16cid wp14" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex" xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing">
                <w:body>
                    <w:bookmarkStart w:id="1" w:name="heading"/>
                    <w:bookmarkEnd w:id="1"/>
                    <w:p>
                        <w:pPr>
                            <w:pStyle w:val="Heading1"/>
                        </w:pPr>
                        <w:r>
                            <w:t>Heading</w:t>
                        </w:r>
                    </w:p>
                    <w:sectPr w:rsidR="00AE6612">
                        <w:pgSz w:h="15840" w:w="12240"/>
                        <w:pgMar w:bottom="1134" w:footer="0" w:gutter="0" w:header="0" w:left="1134" w:right="1134" w:top="1134"/>
                        <w:cols w:space="720"/>
                        <w:formProt w:val="false"/>
                        <w:docGrid w:charSpace="-6145" w:linePitch="240"/>
                    </w:sectPr>
                </w:body>
            </w:document>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.settings+xml" pkg:name="/word/settings.xml">
        <pkg:xmlData>
            <w:settings mc:Ignorable="w14 w15 w16se w16cid" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex">
                <w:zoom w:percent="81"/>
                <w:proofState w:grammar="clean" w:spelling="clean"/>
                <w:stylePaneFormatFilter w:val="1004"/>
                <w:defaultTabStop w:val="720"/>
                <w:characterSpacingControl w:val="doNotCompress"/>
                <w:compat>
                    <w:useFELayout/>
                    <w:compatSetting w:name="compatibilityMode" w:uri="http://schemas.microsoft.com/office/word" w:val="12"/>
                    <w:compatSetting w:name="useWord2013TrackBottomHyphenation" w:uri="http://schemas.microsoft.com/office/word" w:val="1"/>
                </w:compat>
                <w:rsids>
                    <w:rsidRoot w:val="00127E74"/>
                    <w:rsid w:val="00127E74"/>
                    <w:rsid w:val="00274FED"/>
                    <w:rsid w:val="0052043B"/>
                    <w:rsid w:val="00611C6B"/>
                    <w:rsid w:val="008857A9"/>
                    <w:rsid w:val="008E3D84"/>
                    <w:rsid w:val="009863D4"/>
                    <w:rsid w:val="00AE6612"/>
                    <w:rsid w:val="00EA0C5C"/>
                </w:rsids>
                <m:mathPr xmlns:m="http://schemas.openxmlformats.org/officeDocument/2006/math">
                    <m:mathFont m:val="Cambria Math"/>
                    <m:brkBin m:val="before"/>
                    <m:brkBinSub m:val="--"/>
                    <m:smallFrac m:val="0"/>
                    <m:dispDef/>
                    <m:lMargin m:val="0"/>
                    <m:rMargin m:val="0"/>
                    <m:defJc m:val="centerGroup"/>
                    <m:wrapIndent m:val="1440"/>
                    <m:intLim m:val="subSup"/>
                    <m:naryLim m:val="undOvr"/>
                </m:mathPr>
                <w:themeFontLang w:eastAsia="zh-CN" w:val="en-CA"/>
                <w:clrSchemeMapping w:accent1="accent1" w:accent2="accent2" w:accent3="accent3" w:accent4="accent4" w:accent5="accent5" w:accent6="accent6" w:bg1="light1" w:bg2="light2" w:followedHyperlink="followedHyperlink" w:hyperlink="hyperlink" w:t1="dark1" w:t2="dark2"/>
                <w:decimalSymbol w:val="."/>
                <w:listSeparator w:val=","/>
                <w14:docId w14:val="0BBC9FB3"/>
                <w15:docId w15:val="{3EA718AD-8036-0543-B8ED-229FB08F78D4}"/>
            </w:settings>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.webSettings+xml" pkg:name="/word/webSettings.xml">
        <pkg:xmlData>
            <w:webSettings xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main"/>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.styles+xml" pkg:name="/word/styles.xml">
        <pkg:xmlData>
            <w:styles mc:Ignorable="w14 w15 w16se w16cid" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex">
                <w:docDefaults>
                    <w:rPrDefault>
                        <w:rPr>
                            <w:rFonts w:ascii="Liberation Serif" w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:sz w:val="24"/>
                            <w:szCs w:val="24"/>
                            <w:lang w:bidi="hi-IN" w:eastAsia="zh-CN" w:val="en-CA"/>
                        </w:rPr>
                    </w:rPrDefault>
                    <w:pPrDefault/>
                </w:docDefaults>
                <w:style w:default="true" w:styleId="Normal" w:type="paragraph">
                    <w:name w:val="Normal"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="Calibri" w:cs="Times New Roman" w:eastAsia="Calibri" w:hAnsi="Calibri"/>
                        <w:color w:val="00000A"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading1" w:type="paragraph">
                    <w:name w:val="heading 1"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:qFormat/>
                    <w:rsid w:val="00611C6B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:pBdr>
                            <w:bottom w:color="000001" w:space="1" w:sz="2" w:val="single"/>
                        </w:pBdr>
                        <w:outlineLvl w:val="0"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="48"/>
                        <w:szCs w:val="48"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading2" w:type="paragraph">
                    <w:name w:val="heading 2"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="1"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:pBdr>
                            <w:bottom w:color="000001" w:space="1" w:sz="2" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:before="200"/>
                        <w:ind w:hanging="431" w:left="431"/>
                        <w:outlineLvl w:val="1"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="42"/>
                        <w:szCs w:val="42"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading3" w:type="paragraph">
                    <w:name w:val="heading 3"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="2"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:before="140"/>
                        <w:ind w:hanging="505" w:left="505"/>
                        <w:outlineLvl w:val="2"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="36"/>
                        <w:szCs w:val="36"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading4" w:type="paragraph">
                    <w:name w:val="heading 4"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="3"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:before="120"/>
                        <w:ind w:hanging="646" w:left="646"/>
                        <w:outlineLvl w:val="3"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                        <w:sz w:val="30"/>
                        <w:szCs w:val="30"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading5" w:type="paragraph">
                    <w:name w:val="heading 5"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="4"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:after="60" w:before="120"/>
                        <w:ind w:hanging="794" w:left="794"/>
                        <w:outlineLvl w:val="4"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Heading6" w:type="paragraph">
                    <w:name w:val="heading 6"/>
                    <w:basedOn w:val="Heading"/>
                    <w:next w:val="ParagraphTextBody"/>
                    <w:uiPriority w:val="9"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="0052043B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:ilvl w:val="5"/>
                            <w:numId w:val="21"/>
                        </w:numPr>
                        <w:spacing w:after="60" w:before="60"/>
                        <w:ind w:left="936"/>
                        <w:outlineLvl w:val="5"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:i/>
                        <w:color w:val="666666"/>
                    </w:rPr>
                </w:style>
                <w:style w:default="true" w:styleId="DefaultParagraphFont" w:type="character">
                    <w:name w:val="Default Paragraph Font"/>
                    <w:uiPriority w:val="1"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                </w:style>
                <w:style w:default="true" w:styleId="TableNormal" w:type="table">
                    <w:name w:val="Normal Table"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:tblPr>
                        <w:tblInd w:type="dxa" w:w="0"/>
                        <w:tblCellMar>
                            <w:top w:type="dxa" w:w="0"/>
                            <w:left w:type="dxa" w:w="108"/>
                            <w:bottom w:type="dxa" w:w="0"/>
                            <w:right w:type="dxa" w:w="108"/>
                        </w:tblCellMar>
                    </w:tblPr>
                </w:style>
                <w:style w:default="true" w:styleId="NoList" w:type="numbering">
                    <w:name w:val="No List"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Default" w:type="paragraph">
                    <w:name w:val="Default"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Heading" w:type="paragraph">
                    <w:name w:val="Heading"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                </w:style>
                <w:style w:styleId="TOCHeading" w:type="paragraph">
                    <w:name w:val="TOC Heading"/>
                    <w:basedOn w:val="Heading3"/>
                    <w:uiPriority w:val="39"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:keepLines/>
                        <w:numPr>
                            <w:numId w:val="0"/>
                        </w:numPr>
                        <w:spacing w:before="480"/>
                        <w:outlineLvl w:val="9"/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="StrongEmphasis" w:type="character">
                    <w:name w:val="Strong Emphasis"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Emphasis" w:type="character">
                    <w:name w:val="Emphasis"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:i/>
                        <w:iCs/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Superscript" w:type="character">
                    <w:name w:val="Superscript"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:position w:val="8"/>
                        <w:sz w:val="19"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Subscript" w:type="character">
                    <w:name w:val="Subscript"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:position w:val="-4"/>
                        <w:sz w:val="19"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Strikethrough" w:type="character">
                    <w:name w:val="Strikethrough"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:strike/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Underlined" w:type="character">
                    <w:name w:val="Underlined"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:u w:val="single"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="SourceText" w:type="character">
                    <w:name w:val="Source Text"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                        <w:color w:val="BB002F"/>
                        <w:bdr w:color="EEC5E1" w:frame="true" w:space="1" w:sz="2" w:val="single"/>
                        <w:shd w:color="auto" w:fill="FFF8E6" w:val="clear"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Bullets" w:type="character">
                    <w:name w:val="Bullets"/>
                    <w:qFormat/>
                    <w:rPr>
                        <w:rFonts w:ascii="OpenSymbol" w:cs="OpenSymbol" w:eastAsia="OpenSymbol" w:hAnsi="OpenSymbol"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="NumberingSymbols" w:type="character">
                    <w:name w:val="Numbering Symbols"/>
                    <w:qFormat/>
                </w:style>
                <w:style w:styleId="List" w:type="paragraph">
                    <w:name w:val="List"/>
                    <w:basedOn w:val="BodyText"/>
                    <w:rPr>
                        <w:rFonts w:cs="Lucida Sans"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Caption" w:type="paragraph">
                    <w:name w:val="caption"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                        <w:spacing w:after="120" w:before="120"/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:cs="Lucida Sans"/>
                        <w:i/>
                        <w:iCs/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="HorizontalLine" w:type="paragraph">
                    <w:name w:val="Horizontal Line"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                        <w:pBdr>
                            <w:bottom w:color="808080" w:space="0" w:sz="6" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:after="283"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="12"/>
                        <w:szCs w:val="12"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Quotations" w:type="paragraph">
                    <w:name w:val="Quotations"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:pBdr>
                            <w:left w:color="CCCCCC" w:space="9" w:sz="16" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:after="140" w:before="140"/>
                        <w:ind w:left="240"/>
                    </w:pPr>
                    <w:rPr>
                        <w:color w:val="666666"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="AsideBlock" w:type="paragraph">
                    <w:name w:val="AsideBlock"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:pBdr>
                            <w:left w:color="3366FF" w:space="9" w:sz="16" w:val="single"/>
                        </w:pBdr>
                        <w:spacing w:after="140" w:before="140"/>
                        <w:ind w:left="240"/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="PreformattedText" w:type="paragraph">
                    <w:name w:val="Preformatted Text"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:pBdr>
                            <w:top w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                            <w:left w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                            <w:bottom w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                            <w:right w:color="B2B2B2" w:space="1" w:sz="2" w:val="single"/>
                        </w:pBdr>
                        <w:shd w:color="auto" w:fill="EEEEEE" w:val="clear"/>
                        <w:spacing w:after="240" w:before="240"/>
                        <w:contextualSpacing/>
                    </w:pPr>
                    <w:rPr>
                        <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                        <w:sz w:val="20"/>
                        <w:szCs w:val="20"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="TableContents" w:type="paragraph">
                    <w:name w:val="Table Contents"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="TableHeading" w:type="paragraph">
                    <w:name w:val="Table Heading"/>
                    <w:basedOn w:val="TableContents"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="TableCaption" w:type="paragraph">
                    <w:name w:val="Table Caption"/>
                    <w:basedOn w:val="TableContents"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:after="240" w:before="240"/>
                    </w:pPr>
                    <w:rPr>
                        <w:b/>
                        <w:bCs/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="ParagraphTextBody" w:type="paragraph">
                    <w:name w:val="Paragraph Text Body"/>
                    <w:basedOn w:val="BodyText"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:spacing w:after="120" w:before="240"/>
                    </w:pPr>
                </w:style>
                <w:style w:styleId="FootnoteReference" w:type="character">
                    <w:name w:val="footnote reference"/>
                    <w:rPr>
                        <w:vertAlign w:val="superscript"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="EndnoteReference" w:type="character">
                    <w:name w:val="endnote reference"/>
                    <w:unhideWhenUsed/>
                    <w:rPr>
                        <w:vertAlign w:val="superscript"/>
                    </w:rPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="Index0" w:type="paragraph">
                    <w:name w:val="Index"/>
                    <w:basedOn w:val="Normal"/>
                    <w:qFormat/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                    </w:pPr>
                </w:style>
                <w:style w:styleId="FootnoteText" w:type="paragraph">
                    <w:name w:val="footnote text"/>
                    <w:basedOn w:val="Normal"/>
                    <w:pPr>
                        <w:suppressLineNumbers/>
                        <w:ind w:hanging="240" w:left="240"/>
                    </w:pPr>
                    <w:rPr>
                        <w:sz w:val="20"/>
                        <w:szCs w:val="20"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="Hyperlink" w:type="character">
                    <w:name w:val="Hyperlink"/>
                    <w:basedOn w:val="DefaultParagraphFont"/>
                    <w:uiPriority w:val="99"/>
                    <w:unhideWhenUsed/>
                    <w:rsid w:val="00FC1E75"/>
                    <w:rPr>
                        <w:color w:themeColor="hyperlink" w:val="0563C1"/>
                        <w:u w:val="single"/>
                    </w:rPr>
                </w:style>
                <w:style w:styleId="ListNumber" w:type="paragraph">
                    <w:name w:val="List Number"/>
                    <w:basedOn w:val="Normal"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="00400E20"/>
                    <w:pPr>
                        <w:numPr>
                            <w:numId w:val="3"/>
                        </w:numPr>
                        <w:contextualSpacing/>
                    </w:pPr>
                </w:style>
                <w:style w:styleId="ListBullet" w:type="paragraph">
                    <w:name w:val="List Bullet"/>
                    <w:basedOn w:val="Normal"/>
                    <w:uiPriority w:val="99"/>
                    <w:semiHidden/>
                    <w:unhideWhenUsed/>
                    <w:qFormat/>
                    <w:rsid w:val="00C31C4B"/>
                    <w:pPr>
                        <w:numPr>
                            <w:numId w:val="2"/>
                        </w:numPr>
                        <w:contextualSpacing/>
                    </w:pPr>
                </w:style>
                <w:style w:customStyle="true" w:styleId="BodyTextChar" w:type="character">
                    <w:name w:val="Body Text Char"/>
                    <w:basedOn w:val="DefaultParagraphFont"/>
                    <w:link w:val="BodyText"/>
                    <w:rsid w:val="008E3D84"/>
                    <w:rPr>
                        <w:rFonts w:ascii="Calibri" w:cs="Times New Roman" w:eastAsia="Calibri" w:hAnsi="Calibri"/>
                        <w:color w:val="00000A"/>
                    </w:rPr>
                </w:style>
            </w:styles>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.numbering+xml" pkg:name="/word/numbering.xml">
        <pkg:xmlData>
            <w:numbering mc:Ignorable="w14 w15 w16se w16cid wp14" xmlns:mc="http://schemas.openxmlformats.org/markup-compatibility/2006" xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main" xmlns:w14="http://schemas.microsoft.com/office/word/2010/wordml" xmlns:w15="http://schemas.microsoft.com/office/word/2012/wordml" xmlns:w16cid="http://schemas.microsoft.com/office/word/2016/wordml/cid" xmlns:w16se="http://schemas.microsoft.com/office/word/2015/wordml/symex" xmlns:wp14="http://schemas.microsoft.com/office/word/2010/wordprocessingDrawing">
                <w:abstractNum w:abstractNumId="0">
                    <w:nsid w:val="FFFFFF7C"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="5E322D64"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1492" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1492"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="1">
                    <w:nsid w:val="FFFFFF7D"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="D9AC233C"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1209" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1209"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="2">
                    <w:nsid w:val="FFFFFF7E"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="F7E80D9E"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="926" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="926"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="3">
                    <w:nsid w:val="FFFFFF7F"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="2D125BD8"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="643" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="643"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="4">
                    <w:nsid w:val="FFFFFF80"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="ED3CC714"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1492" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1492"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="5">
                    <w:nsid w:val="FFFFFF81"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="AA7CD81E"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1209" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="1209"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="6">
                    <w:nsid w:val="FFFFFF82"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="C324D35A"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="926" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="926"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="7">
                    <w:nsid w:val="FFFFFF83"/>
                    <w:multiLevelType w:val="singleLevel"/>
                    <w:tmpl w:val="0B1A59D4"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="643" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="360" w:left="643"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:ascii="Symbol" w:hAnsi="Symbol" w:hint="default"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="8">
                    <w:nsid w:val="3746254C"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="16701846"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="ListNumber"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="283" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="283"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="567" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="567"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="850" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="850"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1134" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1134"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1417" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1417"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1701" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1701"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1984" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="1984"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="2268" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="2268"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="2551" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="283" w:left="2551"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="9">
                    <w:nsid w:val="3AFF00F5"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="EFD689D6"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading1"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading2"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="4544"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading3"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading4"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading5"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:pStyle w:val="Heading6"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="10">
                    <w:nsid w:val="42FC10CD"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="6292EA86"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="none"/>
                        <w:suff w:val="nothing"/>
                        <w:lvlText w:val=""/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:firstLine="0" w:left="0"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="11">
                    <w:nsid w:val="471B1516"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="8AE615F6"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="12">
                    <w:nsid w:val="4A770700"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="C8F8452A"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="13">
                    <w:nsid w:val="52223094"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="B3FEC886"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:pStyle w:val="ListBullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="227" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="227"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="454" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="454"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="680" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="680"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="907" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="907"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1134" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1134"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1361" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1361"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1587" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1587"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="1814" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="1814"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="bullet"/>
                        <w:lvlText w:val="•"/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:tabs>
                                <w:tab w:pos="2041" w:val="num"/>
                            </w:tabs>
                            <w:ind w:hanging="227" w:left="2041"/>
                        </w:pPr>
                        <w:rPr>
                            <w:rFonts w:cs="OpenSymbol"/>
                        </w:rPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="14">
                    <w:nsid w:val="57AE0420"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="F0AEE870"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="15">
                    <w:nsid w:val="59310431"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="0409001F"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:abstractNum w:abstractNumId="16">
                    <w:nsid w:val="76DE6E9D"/>
                    <w:multiLevelType w:val="multilevel"/>
                    <w:tmpl w:val="8D7C4498"/>
                    <w:lvl w:ilvl="0">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="360" w:left="360"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="1">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="432" w:left="792"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="2">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="504" w:left="1224"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="3">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="648" w:left="1728"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="4">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="792" w:left="2232"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="5">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="936" w:left="2736"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="6">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1080" w:left="3240"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="7">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1224" w:left="3744"/>
                        </w:pPr>
                    </w:lvl>
                    <w:lvl w:ilvl="8">
                        <w:start w:val="1"/>
                        <w:numFmt w:val="decimal"/>
                        <w:lvlText w:val="%1.%2.%3.%4.%5.%6.%7.%8.%9."/>
                        <w:lvlJc w:val="left"/>
                        <w:pPr>
                            <w:ind w:hanging="1440" w:left="4320"/>
                        </w:pPr>
                    </w:lvl>
                </w:abstractNum>
                <w:num w:numId="1">
                    <w:abstractNumId w:val="10"/>
                </w:num>
                <w:num w:numId="2">
                    <w:abstractNumId w:val="13"/>
                </w:num>
                <w:num w:numId="3">
                    <w:abstractNumId w:val="8"/>
                </w:num>
                <w:num w:numId="4">
                    <w:abstractNumId w:val="15"/>
                </w:num>
                <w:num w:numId="5">
                    <w:abstractNumId w:val="11"/>
                </w:num>
                <w:num w:numId="6">
                    <w:abstractNumId w:val="12"/>
                </w:num>
                <w:num w:numId="7">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="8">
                    <w:abstractNumId w:val="14"/>
                </w:num>
                <w:num w:numId="9">
                    <w:abstractNumId w:val="16"/>
                </w:num>
                <w:num w:numId="10">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="11">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="12">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="13">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="14">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="15">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="16">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="17">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="18">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="19">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="20">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="21">
                    <w:abstractNumId w:val="9"/>
                </w:num>
                <w:num w:numId="22">
                    <w:abstractNumId w:val="0"/>
                </w:num>
                <w:num w:numId="23">
                    <w:abstractNumId w:val="1"/>
                </w:num>
                <w:num w:numId="24">
                    <w:abstractNumId w:val="2"/>
                </w:num>
                <w:num w:numId="25">
                    <w:abstractNumId w:val="3"/>
                </w:num>
                <w:num w:numId="26">
                    <w:abstractNumId w:val="4"/>
                </w:num>
                <w:num w:numId="27">
                    <w:abstractNumId w:val="5"/>
                </w:num>
                <w:num w:numId="28">
                    <w:abstractNumId w:val="6"/>
                </w:num>
                <w:num w:numId="29">
                    <w:abstractNumId w:val="7"/>
                </w:num>
            </w:numbering>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.wordprocessingml.fontTable+xml" pkg:name="/word/fontTable.xml">
        <pkg:xmlData>
            <w:fonts xmlns:w="http://schemas.openxmlformats.org/wordprocessingml/2006/main">
                <w:font w:name="Symbol">
                    <w:panose1 w:val="05050102010706020507"/>
                    <w:charset w:val="02"/>
                    <w:family w:val="decorative"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="80000000" w:csb1="00000000" w:usb0="00000000" w:usb1="10000000" w:usb2="00000000" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Times New Roman">
                    <w:panose1 w:val="02020603050405020304"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002EFF" w:usb1="C000785B" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="OpenSymbol">
                    <w:altName w:val="Cambria"/>
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:notTrueType/>
                    <w:pitch w:val="default"/>
                </w:font>
                <w:font w:name="Liberation Serif">
                    <w:altName w:val="Times New Roman"/>
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:notTrueType/>
                    <w:pitch w:val="default"/>
                </w:font>
                <w:font w:name="SimSun">
                    <w:altName w:val="宋体"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="00040001" w:csb1="00000000" w:usb0="00000003" w:usb1="288F0000" w:usb2="00000016" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Lucida Sans">
                    <w:panose1 w:val="020B0602030504020204"/>
                    <w:charset w:val="4D"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="00000001" w:csb1="00000000" w:usb0="00000003" w:usb1="00000000" w:usb2="00000000" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Calibri">
                    <w:panose1 w:val="020F0502020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Courier New">
                    <w:panose1 w:val="02070309020205020404"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="modern"/>
                    <w:pitch w:val="fixed"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002AFF" w:usb1="C0007843" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Liberation Mono">
                    <w:altName w:val="Cambria"/>
                    <w:panose1 w:val="020B0604020202020204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="roman"/>
                    <w:notTrueType/>
                    <w:pitch w:val="default"/>
                </w:font>
                <w:font w:name="DengXian Light">
                    <w:altName w:val="等线 Light"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="0004000F" w:csb1="00000000" w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="Calibri Light">
                    <w:panose1 w:val="020F0302020204030204"/>
                    <w:charset w:val="00"/>
                    <w:family w:val="swiss"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="000001FF" w:csb1="00000000" w:usb0="E0002AFF" w:usb1="C000247B" w:usb2="00000009" w:usb3="00000000"/>
                </w:font>
                <w:font w:name="DengXian">
                    <w:altName w:val="等线"/>
                    <w:panose1 w:val="02010600030101010101"/>
                    <w:charset w:val="86"/>
                    <w:family w:val="auto"/>
                    <w:pitch w:val="variable"/>
                    <w:sig w:csb0="0004000F" w:csb1="00000000" w:usb0="A00002BF" w:usb1="38CF7CFA" w:usb2="00000016" w:usb3="00000000"/>
                </w:font>
            </w:fonts>
        </pkg:xmlData>
    </pkg:part>
    <pkg:part pkg:contentType="application/vnd.openxmlformats-officedocument.theme+xml" pkg:name="/word/theme/theme1.xml">
        <pkg:xmlData>
            <a:theme name="Office Theme" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                <a:themeElements>
                    <a:clrScheme name="Office">
                        <a:dk1>
                            <a:sysClr lastClr="000000" val="windowText"/>
                        </a:dk1>
                        <a:lt1>
                            <a:sysClr lastClr="FFFFFF" val="window"/>
                        </a:lt1>
                        <a:dk2>
                            <a:srgbClr val="44546A"/>
                        </a:dk2>
                        <a:lt2>
                            <a:srgbClr val="E7E6E6"/>
                        </a:lt2>
                        <a:accent1>
                            <a:srgbClr val="4472C4"/>
                        </a:accent1>
                        <a:accent2>
                            <a:srgbClr val="ED7D31"/>
                        </a:accent2>
                        <a:accent3>
                            <a:srgbClr val="A5A5A5"/>
                        </a:accent3>
                        <a:accent4>
                            <a:srgbClr val="FFC000"/>
                        </a:accent4>
                        <a:accent5>
                            <a:srgbClr val="5B9BD5"/>
                        </a:accent5>
                        <a:accent6>
                            <a:srgbClr val="70AD47"/>
                        </a:accent6>
                        <a:hlink>
                            <a:srgbClr val="0563C1"/>
                        </a:hlink>
                        <a:folHlink>
                            <a:srgbClr val="954F72"/>
                        </a:folHlink>
                    </a:clrScheme>
                    <a:fontScheme name="Office">
                        <a:majorFont>
                            <a:latin panose="020F0302020204030204" typeface="Calibri Light"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游ゴシック Light"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线 Light"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Times New Roman"/>
                            <a:font script="Hebr" typeface="Times New Roman"/>
                            <a:font script="Thai" typeface="Angsana New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="MoolBoran"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Times New Roman"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                            <a:font script="Armn" typeface="Arial"/>
                            <a:font script="Bugi" typeface="Leelawadee UI"/>
                            <a:font script="Bopo" typeface="Microsoft JhengHei"/>
                            <a:font script="Java" typeface="Javanese Text"/>
                            <a:font script="Lisu" typeface="Segoe UI"/>
                            <a:font script="Mymr" typeface="Myanmar Text"/>
                            <a:font script="Nkoo" typeface="Ebrima"/>
                            <a:font script="Olck" typeface="Nirmala UI"/>
                            <a:font script="Osma" typeface="Ebrima"/>
                            <a:font script="Phag" typeface="Phagspa"/>
                            <a:font script="Syrn" typeface="Estrangelo Edessa"/>
                            <a:font script="Syrj" typeface="Estrangelo Edessa"/>
                            <a:font script="Syre" typeface="Estrangelo Edessa"/>
                            <a:font script="Sora" typeface="Nirmala UI"/>
                            <a:font script="Tale" typeface="Microsoft Tai Le"/>
                            <a:font script="Talu" typeface="Microsoft New Tai Lue"/>
                            <a:font script="Tfng" typeface="Ebrima"/>
                        </a:majorFont>
                        <a:minorFont>
                            <a:latin panose="020F0502020204030204" typeface="Calibri"/>
                            <a:ea typeface=""/>
                            <a:cs typeface=""/>
                            <a:font script="Jpan" typeface="游明朝"/>
                            <a:font script="Hang" typeface="맑은 고딕"/>
                            <a:font script="Hans" typeface="等线"/>
                            <a:font script="Hant" typeface="新細明體"/>
                            <a:font script="Arab" typeface="Arial"/>
                            <a:font script="Hebr" typeface="Arial"/>
                            <a:font script="Thai" typeface="Cordia New"/>
                            <a:font script="Ethi" typeface="Nyala"/>
                            <a:font script="Beng" typeface="Vrinda"/>
                            <a:font script="Gujr" typeface="Shruti"/>
                            <a:font script="Khmr" typeface="DaunPenh"/>
                            <a:font script="Knda" typeface="Tunga"/>
                            <a:font script="Guru" typeface="Raavi"/>
                            <a:font script="Cans" typeface="Euphemia"/>
                            <a:font script="Cher" typeface="Plantagenet Cherokee"/>
                            <a:font script="Yiii" typeface="Microsoft Yi Baiti"/>
                            <a:font script="Tibt" typeface="Microsoft Himalaya"/>
                            <a:font script="Thaa" typeface="MV Boli"/>
                            <a:font script="Deva" typeface="Mangal"/>
                            <a:font script="Telu" typeface="Gautami"/>
                            <a:font script="Taml" typeface="Latha"/>
                            <a:font script="Syrc" typeface="Estrangelo Edessa"/>
                            <a:font script="Orya" typeface="Kalinga"/>
                            <a:font script="Mlym" typeface="Kartika"/>
                            <a:font script="Laoo" typeface="DokChampa"/>
                            <a:font script="Sinh" typeface="Iskoola Pota"/>
                            <a:font script="Mong" typeface="Mongolian Baiti"/>
                            <a:font script="Viet" typeface="Arial"/>
                            <a:font script="Uigh" typeface="Microsoft Uighur"/>
                            <a:font script="Geor" typeface="Sylfaen"/>
                            <a:font script="Armn" typeface="Arial"/>
                            <a:font script="Bugi" typeface="Leelawadee UI"/>
                            <a:font script="Bopo" typeface="Microsoft JhengHei"/>
                            <a:font script="Java" typeface="Javanese Text"/>
                            <a:font script="Lisu" typeface="Segoe UI"/>
                            <a:font script="Mymr" typeface="Myanmar Text"/>
                            <a:font script="Nkoo" typeface="Ebrima"/>
                            <a:font script="Olck" typeface="Nirmala UI"/>
                            <a:font script="Osma" typeface="Ebrima"/>
                            <a:font script="Phag" typeface="Phagspa"/>
                            <a:font script="Syrn" typeface="Estrangelo Edessa"/>
                            <a:font script="Syrj" typeface="Estrangelo Edessa"/>
                            <a:font script="Syre" typeface="Estrangelo Edessa"/>
                            <a:font script="Sora" typeface="Nirmala UI"/>
                            <a:font script="Tale" typeface="Microsoft Tai Le"/>
                            <a:font script="Talu" typeface="Microsoft New Tai Lue"/>
                            <a:font script="Tfng" typeface="Ebrima"/>
                        </a:minorFont>
                    </a:fontScheme>
                    <a:fmtScheme name="Office">
                        <a:fillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:gradFill rotWithShape="true">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="110000"/>
                                            <a:satMod val="105000"/>
                                            <a:tint val="67000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="103000"/>
                                            <a:tint val="73000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="105000"/>
                                            <a:satMod val="109000"/>
                                            <a:tint val="81000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="false"/>
                            </a:gradFill>
                            <a:gradFill rotWithShape="true">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="103000"/>
                                            <a:lumMod val="102000"/>
                                            <a:tint val="94000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:satMod val="110000"/>
                                            <a:lumMod val="100000"/>
                                            <a:shade val="100000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:lumMod val="99000"/>
                                            <a:satMod val="120000"/>
                                            <a:shade val="78000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="false"/>
                            </a:gradFill>
                        </a:fillStyleLst>
                        <a:lnStyleLst>
                            <a:ln algn="ctr" cap="flat" cmpd="sng" w="6350">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln algn="ctr" cap="flat" cmpd="sng" w="12700">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                            <a:ln algn="ctr" cap="flat" cmpd="sng" w="19050">
                                <a:solidFill>
                                    <a:schemeClr val="phClr"/>
                                </a:solidFill>
                                <a:prstDash val="solid"/>
                                <a:miter lim="800000"/>
                            </a:ln>
                        </a:lnStyleLst>
                        <a:effectStyleLst>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst/>
                            </a:effectStyle>
                            <a:effectStyle>
                                <a:effectLst>
                                    <a:outerShdw algn="ctr" blurRad="57150" dir="5400000" dist="19050" rotWithShape="false">
                                        <a:srgbClr val="000000">
                                            <a:alpha val="63000"/>
                                        </a:srgbClr>
                                    </a:outerShdw>
                                </a:effectLst>
                            </a:effectStyle>
                        </a:effectStyleLst>
                        <a:bgFillStyleLst>
                            <a:solidFill>
                                <a:schemeClr val="phClr"/>
                            </a:solidFill>
                            <a:solidFill>
                                <a:schemeClr val="phClr">
                                    <a:tint val="95000"/>
                                    <a:satMod val="170000"/>
                                </a:schemeClr>
                            </a:solidFill>
                            <a:gradFill rotWithShape="true">
                                <a:gsLst>
                                    <a:gs pos="0">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="93000"/>
                                            <a:satMod val="150000"/>
                                            <a:shade val="98000"/>
                                            <a:lumMod val="102000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="50000">
                                        <a:schemeClr val="phClr">
                                            <a:tint val="98000"/>
                                            <a:satMod val="130000"/>
                                            <a:shade val="90000"/>
                                            <a:lumMod val="103000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                    <a:gs pos="100000">
                                        <a:schemeClr val="phClr">
                                            <a:shade val="63000"/>
                                            <a:satMod val="120000"/>
                                        </a:schemeClr>
                                    </a:gs>
                                </a:gsLst>
                                <a:lin ang="5400000" scaled="false"/>
                            </a:gradFill>
                        </a:bgFillStyleLst>
                    </a:fmtScheme>
                </a:themeElements>
                <a:objectDefaults/>
                <a:extraClrSchemeLst/>
                <a:extLst>
                    <a:ext uri="{05A4C25C-085E-4340-85A3-A5531E510DB2}">
                        <thm15:themeFamily id="{62F939B6-93AF-4DB8-9C6B-D6C7DFDC589F}" name="Office Theme" vid="{4A3C46E8-61CC-4603-A589-7422A47A8E4A}" xmlns:thm15="http://schemas.microsoft.com/office/thememl/2012/main"/>
                    </a:ext>
                </a:extLst>
            </a:theme>
        </pkg:xmlData>
    </pkg:part>
</pkg:package>
````````````````````````````````


```````````````````````````````` example Headings: 3
## Heading
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 4
### Heading
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 5
#### Heading
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 6
##### Heading
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 7
###### Heading
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 8
Heading
=======
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 9
Heading
-------
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
        </w:pPr>
        <w:r>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


