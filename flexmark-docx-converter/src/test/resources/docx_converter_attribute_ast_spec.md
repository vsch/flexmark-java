---
title: DocxConverter Attributes Conversion Spec
author: Vladimir Schneider
version: 1.0
date: '2019-08-15'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## DocxConverter

Converts markdown to docx with attributes

### Paragraphs

basic text

```````````````````````````````` example Paragraphs: 1
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
            <w:t xml:space="preserve">plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 2
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
            <w:t xml:space="preserve">Bold</w:t>
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


```````````````````````````````` example Paragraphs: 3
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
            <w:t xml:space="preserve">Italic</w:t>
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


```````````````````````````````` example Paragraphs: 4
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
            <w:t xml:space="preserve">strike-through</w:t>
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


```````````````````````````````` example Paragraphs: 5
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
            <w:t xml:space="preserve">subscript</w:t>
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
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">O</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 6
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
            <w:t xml:space="preserve">superscript</w:t>
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
            <w:t xml:space="preserve">i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">pi</w:t>
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
Document[0, 58]
  Paragraph[0, 58]
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


```````````````````````````````` example Paragraphs: 7
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
            <w:t xml:space="preserve">superscript</w:t>
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
            <w:t xml:space="preserve">ipi</w:t>
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


```````````````````````````````` example Paragraphs: 8
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
            <w:t xml:space="preserve">underline</w:t>
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


```````````````````````````````` example Paragraphs: 9
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
            <w:t xml:space="preserve">inline code</w:t>
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


```````````````````````````````` example(Paragraphs: 10) options(highlight-code)
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
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono"
                    w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="FF0000"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
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


```````````````````````````````` example(Paragraphs: 11) options(highlight-shade)
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
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono"
                    w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="FF0000"/>
                <w:highlight w:val="white"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
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

```````````````````````````````` example Paragraphs: 12
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
            <w:t xml:space="preserve">Bold-Italic</w:t>
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
            <w:t xml:space="preserve">Bold-Italic</w:t>
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
            <w:t xml:space="preserve">Bold-Underline</w:t>
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
            <w:t xml:space="preserve">Bold-Superscript</w:t>
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
            <w:t xml:space="preserve">i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">&#x1d6d1;</w:t>
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
            <w:t xml:space="preserve">Bold-Subscript</w:t>
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
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">O</w:t>
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
            <w:t xml:space="preserve">Bold-strike-through</w:t>
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

```````````````````````````````` example Paragraphs: 13
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
            <w:t xml:space="preserve">plain text</w:t>
        </w:r>
    </w:p>
</w:body>
.
Document[0, 31]
  Paragraph[0, 31]
    TextBase[0, 10] chars:[0, 10, "plain text"]
      Text[0, 10] chars:[0, 10, "plain text"]
    AttributesNode[10, 30] textOpen:[10, 11, "{"] text:[11, 29, "style=\"color:blue\""] textClose:[29, 30, "}"]
      AttributeNode[11, 29] name:[11, 16, "style"] sep:[16, 17, "="] valueOpen:[17, 18, "\""] value:[18, 28, "color:blue"] valueClose:[28, 29, "\""]
````````````````````````````````


```````````````````````````````` example Paragraphs: 14
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
            <w:t xml:space="preserve">Bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 15
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
            <w:t xml:space="preserve">Italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 16
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
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 17
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
            <w:t xml:space="preserve">subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">O{</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 18
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
            <w:t xml:space="preserve">superscript</w:t>
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
            <w:t xml:space="preserve">i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">pi</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 19
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
            <w:t xml:space="preserve">superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text e</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">ipi</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> = -1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 20
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
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 21
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
            <w:t xml:space="preserve">inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 22) options(highlight-code)
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
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono"
                    w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 23) options(highlight-shade)
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
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono"
                    w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="white"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
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

