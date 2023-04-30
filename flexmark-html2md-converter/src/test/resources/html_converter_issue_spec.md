---
title: Html to Markdown Converter Spec
author: Vladimir Schneider
version: 1.0
date: '2018-02-04'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Issue Tests

Infinite loop on unwrap

```````````````````````````````` example Issue Tests: 1
:heavy_check_mark:
.
<g-emoji alias="heavy_check_mark" fallback-src="https://github.githubassets.com/images/icons/emoji/unicode/2714.png" ios-version="6.0">✔️</g-emoji>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 2
Text

Heading
=======

.
<p>Text&nbsp;</p>
<h1>Heading</h1>
````````````````````````````````


Headings need blank line

```````````````````````````````` example(Issue Tests: 3) options(nbsp)
Text&nbsp;

Heading
=======

.
<p>Text&nbsp;</p>
<h1>Heading</h1>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 4
**Attention!** Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).
.
<div class="warning">
  <strong>Attention!&nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


Headings need blank line

```````````````````````````````` example Issue Tests: 5
**Attention!** Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).
.
<div class="warning">
  <strong>Attention!  &nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


Headings need blank line

```````````````````````````````` example(Issue Tests: 6) options(nbsp)
**Attention!**&nbsp;Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).
.
<div class="warning">
  <strong>Attention!  &nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


Headings need blank line

