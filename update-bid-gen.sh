#!/usr/bin/env bash

BID_GEN="/Users/vlad/src/johner-institut/BidGenConsulting"
VERSION="0.28.30"

rm ${BID_GEN}/lib/flexmark-*.jar

# cp mod/target/mod-${VERSION}.jar ${BID_GEN}/lib
cp flexmark/target/flexmark-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-util/target/flexmark-util-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-docx-converter/target/flexmark-docx-converter-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-formatter/target/flexmark-formatter-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-autolink/target/flexmark-ext-autolink-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-definition/target/flexmark-ext-definition-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-emoji/target/flexmark-ext-emoji-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-footnotes/target/flexmark-ext-footnotes-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-gfm-strikethrough/target/flexmark-ext-gfm-strikethrough-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-ins/target/flexmark-ext-ins-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-superscript/target/flexmark-ext-superscript-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-tables/target/flexmark-ext-tables-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-toc/target/flexmark-ext-toc-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-wikilink/target/flexmark-ext-wikilink-${VERSION}.jar ${BID_GEN}/lib
cp flexmark-ext-typographic/target/flexmark-ext-typographic-${VERSION}.jar ${BID_GEN}/lib
