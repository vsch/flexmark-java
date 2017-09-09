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

### Paragraphs

basic text

```````````````````````````````` example(Paragraphs: 1) options(IGNORES)
Sample text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Sample text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 2) options(IGNORES)
**Bold** text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
            </w:rPr>
            <w:t xml:space="preserve">Bold</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 3) options(IGNORES)
*Italic* text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 4) options(IGNORES)
***Bold-Italic*** text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 5) options(IGNORES)
~~strike-through~~ text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:strike/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 6) options(IGNORES)
~subscript~ text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t xml:space="preserve">subscript</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 7) options(IGNORES)
^superscript^ text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t xml:space="preserve">superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 8) options(IGNORES)
++underline++ text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:u w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Lists

```````````````````````````````` example(Lists: 1) options(IGNORES)
* list 1

  with some text

  * list 2
  
    with some text

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="227"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="454"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Links

Web URL

```````````````````````````````` example(Links: 1) options(IGNORES)
[flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png" \o "Title: flexmark-java logo" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">flexmark-icon-logo</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Images

Web URL

```````````````````````````````` example(Images: 1) options(IGNORES)
![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="541871" cy="541871"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="541871" cy="541871"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Local File

```````````````````````````````` example(Images: 2) options(IGNORES)
![flexmark-icon-logo](file:///Users/vlad/src/flexmark-java/assets/images/flexmark-icon-logo@2x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="541871" cy="541871"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="541871" cy="541871"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Relative Path

```````````````````````````````` example(Images: 3) options(url, IGNORES)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png) 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Block Quotes

with hard break

```````````````````````````````` example(Block Quotes: 1) options(IGNORES)
> block quote 1  
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png" \o "Title: flexmark-java logo" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">flexmark-icon-logo</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


with child paragraphs

```````````````````````````````` example(Block Quotes: 2) options(IGNORES)
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png" \o "Title: flexmark-java logo" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">flexmark-icon-logo</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


with child block quote

```````````````````````````````` example(Block Quotes: 3) options(IGNORES)
> block quote 1  
>
>> another block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="240" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">another block quote</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


with child paragraphs

```````````````````````````````` example(Block Quotes: 4) options(IGNORES)
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
>> another block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png" \o "Title: flexmark-java logo" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">flexmark-icon-logo</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="240" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">another block quote</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## HTML

```````````````````````````````` example HTML: 1
<h1>This is a heading</h1>
<p>paragraph</p>
<hr />
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">&lt;h1&gt;This is a heading&lt;/h1&gt;
&lt;p&gt;paragraph&lt;/p&gt;
&lt;hr /&gt;</w:t>
        </w:r>
    </w:p>
</w:body>
.
Document[0, 51]
  HtmlBlock[0, 51]
````````````````````````````````


## Mixed

```````````````````````````````` example(Mixed: 1) options(IGNORES)
# Heading 1 _italic_

Some Text

## Heading 2 **bold**

Some Text

### Heading 3 ~~strike-through~~

Some Text

#### Heading 4 `inline-code`

Some Text

##### Heading 5

Some Text

###### Heading 6

Some Text

------

Sample paragraph with **bold** *italic* ***bold-italic*** ++underline++ ~~strike-through~~
^superscript^ normal ~subscript~ `inline-code`

* list 1

  ![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png)

  with some text

  * list 2
  
    with some text

    * list 3
      * list 4
        * list 5
          * list 6

<!-- HTML Comment  -->

1. list 1
   1. list 2
      1. list 3
         1. list 4
            1. list 5
            
1. list item 2
   1. list item 2
        
Some plain text        

> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
> > block quote 2
> > > block quote 3
> > > > block quote 4
> > > > > block quote 5

```
pre-formatted code
code
```


| Combined _header_                                ||||
| Default       | Left **Align** | Center | Right ++Align++ |
|---------------|:-----------|:------:|------------:|
| Data 1 `Longer` | Data 2     | Data 3 |      Data 4 |
| Combined **data**             || Data 5 |      Data 6 |
| Data 7        | Combined _Data_      ||      Data 8 |
| Data 9        | Data 10    |    Combined ^Data^    ||
| Combined ~~Data~~                     |||      Data 7 |
| Data 8        | Combined ~Data~                   |||

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:strike/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Heading 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Heading 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Sample paragraph with </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">bold-italic</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:u w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:strike/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t xml:space="preserve">superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:position w:val="-4"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t xml:space="preserve">subscript</w:t>
        </w:r>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="227"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="541871" cy="541871"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="0" name="Image1"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="1" name="Image1"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="541871" cy="541871"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:inline>
            </w:drawing>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="227"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="454"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">&lt;!-- HTML Comment  --&gt;
</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="3"/>
            </w:numPr>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">Some plain text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:spacing w:after="120" w:before="240"/>
            <w:ind w:hanging="0" w:left="0" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId4">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png" \o "Title: flexmark-java logo" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">flexmark-icon-logo</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="240" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="480" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="720" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:hanging="0" w:left="960" w:right="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">pre-formatted code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
            <w:t xml:space="preserve">code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
            <w:rPr/>
        </w:pPr>
        <w:r>
            <w:rPr/>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="30"/>
            <w:tblBorders>
                <w:top w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:left w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:bottom w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:right w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:insideH w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:insideV w:color="000001" w:space="0" w:sz="2" w:val="single"/>
            </w:tblBorders>
            <w:tblCellMar>
                <w:top w:type="dxa" w:w="80"/>
                <w:left w:type="dxa" w:w="80"/>
                <w:bottom w:type="dxa" w:w="80"/>
                <w:right w:type="dxa" w:w="80"/>
            </w:tblCellMar>
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0"
                w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="4"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">header</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:u w:val="single"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Longer</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 2</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 3</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 4</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="2"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 5</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 6</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 7</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="2"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 8</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 9</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 10</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="2"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:position w:val="8"/>
                            <w:sz w:val="19"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="3"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:strike/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 7</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 8</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="3"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:position w:val="-4"/>
                            <w:sz w:val="19"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


## Tables

```````````````````````````````` example(Tables: 1) options(IGNORES)
| Combined _header_                                ||||
| Default       | Left **Align** | Center | Right ++Align++ |
|---------------|:-----------|:------:|------------:|
| Data 1 `Longer` | Data 2     | Data 3 |      Data 4 |
| Combined **data**             || Data 5 |      Data 6 |
| Data 7        | Combined _Data_      ||      Data 8 |
| Data 9        | Data 10    |    Combined ^Data^    ||
| Combined ~~Data~~                     |||      Data 7 |
| Data 8        | Combined ~Data~                   |||
.
<w:body>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="30"/>
            <w:tblBorders>
                <w:top w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:left w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:bottom w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:right w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:insideH w:color="000001" w:space="0" w:sz="2" w:val="single"/>
                <w:insideV w:color="000001" w:space="0" w:sz="2" w:val="single"/>
            </w:tblBorders>
            <w:tblCellMar>
                <w:top w:type="dxa" w:w="80"/>
                <w:left w:type="dxa" w:w="80"/>
                <w:bottom w:type="dxa" w:w="80"/>
                <w:right w:type="dxa" w:w="80"/>
            </w:tblCellMar>
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0"
                w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="4"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">header</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:u w:val="single"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Longer</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 2</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 3</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 4</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="2"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 5</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 6</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 7</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="2"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 8</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 9</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 10</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="2"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="center"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:position w:val="8"/>
                            <w:sz w:val="19"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="3"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:strike/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="right"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 7</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
        <w:tr>
            <w:trPr/>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Data 8</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="3"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                        <w:rPr/>
                    </w:pPr>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:position w:val="-4"/>
                            <w:sz w:val="19"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr/>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


