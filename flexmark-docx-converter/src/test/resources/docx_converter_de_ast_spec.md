---
title: DocxConverter Extension Spec
author: Vladimir Schneider
version: 1.0
date: '2017-09-06'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# DocxConverter

Converts markdown to docx

## Paragraphs

basic text

```````````````````````````````` example Paragraphs: 1
plain text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>plain text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 2
plain **Bold** text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>Bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 3
plain *Italic* text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>Italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 4
plain ~~strike-through~~ text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 5
plain ~subscript~ text H~2~O
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>O</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 6
plain ^superscript^ text e^*i*pi^ = -1
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
.
Document[0, 38]
  Paragraph[0, 38]
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


```````````````````````````````` example Paragraphs: 7
plain ^superscript^ text e^ipi^ = -1
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 8
plain ++underline++ text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Paragraphs: 9
plain `inline code` text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 10) options(highlight-code)
plain `inline code` highlight text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="800000"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="auto" w:space="0" w:sz="0" w:val="none"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Paragraphs: 11) options(highlight-shade)
plain `inline code` highlight text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">plain </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:color w:val="800000"/>
                <w:highlight w:val="yellow"/>
                <w:bdr w:color="auto" w:space="0" w:sz="0" w:val="none"/>
            </w:rPr>
            <w:t>inline code</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> highlight text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


permutations of formatting

```````````````````````````````` example Paragraphs: 12
plain **Bold *Bold-Italic*** text
plain *Italic **Bold-Italic*** text
plain **Bold ++Bold-Underline++** text
plain **Bold ^Bold-Superscript^** text e^*i*𝛑^ = -1
plain **Bold ~Bold-Subscript~** text H~2~O
plain **Bold ~~Bold-strike-through~~** text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            </w:rPr>
            <w:t xml:space="preserve">Italic </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
                <w:i/>
                <w:iCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
                <w:b/>
                <w:bCs/>
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
            </w:rPr>
            <w:t xml:space="preserve">Bold </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
                <w:b/>
                <w:bCs/>
            </w:rPr>
            <w:t>Bold-strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Lists

```````````````````````````````` example Lists: 1
* list 1

  with some text

  * list 2
  
    with some text

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="360"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


Numbering start

```````````````````````````````` example(Lists: 2) options(IGNORED)
2. list 1

   with some text

   3. list 2
  
      with some text

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="360"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


tight lists

```````````````````````````````` example Lists: 3
* list 1 
  * list 1.1
  * list 1.2
* list 2 
  * list 2.1
  * list 2.2
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1.2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2.2</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


loose lists

```````````````````````````````` example Lists: 4
* list 1 
  * list 1.1
  * list 1.2
  
* list 2 
  * list 2.1
  * list 2.2

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1.2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2.2</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


loose lists

```````````````````````````````` example Lists: 5
* list 1 
  * list 1.1
  
  * list 1.2
  
* list 2 
  * list 2.1
  
  * list 2.2

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1.2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2.1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2.2</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


full list items

```````````````````````````````` example Lists: 6
* list 1 
  * list 2
    * list 3
      * list 4
        * list 5
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 5</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


full list items

```````````````````````````````` example Lists: 7
1. list 1 
   1. list 2
      1. list 3
         1. list 4
            1. list 5
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="43"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="44"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="45"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 5</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Links

Web URL

