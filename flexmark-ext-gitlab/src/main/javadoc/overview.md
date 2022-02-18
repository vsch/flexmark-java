**flexmark-java extension for GitLab Flavoured Markdown**

Parses and renders GitLab Flavoured Markdown.

* Video link renderer to convert links to video files to embedded content. The valid video
  extensions are `.mp4`, `.m4v`, `.mov`, `.webm`, and `.ogv`.

  ```
  <div class="video-container">
  <video src="video.mp4" width="400" controls="true"></video>
  <p><a href="video.mp4" target="_blank" rel="noopener noreferrer" title="Download 'Sample Video'">Sample Video</a></p>
  </div>
  ```

* Multiline Block quote delimiters `>>>`

* Deleted text markers `{- -}` or `[- -]`

* Inserted text markers `{+ +}` or `[+ +]`

* Header ids with emoji shortcuts generate an extra `-` because of two spaces around the emoji
  shortcut while GitLab only generates a single `-`. (via no duplicate `-` in ids)

* Math, inline via ```$``$``` or as fenced code with `math` info string requiring inclusion of
  Katex in the rendered HTML page.

* Graphing via Mermaid as fenced code with `mermaid` info string, via Mermaid inclusion similar
  to Math solution above.

