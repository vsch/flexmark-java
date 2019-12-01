---
title: Pegdown with Extensions Compatibility Spec
author: Vladimir Schneider
version: 0.2
date: '2017-01-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Stack Overflow in parser

```````````````````````````````` example(Stack Overflow in parser: 1) options(IGNORE)
[other](#)

[other](other.html)
[Sample Doc](sample-doc.html)

![Screen Shot Project View](../resources/images/ScreenShot_Project_View.png)

![](https://github.com/vsch/MarkdownTest/raw/master/dummy.png)

<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta name=Title content="">
<meta name=Keywords content="">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 14">
<meta name=Originator content="Microsoft Word 14">
<link rel=File-List
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0clip_filelist.xml">
<!--[if gte mso 9]><xml>
 <o:OfficeDocumentSettings>
  <o:AllowPNG/>
  <o:PixelsPerInch>96</o:PixelsPerInch>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<link rel=themeData
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0clip_themedata.xml">
<!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Normal</w:View>
  <w:Zoom>0</w:Zoom>
  <w:TrackMoves/>
  <w:TrackFormatting/>
  <w:PunctuationKerning/>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotPromoteQF/>
  <w:LidThemeOther>DE</w:LidThemeOther>
  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>
  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:SplitPgBreakAndParaMark/>
   <w:EnableOpenTypeKerning/>
   <w:DontFlipMirrorIndents/>
   <w:OverrideTableStyleHps/>
  </w:Compatibility>
  <m:mathPr>
   <m:mathFont m:val="Cambria Math"/>
   <m:brkBin m:val="before"/>
   <m:brkBinSub m:val="&#45;-"/>
   <m:smallFrac m:val="off"/>
   <m:dispDef/>
   <m:lMargin m:val="0"/>
   <m:rMargin m:val="0"/>
   <m:defJc m:val="centerGroup"/>
   <m:wrapIndent m:val="1440"/>
   <m:intLim m:val="subSup"/>
   <m:naryLim m:val="undOvr"/>
  </m:mathPr></w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" DefUnhideWhenUsed="true"
  DefSemiHidden="true" DefQFormat="false" DefPriority="99"
  LatentStyleCount="276">
  <w:LsdException Locked="false" Priority="0" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Normal"/>
  <w:LsdException Locked="false" Priority="9" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="heading 1"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 2"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 3"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 4"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 5"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 6"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 7"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 8"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 9"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 1"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 2"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 3"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 4"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 5"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 6"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 7"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 8"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 9"/>
  <w:LsdException Locked="false" Priority="35" QFormat="true" Name="caption"/>
  <w:LsdException Locked="false" Priority="10" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Title"/>
  <w:LsdException Locked="false" Priority="1" Name="Default Paragraph Font"/>
  <w:LsdException Locked="false" Priority="11" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtitle"/>
  <w:LsdException Locked="false" Priority="22" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Strong"/>
  <w:LsdException Locked="false" Priority="20" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Emphasis"/>
  <w:LsdException Locked="false" Priority="59" SemiHidden="false"
   UnhideWhenUsed="false" Name="Table Grid"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Placeholder Text"/>
  <w:LsdException Locked="false" Priority="1" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="No Spacing"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 1"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 1"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Revision"/>
  <w:LsdException Locked="false" Priority="34" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="List Paragraph"/>
  <w:LsdException Locked="false" Priority="29" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Quote"/>
  <w:LsdException Locked="false" Priority="30" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Quote"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 1"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 1"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 1"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 2"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 2"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 2"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 2"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 3"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 3"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 3"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 4"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 4"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 4"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 4"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 5"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 5"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 5"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 5"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 6"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 6"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 6"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 6"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="19" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Emphasis"/>
  <w:LsdException Locked="false" Priority="21" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Emphasis"/>
  <w:LsdException Locked="false" Priority="31" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Reference"/>
  <w:LsdException Locked="false" Priority="32" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Reference"/>
  <w:LsdException Locked="false" Priority="33" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Book Title"/>
  <w:LsdException Locked="false" Priority="37" Name="Bibliography"/>
  <w:LsdException Locked="false" Priority="39" QFormat="true" Name="TOC Heading"/>
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
@font-face
→{font-family:"Courier New";
→panose-1:2 7 3 9 2 2 5 2 4 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:3 0 0 0 1 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:Calibri;
→panose-1:2 15 5 2 2 2 4 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:3 0 0 0 1 0;}
 /* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
→{mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-parent:"";
→margin:0cm;
→margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
.MsoChpDefault
→{mso-style-type:export-only;
→mso-default-props:yes;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
@page WordSection1
→{size:595.0pt 842.0pt;
→margin:70.85pt 70.85pt 2.0cm 70.85pt;
→mso-header-margin:35.4pt;
→mso-footer-margin:35.4pt;
→mso-paper-source:0;}
div.WordSection1
→{page:WordSection1;}
 /* List Definitions */
@list l0
→{mso-list-id:2090998432;
→mso-list-type:hybrid;
→mso-list-template-ids:613421974 67567617 67567619 67567621 67567617 67567619 67567621 67567617 67567619 67567621;}
@list l0:level1
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:18.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level2
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:54.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";
→mso-bidi-font-family:"Courier New";}
@list l0:level3
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:90.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level4
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:126.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level5
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:162.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";
→mso-bidi-font-family:"Courier New";}
@list l0:level6
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:198.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level7
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:234.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level8
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:270.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";
→mso-bidi-font-family:"Courier New";}
@list l0:level9
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:306.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
ol
→{margin-bottom:0cm;}
ul
→{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
table.MsoNormalTable
→{mso-style-name:"Table Normal";
→mso-tstyle-rowband-size:0;
→mso-tstyle-colband-size:0;
→mso-style-noshow:yes;
→mso-style-priority:99;
→mso-style-parent:"";
→mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
→mso-para-margin:0cm;
→mso-para-margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-ansi-language:DE;}
</style>
<![endif]-->
</head>

<body bgcolor=white lang=EN-GB style='tab-interval:36.0pt'>
<!--StartFragment-->

<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Equipment<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Chemicals<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Consumables<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Enzymes<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>GMO<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Antibodies<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>DNA Constructs<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>RNA Constructs<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Vectors<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;
text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Oligos<o:p></o:p></span></p>

<!--EndFragment-->
</body>

</html>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta name=Title content="">
<meta name=Keywords content="">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 14">
<meta name=Originator content="Microsoft Word 14">
<link rel=File-List
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_filelist.xml">
<!--[if gte mso 9]><xml>
 <o:OfficeDocumentSettings>
  <o:AllowPNG/>
  <o:PixelsPerInch>96</o:PixelsPerInch>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<link rel=themeData
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_themedata.xml">
<!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Normal</w:View>
  <w:Zoom>0</w:Zoom>
  <w:TrackMoves/>
  <w:TrackFormatting/>
  <w:PunctuationKerning/>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotPromoteQF/>
  <w:LidThemeOther>DE</w:LidThemeOther>
  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>
  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:SplitPgBreakAndParaMark/>
   <w:EnableOpenTypeKerning/>
   <w:DontFlipMirrorIndents/>
   <w:OverrideTableStyleHps/>
  </w:Compatibility>
  <m:mathPr>
   <m:mathFont m:val="Cambria Math"/>
   <m:brkBin m:val="before"/>
   <m:brkBinSub m:val="&#45;-"/>
   <m:smallFrac m:val="off"/>
   <m:dispDef/>
   <m:lMargin m:val="0"/>
   <m:rMargin m:val="0"/>
   <m:defJc m:val="centerGroup"/>
   <m:wrapIndent m:val="1440"/>
   <m:intLim m:val="subSup"/>
   <m:naryLim m:val="undOvr"/>
  </m:mathPr></w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" DefUnhideWhenUsed="true"
  DefSemiHidden="true" DefQFormat="false" DefPriority="99"
  LatentStyleCount="276">
  <w:LsdException Locked="false" Priority="0" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Normal"/>
  <w:LsdException Locked="false" Priority="9" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="heading 1"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 2"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 3"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 4"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 5"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 6"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 7"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 8"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 9"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 1"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 2"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 3"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 4"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 5"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 6"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 7"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 8"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 9"/>
  <w:LsdException Locked="false" Priority="35" QFormat="true" Name="caption"/>
  <w:LsdException Locked="false" Priority="10" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Title"/>
  <w:LsdException Locked="false" Priority="1" Name="Default Paragraph Font"/>
  <w:LsdException Locked="false" Priority="11" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtitle"/>
  <w:LsdException Locked="false" Priority="22" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Strong"/>
  <w:LsdException Locked="false" Priority="20" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Emphasis"/>
  <w:LsdException Locked="false" Priority="59" SemiHidden="false"
   UnhideWhenUsed="false" Name="Table Grid"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Placeholder Text"/>
  <w:LsdException Locked="false" Priority="1" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="No Spacing"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 1"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 1"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Revision"/>
  <w:LsdException Locked="false" Priority="34" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="List Paragraph"/>
  <w:LsdException Locked="false" Priority="29" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Quote"/>
  <w:LsdException Locked="false" Priority="30" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Quote"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 1"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 1"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 1"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 2"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 2"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 2"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 2"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 3"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 3"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 3"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 4"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 4"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 4"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 4"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 5"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 5"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 5"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 5"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 6"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 6"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 6"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 6"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="19" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Emphasis"/>
  <w:LsdException Locked="false" Priority="21" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Emphasis"/>
  <w:LsdException Locked="false" Priority="31" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Reference"/>
  <w:LsdException Locked="false" Priority="32" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Reference"/>
  <w:LsdException Locked="false" Priority="33" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Book Title"/>
  <w:LsdException Locked="false" Priority="37" Name="Bibliography"/>
  <w:LsdException Locked="false" Priority="39" QFormat="true" Name="TOC Heading"/>
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
@font-face
→{font-family:"Courier New";
→panose-1:2 7 3 9 2 2 5 2 4 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-536859905 -1073711037 9 0 511 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:"Cambria Math";
→panose-1:2 4 5 3 5 4 6 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:3 0 0 0 1 0;}
@font-face
→{font-family:Calibri;
→panose-1:2 15 5 2 2 2 4 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-520092929 1073786111 9 0 415 0;}
 /* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
→{mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-parent:"";
→margin:0cm;
→margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
.MsoChpDefault
→{mso-style-type:export-only;
→mso-default-props:yes;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
@page WordSection1
→{size:595.0pt 842.0pt;
→margin:70.85pt 70.85pt 2.0cm 70.85pt;
→mso-header-margin:35.4pt;
→mso-footer-margin:35.4pt;
→mso-paper-source:0;}
div.WordSection1
→{page:WordSection1;}
 /* List Definitions */
@list l0
→{mso-list-id:1550844186;
→mso-list-type:hybrid;
→mso-list-template-ids:-883231358 67698703 67567619 67567621 67567617 67567619 67567621 67567617 67567619 67567621;}
@list l0:level1
→{mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:18.0pt;
→text-indent:-18.0pt;}
@list l0:level2
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:54.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level3
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:90.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level4
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:126.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level5
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:162.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level6
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:198.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level7
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:234.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level8
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:270.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level9
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:306.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
ol
→{margin-bottom:0cm;}
ul
→{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
table.MsoNormalTable
→{mso-style-name:"Table Normal";
→mso-tstyle-rowband-size:0;
→mso-tstyle-colband-size:0;
→mso-style-noshow:yes;
→mso-style-priority:99;
→mso-style-parent:"";
→mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
→mso-para-margin:0cm;
→mso-para-margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-ansi-language:DE;}
</style>
<![endif]-->
</head>

<body bgcolor=white lang=EN-GB style='tab-interval:36.0pt'>
<!--StartFragment-->

<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>1.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Equipment<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>2.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Chemicals<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>3.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Consumables<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>4.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Enzymes<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>5.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>GMO<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>6.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Antibodies<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>7.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>DNA Constructs<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>8.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>RNA Constructs<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>9.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Vectors<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;
text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>10.<span
style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Oligos<o:p></o:p></span></p>

<!--EndFragment-->
</body>

</html>


<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">

<head>
<meta name=Title content="">
<meta name=Keywords content="">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 14">
<meta name=Originator content="Microsoft Word 14">
<link rel=File-List
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_filelist.xml">
<!--[if gte mso 9]><xml>
 <o:OfficeDocumentSettings>
  <o:AllowPNG/>
  <o:PixelsPerInch>96</o:PixelsPerInch>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<link rel=themeData
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_themedata.xml">
<!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Normal</w:View>
  <w:Zoom>0</w:Zoom>
  <w:TrackMoves/>
  <w:TrackFormatting/>
  <w:PunctuationKerning/>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotPromoteQF/>
  <w:LidThemeOther>DE</w:LidThemeOther>
  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>
  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:SplitPgBreakAndParaMark/>
   <w:EnableOpenTypeKerning/>
   <w:DontFlipMirrorIndents/>
   <w:OverrideTableStyleHps/>
  </w:Compatibility>
  <m:mathPr>
   <m:mathFont m:val="Cambria Math"/>
   <m:brkBin m:val="before"/>
   <m:brkBinSub m:val="&#45;-"/>
   <m:smallFrac m:val="off"/>
   <m:dispDef/>
   <m:lMargin m:val="0"/>
   <m:rMargin m:val="0"/>
   <m:defJc m:val="centerGroup"/>
   <m:wrapIndent m:val="1440"/>
   <m:intLim m:val="subSup"/>
   <m:naryLim m:val="undOvr"/>
  </m:mathPr></w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" DefUnhideWhenUsed="true"
  DefSemiHidden="true" DefQFormat="false" DefPriority="99"
  LatentStyleCount="276">
  <w:LsdException Locked="false" Priority="0" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Normal"/>
  <w:LsdException Locked="false" Priority="9" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="heading 1"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 2"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 3"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 4"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 5"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 6"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 7"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 8"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 9"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 1"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 2"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 3"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 4"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 5"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 6"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 7"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 8"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 9"/>
  <w:LsdException Locked="false" Priority="35" QFormat="true" Name="caption"/>
  <w:LsdException Locked="false" Priority="10" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Title"/>
  <w:LsdException Locked="false" Priority="1" Name="Default Paragraph Font"/>
  <w:LsdException Locked="false" Priority="11" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtitle"/>
  <w:LsdException Locked="false" Priority="22" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Strong"/>
  <w:LsdException Locked="false" Priority="20" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Emphasis"/>
  <w:LsdException Locked="false" Priority="59" SemiHidden="false"
   UnhideWhenUsed="false" Name="Table Grid"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Placeholder Text"/>
  <w:LsdException Locked="false" Priority="1" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="No Spacing"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 1"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 1"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Revision"/>
  <w:LsdException Locked="false" Priority="34" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="List Paragraph"/>
  <w:LsdException Locked="false" Priority="29" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Quote"/>
  <w:LsdException Locked="false" Priority="30" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Quote"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 1"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 1"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 1"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 2"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 2"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 2"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 2"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 3"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 3"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 3"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 4"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 4"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 4"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 4"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 5"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 5"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 5"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 5"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 6"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 6"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 6"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 6"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="19" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Emphasis"/>
  <w:LsdException Locked="false" Priority="21" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Emphasis"/>
  <w:LsdException Locked="false" Priority="31" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Reference"/>
  <w:LsdException Locked="false" Priority="32" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Reference"/>
  <w:LsdException Locked="false" Priority="33" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Book Title"/>
  <w:LsdException Locked="false" Priority="37" Name="Bibliography"/>
  <w:LsdException Locked="false" Priority="39" QFormat="true" Name="TOC Heading"/>
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
@font-face
→{font-family:"Courier New";
→panose-1:2 7 3 9 2 2 5 2 4 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-536859905 -1073711037 9 0 511 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:"Cambria Math";
→panose-1:2 4 5 3 5 4 6 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-536870145 1107305727 0 0 415 0;}
@font-face
→{font-family:Calibri;
→panose-1:2 15 5 2 2 2 4 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-520092929 1073786111 9 0 415 0;}
 /* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
→{mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-parent:"";
→margin:0cm;
→margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
.MsoChpDefault
→{mso-style-type:export-only;
→mso-default-props:yes;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
@page WordSection1
→{size:595.0pt 842.0pt;
→margin:70.85pt 70.85pt 2.0cm 70.85pt;
→mso-header-margin:35.4pt;
→mso-footer-margin:35.4pt;
→mso-paper-source:0;}
div.WordSection1
→{page:WordSection1;}
 /* List Definitions */
@list l0
→{mso-list-id:321811675;
→mso-list-type:hybrid;
→mso-list-template-ids:1820389242 67698705 67567619 67567621 67567617 67567619 67567621 67567617 67567619 67567621;}
@list l0:level1
→{mso-level-text:"%1\)";
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:18.0pt;
→text-indent:-18.0pt;}
@list l0:level2
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:54.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level3
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:90.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level4
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:126.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level5
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:162.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level6
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:198.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level7
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:234.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level8
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:270.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level9
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:306.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
ol
→{margin-bottom:0cm;}
ul
→{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
table.MsoNormalTable
→{mso-style-name:"Table Normal";
→mso-tstyle-rowband-size:0;
→mso-tstyle-colband-size:0;
→mso-style-noshow:yes;
→mso-style-priority:99;
→mso-style-parent:"";
→mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
→mso-para-margin:0cm;
→mso-para-margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-ansi-language:DE;}
</style>
<![endif]-->
</head>

<body bgcolor=white lang=EN-GB style='tab-interval:36.0pt'>
<!--StartFragment-->

<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>1)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Equipment<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>2)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Chemicals<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>3)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Consumables<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>4)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Enzymes<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>5)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>GMO<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>6)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Antibodies<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>7)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>DNA Constructs<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>8)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>RNA Constructs<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>9)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Vectors<o:p></o:p></span></p>