```````````````````````````````` example(Issue Tests: 7) options(nbsp)
**Attention!**&nbsp;Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple [form](https://tech.yandex.com/key/form.xml?service=trnsl).
.
<div class="warning">
  <strong>Attention!&nbsp;</strong>Starting from version 1.5, access to all API methods requires a free API key, which you can get by submitting a simple <a target="_blank" href="https://tech.yandex.com/key/form.xml?service=trnsl">form</a>.
</div>
````````````````````````````````


handle `pre` without `code`

```````````````````````````````` example Issue Tests: 8
```
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// http://www.allcolor.org/YaHPConverter/
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

public class HtmlToPdf_yahp {
  public  static void main(String ... args ) throws Exception {
    htmlToPdfFile();
  }

  public static void htmlToPdfFile() throws Exception {
    CYaHPConverter converter = new CYaHPConverter();
    File fout = new File("c:/temp/x.pdf");
    FileOutputStream out = new FileOutputStream(fout);
    Map properties = new HashMap();
    List headerFooterList = new ArrayList();

    String str = "<HTML><HEAD></HEAD><BODY><H1>Testing</H1><FORM>" +
                 "check : <INPUT TYPE='checkbox' checked=checked/><br/>"   +
                 "</FORM></BODY></HTML>";

    properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
                   IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
    //properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, fontPath);
    converter.convertToPdf(str,
          IHtmlToPdfTransformer.A4P,
          headerFooterList,
          "file:///temp/", // root for relative external CSS and IMAGE
          out,
          properties);
    out.flush();
    out.close();
  }
}
```

.
<div class="howtocode"><pre>import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// http://www.allcolor.org/YaHPConverter/
import org.allcolor.yahp.converter.CYaHPConverter;
import org.allcolor.yahp.converter.IHtmlToPdfTransformer;

public class HtmlToPdf_yahp {
  public  static void main(String ... args ) throws Exception {
    htmlToPdfFile();
  }

  public static void htmlToPdfFile() throws Exception {
    CYaHPConverter converter = new CYaHPConverter();
    File fout = new File("c:/temp/x.pdf");
    FileOutputStream out = new FileOutputStream(fout);
    Map properties = new HashMap();
    List headerFooterList = new ArrayList();

    String str = "&lt;HTML&gt;&lt;HEAD&gt;&lt;/HEAD&gt;&lt;BODY&gt;&lt;H1&gt;Testing&lt;/H1&gt;&lt;FORM&gt;" +
                 "check : &lt;INPUT TYPE='checkbox' checked=checked/&gt;&lt;br/&gt;"   +
                 "&lt;/FORM&gt;&lt;/BODY&gt;&lt;/HTML&gt;";

    properties.put(IHtmlToPdfTransformer.PDF_RENDERER_CLASS,
                   IHtmlToPdfTransformer.FLYINGSAUCER_PDF_RENDERER);
    //properties.put(IHtmlToPdfTransformer.FOP_TTF_FONT_PATH, fontPath);
    converter.convertToPdf(str,
          IHtmlToPdfTransformer.A4P,
          headerFooterList,
          "file:///temp/", // root for relative external CSS and IMAGE
          out,
          properties);
    out.flush();
    out.close();
  }
}
</pre></div>
````````````````````````````````


```````````````````````````````` example Issue Tests: 9
priyanka • [3 years ago](http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391 "Monday, September 23, 2013 5:49 AM")  
Though i am not getting any error ...no pdf is getting created
see more
* [2](# "")
* •
* [Reply](#)
* •
* Share ›
  * Twitter
  * Facebook
  * 
* 

.
<div class="post-body">
  <header class="comment__header">
<span class="post-byline">
<span class="author">priyanka</span>

</span>

    <span class="post-meta">
<span class="bullet time-ago-bullet" aria-hidden="true">•</span>

<a href="http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391" data-role="relative-time" class="time-ago" title="Monday, September 23, 2013 5:49 AM">3 years ago</a>
</span>

  </header>

  <div class="post-body-inner">
    <div class="post-message-container" data-role="message-container">
      <div class="publisher-anchor-color" data-role="message-content">
        <div class="post-message " data-role="message" dir="auto">
          <p>Though i am not getting any error ...no pdf is getting created</p>
        </div>

        <span class="post-media"><ul data-role="post-media-list"></ul></span>
      </div>
    </div>
    <a class="see-more hidden" title="see more" data-action="see-more">see more</a>
  </div>

  <footer class="comment__footer">
    <menu class="comment-footer__menu">
      <li class="voting" data-role="voting">
        <a href="#" class="vote-up  count-2" data-action="upvote" title="">

          <span class="updatable count" data-role="likes">2</span>
          <span class="control"><i aria-hidden="true" class="icon icon-arrow-2"></i></span>
        </a>
        <span role="button" class="vote-down  count-0" data-action="downvote" title="Vote down">

<span class="control"><i aria-hidden="true" class="icon icon-arrow"></i></span>
</span>
      </li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="reply" data-role="reply-link">
        <a href="#" data-action="reply">
          <span class="text">Reply</span></a></li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="comment__share">
        <a class="toggle"><span class="text">Share ›</span></a>
        <ul class="comment-share__buttons">
          <li class="twitter">
            <button class="share__button" data-action="share:twitter">Twitter</button>
          </li>
          <li class="facebook">
            <button class="share__button" data-action="share:facebook">Facebook</button>
          </li>
          <li class="link">
            <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly="">
          </li>
        </ul>
      </li>

      <li class="realtime" data-role="realtime-notification:1055568391">
        <span style="display:none;" class="realtime-replies"></span>
        <a style="display:none;" href="#" class="realtime-button"></a>
      </li>

    </menu>
  </footer>
</div>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 10) options(output-unknown)
<header class="comment__header"> priyanka • [3 years ago](http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391 "Monday, September 23, 2013 5:49 AM") </header>  
Though i am not getting any error ...no pdf is getting created
see more
<footer class="comment__footer"> <menu class="comment-footer__menu">
* [2](# "")
* •
* [Reply](#)
* •
* Share ›
  * <button class="share__button" data-action="share:twitter">Twitter</button>
  * <button class="share__button" data-action="share:facebook">Facebook</button>
  * <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly>
* 

</menu> </footer>
.
<div class="post-body">
  <header class="comment__header">
<span class="post-byline">
<span class="author">priyanka</span>

</span>

    <span class="post-meta">
<span class="bullet time-ago-bullet" aria-hidden="true">•</span>

<a href="http://www.rgagnon.com/javadetails/java-convert-html-to-pdf-using-yahp.html#comment-1055568391" data-role="relative-time" class="time-ago" title="Monday, September 23, 2013 5:49 AM">3 years ago</a>
</span>

  </header>

  <div class="post-body-inner">
    <div class="post-message-container" data-role="message-container">
      <div class="publisher-anchor-color" data-role="message-content">
        <div class="post-message " data-role="message" dir="auto">
          <p>Though i am not getting any error ...no pdf is getting created</p>
        </div>

        <span class="post-media"><ul data-role="post-media-list"></ul></span>
      </div>
    </div>
    <a class="see-more hidden" title="see more" data-action="see-more">see more</a>
  </div>

  <footer class="comment__footer">
    <menu class="comment-footer__menu">
      <li class="voting" data-role="voting">
        <a href="#" class="vote-up  count-2" data-action="upvote" title="">

          <span class="updatable count" data-role="likes">2</span>
          <span class="control"><i aria-hidden="true" class="icon icon-arrow-2"></i></span>
        </a>
        <span role="button" class="vote-down  count-0" data-action="downvote" title="Vote down">

<span class="control"><i aria-hidden="true" class="icon icon-arrow"></i></span>
</span>
      </li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="reply" data-role="reply-link">
        <a href="#" data-action="reply">
          <span class="text">Reply</span></a></li>
      <li class="bullet" aria-hidden="true">•</li>

      <li class="comment__share">
        <a class="toggle"><span class="text">Share ›</span></a>
        <ul class="comment-share__buttons">
          <li class="twitter">
            <button class="share__button" data-action="share:twitter">Twitter</button>
          </li>
          <li class="facebook">
            <button class="share__button" data-action="share:facebook">Facebook</button>
          </li>
          <li class="link">
            <input class="share__button" value="http://disq.us/p/hgghs7" name="Link" title="Click to copy post link" data-action="copy-link" readonly="">
          </li>
        </ul>
      </li>

      <li class="realtime" data-role="realtime-notification:1055568391">
        <span style="display:none;" class="realtime-replies"></span>
        <a style="display:none;" href="#" class="realtime-button"></a>
      </li>

    </menu>
  </footer>
</div>
````````````````````````````````


Unwrap iframes

```````````````````````````````` example Issue Tests: 11
.
<iframe id="dsq-app4" name="dsq-app4" allowtransparency="true" frameborder="0" scrolling="no" tabindex="0" title="Disqus" width="100%" src="http://disqusads.com/ads-iframe/taboola/?category=tech&amp;stories_allowed=0&amp;service=dynamic&amp;safetylevel=20&amp;display_allowed=1&amp;video_allowed=0&amp;provider=taboola&amp;thumbnails_allowed=1&amp;experiment=network_default&amp;variant=fallthrough&amp;display_only=0&amp;t=1485117195&amp;links_allowed=1&amp;position=bottom&amp;shortname=realshowto&amp;display_bidding_allowed=0&amp;forum_shortname=realshowto&amp;forum_pk=1341383&amp;anchorColor=%23039be5&amp;colorScheme=light&amp;sourceUrl=http%3A%2F%2Fwww.rgagnon.com%2Fjavadetails%2Fjava-convert-html-to-pdf-using-yahp.html&amp;typeface=sans-serif&amp;disqus_version=9464b90" style="box-sizing: inherit; width: 1px !important; min-width: 100%; border: none !important; overflow: hidden !important; height: 586px !important;"></iframe>
````````````````````````````````


suppress GitHub anchors and octicon svg

```````````````````````````````` example Issue Tests: 12
TEST CASES
==========

.
<h1><a id="user-content-test-cases" class="anchor" href="#test-cases" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>TEST CASES</h1>
````````````````````````````````


suppress GitHub anchors and octicon svg

```````````````````````````````` example Issue Tests: 13
TEST CASES
==========

.
<h1><a id="user-content-test-cases" class="anchor" href="file#test-cases" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>TEST CASES</h1>
````````````````````````````````


empty headings

```````````````````````````````` example Issue Tests: 14
.
<h1 style="box-sizing: border-box; font-size: 2em; margin: 24px 0px 16px; font-weight: 600; line-height: 1.25; padding-bottom: 0.3em; border-bottom: 1px solid rgb(238, 238, 238); position: relative; padding-right: 0.8em; cursor: pointer; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;"> <a id="user-content-changelog" class="anchor" href="https://github.com/danfickle/openhtmltopdf#changelog" aria-hidden="true" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none; float: left; padding-right: 4px; margin-left: -20px; line-height: 1;"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>  </h1><br class="Apple-interchange-newline">
````````````````````````````````


GitHub file list

```````````````````````````````` example Issue Tests: 15
![@danfickle](https://avatars2.githubusercontent.com/u/1415728?v=3&s=40) [danfickle](https://github.com/danfickle) [For](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") [#52](https://github.com/danfickle/openhtmltopdf/issues/52 "Adobe Reader promts for saving") [- Do not output acroform dict in case where document contains...](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") ...  

|---|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------|
|   | [docs](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/docs "docs")                                                                                        | [For](https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe "For #8 - Update README and integration guide with RC8 version. [ci skip]") [#8](https://github.com/danfickle/openhtmltopdf/issues/8) [- Update README and integration guide with RC8 version. \[ci skip\]](https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe "For #8 - Update README and integration guide with RC8 version. [ci skip]")                                                                   | 2 months ago |
|   | [obsolete-archive](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/obsolete-archive "obsolete-archive")                                                    | [Cleanup repository - Move everything obsolete to the obsolete-archive...](https://github.com/danfickle/openhtmltopdf/commit/3ca888aab7c587d16ec25e31db8a5659a0ed75ee "Cleanup repository - Move everything obsolete to the obsolete-archive folder.")                                                                                                                                                                                                                                                                                                | 7 months ago |
|   | [openhtmltopdf-core](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-core "openhtmltopdf-core")                                              | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [openhtmltopdf-examples](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-examples "openhtmltopdf-examples")                                  | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [openhtmltopdf-jsoup-dom-converter](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-jsoup-dom-converter "openhtmltopdf-jsoup-dom-converter") | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [openhtmltopdf-log4j](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-log4j "openhtmltopdf-log4j")                                           | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [openhtmltopdf-pdfbox](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-pdfbox "openhtmltopdf-pdfbox")                                        | [For](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") [#52](https://github.com/danfickle/openhtmltopdf/issues/52 "Adobe Reader promts for saving") [- Do not output acroform dict in case where document contains...](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") | 22 days ago  |
|   | [openhtmltopdf-rtl-support](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-rtl-support "openhtmltopdf-rtl-support")                         | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [openhtmltopdf-slf4j](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-slf4j "openhtmltopdf-slf4j")                                           | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [openhtmltopdf-svg-support](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-svg-support "openhtmltopdf-svg-support")                         | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |
|   | [tests](https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/tests "tests")                                                                                     | [regression snapshot was missing source images, now using Ant to make ...](https://github.com/danfickle/openhtmltopdf/commit/927efc50fb4a8870691a168ea86223ebcb70b4fc "regression snapshot was missing source images, now using Ant to make copy of entire source reference tree and including this in snapshot diff. rebuild R8 snapshot so it includes these files. have ref comparison only report failures.")                                                                                                                                     | 8 years ago  |
|   | [.cvsignore](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.cvsignore ".cvsignore")                                                                      | [.](https://github.com/danfickle/openhtmltopdf/commit/81951cc933fffdf85a5b5fbe274bd9c181c1daed ".")                                                                                                                                                                                                                                                                                                                                                                                                                                                   | 8 years ago  |
|   | [.gitignore](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.gitignore ".gitignore")                                                                      | [For](https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf "For #1 - Set up Apache PDF-BOX 2 output device module.") [#1](https://github.com/danfickle/openhtmltopdf/issues/1) [- Set up Apache PDF-BOX 2 output device module.](https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf "For #1 - Set up Apache PDF-BOX 2 output device module.")                                                                                                                           | a year ago   |
|   | [.hgignore](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.hgignore ".hgignore")                                                                         | [Add .hgignore for those using hg-git](https://github.com/danfickle/openhtmltopdf/commit/011613889106579f70d33e6ec78a77168ee54424 "Add .hgignore for those using hg-git --HG-- rename : .gitignore => .hgignore")                                                                                                                                                                                                                                                                                                                                     | 7 years ago  |
|   | [.travis.yml](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.travis.yml ".travis.yml")                                                                   | [Continuous integration with travis.](https://github.com/danfickle/openhtmltopdf/commit/6c79ddb644ecc7ab2617480b4b8207de7adec6f3 "Continuous integration with travis. Primarily so we stay compatible with Java 7.")                                                                                                                                                                                                                                                                                                                                  | 7 months ago |
|   | [LICENSE](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE "LICENSE")                                                                               | [For](https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a "For #8 - Update LICENSE.") [#8](https://github.com/danfickle/openhtmltopdf/issues/8) [- Update LICENSE.](https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a "For #8 - Update LICENSE.")                                                                                                                                                                                                                     | 7 months ago |
|   | [LICENSE-LGPL-2.1.txt](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-2.1.txt "LICENSE-LGPL-2.1.txt")                                        | [- change (back) license to 2.1 or later](https://github.com/danfickle/openhtmltopdf/commit/21c4dd319d5a7fac397bda10d60bb07a34782f1c "- change (back) license to 2.1 or later - include all LICENSE files in the jars")                                                                                                                                                                                                                                                                                                                               | 2 years ago  |
|   | [LICENSE-LGPL-3.txt](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-3.txt "LICENSE-LGPL-3.txt")                                              | [- license update LGPL version 2.1 to 3](https://github.com/danfickle/openhtmltopdf/commit/a1ea2f09424b35ad2988453f70dc820872eb5ce0 "- license update LGPL version 2.1 to 3 - added license text of GPLv3 (mandatory) - links updated - added license information in poms - added license includes for jars")                                                                                                                                                                                                                                         | 2 years ago  |
|   | [LICENSE-W3C-TEST](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-W3C-TEST "LICENSE-W3C-TEST")                                                    | [Added license for W3C tests, as requested on tests website.](https://github.com/danfickle/openhtmltopdf/commit/17e53385724025576684290a62cc05c3183b163e "Added license for W3C tests, as requested on tests website.")                                                                                                                                                                                                                                                                                                                               | 10 years ago |
|   | [README.md](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/README.md "README.md")                                                                         | [For](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") [#52](https://github.com/danfickle/openhtmltopdf/issues/52 "Adobe Reader promts for saving") [- Do not output acroform dict in case where document contains...](https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91 "For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]") | 22 days ago  |
|   | [pom.xml](https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/pom.xml "pom.xml")                                                                               | [\[maven-release-plugin\] prepare for next development iteration](https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec "[maven-release-plugin] prepare for next development iteration")                                                                                                                                                                                                                                                                                                                         | 2 months ago |

.
<meta charset='utf-8'>
<div class="commit-tease js-details-container" style="box-sizing: border-box; position: relative; padding: 10px; margin-bottom: -1px; font-size: 13px; line-height: 20px; color: rgb(104, 119, 125); background-color: rgb(242, 249, 252); border: 1px solid rgb(201, 230, 242); border-radius: 3px 3px 0px 0px; font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;">
  <span class="commit-author-section" style="box-sizing: border-box; color: rgb(51, 51, 51);"><img alt="@danfickle" class="avatar" height="20" src="https://avatars2.githubusercontent.com/u/1415728?v=3&amp;s=40" width="20" style="box-sizing: border-box; border-style: none; display: inline-block; overflow: hidden; line-height: 1; vertical-align: middle; border-radius: 3px; margin-top: -1px;"><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle" class="user-mention" rel="author" style="box-sizing: border-box; background-color: transparent; color: rgb(51, 51, 51); text-decoration: none; font-weight: 600; white-space: nowrap;">danfickle</a><span class="Apple-converted-space"> </span></span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: inherit; text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/52" class="issue-link js-issue-link" data-id="191574839" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" title="Adobe Reader promts for saving" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">#52</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: inherit; text-decoration: none;">- Do not output acroform dict in case where document contains…</a><span class="Apple-converted-space"> </span><span class="hidden-text-expander inline" style="box-sizing: border-box; display: inline-block; position: relative; top: -1px; margin-left: 5px; line-height: 0;"><button type="button" class="ellipsis-expander js-details-target" style="box-sizing: border-box; font-style: inherit; font-variant: inherit; font-weight: bold; font-stretch: inherit; font-size: 12px; line-height: 6px; font-family: inherit; margin: 0px; overflow: visible; text-transform: none; -webkit-appearance: button; cursor: pointer; display: inline-block; height: 12px; padding: 0px 5px 5px; color: rgb(85, 85, 85); text-decoration: none; vertical-align: middle; background: rgb(221, 221, 221); border: 0px; border-radius: 1px;">…</button></span>
</div>
<div class="file-wrap" style="box-sizing: border-box; margin-bottom: 10px; border-width: 0px 1px 1px; border-right-style: solid; border-bottom-style: solid; border-left-style: solid; border-right-color: rgb(221, 221, 221); border-bottom-color: rgb(221, 221, 221); border-left-color: rgb(221, 221, 221); border-image: initial; border-top-style: initial; border-top-color: initial; border-bottom-right-radius: 3px; border-bottom-left-radius: 3px; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 14px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);">
  <table class="files js-navigation-container js-active-navigation-container" data-pjax="" style="box-sizing: border-box; border-spacing: 0px; border-collapse: collapse; width: 977.778px; background: rgb(255, 255, 255); border-radius: 2px;">
    <tbody style="box-sizing: border-box;">
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/docs" class="js-navigation-open" id="e3e2a9bfd88566b05001b02a3f51d286-b94e3fb3293e2773a2e0cff1140fa924842a3662" title="docs" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">docs</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe" class="message" data-pjax="true" title="For #8 - Update README and integration guide with RC8 version. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/8" class="issue-link js-issue-link" data-url="https://github.com/danfickle/openhtmltopdf/issues/8" data-id="131634448" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#8</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/d41eb81c246083b521f3e63f8685ace3620be3fe" class="message" data-pjax="true" title="For #8 - Update README and integration guide with RC8 version. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Update README and integration guide with RC8 version. [ci skip]</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T11:05:17Z" title="Nov 22, 2016, 6:05 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/obsolete-archive" class="js-navigation-open" id="c68a1d46a8d6446c655338dc04a36187-b659a4b026b17151924c11a94f687d949afa4b5f" title="obsolete-archive" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">obsolete-archive</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/3ca888aab7c587d16ec25e31db8a5659a0ed75ee" class="message" data-pjax="true" title="Cleanup repository - Move everything obsolete to the obsolete-archive folder." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Cleanup repository - Move everything obsolete to the obsolete-archive…</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-06-25T10:19:05Z" title="Jun 25, 2016, 6:19 AM GMT-4" style="box-sizing: border-box;">7 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-core" class="js-navigation-open" id="43c1644a7dd1cc3e7295e178d19c554a-3293f3b0f6207b4ab1cd264b7adfeecc2df81f22" title="openhtmltopdf-core" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-core</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-examples" class="js-navigation-open" id="70804f5dd080109e48eaad24fc5c4097-8a9fa3a342a5f7f15325c5b19bb25f3b96f6126b" title="openhtmltopdf-examples" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-examples</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-jsoup-dom-converter" class="js-navigation-open" id="1e7db2f9f0762d128f1281dca6239083-77d726435adb235dac254d0b6d68b5583fee50c4" title="openhtmltopdf-jsoup-dom-converter" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-jsoup-dom-converter</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-log4j" class="js-navigation-open" id="680c20366dfd644b5fb886e15c131d0d-16ac99b925ef526d8df63ab6a5d22bab12eeb4f9" title="openhtmltopdf-log4j" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-log4j</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-pdfbox" class="js-navigation-open" id="7d9e5aec22a43aab0ee1c67d71cdccd0-a7ffee83109805cb8ff5f75914c92a9e950944de" title="openhtmltopdf-pdfbox" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-pdfbox</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/52" class="issue-link js-issue-link" data-id="191574839" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" title="Adobe Reader promts for saving" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#52</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Do not output acroform dict in case where document contains…</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2017-01-02T11:11:16Z" title="Jan 2, 2017, 6:11 AM GMT-5" style="box-sizing: border-box;">22 days ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-rtl-support" class="js-navigation-open" id="4f21e98d89412d75c1a1e723231b1a93-0e3e5145104c719670100d02751ff4d96b03ae71" title="openhtmltopdf-rtl-support" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-rtl-support</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-slf4j" class="js-navigation-open" id="d510b03c0e9243cec05d086a8bc69dc0-7b3045db1f683da0dd9521d9c6dd6ec2c9374aea" title="openhtmltopdf-slf4j" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-slf4j</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/openhtmltopdf-svg-support" class="js-navigation-open" id="0ec99f35d67fead18978c7a2880db8c1-90a1e298cddc2fedd9578556df2bc7d40d4c6568" title="openhtmltopdf-svg-support" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">openhtmltopdf-svg-support</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-directory" height="16" version="1.1" viewBox="0 0 14 16" width="14">
            <path fill-rule="evenodd" d="M13 4H7V3c0-.66-.31-1-1-1H1c-.55 0-1 .45-1 1v10c0 .55.45 1 1 1h12c.55 0 1-.45 1-1V5c0-.55-.45-1-1-1zM6 4H1V3h5v1z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/tree/open-dev-v1/tests" class="js-navigation-open" id="b61a6d542f9036550ba9c401c80f00ef-5f0ca6a61b4fbe07824e4c7aed64c4839042c695" title="tests" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">tests</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/927efc50fb4a8870691a168ea86223ebcb70b4fc" class="message" data-pjax="true" title="regression snapshot was missing source images, now using Ant to make copy of entire source reference tree and including this in snapshot diff. rebuild R8 snapshot so it includes these files. have ref comparison only report failures." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">regression snapshot was missing source images, now using Ant to make …</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2009-04-17T18:14:50Z" title="Apr 17, 2009, 2:14 PM GMT-4" style="box-sizing: border-box;">8 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.cvsignore" class="js-navigation-open" id="6d2b615139fd238e7c7471805068037e-47f0e8c731d42d4b5a30948b26bd9cce93bed18d" title=".cvsignore" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.cvsignore</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/81951cc933fffdf85a5b5fbe274bd9c181c1daed" class="message" data-pjax="true" title="." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">.</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2009-04-13T13:36:19Z" title="Apr 13, 2009, 9:36 AM GMT-4" style="box-sizing: border-box;">8 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.gitignore" class="js-navigation-open" id="a084b794bc0759e7a6b77810e01874f2-f170b301ac80705855d2b2f7fa7208dae6f95190" title=".gitignore" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.gitignore</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf" class="message" data-pjax="true" title="For #1 - Set up Apache PDF-BOX 2 output device module." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/1" class="issue-link js-issue-link" data-url="https://github.com/danfickle/openhtmltopdf/issues/1" data-id="115005751" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#1</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/04742bbc076613fc809fe05ca254269598f996bf" class="message" data-pjax="true" title="For #1 - Set up Apache PDF-BOX 2 output device module." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Set up Apache PDF-BOX 2 output device module.</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2015-11-04T09:51:21Z" title="Nov 4, 2015, 4:51 AM GMT-5" style="box-sizing: border-box;">a year ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.hgignore" class="js-navigation-open" id="c8e92ef85cd1579eac1c2ad728e79720-28a4f06ef31cdf35617690a280f8fe45243415d1" title=".hgignore" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.hgignore</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/011613889106579f70d33e6ec78a77168ee54424" class="message" data-pjax="true" title="Add .hgignore for those using hg-git
--HG--
rename : .gitignore => .hgignore" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Add .hgignore for those using hg-git</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2010-08-07T10:08:31Z" title="Aug 7, 2010, 6:08 AM GMT-4" style="box-sizing: border-box;">7 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/.travis.yml" class="js-navigation-open" id="354f30a63fb0907d4ad57269548329e3-7957a820abc3e86f2a0664ee2879d64e77437beb" title=".travis.yml" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">.travis.yml</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/6c79ddb644ecc7ab2617480b4b8207de7adec6f3" class="message" data-pjax="true" title="Continuous integration with travis.

Primarily so we stay compatible with Java 7." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Continuous integration with travis.</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-06-25T12:59:30Z" title="Jun 25, 2016, 8:59 AM GMT-4" style="box-sizing: border-box;">7 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE" class="js-navigation-open" id="9879d6db96fd29134fc802214163b95a-9756d4317eb74427f88dd3f24b37764cf03dbf72" itemprop="license" title="LICENSE" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a" class="message" data-pjax="true" title="For #8 - Update LICENSE." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/8" class="issue-link js-issue-link" data-url="https://github.com/danfickle/openhtmltopdf/issues/8" data-id="131634448" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#8</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/f4dfeb9faf89f4108ed6a50b67392073b9dde02a" class="message" data-pjax="true" title="For #8 - Update LICENSE." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Update LICENSE.</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-06-25T10:35:55Z" title="Jun 25, 2016, 6:35 AM GMT-4" style="box-sizing: border-box;">7 months ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-2.1.txt" class="js-navigation-open" id="82af9b08b0a2adf5b8dd97639db5a1ff-5ab7695ab8cabe0c5c8a814bb0ab1e8066578fbb" itemprop="license" title="LICENSE-LGPL-2.1.txt" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE-LGPL-2.1.txt</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/21c4dd319d5a7fac397bda10d60bb07a34782f1c" class="message" data-pjax="true" title="- change (back) license to 2.1 or later
- include all LICENSE files in the jars" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- change (back) license to 2.1 or later</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2015-07-15T15:45:49Z" title="Jul 15, 2015, 11:45 AM GMT-4" style="box-sizing: border-box;">2 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-LGPL-3.txt" class="js-navigation-open" id="71b1628a315fa537b1b8dc7548a5a43b-02bbb60bc49afc2d6a1bedf96288eab236d80fbd" itemprop="license" title="LICENSE-LGPL-3.txt" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE-LGPL-3.txt</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/a1ea2f09424b35ad2988453f70dc820872eb5ce0" class="message" data-pjax="true" title="- license update LGPL version 2.1 to 3
- added license text of GPLv3 (mandatory)
- links updated
- added license information in poms
- added license includes for jars" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- license update LGPL version 2.1 to 3</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2015-07-15T09:34:52Z" title="Jul 15, 2015, 5:34 AM GMT-4" style="box-sizing: border-box;">2 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/LICENSE-W3C-TEST" class="js-navigation-open" id="d04bb24acfc954e4d9d3bca685827fe9-0ee2f1e33aa98a4e15f005e05e0e5abcff7dbd8d" itemprop="license" title="LICENSE-W3C-TEST" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">LICENSE-W3C-TEST</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/17e53385724025576684290a62cc05c3183b163e" class="message" data-pjax="true" title="Added license for W3C tests, as requested on tests website." style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">Added license for W3C tests, as requested on tests website.</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2007-07-10T19:52:36Z" title="Jul 10, 2007, 3:52 PM GMT-4" style="box-sizing: border-box;">10 years ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/README.md" class="js-navigation-open" id="04c6e90faac2675aa89e2176d2eec7d8-1ed0fe7dc9e7c33e8b617c062531b4c3e3abe1a9" title="README.md" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">README.md</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136);">
          <span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">For</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/issues/52" class="issue-link js-issue-link" data-id="191574839" data-error-text="Failed to load issue title" data-permission-text="Issue title is private" title="Adobe Reader promts for saving" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">#52</a><span class="Apple-converted-space"> </span><a href="https://github.com/danfickle/openhtmltopdf/commit/bfdc7c96401bbedc36455b6ea086e310abe60e91" class="message" data-pjax="true" title="For #52 - Do not output acroform dict in case where document contains no forms. [ci skip]" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">- Do not output acroform dict in case where document contains…</a></span>
        </td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap;"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2017-01-02T11:11:16Z" title="Jan 2, 2017, 6:11 AM GMT-5" style="box-sizing: border-box;">22 days ago</time-ago></span></td>
      </tr>
      <tr class="js-navigation-item navigation-focus" style="box-sizing: border-box;">
        <td class="icon" style="box-sizing: border-box; padding: 6px 2px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 17px; color: rgb(118, 118, 118); background: rgb(245, 245, 245);">
          <svg aria-hidden="true" class="octicon octicon-file-text" height="16" version="1.1" viewBox="0 0 12 16" width="12">
            <path d="M6 5H2V4h4v1zM2 8h7V7H2v1zm0 2h7V9H2v1zm0 2h7v-1H2v1zm10-7.5V14c0 .55-.45 1-1 1H1c-.55 0-1-.45-1-1V2c0-.55.45-1 1-1h7.5L12 4.5zM11 5L8 2H1v12h10V5z"></path>
          </svg>
        </td>
        <td class="content" style="box-sizing: border-box; padding: 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 180px; background: rgb(245, 245, 245);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/blob/open-dev-v1/pom.xml" class="js-navigation-open" id="600376dffeb79835ede4a0b285078036-3d3378a25a49593f03fbb7f81116ca2ca09e270e" title="pom.xml" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">pom.xml</a></span></td>
        <td class="message" style="box-sizing: border-box; padding: 6px 3px 6px 10px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); max-width: 442px; overflow: hidden; color: rgb(136, 136, 136); background: rgb(245, 245, 245);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><a href="https://github.com/danfickle/openhtmltopdf/commit/d2e1430111e69d959dd0134d0b3aec50d1a4e9ec" class="message" data-pjax="true" title="[maven-release-plugin] prepare for next development iteration" style="box-sizing: border-box; background-color: transparent; color: rgb(136, 136, 136); text-decoration: none;">[maven-release-plugin] prepare for next development iteration</a></span></td>
        <td class="age" style="box-sizing: border-box; padding: 6px 10px 6px 3px; line-height: 20px; border-top: 1px solid rgb(238, 238, 238); width: 125px; color: rgb(136, 136, 136); text-align: right; white-space: nowrap; background: rgb(245, 245, 245);"><span class="css-truncate css-truncate-target" style="box-sizing: border-box; display: inline-block; max-width: 100%; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;"><time-ago datetime="2016-11-22T10:43:19Z" title="Nov 22, 2016, 5:43 AM GMT-5" style="box-sizing: border-box;">2 months ago</time-ago></span></td>
      </tr>
    </tbody>
  </table>
</div>
````````````````````````````````


```````````````````````````````` example Issue Tests: 16
* [Version 0.8.3 - Bug Fix and Improvement Release](https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release)
* [Version 0.8.2 - Enhanced Paste From History](https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history)
* [Version 0.8.0 - Mia has come of age!](https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age)
* [Why Do I Need Mia?](https://github.com/vsch/MissingInActions#why-do-i-need-mia)
  * [What you didn't know you were missing](https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing)
  * [Auto Indent Lines after Move Lines Up/Down](https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown)
  * [Auto Line Selections](https://github.com/vsch/MissingInActions#auto-line-selections)

<br />

.
<ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 16px; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;">
  <li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.3 - Bug Fix and Improvement Release</a></li>
  <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.2 - Enhanced Paste From History</a></li>
  <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.0 - Mia has come of age!</a></li>
  <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#why-do-i-need-mia" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Why Do I Need Mia?</a>
    <ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 0px;">
      <li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">What you didn't know you were missing</a></li>
      <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Indent Lines after Move Lines Up/Down</a></li>
      <li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-line-selections" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Line Selections</a></li>
    </ul>
  </li>
</ul>
<br class="Apple-interchange-newline">
````````````````````````````````


```````````````````````````````` example Issue Tests: 17
* [Version 0.8.3 - Bug Fix and Improvement Release](https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release)
* [Version 0.8.2 - Enhanced Paste From History](https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history)
* [Version 0.8.0 - Mia has come of age!](https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age)
* [Why Do I Need Mia?](https://github.com/vsch/MissingInActions#why-do-i-need-mia)
  * [What you didn't know you were missing](https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing)
  * [Auto Indent Lines after Move Lines Up/Down](https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown)
  * [Auto Line Selections](https://github.com/vsch/MissingInActions#auto-line-selections)

<br />

.
<ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 16px; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;"><li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#version-083---bug-fix-and-improvement-release" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.3 - Bug Fix and Improvement Release</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-082---enhanced-paste-from-history" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.2 - Enhanced Paste From History</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#version-080---mia-has-come-of-age" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Version 0.8.0 - Mia has come of age!</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#why-do-i-need-mia" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Why Do I Need Mia?</a><ul style="box-sizing: border-box; padding-left: 2em; margin-top: 0px; margin-bottom: 0px;"><li style="box-sizing: border-box;"><a href="https://github.com/vsch/MissingInActions#what-you-didnt-know-you-were-missing" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">What you didn't know you were missing</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-indent-lines-after-move-lines-updown" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Indent Lines after Move Lines Up/Down</a></li><li style="box-sizing: border-box; margin-top: 0.25em;"><a href="https://github.com/vsch/MissingInActions#auto-line-selections" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;">Auto Line Selections</a></li></ul></li></ul><br class="Apple-interchange-newline">
````````````````````````````````


don't create a multi-line image url if no new line after ?

```````````````````````````````` example Issue Tests: 18
[![](https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?)](https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?)Missing In Actions
===================================================================================================================================================================================================

<br />

.
<meta charset='utf-8'><h1 style="box-sizing: border-box; font-size: 2em; margin-top: 0px !important; margin-right: 0px; margin-bottom: 16px; margin-left: 0px; font-weight: 600; line-height: 1.25; padding-bottom: 0.3em; border-bottom: 1px solid rgb(238, 238, 238); position: relative; padding-right: 0.8em; cursor: pointer; color: rgb(51, 51, 51); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;"><a href="https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?" target="_blank" style="box-sizing: border-box; background-color: transparent; color: rgb(64, 120, 192); text-decoration: none;"><img src="https://github.com/vsch/MissingInActions/raw/master/resources/icons/Mia_logo@2x.png?" height="32" width="54" border="0" align="absmiddle" style="box-sizing: content-box; border-style: none; max-width: 100%; background-color: rgb(255, 255, 255);"></a>Missing In Actions</h1>
<br class="Apple-interchange-newline">
````````````````````````````````


```````````````````````````````` example Issue Tests: 19
```
  enum class TestFormatter {
    FOO, // I am a comment
    BAR, // I am also a comment
  }
```

.
<pre class="wikicode prettyprint prettyprinted"><span class="pln">  </span><span class="kwd">enum</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">TestFormatter</span><span class="pln"> </span><span class="pun">{</span><br><span class="pln">    FOO</span><span class="pun">,</span><span class="pln"> </span><span class="com">// I am a comment</span><br><span class="pln">    BAR</span><span class="pun">,</span><span class="pln"> </span><span class="com">// I am also a comment</span><br><span class="pln">  </span><span class="pun">}</span></pre>
````````````````````````````````


```````````````````````````````` example Issue Tests: 20
```
  enum class TestFormatter {
    FOO, // I am a comment BAR, // I am also a comment
  }
```

.
<pre class="wikicode prettyprint prettyprinted"><span class="pln">  </span><span class="kwd">enum</span><span class="pln"> </span><span class="kwd">class</span><span class="pln"> </span><span class="typ">TestFormatter</span><span class="pln"> </span><span class="pun">{</span><br><span class="pln">    FOO</span><span class="pun">,</span><span class="pln"> </span><span class="com">// I am a comment BAR, // I am also a comment</span><br><span class="pln">  </span><span class="pun">}</span></pre>
````````````````````````````````


```````````````````````````````` example Issue Tests: 21
\<h3\>JavaScript is disabled in your browser or not supported!\</h3\>  
View Options

* Show description
* Show comments

Click To Add a Title
====================

[project: Kotlin State: -Fixed,-Duplicate,-Rejected,-{As Designed},-Obsolete type: Feature,{Feature Group} refactoring](/issues?q=project%3A+Kotlin+State%3A+-Fixed%2C-Duplicate%2C-Rejected%2C-%7BAs+Designed%7D%2C-Obsolete+++type%3A+Feature%2C%7BFeature+Group%7D++refactoring)  
Only the first 2500 issues are exported because of the "Max Issues to Export" setting.

Feature
:   [KT-2615](/issue/KT-2615)
    --- Rename refactoring
:   [KT-2638](/issue/KT-2638)
    --- Inline function/non-trivial property refactoring
:   [KT-4478](/issue/KT-4478)
    --- Refactorings
:   [KT-5201](/issue/KT-5201)
    --- Extract Function: Allow forced extraction by shortcut
:   [KT-5602](/issue/KT-5602)
    --- move class/method refactoring should be able to move file
:   [KT-5804](/issue/KT-5804)
    --- Rename refactoring in Eclipse
:   [KT-5864](/issue/KT-5864)
    --- Refactoring: convert get/set methods to property
:   [KT-6159](/issue/KT-6159)
    --- Inline Method refactoring
:   [KT-6215](/issue/KT-6215)
    --- Converter: provide an option to place multiple java files converted to Kotlin in one Kotlin file
:   [KT-6370](/issue/KT-6370)
    --- Allow inline Java methods by using J2K converter
:   [KT-7107](/issue/KT-7107)
    --- Rename refactoring for labels
:   [KT-7973](/issue/KT-7973)
    --- Introduce parameter refactoring
:   [KT-8178](/issue/KT-8178)
    --- Copy Refactoring
:   [KT-8514](/issue/KT-8514)
    --- Refactor / Rename: "Rename variables" option could be supported for object declaration
:   [KT-8545](/issue/KT-8545)
    --- Rename: in-place mode could be supported for Kotlin
:   [KT-8565](/issue/KT-8565)
    --- Intention to remove default value and inline it in call sites (and vice versa?)
:   [KT-8954](/issue/KT-8954)
    --- Refactor / Copy could have "Update package directive" option
:   [KT-9851](/issue/KT-9851)
    --- Create "Extract/Introduce lateinit var" Refactoring
:   [KT-10346](/issue/KT-10346)
    --- Support \`\|\|\` in when conditions in "Introduce subject" intention
:   [KT-10642](/issue/KT-10642)
    --- IntelliJ plugin: Support KDoc syntax for all types of comments
:   [KT-11050](/issue/KT-11050)
    --- "Extract constant" refactoring
:   [KT-11882](/issue/KT-11882)
    --- When caret is over a type in Intellij, highlight "var"/"val" which have this type inferred
:   [KT-11883](/issue/KT-11883)
    --- IntelliJ - Extract Variable refactoring - Specify type explicitly by default
:   [KT-12165](/issue/KT-12165)
    --- Add inspections for useless collection operations
:   [KT-12348](/issue/KT-12348)
    --- Rename class refactoring: propose to rename usages in comments
:   [KT-12471](/issue/KT-12471)
    --- Allow initializing \`const\` properties with enum constants
:   [KT-12713](/issue/KT-12713)
    --- Either/ Result to handle errors without exceptions
:   [KT-12756](/issue/KT-12756)
    --- Support @JvmMultifileClass when @JvmName value matches another file facade class
:   [KT-13309](/issue/KT-13309)
    --- Create field from local variable intention/refactoring
:   [KT-13414](/issue/KT-13414)
    --- Indexed properties
:   [KT-13436](/issue/KT-13436)
    --- Replace 'when' with return: handle case when all branches jump out (return Nothing)
:   [KT-13458](/issue/KT-13458)
    --- Cascade "replace with return" for if/when expressions
:   [KT-13623](/issue/KT-13623)
    --- Enhancement for Quick fix: while in a apply-style block, offer "Create member function" without "this." and similarly allow "Extract Method" refactoring
:   [KT-14137](/issue/KT-14137)
    --- Add intention to convert top level val with object expression to object
:   [KT-14272](/issue/KT-14272)
    --- Support Move function refactoring when instance is not captured
:   [KT-14627](/issue/KT-14627)
    --- Inline parameter refactoring
:   [KT-15638](/issue/KT-15638)
    --- Extract Interface/Superclass: suggest to search and update usages of refactored class (like for Java)
:   [KT-15664](/issue/KT-15664)
    --- Refactoring: extract header from platform implementation
:   [KT-15679](/issue/KT-15679)
    --- Rename refactoring for header/impl interface/class/function/typealias
:   [KT-15867](/issue/KT-15867)
    --- Copy file window imporvements

Feature Group
:   [KT-2637](/issue/KT-2637)
    --- Inline variable/constant refactoring
.
<body class="chrome release-notes ">
<noscript>
&lt;h3&gt;JavaScript is disabled in your browser or not supported!&lt;/h3&gt;
</noscript>
<div cn="l.R.issuesInHtml" id="id_l.R.issuesInHtml">
<button cn="l.R.viewOptions" id="id_l.R.viewOptions" class="jt-menu-button ring-link rn-view rn-view__item" data-toggle-onclick="true">
View Options<span class="ring-font-icon ring-font-icon_caret-down"></span>
</button>
<ul class="jt-menu">
<li>
<a class="rn-view__item" href="javascript:void(0)">
<input name="l.R.description" cn="l.R.description" id="id_l.R.description" class="jt-input" type="checkbox">
<label cn="l.R.descriptionLabel" id="id_l.R.descriptionLabel">Show description</label>
</a>
</li>
<li>
<a class="rn-view__item" href="javascript:void(0)">
<input name="l.R.comments" cn="l.R.comments" id="id_l.R.comments" class="jt-input" type="checkbox">
<label cn="l.R.commentsLabel" id="id_l.R.commentsLabel">Show comments</label>
</a>
</li>
</ul>
<h1>
<a cn="l.R.titleLink" p0="set" id="id_l.R.titleLink_set" href="javascript:void(0)">Click To Add a Title</a>
<span cn="l.R.titleLinkInlineEditor" p0="set" id="id_l.R.titleLinkInlineEditor_set" pid="id_l.R.titleLink_set" eid="id_l.R.titleText_set_title"></span>
</h1>
<div class="rn-query">
<a href="/issues?q=project%3A+Kotlin+State%3A+-Fixed%2C-Duplicate%2C-Rejected%2C-%7BAs+Designed%7D%2C-Obsolete+++type%3A+Feature%2C%7BFeature+Group%7D++refactoring">project: Kotlin State: -Fixed,-Duplicate,-Rejected,-{As Designed},-Obsolete   type: Feature,{Feature Group}  refactoring</a>
</div>
<div cn="l.R.rnc.releaseNotesContent" id="id_l.R.rnc.releaseNotesContent">
<div cn="l.R.rnc.limitExceedMsg" id="id_l.R.rnc.limitExceedMsg" class="infoNote" style="display: none">
Only the first <span class="bold">2500</span> issues are exported because of the "Max Issues to Export" setting. 
</div>
<dl>
<dt class="rn-type">Feature</dt>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-2615">KT-2615</a>
    <span class="rn-sum">— Rename refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-2638">KT-2638</a>
    <span class="rn-sum">— Inline function/non-trivial property refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-4478">KT-4478</a>
    <span class="rn-sum">— Refactorings</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5201">KT-5201</a>
    <span class="rn-sum">— Extract Function: Allow forced extraction by shortcut</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5602">KT-5602</a>
    <span class="rn-sum">— move class/method refactoring should be able to move file</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5804">KT-5804</a>
    <span class="rn-sum">— Rename refactoring in Eclipse</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-5864">KT-5864</a>
    <span class="rn-sum">— Refactoring: convert get/set methods to property</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-6159">KT-6159</a>
    <span class="rn-sum">— Inline Method refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-6215">KT-6215</a>
    <span class="rn-sum">— Converter: provide an option to place multiple java files converted to Kotlin in one Kotlin file</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-6370">KT-6370</a>
    <span class="rn-sum">— Allow inline Java methods by using J2K converter</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-7107">KT-7107</a>
    <span class="rn-sum">— Rename refactoring for labels</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-7973">KT-7973</a>
    <span class="rn-sum">— Introduce parameter refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8178">KT-8178</a>
    <span class="rn-sum">— Copy Refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8514">KT-8514</a>
    <span class="rn-sum">— Refactor / Rename: "Rename variables" option could be supported for object declaration</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8545">KT-8545</a>
    <span class="rn-sum">— Rename: in-place mode could be supported for Kotlin</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8565">KT-8565</a>
    <span class="rn-sum">— Intention to remove default value and inline it in call sites (and vice versa?)</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-8954">KT-8954</a>
    <span class="rn-sum">— Refactor / Copy could have "Update package directive" option</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-9851">KT-9851</a>
    <span class="rn-sum">— Create "Extract/Introduce lateinit var" Refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-10346">KT-10346</a>
    <span class="rn-sum">— Support `||` in when conditions in "Introduce subject" intention</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-10642">KT-10642</a>
    <span class="rn-sum">— IntelliJ plugin: Support KDoc syntax for all types of comments</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-11050">KT-11050</a>
    <span class="rn-sum">— "Extract constant" refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-11882">KT-11882</a>
    <span class="rn-sum">— When caret is over a type in Intellij, highlight "var"/"val" which have this type inferred</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-11883">KT-11883</a>
    <span class="rn-sum">— IntelliJ - Extract Variable refactoring - Specify type explicitly by default</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12165">KT-12165</a>
    <span class="rn-sum">— Add inspections for useless collection operations</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12348">KT-12348</a>
    <span class="rn-sum">— Rename class refactoring: propose to rename usages in comments</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12471">KT-12471</a>
    <span class="rn-sum">— Allow initializing `const` properties with enum constants</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12713">KT-12713</a>
    <span class="rn-sum">— Either/ Result to handle errors without exceptions</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-12756">KT-12756</a>
    <span class="rn-sum">— Support @JvmMultifileClass when @JvmName value matches another file facade class</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13309">KT-13309</a>
    <span class="rn-sum">— Create field from local variable intention/refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13414">KT-13414</a>
    <span class="rn-sum">— Indexed properties</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13436">KT-13436</a>
    <span class="rn-sum">— Replace 'when' with return: handle case when all branches jump out (return Nothing)</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13458">KT-13458</a>
    <span class="rn-sum">— Cascade "replace with return" for if/when expressions</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-13623">KT-13623</a>
    <span class="rn-sum">— Enhancement for Quick fix: while in a apply-style block, offer "Create member function" without "this." and similarly allow "Extract Method" refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-14137">KT-14137</a>
    <span class="rn-sum">— Add intention to convert top level val with object expression to object</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-14272">KT-14272</a>
    <span class="rn-sum">— Support Move function refactoring when instance is not captured</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-14627">KT-14627</a>
    <span class="rn-sum">— Inline parameter refactoring</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15638">KT-15638</a>
    <span class="rn-sum">— Extract Interface/Superclass: suggest to search and update usages of refactored class (like for Java)</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15664">KT-15664</a>
    <span class="rn-sum">— Refactoring: extract header from platform implementation</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15679">KT-15679</a>
    <span class="rn-sum">— Rename refactoring for header/impl interface/class/function/typealias</span>
</dd>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-15867">KT-15867</a>
    <span class="rn-sum">— Copy file window imporvements</span>
</dd>
<dt class="rn-type">Feature Group</dt>
<dd class="rn-list-item">    <a class="rn-id" href="/issue/KT-2637">KT-2637</a>
    <span class="rn-sum">— Inline variable/constant refactoring</span>
</dd>
</dl>
</div>
</div>
<script type="text/javascript">$(document).ready(function () {
cr.setTemplatePaths([{name: "CustomReleaseNotesIssueView", paths: [{path: "l.R.rnc.CustomReleaseNotesIssueView"}]},{name: "ReleaseNotesContent", paths: [{path: "l.R.rnc"}]},{name: "ReleaseNotes", paths: [{path: "l.R"}]}]);

});

</script>
<script type="text/javascript">$(document).ready(function () {
cr.onDocumentLoad();
});

</script>

<script type="text/javascript">$(document).ready(function () {
Webr.HeartBeat.init();
$("#__RESPONSE_TIME_CONTENT__").replaceWith(document.createTextNode("2974 ms"));
info("End of the response");

});

</script>

</body>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 22) options(no-quotes, no-smarts)
“  
”  
‘  
’  
'  
«  
»  
…  
\&endash;  
\&emdash;  
“  
”  
‘  
’  
«  
»  
…  
–  
—  
.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 23) options(no-quotes)
“  
”  
‘  
’  
'  
«  
»  
...  
--  
---  
“  
”  
‘  
’  
«  
»  
...  
--  
---  
.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


```````````````````````````````` example(Issue Tests: 24) options(no-smarts)
"  
"  
'  
'  
'  
\<\<  
\>\>  
…  
\&endash;  
\&emdash;  
"  
"  
'  
'  
\<\<  
\>\>  
…  
–  
—  
.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


```````````````````````````````` example Issue Tests: 25
"  
"  
'  
'  
'  
\<\<  
\>\>  
...  
--  
---  
"  
"  
'  
'  
\<\<  
\>\>  
...  
--  
---  
.
&ldquo;<br>
&rdquo;<br>
&lsquo;<br>
&rsquo;<br>
&apos;<br>
&laquo;<br>
&raquo;<br>
&hellip;<br>
&endash;<br>
&emdash;<br>
“<br>
”<br>
‘<br>
’<br>
«<br>
»<br>
…<br>
–<br>
—<br>
````````````````````````````````


## Issue MN-408

Issue #MN-408

```````````````````````````````` example Issue MN-408: 1
* 
* test
* 
.
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
→<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
→<title></title>
→<meta name="generator" content="LibreOffice 5.2.3.3 (MacOSX)"/>
→<style type="text/css">
→→@page { margin: 2cm }
→→p { margin-bottom: 0.25cm; direction: ltr; line-height: 120%; text-align: left; orphans: 2; widows: 2 }
→</style>
</head>
<body lang="de-DE" dir="ltr">
<ul>
→<li/>
→<li>test</li>
→<li></li>
</ul>
</body>
</html>
````````````````````````````````


```````````````````````````````` example Issue MN-408: 2
* 
* Equipment
* 
* Chemicals
* 
* Consumables
* 
* Enzymes
* 
* GMO
* 
* Antibodies
* 
* DNA Constructs
* 
* RNA Constructs
* 
* Vectors
* 
* Oligos
.
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
→<meta http-equiv="content-type" content="text/html; charset=utf-8"/>
→<title></title>
→<meta name="generator" content="LibreOffice 5.2.3.3 (MacOSX)"/>
→<style type="text/css">
→→@page { margin: 2cm }
→→p { margin-bottom: 0.25cm; direction: ltr; line-height: 120%; text-align: left; orphans: 2; widows: 2 }
→</style>
</head>
<body lang="de-DE" dir="ltr">
<ul>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Equipment</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Chemicals</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Consumables</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Enzymes</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">GMO</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Antibodies</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">DNA
→Constructs</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">RNA
→Constructs</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Vectors</span></p>
→<li/>
<p style="margin-bottom: 0cm; line-height: 100%"><span lang="en-US">Oligos</span></p>
</ul>
</body>
</html>
````````````````````````````````


## Issue

Missing link text

```````````````````````````````` example Issue: 1
[Vladimir Reznichenko](https://intellij-support.jetbrains.com/hc/en-us/profiles/1010800390-Vladimir-Reznichenko)
.
<meta charset='utf-8'><a class="_color-black" href="https://intellij-support.jetbrains.com/hc/en-us/profiles/1010800390-Vladimir-Reznichenko" style="background-color: rgb(255, 255, 255); margin: 0px; padding: 0px; border: 0px; font-size: 14px; vertical-align: baseline; cursor: pointer; text-decoration: none; color: rgb(22, 22, 22); font-family: &quot;Gotham SSm A&quot;, &quot;Gotham SSm B&quot;, Helvetica, Arial, sans-serif; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: bold; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px;">Vladimir Reznichenko</a>
````````````````````````````````


## Issue 138

Issue #138, HTML to Markdown converter missing list end for two consecutive lists

```````````````````````````````` example Issue 138: 1
**Ordered**

1. Item 1
2. Item 2
3. Item 3

<!-- -->

1. [Example.com](http://www.example.com)
2. [Google](http://www.google.com)
3. [Yahoo!](http://www.yahoo.com)
4. [Another Example.com](http://www.example.com)
.
<p><strong>Ordered</strong></p>
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ol>
<ol>
  <li><a href="http://www.example.com">Example.com</a></li>
  <li><a href="http://www.google.com">Google</a></li>
  <li><a href="http://www.yahoo.com">Yahoo!</a></li>
  <li><a href="http://www.example.com">Another Example.com</a></li>
</ol>
````````````````````````````````


end on double blank line

```````````````````````````````` example(Issue 138: 2) options(list-break)
**Ordered**

1. Item 1
2. Item 2
3. Item 3


1. [Example.com](http://www.example.com)
2. [Google](http://www.google.com)
3. [Yahoo!](http://www.yahoo.com)
4. [Another Example.com](http://www.example.com)
.
<p><strong>Ordered</strong></p>
<ol>
  <li>Item 1</li>
  <li>Item 2</li>
  <li>Item 3</li>
</ol>
<ol>
  <li><a href="http://www.example.com">Example.com</a></li>
  <li><a href="http://www.google.com">Google</a></li>
  <li><a href="http://www.yahoo.com">Yahoo!</a></li>
  <li><a href="http://www.example.com">Another Example.com</a></li>
</ol>
````````````````````````````````


## Issue  149

Issue #149

```````````````````````````````` example Issue  149: 1
.
<div>
<strong>→</strong>
</div>
````````````````````````````````


```````````````````````````````` example Issue  149: 2
#### G2 \& G3: Controlled Arc Move\[[edit](https://duet3d.com/w2/index.php?title=G-code&action=edit&section=12 "Edit section: G2 & G3: Controlled Arc Move")\]

**Implemented in beta in 1.17c+2**

Usage
:   G2 Xnnn Ynnn Innn Jnnn Ennn Fnnn *(Clockwise Arc)*
:   G3 Xnnn Ynnn Innn Jnnn Ennn Fnnn *(Counter-Clockwise Arc)*

Parameters
:   **Xnnn** The position to move to on the X axis.
:   **Ynnn** The position to move to on the Y axis.
:   **Innn** The point in X space from the current X position to maintain a constant distance from.
:   **Jnnn** The point in Y space from the current Y position to maintain a constant distance from.
:   **Ennn** The amount to extrude between the starting point and ending point.
:   **Fnnn** The feedrate per minute of the move between the starting point and ending point (if supplied).

Examples
:   G2 X90.6 Y13.8 I5 J10 E22.4 *(Move in a Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)*
:   G3 X90.6 Y13.8 I5 J10 E22.4 *(Move in a Counter-Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)*

*** ** * ** ***

<br />

.
<meta charset='utf-8'><h4 style="box-sizing: border-box; font-family: Lato, Helvetica, Arial, sans-serif; font-weight: 300; line-height: 1.1; color: rgb(10, 10, 10); margin-top: 10px; margin-bottom: 10px; font-size: 28px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><span class="mw-headline" id="G2_.26_G3:_Controlled_Arc_Move" style="box-sizing: border-box;">G2 &amp; G3: Controlled Arc Move</span><span class="mw-editsection" style="box-sizing: border-box; user-select: none; font-size: small; font-weight: normal; margin-left: 1em; vertical-align: baseline; line-height: 1em; display: inline-block;"><span class="mw-editsection-bracket" style="box-sizing: border-box;">[</span><a
    href="https://duet3d.com/w2/index.php?title=G-code&amp;action=edit&amp;section=12" title="Edit section: G2 &amp; G3: Controlled Arc Move" style="box-sizing: border-box; background: transparent; color: rgb(35, 107, 155); text-decoration: none; transition: color 250ms ease-out, background-color 250ms ease-out, border-color 250ms ease-out; cursor: pointer;">edit</a><span class="mw-editsection-bracket" style="box-sizing: border-box;">]</span></span></h4><p
    style="box-sizing: border-box; margin: 0px 0px 10px; line-height: 1.5; color: rgb(10, 10, 10); font-family: Lato, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><b style="box-sizing: border-box; font-weight: bold;">Implemented in beta in 1.17c+2</b></p>
<dl style="box-sizing: border-box; margin-top: 0px; margin-bottom: 20px; color: rgb(10, 10, 10); font-family: Lato, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;">
  <dt style="box-sizing: border-box; line-height: 1.25; font-weight: bold;">Usage</dt>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G2 Xnnn Ynnn Innn Jnnn Ennn Fnnn<span> </span><i style="box-sizing: border-box;">(Clockwise Arc)</i></dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G3 Xnnn Ynnn Innn Jnnn Ennn Fnnn<span> </span><i style="box-sizing: border-box;">(Counter-Clockwise Arc)</i></dd>
  <dt style="box-sizing: border-box; line-height: 1.25; font-weight: bold;">Parameters</dt>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Xnnn</b><span> </span>The position to move to on the X axis.</dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Ynnn</b><span> </span>The position to move to on the Y axis.</dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Innn</b><span> </span>The point in X space from the current X position to maintain a constant distance from.</dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Jnnn</b><span> </span>The point in Y space from the current Y position to maintain a constant distance from.</dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Ennn</b><span> </span>The amount to extrude between the starting point and ending point.</dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Fnnn</b><span> </span>The feedrate per minute of the move between the starting point and ending point (if supplied).</dd>
  <dt style="box-sizing: border-box; line-height: 1.25; font-weight: bold;">Examples</dt>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G2 X90.6 Y13.8 I5 J10 E22.4<span> </span><i style="box-sizing: border-box;">(Move in a Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)</i></dd>
  <dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G3 X90.6 Y13.8 I5 J10 E22.4<span> </span><i style="box-sizing: border-box;">(Move in a Counter-Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)</i></dd>
</dl>
<hr style="box-sizing: content-box; height: 0px; margin-top: 20px; margin-bottom: 20px; border-width: 1px 0px 0px; border-right-style: initial; border-bottom-style: initial; border-left-style: initial; border-right-color: initial; border-bottom-color: initial; border-left-color: initial; border-image: initial; border-top-style: solid; border-top-color: rgb(238, 238, 238); color: rgb(10, 10, 10); font-family: Lato, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><br class="Apple-interchange-newline">
````````````````````````````````


```````````````````````````````` example Issue  149: 3
#### G2 \& G3: Controlled Arc Move\[[edit](https://duet3d.com/w2/index.php?title=G-code&action=edit&section=12 "Edit section: G2 & G3: Controlled Arc Move")\]

**Implemented in beta in 1.17c+2**

Usage
:   G2 Xnnn Ynnn Innn Jnnn Ennn Fnnn *(Clockwise Arc)*
:   G3 Xnnn Ynnn Innn Jnnn Ennn Fnnn *(Counter-Clockwise Arc)*

Parameters
:   **Xnnn** The position to move to on the X axis.
:   **Ynnn** The position to move to on the Y axis.
:   **Innn** The point in X space from the current X position to maintain a constant distance from.
:   **Jnnn** The point in Y space from the current Y position to maintain a constant distance from.
:   **Ennn** The amount to extrude between the starting point and ending point.
:   **Fnnn** The feedrate per minute of the move between the starting point and ending point (if supplied).

Examples
:   G2 X90.6 Y13.8 I5 J10 E22.4 *(Move in a Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)*
:   G3 X90.6 Y13.8 I5 J10 E22.4 *(Move in a Counter-Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)*

*** ** * ** ***

<br />

.
<meta charset='utf-8'><h4 style="box-sizing: border-box; font-family: Lato, Helvetica, Arial, sans-serif; font-weight: 300; line-height: 1.1; color: rgb(10, 10, 10); margin-top: 10px; margin-bottom: 10px; font-size: 28px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><span class="mw-headline" id="G2_.26_G3:_Controlled_Arc_Move" style="box-sizing: border-box;">G2 &amp; G3: Controlled Arc Move</span><span class="mw-editsection" style="box-sizing: border-box; user-select: none; font-size: small; font-weight: normal; margin-left: 1em; vertical-align: baseline; line-height: 1em; display: inline-block;"><span class="mw-editsection-bracket" style="box-sizing: border-box;">[</span><a href="https://duet3d.com/w2/index.php?title=G-code&amp;action=edit&amp;section=12" title="Edit section: G2 &amp; G3: Controlled Arc Move" style="box-sizing: border-box; background: transparent; color: rgb(35, 107, 155); text-decoration: none; transition: color 250ms ease-out, background-color 250ms ease-out, border-color 250ms ease-out; cursor: pointer;">edit</a><span class="mw-editsection-bracket" style="box-sizing: border-box;">]</span></span></h4><p style="box-sizing: border-box; margin: 0px 0px 10px; line-height: 1.5; color: rgb(10, 10, 10); font-family: Lato, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><b style="box-sizing: border-box; font-weight: bold;">Implemented in beta in 1.17c+2</b></p><dl style="box-sizing: border-box; margin-top: 0px; margin-bottom: 20px; color: rgb(10, 10, 10); font-family: Lato, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><dt style="box-sizing: border-box; line-height: 1.25; font-weight: bold;">Usage</dt><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G2 Xnnn Ynnn Innn Jnnn Ennn Fnnn<span> </span><i style="box-sizing: border-box;">(Clockwise Arc)</i></dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G3 Xnnn Ynnn Innn Jnnn Ennn Fnnn<span> </span><i style="box-sizing: border-box;">(Counter-Clockwise Arc)</i></dd><dt style="box-sizing: border-box; line-height: 1.25; font-weight: bold;">Parameters</dt><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Xnnn</b><span> </span>The position to move to on the X axis.</dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Ynnn</b><span> </span>The position to move to on the Y axis.</dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Innn</b><span> </span>The point in X space from the current X position to maintain a constant distance from.</dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Jnnn</b><span> </span>The point in Y space from the current Y position to maintain a constant distance from.</dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Ennn</b><span> </span>The amount to extrude between the starting point and ending point.</dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;"><b style="box-sizing: border-box; font-weight: bold;">Fnnn</b><span> </span>The feedrate per minute of the move between the starting point and ending point (if supplied).</dd><dt style="box-sizing: border-box; line-height: 1.25; font-weight: bold;">Examples</dt><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G2 X90.6 Y13.8 I5 J10 E22.4<span> </span><i style="box-sizing: border-box;">(Move in a Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)</i></dd><dd style="box-sizing: border-box; line-height: 1.25; margin-left: 1.6em; margin-right: 0px;">G3 X90.6 Y13.8 I5 J10 E22.4<span> </span><i style="box-sizing: border-box;">(Move in a Counter-Clockwise arc from the current point to point (X=90.6,Y=13.8), with a center point at (X=current_X+5, Y=current_Y+10), extruding 22.4mm of material between starting and stopping)</i></dd></dl><hr style="box-sizing: content-box; height: 0px; margin-top: 20px; margin-bottom: 20px; border-width: 1px 0px 0px; border-right-style: initial; border-bottom-style: initial; border-left-style: initial; border-right-color: initial; border-bottom-color: initial; border-left-color: initial; border-image: initial; border-top-style: solid; border-top-color: rgb(238, 238, 238); color: rgb(10, 10, 10); font-family: Lato, Helvetica, Arial, sans-serif; font-size: 16px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 300; letter-spacing: normal; orphans: 2; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-style: initial; text-decoration-color: initial;"><br class="Apple-interchange-newline">
````````````````````````````````


## Issue

Issue #, image alt text not escaped for `[` and `]` in the text.

```````````````````````````````` example Issue: 1
![\[CPU-memory connection\]](cpu-mem.svg)
.
<html>
<body>
  <p><img src="cpu-mem.svg" alt="[CPU-memory connection]" border="0"></p>
</body>
</html>
````````````````````````````````


make sure links do the same

```````````````````````````````` example Issue: 2
[\[CPU-memory connection\]](cpu-mem.svg)
.
<html>
<body>
  <p><a href="cpu-mem.svg">[CPU-memory connection]</a></p>
</body>
</html>
````````````````````````````````


## Issue pre/tt

Issue pre/tt, convert <pre><tt> to indented code

```````````````````````````````` example Issue pre/tt: 1
Test


            _____     _           
           / ____|   (_)          
     _   _| |     ___ _ _ __ ___  
    | | | | |    / __| | '_ ` _ \\ 
    | |_| | |____\\__ \\ | | | | | |
    \\ ._,_|\\_____|___/_|_| |_| |_|
    | |                           
    |_|                           

.
<p>Test</p>
<pre><tt><font size="+1">
        _____     _           
       / ____|   (_)          
 _   _| |     ___ _ _ __ ___  
| | | | |    / __| | '_ ` _ \\ 
| |_| | |____\\__ \\ | | | | | |
\\ ._,_|\\_____|___/_|_| |_| |_|
| |                           
|_|                           
</font></tt></pre>
````````````````````````````````


## Issue MN-610

Options to clean-up paste, #610, hope can have more paste option

```````````````````````````````` example Issue MN-610: 1
### **[vsch](https://github.com/vsch)** commented [on Jun 29](https://github.com/vsch/idea-multimarkdown/issues/610#issuecomment-401358065)

.
<h3 class="timeline-comment-header-text f5 text-normal" style="box-sizing: border-box; margin-top: 0px; margin-bottom: 0px; font-size: 14px !important; font-weight: 400 !important; max-width: 78%; padding-top: 10px; padding-bottom: 10px; caret-color: rgb(88, 96, 105); color: rgb(88, 96, 105); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-caps: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(241, 248, 255); text-decoration: none;"><strong class="css-truncate" style="box-sizing: border-box; font-weight: 600;"><a class="author text-inherit css-truncate-target" data-hovercard-user-id="10299086" data-octo-click="hovercard-link-click" data-octo-dimensions="link_type:self" href="https://github.com/vsch" aria-describedby="hovercard-aria-description" style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105); text-decoration: none; display: inline-block; max-width: 125px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;">vsch</a><span class="Apple-converted-space"> </span></strong>commented<span class="Apple-converted-space"> </span><a href="https://github.com/vsch/idea-multimarkdown/issues/610#issuecomment-401358065" class="timestamp" style="box-sizing: border-box; background-color: transparent; color: inherit; text-decoration: none; white-space: nowrap;"><relative-time datetime="2018-06-29T13:42:53Z" title="Jun 29, 2018, 9:42 AM GMT-4" style="box-sizing: border-box;">on Jun 29</relative-time></a></h3>
````````````````````````````````


skip attributes

```````````````````````````````` example(Issue MN-610: 2) options(skip-heading-3, text-ext-inline-strong)
[vsch](https://github.com/vsch) commented [on Jun 29](https://github.com/vsch/idea-multimarkdown/issues/610#issuecomment-401358065)
.
<h3 class="timeline-comment-header-text f5 text-normal" style="box-sizing: border-box; margin-top: 0px; margin-bottom: 0px; font-size: 14px !important; font-weight: 400 !important; max-width: 78%; padding-top: 10px; padding-bottom: 10px; caret-color: rgb(88, 96, 105); color: rgb(88, 96, 105); font-family: -apple-system, BlinkMacSystemFont, &quot;Segoe UI&quot;, Helvetica, Arial, sans-serif, &quot;Apple Color Emoji&quot;, &quot;Segoe UI Emoji&quot;, &quot;Segoe UI Symbol&quot;; font-style: normal; font-variant-caps: normal; letter-spacing: normal; orphans: auto; text-align: start; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(241, 248, 255); text-decoration: none;"><strong class="css-truncate" style="box-sizing: border-box; font-weight: 600;"><a class="author text-inherit css-truncate-target" data-hovercard-user-id="10299086" data-octo-click="hovercard-link-click" data-octo-dimensions="link_type:self" href="https://github.com/vsch" aria-describedby="hovercard-aria-description" style="box-sizing: border-box; background-color: transparent; color: rgb(88, 96, 105); text-decoration: none; display: inline-block; max-width: 125px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; vertical-align: top;">vsch</a><span class="Apple-converted-space"> </span></strong>commented<span class="Apple-converted-space"> </span><a href="https://github.com/vsch/idea-multimarkdown/issues/610#issuecomment-401358065" class="timestamp" style="box-sizing: border-box; background-color: transparent; color: inherit; text-decoration: none; white-space: nowrap;"><relative-time datetime="2018-06-29T13:42:53Z" title="Jun 29, 2018, 9:42 AM GMT-4" style="box-sizing: border-box;">on Jun 29</relative-time></a></h3>
````````````````````````````````


## Issue 268

Issue #268

```````````````````````````````` example Issue 268: 1
abc\|\[\]

| th\| |
|------|
| tr\| |
| ---- |
[\[\]caption]

.
<html>
<body>
abc|[]
<table>
  <caption>[]caption</caption>
  <tr>
    <th>th|</th>
  </tr>
  <tr>
    <td>tr|</td>
  </tr>
  <tr>
    <td>----</td>
  </tr>
</table>
</body>
</html>
````````````````````````````````


## Issue 274

Issue #274, FlexmarkHtmlParser can not handle escaped tags correctly

```````````````````````````````` example Issue 274: 1
\<em\>abc\</em\>

| \<s\>th\</s\> |
|---------------|
| \<u\>tr\</u\> |
| \<i\>tr\</i\> |
| \<\>          |
| \\            |
[\<b\>caption\</b\>]

.
<html><body>
&lt;em&gt;abc&lt;/em&gt;
<table>
<caption>&lt;b&gt;caption&lt;/b&gt;</caption>
<tr><th>&lt;s&gt;th&lt;/s&gt;</th></tr>
<tr><td>&lt;u&gt;tr&lt;/u&gt;</td></tr>
<tr><td>&lt;i&gt;tr&lt;/i&gt;</td></tr>
<tr><td>&lt;&gt;</td></tr>
<tr><td>\</td></tr>
</table>
</body></html>
````````````````````````````````


## Issue 287

Issue #287, ''flexmark-html-parser' The module has an mistake

```````````````````````````````` example Issue 287: 1
[flexmark-javaa\>](https://github.com/vsch/flexmark-java)
.
<a href="https://github.com/vsch/flexmark-java">flexmark-java</<!-- [[[read_end]]] -->a>
````````````````````````````````


## Issue 299

Issue #299, FlexmarkHtmlParser produces extra empty list item for enclosing </p> element

```````````````````````````````` example Issue 299: 1
**Note:**
* First note.
* Second note.

.
<p><b>Note:</b>
<li>First note.</li>
<li>Second note.</li>
</p>
````````````````````````````````


## Issue 317

Issue #317

```````````````````````````````` example Issue 317: 1
1. P1
2. P2
   1. P2-1
3. P3
.
<ol>
    <li>P1</li>
    <li>P2
        <ol>
            <li>P2-1</li>
        </ol>
    </li>
    <li>P3</li>
</ol>
````````````````````````````````


## Issue 326

Issue #326, flexmark-html-parser - multiple \<code\> inside \<pre\> bug

```````````````````````````````` example Issue 326: 1
    casa
    asdf
    ffdd

.
<pre><code>casa</code><br><code>asdf</code><br>ffdd</pre>
````````````````````````````````


## Issue xxx-01

links in indented code should be converted to URL

```````````````````````````````` example Issue xxx-01: 1
    \```java
      System.out.println("Hello world");
    \```
    This is [Sparta](http://sparta.com#anchor)

.
<pre><code>\```java
  System.out.println("Hello world");
\```
This is [Sparta](<a href="http://sparta.com/#anchor">http://sparta.com#anchor</a>)
````````````````````````````````


## Issue 351

Issue [#351, Is there any special format requirement for processing html data to markdown]

```````````````````````````````` example Issue 351: 1
[test](/)
=========

.
<!doctype html>
<html>
<head>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
</head>
<body>
<div class="container logo-search">
    <div class="col search row-search-mobile">
        <form action="index.html">
            <input autocomplete="off" class="placeholder" name="s" placeholder="search">
        </form>
    </div>
    <div class="row">
        <div class="col logo">
            <h1><a href="/">test</a></h1>
        </div>
        <div class="col right-list">
            <button class="btn btn-responsive-nav btn-inverse" data-target=".nav-main-collapse" data-toggle="collapse"
                    id="pull" style=""><i class="fa fa-navicon"></i></button>
        </div>
        <div class="col search search-desktop last">
            <form action="#last" target="_blank">
                <input autocomplete="off" class="placeholder" id="s" name="s" placeholder="search">
            </form>
        </div>
    </div>
</div>
</body>
</html>
````````````````````````````````


## Issue 353

Issue [#353, How to self modify parse method when htmltomarkdown] , How to self modify parse
method when htmltomarkdown

```````````````````````````````` example Issue 353: 1
![](//img.alicdn.com/tfscom/TB1mR4xPpXXXXXvapXXXXXXXXXX.jpg)
.
<img src="//img.alicdn.com/tfscom/TB1mR4xPpXXXXXvapXXXXXXXXXX.jpg" >
````````````````````````````````


## Issue 331

Issue #331

```````````````````````````````` example Issue 331: 1
1

<br />

2
.
<p>1</p>
<p></p>
<p>2</p>
````````````````````````````````


## Issue 328

Issue #328

```````````````````````````````` example Issue 328: 1
Paragraph 1  
Paragraph 2
.
<p>Paragraph 1</p>
<div>Paragraph 2</div>
````````````````````````````````


DIV_AS_PARAGRAPH true

```````````````````````````````` example(Issue 328: 2) options(div-as-para)
Paragraph 1

Paragraph 2
.
<p>Paragraph 1</p>
<div>Paragraph 2</div>
````````````````````````````````


```````````````````````````````` example Issue 328: 3
Paragraph 1  
Text  
Paragraph 2
.
<div>Paragraph 1</div>
<div>
    Text
    <div>Paragraph 2</div>
</div>
````````````````````````````````


DIV_AS_PARAGRAPH true

```````````````````````````````` example(Issue 328: 4) options(div-as-para)
Paragraph 1

Text

Paragraph 2
.
<div>Paragraph 1</div>
<div>
    Text
    <div>Paragraph 2</div>
</div>
````````````````````````````````


## Issue 348

Issue #348, wrap auto links needs to be on to convert auto-links

```````````````````````````````` example(Issue 348: 1) options(no-wrap-autolinks)
This doesn't get rendered: https://google.com [This does.](https://google.com)
.
<p>This doesn't get rendered: <a href="https://google.com" rel="nofollow">https://google.com</a> <a href="https://google.com" rel="nofollow">This does.</a></p> 
````````````````````````````````


default to wrap

```````````````````````````````` example Issue 348: 2
This doesn't get rendered: <https://google.com> [This does.](https://google.com)
.
<p>This doesn't get rendered: <a href="https://google.com" rel="nofollow">https://google.com</a> <a href="https://google.com" rel="nofollow">This does.</a></p> 
````````````````````````````````


```````````````````````````````` example(Issue 348: 3) options(wrap-autolinks)
This doesn't get rendered: <https://google.com> [This does.](https://google.com)
.
<p>This doesn't get rendered: <a href="https://google.com" rel="nofollow">https://google.com</a> <a href="https://google.com" rel="nofollow">This does.</a></p> 
````````````````````````````````


## Issue 357

Issue [#357, HTML to markdown and removed nested list]

```````````````````````````````` example Issue 357: 1
* 1
* 2
  * 2.1
* 3
* 4
  * 4.1
* 5
.
<ul> 
 <li>1</li> 
 <li>2</li> 
 <ul>
  <li>2.1</li>
 </ul> 
 <li>3</li> 
 <li>4
  <ul> 
   <li>4.1</li> 
  </ul> 
 </li> 
 <li>5</li> 
</ul>
````````````````````````````````


```````````````````````````````` example Issue 357: 2
* 2.1
* 1
* 2
* 3
* 4
  * 4.1
* 5
.
<ul> 
 <ul>
  <li>2.1</li>
 </ul> 
 <li>1</li> 
 <li>2</li> 
 <li>3</li> 
 <li>4
  <ul> 
   <li>4.1</li> 
  </ul> 
 </li> 
 <li>5</li> 
</ul>
````````````````````````````````


## Issue 548

Issue [#548, Issue parsing Facebook img/emojis], Images with emoji-filenames break the emoji
handling code if they have no corresponding shortcut

```````````````````````````````` example Issue 548: 1
This is my test to a Facebook emoji:  
![text](https://static.xx.fbcdn.net/images/emoji.php/v9/t71/2/16/1f967.png)
.
<div>
<div>This is my test to a Facebook emoji:</div>
<div><img src="https://static.xx.fbcdn.net/images/emoji.php/v9/t71/2/16/1f967.png" alt="text" width="24" height="24"></div>
</div>
````````````````````````````````


```````````````````````````````` example Issue 548: 2
![](1f9a0.png)
.
<img src="1f9a0.png" />
````````````````````````````````


[#351, Is there any special format requirement for processing html data to markdown]: https://github.com/vsch/flexmark-java/issues/351
[#353, How to self modify parse method when htmltomarkdown]: https://github.com/vsch/flexmark-java/issues/353
[#357, HTML to markdown and removed nested list]: https://github.com/vsch/flexmark-java/issues/357
[#548, Issue parsing Facebook img/emojis]: https://github.com/vsch/flexmark-java/issues/548

