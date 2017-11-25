---
title: DocxConverter Extension Spec
author: Vladimir Schneider
version: 1.0
date: '2017-11-23'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## DocxConverter Issues

Issue related tests

### Issue 176

Issue #176, docx-converter pandoc emulation mode

```````````````````````````````` example Issue 176: 1
### header
1. List item started from 1
### header
1. List item started from 1
1. List item continued 2
### test
1. List item started from 1
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">header</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="6"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item started from 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">header</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item started from 1</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="7"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item continued 2</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="Heading3"/>
            <w:numPr>
                <w:ilvl w:val="2"/>
                <w:numId w:val="0"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">test</w:t>
        </w:r>
    </w:p>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="TextBody"/>
            <w:numPr>
                <w:ilvl w:val="0"/>
                <w:numId w:val="8"/>
            </w:numPr>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">List item started from 1</w:t>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


