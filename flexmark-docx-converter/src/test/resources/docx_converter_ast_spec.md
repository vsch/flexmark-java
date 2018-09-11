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

```````````````````````````````` example Paragraphs: 1
plain text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 2
plain **Bold** text
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
            </w:rPr>
            <w:t xml:space="preserve">Bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 3
plain *Italic* text
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
            </w:rPr>
            <w:t xml:space="preserve">Italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 4
plain ~~strike-through~~ text
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
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 5
plain ~subscript~ text H~2~O
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
            <w:t xml:space="preserve">O</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 6
plain ^superscript^ text e^*i*pi^ = -1
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
.
Document[0, 39]
  Paragraph[0, 39]
    Text[0, 6] chars:[0, 6, "plain "]
    Superscript[6, 19] textOpen:[6, 7, "^"] text:[7, 18, "superscript"] textClose:[18, 19, "^"]
      Text[7, 18] chars:[7, 18, "super … cript"]
    Text[19, 26] chars:[19, 26, " text e"]
    Superscript[26, 33] textOpen:[26, 27, "^"] text:[27, 32, "*i*pi"] textClose:[32, 33, "^"]
      Emphasis[27, 30] textOpen:[27, 28, "*"] text:[28, 29, "i"] textClose:[29, 30, "*"]
        Text[28, 29] chars:[28, 29, "i"]
      Text[30, 32] chars:[30, 32, "pi"]
    Text[33, 38] chars:[33, 38, " = -1"]
````````````````````````````````


```````````````````````````````` example Paragraphs: 7
plain ^superscript^ text e^ipi^ = -1
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


```````````````````````````````` example Paragraphs: 8
plain ++underline++ text
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
            </w:rPr>
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Paragraphs: 9
plain `inline code` text
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
            </w:rPr>
            <w:t xml:space="preserve">inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 10) options(highlight-code)
plain `inline code` highlight text
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
                <w:color w:val="BB002F"/>
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


```````````````````````````````` example(Paragraphs: 11) options(highlight-shade)
plain `inline code` highlight text
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
                <w:color w:val="BB002F"/>
                <w:highlight w:val="white"/>
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


permutations of formatting

```````````````````````````````` example Paragraphs: 12
plain **Bold *Bold-Italic*** text
plain *Italic **Bold-Italic*** text
plain **Bold ++Bold-Underline++** text
plain **Bold ^Bold-Superscript^** text e^*i*𝛑^ = -1
plain **Bold ~Bold-Subscript~** text H~2~O
plain **Bold ~~Bold-strike-through~~** text
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Italic </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:b/>
                <w:bCs/>
            </w:rPr>
            <w:t xml:space="preserve">Bold-strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Lists

```````````````````````````````` example Lists: 1
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
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="227"/>
        </w:pPr>
        <w:r>
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
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="454"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Numbering start

```````````````````````````````` example(Lists: 2) options(IGNORED)
2. list 1

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
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="283"/>
        </w:pPr>
        <w:r>
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
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="567"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


tight lists

```````````````````````````````` example Lists: 3
* list 1 
  * list 1.1
  * list 1.2
* list 2 
  * list 2.1
  * list 2.2
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1.2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2.2</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


loose lists

```````````````````````````````` example Lists: 4
* list 1 
  * list 1.1
  * list 1.2
  
* list 2 
  * list 2.1
  * list 2.2

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
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1.2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2.2</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


loose lists

```````````````````````````````` example Lists: 5
* list 1 
  * list 1.1
  
  * list 1.2
  
* list 2 
  * list 2.1
  
  * list 2.2

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
            <w:t xml:space="preserve">list 1</w:t>
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
            <w:t xml:space="preserve">list 1.1</w:t>
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
            <w:t xml:space="preserve">list 1.2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2.2</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Links

Web URL