```````````````````````````````` example Links: 1
[flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:hyperlink r:id="rId10" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Images

Web URL

```````````````````````````````` example(Images: 1) options(IGNORED)
![flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
                                    <a:blip r:embed="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


Local File

```````````````````````````````` example(Images: 2) options(IGNORED)
![flexmark-icon-logo](file:///Users/vlad/src/projects/flexmark-java/assets/images/flexmark-icon-logo@2x.png "Title: flexmark-java logo") 

with some text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
                                    <a:blip r:embed="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


Relative Path

```````````````````````````````` example(Images: 3) options(url, IGNORED)
![flexmark-icon-logo](assets/images/flexmark-icon-logo@2x.png) 

with some text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
                                    <a:blip r:embed="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


Root Path

```````````````````````````````` example(Images: 4) options(url, IGNORED)
![flexmark-icon-logo](/images/flexmark-icon-logo@2x.png) 

with some text
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
                                    <a:blip r:embed="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Block Quotes

lazy continuation

```````````````````````````````` example Block Quotes: 1
* list item
    > block quote
    lazy continuation
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item</w:t>
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
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:t>block quote</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:t>lazy continuation</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


with hyperlink

```````````````````````````````` example Block Quotes: 2
> [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        <w:hyperlink r:id="rId10" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


with hard break

```````````````````````````````` example Block Quotes: 3
> block quote 1  
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>block quote 1</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId10" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


with child paragraphs

```````````````````````````````` example Block Quotes: 4
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId10" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


with child block quote

```````````````````````````````` example Block Quotes: 5
> block quote 1  
>
>> another block quote
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>block quote 1</w:t>
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
            <w:t>another block quote</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


with child paragraphs

```````````````````````````````` example Block Quotes: 6
> block quote 1  
>
> with a link [flexmark-icon-logo](https://raw.githubusercontent.com/vsch/flexmark-java/master/assets/images/flexmark-icon-logo%402x.png "Title: flexmark-java logo") 
>> another block quote
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId10" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
            <w:t>another block quote</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Fenced Code

```````````````````````````````` example Fenced Code: 1
```
pre-formatted code
code
```

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t>code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="240" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example Fenced Code: 2
text before

```
pre-formatted code
code
```

text after
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>text before</w:t>
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
            <w:t>pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t>code</w:t>
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
            <w:t>text after</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## HTML

```````````````````````````````` example HTML: 1
<h1>This is a heading</h1>
<p>paragraph</p>
<hr />
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Mixed

With image

```````````````````````````````` example(Mixed: 1) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:bookmarkStart w:id="1" w:name="heading-1-italic"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t>italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2-bold"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t>bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3-strike-through"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4-inline-code"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t>inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
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
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
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
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
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
            <w:t>bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t>italic</w:t>
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
            <w:t>bold-italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t>subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t>inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="360"/>
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
                                    <a:blip r:embed="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
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
            <w:ind w:left="360"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="43"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="44"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="45"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="46"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some plain text</w:t>
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
            <w:t>block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId11" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
            <w:t>block quote 2</w:t>
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
            <w:t>block quote 3</w:t>
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
            <w:t>block quote 4</w:t>
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
            <w:t>block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="47"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1560"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1920"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>nested list</w:t>
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
            <w:t>pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t>code</w:t>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>header</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                        <w:t>Longer</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 2</w:t>
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
                        <w:t>Data 3</w:t>
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
                        <w:t>Data 4</w:t>
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
                        <w:t>data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 5</w:t>
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
                        <w:t>Data 6</w:t>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data 9</w:t>
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
                        <w:t>Data 10</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


Without image

```````````````````````````````` example(Mixed: 2) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:bookmarkStart w:id="1" w:name="heading-1-italic"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t>italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2-bold"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t>bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3-strike-through"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4-inline-code"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t>inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
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
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
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
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some Text</w:t>
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
            <w:t>bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t>italic</w:t>
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
            <w:t>bold-italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t>subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t>inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="360"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="720"/>
        </w:pPr>
        <w:r>
            <w:t>with some text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="43"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 3</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="44"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 4</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="45"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="46"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some plain text</w:t>
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
            <w:t>block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId10" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
            <w:t>block quote 2</w:t>
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
            <w:t>block quote 3</w:t>
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
            <w:t>block quote 4</w:t>
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
            <w:t>block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="47"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1560"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1920"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>nested list</w:t>
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
            <w:t>pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t>code</w:t>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>header</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                        <w:t>Longer</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 2</w:t>
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
                        <w:t>Data 3</w:t>
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
                        <w:t>Data 4</w:t>
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
                        <w:t>data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 5</w:t>
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
                        <w:t>Data 6</w:t>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data 9</w:t>
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
                        <w:t>Data 10</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Mixed: 3) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:bookmarkStart w:id="1" w:name="heading-1-italic"/>
    <w:bookmarkEnd w:id="1"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading1"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 1 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t>italic</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t> </w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="2" w:name="heading-2-bold"/>
    <w:bookmarkEnd w:id="2"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading2"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 2 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="StrongEmphasis"/>
            </w:rPr>
            <w:t>bold</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t> </w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="3" w:name="heading-3-strike-through"/>
    <w:bookmarkEnd w:id="3"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 3 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
    </w:p>
    <w:bookmarkStart w:id="4" w:name="heading-4-inline-code"/>
    <w:bookmarkEnd w:id="4"/>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading4"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Heading 4 </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t>inline-code</w:t>
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
            <w:t>bold</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Emphasis"/>
            </w:rPr>
            <w:t>italic</w:t>
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
            <w:t>bold-italic</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t>underline</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Strikethrough"/>
            </w:rPr>
            <w:t>strike-through</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Superscript"/>
            </w:rPr>
            <w:t>superscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> normal </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Subscript"/>
            </w:rPr>
            <w:t>subscript</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="SourceText"/>
            </w:rPr>
            <w:t>inline-code</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:ind w:left="360"/>
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
                                    <a:blip r:embed="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"/>
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
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="43"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>list item 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t>Some plain text</w:t>
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
            <w:t>block quote 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">with a link </w:t>
        </w:r>
        <w:hyperlink r:id="rId11" w:tooltip="Title: flexmark-java logo" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
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
                <w:t>flexmark-icon-logo</w:t>
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
            <w:t>block quote 2</w:t>
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
            <w:t>block quote 5</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="44"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1080"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1440"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>nested list</w:t>
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
            <w:t>pre-formatted code</w:t>
        </w:r>
        <w:r>
            <w:br/>
        </w:r>
        <w:r>
            <w:t>code</w:t>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>header</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                        <w:t>Longer</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 2</w:t>
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
                        <w:t>Data 3</w:t>
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
                        <w:t>Data 4</w:t>
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
                        <w:t>data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 5</w:t>
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
                        <w:t>Data 6</w:t>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data 9</w:t>
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
                        <w:t>Data 10</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## GitLab

GitLab formatting

```````````````````````````````` example GitLab: 1
plain [-del-] [+ins+]
plain {-del-} {+ins+}
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
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
            <w:t>del</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t>ins</w:t>
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
            <w:t>del</w:t>
        </w:r>
        <w:r>
            <w:t xml:space="preserve"> </w:t>
        </w:r>
        <w:r>
            <w:rPr>
                <w:rStyle w:val="Underlined"/>
            </w:rPr>
            <w:t>ins</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


GitLab block quotes formatting

```````````````````````````````` example GitLab: 2
>>>
Block Quote
>>>
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="240"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>Block Quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


GitLab block quotes formatting

```````````````````````````````` example GitLab: 3
>>>
Block Quote
>>>
Nested Block Quote
>>>
>>>
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="240"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>Block Quote</w:t>
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
        </w:pPr>
        <w:r>
            <w:t>Nested Block Quote</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Tables

All the fixings

```````````````````````````````` example Tables: 1
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0" w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:gridSpan w:val="4"/>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Combined </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Emphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>header</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Default</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Left </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="StrongEmphasis"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="center"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Center</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="right"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t xml:space="preserve">Right </w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="Underlined"/>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Align</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t/>
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
                        <w:t>Longer</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 2</w:t>
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
                        <w:t>Data 3</w:t>
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
                        <w:t>Data 4</w:t>
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
                        <w:t>data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 5</w:t>
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
                        <w:t>Data 6</w:t>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data 9</w:t>
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
                        <w:t>Data 10</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
                        <w:t>Data 7</w:t>
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
                        <w:t>Data 8</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                    <w:r>
                        <w:t/>
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
            <w:t>Table Caption</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


With breaks

```````````````````````````````` example Tables: 2
|Document / Item|versioning|
|:---------------------|:-------------|
|Technical Documentation, e.g.<br/> - Software Requirements<br/> - Software Architecture<br/> - Validation Plan|We use our versioning system to control changes to these (markdown) documents|
|Records such as completed checklist|See 'SOP' "Control of documents and records" (```DC-SOP01```)| 
|Development artifacts such as<br/> - source code<br/> - configuration files<br/> - Icons, graphics|We use our versioning system to control changes to these artifacts|
|SOUP|We use our versioning system to control changes to these artifacts|
[Table 4: Version control]

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0" w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Document / Item</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>versioning</w:t>
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
                        <w:t>Technical Documentation, e.g.</w:t>
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
                        <w:t>We use our versioning system to control changes to these (markdown) documents</w:t>
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
                        <w:t>Records such as completed checklist</w:t>
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
                        <w:t>See 'SOP' "Control of documents and records" (</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t>DC-SOP01</w:t>
                    </w:r>
                    <w:r>
                        <w:t>)</w:t>
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
                        <w:t>Development artifacts such as</w:t>
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
                        <w:t>We use our versioning system to control changes to these artifacts</w:t>
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
                        <w:t>SOUP</w:t>
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
                        <w:t>We use our versioning system to control changes to these artifacts</w:t>
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
            <w:t>Table 4: Version control</w:t>
        </w:r>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


With breaks and caption before

```````````````````````````````` example(Tables: 3) options(caption-before)
|Document / Item|versioning|
|:---------------------|:-------------|
|Technical Documentation, e.g.<br/> - Software Requirements<br/> - Software Architecture<br/> - Validation Plan|We use our versioning system to control changes to these (markdown) documents|
|Records such as completed checklist|See 'SOP' "Control of documents and records" (```DC-SOP01```)| 
|Development artifacts such as<br/> - source code<br/> - configuration files<br/> - Icons, graphics|We use our versioning system to control changes to these artifacts|
|SOUP|We use our versioning system to control changes to these artifacts|
[Table 4: Version control]

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TableCaption"/>
        </w:pPr>
        <w:r>
            <w:t>Table 4: Version control</w:t>
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
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0" w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Document / Item</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>versioning</w:t>
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
                        <w:t>Technical Documentation, e.g.</w:t>
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
                        <w:t>We use our versioning system to control changes to these (markdown) documents</w:t>
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
                        <w:t>Records such as completed checklist</w:t>
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
                        <w:t>See 'SOP' "Control of documents and records" (</w:t>
                    </w:r>
                    <w:r>
                        <w:rPr>
                            <w:rStyle w:val="SourceText"/>
                        </w:rPr>
                        <w:t>DC-SOP01</w:t>
                    </w:r>
                    <w:r>
                        <w:t>)</w:t>
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
                        <w:t>Development artifacts such as</w:t>
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
                        <w:t>We use our versioning system to control changes to these artifacts</w:t>
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
                        <w:t>SOUP</w:t>
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
                        <w:t>We use our versioning system to control changes to these artifacts</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


With spanning columns

```````````````````````````````` example Tables: 4

| data   | label  |
|:-------|:-------|
| spanning       ||
| normal | column |

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0" w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>label</w:t>
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
                        <w:t>spanning</w:t>
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
                        <w:t>normal</w:t>
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
                        <w:t>column</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


With spanning columns turned off

```````````````````````````````` example(Tables: 5) options(table-no-span)

| data   | label  |
|:-------|:-------|
| spanning       ||
| normal | column |

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0" w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>label</w:t>
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
                        <w:t>spanning</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr/>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableContents"/>
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
                        <w:t>normal</w:t>
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
                        <w:t>column</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


With embedded links

```````````````````````````````` example Tables: 6

| data   | label  |
|:-------|:-------|
| text [link](http://example.com) text | column |

.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:tblLook w:firstColumn="1" w:firstRow="1" w:lastColumn="0" w:lastRow="0" w:noHBand="0" w:noVBand="1" w:val="04a0"/>
        </w:tblPr>
        <w:tr>
            <w:trPr>
                <w:tblHeader/>
            </w:trPr>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
            <w:tc>
                <w:tcPr>
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                        <w:jc w:val="left"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>label</w:t>
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
                        <w:t xml:space="preserve">text </w:t>
                    </w:r>
                    <w:hyperlink r:id="rId10" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships">
                        <w:r>
                            <w:rPr>
                                <w:rStyle w:val="Hyperlink"/>
                            </w:rPr>
                            <w:t>link</w:t>
                        </w:r>
                    </w:hyperlink>
                    <w:r>
                        <w:t xml:space="preserve"> text</w:t>
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
                        <w:t>column</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


## Inheritance

Tests for inheritance of indentation and borders

block quote with bullet list

```````````````````````````````` example Inheritance: 1
> some quote
>
> * a list item 
> * another list item
>
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


block quote with bullet nested list

```````````````````````````````` example Inheritance: 2
> some quote
>
> * a list item 
>   * another list item
>
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


block quote with bullet list nested beyond border space limit

```````````````````````````````` example Inheritance: 3
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


block quote with ordered list

```````````````````````````````` example(Inheritance: 4) options(IGNORED)
> some quote
>
> 1. a list item 
> 1. another list item
>
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Inheritance: 5) options(IGNORED)
1. Table in a list

   | Simple Table |
   |--------------|
   | Data         |
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>Table in a list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="480"/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Simple Table</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Inheritance: 6) options(IGNORED)
> Table in quote
>
> | Simple Table |
> |--------------|
> | Data         |
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>Table in quote</w:t>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:color w:val="666666"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Simple Table</w:t>
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
                        <w:t>Data</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 7) options(IGNORED)
1. ordered list

   > with block quote
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
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
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:t>with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 8) options(IGNORED)
1. ordered list
   * bullet list
   
     > with block quote
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>bullet list</w:t>
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
            <w:t>with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 9) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
            <w:t>with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:before="240" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>text</w:t>
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
            <w:ind w:left="840"/>
        </w:pPr>
        <w:r>
            <w:t>block quote</w:t>
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
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>bullet list</w:t>
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
            <w:t>with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="43"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:before="240" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>list child text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Quotations"/>
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1680"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>list child text</w:t>
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
            <w:ind w:left="1560"/>
        </w:pPr>
        <w:r>
            <w:t>last block quote</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with block quote