```````````````````````````````` example Paragraphs: 24
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
            <w:t xml:space="preserve">Bold-Italic</w:t>
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
            <w:t xml:space="preserve">Bold-Italic</w:t>
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
            <w:t xml:space="preserve">Bold-Underline</w:t>
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
            <w:t xml:space="preserve">Bold-Superscript</w:t>
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
            <w:t xml:space="preserve">i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">&#x1d6d1;</w:t>
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
            <w:t xml:space="preserve">Bold-Subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text H</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">O</w:t>
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
            <w:t xml:space="preserve">Bold-strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


basic text

```````````````````````````````` example Paragraphs: 25
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
            <w:t xml:space="preserve">plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 26
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
            <w:t xml:space="preserve">Bold</w:t>
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


```````````````````````````````` example Paragraphs: 27
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
            <w:t xml:space="preserve">Italic</w:t>
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


```````````````````````````````` example Paragraphs: 28
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
            <w:t xml:space="preserve">strike-through</w:t>
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


```````````````````````````````` example Paragraphs: 29
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
            <w:t xml:space="preserve">subscript</w:t>
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
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">O</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 30
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
            <w:t xml:space="preserve">superscript</w:t>
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
            <w:t xml:space="preserve">i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">pi</w:t>
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
Document[0, 78]
  Paragraph[0, 78]
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


```````````````````````````````` example Paragraphs: 31
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
            <w:t xml:space="preserve">superscript</w:t>
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
            <w:t xml:space="preserve">ipi</w:t>
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


```````````````````````````````` example Paragraphs: 32
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
            <w:t xml:space="preserve">underline</w:t>
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


```````````````````````````````` example Paragraphs: 33
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
            <w:t xml:space="preserve">inline code</w:t>
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


```````````````````````````````` example(Paragraphs: 34) options(highlight-code)
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
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono"
                    w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
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


```````````````````````````````` example(Paragraphs: 35) options(highlight-shade)
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
                <w:rFonts w:ascii="Courier New" w:cs="Liberation Mono"
                    w:eastAsia="Courier New" w:hAnsi="Courier New"/>
                <w:color w:val="0000FF"/>
                <w:highlight w:val="white"/>
                <w:bdr w:color="EEC5E1" w:space="1" w:sz="2" w:val="single"/>
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
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

```````````````````````````````` example Paragraphs: 36
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
            <w:t xml:space="preserve">Bold-Italic</w:t>
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
            <w:t xml:space="preserve">Bold-Italic</w:t>
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
            <w:t xml:space="preserve">Bold-Underline</w:t>
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
            <w:t xml:space="preserve">Bold-Superscript</w:t>
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
            <w:t xml:space="preserve">i</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">&#x1d6d1;</w:t>
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
            <w:t xml:space="preserve">Bold-Subscript</w:t>
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
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">O</w:t>
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
            <w:t xml:space="preserve">Bold-strike-through</w:t>
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

```````````````````````````````` example Paragraphs: 37
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
            <w:t xml:space="preserve">plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


background color for paragraphs

```````````````````````````````` example Paragraphs: 38
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
            <w:t xml:space="preserve">plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 39
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
            <w:t xml:space="preserve">Bold</w:t>
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

```````````````````````````````` example Lists: 1
* {style="color:red"}list 1

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
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">list 1</w:t>
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
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">list 2</w:t>
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
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Numbering start

```````````````````````````````` example(Lists: 2) options(IGNORED)
2. {style="color:red"}list 1

   with some text

   3. list 2
  
      with some text

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">list 1</w:t>
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
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="FF0000"/>
            </w:rPr>
            <w:t xml:space="preserve">list 2</w:t>
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
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Headings

```````````````````````````````` example Headings: 1
# {style="color:red;background-color:yellow"}Heading <!---->Text{style="color:blue"}
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-text"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pBdr>
                <w:left/>
            </w:pBdr>
            <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
                <w:color w:val="FF0000"/>
                <w:sz w:val="48"/>
                <w:szCs w:val="48"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t xml:space="preserve">Heading </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:b/>
                <w:bCs/>
                <w:color w:val="0000FF"/>
                <w:sz w:val="48"/>
                <w:szCs w:val="48"/>
                <w:shd w:color="auto" w:fill="yellow" w:val="clear"/>
            </w:rPr>
            <w:t xml:space="preserve">Text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


