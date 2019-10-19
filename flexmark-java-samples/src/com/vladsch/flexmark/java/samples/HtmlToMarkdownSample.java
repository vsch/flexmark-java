package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.html2md.converter.FlexmarkHtmlConverter;

public class HtmlToMarkdownSample {
    public static void main(String[] args) {
        String html = "<ul>\n" +
                "  <li>\n" +
                "    <p>Add: live templates starting with <code>.</code></p>\n" +
                "    <table>\n" +
                "      <thead>\n" +
                "        <tr><th> Element       </th><th> Abbreviation    </th><th> Expansion                                               </th></tr>\n" +
                "      </thead>\n" +
                "      <tbody>\n" +
                "        <tr><td> Abbreviation  </td><td> <code>.abbreviation</code> </td><td> <code>*[]:</code>                                                 </td></tr>\n" +
                "        <tr><td> Code fence    </td><td> <code>.codefence</code>    </td><td> ``` ... ```                                       </td></tr>\n" +
                "        <tr><td> Explicit link </td><td> <code>.link</code>         </td><td> <code>[]()</code>                                                  </td></tr>\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "  </li>\n" +
                "</ul>";
        String markdown = FlexmarkHtmlConverter.builder().build().convert(html);

        System.out.println("HTML:");
        System.out.println(html);

        System.out.println("\nMarkdown:");
        System.out.println(markdown);
    }
}
