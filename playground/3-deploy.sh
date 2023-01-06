##!/bin/bash
#set -eo pipefail
#ARTIFACT_BUCKET=$(cat bucket-name.txt)
#TEMPLATE=template.yml
mvn package
#
#aws cloudformation package --template-file $TEMPLATE --s3-bucket $ARTIFACT_BUCKET --output-template-file out.yml
#aws cloudformation deploy --template-file out.yml --stack-name playground --capabilities CAPABILITY_NAMED_IAM

aws cloudformation package --template-file template.yml --s3-bucket lambda-artifacts-2de8d5b30a607751 --output-template-file out.yml
aws cloudformation deploy --template-file out.yml --stack-name playground --capabilities CAPABILITY_NAMED_IAM