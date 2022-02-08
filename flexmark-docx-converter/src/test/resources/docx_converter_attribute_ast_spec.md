---
title: DocxConverter Attributes Conversion Spec
author: Vladimir Schneider
version: 1.0
date: '2019-08-15'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# DocxConverter

Converts markdown to docx with attributes

## Paragraphs

basic text

```````````````````````````````` example Paragraphs: 1
{style="color:red"}plain text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 2
{style="color:red"}plain **Bold** text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 3
{style="color:red"}plain *Italic* text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 4
{style="color:red"}plain ~~strike-through~~ text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 5
{style="color:red"}plain ~subscript~ text H~2~O
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>subscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>O</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 6
{style="color:red"}plain ^superscript^ text e^*i*pi^ = -1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="FF0000"/>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t>i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>pi</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
.
Document[0, 57]
  Paragraph[0, 57]
    AttributesNode[0, 19] textOpen:[0, 1, "{"] text:[1, 18, "style=\"color:red\""] textClose:[18, 19, "}"]
      AttributeNode[1, 18] name:[1, 6, "style"] sep:[6, 7, "="] valueOpen:[7, 8, "\""] value:[8, 17, "color:red"] valueClose:[17, 18, "\""]
    Text[19, 25] chars:[19, 25, "plain "]
    Superscript[25, 38] textOpen:[25, 26, "^"] text:[26, 37, "superscript"] textClose:[37, 38, "^"]
      Text[26, 37] chars:[26, 37, "super … cript"]
    Text[38, 45] chars:[38, 45, " text e"]
    Superscript[45, 52] textOpen:[45, 46, "^"] text:[46, 51, "*i*pi"] textClose:[51, 52, "^"]
      Emphasis[46, 49] textOpen:[46, 47, "*"] text:[47, 48, "i"] textClose:[48, 49, "*"]
        Text[47, 48] chars:[47, 48, "i"]
      Text[49, 51] chars:[49, 51, "pi"]
    Text[52, 57] chars:[52, 57, " = -1"]
````````````````````````````````


```````````````````````````````` example Paragraphs: 7
{style="color:red"}plain ^superscript^ text e^ipi^ = -1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>ipi</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 8
{style="color:red"}plain ++underline++ text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 9
{style="color:red"}plain `inline code` text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 10) options(highlight-code)
{style="color:red"}plain `inline code` highlight text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="FF0000"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 11) options(highlight-shade)
{style="color:red"}plain `inline code` highlight text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="FF0000"/>
                <w:highlight w:val="white"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 12
# Change Font Size {style="font-size: 20pt"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="change-font-size"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="40"/>
                <w:szCs w:val="40"/>
            </w:rPr>
            <w:t>Change Font Size</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 13
# Change Font Size {style="font-size: 20.5 pt"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="change-font-size"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="41"/>
                <w:szCs w:val="41"/>
            </w:rPr>
            <w:t>Change Font Size</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 14
# Change Font Size {style="font-size: 20"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="change-font-size"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="40"/>
                <w:szCs w:val="40"/>
            </w:rPr>
            <w:t>Change Font Size</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 15
# Change Font Size {style="font-size: 20.5"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="change-font-size"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="41"/>
                <w:szCs w:val="41"/>
            </w:rPr>
            <w:t>Change Font Size</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


permutations of formatting

```````````````````````````````` example Paragraphs: 16
{style="color:red"}plain **Bold *Bold-Italic*** text
{style="color:red"}plain *Italic **Bold-Italic*** text
{style="color:red"}plain **Bold ++Bold-Underline++** text
{style="color:red"}plain **Bold ^Bold-Superscript^** text e^*i*𝛑^ = -1
{style="color:red"}plain **Bold ~Bold-Subscript~** text H~2~O
{style="color:red"}plain **Bold ~~Bold-strike-through~~** text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">Italic </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold-Underline</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold-Superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="FF0000"/>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t>i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>𝛑</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold-Subscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>O</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>Bold-strike-through</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Partial text attributes