<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;
text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>10)<span
style='font:7.0pt "Times New Roman"'> </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Oligos<o:p></o:p></span></p>

<!--EndFragment-->
</body>

</html>
.
<p><a href="#">other</a></p>
<p><a href="other.html">other</a>
<a href="sample-doc.html">Sample Doc</a></p>
<p><img src="../resources/images/ScreenShot_Project_View.png" alt="Screen Shot Project View" /></p>
<p><img src="https://github.com/vsch/MarkdownTest/raw/master/dummy.png" alt="" /></p>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta name=Title content="">
<meta name=Keywords content="">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 14">
<meta name=Originator content="Microsoft Word 14">
<link rel=File-List
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0clip_filelist.xml">
<!--[if gte mso 9]><xml>
 <o:OfficeDocumentSettings>
  <o:AllowPNG/>
  <o:PixelsPerInch>96</o:PixelsPerInch>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<link rel=themeData
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0clip_themedata.xml">
<!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Normal</w:View>
  <w:Zoom>0</w:Zoom>
  <w:TrackMoves/>
  <w:TrackFormatting/>
  <w:PunctuationKerning/>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotPromoteQF/>
  <w:LidThemeOther>DE</w:LidThemeOther>
  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>
  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:SplitPgBreakAndParaMark/>
   <w:EnableOpenTypeKerning/>
   <w:DontFlipMirrorIndents/>
   <w:OverrideTableStyleHps/>
  </w:Compatibility>
  <m:mathPr>
   <m:mathFont m:val="Cambria Math"/>
   <m:brkBin m:val="before"/>
   <m:brkBinSub m:val="&#45;-"/>
   <m:smallFrac m:val="off"/>
   <m:dispDef/>
   <m:lMargin m:val="0"/>
   <m:rMargin m:val="0"/>
   <m:defJc m:val="centerGroup"/>
   <m:wrapIndent m:val="1440"/>
   <m:intLim m:val="subSup"/>
   <m:naryLim m:val="undOvr"/>
  </m:mathPr></w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" DefUnhideWhenUsed="true"
  DefSemiHidden="true" DefQFormat="false" DefPriority="99"
  LatentStyleCount="276">
  <w:LsdException Locked="false" Priority="0" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Normal"/>
  <w:LsdException Locked="false" Priority="9" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="heading 1"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 2"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 3"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 4"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 5"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 6"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 7"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 8"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 9"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 1"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 2"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 3"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 4"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 5"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 6"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 7"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 8"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 9"/>
  <w:LsdException Locked="false" Priority="35" QFormat="true" Name="caption"/>
  <w:LsdException Locked="false" Priority="10" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Title"/>
  <w:LsdException Locked="false" Priority="1" Name="Default Paragraph Font"/>
  <w:LsdException Locked="false" Priority="11" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtitle"/>
  <w:LsdException Locked="false" Priority="22" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Strong"/>
  <w:LsdException Locked="false" Priority="20" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Emphasis"/>
  <w:LsdException Locked="false" Priority="59" SemiHidden="false"
   UnhideWhenUsed="false" Name="Table Grid"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Placeholder Text"/>
  <w:LsdException Locked="false" Priority="1" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="No Spacing"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 1"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 1"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Revision"/>
  <w:LsdException Locked="false" Priority="34" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="List Paragraph"/>
  <w:LsdException Locked="false" Priority="29" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Quote"/>
  <w:LsdException Locked="false" Priority="30" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Quote"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 1"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 1"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 1"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 2"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 2"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 2"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 2"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 3"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 3"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 3"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 4"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 4"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 4"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 4"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 5"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 5"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 5"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 5"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 6"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 6"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 6"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 6"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="19" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Emphasis"/>
  <w:LsdException Locked="false" Priority="21" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Emphasis"/>
  <w:LsdException Locked="false" Priority="31" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Reference"/>
  <w:LsdException Locked="false" Priority="32" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Reference"/>
  <w:LsdException Locked="false" Priority="33" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Book Title"/>
  <w:LsdException Locked="false" Priority="37" Name="Bibliography"/>
  <w:LsdException Locked="false" Priority="39" QFormat="true" Name="TOC Heading"/>
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
@font-face
→{font-family:"Courier New";
→panose-1:2 7 3 9 2 2 5 2 4 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:3 0 0 0 1 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:Calibri;
→panose-1:2 15 5 2 2 2 4 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:3 0 0 0 1 0;}
 /* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
→{mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-parent:"";
→margin:0cm;
→margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
.MsoChpDefault
→{mso-style-type:export-only;
→mso-default-props:yes;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
@page WordSection1
→{size:595.0pt 842.0pt;
→margin:70.85pt 70.85pt 2.0cm 70.85pt;
→mso-header-margin:35.4pt;
→mso-footer-margin:35.4pt;
→mso-paper-source:0;}
div.WordSection1
→{page:WordSection1;}
 /* List Definitions */