```````````````````````````````` example(Inheritance: 10) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>bullet list</w:t>
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
            <w:t>with block quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="CCCCCC" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="666666"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="1080"/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:color w:val="666666"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Simple Table</w:t>
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
                        <w:t>Data</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


aside block with bullet list

```````````````````````````````` example Inheritance: 11
| some quote
|
| * a list item 
| * another list item
|
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        </w:pPr>
        <w:r>
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


aside block with bullet nested list

```````````````````````````````` example Inheritance: 12
| some quote
|
| * a list item 
|   * another list item
|
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        </w:pPr>
        <w:r>
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


aside block with bullet list nested beyond border space limit

```````````````````````````````` example Inheritance: 13
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F" w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        </w:pPr>
        <w:r>
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="3"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="4"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true" w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3" w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


aside block with ordered list

```````````````````````````````` example(Inheritance: 14) options(IGNORED)
| some quote
|
| 1. a list item 
| 1. another list item
|
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        </w:pPr>
        <w:r>
            <w:t>some quote</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>a list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>another list item</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Inheritance: 15) options(IGNORED)
1. Table in a list

   | Simple Table |
   |--------------|
   | Data         |
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>Table in a list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="480"/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Simple Table</w:t>
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
                        <w:t>Data</w:t>
                    </w:r>
                </w:p>
            </w:tc>
        </w:tr>
    </w:tbl>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