```````````````````````````````` example Links: 1
[flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Images

Web URL

```````````````````````````````` example(Images: 1) options(IGNORED)
![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Local File

```````````````````````````````` example(Images: 2) options(IGNORED)
![flexmark-icon-logo](file:///Users/vlad/src/projects/flexmark-java/assets/images/flexmark-icon-logo@2x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Relative Path

```````````````````````````````` example(Images: 3) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png) 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


Root Path

```````````````````````````````` example(Images: 4) options(url, IGNORED)
![flexmark-icon-logo](/images/flexmark-icon-logo@2x.png) 

with some text
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Block Quotes

lazy continuation

```````````````````````````````` example Block Quotes: 1
* list item
    > block quote
    lazy continuation
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="467"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">lazy continuation</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with hyperlink

```````````````````````````````` example Block Quotes: 2
> [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with hard break

```````````````````````````````` example Block Quotes: 3
> block quote 1  
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child paragraphs

```````````````````````````````` example Block Quotes: 4
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child block quote

```````````````````````````````` example Block Quotes: 5
> block quote 1  
>
>> another block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child paragraphs

```````````````````````````````` example Block Quotes: 6
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
>> another block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


## Fenced Code

```````````````````````````````` example Fenced Code: 1
```
pre-formatted code
code
```

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Fenced Code: 2
text before

```
pre-formatted code
code
```

text after
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text before</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text after</w:t>
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
<w:body/>
````````````````````````````````


## Mixed

With image

```````````````````````````````` example(Mixed: 1) options(IGNORED)
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
> > > > > 1. list 
> > > > >    * nested list

```
pre-formatted code
code
```


| Combined _header_                                        ||||
| Default         | Left **Align** | Center | Right ++Align++ |
|-----------------|:---------------|:------:|----------------:|
| Data 1 `Longer` | Data 2         | Data 3 |          Data 4 |
| Combined **data**               || Data 5 |          Data 6 |
| Data 7          | Combined _Data_        ||          Data 8 |
| Data 9          | Data 10        |     Combined ^Data^     ||
| Combined ~~Data~~                       |||          Data 7 |
| Data 8          | Combined ~Data~                         |||

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1-italic"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2-bold"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3-strike-through"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4-inline-code"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Sample paragraph with </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">bold-italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t xml:space="preserve">subscript</w:t>
        </w:r>
        <w:r>
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
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="227"/>
        </w:pPr>
        <w:r>
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
            <w:ind w:left="227"/>
        </w:pPr>
        <w:r>
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
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="454"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="8"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="9"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="10"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="11"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="12"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="13"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="14"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="10"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="15"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some plain text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId4" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="1200"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="16"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1483" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="17"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1647" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">nested list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">header</w:t>
                    </w:r>
                    <w:r>
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
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data 1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Longer</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Superscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Strikethrough"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Subscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


Without image

```````````````````````````````` example(Mixed: 2) options(IGNORED)
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
> > > > > 1. list 
> > > > >    * nested list

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
    <w:bookmarkStart w:id="1" w:name="heading-1-italic"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2-bold"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3-strike-through"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4-inline-code"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
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
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some Text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Sample paragraph with </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">bold-italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t xml:space="preserve">subscript</w:t>
        </w:r>
        <w:r>
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
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="227"/>
        </w:pPr>
        <w:r>
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
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="454"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="8"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="9"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="10"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="11"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="12"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="13"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="14"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="10"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="15"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some plain text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="1200"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="16"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1483" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="17"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1647" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">nested list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">header</w:t>
                    </w:r>
                    <w:r>
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
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data 1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Longer</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Superscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Strikethrough"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Subscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


```````````````````````````````` example(Mixed: 3) options(IGNORED)
# Heading 1 _italic_

&nbsp;

## Heading 2 **bold**

&nbsp;

### Heading 3 ~~strike-through~~

#### Heading 4 `inline-code`

##### Heading 5

###### Heading 6

------

Sample paragraph with **bold** *italic* ***bold-italic*** ++underline++ ~~strike-through~~
^superscript^ normal ~subscript~ `inline-code`

* list 1

  ![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png)

  * list 2
  
<!-- HTML Comment  -->

1. list 1
   1. list 2
            
1. list item 2
   1. list item 2
        
Some plain text        

> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
> > block quote 2
> > > block quote 5
> > > 1. list 
> > >    * nested list

```
pre-formatted code
code
```


| Combined _header_                                        ||||
| Default         | Left **Align** | Center | Right ++Align++ |
|-----------------|:---------------|:------:|----------------:|
| Data 1 `Longer` | Data 2         | Data 3 |          Data 4 |
| Combined **data**               || Data 5 |          Data 6 |
| Data 7          | Combined _Data_        ||          Data 8 |
| Data 9          | Data 10        |     Combined ^Data^     ||
| Combined ~~Data~~                       |||          Data 7 |
| Data 8          | Combined ~Data~                         |||

.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1-italic"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2-bold"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3-strike-through"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4-inline-code"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t xml:space="preserve">inline-code</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-5"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading5"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 5</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-6"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading6"/>
            <w:numPr>
                <w:ilvl w:val="5"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 6</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="HorizontalLine"/>
        </w:pPr>
        <w:r/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Sample paragraph with </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
            </w:rPr>
            <w:t xml:space="preserve">bold-italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t xml:space="preserve">underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t xml:space="preserve">superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t xml:space="preserve">subscript</w:t>
        </w:r>
        <w:r>
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
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="227"/>
        </w:pPr>
        <w:r>
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
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="8"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Some plain text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId4" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="9"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="1003" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="10"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1167" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">nested list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="PreformattedText"/>
            <w:spacing w:after="0" w:before="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">header</w:t>
                    </w:r>
                    <w:r>
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
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data 1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Longer</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Superscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Strikethrough"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Subscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


## GitLab

GitLab formatting

```````````````````````````````` example GitLab: 1
plain [-del-] [+ins+]
plain {-del-} {+ins+}
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
            </w:rPr>
            <w:t xml:space="preserve">del</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t xml:space="preserve">ins</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t xml:space="preserve">del</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t xml:space="preserve">ins</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


GitLab block quotes formatting

```````````````````````````````` example GitLab: 2
>>>
Block Quote
<<<
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="240" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Block Quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


GitLab block quotes formatting

```````````````````````````````` example GitLab: 3
>>>
Block Quote
>>>
Nested Block Quote
<<<
<<<
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="240" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Block Quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="480" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">Nested Block Quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


## Tables

All the fixings

```````````````````````````````` example Tables: 1
| Combined _header_                                        ||||
| Default         | Left **Align** | Center | Right ++Align++ |
|-----------------|:---------------|:------:|----------------:|
| Data 1 `Longer` | Data 2         | Data 3 |          Data 4 |
| Combined **data**               || Data 5 |          Data 6 |
| Data 7          | Combined _Data_        ||          Data 8 |
| Data 9          | Data 10        |     Combined ^Data^     ||
| Combined ~~Data~~                       |||          Data 7 |
| Data 8          | Combined ~Data~                         |||
[Table Caption]
.
<w:body>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">header</w:t>
                    </w:r>
                    <w:r>
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
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Align</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data 1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Longer</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Superscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Strikethrough"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Subscript"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table Caption</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


With breaks

```````````````````````````````` example Tables: 2
|Document / Item|versioning|
|:---------------------|:-------------|
|Technical Documentation, e.g.<br/> - Software Requirements<br/> - Software Architecture<br/> - Validation Plan|We use our versioning system to control changes to these (markdown) documents|
|Records such as completed checklist|See 'SOP' "Control of documents and records" (```DC-SOP01```)| 
|Development artifacts such as<br/> - source code<br/> - configuration files<br/> - Icons, graphics|We use our versioning system to control changes to these artifacts|
|SOUP|We use our versioning system to control changes to these artifacts|
[Table 4: Version control]

.
<w:body>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Document / Item</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">versioning</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Technical Documentation, e.g.</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Software Requirements</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Software Architecture</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Validation Plan</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">We use our versioning system to control changes to these (markdown) documents</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Records such as completed checklist</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">See 'SOP' "Control of documents and records" (</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">DC-SOP01</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve">)</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Development artifacts such as</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - source code</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - configuration files</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Icons, graphics</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">We use our versioning system to control changes to these artifacts</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">SOUP</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">We use our versioning system to control changes to these artifacts</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table 4: Version control</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


With breaks and caption before

```````````````````````````````` example(Tables: 3) options(caption-before)
|Document / Item|versioning|
|:---------------------|:-------------|
|Technical Documentation, e.g.<br/> - Software Requirements<br/> - Software Architecture<br/> - Validation Plan|We use our versioning system to control changes to these (markdown) documents|
|Records such as completed checklist|See 'SOP' "Control of documents and records" (```DC-SOP01```)| 
|Development artifacts such as<br/> - source code<br/> - configuration files<br/> - Icons, graphics|We use our versioning system to control changes to these artifacts|
|SOUP|We use our versioning system to control changes to these artifacts|
[Table 4: Version control]

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table 4: Version control</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Document / Item</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">versioning</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Technical Documentation, e.g.</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Software Requirements</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Software Architecture</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Validation Plan</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">We use our versioning system to control changes to these (markdown) documents</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Records such as completed checklist</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">See 'SOP' "Control of documents and records" (</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t xml:space="preserve">DC-SOP01</w:t>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve">)</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Development artifacts such as</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - source code</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - configuration files</w:t>
                    </w:r>
                    <w:r>
                        <w:br/>
                    </w:r>
                    <w:r>
                        <w:t xml:space="preserve"> - Icons, graphics</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">We use our versioning system to control changes to these artifacts</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">SOUP</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">We use our versioning system to control changes to these artifacts</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


With spanning columns

```````````````````````````````` example Tables: 4

| data   | label  |
|:-------|:-------|
| spanning       ||
| normal | column |

.
<w:body>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">label</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">spanning</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">normal</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">column</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


With spanning columns turned off

```````````````````````````````` example(Tables: 5) options(table-no-span)

| data   | label  |
|:-------|:-------|
| spanning       ||
| normal | column |

.
<w:body>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">label</w:t>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">spanning</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
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
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">normal</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">column</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


## Inheritance

Tests for inheritance of indentation and borders

block quote with bullet list

```````````````````````````````` example Inheritance: 1
> some quote
>
> * a list item 
> * another list item
>
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


block quote with bullet nested list

```````````````````````````````` example Inheritance: 2
> some quote
>
> * a list item 
>   * another list item
>
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="687" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


block quote with bullet list nested beyond border space limit

```````````````````````````````` example Inheritance: 3
> some quote
>
> * a list item 
>   * another list item
>     * another list item
>       * another list item
>         * another list item
>
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="687" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="6"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="907" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="7"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1147" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="8"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1367" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


block quote with ordered list

```````````````````````````````` example(Inheritance: 4) options(IGNORED)
> some quote
>
> 1. a list item 
> 1. another list item
>
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Inheritance: 5) options(IGNORED)
1. Table in a list

   | Simple Table |
   |--------------|
   | Data         |
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
            <w:t xml:space="preserve">Table in a list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="403"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Simple Table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


```````````````````````````````` example(Inheritance: 6) options(IGNORED)
> Table in quote
>
> | Simple Table |
> |--------------|
> | Data         |
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table in quote</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="360"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Simple Table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 7) options(IGNORED)
1. ordered list

   > with block quote
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
            <w:t xml:space="preserve">ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="523"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 8) options(IGNORED)
