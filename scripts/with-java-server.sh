#!/usr/bin/env bash

# 2, because that's the number of JVMs we test on currently - 11, 12
mod="$((${GO_PIPELINE_COUNTER} % 2))"

function use_jdk() {
    if [ ! -f "${HOME}/.jabba/jabba.sh" ]; then
      curl -sL https://github.com/shyiko/jabba/raw/master/install.sh | bash
    fi
    source "${HOME}/.jabba/jabba.sh"
    jabba install "$1=$2"
    jabba use "$1"
}

if  "$mod" = "0" ]; then
  use_jdk "openjdk@1.11.0-28" "tgz+https://nexus.gocd.io/repository/s3-mirrors/local/jdk/openjdk-11-28_linux-x64_bin.tar.gz"
elif [ "$mod" = "1" ]; then
  use_jdk "openjdk@1.12.0" "tgz+https://download.java.net/java/GA/jdk12/GPL/openjdk-12_linux-x64_bin.tar.gz"
fi

echo "JAVA_HOME set to : $JAVA_HOME"

exec "$@"
