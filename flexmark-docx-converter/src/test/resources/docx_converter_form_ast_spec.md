---
title: DocxConverter Attributes Conversion Spec
author: Vladimir Schneider
version: 1.0
date: '2019-11-15'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

# DocxConverter

## Form Controls

### Text

text, class name `.text`

attributes:

* `name` - name for the form field
* `default` - default value
* `help` - text to show in status bar and on hitting F1
* `max-length` - number or leave out for unlimited
* `type`
  * `regular` - default, regular text
    * `format` - "UPPERCASE", "LOWERCASE", "FIRST CAPITAL", "TITLE CASE", case not sensitive,
      space between words can be eliminated or replaced by `-`
  * `date` - date
    * `format` - word date format
  * `number` - number
    * `format` - word number format
  * `current-date` - current date
    * `format` - word date format
  * `current-time` - current time
    * `format` - word time format

#### Regular

```````````````````````````````` example(Form Controls - Text - Regular: 1) options(form-controls-input)
Text: [input]{.text default=test}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Text: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Text1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="regular"/>
                        <w:default w:val="test"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>test</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Text - Regular: 2) options(form-controls-form)
Text: [form]{.text default=test}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Text: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Text1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="regular"/>
                        <w:default w:val="test"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>test</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Text - Regular: 3) options(form-controls-input)
Text: [input]{.text default=test help="Help String for field"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Text: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Text1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:helpText w:type="text" w:val="Help String for field"/>
                    <w:statusText w:type="text" w:val="Help String for field"/>
                    <w:textInput>
                        <w:type w:val="regular"/>
                        <w:default w:val="test"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>test</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


#### Number

```````````````````````````````` example(Form Controls - Text - Number: 1) options(form-controls-input)
Number: [input]{.text type=number format=#,##0}
    
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Number: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Number1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="number"/>
                        <w:default w:val="0"/>
                        <w:format w:val="#,##0"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>0</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


#### Date

```````````````````````````````` example(Form Controls - Text - Date: 1) options(form-controls-input, IGNORED)
Date No Default: [input]{.text type=date format=yyyy/M/d}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Date No Default: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Date1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="date"/>
                        <w:default w:val="2001/9/11"/>
                        <w:format w:val="yyyy/M/d"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>2001/9/11</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Text - Date: 2) options(form-controls-input, IGNORED)
Date: [input]{.text type=date default=2019/11/11 format=yyyy/M/d}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Date: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Date1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="date"/>
                        <w:default w:val="2019/11/11"/>
                        <w:format w:val="yyyy/M/d"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>2019/11/11</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


#### Current Date

```````````````````````````````` example(Form Controls - Text - Current Date: 1) options(form-controls-input, IGNORED)
Current Date: [input]{.text type=current-date format=yyyy/M/d}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Current Date: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="CurrentDate1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="currentTime"/>
                        <w:default w:val="2001/9/11"/>
                        <w:format w:val="yyyy/M/d"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin"/>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> DATE \@ "yyyy/M/d" </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:instrText>2001/9/11</w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>2001/9/11</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


#### Current Time

```````````````````````````````` example(Form Controls - Text - Current Time: 1) options(form-controls-input, IGNORED)
Current Time: [input]{.text type=current-time format=hh:mm:ss}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Current Time: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="CurrentTime1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:textInput>
                        <w:type w:val="currentDate"/>
                        <w:default w:val="09:46:40"/>
                        <w:format w:val="hh:mm:ss"/>
                    </w:textInput>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMTEXT </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin"/>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> TIME \@ "hh:mm:ss" </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:instrText>09:46:40</w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="separate"/>
        </w:r>
        <w:r>
            <w:rPr>
                <w:noProof/>
            </w:rPr>
            <w:t>09:46:40</w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


### Checkbox

class name `.checkbox`

* `name` - name for the form field
* `checked` - default will be checked, otherwise unchecked
* `help` - text to show in status bar and on hitting F1

```````````````````````````````` example(Form Controls - Checkbox: 1) options(form-controls-input)
Trial: [input]{.checkbox name="Trial"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Trial: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Trial"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:checkBox>
                        <w:sizeAuto/>
                        <w:default w:val="false"/>
                    </w:checkBox>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMCHECKBOX </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Checkbox: 2) options(form-controls-input)
Checked: [input]{.checkbox checked}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Checked: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Check1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:checkBox>
                        <w:sizeAuto/>
                        <w:default w:val="true"/>
                    </w:checkBox>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMCHECKBOX </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


### Dropdown

class name `.dropdown`

* `name` - name for the form field
* `default` - default selection first item if no default provided. Can be option text (case
  sensitive tried first, if no match then case insensitive is tried), if text match fails then
  attempted to parse as an integer index 1..number of options
* `help` - text to show in status bar and on hitting F1
* `options` - list of options separated by `|`, individual options will be trimmed but otherwise
  left as is

```````````````````````````````` example(Form Controls - Dropdown: 1) options(form-controls-input)
No Default: [input]{.dropdown options="Item 1 | Item 2 | Item 3"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">No Default: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="Item 2"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Dropdown: 2) options(form-controls-input)
1: [input]{.dropdown options="Item 1 | Item 2 | Item 3" default=1}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">1: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:default w:val="0"/>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="Item 2"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Dropdown: 3) options(form-controls-input)
2: [input]{.dropdown options="Item 1 | Item 2 | Item 3" default=2}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">2: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:default w:val="1"/>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="Item 2"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Dropdown: 4) options(form-controls-input)
3: [input]{.dropdown options="Item 1 | Item 2 | Item 3" default=3}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">3: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:default w:val="2"/>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="Item 2"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Dropdown: 5) options(form-controls-input)
Item 2: [input]{.dropdown default="Item 2" options="Item 1 | Item 2 | Item 3"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Item 2: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:default w:val="1"/>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="Item 2"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Dropdown: 6) options(form-controls-input)
item 1: [input]{.dropdown default="item 1" options="Item 1 | item 1 | Item 3"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">item 1: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:default w:val="1"/>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="item 1"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


```````````````````````````````` example(Form Controls - Dropdown: 7) options(form-controls-input)
Item 3: [input]{.dropdown default="item 3" options="Item 1 | item 1 | Item 3"}
.
<w:body>
    <w:p>
        <w:pPr>
            <w:pStyle w:val="ParagraphTextBody"/>
        </w:pPr>
        <w:r>
            <w:t xml:space="preserve">Item 3: </w:t>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="begin">
                <w:ffData>
                    <w:name w:val="Dropdown1"/>
                    <w:enabled/>
                    <w:calcOnExit w:val="false"/>
                    <w:ddList>
                        <w:default w:val="2"/>
                        <w:listEntry w:val="Item 1"/>
                        <w:listEntry w:val="item 1"/>
                        <w:listEntry w:val="Item 3"/>
                    </w:ddList>
                </w:ffData>
            </w:fldChar>
        </w:r>
        <w:r>
            <w:instrText xml:space="preserve"> FORMDROPDOWN </w:instrText>
        </w:r>
        <w:r>
            <w:fldChar w:fldCharType="end"/>
        </w:r>
    </w:p>
</w:body>
````````````````````````````````


