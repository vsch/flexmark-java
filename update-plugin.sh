#!/usr/bin/env bash
HOME_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" >/dev/null 2>&1 && pwd)"

cd "${HOME_DIR}" || exit

function UpdProjects() {
  JAR="$1"

  echo updating "${JAR}" in
  for PROJECT in "${@:2}"; do
    echo "   " "${PROJECT}"
    cp "out/artifacts/${JAR}" "${PROJECT}"
  done
}

function UpdJars() {
  PROJECT="$1"

  echo updating "${PROJECT}" with
  for JAR in "${@:2}"; do
    echo "   " "${JAR}"
    cp "out/artifacts/${JAR}" "${PROJECT}"
  done
}

# Update flexmark-util.jar flexmark-tree-iteration.jar
#UpdJars ../plugin-util/lib flexmark-util.jar flexmark-tree-iteration.jar
#UpdJars ../plugin-test-util/lib flexmark-util.jar flexmark-test-util.jar
UpdJars ../MissingInActions/lib flexmark-util.jar flexmark-tree-iteration.jar
UpdJars ../idea-multimarkdown3/lib flexmark-util.jar flexmark-test-util.jar flexmark-tree-iteration.jar flexmark-parent.jar
UpdJars ../touch-typists-completion-caddy/lib flexmark-util.jar flexmark-tree-iteration.jar

# Update flexmark-test-util.jar
#UpdJars ../CLionArduinoPlugin/lib flexmark-test-util.jar flexmark.jar flexmark-util.jar

# This contains util and tree-iteration
UpdProjects flexmark-parent.jar ../markdown-profiling/lib ../MarkdownTest/lib
#UpdProjects flexmark-parent.jar ../idea-multimarkdown3/lib ../idea-multimarkdown2/lib ../idea-multimarkdown1/lib ../markdown-profiling/lib ../MarkdownTest/lib
