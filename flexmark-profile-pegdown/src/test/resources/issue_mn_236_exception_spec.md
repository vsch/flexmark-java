---
title: Pegdown with Extensions Compatibility Spec
author: Vladimir Schneider
version: 0.2
date: '2017-01-07'
license: '[CC-BY-SA 4.0](http://creativecommons.org/licenses/by-sa/4.0/)'
...

---

## Stack Overflow in parser

```````````````````````````````` example Stack Overflow in parser: 1
[![Build Status](https://travis-ci.org/TypeStrong/ts-loader.svg?branch=master)](https://travis-ci.org/TypeStrong/ts-loader)
[![Build Status](https://ci.appveyor.com/api/projects/status/bjh0r0d4ckspgkh9/branch/master?svg=true)](https://ci.appveyor.com/project/JohnReilly/ts-loader/branch/master)
[![Downloads](http://img.shields.io/npm/dm/ts-loader.svg)](https://npmjs.org/package/ts-loader)
[![Join the chat at https://gitter.im/TypeStrong/ts-loader](https://img.shields.io/badge/gitter-join%20chat-brightgreen.svg)](https://gitter.im/TypeStrong/ts-loader)

# TypeScript loader for webpack

This is the typescript loader for webpack.

## Getting Started

Tutorials and examples can be [found here](https://github.com/TypeStrong/ts-loader/wiki/Tutorials-&-Examples).  Also take a look at our [examples](examples/).

### Compatibility

#### TypeScript

ts-loader supports the latest and greatest version of TypeScript right back to v1.6.  (Including the [nightly build](http://blogs.msdn.com/b/typescript/archive/2015/07/27/introducing-typescript-nightlies.aspx).)

A full test suite runs each night (and on each pull request). It runs both on Linux ([Travis](https://travis-ci.org/)) and Windows ([AppVeyor](https://www.appveyor.com/)), testing ts-loader against the following versions of TypeScript:
- TypeScript 2.1
- TypeScript 2.0
- TypeScript 1.8
- TypeScript 1.7
- TypeScript 1.6

and also:
- TypeScript@next (because we want to use it as much as you do)

If you become aware of issues not caught by the test suite then please let us know. Better yet, write a test and submit it in a PR!

#### Webpack

ts-loader is designed for Webpack 1.x.  All our CI tests run against that.  Webpack 2.0 is on the way and we're excited.  When it's released we'll look to target it.  In the meantime, people have been using webpack 2.0 with ts-loader with some success.  

[There's a known "gotcha"](https://github.com/TypeStrong/ts-loader/issues/283) if you are using webpack 2 with the `LoaderOptionsPlugin`.  If you are faced with the `Cannot read property 'unsafeCache' of undefined` error then you probably need to supply a `resolve` object as below: (Thanks @jeffijoe!)

```js
new LoaderOptionsPlugin({
  debug: false,
  options: {
    resolve: {} // super important by all accounts...
  }
})
```

### Babel

ts-loader works very well in combination with [babel](https://babeljs.io/) and [babel-loader](https://github.com/babel/babel-loader).  To see an example of this in practice take a look at the [example](https://github.com/Microsoft/TypeScriptSamples/tree/master/react-flux-babel-karma) in the official [TypeScript Samples](https://github.com/Microsoft/TypeScriptSamples) or in our [examples](examples/).

### Contributing

This is your TypeScript loader! We want you to help make it even better. Please feel free to contribute; see the [contributor's guide](CONTRIBUTING.md) to get started.

### Installation

```
npm install ts-loader
```

You will also need to install TypeScript if you have not already.

```
npm install typescript
```

or if you want to install TypeScript globally

```
npm install typescript -g
npm link typescript
```

### Running

Use webpack like normal, including `webpack --watch` and `webpack-dev-server`, or through another
build system using the [Node.js API](http://webpack.github.io/docs/node.js-api.html).

### Configuration

1. Create or update `webpack.config.js` like so:

    ```javascript
    module.exports = {
      entry: './app.ts',
      output: {
        filename: 'bundle.js'
      },
      resolve: {
        // Add `.ts` and `.tsx` as a resolvable extension.
        extensions: ['', '.webpack.js', '.web.js', '.ts', '.tsx', '.js']
      },
      module: {
        loaders: [
          // all files with a `.ts` or `.tsx` extension will be handled by `ts-loader`
          { test: /\.tsx?$/, loader: 'ts-loader' }
        ]
      }
    }
    ```

2. Add a [`tsconfig.json`](https://www.typescriptlang.org/docs/handbook/tsconfig-json.html) file. (The one below is super simple; but you can tweak this to your hearts desire)

    ```json
    {
      "compilerOptions": {
      }
    }
    ```

The [tsconfig.json](http://www.typescriptlang.org/docs/handbook/tsconfig-json.html) file controls
TypeScript-related options so that your IDE, the `tsc` command, and this loader all share the
same options.

### Failing the build on TypeScript compilation error

When the build fails (i.e. at least one typescript compile error occured), ts-loader does **not** propagate the build failure to webpack.  The upshot of this is you can fail to notice an erroring build. This is inconvenient; particularly in continuous integration scenarios.  If you want to ensure that the build failure is propogated it is advised that you make use of the [webpack-fail-plugin](https://www.npmjs.com/package/webpack-fail-plugin).  This plugin that will make the process return status code 1 when it finishes with errors in single-run mode. Et voilà! Build failure.

For more background have a read of [this issue](https://github.com/TypeStrong/ts-loader/issues/108).

### Upgrading

Take advantage of the [Changelog](CHANGELOG.md) and [Upgrade Guide](UPGRADE.md).

#### Options

There are two types of options: TypeScript options (aka "compiler options") and loader options.
TypeScript options should be set using a tsconfig.json file. Loader options can be set either
using a query when specifying the loader or through the `ts` property in the webpack configuration.

```javascript
module.exports = {
  ...
  module: {
    loaders: [
      // specify option using query
      { test: /\.tsx?$/, loader: 'ts-loader?compiler=ntypescript' }
    ]
  },
  // specify option using `ts` property
  ts: {
    compiler: 'ntypescript'
  }
}
```

##### transpileOnly *(boolean) (default=false)*

If you want to speed up compilation significantly you can set this flag.
However, many of the benefits you get from static type checking between
different dependencies in your application will be lost. You should also
set the `isolatedModules` TypeScript option if you plan to ever make use
of this.

##### logInfoToStdOut *(boolean) (default=false)*

This is important if you read from stdout or stderr and for proper error handling.
The default value ensures that you can read from stdout e.g. via pipes or you use webpack -j to generate json output.

##### logLevel *(string) (default=info)*

Can be `info`, `warn` or `error` which limits the log output to the specified log level.
Beware of the fact that errors are written to stderr and everything else is written to stderr (or stdout if logInfoToStdOut is true).

##### silent *(boolean) (default=false)*

If true, no console.log messages will be emitted. Note that most error
messages are emitted via webpack which is not affected by this flag.

##### ignoreDiagnostics *(number[]) (default=[])*

You can squelch certain TypeScript errors by specifying an array of diagnostic
codes to ignore.

##### compiler *(string) (default='typescript')*

Allows use of TypeScript compilers other than the official one. Should be
set to the NPM name of the compiler, eg [`ntypescript`](https://github.com/basarat/ntypescript).

##### configFileName *(string) (default='tsconfig.json')*

Allows you to specify a custom configuration file.

##### visualStudioErrorFormat *(boolean) (default=false)*

If `true`, the TypeScript compiler output for an error or a warning, e.g. `(3,14): error TS4711: you did something very wrong`, in file `myFile` will instead be `myFile(3,14): error TS4711: you did something very wrong` (notice the file name at the beginning). This way Visual Studio will interpret this line and show any errors or warnings in the *error list*. This enables navigation to the file/line/column through double click.

##### compilerOptions *(object) (default={})*

Allows overriding TypeScript options. Should be specified in the same format
as you would do for the `compilerOptions` property in tsconfig.json.

##### instance *(string)*

Advanced option to force files to go through different instances of the
TypeScript compiler. Can be used to force segregation between different parts
of your code.

#### entryFileIsJs *(boolean) (default=false)*

To be used in concert with the `allowJs` compiler option. If your entry file is JS then you'll need to set this option to true.  Please note that this is rather unusual and will generally not be necessary when using `allowJs`.

#### appendTsSuffixTo *(RegExp[]) (default=[])*
A list of regular expressions to be matched against filename. If filename matches one of the regular expressions, a `.ts` suffix will be appended to that filename.

This is useful for `*.vue` [file format](https://vuejs.org/v2/guide/single-file-components.html) for now. (Probably will benefit from the new single file format in the future.)

Example:

webpack.config.js:

```javascript
module.exports = {
    entry: './index.vue',
    output: { filename: 'bundle.js' },
    resolve: {
        extensions: ['', '.ts', '.vue']
    },
    module: {
        loaders: [
            { test: /\.vue$/, loader: 'vue' },
            { test: /\.ts$/, loader: 'ts' }
        ]
    },
    ts: {
      appendTsSuffixTo: [/\.vue$/]
    }
}
```

index.vue

```vue
<template><p>hello {{msg}}</p></template>
<script lang="ts">
export default {
  data(): Object {
    return {
      msg: "world"
    }
  },
}
</script>
```


### Loading other resources and code splitting

Loading css and other resources is possible but you will need to make sure that
you have defined the `require` function in a [declaration file](https://www.typescriptlang.org/docs/handbook/writing-declaration-files.html).

```typescript
declare var require: {
    <T>(path: string): T;
    (paths: string[], callback: (...modules: any[]) => void): void;
    ensure: (paths: string[], callback: (require: <T>(path: string) => T) => void) => void;
};
```

Then you can simply require assets or chunks per the [webpack documentation](http://webpack.github.io/docs).

```js
require('!style!css!./style.css');
```

The same basic process is required for code splitting. In this case, you `import` modules you need but you
don't directly use them. Instead you require them at [split points](http://webpack.github.io/docs/code-splitting.html#defining-a-split-point).
See [this example](test/comparison-tests/codeSplitting) and [this example](test/comparison-tests/es6codeSplitting) for more details.

## License

MIT License

.
<p><a href="https://travis-ci.org/TypeStrong/ts-loader"><img src="https://travis-ci.org/TypeStrong/ts-loader.svg?branch=master" alt="Build Status" /></a> <a href="https://ci.appveyor.com/project/JohnReilly/ts-loader/branch/master"><img src="https://ci.appveyor.com/api/projects/status/bjh0r0d4ckspgkh9/branch/master?svg=true" alt="Build Status" /></a> <a href="https://npmjs.org/package/ts-loader"><img src="http://img.shields.io/npm/dm/ts-loader.svg" alt="Downloads" /></a> <a href="https://gitter.im/TypeStrong/ts-loader"><img src="https://img.shields.io/badge/gitter-join%20chat-brightgreen.svg" alt="Join the chat at https://gitter.im/TypeStrong/ts-loader" /></a></p>
<h1><a href="#typescript-loader-for-webpack" id="typescript-loader-for-webpack">TypeScript loader for webpack</a></h1>
<p>This is the typescript loader for webpack.</p>
<h2><a href="#getting-started" id="getting-started">Getting Started</a></h2>
<p>Tutorials and examples can be <a href="https://github.com/TypeStrong/ts-loader/wiki/Tutorials-&amp;-Examples">found here</a>.  Also take a look at our <a href="examples/">examples</a>.</p>
<h3><a href="#compatibility" id="compatibility">Compatibility</a></h3>
<h4><a href="#typescript" id="typescript">TypeScript</a></h4>
<p>ts-loader supports the latest and greatest version of TypeScript right back to v1.6.  (Including the <a href="http://blogs.msdn.com/b/typescript/archive/2015/07/27/introducing-typescript-nightlies.aspx">nightly build</a>.)</p>
<p>A full test suite runs each night (and on each pull request). It runs both on Linux (<a href="https://travis-ci.org/">Travis</a>) and Windows (<a href="https://www.appveyor.com/">AppVeyor</a>), testing ts-loader against the following versions of TypeScript: - TypeScript 2.1 - TypeScript 2.0 - TypeScript 1.8 - TypeScript 1.7 - TypeScript 1.6</p>
<p>and also: - TypeScript@next (because we want to use it as much as you do)</p>
<p>If you become aware of issues not caught by the test suite then please let us know. Better yet, write a test and submit it in a PR!</p>
<h4><a href="#webpack" id="webpack">Webpack</a></h4>
<p>ts-loader is designed for Webpack 1.x.  All our CI tests run against that.  Webpack 2.0 is on the way and we&rsquo;re excited.  When it&rsquo;s released we&rsquo;ll look to target it.  In the meantime, people have been using webpack 2.0 with ts-loader with some success.</p>
<p><a href="https://github.com/TypeStrong/ts-loader/issues/283">There&rsquo;s a known &ldquo;gotcha&rdquo;</a> if you are using webpack 2 with the <code>LoaderOptionsPlugin</code>.  If you are faced with the <code>Cannot read property 'unsafeCache' of undefined</code> error then you probably need to supply a <code>resolve</code> object as below: (Thanks @jeffijoe!)</p>
<pre><code class="js">new LoaderOptionsPlugin({
  debug: false,
  options: {
    resolve: {} // super important by all accounts...
  }
})
</code></pre>
<h3><a href="#babel" id="babel">Babel</a></h3>
<p>ts-loader works very well in combination with <a href="https://babeljs.io/">babel</a> and <a href="https://github.com/babel/babel-loader">babel-loader</a>.  To see an example of this in practice take a look at the <a href="https://github.com/Microsoft/TypeScriptSamples/tree/master/react-flux-babel-karma">example</a> in the official <a href="https://github.com/Microsoft/TypeScriptSamples">TypeScript Samples</a> or in our <a href="examples/">examples</a>.</p>
<h3><a href="#contributing" id="contributing">Contributing</a></h3>
<p>This is your TypeScript loader! We want you to help make it even better. Please feel free to contribute; see the <a href="CONTRIBUTING.md">contributor&rsquo;s guide</a> to get started.</p>
<h3><a href="#installation" id="installation">Installation</a></h3>
<pre><code>npm install ts-loader
</code></pre>
<p>You will also need to install TypeScript if you have not already.</p>
<pre><code>npm install typescript
</code></pre>
<p>or if you want to install TypeScript globally</p>
<pre><code>npm install typescript -g
npm link typescript
</code></pre>
<h3><a href="#running" id="running">Running</a></h3>
<p>Use webpack like normal, including <code>webpack --watch</code> and <code>webpack-dev-server</code>, or through another build system using the <a href="http://webpack.github.io/docs/node.js-api.html">Node.js API</a>.</p>
<h3><a href="#configuration" id="configuration">Configuration</a></h3>
<ol>
  <li>
    <p>Create or update <code>webpack.config.js</code> like so:</p>
    <pre><code class="javascript">module.exports = {
  entry: './app.ts',
  output: {
    filename: 'bundle.js'
  },
  resolve: {
    // Add `.ts` and `.tsx` as a resolvable extension.
    extensions: ['', '.webpack.js', '.web.js', '.ts', '.tsx', '.js']
  },
  module: {
    loaders: [
      // all files with a `.ts` or `.tsx` extension will be handled by `ts-loader`
      { test: /\.tsx?$/, loader: 'ts-loader' }
    ]
  }
}
</code></pre>
  </li>
  <li>
    <p>Add a <a href="https://www.typescriptlang.org/docs/handbook/tsconfig-json.html"><code>tsconfig.json</code></a> file. (The one below is super simple; but you can tweak this to your hearts desire)</p>
    <pre><code class="json">{
  &quot;compilerOptions&quot;: {
  }
}
</code></pre>
  </li>
</ol>
<p>The <a href="http://www.typescriptlang.org/docs/handbook/tsconfig-json.html">tsconfig.json</a> file controls TypeScript-related options so that your IDE, the <code>tsc</code> command, and this loader all share the same options.</p>
<h3><a href="#failing-the-build-on-typescript-compilation-error" id="failing-the-build-on-typescript-compilation-error">Failing the build on TypeScript compilation error</a></h3>
<p>When the build fails (i.e. at least one typescript compile error occured), ts-loader does <strong>not</strong> propagate the build failure to webpack.  The upshot of this is you can fail to notice an erroring build. This is inconvenient; particularly in continuous integration scenarios.  If you want to ensure that the build failure is propogated it is advised that you make use of the <a href="https://www.npmjs.com/package/webpack-fail-plugin">webpack-fail-plugin</a>.  This plugin that will make the process return status code 1 when it finishes with errors in single-run mode. Et voilà! Build failure.</p>
<p>For more background have a read of <a href="https://github.com/TypeStrong/ts-loader/issues/108">this issue</a>.</p>
<h3><a href="#upgrading" id="upgrading">Upgrading</a></h3>
<p>Take advantage of the <a href="CHANGELOG.md">Changelog</a> and <a href="UPGRADE.md">Upgrade Guide</a>.</p>
<h4><a href="#options" id="options">Options</a></h4>
<p>There are two types of options: TypeScript options (aka &ldquo;compiler options&rdquo;) and loader options. TypeScript options should be set using a tsconfig.json file. Loader options can be set either using a query when specifying the loader or through the <code>ts</code> property in the webpack configuration.</p>
<pre><code class="javascript">module.exports = {
  ...
  module: {
    loaders: [
      // specify option using query
      { test: /\.tsx?$/, loader: 'ts-loader?compiler=ntypescript' }
    ]
  },
  // specify option using `ts` property
  ts: {
    compiler: 'ntypescript'
  }
}
</code></pre>
<h5><a href="#transpileonly-boolean-defaultfalse" id="transpileonly-boolean-defaultfalse">transpileOnly <em>(boolean) (default=false)</em></a></h5>
<p>If you want to speed up compilation significantly you can set this flag. However, many of the benefits you get from static type checking between different dependencies in your application will be lost. You should also set the <code>isolatedModules</code> TypeScript option if you plan to ever make use of this.</p>
<h5><a href="#loginfotostdout-boolean-defaultfalse" id="loginfotostdout-boolean-defaultfalse">logInfoToStdOut <em>(boolean) (default=false)</em></a></h5>
<p>This is important if you read from stdout or stderr and for proper error handling. The default value ensures that you can read from stdout e.g. via pipes or you use webpack -j to generate json output.</p>
<h5><a href="#loglevel-string-defaultinfo" id="loglevel-string-defaultinfo">logLevel <em>(string) (default=info)</em></a></h5>
<p>Can be <code>info</code>, <code>warn</code> or <code>error</code> which limits the log output to the specified log level. Beware of the fact that errors are written to stderr and everything else is written to stderr (or stdout if logInfoToStdOut is true).</p>
<h5><a href="#silent-boolean-defaultfalse" id="silent-boolean-defaultfalse">silent <em>(boolean) (default=false)</em></a></h5>
<p>If true, no console.log messages will be emitted. Note that most error messages are emitted via webpack which is not affected by this flag.</p>
<h5><a href="#ignorediagnostics-number-default" id="ignorediagnostics-number-default">ignoreDiagnostics <em>(number[]) (default=[])</em></a></h5>
<p>You can squelch certain TypeScript errors by specifying an array of diagnostic codes to ignore.</p>
<h5><a href="#compiler-string-defaulttypescript" id="compiler-string-defaulttypescript">compiler <em>(string) (default=&lsquo;typescript&rsquo;)</em></a></h5>
<p>Allows use of TypeScript compilers other than the official one. Should be set to the NPM name of the compiler, eg <a href="https://github.com/basarat/ntypescript"><code>ntypescript</code></a>.</p>
<h5><a href="#configfilename-string-defaulttsconfigjson" id="configfilename-string-defaulttsconfigjson">configFileName <em>(string) (default=&lsquo;tsconfig.json&rsquo;)</em></a></h5>
<p>Allows you to specify a custom configuration file.</p>
<h5><a href="#visualstudioerrorformat-boolean-defaultfalse" id="visualstudioerrorformat-boolean-defaultfalse">visualStudioErrorFormat <em>(boolean) (default=false)</em></a></h5>
<p>If <code>true</code>, the TypeScript compiler output for an error or a warning, e.g. <code>(3,14): error TS4711: you did something very wrong</code>, in file <code>myFile</code> will instead be <code>myFile(3,14): error TS4711: you did something very wrong</code> (notice the file name at the beginning). This way Visual Studio will interpret this line and show any errors or warnings in the <em>error list</em>. This enables navigation to the file/line/column through double click.</p>
<h5><a href="#compileroptions-object-default" id="compileroptions-object-default">compilerOptions <em>(object) (default={})</em></a></h5>
<p>Allows overriding TypeScript options. Should be specified in the same format as you would do for the <code>compilerOptions</code> property in tsconfig.json.</p>
<h5><a href="#instance-string" id="instance-string">instance <em>(string)</em></a></h5>
<p>Advanced option to force files to go through different instances of the TypeScript compiler. Can be used to force segregation between different parts of your code.</p>
<h4><a href="#entryfileisjs-boolean-defaultfalse" id="entryfileisjs-boolean-defaultfalse">entryFileIsJs <em>(boolean) (default=false)</em></a></h4>
<p>To be used in concert with the <code>allowJs</code> compiler option. If your entry file is JS then you&rsquo;ll need to set this option to true.  Please note that this is rather unusual and will generally not be necessary when using <code>allowJs</code>.</p>
<h4><a href="#appendtssuffixto-regexp-default" id="appendtssuffixto-regexp-default">appendTsSuffixTo <em>(RegExp[]) (default=[])</em></a></h4>
<p>A list of regular expressions to be matched against filename. If filename matches one of the regular expressions, a <code>.ts</code> suffix will be appended to that filename.</p>
<p>This is useful for <code>*.vue</code> <a href="https://vuejs.org/v2/guide/single-file-components.html">file format</a> for now. (Probably will benefit from the new single file format in the future.)</p>
<p>Example:</p>
<p>webpack.config.js:</p>
<pre><code class="javascript">module.exports = {
    entry: './index.vue',
    output: { filename: 'bundle.js' },
    resolve: {
        extensions: ['', '.ts', '.vue']
    },
    module: {
        loaders: [
            { test: /\.vue$/, loader: 'vue' },
            { test: /\.ts$/, loader: 'ts' }
        ]
    },
    ts: {
      appendTsSuffixTo: [/\.vue$/]
    }
}
</code></pre>
<p>index.vue</p>
<pre><code class="vue">&lt;template&gt;&lt;p&gt;hello {{msg}}&lt;/p&gt;&lt;/template&gt;
&lt;script lang=&quot;ts&quot;&gt;
export default {
  data(): Object {
    return {
      msg: &quot;world&quot;
    }
  },
}
&lt;/script&gt;
</code></pre>
<h3><a href="#loading-other-resources-and-code-splitting" id="loading-other-resources-and-code-splitting">Loading other resources and code splitting</a></h3>
<p>Loading css and other resources is possible but you will need to make sure that you have defined the <code>require</code> function in a <a href="https://www.typescriptlang.org/docs/handbook/writing-declaration-files.html">declaration file</a>.</p>
<pre><code class="typescript">declare var require: {
    &lt;T&gt;(path: string): T;
    (paths: string[], callback: (...modules: any[]) =&gt; void): void;
    ensure: (paths: string[], callback: (require: &lt;T&gt;(path: string) =&gt; T) =&gt; void) =&gt; void;
};
</code></pre>
<p>Then you can simply require assets or chunks per the <a href="http://webpack.github.io/docs">webpack documentation</a>.</p>
<pre><code class="js">require('!style!css!./style.css');
</code></pre>
<p>The same basic process is required for code splitting. In this case, you <code>import</code> modules you need but you don&rsquo;t directly use them. Instead you require them at <a href="http://webpack.github.io/docs/code-splitting.html#defining-a-split-point">split points</a>. See <a href="test/comparison-tests/codeSplitting">this example</a> and <a href="test/comparison-tests/es6codeSplitting">this example</a> for more details.</p>
<h2><a href="#license" id="license">License</a></h2>
<p>MIT License</p>
.
Document[0, 10472]
  Paragraph[0, 557] isTrailingBlankLine
    Link[0, 123] textOpen:[0, 1, "["] text:[1, 78, "![Build Status](https://travis-ci.org/TypeStrong/ts-loader.svg?branch=master)"] textClose:[78, 79, "]"] linkOpen:[79, 80, "("] url:[80, 122, "https://travis-ci.org/TypeStrong/ts-loader"] pageRef:[80, 122, "https://travis-ci.org/TypeStrong/ts-loader"] linkClose:[122, 123, ")"]
      Image[1, 78] textOpen:[1, 3, "!["] text:[3, 15, "Build Status"] textClose:[15, 16, "]"] linkOpen:[16, 17, "("] url:[17, 77, "https://travis-ci.org/TypeStrong/ts-loader.svg?branch=master"] pageRef:[17, 77, "https://travis-ci.org/TypeStrong/ts-loader.svg?branch=master"] linkClose:[77, 78, ")"]
        Text[3, 15] chars:[3, 15, "Build … tatus"]
    SoftLineBreak[123, 124]
    Link[124, 294] textOpen:[124, 125, "["] text:[125, 225, "![Build Status](https://ci.appveyor.com/api/projects/status/bjh0r0d4ckspgkh9/branch/master?svg=true)"] textClose:[225, 226, "]"] linkOpen:[226, 227, "("] url:[227, 293, "https://ci.appveyor.com/project/JohnReilly/ts-loader/branch/master"] pageRef:[227, 293, "https://ci.appveyor.com/project/JohnReilly/ts-loader/branch/master"] linkClose:[293, 294, ")"]
      Image[125, 225] textOpen:[125, 127, "!["] text:[127, 139, "Build Status"] textClose:[139, 140, "]"] linkOpen:[140, 141, "("] url:[141, 224, "https://ci.appveyor.com/api/projects/status/bjh0r0d4ckspgkh9/branch/master?svg=true"] pageRef:[141, 224, "https://ci.appveyor.com/api/projects/status/bjh0r0d4ckspgkh9/branch/master?svg=true"] linkClose:[224, 225, ")"]
        Text[127, 139] chars:[127, 139, "Build … tatus"]
    SoftLineBreak[294, 295]
    Link[295, 390] textOpen:[295, 296, "["] text:[296, 352, "![Downloads](http://img.shields.io/npm/dm/ts-loader.svg)"] textClose:[352, 353, "]"] linkOpen:[353, 354, "("] url:[354, 389, "https://npmjs.org/package/ts-loader"] pageRef:[354, 389, "https://npmjs.org/package/ts-loader"] linkClose:[389, 390, ")"]
      Image[296, 352] textOpen:[296, 298, "!["] text:[298, 307, "Downloads"] textClose:[307, 308, "]"] linkOpen:[308, 309, "("] url:[309, 351, "http://img.shields.io/npm/dm/ts-loader.svg"] pageRef:[309, 351, "http://img.shields.io/npm/dm/ts-loader.svg"] linkClose:[351, 352, ")"]
        Text[298, 307] chars:[298, 307, "Downloads"]
    SoftLineBreak[390, 391]
    Link[391, 556] textOpen:[391, 392, "["] text:[392, 515, "![Join the chat at https://gitter.im/TypeStrong/ts-loader](https://img.shields.io/badge/gitter-join%20chat-brightgreen.svg)"] textClose:[515, 516, "]"] linkOpen:[516, 517, "("] url:[517, 555, "https://gitter.im/TypeStrong/ts-loader"] pageRef:[517, 555, "https://gitter.im/TypeStrong/ts-loader"] linkClose:[555, 556, ")"]
      Image[392, 515] textOpen:[392, 394, "!["] text:[394, 449, "Join the chat at https://gitter.im/TypeStrong/ts-loader"] textClose:[449, 450, "]"] linkOpen:[450, 451, "("] url:[451, 514, "https://img.shields.io/badge/gitter-join%20chat-brightgreen.svg"] pageRef:[451, 514, "https://img.shields.io/badge/gitter-join%20chat-brightgreen.svg"] linkClose:[514, 515, ")"]
        Text[394, 449] chars:[394, 449, "Join  … oader"]
  Heading[558, 589] textOpen:[558, 559, "#"] text:[560, 589, "TypeScript loader for webpack"]
    AnchorLink[560, 589]
      Text[560, 589] chars:[560, 589, "TypeS … bpack"]
  Paragraph[591, 634] isTrailingBlankLine
    Text[591, 633] chars:[591, 633, "This  … pack."]
  Heading[635, 653] textOpen:[635, 637, "##"] text:[638, 653, "Getting Started"]
    AnchorLink[638, 653]
      Text[638, 653] chars:[638, 653, "Getti … arted"]
  Paragraph[655, 814] isTrailingBlankLine
    Text[655, 685] chars:[655, 685, "Tutor … n be "]
    Link[685, 764] textOpen:[685, 686, "["] text:[686, 696, "found here"] textClose:[696, 697, "]"] linkOpen:[697, 698, "("] url:[698, 763, "https://github.com/TypeStrong/ts-loader/wiki/Tutorials-&-Examples"] pageRef:[698, 763, "https://github.com/TypeStrong/ts-loader/wiki/Tutorials-&-Examples"] linkClose:[763, 764, ")"]
      Text[686, 696] chars:[686, 696, "found here"]
    Text[764, 791] chars:[764, 791, ".  Al …  our "]
    Link[791, 812] textOpen:[791, 792, "["] text:[792, 800, "examples"] textClose:[800, 801, "]"] linkOpen:[801, 802, "("] url:[802, 811, "examples/"] pageRef:[802, 811, "examples/"] linkClose:[811, 812, ")"]
      Text[792, 800] chars:[792, 800, "examples"]
    Text[812, 813] chars:[812, 813, "."]
  Heading[815, 832] textOpen:[815, 818, "###"] text:[819, 832, "Compatibility"]
    AnchorLink[819, 832]
      Text[819, 832] chars:[819, 832, "Compa … ility"]
  Heading[834, 849] textOpen:[834, 838, "####"] text:[839, 849, "TypeScript"]
    AnchorLink[839, 849]
      Text[839, 849] chars:[839, 849, "TypeScript"]
  Paragraph[851, 1063] isTrailingBlankLine
    Text[851, 952] chars:[851, 952, "ts-lo …  the "]
    Link[952, 1060] textOpen:[952, 953, "["] text:[953, 966, "nightly build"] textClose:[966, 967, "]"] linkOpen:[967, 968, "("] url:[968, 1059, "http://blogs.msdn.com/b/typescript/archive/2015/07/27/introducing-typescript-nightlies.aspx"] pageRef:[968, 1059, "http://blogs.msdn.com/b/typescript/archive/2015/07/27/introducing-typescript-nightlies.aspx"] linkClose:[1059, 1060, ")"]
      Text[953, 966] chars:[953, 966, "night … build"]
    Text[1060, 1062] chars:[1060, 1062, ".)"]
  Paragraph[1064, 1385] isTrailingBlankLine
    Text[1064, 1149] chars:[1064, 1149, "A ful … nux ("]
    Link[1149, 1181] textOpen:[1149, 1150, "["] text:[1150, 1156, "Travis"] textClose:[1156, 1157, "]"] linkOpen:[1157, 1158, "("] url:[1158, 1180, "https://travis-ci.org/"] pageRef:[1158, 1180, "https://travis-ci.org/"] linkClose:[1180, 1181, ")"]
      Text[1150, 1156] chars:[1150, 1156, "Travis"]
    Text[1181, 1196] chars:[1181, 1196, ") and … ows ("]
    Link[1196, 1233] textOpen:[1196, 1197, "["] text:[1197, 1205, "AppVeyor"] textClose:[1205, 1206, "]"] linkOpen:[1206, 1207, "("] url:[1207, 1232, "https://www.appveyor.com/"] pageRef:[1207, 1232, "https://www.appveyor.com/"] linkClose:[1232, 1233, ")"]
      Text[1197, 1205] chars:[1197, 1205, "AppVeyor"]
    Text[1233, 1299] chars:[1233, 1299, "), te … ript:"]
    SoftLineBreak[1299, 1300]
    Text[1300, 1316] chars:[1300, 1316, "- Typ … t 2.1"]
    SoftLineBreak[1316, 1317]
    Text[1317, 1333] chars:[1317, 1333, "- Typ … t 2.0"]
    SoftLineBreak[1333, 1334]
    Text[1334, 1350] chars:[1334, 1350, "- Typ … t 1.8"]
    SoftLineBreak[1350, 1351]
    Text[1351, 1367] chars:[1351, 1367, "- Typ … t 1.7"]
    SoftLineBreak[1367, 1368]
    Text[1368, 1384] chars:[1368, 1384, "- Typ … t 1.6"]
  Paragraph[1386, 1460] isTrailingBlankLine
    Text[1386, 1395] chars:[1386, 1395, "and also:"]
    SoftLineBreak[1395, 1396]
    Text[1396, 1459] chars:[1396, 1459, "- Typ … u do)"]
  Paragraph[1461, 1593] isTrailingBlankLine
    Text[1461, 1592] chars:[1461, 1592, "If yo … a PR!"]
  Heading[1594, 1606] textOpen:[1594, 1598, "####"] text:[1599, 1606, "Webpack"]
    AnchorLink[1599, 1606]
      Text[1599, 1606] chars:[1599, 1606, "Webpack"]
  Paragraph[1608, 1863] isTrailingBlankLine
    Text[1608, 1716] chars:[1608, 1716, "ts-lo … nd we"]
    TypographicSmarts[1716, 1717] typographic: &rsquo; 
    Text[1717, 1737] chars:[1717, 1737, "re ex … en it"]
    TypographicSmarts[1737, 1738] typographic: &rsquo; 
    Text[1738, 1751] chars:[1738, 1751, "s rel … ed we"]
    TypographicSmarts[1751, 1752] typographic: &rsquo; 
    Text[1752, 1860] chars:[1752, 1860, "ll lo … cess."]
  Paragraph[1864, 2167] isTrailingBlankLine
    Link[1864, 1942] textOpen:[1864, 1865, "["] text:[1865, 1889, "There's a known \"gotcha\""] textClose:[1889, 1890, "]"] linkOpen:[1890, 1891, "("] url:[1891, 1941, "https://github.com/TypeStrong/ts-loader/issues/283"] pageRef:[1891, 1941, "https://github.com/TypeStrong/ts-loader/issues/283"] linkClose:[1941, 1942, ")"]
      Text[1865, 1870] chars:[1865, 1870, "There"]
      TypographicSmarts[1870, 1871] typographic: &rsquo; 
      Text[1871, 1881] chars:[1871, 1881, "s a known "]
      TypographicQuotes[1881, 1889] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[1881, 1882, "\""] text:[1882, 1888, "gotcha"] textClose:[1888, 1889, "\""]
        Text[1882, 1888] chars:[1882, 1888, "gotcha"]
    Text[1942, 1979] chars:[1942, 1979, " if y …  the "]
    Code[1979, 2000] textOpen:[1979, 1980, "`"] text:[1980, 1999, "Loade … rOptionsPlugin"] textClose:[1999, 2000, "`"]
      Text[1980, 1999] chars:[1980, 1999, "Loade … lugin"]
    Text[2000, 2029] chars:[2000, 2029, ".  If …  the "]
    Code[2029, 2078] textOpen:[2029, 2030, "`"] text:[2030, 2077, "Canno … t read property 'unsafeCache' of undefined"] textClose:[2077, 2078, "`"]
      Text[2030, 2077] chars:[2030, 2077, "Canno … fined"]
    Text[2078, 2120] chars:[2078, 2120, " erro … ly a "]
    Code[2120, 2129] textOpen:[2120, 2121, "`"] text:[2121, 2128, "resolve"] textClose:[2128, 2129, "`"]
      Text[2121, 2128] chars:[2121, 2128, "resolve"]
    Text[2129, 2166] chars:[2129, 2166, " obje … joe!)"]
  FencedCodeBlock[2168, 2293] open:[2168, 2171, "```"] info:[2171, 2173, "js"] content:[2174, 2290] lines[6] close:[2290, 2293, "```"]
    Text[2174, 2290] chars:[2174, 2290, "new L … }\n})\n"]
  Heading[2295, 2304] textOpen:[2295, 2298, "###"] text:[2299, 2304, "Babel"]
    AnchorLink[2299, 2304]
      Text[2299, 2304] chars:[2299, 2304, "Babel"]
  Paragraph[2306, 2709] isTrailingBlankLine
    Text[2306, 2352] chars:[2306, 2352, "ts-lo … with "]
    Link[2352, 2380] textOpen:[2352, 2353, "["] text:[2353, 2358, "babel"] textClose:[2358, 2359, "]"] linkOpen:[2359, 2360, "("] url:[2360, 2379, "https://babeljs.io/"] pageRef:[2360, 2379, "https://babeljs.io/"] linkClose:[2379, 2380, ")"]
      Text[2353, 2358] chars:[2353, 2358, "babel"]
    Text[2380, 2385] chars:[2380, 2385, " and "]
    Link[2385, 2438] textOpen:[2385, 2386, "["] text:[2386, 2398, "babel-loader"] textClose:[2398, 2399, "]"] linkOpen:[2399, 2400, "("] url:[2400, 2437, "https://github.com/babel/babel-loader"] pageRef:[2400, 2437, "https://github.com/babel/babel-loader"] linkClose:[2437, 2438, ")"]
      Text[2386, 2398] chars:[2386, 2398, "babel … oader"]
    Text[2438, 2498] chars:[2438, 2498, ".  To …  the "]
    Link[2498, 2590] textOpen:[2498, 2499, "["] text:[2499, 2506, "example"] textClose:[2506, 2507, "]"] linkOpen:[2507, 2508, "("] url:[2508, 2589, "https://github.com/Microsoft/TypeScriptSamples/tree/master/react-flux-babel-karma"] pageRef:[2508, 2589, "https://github.com/Microsoft/TypeScriptSamples/tree/master/react-flux-babel-karma"] linkClose:[2589, 2590, ")"]
      Text[2499, 2506] chars:[2499, 2506, "example"]
    Text[2590, 2607] chars:[2590, 2607, " in t … cial "]
    Link[2607, 2675] textOpen:[2607, 2608, "["] text:[2608, 2626, "TypeScript Samples"] textClose:[2626, 2627, "]"] linkOpen:[2627, 2628, "("] url:[2628, 2674, "https://github.com/Microsoft/TypeScriptSamples"] pageRef:[2628, 2674, "https://github.com/Microsoft/TypeScriptSamples"] linkClose:[2674, 2675, ")"]
      Text[2608, 2626] chars:[2608, 2626, "TypeS … mples"]
    Text[2675, 2686] chars:[2675, 2686, " or i …  our "]
    Link[2686, 2707] textOpen:[2686, 2687, "["] text:[2687, 2695, "examples"] textClose:[2695, 2696, "]"] linkOpen:[2696, 2697, "("] url:[2697, 2706, "examples/"] pageRef:[2697, 2706, "examples/"] linkClose:[2706, 2707, ")"]
      Text[2687, 2695] chars:[2687, 2695, "examples"]
    Text[2707, 2708] chars:[2707, 2708, "."]
  Heading[2710, 2726] textOpen:[2710, 2713, "###"] text:[2714, 2726, "Contributing"]
    AnchorLink[2714, 2726]
      Text[2714, 2726] chars:[2714, 2726, "Contr … uting"]
  Paragraph[2728, 2896] isTrailingBlankLine
    Text[2728, 2841] chars:[2728, 2841, "This  …  the "]
    Link[2841, 2879] textOpen:[2841, 2842, "["] text:[2842, 2861, "contributor's guide"] textClose:[2861, 2862, "]"] linkOpen:[2862, 2863, "("] url:[2863, 2878, "CONTRIBUTING.md"] pageRef:[2863, 2878, "CONTRIBUTING.md"] linkClose:[2878, 2879, ")"]
      Text[2842, 2853] chars:[2842, 2853, "contr … butor"]
      TypographicSmarts[2853, 2854] typographic: &rsquo; 
      Text[2854, 2861] chars:[2854, 2861, "s guide"]
    Text[2879, 2895] chars:[2879, 2895, " to g … rted."]
  Heading[2897, 2913] textOpen:[2897, 2900, "###"] text:[2901, 2913, "Installation"]
    AnchorLink[2901, 2913]
      Text[2901, 2913] chars:[2901, 2913, "Insta … ation"]
  FencedCodeBlock[2915, 2944] open:[2915, 2918, "```"] content:[2919, 2941] lines[1] close:[2941, 2944, "```"]
    Text[2919, 2941] chars:[2919, 2941, "npm i … ader\n"]
  Paragraph[2946, 3012] isTrailingBlankLine
    Text[2946, 3011] chars:[2946, 3011, "You w … eady."]
  FencedCodeBlock[3013, 3043] open:[3013, 3016, "```"] content:[3017, 3040] lines[1] close:[3040, 3043, "```"]
    Text[3017, 3040] chars:[3017, 3040, "npm i … ript\n"]
  Paragraph[3045, 3091] isTrailingBlankLine
    Text[3045, 3090] chars:[3045, 3090, "or if … bally"]
  FencedCodeBlock[3092, 3145] open:[3092, 3095, "```"] content:[3096, 3142] lines[2] close:[3142, 3145, "```"]
    Text[3096, 3142] chars:[3096, 3142, "npm i … ript\n"]
  Heading[3147, 3158] textOpen:[3147, 3150, "###"] text:[3151, 3158, "Running"]
    AnchorLink[3151, 3158]
      Text[3151, 3158] chars:[3151, 3158, "Running"]
  Paragraph[3160, 3344] isTrailingBlankLine
    Text[3160, 3195] chars:[3160, 3195, "Use w … ding "]
    Code[3195, 3212] textOpen:[3195, 3196, "`"] text:[3196, 3211, "webpa … ck --watch"] textClose:[3211, 3212, "`"]
      Text[3196, 3211] chars:[3196, 3211, "webpa … watch"]
    Text[3212, 3217] chars:[3212, 3217, " and "]
    Code[3217, 3237] textOpen:[3217, 3218, "`"] text:[3218, 3236, "webpa … ck-dev-server"] textClose:[3236, 3237, "`"]
      Text[3218, 3236] chars:[3218, 3236, "webpa … erver"]
    Text[3237, 3257] chars:[3237, 3257, ", or  … other"]
    SoftLineBreak[3257, 3258]
    Text[3258, 3281] chars:[3258, 3281, "build …  the "]
    Link[3281, 3342] textOpen:[3281, 3282, "["] text:[3282, 3293, "Node.js API"] textClose:[3293, 3294, "]"] linkOpen:[3294, 3295, "("] url:[3295, 3341, "http://webpack.github.io/docs/node.js-api.html"] pageRef:[3295, 3341, "http://webpack.github.io/docs/node.js-api.html"] linkClose:[3341, 3342, ")"]
      Text[3282, 3293] chars:[3282, 3293, "Node. … s API"]
    Text[3342, 3343] chars:[3342, 3343, "."]
  Heading[3345, 3362] textOpen:[3345, 3348, "###"] text:[3349, 3362, "Configuration"]
    AnchorLink[3349, 3362]
      Text[3349, 3362] chars:[3349, 3362, "Confi … ation"]
  OrderedList[3364, 4142] isTight delimiter:'.'
    OrderedListItem[3364, 3897] open:[3364, 3366, "1."] isLoose hadBlankLineAfter
      Paragraph[3367, 3413] isTrailingBlankLine
        Text[3367, 3384] chars:[3367, 3384, "Creat … date "]
        Code[3384, 3403] textOpen:[3384, 3385, "`"] text:[3385, 3402, "webpa … ck.config.js"] textClose:[3402, 3403, "`"]
          Text[3385, 3402] chars:[3385, 3402, "webpa … ig.js"]
        Text[3403, 3412] chars:[3403, 3412, " like so:"]
      FencedCodeBlock[3418, 3897] open:[3418, 3421, "```"] info:[3421, 3431, "javascript"] content:[3436, 3890] lines[16] close:[3894, 3897, "```"]
        Text[3436, 3890] chars:[3436, 3890, "modul …  }\n}\n"]
    OrderedListItem[3899, 4142] open:[3899, 3901, "2."] isLoose hadBlankLineAfter
      Paragraph[3902, 4075] isTrailingBlankLine
        Text[3902, 3908] chars:[3902, 3908, "Add a "]
        Link[3908, 3990] textOpen:[3908, 3909, "["] text:[3909, 3924, "`tsconfig.json`"] textClose:[3924, 3925, "]"] linkOpen:[3925, 3926, "("] url:[3926, 3989, "https://www.typescriptlang.org/docs/handbook/tsconfig-json.html"] pageRef:[3926, 3989, "https://www.typescriptlang.org/docs/handbook/tsconfig-json.html"] linkClose:[3989, 3990, ")"]
          Code[3909, 3924] textOpen:[3909, 3910, "`"] text:[3910, 3923, "tscon … fig.json"] textClose:[3923, 3924, "`"]
            Text[3910, 3923] chars:[3910, 3923, "tscon … .json"]
        Text[3990, 4074] chars:[3990, 4074, " file … sire)"]
      FencedCodeBlock[4080, 4142] open:[4080, 4083, "```"] info:[4083, 4087, "json"] content:[4092, 4135] lines[4] close:[4139, 4142, "```"]
        Text[4092, 4135] chars:[4092, 4135, "{\n  \" …  }\n}\n"]
  Paragraph[4144, 4350] isTrailingBlankLine
    Text[4144, 4148] chars:[4144, 4148, "The "]
    Link[4148, 4227] textOpen:[4148, 4149, "["] text:[4149, 4162, "tsconfig.json"] textClose:[4162, 4163, "]"] linkOpen:[4163, 4164, "("] url:[4164, 4226, "http://www.typescriptlang.org/docs/handbook/tsconfig-json.html"] pageRef:[4164, 4226, "http://www.typescriptlang.org/docs/handbook/tsconfig-json.html"] linkClose:[4226, 4227, ")"]
      Text[4149, 4162] chars:[4149, 4162, "tscon … .json"]
    Text[4227, 4241] chars:[4227, 4241, " file … trols"]
    SoftLineBreak[4241, 4242]
    Text[4242, 4291] chars:[4242, 4291, "TypeS …  the "]
    Code[4291, 4296] textOpen:[4291, 4292, "`"] text:[4292, 4295, "tsc"] textClose:[4295, 4296, "`"]
      Text[4292, 4295] chars:[4292, 4295, "tsc"]
    Text[4296, 4335] chars:[4296, 4335, " comm … e the"]
    SoftLineBreak[4335, 4336]
    Text[4336, 4349] chars:[4336, 4349, "same  … ions."]
  Heading[4351, 4404] textOpen:[4351, 4354, "###"] text:[4355, 4404, "Failing the build on TypeScript compilation error"]
    AnchorLink[4355, 4404]
      Text[4355, 4404] chars:[4355, 4404, "Faili … error"]
  Paragraph[4406, 4989] isTrailingBlankLine
    Text[4406, 4496] chars:[4406, 4496, "When  … does "]
    StrongEmphasis[4496, 4503] textOpen:[4496, 4498, "**"] text:[4498, 4501, "not"] textClose:[4501, 4503, "**"]
      Text[4498, 4501] chars:[4498, 4501, "not"]
    Text[4503, 4780] chars:[4503, 4780, " prop …  the "]
    Link[4780, 4852] textOpen:[4780, 4781, "["] text:[4781, 4800, "webpack-fail-plugin"] textClose:[4800, 4801, "]"] linkOpen:[4801, 4802, "("] url:[4802, 4851, "https://www.npmjs.com/package/webpack-fail-plugin"] pageRef:[4802, 4851, "https://www.npmjs.com/package/webpack-fail-plugin"] linkClose:[4851, 4852, ")"]
      Text[4781, 4800] chars:[4781, 4800, "webpa … lugin"]
    Text[4852, 4988] chars:[4852, 4988, ".  Th … lure."]
  Paragraph[4990, 5091] isTrailingBlankLine
    Text[4990, 5025] chars:[4990, 5025, "For m … d of "]
    Link[5025, 5089] textOpen:[5025, 5026, "["] text:[5026, 5036, "this issue"] textClose:[5036, 5037, "]"] linkOpen:[5037, 5038, "("] url:[5038, 5088, "https://github.com/TypeStrong/ts-loader/issues/108"] pageRef:[5038, 5088, "https://github.com/TypeStrong/ts-loader/issues/108"] linkClose:[5088, 5089, ")"]
      Text[5026, 5036] chars:[5026, 5036, "this issue"]
    Text[5089, 5090] chars:[5089, 5090, "."]
  Heading[5092, 5105] textOpen:[5092, 5095, "###"] text:[5096, 5105, "Upgrading"]
    AnchorLink[5096, 5105]
      Text[5096, 5105] chars:[5096, 5105, "Upgrading"]
  Paragraph[5107, 5188] isTrailingBlankLine
    Text[5107, 5129] chars:[5107, 5129, "Take  …  the "]
    Link[5129, 5154] textOpen:[5129, 5130, "["] text:[5130, 5139, "Changelog"] textClose:[5139, 5140, "]"] linkOpen:[5140, 5141, "("] url:[5141, 5153, "CHANGELOG.md"] pageRef:[5141, 5153, "CHANGELOG.md"] linkClose:[5153, 5154, ")"]
      Text[5130, 5139] chars:[5130, 5139, "Changelog"]
    Text[5154, 5159] chars:[5154, 5159, " and "]
    Link[5159, 5186] textOpen:[5159, 5160, "["] text:[5160, 5173, "Upgrade Guide"] textClose:[5173, 5174, "]"] linkOpen:[5174, 5175, "("] url:[5175, 5185, "UPGRADE.md"] pageRef:[5175, 5185, "UPGRADE.md"] linkClose:[5185, 5186, ")"]
      Text[5160, 5173] chars:[5160, 5173, "Upgra … Guide"]
    Text[5186, 5187] chars:[5186, 5187, "."]
  Heading[5189, 5201] textOpen:[5189, 5193, "####"] text:[5194, 5201, "Options"]
    AnchorLink[5194, 5201]
      Text[5194, 5201] chars:[5194, 5201, "Options"]
  Paragraph[5203, 5493] isTrailingBlankLine
    Text[5203, 5259] chars:[5203, 5259, "There … (aka "]
    TypographicQuotes[5259, 5277] typographicOpening: &ldquo;  typographicClosing: &rdquo;  textOpen:[5259, 5260, "\""] text:[5260, 5276, "compiler options"] textClose:[5276, 5277, "\""]
      Text[5260, 5276] chars:[5260, 5276, "compi … tions"]
    Text[5277, 5298] chars:[5277, 5298, ") and … ions."]
    SoftLineBreak[5298, 5299]
    Text[5299, 5392] chars:[5299, 5392, "TypeS … ither"]
    SoftLineBreak[5392, 5393]
    Text[5393, 5449] chars:[5393, 5449, "using …  the "]
    Code[5449, 5453] textOpen:[5449, 5450, "`"] text:[5450, 5452, "ts"] textClose:[5452, 5453, "`"]
      Text[5450, 5452] chars:[5450, 5452, "ts"]
    Text[5453, 5492] chars:[5453, 5492, " prop … tion."]
  FencedCodeBlock[5494, 5760] open:[5494, 5497, "```"] info:[5497, 5507, "javascript"] content:[5508, 5757] lines[13] close:[5757, 5760, "```"]
    Text[5508, 5757] chars:[5508, 5757, "modul …  }\n}\n"]
  Heading[5762, 5809] textOpen:[5762, 5767, "#####"] text:[5768, 5809, "transpileOnly *(boolean) (default=false)*"]
    AnchorLink[5768, 5809]
      Text[5768, 5782] chars:[5768, 5782, "trans … Only "]
      Emphasis[5782, 5809] textOpen:[5782, 5783, "*"] text:[5783, 5808, "(boolean) (default=false)"] textClose:[5808, 5809, "*"]
        Text[5783, 5808] chars:[5783, 5808, "(bool … alse)"]
  Paragraph[5811, 6111] isTrailingBlankLine
    Text[5811, 5883] chars:[5811, 5883, "If yo … flag."]
    SoftLineBreak[5883, 5884]
    Text[5884, 5955] chars:[5884, 5955, "Howev … tween"]
    SoftLineBreak[5955, 5956]
    Text[5956, 6028] chars:[5956, 6028, "diffe …  also"]
    SoftLineBreak[6028, 6029]
    Text[6029, 6037] chars:[6029, 6037, "set the "]
    Code[6037, 6054] textOpen:[6037, 6038, "`"] text:[6038, 6053, "isola … tedModules"] textClose:[6053, 6054, "`"]
      Text[6038, 6053] chars:[6038, 6053, "isola … dules"]
    Text[6054, 6101] chars:[6054, 6101, " Type … e use"]
    SoftLineBreak[6101, 6102]
    Text[6102, 6110] chars:[6102, 6110, "of this."]
  Heading[6112, 6161] textOpen:[6112, 6117, "#####"] text:[6118, 6161, "logInfoToStdOut *(boolean) (default=false)*"]
    AnchorLink[6118, 6161]
      Text[6118, 6134] chars:[6118, 6134, "logIn … dOut "]
      Emphasis[6134, 6161] textOpen:[6134, 6135, "*"] text:[6135, 6160, "(boolean) (default=false)"] textClose:[6160, 6161, "*"]
        Text[6135, 6160] chars:[6135, 6160, "(bool … alse)"]
  Paragraph[6163, 6364] isTrailingBlankLine
    Text[6163, 6245] chars:[6163, 6245, "This  … ling."]
    SoftLineBreak[6245, 6246]
    Text[6246, 6363] chars:[6246, 6363, "The d … tput."]
  Heading[6365, 6405] textOpen:[6365, 6370, "#####"] text:[6371, 6405, "logLevel *(string) (default=info)*"]
    AnchorLink[6371, 6405]
      Text[6371, 6380] chars:[6371, 6380, "logLevel "]
      Emphasis[6380, 6405] textOpen:[6380, 6381, "*"] text:[6381, 6404, "(string) (default=info)"] textClose:[6404, 6405, "*"]
        Text[6381, 6404] chars:[6381, 6404, "(stri … info)"]
  Paragraph[6407, 6630] isTrailingBlankLine
    Text[6407, 6414] chars:[6407, 6414, "Can be "]
    Code[6414, 6420] textOpen:[6414, 6415, "`"] text:[6415, 6419, "info"] textClose:[6419, 6420, "`"]
      Text[6415, 6419] chars:[6415, 6419, "info"]
    Text[6420, 6422] chars:[6420, 6422, ", "]
    Code[6422, 6428] textOpen:[6422, 6423, "`"] text:[6423, 6427, "warn"] textClose:[6427, 6428, "`"]
      Text[6423, 6427] chars:[6423, 6427, "warn"]
    Text[6428, 6432] chars:[6428, 6432, " or "]
    Code[6432, 6439] textOpen:[6432, 6433, "`"] text:[6433, 6438, "error"] textClose:[6438, 6439, "`"]
      Text[6433, 6438] chars:[6433, 6438, "error"]
    Text[6439, 6495] chars:[6439, 6495, " whic … evel."]
    SoftLineBreak[6495, 6496]
    Text[6496, 6629] chars:[6496, 6629, "Bewar … rue)."]
  Heading[6631, 6671] textOpen:[6631, 6636, "#####"] text:[6637, 6671, "silent *(boolean) (default=false)*"]
    AnchorLink[6637, 6671]
      Text[6637, 6644] chars:[6637, 6644, "silent "]
      Emphasis[6644, 6671] textOpen:[6644, 6645, "*"] text:[6645, 6670, "(boolean) (default=false)"] textClose:[6670, 6671, "*"]
        Text[6645, 6670] chars:[6645, 6670, "(bool … alse)"]
  Paragraph[6673, 6813] isTrailingBlankLine
    Text[6673, 6743] chars:[6673, 6743, "If tr … error"]
    SoftLineBreak[6743, 6744]
    Text[6744, 6812] chars:[6744, 6812, "messa … flag."]
  Heading[6814, 6863] textOpen:[6814, 6819, "#####"] text:[6820, 6863, "ignoreDiagnostics *(number[]) (default=[])*"]
    AnchorLink[6820, 6863]
      Text[6820, 6838] chars:[6820, 6838, "ignor … tics "]
      Emphasis[6838, 6863] textOpen:[6838, 6839, "*"] text:[6839, 6862, "(number[]) (default=[])"] textClose:[6862, 6863, "*"]
        Text[6839, 6846] chars:[6839, 6846, "(number"]
        LinkRef[6846, 6848] referenceOpen:[6846, 6847, "["] reference:[6847, 6847] referenceClose:[6847, 6848, "]"]
        Text[6848, 6859] chars:[6848, 6859, ") (de … ault="]
        LinkRef[6859, 6861] referenceOpen:[6859, 6860, "["] reference:[6860, 6860] referenceClose:[6860, 6861, "]"]
        Text[6861, 6862] chars:[6861, 6862, ")"]
  Paragraph[6865, 6961] isTrailingBlankLine
    Text[6865, 6943] chars:[6865, 6943, "You c … ostic"]
    SoftLineBreak[6943, 6944]
    Text[6944, 6960] chars:[6944, 6960, "codes … nore."]
  Heading[6962, 7010] textOpen:[6962, 6967, "#####"] text:[6968, 7010, "compiler *(string) (default='typescript')*"]
    AnchorLink[6968, 7010]
      Text[6968, 6977] chars:[6968, 6977, "compiler "]
      Emphasis[6977, 7010] textOpen:[6977, 6978, "*"] text:[6978, 7009, "(string) (default='typescript')"] textClose:[7009, 7010, "*"]
        Text[6978, 6996] chars:[6978, 6996, "(stri … ault="]
        TypographicQuotes[6996, 7008] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[6996, 6997, "'"] text:[6997, 7007, "typescript"] textClose:[7007, 7008, "'"]
          Text[6997, 7007] chars:[6997, 7007, "typescript"]
        Text[7008, 7009] chars:[7008, 7009, ")"]
  Paragraph[7012, 7183] isTrailingBlankLine
    Text[7012, 7085] chars:[7012, 7085, "Allow … ld be"]
    SoftLineBreak[7085, 7086]
    Text[7086, 7126] chars:[7086, 7126, "set t … , eg "]
    Link[7126, 7181] textOpen:[7126, 7127, "["] text:[7127, 7140, "`ntypescript`"] textClose:[7140, 7141, "]"] linkOpen:[7141, 7142, "("] url:[7142, 7180, "https://github.com/basarat/ntypescript"] pageRef:[7142, 7180, "https://github.com/basarat/ntypescript"] linkClose:[7180, 7181, ")"]
      Code[7127, 7140] textOpen:[7127, 7128, "`"] text:[7128, 7139, "ntype … script"] textClose:[7139, 7140, "`"]
        Text[7128, 7139] chars:[7128, 7139, "ntype … cript"]
    Text[7181, 7182] chars:[7181, 7182, "."]
  Heading[7184, 7241] textOpen:[7184, 7189, "#####"] text:[7190, 7241, "configFileName *(string) (default='tsconfig.json')*"]
    AnchorLink[7190, 7241]
      Text[7190, 7205] chars:[7190, 7205, "confi … Name "]
      Emphasis[7205, 7241] textOpen:[7205, 7206, "*"] text:[7206, 7240, "(string) (default='tsconfig.json')"] textClose:[7240, 7241, "*"]
        Text[7206, 7224] chars:[7206, 7224, "(stri … ault="]
        TypographicQuotes[7224, 7239] typographicOpening: &lsquo;  typographicClosing: &rsquo;  textOpen:[7224, 7225, "'"] text:[7225, 7238, "tsconfig.json"] textClose:[7238, 7239, "'"]
          Text[7225, 7238] chars:[7225, 7238, "tscon … .json"]
        Text[7239, 7240] chars:[7239, 7240, ")"]
  Paragraph[7243, 7294] isTrailingBlankLine
    Text[7243, 7293] chars:[7243, 7293, "Allow … file."]
  Heading[7295, 7352] textOpen:[7295, 7300, "#####"] text:[7301, 7352, "visualStudioErrorFormat *(boolean) (default=false)*"]
    AnchorLink[7301, 7352]
      Text[7301, 7325] chars:[7301, 7325, "visua … rmat "]
      Emphasis[7325, 7352] textOpen:[7325, 7326, "*"] text:[7326, 7351, "(boolean) (default=false)"] textClose:[7351, 7352, "*"]
        Text[7326, 7351] chars:[7326, 7351, "(bool … alse)"]
  Paragraph[7354, 7786] isTrailingBlankLine
    Text[7354, 7357] chars:[7354, 7357, "If "]
    Code[7357, 7363] textOpen:[7357, 7358, "`"] text:[7358, 7362, "true"] textClose:[7362, 7363, "`"]
      Text[7358, 7362] chars:[7358, 7362, "true"]
    Text[7363, 7428] chars:[7363, 7428, ", the … e.g. "]
    Code[7428, 7480] textOpen:[7428, 7429, "`"] text:[7429, 7479, "(3,14 … ): error TS4711: you did something very wrong"] textClose:[7479, 7480, "`"]
      Text[7429, 7479] chars:[7429, 7479, "(3,14 … wrong"]
    Text[7480, 7490] chars:[7480, 7490, ", in file "]
    Code[7490, 7498] textOpen:[7490, 7491, "`"] text:[7491, 7497, "myFile"] textClose:[7497, 7498, "`"]
      Text[7491, 7497] chars:[7491, 7497, "myFile"]
    Text[7498, 7515] chars:[7498, 7515, " will … d be "]
    Code[7515, 7573] textOpen:[7515, 7516, "`"] text:[7516, 7572, "myFil … e(3,14): error TS4711: you did something very wrong"] textClose:[7572, 7573, "`"]
      Text[7516, 7572] chars:[7516, 7572, "myFil … wrong"]
    Text[7573, 7702] chars:[7573, 7702, " (not …  the "]
    Emphasis[7702, 7714] textOpen:[7702, 7703, "*"] text:[7703, 7713, "error list"] textClose:[7713, 7714, "*"]
      Text[7703, 7713] chars:[7703, 7713, "error list"]
    Text[7714, 7785] chars:[7714, 7785, ". Thi … lick."]
  Heading[7787, 7832] textOpen:[7787, 7792, "#####"] text:[7793, 7832, "compilerOptions *(object) (default={})*"]
    AnchorLink[7793, 7832]
      Text[7793, 7809] chars:[7793, 7809, "compi … ions "]
      Emphasis[7809, 7832] textOpen:[7809, 7810, "*"] text:[7810, 7831, "(object) (default={})"] textClose:[7831, 7832, "*"]
        Text[7810, 7831] chars:[7810, 7831, "(obje … t={})"]
  Paragraph[7834, 7980] isTrailingBlankLine
    Text[7834, 7910] chars:[7834, 7910, "Allow … ormat"]
    SoftLineBreak[7910, 7911]
    Text[7911, 7935] chars:[7911, 7935, "as yo …  the "]
    Code[7935, 7952] textOpen:[7935, 7936, "`"] text:[7936, 7951, "compi … lerOptions"] textClose:[7951, 7952, "`"]
      Text[7936, 7951] chars:[7936, 7951, "compi … tions"]
    Text[7952, 7979] chars:[7952, 7979, " prop … json."]
  Heading[7981, 8006] textOpen:[7981, 7986, "#####"] text:[7987, 8006, "instance *(string)*"]
    AnchorLink[7987, 8006]
      Text[7987, 7996] chars:[7987, 7996, "instance "]
      Emphasis[7996, 8006] textOpen:[7996, 7997, "*"] text:[7997, 8005, "(string)"] textClose:[8005, 8006, "*"]
        Text[7997, 8005] chars:[7997, 8005, "(string)"]
  Paragraph[8008, 8172] isTrailingBlankLine
    Text[8008, 8079] chars:[8008, 8079, "Advan … f the"]
    SoftLineBreak[8079, 8080]
    Text[8080, 8157] chars:[8080, 8157, "TypeS … parts"]
    SoftLineBreak[8157, 8158]
    Text[8158, 8171] chars:[8158, 8171, "of yo … code."]
  Heading[8173, 8219] textOpen:[8173, 8177, "####"] text:[8178, 8219, "entryFileIsJs *(boolean) (default=false)*"]
    AnchorLink[8178, 8219]
      Text[8178, 8192] chars:[8178, 8192, "entry … IsJs "]
      Emphasis[8192, 8219] textOpen:[8192, 8193, "*"] text:[8193, 8218, "(boolean) (default=false)"] textClose:[8218, 8219, "*"]
        Text[8193, 8218] chars:[8193, 8218, "(bool … alse)"]
  Paragraph[8221, 8448] isTrailingBlankLine
    Text[8221, 8252] chars:[8221, 8252, "To be …  the "]
    Code[8252, 8261] textOpen:[8252, 8253, "`"] text:[8253, 8260, "allowJs"] textClose:[8260, 8261, "`"]
      Text[8253, 8260] chars:[8253, 8260, "allowJs"]
    Text[8261, 8312] chars:[8261, 8312, " comp … n you"]
    TypographicSmarts[8312, 8313] typographic: &rsquo; 
    Text[8313, 8437] chars:[8313, 8437, "ll ne … sing "]
    Code[8437, 8446] textOpen:[8437, 8438, "`"] text:[8438, 8445, "allowJs"] textClose:[8445, 8446, "`"]
      Text[8438, 8445] chars:[8438, 8445, "allowJs"]
    Text[8446, 8447] chars:[8446, 8447, "."]
  Heading[8449, 8496] textOpen:[8449, 8453, "####"] text:[8454, 8496, "appendTsSuffixTo *(RegExp[]) (default=[])*"]
    AnchorLink[8454, 8496]
      Text[8454, 8471] chars:[8454, 8471, "appen … ixTo "]
      Emphasis[8471, 8496] textOpen:[8471, 8472, "*"] text:[8472, 8495, "(RegExp[]) (default=[])"] textClose:[8495, 8496, "*"]
        Text[8472, 8479] chars:[8472, 8479, "(RegExp"]
        LinkRef[8479, 8481] referenceOpen:[8479, 8480, "["] reference:[8480, 8480] referenceClose:[8480, 8481, "]"]
        Text[8481, 8492] chars:[8481, 8492, ") (de … ault="]
        LinkRef[8492, 8494] referenceOpen:[8492, 8493, "["] reference:[8493, 8493] referenceClose:[8493, 8494, "]"]
        Text[8494, 8495] chars:[8494, 8495, ")"]
  Paragraph[8497, 8661] isTrailingBlankLine
    Text[8497, 8613] chars:[8497, 8613, "A lis … s, a "]
    Code[8613, 8618] textOpen:[8613, 8614, "`"] text:[8614, 8617, ".ts"] textClose:[8617, 8618, "`"]
      Text[8614, 8617] chars:[8614, 8617, ".ts"]
    Text[8618, 8660] chars:[8618, 8660, " suff … name."]
  Paragraph[8662, 8839] isTrailingBlankLine
    Text[8662, 8681] chars:[8662, 8681, "This  …  for "]
    Code[8681, 8688] textOpen:[8681, 8682, "`"] text:[8682, 8687, "*.vue"] textClose:[8687, 8688, "`"]
      Text[8682, 8687] chars:[8682, 8687, "*.vue"]
    Text[8688, 8689] chars:[8688, 8689, " "]
    Link[8689, 8758] textOpen:[8689, 8690, "["] text:[8690, 8701, "file format"] textClose:[8701, 8702, "]"] linkOpen:[8702, 8703, "("] url:[8703, 8757, "https://vuejs.org/v2/guide/single-file-components.html"] pageRef:[8703, 8757, "https://vuejs.org/v2/guide/single-file-components.html"] linkClose:[8757, 8758, ")"]
      Text[8690, 8701] chars:[8690, 8701, "file  … ormat"]
    Text[8758, 8838] chars:[8758, 8838, " for  … ure.)"]
  Paragraph[8840, 8849] isTrailingBlankLine
    Text[8840, 8848] chars:[8840, 8848, "Example:"]
  Paragraph[8850, 8869] isTrailingBlankLine
    Text[8850, 8868] chars:[8850, 8868, "webpa … g.js:"]
  FencedCodeBlock[8870, 9227] open:[8870, 8873, "```"] info:[8873, 8883, "javascript"] content:[8884, 9224] lines[16] close:[9224, 9227, "```"]
    Text[8884, 9224] chars:[8884, 9224, "modul …  }\n}\n"]
  Paragraph[9229, 9239] isTrailingBlankLine
    Text[9229, 9238] chars:[9229, 9238, "index.vue"]
  FencedCodeBlock[9240, 9402] open:[9240, 9243, "```"] info:[9243, 9246, "vue"] content:[9247, 9399] lines[10] close:[9399, 9402, "```"]
    Text[9247, 9399] chars:[9247, 9399, "<temp … ipt>\n"]
  Heading[9405, 9451] textOpen:[9405, 9408, "###"] text:[9409, 9451, "Loading other resources and code splitting"]
    AnchorLink[9409, 9451]
      Text[9409, 9451] chars:[9409, 9451, "Loadi … tting"]
  Paragraph[9453, 9675] isTrailingBlankLine
    Text[9453, 9532] chars:[9453, 9532, "Loadi …  that"]
    SoftLineBreak[9532, 9533]
    Text[9533, 9554] chars:[9533, 9554, "you h …  the "]
    Code[9554, 9563] textOpen:[9554, 9555, "`"] text:[9555, 9562, "require"] textClose:[9562, 9563, "`"]
      Text[9555, 9562] chars:[9555, 9562, "require"]
    Text[9563, 9578] chars:[9563, 9578, " func … in a "]
    Link[9578, 9673] textOpen:[9578, 9579, "["] text:[9579, 9595, "declaration file"] textClose:[9595, 9596, "]"] linkOpen:[9596, 9597, "("] url:[9597, 9672, "https://www.typescriptlang.org/docs/handbook/writing-declaration-files.html"] pageRef:[9597, 9672, "https://www.typescriptlang.org/docs/handbook/writing-declaration-files.html"] linkClose:[9672, 9673, ")"]
      Text[9579, 9595] chars:[9579, 9595, "decla …  file"]
    Text[9673, 9674] chars:[9673, 9674, "."]
  FencedCodeBlock[9676, 9905] open:[9676, 9679, "```"] info:[9679, 9689, "typescript"] content:[9690, 9902] lines[5] close:[9902, 9905, "```"]
    Text[9690, 9902] chars:[9690, 9902, "decla … ;\n};\n"]
  Paragraph[9907, 10016] isTrailingBlankLine
    Text[9907, 9960] chars:[9907, 9960, "Then  …  the "]
    Link[9960, 10014] textOpen:[9960, 9961, "["] text:[9961, 9982, "webpack documentation"] textClose:[9982, 9983, "]"] linkOpen:[9983, 9984, "("] url:[9984, 10013, "http://webpack.github.io/docs"] pageRef:[9984, 10013, "http://webpack.github.io/docs"] linkClose:[10013, 10014, ")"]
      Text[9961, 9982] chars:[9961, 9982, "webpa … ation"]
    Text[10014, 10015] chars:[10014, 10015, "."]
  FencedCodeBlock[10017, 10061] open:[10017, 10020, "```"] info:[10020, 10022, "js"] content:[10023, 10058] lines[1] close:[10058, 10061, "```"]
    Text[10023, 10058] chars:[10023, 10058, "requi … s');\n"]
  Paragraph[10063, 10446] isTrailingBlankLine
    Text[10063, 10136] chars:[10063, 10136, "The s …  you "]
    Code[10136, 10144] textOpen:[10136, 10137, "`"] text:[10137, 10143, "import"] textClose:[10143, 10144, "`"]
      Text[10137, 10143] chars:[10137, 10143, "import"]
    Text[10144, 10169] chars:[10144, 10169, " modu … t you"]
    SoftLineBreak[10169, 10170]
    Text[10170, 10173] chars:[10170, 10173, "don"]
    TypographicSmarts[10173, 10174] typographic: &rsquo; 
    Text[10174, 10223] chars:[10174, 10223, "t dir … m at "]
    Link[10223, 10311] textOpen:[10223, 10224, "["] text:[10224, 10236, "split points"] textClose:[10236, 10237, "]"] linkOpen:[10237, 10238, "("] url:[10238, 10310, "http://webpack.github.io/docs/code-splitting.html#defining-a-split-point"] pageRef:[10238, 10287, "http://webpack.github.io/docs/code-splitting.html"] anchorMarker:[10287, 10288, "#"] anchorRef:[10288, 10310, "defining-a-split-point"] linkClose:[10310, 10311, ")"]
      Text[10224, 10236] chars:[10224, 10236, "split … oints"]
    Text[10311, 10312] chars:[10311, 10312, "."]
    SoftLineBreak[10312, 10313]
    Text[10313, 10317] chars:[10313, 10317, "See "]
    Link[10317, 10368] textOpen:[10317, 10318, "["] text:[10318, 10330, "this example"] textClose:[10330, 10331, "]"] linkOpen:[10331, 10332, "("] url:[10332, 10367, "test/comparison-tests/codeSplitting"] pageRef:[10332, 10367, "test/comparison-tests/codeSplitting"] linkClose:[10367, 10368, ")"]
      Text[10318, 10330] chars:[10318, 10330, "this  … ample"]
    Text[10368, 10373] chars:[10368, 10373, " and "]
    Link[10373, 10427] textOpen:[10373, 10374, "["] text:[10374, 10386, "this example"] textClose:[10386, 10387, "]"] linkOpen:[10387, 10388, "("] url:[10388, 10426, "test/comparison-tests/es6codeSplitting"] pageRef:[10388, 10426, "test/comparison-tests/es6codeSplitting"] linkClose:[10426, 10427, ")"]
      Text[10374, 10386] chars:[10374, 10386, "this  … ample"]
    Text[10427, 10445] chars:[10427, 10445, " for  … ails."]
  Heading[10447, 10457] textOpen:[10447, 10449, "##"] text:[10450, 10457, "License"]
    AnchorLink[10450, 10457]
      Text[10450, 10457] chars:[10450, 10457, "License"]
  Paragraph[10459, 10471] isTrailingBlankLine
    Text[10459, 10470] chars:[10459, 10470, "MIT L … cense"]
````````````````````````````````


