#!/usr/bin/env bash
HOME_DIR="/Users/vlad/src/projects/flexmark-java"

cd ${HOME_DIR} || exit

# Updated by plugin-util
#cp out/artifacts/flexmark-util.jar ../MissingInActions/lib
#echo updated out/artifacts/flexmark-util.jar in ../MissingInActions/lib

#cp out/artifacts/flexmark-util.jar ../touch-typists-completion-caddy/lib
#echo updated out/artifacts/flexmark-util.jar in ../touch-typists-completion-caddy/lib

#cp out/artifacts/flexmark-util.jar ../CLionArduinoPlugin/lib
#echo updated out/artifacts/flexmark-util.jar in ../CLionArduinoPlugin/lib
cp out/artifacts/flexmark-test-util.jar ../CLionArduinoPlugin/lib
echo updated out/artifacts/flexmark-test-util.jar in ../CLionArduinoPlugin/lib
cp out/artifacts/flexmark-formatter.jar ../CLionArduinoPlugin/lib
echo updated out/artifacts/flexmark-formatter.jar in ../CLionArduinoPlugin/lib

cp out/artifacts/flexmark-parent.jar ../idea-multimarkdown3/lib
echo updated out/artifacts/flexmark-parent.jar in ../idea-multimarkdown3/lib

cp out/artifacts/flexmark-parent.jar ../idea-multimarkdown2/lib
echo updated out/artifacts/flexmark-parent.jar in ../idea-multimarkdown2/lib

cp out/artifacts/flexmark-parent.jar ../idea-multimarkdown1/lib
echo updated out/artifacts/flexmark-parent.jar in ../idea-multimarkdown1/lib

cp out/artifacts/flexmark-parent.jar ../tree-iteration/lib
echo updated out/artifacts/flexmark-parent.jar in ../tree-iteration/lib

cp out/artifacts/flexmark-util.jar ../plugin-util/lib
echo updated out/artifacts/flexmark-util.jar in ../plugin-util/lib

cp out/artifacts/flexmark-parent.jar ../markdown-profiling/lib
echo updated out/artifacts/flexmark-parent.jar in ../markdown-profiling/lib
#cp out/artifacts/flexmark-parent.jar ../netbeans-markdown-navigator/release/modules/ext/flexmark-all.jar
#echo updated out/artifacts/flexmark-parent.jar in ../netbeans-markdown-navigator/release/modules/ext/flexmark-all.jar

cp out/artifacts/flexmark-parent.jar ../MarkdownTest/lib
echo updated out/artifacts/flexmark-parent.jar in ../MarkdownTest/lib
