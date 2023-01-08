sam build

sam package --output-template-file packaged.yaml --s3-bucket theme-park-sam-deploys-008744601422

sam deploy --template-file packaged.yaml --stack-name theme-park-photos-compose --capabilities CAPABILITY_IAM --parameter-overrides FinalBucketName=theme-park-backend-finalbucket-1v5fxr1xxoh4l

