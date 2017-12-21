**flexmark-java extension for enumerated reference processing**

Converts `[@type:reference]` to enumerated reference link based on type pattern defined in enumerated
reference definition.

Converts `[#type:reference]` to enumerated reference text based on type pattern defined in enumerated
reference definition.

Enumerated reference definition defines the text to be used by an enumerated reference. 

[@type]: Type content [#]

Where [#] is replaced by the ordinal for the actual reference in the document. [@] is equivalent
to [@] when there is no id. It is treated as a placeholder for the ordinal number for the given
type. Outside of a enumerated reference definition it will render `0` 


For example:

```
![Fig](http://example.com/test.png){#fig:test}  
[#fig:test]

![Fig](http://example.com/test.png){#fig:test2}  
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
```

Will render as:

<p><img src="http://example.com/test.png" alt="Fig" id="fig:test" /><br />
<span>Figure 1.</span></p>
<p><img src="http://example.com/test.png" alt="Fig" id="fig:test2" /><br />
<span>Figure 2.</span></p>
<table id="tbl:test">
  <thead>
    <tr><th>table</th></tr>
  </thead>
  <tbody>
    <tr><td>data</td></tr>
  </tbody>
  <caption><span>Table 1.</span> caption</caption>
</table>
<p></p>
<p>See <a href="#fig:test2"><span>Figure 2.</span></a></p>
<p>See <a href="#fig:test"><span>Figure 1.</span></a></p>
<p>See <a href="#tbl:test"><span>Table 1.</span></a></p>

