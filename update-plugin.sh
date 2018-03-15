#!/usr/bin/env bash
HOME_DIR="/Users/vlad/src/flexmark-java"

cd ${HOME_DIR}

cp out/artifacts/flexmark-util.jar ../MissingInActions/lib
echo updated out/artifacts/flexmark-util.jar in ../MissingInActions/lib
cp out/artifacts/flexmark-parent.jar ../idea-multimarkdown2/lib
echo updated out/artifacts/flexmark-parent.jar in ../idea-multimarkdown2/lib
cp out/artifacts/flexmark-parent.jar ../markdown-profiling/lib
echo updated out/artifacts/flexmark-parent.jar in ../markdown-profiling/lib
#cp out/artifacts/flexmark-parent.jar ../netbeans-markdown-navigator/release/modules/ext/flexmark-all.jar
#echo updated out/artifacts/flexmark-parent.jar in ../netbeans-markdown-navigator/release/modules/ext/flexmark-all.jar
