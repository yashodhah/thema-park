mvn package

#sam package --output-template-file packaged.yaml --s3-bucket theme-park-sam-deploys-008744601422
#sam deploy --template-file packaged.yaml --stack-name theme-park-ride-times --capabilities CAPABILITY_IAM

aws cloudformation package --template-file template.yaml --s3-bucket theme-park-sam-deploys-008744601422 --output-template-file packaged.yaml
aws cloudformation deploy --template-file packaged.yaml --stack-name theme-park-ride-times --capabilities CAPABILITY_IAM