```````````````````````````````` example(Inheritance: 16) options(IGNORED)
| Table in quote
|
| | Simple Table |
| |--------------|
| | Data         |
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        </w:pPr>
        <w:r>
            <w:t>Table in quote</w:t>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Simple Table</w:t>
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
                        <w:t>Data</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 17) options(IGNORED)
1. ordered list

   | with aside block
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:before="240" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
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
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:t>with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 18) options(IGNORED)
1. ordered list
   * bullet list
   
     | with aside block
.
<w:body>
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>bullet list</w:t>
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
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Normal"/>
            <w:spacing w:after="0" w:before="0" w:line="140" w:lineRule="exact"/>
        </w:pPr>
    </w:p>
</w:body>
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 19) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
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
        </w:pPr>
        <w:r>
            <w:t>with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:before="240" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="960"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="600"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>text</w:t>
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
            <w:ind w:left="840"/>
        </w:pPr>
        <w:r>
            <w:t>aside block</w:t>
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
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>bullet list</w:t>
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
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="43"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:before="240" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>list child text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="AsideBlock"/>
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>text</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="27" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="120" w:before="240" w:line="300" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1680"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:ind w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:sz w:val="18"/>
            </w:rPr>
            <w:t>list child text</w:t>
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
            <w:ind w:left="1560"/>
        </w:pPr>
        <w:r>
            <w:t>last aside block</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