1. ordered list
   * bullet list
   
     > with block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ordered list</w:t>
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
            <w:t xml:space="preserve">bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 9) options(IGNORED)
 > with block quote
 >
 > text
 >
 > 1. with list
 > 
 >    text 
 > 
 > text 
 > 
 > * with list
 > 
 >   text 
 > 
 >   > block quote
 
1. ordered list
   * bullet list
   
     > with block quote
     >
     > text
     >
     > 1. with list
     > 
     >    list child text
     > 
     > text 
     > 
     > * with list
     > 
     >   list child text                                                                                                                                
     > 
     >   > last block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="23" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="520" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="460" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="700"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="8"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="977" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="23" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="974" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">list child text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="9"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="921" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="914" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">list child text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="1154"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">last block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 10) options(IGNORED)
1. ordered list
   * bullet list
     > with block quote
     > 1. with list
     > 
     > | Simple Table |
     > |--------------|
     > | Data         |
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="977" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="814"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Simple Table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


aside block with bullet list

```````````````````````````````` example Inheritance: 11
| some quote
|
| * a list item 
| * another list item
|
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


aside block with bullet nested list

```````````````````````````````` example Inheritance: 12
| some quote
|
| * a list item 
|   * another list item
|
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="687" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


aside block with bullet list nested beyond border space limit

```````````````````````````````` example Inheritance: 13
| some quote
|
| * a list item 
|   * another list item
|     * another list item
|       * another list item
|         * another list item
|
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="687" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="6"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="907" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="7"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1147" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="8"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="31" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="1367" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


aside block with ordered list

```````````````````````````````` example(Inheritance: 14) options(IGNORED)
| some quote
|
| 1. a list item 
| 1. another list item
|
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Inheritance: 15) options(IGNORED)
1. Table in a list

   | Simple Table |
   |--------------|
   | Data         |
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
            <w:t xml:space="preserve">Table in a list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="403"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Simple Table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
````````````````````````````````


```````````````````````````````` example(Inheritance: 16) options(IGNORED)
| Table in quote
|
| | Simple Table |
| |--------------|
| | Data         |
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table in quote</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="360"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Simple Table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 17) options(IGNORED)
1. ordered list

   | with aside block
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
            <w:t xml:space="preserve">ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="523"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 18) options(IGNORED)
1. ordered list
   * bullet list
   
     | with aside block
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ordered list</w:t>
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
            <w:t xml:space="preserve">bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 19) options(IGNORED)
 | with aside block
 |
 | text
 |
 | 1. with list
 | 
 |    text 
 | 
 | text 
 | 
 | * with list
 | 
 |   text 
 | 
 |   | aside block
 
1. ordered list
   * bullet list
   
     | with aside block
     |
     | text
     |
     | 1. with list
     | 
     |    list child text
     | 
     | text 
     | 
     | * with list
     | 
     |   list child text                                                                                                                                
     | 
     |   | last aside block
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="523" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="23" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="520" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="5"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="467" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="460" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="700"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="8"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="977" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="23" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="974" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list child text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="9"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="227" w:left="921" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="20" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="914" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list child text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="1154"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">last aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 20) options(IGNORED)
1. ordered list
   * bullet list
     | with aside block
     | 1. with list
     | 
     | | Simple Table |
     | |--------------|
     | | Data         |
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="5"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">bullet list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="694"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:hanging="283" w:left="977" w:right="0"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="814"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Simple Table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


