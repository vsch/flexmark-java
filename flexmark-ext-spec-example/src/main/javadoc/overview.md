**flexmark-java extension for flexmark-java spec test example processing**

Converts spec example syntax into flexmark-java AST node.

Syntax:

    ```````````````````````````````` example SpecExample: 1
    markdown content
    .
    html content
    .
    AST content
    ````````````````````````````````
    
Example test spec:    

    ```````````````````````````````` example GFM Emphasis: 7
    **a*b*c**
    
    .
    <p><strong>a<em>b</em>c</strong></p>
    .
    Document[0, 11]
      Paragraph[0, 10] isTrailingBlankLine
        StrongEmphasis[0, 9] textOpen:[0, 2, "**"] text:[2, 7, "a*b*c"] textClose:[7, 9, "**"]
          Text[2, 3] chars:[2, 3, "a"]
          Emphasis[3, 6] textOpen:[3, 4, "*"] text:[4, 5, "b"] textClose:[5, 6, "*"]
            Text[4, 5] chars:[4, 5, "b"]
          Text[6, 7] chars:[6, 7, "c"]
    ````````````````````````````````