@list l0
→{mso-list-id:2090998432;
→mso-list-type:hybrid;
→mso-list-template-ids:613421974 67567617 67567619 67567621 67567617 67567619 67567621 67567617 67567619 67567621;}
@list l0:level1
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:18.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level2
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:54.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";
→mso-bidi-font-family:"Courier New";}
@list l0:level3
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:90.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level4
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:126.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level5
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:162.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";
→mso-bidi-font-family:"Courier New";}
@list l0:level6
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:198.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level7
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:234.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level8
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:270.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";
→mso-bidi-font-family:"Courier New";}
@list l0:level9
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:306.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
ol
→{margin-bottom:0cm;}
ul
→{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
table.MsoNormalTable
→{mso-style-name:"Table Normal";
→mso-tstyle-rowband-size:0;
→mso-tstyle-colband-size:0;
→mso-style-noshow:yes;
→mso-style-priority:99;
→mso-style-parent:"";
→mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
→mso-para-margin:0cm;
→mso-para-margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-ansi-language:DE;}
</style>
<![endif]-->
</head>
<body bgcolor=white lang=EN-GB style='tab-interval:36.0pt'>
<!--StartFragment-->
<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Equipment<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Chemicals<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Consumables<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Enzymes<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>GMO<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Antibodies<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>DNA Constructs<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>RNA Constructs<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Vectors<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;
text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='font-family:Symbol;mso-fareast-font-family:Symbol;mso-bidi-font-family:
Symbol;mso-ansi-language:EN-US'><span style='mso-list:Ignore'>·<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Oligos<o:p></o:p></span></p>
<!--EndFragment-->
</body>
</html>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta name=Title content="">
<meta name=Keywords content="">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 14">
<meta name=Originator content="Microsoft Word 14">
<link rel=File-List
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_filelist.xml">
<!--[if gte mso 9]><xml>
 <o:OfficeDocumentSettings>
  <o:AllowPNG/>
  <o:PixelsPerInch>96</o:PixelsPerInch>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<link rel=themeData
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_themedata.xml">
<!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Normal</w:View>
  <w:Zoom>0</w:Zoom>
  <w:TrackMoves/>
  <w:TrackFormatting/>
  <w:PunctuationKerning/>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotPromoteQF/>
  <w:LidThemeOther>DE</w:LidThemeOther>
  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>
  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:SplitPgBreakAndParaMark/>
   <w:EnableOpenTypeKerning/>
   <w:DontFlipMirrorIndents/>
   <w:OverrideTableStyleHps/>
  </w:Compatibility>
  <m:mathPr>
   <m:mathFont m:val="Cambria Math"/>
   <m:brkBin m:val="before"/>
   <m:brkBinSub m:val="&#45;-"/>
   <m:smallFrac m:val="off"/>
   <m:dispDef/>
   <m:lMargin m:val="0"/>
   <m:rMargin m:val="0"/>
   <m:defJc m:val="centerGroup"/>
   <m:wrapIndent m:val="1440"/>
   <m:intLim m:val="subSup"/>
   <m:naryLim m:val="undOvr"/>
  </m:mathPr></w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" DefUnhideWhenUsed="true"
  DefSemiHidden="true" DefQFormat="false" DefPriority="99"
  LatentStyleCount="276">
  <w:LsdException Locked="false" Priority="0" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Normal"/>
  <w:LsdException Locked="false" Priority="9" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="heading 1"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 2"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 3"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 4"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 5"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 6"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 7"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 8"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 9"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 1"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 2"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 3"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 4"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 5"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 6"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 7"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 8"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 9"/>
  <w:LsdException Locked="false" Priority="35" QFormat="true" Name="caption"/>
  <w:LsdException Locked="false" Priority="10" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Title"/>
  <w:LsdException Locked="false" Priority="1" Name="Default Paragraph Font"/>
  <w:LsdException Locked="false" Priority="11" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtitle"/>
  <w:LsdException Locked="false" Priority="22" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Strong"/>
  <w:LsdException Locked="false" Priority="20" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Emphasis"/>
  <w:LsdException Locked="false" Priority="59" SemiHidden="false"
   UnhideWhenUsed="false" Name="Table Grid"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Placeholder Text"/>
  <w:LsdException Locked="false" Priority="1" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="No Spacing"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 1"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 1"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Revision"/>
  <w:LsdException Locked="false" Priority="34" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="List Paragraph"/>
  <w:LsdException Locked="false" Priority="29" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Quote"/>
  <w:LsdException Locked="false" Priority="30" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Quote"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 1"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 1"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 1"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 2"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 2"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 2"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 2"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 3"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 3"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 3"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 4"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 4"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 4"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 4"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 5"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 5"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 5"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 5"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 6"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 6"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 6"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 6"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="19" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Emphasis"/>
  <w:LsdException Locked="false" Priority="21" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Emphasis"/>
  <w:LsdException Locked="false" Priority="31" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Reference"/>
  <w:LsdException Locked="false" Priority="32" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Reference"/>
  <w:LsdException Locked="false" Priority="33" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Book Title"/>
  <w:LsdException Locked="false" Priority="37" Name="Bibliography"/>
  <w:LsdException Locked="false" Priority="39" QFormat="true" Name="TOC Heading"/>
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
@font-face
→{font-family:"Courier New";
→panose-1:2 7 3 9 2 2 5 2 4 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-536859905 -1073711037 9 0 511 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:"Cambria Math";
→panose-1:2 4 5 3 5 4 6 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:3 0 0 0 1 0;}
@font-face
→{font-family:Calibri;
→panose-1:2 15 5 2 2 2 4 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-520092929 1073786111 9 0 415 0;}
 /* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
→{mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-parent:"";
→margin:0cm;
→margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
.MsoChpDefault
→{mso-style-type:export-only;
→mso-default-props:yes;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
@page WordSection1
→{size:595.0pt 842.0pt;
→margin:70.85pt 70.85pt 2.0cm 70.85pt;
→mso-header-margin:35.4pt;
→mso-footer-margin:35.4pt;
→mso-paper-source:0;}
div.WordSection1
→{page:WordSection1;}
 /* List Definitions */
@list l0
→{mso-list-id:1550844186;
→mso-list-type:hybrid;
→mso-list-template-ids:-883231358 67698703 67567619 67567621 67567617 67567619 67567621 67567617 67567619 67567621;}
@list l0:level1
→{mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:18.0pt;
→text-indent:-18.0pt;}
@list l0:level2
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:54.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level3
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:90.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level4
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:126.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level5
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:162.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level6
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:198.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level7
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:234.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level8
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:270.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level9
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:306.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
ol
→{margin-bottom:0cm;}
ul
→{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
table.MsoNormalTable
→{mso-style-name:"Table Normal";
→mso-tstyle-rowband-size:0;
→mso-tstyle-colband-size:0;
→mso-style-noshow:yes;
→mso-style-priority:99;
→mso-style-parent:"";
→mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
→mso-para-margin:0cm;
→mso-para-margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-ansi-language:DE;}
</style>
<![endif]-->
</head>
<body bgcolor=white lang=EN-GB style='tab-interval:36.0pt'>
<!--StartFragment-->
<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>1.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Equipment<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>2.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Chemicals<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>3.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Consumables<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>4.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Enzymes<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>5.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>GMO<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>6.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Antibodies<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>7.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>DNA Constructs<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>8.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>RNA Constructs<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>9.<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Vectors<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;
text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>10.<span
style='font:7.0pt "Times New Roman"'>&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Oligos<o:p></o:p></span></p>
<!--EndFragment-->
</body>
</html>
<html xmlns:o="urn:schemas-microsoft-com:office:office"
xmlns:w="urn:schemas-microsoft-com:office:word"
xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
xmlns="http://www.w3.org/TR/REC-html40">
<head>
<meta name=Title content="">
<meta name=Keywords content="">
<meta http-equiv=Content-Type content="text/html; charset=utf-8">
<meta name=ProgId content=Word.Document>
<meta name=Generator content="Microsoft Word 14">
<meta name=Originator content="Microsoft Word 14">
<link rel=File-List
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_filelist.xml">
<!--[if gte mso 9]><xml>
 <o:OfficeDocumentSettings>
  <o:AllowPNG/>
  <o:PixelsPerInch>96</o:PixelsPerInch>
 </o:OfficeDocumentSettings>
</xml><![endif]-->
<link rel=themeData
href="file://localhost/private/var/folders/bs/4ktxsyn54pj5mc0l8b43h8vc0000gp/T/TemporaryItems/msoclip/0/clip_themedata.xml">
<!--[if gte mso 9]><xml>
 <w:WordDocument>
  <w:View>Normal</w:View>
  <w:Zoom>0</w:Zoom>
  <w:TrackMoves/>
  <w:TrackFormatting/>
  <w:PunctuationKerning/>
  <w:ValidateAgainstSchemas/>
  <w:SaveIfXMLInvalid>false</w:SaveIfXMLInvalid>
  <w:IgnoreMixedContent>false</w:IgnoreMixedContent>
  <w:AlwaysShowPlaceholderText>false</w:AlwaysShowPlaceholderText>
  <w:DoNotPromoteQF/>
  <w:LidThemeOther>DE</w:LidThemeOther>
  <w:LidThemeAsian>X-NONE</w:LidThemeAsian>
  <w:LidThemeComplexScript>X-NONE</w:LidThemeComplexScript>
  <w:Compatibility>
   <w:BreakWrappedTables/>
   <w:SnapToGridInCell/>
   <w:WrapTextWithPunct/>
   <w:UseAsianBreakRules/>
   <w:DontGrowAutofit/>
   <w:SplitPgBreakAndParaMark/>
   <w:EnableOpenTypeKerning/>
   <w:DontFlipMirrorIndents/>
   <w:OverrideTableStyleHps/>
  </w:Compatibility>
  <m:mathPr>
   <m:mathFont m:val="Cambria Math"/>
   <m:brkBin m:val="before"/>
   <m:brkBinSub m:val="&#45;-"/>
   <m:smallFrac m:val="off"/>
   <m:dispDef/>
   <m:lMargin m:val="0"/>
   <m:rMargin m:val="0"/>
   <m:defJc m:val="centerGroup"/>
   <m:wrapIndent m:val="1440"/>
   <m:intLim m:val="subSup"/>
   <m:naryLim m:val="undOvr"/>
  </m:mathPr></w:WordDocument>
</xml><![endif]--><!--[if gte mso 9]><xml>
 <w:LatentStyles DefLockedState="false" DefUnhideWhenUsed="true"
  DefSemiHidden="true" DefQFormat="false" DefPriority="99"
  LatentStyleCount="276">
  <w:LsdException Locked="false" Priority="0" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Normal"/>
  <w:LsdException Locked="false" Priority="9" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="heading 1"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 2"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 3"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 4"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 5"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 6"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 7"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 8"/>
  <w:LsdException Locked="false" Priority="9" QFormat="true" Name="heading 9"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 1"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 2"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 3"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 4"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 5"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 6"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 7"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 8"/>
  <w:LsdException Locked="false" Priority="39" Name="toc 9"/>
  <w:LsdException Locked="false" Priority="35" QFormat="true" Name="caption"/>
  <w:LsdException Locked="false" Priority="10" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Title"/>
  <w:LsdException Locked="false" Priority="1" Name="Default Paragraph Font"/>
  <w:LsdException Locked="false" Priority="11" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtitle"/>
  <w:LsdException Locked="false" Priority="22" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Strong"/>
  <w:LsdException Locked="false" Priority="20" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Emphasis"/>
  <w:LsdException Locked="false" Priority="59" SemiHidden="false"
   UnhideWhenUsed="false" Name="Table Grid"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Placeholder Text"/>
  <w:LsdException Locked="false" Priority="1" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="No Spacing"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 1"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 1"/>
  <w:LsdException Locked="false" UnhideWhenUsed="false" Name="Revision"/>
  <w:LsdException Locked="false" Priority="34" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="List Paragraph"/>
  <w:LsdException Locked="false" Priority="29" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Quote"/>
  <w:LsdException Locked="false" Priority="30" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Quote"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 1"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 1"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 1"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 1"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 1"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 1"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 1"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 2"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 2"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 2"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 2"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 2"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 2"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 2"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 2"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 3"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 3"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 3"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 3"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 3"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 3"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 3"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 3"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 4"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 4"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 4"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 4"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 4"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 4"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 4"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 4"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 5"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 5"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 5"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 5"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 5"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 5"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 5"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 5"/>
  <w:LsdException Locked="false" Priority="60" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="61" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light List Accent 6"/>
  <w:LsdException Locked="false" Priority="62" SemiHidden="false"
   UnhideWhenUsed="false" Name="Light Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="63" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="64" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Shading 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="65" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="66" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium List 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="67" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 1 Accent 6"/>
  <w:LsdException Locked="false" Priority="68" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 2 Accent 6"/>
  <w:LsdException Locked="false" Priority="69" SemiHidden="false"
   UnhideWhenUsed="false" Name="Medium Grid 3 Accent 6"/>
  <w:LsdException Locked="false" Priority="70" SemiHidden="false"
   UnhideWhenUsed="false" Name="Dark List Accent 6"/>
  <w:LsdException Locked="false" Priority="71" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Shading Accent 6"/>
  <w:LsdException Locked="false" Priority="72" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful List Accent 6"/>
  <w:LsdException Locked="false" Priority="73" SemiHidden="false"
   UnhideWhenUsed="false" Name="Colorful Grid Accent 6"/>
  <w:LsdException Locked="false" Priority="19" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Emphasis"/>
  <w:LsdException Locked="false" Priority="21" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Emphasis"/>
  <w:LsdException Locked="false" Priority="31" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Subtle Reference"/>
  <w:LsdException Locked="false" Priority="32" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Intense Reference"/>
  <w:LsdException Locked="false" Priority="33" SemiHidden="false"
   UnhideWhenUsed="false" QFormat="true" Name="Book Title"/>
  <w:LsdException Locked="false" Priority="37" Name="Bibliography"/>
  <w:LsdException Locked="false" Priority="39" QFormat="true" Name="TOC Heading"/>
 </w:LatentStyles>
</xml><![endif]-->
<style>
<!--
 /* Font Definitions */
@font-face
→{font-family:"Courier New";
→panose-1:2 7 3 9 2 2 5 2 4 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-536859905 -1073711037 9 0 511 0;}
@font-face
→{font-family:Wingdings;
→panose-1:5 0 0 0 0 0 0 0 0 0;
→mso-font-charset:2;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:0 268435456 0 0 -2147483648 0;}
@font-face
→{font-family:"Cambria Math";
→panose-1:2 4 5 3 5 4 6 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-536870145 1107305727 0 0 415 0;}
@font-face
→{font-family:Calibri;
→panose-1:2 15 5 2 2 2 4 3 2 4;
→mso-font-charset:0;
→mso-generic-font-family:auto;
→mso-font-pitch:variable;
→mso-font-signature:-520092929 1073786111 9 0 415 0;}
 /* Style Definitions */
p.MsoNormal, li.MsoNormal, div.MsoNormal
→{mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-parent:"";
→margin:0cm;
→margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraph, li.MsoListParagraph, div.MsoListParagraph
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpFirst, li.MsoListParagraphCxSpFirst, div.MsoListParagraphCxSpFirst
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpMiddle, li.MsoListParagraphCxSpMiddle, div.MsoListParagraphCxSpMiddle
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
p.MsoListParagraphCxSpLast, li.MsoListParagraphCxSpLast, div.MsoListParagraphCxSpLast
→{mso-style-priority:34;
→mso-style-unhide:no;
→mso-style-qformat:yes;
→mso-style-type:export-only;
→margin-top:0cm;
→margin-right:0cm;
→margin-bottom:0cm;
→margin-left:36.0pt;
→margin-bottom:.0001pt;
→mso-add-space:auto;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
.MsoChpDefault
→{mso-style-type:export-only;
→mso-default-props:yes;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-fareast-font-family:Calibri;
→mso-fareast-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-bidi-font-family:"Times New Roman";
→mso-bidi-theme-font:minor-bidi;
→mso-ansi-language:DE;}
@page WordSection1
→{size:595.0pt 842.0pt;
→margin:70.85pt 70.85pt 2.0cm 70.85pt;
→mso-header-margin:35.4pt;
→mso-footer-margin:35.4pt;
→mso-paper-source:0;}
div.WordSection1
→{page:WordSection1;}
 /* List Definitions */
@list l0
→{mso-list-id:321811675;
→mso-list-type:hybrid;
→mso-list-template-ids:1820389242 67698705 67567619 67567621 67567617 67567619 67567621 67567617 67567619 67567621;}
@list l0:level1
→{mso-level-text:"%1\)";
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:18.0pt;
→text-indent:-18.0pt;}
@list l0:level2
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:54.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level3
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:90.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level4
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:126.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level5
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:162.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level6
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:198.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
@list l0:level7
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:234.0pt;
→text-indent:-18.0pt;
→font-family:Symbol;}
@list l0:level8
→{mso-level-number-format:bullet;
→mso-level-text:o;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:270.0pt;
→text-indent:-18.0pt;
→font-family:"Courier New";}
@list l0:level9
→{mso-level-number-format:bullet;
→mso-level-text:;
→mso-level-tab-stop:none;
→mso-level-number-position:left;
→margin-left:306.0pt;
→text-indent:-18.0pt;
→font-family:Wingdings;}
ol
→{margin-bottom:0cm;}
ul
→{margin-bottom:0cm;}
-->
</style>
<!--[if gte mso 10]>
<style>
 /* Style Definitions */
table.MsoNormalTable
→{mso-style-name:"Table Normal";
→mso-tstyle-rowband-size:0;
→mso-tstyle-colband-size:0;
→mso-style-noshow:yes;
→mso-style-priority:99;
→mso-style-parent:"";
→mso-padding-alt:0cm 5.4pt 0cm 5.4pt;
→mso-para-margin:0cm;
→mso-para-margin-bottom:.0001pt;
→mso-pagination:widow-orphan;
→font-size:12.0pt;
→font-family:Calibri;
→mso-ascii-font-family:Calibri;
→mso-ascii-theme-font:minor-latin;
→mso-hansi-font-family:Calibri;
→mso-hansi-theme-font:minor-latin;
→mso-ansi-language:DE;}
</style>
<![endif]-->
</head>
<body bgcolor=white lang=EN-GB style='tab-interval:36.0pt'>
<!--StartFragment-->
<p class=MsoListParagraphCxSpFirst style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>1)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Equipment<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>2)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Chemicals<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>3)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Consumables<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>4)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Enzymes<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>5)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>GMO<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>6)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Antibodies<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>7)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>DNA Constructs<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>8)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>RNA Constructs<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpMiddle style='margin-left:18.0pt;mso-add-space:
auto;text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>9)<span
style='font:7.0pt "Times New Roman"'>&nbsp;&nbsp;&nbsp; </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Vectors<o:p></o:p></span></p>
<p class=MsoListParagraphCxSpLast style='margin-left:18.0pt;mso-add-space:auto;
text-indent:-18.0pt;mso-list:l0 level1 lfo1'><![if !supportLists]><span
lang=EN-US style='mso-bidi-font-family:Calibri;mso-bidi-theme-font:minor-latin;
mso-ansi-language:EN-US'><span style='mso-list:Ignore'>10)<span
style='font:7.0pt "Times New Roman"'> </span></span></span><![endif]><span
lang=EN-US style='mso-ansi-language:EN-US'>Oligos<o:p></o:p></span></p>
<!--EndFragment-->
</body>
</html>
.
Document[0, 90741]
  Paragraph[0, 11] isTrailingBlankLine
    Link[0, 10] textOpen:[0, 1, "["] text:[1, 6, "other"] textClose:[6, 7, "]"] linkOpen:[7, 8, "("] url:[8, 9, "#"] pageRef:[8, 8] anchorMarker:[8, 9, "#"] anchorRef:[9, 9] linkClose:[9, 10, ")"]
      Text[1, 6] chars:[1, 6, "other"]
  Paragraph[12, 62] isTrailingBlankLine
    Link[12, 31] textOpen:[12, 13, "["] text:[13, 18, "other"] textClose:[18, 19, "]"] linkOpen:[19, 20, "("] url:[20, 30, "other.html"] pageRef:[20, 30, "other.html"] linkClose:[30, 31, ")"]
      Text[13, 18] chars:[13, 18, "other"]
    SoftLineBreak[31, 32]
    Link[32, 61] textOpen:[32, 33, "["] text:[33, 43, "Sample Doc"] textClose:[43, 44, "]"] linkOpen:[44, 45, "("] url:[45, 60, "sample-doc.html"] pageRef:[45, 60, "sample-doc.html"] linkClose:[60, 61, ")"]
      Text[33, 43] chars:[33, 43, "Sample Doc"]
  Paragraph[63, 140] isTrailingBlankLine
    Image[63, 139] textOpen:[63, 65, "!["] text:[65, 89, "Screen Shot Project View"] textClose:[89, 90, "]"] linkOpen:[90, 91, "("] url:[91, 138, "../resources/images/ScreenShot_Project_View.png"] pageRef:[91, 138, "../resources/images/ScreenShot_Project_View.png"] linkClose:[138, 139, ")"]
      Text[65, 89] chars:[65, 89, "Scree …  View"]
  Paragraph[141, 204] isTrailingBlankLine
    Image[141, 203] textOpen:[141, 143, "!["] text:[143, 143] textClose:[143, 144, "]"] linkOpen:[144, 145, "("] url:[145, 202, "https://github.com/vsch/MarkdownTest/raw/master/dummy.png"] pageRef:[145, 202, "https://github.com/vsch/MarkdownTest/raw/master/dummy.png"] linkClose:[202, 203, ")"]
  HtmlBlock[205, 409]
  HtmlBlock[410, 25592]
  HtmlBlock[25593, 25674]
  HtmlBlock[25675, 26167]
  HtmlBlock[26168, 26661]
  HtmlBlock[26662, 27157]
  HtmlBlock[27158, 27649]
  HtmlBlock[27650, 28137]
  HtmlBlock[28138, 28632]
  HtmlBlock[28633, 29131]
  HtmlBlock[29132, 29630]
  HtmlBlock[29631, 30122]
  HtmlBlock[30123, 30611]
  HtmlCommentBlock[30612, 30631]
  HtmlBlock[30631, 30639]
  HtmlBlock[30640, 30648]
  HtmlBlock[30650, 30854]
  HtmlBlock[30855, 55884]
  HtmlBlock[55885, 55966]
  HtmlBlock[55967, 56437]
  HtmlBlock[56438, 56909]
  HtmlBlock[56910, 57383]
  HtmlBlock[57384, 57853]
  HtmlBlock[57854, 58319]
  HtmlBlock[58320, 58792]
  HtmlBlock[58793, 59269]
  HtmlBlock[59270, 59746]
  HtmlBlock[59747, 60216]
  HtmlBlock[60217, 60666]
  HtmlCommentBlock[60667, 60686]
  HtmlBlock[60686, 60694]
  HtmlBlock[60695, 60703]
  HtmlBlock[60705, 60909]
  HtmlBlock[60910, 85982]
  HtmlBlock[85983, 86064]
  HtmlBlock[86065, 86529]
  HtmlBlock[86530, 86995]
  HtmlBlock[86996, 87463]
  HtmlBlock[87464, 87927]
  HtmlBlock[87928, 88387]
  HtmlBlock[88388, 88854]
  HtmlBlock[88855, 89325]
  HtmlBlock[89326, 89796]
  HtmlBlock[89797, 90260]
  HtmlBlock[90261, 90704]
  HtmlCommentBlock[90705, 90724]
  HtmlBlock[90724, 90732]
  HtmlBlock[90733, 90741]
````````````````````````````````