```````````````````````````````` example Paragraphs: 17
plain text{style="color:blue"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>plain text</w:t>
        </w:r>
    </w:p>
</w:body>
.
Document[0, 30]
  Paragraph[0, 30]
    TextBase[0, 10] chars:[0, 10, "plain text"]
      Text[0, 10] chars:[0, 10, "plain text"]
    AttributesNode[10, 30] textOpen:[10, 11, "{"] text:[11, 29, "style=\"color:blue\""] textClose:[29, 30, "}"]
      AttributeNode[11, 29] name:[11, 16, "style"] sep:[16, 17, "="] valueOpen:[17, 18, "\""] value:[18, 28, "color:blue"] valueClose:[28, 29, "\""]
````````````````````````````````


```````````````````````````````` example Paragraphs: 18
plain **Bold**{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 19
plain *Italic*{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 20
plain ~~strike-through~~{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 21
plain ~subscript~{style="color:blue"} text H~2~O{
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t>2</w:t>
        </w:r>
        <w:r>
            <w:t>O{</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 22
plain ^superscript^{style="color:blue"} text e^*i*pi^ = -1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t>i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t>pi</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 23
plain ^superscript^{style="color:blue"} text e^ipi^ = -1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t>ipi</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 24
plain ++underline++{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 25
plain `inline code`{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 26) options(highlight-code)
plain `inline code`{style="color:blue"} highlight text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 27) options(highlight-shade)
{style="color:red"}plain `inline code`{style="color:blue"} highlight text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="white"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


permutations of formatting

```````````````````````````````` example Paragraphs: 28
plain **Bold *Bold-Italic***{style="color:blue"} text
plain *Italic **Bold-Italic***{style="color:blue"} text
plain **Bold ++Bold-Underline++**{style="color:blue"} text
plain **Bold ^Bold-Superscript^**{style="color:blue"} text e^*i*𝛑^ = -1
plain **Bold ~Bold-Subscript~**{style="color:blue"} text H~2~O
plain **Bold ~~Bold-strike-through~~**{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Italic </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t>i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t>𝛑</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t>2</w:t>
        </w:r>
        <w:r>
            <w:t>O</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


basic text

```````````````````````````````` example Paragraphs: 29
{style="color:red"}plain text{style="color:blue"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 30
{style="color:red"}plain **Bold**{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 31
{style="color:red"}plain *Italic*{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 32
{style="color:red"}plain ~~strike-through~~{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 33
{style="color:red"}plain ~subscript~{style="color:blue"} text H~2~O
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>subscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>O</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 34
{style="color:red"}plain ^superscript^{style="color:blue"} text e^*i*pi^ = -1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="FF0000"/>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t>i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>pi</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
.
Document[0, 77]
  Paragraph[0, 77]
    AttributesNode[0, 19] textOpen:[0, 1, "{"] text:[1, 18, "style=\"color:red\""] textClose:[18, 19, "}"]
      AttributeNode[1, 18] name:[1, 6, "style"] sep:[6, 7, "="] valueOpen:[7, 8, "\""] value:[8, 17, "color:red"] valueClose:[17, 18, "\""]
    Text[19, 25] chars:[19, 25, "plain "]
    Superscript[25, 38] textOpen:[25, 26, "^"] text:[26, 37, "superscript"] textClose:[37, 38, "^"]
      Text[26, 37] chars:[26, 37, "super … cript"]
    AttributesNode[38, 58] textOpen:[38, 39, "{"] text:[39, 57, "style=\"color:blue\""] textClose:[57, 58, "}"]
      AttributeNode[39, 57] name:[39, 44, "style"] sep:[44, 45, "="] valueOpen:[45, 46, "\""] value:[46, 56, "color:blue"] valueClose:[56, 57, "\""]
    Text[58, 65] chars:[58, 65, " text e"]
    Superscript[65, 72] textOpen:[65, 66, "^"] text:[66, 71, "*i*pi"] textClose:[71, 72, "^"]
      Emphasis[66, 69] textOpen:[66, 67, "*"] text:[67, 68, "i"] textClose:[68, 69, "*"]
        Text[67, 68] chars:[67, 68, "i"]
      Text[69, 71] chars:[69, 71, "pi"]
    Text[72, 77] chars:[72, 77, " = -1"]
````````````````````````````````


```````````````````````````````` example Paragraphs: 35
{style="color:red"}plain ^superscript^{style="color:blue"} text e^ipi^ = -1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>ipi</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 36
{style="color:red"}plain ++underline++{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 37
{style="color:red"}plain `inline code`{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 38) options(highlight-code)
{style="color:red"}plain `inline code`{style="color:blue"} highlight text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 39) options(highlight-shade)
{style="color:red"}plain `inline code`{style="color:blue"} highlight text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono" w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="white"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


permutations of formatting

```````````````````````````````` example Paragraphs: 40
{style="color:red"}plain **Bold *Bold-Italic***{style="color:blue"} text
{style="color:red"}plain *Italic **Bold-Italic***{style="color:blue"} text
{style="color:red"}plain **Bold ++Bold-Underline++**{style="color:blue"} text
{style="color:red"}plain **Bold ^Bold-Superscript^**{style="color:blue"} text e^*i*𝛑^ = -1
{style="color:red"}plain **Bold ~Bold-Subscript~**{style="color:blue"} text H~2~O
{style="color:red"}plain **Bold ~~Bold-strike-through~~**{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Italic </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Italic</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Underline</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Superscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:color w:val="FF0000"/>
                <w:position w:val="8"/>
                <w:sz w:val="19"/>
            </w:rPr>
            <w:t>i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>𝛑</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-Subscript</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>O</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
            </w:rPr>
            <w:t>Bold-strike-through</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


background color for paragraphs

```````````````````````````````` example Paragraphs: 41
{style="color:red;background-color:yellow"}plain text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t>plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


background color for paragraphs

```````````````````````````````` example Paragraphs: 42
{style="color:red;background-color:yellow"}plain text{style="color:blue"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="0000FF"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t>plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 43
{style="color:red;background-color:yellow"}plain **Bold**{style="color:blue"} text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:color w:val="0000FF"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t>Bold</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Lists

```````````````````````````````` example Lists: 1
* {style="color:red"}list 1

  with some text

  * list 2
  
    with some text

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="227"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="2"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="454"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Numbering start

```````````````````````````````` example(Lists: 2) options(IGNORED)
2. {style="color:red"}list 1

   with some text

   3. list 2
  
      with some text

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="283"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="567"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Headings

```````````````````````````````` example Headings: 1
# {style="color:red;background-color:yellow"}Heading <!---->Text{style="color:blue"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-text"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t xml:space="preserve">Heading </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="0000FF"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t>Text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 2
Heading {style="font-size: 26pt"}
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
            <w:rPr>
                <w:sz w:val="52"/>
                <w:szCs w:val="52"/>
            </w:rPr>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Headings: 3
# Heading {style="font-size: 26pt"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="52"/>
                <w:szCs w:val="52"/>
            </w:rPr>
            <w:t>Heading</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Style

```````````````````````````````` example Style: 1
Paragraph Style 

{.Heading1}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:t>Paragraph Style</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Page Break

```````````````````````````````` example Page Break: 1
{.pagebreak}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:br w:type="page"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Page Break: 2
Some text
{.pagebreak}

Following Text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some text</w:t>
            <w:br w:type="page"/>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Following Text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Page Break: 3
Some text  

{.pagebreak} Following text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:br w:type="page"/>
        </w:r>
        <w:r>
            <w:t>Following text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Page Break: 4
Some text

{.pagebreak}

Following Text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:br w:type="page"/>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Following Text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Tab

```````````````````````````````` example Tab: 1
{.tab}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:tab/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Tab: 2
Some text {.tab} Following Text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some text </w:t>
            <w:tab/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> Following Text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Images

Bare

```````````````````````````````` example(Images: 1) options(url, IGNORED)
![flexmark-icon-logo](/images/flexmark-icon-logo@2x.png) with some text wrapped around the image
if possible. So we add more text otherwise there is not enough to wrap in Word and validate how
the text is wrapped.
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="609600" cy="609600"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="609600" cy="609600"/>
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
        <w:r>
            <w:t xml:space="preserve"> with some text wrapped around the image</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>if possible. So we add more text otherwise there is not enough to wrap in Word and validate how</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>the text is wrapped.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Align left

```````````````````````````````` example(Images: 2) options(url, IGNORED)
![flexmark-icon-logo](/images/flexmark-icon-logo@2x.png){align="left"}with some text wrapped
around the image if possible. So we add more text otherwise there is not enough to wrap in Word
and validate how the text is wrapped.
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:anchor allowOverlap="true" behindDoc="false"
                    distB="0" distL="114300" distR="114300" distT="0"
                    layoutInCell="true" locked="false"
                    relativeHeight="251658240" simplePos="false" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:simplePos x="0" y="0"/>
                    <wp:positionH relativeFrom="column">
                        <wp:align>left</wp:align>
                    </wp:positionH>
                    <wp:positionV relativeFrom="paragraph">
                        <wp:posOffset>0</wp:posOffset>
                    </wp:positionV>
                    <wp:extent cx="609600" cy="609600"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:wrapSquare wrapText="right"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="609600" cy="609600"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:anchor>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t>with some text wrapped</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>around the image if possible. So we add more text otherwise there is not enough to wrap in Word</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>and validate how the text is wrapped.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Align right

```````````````````````````````` example(Images: 3) options(url, IGNORED)
![flexmark-icon-logo](/images/flexmark-icon-logo@2x.png){align="right"}with some text wrapped
around the image if possible. So we add more text otherwise there is not enough to wrap in Word
and validate how the text is wrapped.
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:anchor allowOverlap="true" behindDoc="false"
                    distB="0" distL="114300" distR="114300" distT="0"
                    layoutInCell="true" locked="false"
                    relativeHeight="251658240" simplePos="false" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:simplePos x="0" y="0"/>
                    <wp:positionH relativeFrom="column">
                        <wp:align>right</wp:align>
                    </wp:positionH>
                    <wp:positionV relativeFrom="paragraph">
                        <wp:posOffset>0</wp:posOffset>
                    </wp:positionV>
                    <wp:extent cx="609600" cy="609600"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:wrapSquare wrapText="left"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="609600" cy="609600"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:anchor>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t>with some text wrapped</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>around the image if possible. So we add more text otherwise there is not enough to wrap in Word</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>and validate how the text is wrapped.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Align center

```````````````````````````````` example(Images: 4) options(url, IGNORED)
![flexmark-icon-logo](/images/flexmark-icon-logo@2x.png){align="center"}with some text wrapped
around the image if possible. So we add more text otherwise there is not enough to wrap in Word
and validate how the text is wrapped.
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:anchor allowOverlap="true" behindDoc="false"
                    distB="0" distL="114300" distR="114300" distT="0"
                    layoutInCell="true" locked="false"
                    relativeHeight="251658240" simplePos="false" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:simplePos x="0" y="0"/>
                    <wp:positionH relativeFrom="column">
                        <wp:align>center</wp:align>
                    </wp:positionH>
                    <wp:positionV relativeFrom="paragraph">
                        <wp:posOffset>0</wp:posOffset>
                    </wp:positionV>
                    <wp:extent cx="609600" cy="609600"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:wrapSquare wrapText="bothSides"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="609600" cy="609600"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:anchor>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t>with some text wrapped</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>around the image if possible. So we add more text otherwise there is not enough to wrap in Word</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>and validate how the text is wrapped.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Size in cm

```````````````````````````````` example(Images: 5) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){align=right width=2cm} 
with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:anchor allowOverlap="true" behindDoc="false"
                    distB="0" distL="114300" distR="114300" distT="0"
                    layoutInCell="true" locked="false"
                    relativeHeight="251658240" simplePos="false" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:simplePos x="0" y="0"/>
                    <wp:positionH relativeFrom="column">
                        <wp:align>right</wp:align>
                    </wp:positionH>
                    <wp:positionV relativeFrom="paragraph">
                        <wp:posOffset>0</wp:posOffset>
                    </wp:positionV>
                    <wp:extent cx="719455" cy="719455"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:wrapSquare wrapText="left"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="719455" cy="719455"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:anchor>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Size in cm

```````````````````````````````` example(Images: 6) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){align=right height=6cm} with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:anchor allowOverlap="true" behindDoc="false"
                    distB="0" distL="114300" distR="114300" distT="0"
                    layoutInCell="true" locked="false"
                    relativeHeight="251658240" simplePos="false" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:simplePos x="0" y="0"/>
                    <wp:positionH relativeFrom="column">
                        <wp:align>right</wp:align>
                    </wp:positionH>
                    <wp:positionV relativeFrom="paragraph">
                        <wp:posOffset>0</wp:posOffset>
                    </wp:positionV>
                    <wp:extent cx="2159635" cy="2159635"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:wrapSquare wrapText="left"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="2159635" cy="2159635"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:anchor>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Size in cm

```````````````````````````````` example(Images: 7) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){align=right height=6cm width=4cm}
with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:anchor allowOverlap="true" behindDoc="false"
                    distB="0" distL="114300" distR="114300" distT="0"
                    layoutInCell="true" locked="false"
                    relativeHeight="251658240" simplePos="false" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:simplePos x="0" y="0"/>
                    <wp:positionH relativeFrom="column">
                        <wp:align>right</wp:align>
                    </wp:positionH>
                    <wp:positionV relativeFrom="paragraph">
                        <wp:posOffset>0</wp:posOffset>
                    </wp:positionV>
                    <wp:extent cx="1439545" cy="2159635"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:wrapSquare wrapText="left"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="1439545" cy="2159635"/>
                                    </a:xfrm>
                                    <a:prstGeom prst="rect">
                                    <a:avLst/>
                                    </a:prstGeom>
                                </pic:spPr>
                            </pic:pic>
                        </a:graphicData>
                    </a:graphic>
                </wp:anchor>
            </w:drawing>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Size in inches

```````````````````````````````` example(Images: 8) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){width=1in} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="914400" cy="914400"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="914400" cy="914400"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max width in cm

```````````````````````````````` example(Images: 9) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){max-width=1cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="359410" cy="359410"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="359410" cy="359410"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max width in cm, smaller image

```````````````````````````````` example(Images: 10) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){max-width=10cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="609600" cy="609600"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="609600" cy="609600"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max height in cm

```````````````````````````````` example(Images: 11) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){max-height=1cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="359410" cy="359410"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="359410" cy="359410"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max width/height in cm, use max width

```````````````````````````````` example(Images: 12) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){max-width=1cm max-height=2cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="359410" cy="359410"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="359410" cy="359410"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max width/height in cm, use max height

```````````````````````````````` example(Images: 13) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){max-width=2cm max-height=1cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="359410" cy="359410"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="359410" cy="359410"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max height in cm, smaller image

```````````````````````````````` example(Images: 14) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png){max-height=10cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="609600" cy="609600"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="609600" cy="609600"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Max height in cm, smaller image

```````````````````````````````` example(Images: 15) options(url, IGNORED)
![flexmark-icon-logo](assets/images/testImage.png){max-width=6cm max-height=2.4cm} 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0" xmlns:wp="http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing">
                    <wp:extent cx="2159635" cy="777469"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="100000" name="Image100000"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true" xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic xmlns:a="http://schemas.openxmlformats.org/drawingml/2006/main">
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic xmlns:pic="http://schemas.openxmlformats.org/drawingml/2006/picture">
                                <pic:nvPicPr>
                                    <pic:cNvPr id="100001" name="Image100000"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId3" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
                                    <a:stretch>
                                    <a:fillRect/>
                                    </a:stretch>
                                </pic:blipFill>
                                <pic:spPr>
                                    <a:xfrm>
                                    <a:off x="0" y="0"/>
                                    <a:ext cx="2159635" cy="777469"/>
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
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