## Footnotes

```````````````````````````````` example Footnotes: 1
Paragraph text[^1]

[^1]: Footnote text

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Paragraph text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Footnote text</w:t>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Footnotes: 2
Paragraph text[^1]

[^1]: Footnote text that will be wrapped and should have a hanging indent to align the overflow
      to the level of the text after the footnote anchor at the left margin.

      * list as part of footnote
      * another item
      
      | table |
      |-------|
      | data  |

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Paragraph text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Footnote text that will be wrapped and should have a hanging indent to align the overflow</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">to the level of the text after the footnote anchor at the left margin.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:ind w:hanging="227" w:left="467"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="20"/>
                <w:szCs w:val="20"/>
            </w:rPr>
            <w:t xml:space="preserve">list as part of footnote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
            <w:ind w:hanging="227" w:left="467"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="20"/>
                <w:szCs w:val="20"/>
            </w:rPr>
            <w:t xml:space="preserve">another item</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="360"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Footnotes: 3
Paragraph text[^1]

Paragraph text, repeated footnote[^1]

[^1]: Footnote text that will be wrapped and should have a hanging indent to align the overflow
      to the level of the text after the footnote anchor at the left margin.

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Paragraph text</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="1"/>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Paragraph text, repeated footnote</w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteReference w:id="2"/>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="1">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Footnote text that will be wrapped and should have a hanging indent to align the overflow</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">to the level of the text after the footnote anchor at the left margin.</w:t>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="2">
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Footnote"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="FootnoteReference"/>
            </w:rPr>
            <w:footnoteRef/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">→</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Footnote text that will be wrapped and should have a hanging indent to align the overflow</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">to the level of the text after the footnote anchor at the left margin.</w:t>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## TOC

```````````````````````````````` example(TOC: 1) options(IGNORED)
[TOC] 

# Heading **some bold** 1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.

Quisque dapibus sit amet nunc ac pulvinar. Aenean lacus augue, vehicula id tellus sit amet, congue pellentesque felis. Aenean in ipsum ligula. Ut finibus laoreet risus eu egestas. Aenean et diam eget arcu luctus venenatis sed et elit. Sed porta ipsum quis varius facilisis. Sed at neque ex. Duis vel sapien eleifend, volutpat enim sit amet, elementum nulla. Sed fermentum ullamcorper tempor. Pellentesque nec ante aliquet, pulvinar risus non, efficitur magna. Duis ut ornare nisl. In vel blandit eros. Duis eget rhoncus nisi. Etiam nec justo id eros sodales convallis eget sit amet metus.

Mauris luctus gravida risus, ac iaculis tellus varius at. Nullam vulputate ullamcorper risus vel ultricies. Quisque nec purus sit amet est consectetur suscipit. Pellentesque volutpat orci mauris, vitae pretium mi faucibus in. Proin pretium id est rhoncus congue. In tellus purus, gravida nec egestas non, auctor vitae tortor. Proin dolor nisl, placerat quis egestas in, ornare sit amet lectus. Nullam sem lorem, venenatis sed ipsum vel, egestas convallis ligula. Vestibulum vel finibus leo. Proin ullamcorper vulputate nibh eget pharetra. Etiam enim ipsum, vestibulum at ultrices ac, bibendum eu mauris. Sed eu tellus porta, feugiat elit ac, faucibus nunc. Duis sollicitudin tristique augue eget suscipit. Cras dignissim arcu ac porta ornare. Mauris dignissim nisl eu mattis malesuada. Proin feugiat est non eros hendrerit, sed congue ligula convallis.

## Heading 1.1 _some italic_

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 1.1.1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 1.1.2  **_some bold italic_**

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.

## Heading 1.2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

## Heading 1.3

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

# Heading 2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 2.0.1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 2.0.2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

.
<w:body>
    <w:sdt>
        <w:sdtPr>
            <w:docPartObj>
                <w:docPartGallery w:val="Table of Contents"/>
                <w:docPartUnique/>
            </w:docPartObj>
            <w:id w:val="648305731"/>
        </w:sdtPr>
        <w:sdtContent>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOCHeading"/>
                </w:pPr>
                <w:r>
                    <w:t>Contents</w:t>
                </w:r>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC1"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:r>
                    <w:fldChar w:fldCharType="begin"/>
                </w:r>
                <w:r>
                    <w:instrText xml:space="preserve">TOC \o "1-3" \h \z \u</w:instrText>
                </w:r>
                <w:r>
                    <w:fldChar w:fldCharType="separate"/>
                </w:r>
                <w:hyperlink w:anchor="_Toc43547810">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some bold</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve"> 1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547810 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547811">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some italic</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547811 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547812">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1.1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547812 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547813">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1.2  </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some bold italic</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547813 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547814">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547814 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547815">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.3</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547815 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC1"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547816">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547816 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547817">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2.0.1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547817 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc43547818">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2.0.2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc43547818 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:r>
                    <w:fldChar w:fldCharType="end"/>
                </w:r>
            </w:p>
        </w:sdtContent>
    </w:sdt>
    <w:bookmarkStart w:id="1" w:name="heading-some-bold-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="10" w:name="_Toc43547810"/>
        <w:r>
            <w:t xml:space="preserve">Heading </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> 1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="10"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Quisque dapibus sit amet nunc ac pulvinar. Aenean lacus augue, vehicula id tellus sit amet, congue pellentesque felis. Aenean in ipsum ligula. Ut finibus laoreet risus eu egestas. Aenean et diam eget arcu luctus venenatis sed et elit. Sed porta ipsum quis varius facilisis. Sed at neque ex. Duis vel sapien eleifend, volutpat enim sit amet, elementum nulla. Sed fermentum ullamcorper tempor. Pellentesque nec ante aliquet, pulvinar risus non, efficitur magna. Duis ut ornare nisl. In vel blandit eros. Duis eget rhoncus nisi. Etiam nec justo id eros sodales convallis eget sit amet metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Mauris luctus gravida risus, ac iaculis tellus varius at. Nullam vulputate ullamcorper risus vel ultricies. Quisque nec purus sit amet est consectetur suscipit. Pellentesque volutpat orci mauris, vitae pretium mi faucibus in. Proin pretium id est rhoncus congue. In tellus purus, gravida nec egestas non, auctor vitae tortor. Proin dolor nisl, placerat quis egestas in, ornare sit amet lectus. Nullam sem lorem, venenatis sed ipsum vel, egestas convallis ligula. Vestibulum vel finibus leo. Proin ullamcorper vulputate nibh eget pharetra. Etiam enim ipsum, vestibulum at ultrices ac, bibendum eu mauris. Sed eu tellus porta, feugiat elit ac, faucibus nunc. Duis sollicitudin tristique augue eget suscipit. Cras dignissim arcu ac porta ornare. Mauris dignissim nisl eu mattis malesuada. Proin feugiat est non eros hendrerit, sed congue ligula convallis.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-11-some-italic"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="11" w:name="_Toc43547811"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some italic</w:t>
        </w:r>
        <w:bookmarkEnd w:id="11"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-111"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="12" w:name="_Toc43547812"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1.1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="12"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-112--some-bold-italic"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="13" w:name="_Toc43547813"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1.2  </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some bold italic</w:t>
        </w:r>
        <w:bookmarkEnd w:id="13"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-12"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="14" w:name="_Toc43547814"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="14"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-13"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="15" w:name="_Toc43547815"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.3</w:t>
        </w:r>
        <w:bookmarkEnd w:id="15"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="7" w:name="heading-2"/>
    <w:bookmarkEnd w:id="7"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="16" w:name="_Toc43547816"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="16"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="8" w:name="heading-201"/>
    <w:bookmarkEnd w:id="8"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="17" w:name="_Toc43547817"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2.0.1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="17"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="9" w:name="heading-202"/>
    <w:bookmarkEnd w:id="9"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="18" w:name="_Toc43547818"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2.0.2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="18"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