list with aside block

```````````````````````````````` example(Inheritance: 20) options(IGNORED)
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
    <w:p w14:paraId="05F8A951" w14:textId="17651ACC" w:rsidP="00797E3F"
        w:rsidR="00882252" w:rsidRDefault="00882252" w:rsidRPr="00882252">
        <w:bookmarkStart w:id="0" w:name="_GoBack"/>
        <w:bookmarkEnd w:id="0"/>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="41"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>ordered list</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListBullet"/>
            <w:numPr>
                <w:ilvl w:val="1"/>
                <w:numId w:val="1"/>
            </w:numPr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
        </w:pPr>
        <w:r>
            <w:t>bullet list</w:t>
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
            <w:ind w:left="960"/>
        </w:pPr>
        <w:r>
            <w:t>with aside block</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ListNumber"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="42"/>
            </w:numPr>
            <w:pBdr>
                <w:left w:color="3366FF" w:frame="true" w:shadow="true"
                    w:space="9" w:sz="16" w:val="single"/>
            </w:pBdr>
            <w:spacing w:after="0" w:line="288" w:lineRule="auto"/>
            <w:ind w:hanging="360" w:left="1320"/>
        </w:pPr>
        <w:r>
            <w:rPr>
                <w:rFonts w:cs="Times New Roman" w:eastAsia="Calibri"/>
                <w:color w:val="00000A"/>
                <w:sz w:val="18"/>
                <w:szCs w:val="18"/>
            </w:rPr>
            <w:t>with list</w:t>
        </w:r>
    </w:p>
    <w:tbl>
        <w:tblPr>
            <w:tblW w:type="auto" w:w="0"/>
            <w:jc w:val="left"/>
            <w:tblInd w:type="dxa" w:w="1080"/>
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
                    <w:shd w:color="auto" w:fill="DDDDDD" w:val="clear"/>
                </w:tcPr>
                <w:p>
                    <w:pPr>
                        <w:pStyle w:val="TableHeading"/>
                    </w:pPr>
                    <w:r>
                        <w:rPr>
                            <w:rFonts w:cs="Tms Rmn" w:eastAsia="Times New Roman"/>
                            <w:sz w:val="21"/>
                        </w:rPr>
                        <w:t>Simple Table</w:t>
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
                        <w:t>Data</w:t>
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
<w:footnote w:id="-1" w:type="separator">
    <w:p w14:paraId="3B24EFC6" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:separator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="0" w:type="continuationSeparator">
    <w:p w14:paraId="4FD2E378" w14:textId="77777777" w:rsidP="005C2DA3"
        w:rsidR="00303ECF" w:rsidRDefault="00303ECF">
        <w:pPr>
            <w:spacing w:after="0"/>
        </w:pPr>
        <w:r>
            <w:continuationSeparator/>
        </w:r>
    </w:p>
</w:footnote>
<w:footnote w:id="-1"/>
<w:footnote w:id="0"/>
````````````````````````````````


