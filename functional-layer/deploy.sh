mvn clean install

cd target
mkdir -p java/lib
cp functionalLayer-1.0-SNAPSHOT.jar java/lib
tar -a -c -f java.zip java

aws lambda publish-layer-version --layer-name theme-park-java-layer \
      --description "theme-park-java-layer" \
      --license-info "MIT" \
      --zip-file fileb://java.zip \
      --compatible-runtimes java11 \
      --compatible-architectures "arm64" "x86_64"