sim toc

```````````````````````````````` example(TOC: 2) options(IGNORED)
[TOC]: # 


# Heading **some bold** 1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.

Quisque dapibus sit amet nunc ac pulvinar. Aenean lacus augue, vehicula id tellus sit amet, congue pellentesque felis. Aenean in ipsum ligula. Ut finibus laoreet risus eu egestas. Aenean et diam eget arcu luctus venenatis sed et elit. Sed porta ipsum quis varius facilisis. Sed at neque ex. Duis vel sapien eleifend, volutpat enim sit amet, elementum nulla. Sed fermentum ullamcorper tempor. Pellentesque nec ante aliquet, pulvinar risus non, efficitur magna. Duis ut ornare nisl. In vel blandit eros. Duis eget rhoncus nisi. Etiam nec justo id eros sodales convallis eget sit amet metus.

Mauris luctus gravida risus, ac iaculis tellus varius at. Nullam vulputate ullamcorper risus vel ultricies. Quisque nec purus sit amet est consectetur suscipit. Pellentesque volutpat orci mauris, vitae pretium mi faucibus in. Proin pretium id est rhoncus congue. In tellus purus, gravida nec egestas non, auctor vitae tortor. Proin dolor nisl, placerat quis egestas in, ornare sit amet lectus. Nullam sem lorem, venenatis sed ipsum vel, egestas convallis ligula. Vestibulum vel finibus leo. Proin ullamcorper vulputate nibh eget pharetra. Etiam enim ipsum, vestibulum at ultrices ac, bibendum eu mauris. Sed eu tellus porta, feugiat elit ac, faucibus nunc. Duis sollicitudin tristique augue eget suscipit. Cras dignissim arcu ac porta ornare. Mauris dignissim nisl eu mattis malesuada. Proin feugiat est non eros hendrerit, sed congue ligula convallis.

## Heading 1.1 _some italic_

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 1.1.1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 1.1.2  **_some bold italic_**

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.

## Heading 1.2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

## Heading 1.3

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

# Heading 2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 2.0.1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 2.0.2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

.
<w:body>
    <w:sdt>
        <w:sdtPr>
            <w:docPartObj>
                <w:docPartGallery w:val="Table of Contents"/>
                <w:docPartUnique/>
            </w:docPartObj>
            <w:id w:val="614570707"/>
        </w:sdtPr>
        <w:sdtContent>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOCHeading"/>
                </w:pPr>
                <w:r>
                    <w:t>Contents</w:t>
                </w:r>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC1"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:r>
                    <w:fldChar w:fldCharType="begin"/>
                </w:r>
                <w:r>
                    <w:instrText xml:space="preserve">TOC \o "1-3" \h \z \u</w:instrText>
                </w:r>
                <w:r>
                    <w:fldChar w:fldCharType="separate"/>
                </w:r>
                <w:hyperlink w:anchor="_Toc91458510">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some bold</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve"> 1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458510 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458511">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some italic</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458511 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458512">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1.1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458512 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458513">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1.2  </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some bold italic</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458513 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458514">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458514 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458515">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.3</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458515 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC1"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458516">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458516 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458517">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2.0.1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458517 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc91458518">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2.0.2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc91458518 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:r>
                    <w:fldChar w:fldCharType="end"/>
                </w:r>
            </w:p>
        </w:sdtContent>
    </w:sdt>
    <w:bookmarkStart w:id="1" w:name="heading-some-bold-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="10" w:name="_Toc91458510"/>
        <w:r>
            <w:t xml:space="preserve">Heading </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> 1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="10"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Quisque dapibus sit amet nunc ac pulvinar. Aenean lacus augue, vehicula id tellus sit amet, congue pellentesque felis. Aenean in ipsum ligula. Ut finibus laoreet risus eu egestas. Aenean et diam eget arcu luctus venenatis sed et elit. Sed porta ipsum quis varius facilisis. Sed at neque ex. Duis vel sapien eleifend, volutpat enim sit amet, elementum nulla. Sed fermentum ullamcorper tempor. Pellentesque nec ante aliquet, pulvinar risus non, efficitur magna. Duis ut ornare nisl. In vel blandit eros. Duis eget rhoncus nisi. Etiam nec justo id eros sodales convallis eget sit amet metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Mauris luctus gravida risus, ac iaculis tellus varius at. Nullam vulputate ullamcorper risus vel ultricies. Quisque nec purus sit amet est consectetur suscipit. Pellentesque volutpat orci mauris, vitae pretium mi faucibus in. Proin pretium id est rhoncus congue. In tellus purus, gravida nec egestas non, auctor vitae tortor. Proin dolor nisl, placerat quis egestas in, ornare sit amet lectus. Nullam sem lorem, venenatis sed ipsum vel, egestas convallis ligula. Vestibulum vel finibus leo. Proin ullamcorper vulputate nibh eget pharetra. Etiam enim ipsum, vestibulum at ultrices ac, bibendum eu mauris. Sed eu tellus porta, feugiat elit ac, faucibus nunc. Duis sollicitudin tristique augue eget suscipit. Cras dignissim arcu ac porta ornare. Mauris dignissim nisl eu mattis malesuada. Proin feugiat est non eros hendrerit, sed congue ligula convallis.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-11-some-italic"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="11" w:name="_Toc91458511"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some italic</w:t>
        </w:r>
        <w:bookmarkEnd w:id="11"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-111"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="12" w:name="_Toc91458512"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1.1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="12"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-112--some-bold-italic"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="13" w:name="_Toc91458513"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1.2  </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some bold italic</w:t>
        </w:r>
        <w:bookmarkEnd w:id="13"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-12"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="14" w:name="_Toc91458514"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="14"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-13"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="15" w:name="_Toc91458515"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.3</w:t>
        </w:r>
        <w:bookmarkEnd w:id="15"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="7" w:name="heading-2"/>
    <w:bookmarkEnd w:id="7"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="16" w:name="_Toc91458516"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="16"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="8" w:name="heading-201"/>
    <w:bookmarkEnd w:id="8"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="17" w:name="_Toc91458517"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2.0.1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="17"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="9" w:name="heading-202"/>
    <w:bookmarkEnd w:id="9"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="18" w:name="_Toc91458518"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2.0.2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="18"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


sim toc

```````````````````````````````` example(TOC: 3) options(IGNORED)
[TOC]: #


# Heading **some bold** 1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.

Quisque dapibus sit amet nunc ac pulvinar. Aenean lacus augue, vehicula id tellus sit amet, congue pellentesque felis. Aenean in ipsum ligula. Ut finibus laoreet risus eu egestas. Aenean et diam eget arcu luctus venenatis sed et elit. Sed porta ipsum quis varius facilisis. Sed at neque ex. Duis vel sapien eleifend, volutpat enim sit amet, elementum nulla. Sed fermentum ullamcorper tempor. Pellentesque nec ante aliquet, pulvinar risus non, efficitur magna. Duis ut ornare nisl. In vel blandit eros. Duis eget rhoncus nisi. Etiam nec justo id eros sodales convallis eget sit amet metus.

Mauris luctus gravida risus, ac iaculis tellus varius at. Nullam vulputate ullamcorper risus vel ultricies. Quisque nec purus sit amet est consectetur suscipit. Pellentesque volutpat orci mauris, vitae pretium mi faucibus in. Proin pretium id est rhoncus congue. In tellus purus, gravida nec egestas non, auctor vitae tortor. Proin dolor nisl, placerat quis egestas in, ornare sit amet lectus. Nullam sem lorem, venenatis sed ipsum vel, egestas convallis ligula. Vestibulum vel finibus leo. Proin ullamcorper vulputate nibh eget pharetra. Etiam enim ipsum, vestibulum at ultrices ac, bibendum eu mauris. Sed eu tellus porta, feugiat elit ac, faucibus nunc. Duis sollicitudin tristique augue eget suscipit. Cras dignissim arcu ac porta ornare. Mauris dignissim nisl eu mattis malesuada. Proin feugiat est non eros hendrerit, sed congue ligula convallis.

## Heading 1.1 _some italic_

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 1.1.1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 1.1.2  **_some bold italic_**

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.

## Heading 1.2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

## Heading 1.3

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

# Heading 2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 2.0.1

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

### Heading 2.0.2

Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.

Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.

.
<w:body>
    <w:sdt>
        <w:sdtPr>
            <w:docPartObj>
                <w:docPartGallery w:val="Table of Contents"/>
                <w:docPartUnique/>
            </w:docPartObj>
            <w:id w:val="1309416653"/>
        </w:sdtPr>
        <w:sdtContent>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOCHeading"/>
                </w:pPr>
                <w:r>
                    <w:t>Contents</w:t>
                </w:r>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC1"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:r>
                    <w:fldChar w:fldCharType="begin"/>
                </w:r>
                <w:r>
                    <w:instrText xml:space="preserve">TOC \o "1-3" \h \z \u</w:instrText>
                </w:r>
                <w:r>
                    <w:fldChar w:fldCharType="separate"/>
                </w:r>
                <w:hyperlink w:anchor="_Toc8638610">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:b/>
                            <w:bCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some bold</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve"> 1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638610 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638611">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1 </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some italic</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638611 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638612">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1.1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638612 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638613">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.1.2  </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                            <w:rFonts w:ascii="Liberation Serif"
                                w:cs="Lucida Sans" w:eastAsia="SimSun" w:hAnsi="Liberation Serif"/>
                            <w:i/>
                            <w:iCs/>
                        </w:rPr>
                        <w:t xml:space="preserve">some bold italic</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638613 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638614">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638614 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC2"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638615">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 1.3</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638615 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC1"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638616">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638616 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638617">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2.0.1</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638617 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:pPr>
                    <w:pStyle w:val="TOC3"/>
                    <w:tabs>
                        <w:tab w:leader="dot" w:pos="9972" w:val="right"/>
                    </w:tabs>
                    <w:rPr>
                        <w:noProof/>
                    </w:rPr>
                </w:pPr>
                <w:hyperlink w:anchor="_Toc8638618">
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Hyperlink"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Heading 2.0.2</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:tab/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="begin"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:instrText xml:space="preserve">PAGEREF _Toc8638618 \h</w:instrText>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="separate"/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:noProof/>
                            <w:webHidden/>
                        </w:rPr>
                        <w:fldChar w:fldCharType="end"/>
                    </w:r>
                </w:hyperlink>
            </w:p>
            <w:p>
                <w:r>
                    <w:fldChar w:fldCharType="end"/>
                </w:r>
            </w:p>
        </w:sdtContent>
    </w:sdt>
    <w:bookmarkStart w:id="1" w:name="heading-some-bold-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="10" w:name="_Toc8638610"/>
        <w:r>
            <w:t xml:space="preserve">Heading </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> 1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="10"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Quisque dapibus sit amet nunc ac pulvinar. Aenean lacus augue, vehicula id tellus sit amet, congue pellentesque felis. Aenean in ipsum ligula. Ut finibus laoreet risus eu egestas. Aenean et diam eget arcu luctus venenatis sed et elit. Sed porta ipsum quis varius facilisis. Sed at neque ex. Duis vel sapien eleifend, volutpat enim sit amet, elementum nulla. Sed fermentum ullamcorper tempor. Pellentesque nec ante aliquet, pulvinar risus non, efficitur magna. Duis ut ornare nisl. In vel blandit eros. Duis eget rhoncus nisi. Etiam nec justo id eros sodales convallis eget sit amet metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Mauris luctus gravida risus, ac iaculis tellus varius at. Nullam vulputate ullamcorper risus vel ultricies. Quisque nec purus sit amet est consectetur suscipit. Pellentesque volutpat orci mauris, vitae pretium mi faucibus in. Proin pretium id est rhoncus congue. In tellus purus, gravida nec egestas non, auctor vitae tortor. Proin dolor nisl, placerat quis egestas in, ornare sit amet lectus. Nullam sem lorem, venenatis sed ipsum vel, egestas convallis ligula. Vestibulum vel finibus leo. Proin ullamcorper vulputate nibh eget pharetra. Etiam enim ipsum, vestibulum at ultrices ac, bibendum eu mauris. Sed eu tellus porta, feugiat elit ac, faucibus nunc. Duis sollicitudin tristique augue eget suscipit. Cras dignissim arcu ac porta ornare. Mauris dignissim nisl eu mattis malesuada. Proin feugiat est non eros hendrerit, sed congue ligula convallis.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-11-some-italic"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="11" w:name="_Toc8638611"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some italic</w:t>
        </w:r>
        <w:bookmarkEnd w:id="11"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-111"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="12" w:name="_Toc8638612"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1.1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="12"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-112--some-bold-italic"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="13" w:name="_Toc8638613"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.1.2  </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t xml:space="preserve">some bold italic</w:t>
        </w:r>
        <w:bookmarkEnd w:id="13"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Fusce eget sagittis lorem. Quisque quis blandit ante. Nunc neque erat, mollis ut sem efficitur, luctus tempor turpis. Nunc a nisi cursus, faucibus tortor at, ornare lectus. Nullam nibh magna, rutrum eu tincidunt ut, iaculis at augue. In congue ligula pulvinar purus tincidunt, in placerat magna interdum. Nunc mollis arcu vel metus dapibus lacinia.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="5" w:name="heading-12"/>
    <w:bookmarkEnd w:id="5"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="14" w:name="_Toc8638614"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="14"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="6" w:name="heading-13"/>
    <w:bookmarkEnd w:id="6"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="15" w:name="_Toc8638615"/>
        <w:r>
            <w:t xml:space="preserve">Heading 1.3</w:t>
        </w:r>
        <w:bookmarkEnd w:id="15"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="7" w:name="heading-2"/>
    <w:bookmarkEnd w:id="7"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="16" w:name="_Toc8638616"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="16"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="8" w:name="heading-201"/>
    <w:bookmarkEnd w:id="8"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="17" w:name="_Toc8638617"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2.0.1</w:t>
        </w:r>
        <w:bookmarkEnd w:id="17"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="9" w:name="heading-202"/>
    <w:bookmarkEnd w:id="9"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:bookmarkStart w:id="18" w:name="_Toc8638618"/>
        <w:r>
            <w:t xml:space="preserve">Heading 2.0.2</w:t>
        </w:r>
        <w:bookmarkEnd w:id="18"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque ex nunc, interdum et volutpat hendrerit, consectetur in leo. Vivamus elit urna, interdum id ullamcorper non, pulvinar eget ex. Nunc non sodales ligula. Praesent tincidunt dapibus eleifend. Pellentesque fringilla placerat luctus. Duis dictum ac leo at iaculis. Praesent a cursus dolor, et dapibus tellus. Fusce porttitor tristique gravida.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Proin maximus ante ac justo rutrum consectetur. Praesent ultrices in lacus id faucibus. Praesent consectetur lorem lorem, ac imperdiet orci pharetra in. Nulla placerat neque a neque ultrices, vitae auctor nisi fermentum. Donec et odio non ipsum ultricies dictum euismod sed urna. Maecenas maximus tellus tempor felis sagittis pulvinar. Maecenas bibendum, purus egestas accumsan porta, ipsum turpis viverra lorem, a gravida nibh ipsum convallis nibh. Aliquam at ante ligula. Quisque et dolor eget magna mollis blandit. Quisque in enim finibus, elementum nunc sit amet, blandit est. Donec consectetur urna in turpis pulvinar efficitur. Vivamus molestie massa id tortor cursus, eu auctor metus tincidunt. Aenean dictum turpis in convallis consectetur. Nam dui nisl, consequat non tincidunt eu, hendrerit at tellus. Pellentesque sit amet diam et leo sollicitudin blandit eu eget metus.</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


## Enumerated References

```````````````````````````````` example(Enumerated References: 1) options(IGNORED)
![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo"){#fig:test}  
[#fig:test]

![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo"){#fig:test2}  
[#fig:test2]

| table |
|-------|
| data  |
[[#tbl:test] caption]
{#tbl:test}

See [@fig:test2]

See [@fig:test]

See [@tbl:test]


[@fig]: Figure [#].

[@tbl]: Table [#].

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:bookmarkStart w:id="1" w:name="fig:test"/>
        <w:r>
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
        <w:bookmarkEnd w:id="1"/>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Figure </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">1</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">.</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:bookmarkStart w:id="2" w:name="fig:test2"/>
        <w:r>
            <w:drawing>
                <wp:inline distB="0" distL="0" distR="0" distT="0">
                    <wp:extent cx="541871" cy="541871"/>
                    <wp:effectExtent b="0" l="0" r="0" t="0"/>
                    <wp:docPr descr="flexmark-icon-logo" id="2" name="Image2"/>
                    <wp:cNvGraphicFramePr>
                        <a:graphicFrameLocks noChangeAspect="true"/>
                    </wp:cNvGraphicFramePr>
                    <a:graphic>
                        <a:graphicData uri="http://schemas.openxmlformats.org/drawingml/2006/picture">
                            <pic:pic>
                                <pic:nvPicPr>
                                    <pic:cNvPr id="3" name="Image2"/>
                                    <pic:cNvPicPr/>
                                </pic:nvPicPr>
                                <pic:blipFill>
                                    <a:blip r:embed="rId4"/>
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
        <w:bookmarkEnd w:id="2"/>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">Figure </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">2</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">.</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="tbl:test"/>
    <w:bookmarkEnd w:id="3"/>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">1</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">.</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> caption</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">See </w:t>
        </w:r>
        <w:hyperlink w:anchor="fig:test2" w:tooltip="Figure 2.">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "#fig:test2" \o "Figure 2." </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Figure </w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">2</w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">.</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">See </w:t>
        </w:r>
        <w:hyperlink w:anchor="fig:test" w:tooltip="Figure 1.">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "#fig:test" \o "Figure 1." </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Figure </w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">1</w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">.</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">See </w:t>
        </w:r>
        <w:hyperlink w:anchor="tbl:test" w:tooltip="Table 1.">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "#tbl:test" \o "Table 1." </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Table </w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">1</w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">.</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Enumerated References: 2

{#abc}

[@fig]: Figure [#].

[@tbl]: Table [#].
.
<w:body/>
````````````````````````````````


test with suffix

```````````````````````````````` example(Enumerated References: 3) options(hyperlink-suffix)

test{#fig:abc}

See [@fig:abc]

[@fig]: Figure [#].

[@tbl]: Table [#].
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:bookmarkStart w:id="1" w:name="fig:abc"/>
        <w:r>
            <w:t xml:space="preserve">test</w:t>
        </w:r>
        <w:bookmarkEnd w:id="1"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">See </w:t>
        </w:r>
        <w:hyperlink w:anchor="fig:abc_1" w:tooltip="Figure 1.">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "#fig:abc_1" \o "Figure 1." </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Figure </w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">1</w:t>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">.</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example Enumerated References: 4

| table |
|-------|
| data  |
[[#tbl:test] caption]
{#tbl:test}

[@fig]: Figure [#].

[@tbl]: Table [#].
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="tbl:test"/>
    <w:bookmarkEnd w:id="1"/>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="120"/>
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
                    <w:shd w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">table</w:t>
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
                    </w:pPr>
                    <w:r>
                        <w:t xml:space="preserve">data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Table </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">1</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">.</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> caption</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


missing definition

```````````````````````````````` example Enumerated References: 5
abc{#fig:test}

[#fig:test]

[@fig:test]

.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:bookmarkStart w:id="1" w:name="fig:test"/>
        <w:r>
            <w:t xml:space="preserve">abc</w:t>
        </w:r>
        <w:bookmarkEnd w:id="1"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="fig:test" w:tooltip="fig 1">
            <w:r>
                <w:fldChar w:fldCharType="begin"/>
            </w:r>
            <w:r>
                <w:instrText xml:space="preserve"> HYPERLINK "#fig:test" \o "fig 1" </w:instrText>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="separate"/>
            </w:r>
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">fig 1</w:t>
            </w:r>
            <w:r>
                <w:fldChar w:fldCharType="end"/>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


## Heading and Link Ids

Suffix links

```````````````````````````````` example(Heading and Link Ids: 1) options(hyperlink-suffix)
# Heading 1

## Heading 2

[Top](#heading-1) 
[Heading 2](#heading-2) 
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="heading-1_1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Top</w:t>
            </w:r>
        </w:hyperlink>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:hyperlink w:anchor="heading-2_1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Heading 2</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


Suffix first heading links

```````````````````````````````` example(Heading and Link Ids: 2) options(IGNORE, heading-id-suffix)
# Heading 1

## Heading 2

[Top](#heading-1) 
[Heading 2](#heading-2) 
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1_1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="heading-1_1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Top</w:t>
            </w:r>
        </w:hyperlink>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:hyperlink w:anchor="heading-2">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Heading 2</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


Suffix hyperlinks and first heading links

```````````````````````````````` example(Heading and Link Ids: 3) options(IGNORE, hyperlink-suffix, heading-id-suffix)
# Heading 1

## Heading 2

[Top](#heading-1) 
[Heading 2](#heading-2) 
.
<w:body>
    <w:bookmarkStart w:id="1" w:name="heading-1_1"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink w:anchor="heading-1_1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Top</w:t>
            </w:r>
        </w:hyperlink>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:hyperlink w:anchor="heading-2_1">
            <w:r>
                <w:rPr>
                    <w:rStyle w:val="Hyperlink"/>
                </w:rPr>
                <w:t xml:space="preserve">Heading 2</w:t>
            </w:r>
        </w:hyperlink>
    </w:p>
</w:body>
````````````````````````````````


## Aside

lazy continuation

```````````````````````````````` example Aside: 1
* list item
    | aside block
    lazy continuation
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="BodyText"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="4"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="467"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">lazy continuation</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with hyperlink

```````````````````````````````` example Aside: 2
| [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with hard break

```````````````````````````````` example Aside: 3
| aside block 1  
| with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block 1</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child paragraphs

```````````````````````````````` example Aside: 4
| aside block 1  
|
| with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child aside blocks

```````````````````````````````` example Aside: 5
| aside block 1  
|
|| another aside block
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child block quote

```````````````````````````````` example Aside: 6
| aside block 1
|
|> block quote
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


as child of block quote

```````````````````````````````` example Aside: 7
> block quote 1  
>
>| aside block
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
            </w:rPr>
            <w:t xml:space="preserve">aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


with child paragraphs

```````````````````````````````` example Aside: 8
| aside block 1  
|
| with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
|| another aside block
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">aside block 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId3" w:tooltip="Title: flexmark-java logo">
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
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="480"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">another aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
````````````````````````````````


