sam package --output-template-file packaged.yaml --s3-bucket theme-park-sam-deploys-008744601422
sam deploy --template-file packaged.yaml --stack-name theme-park-real-time-ride-controller --capabilities CAPABILITY_IAM --parameter-overrides DynamoDBTableName=theme-park-backend-DynamoDBTable-KQ1MONI7499 IOTendpoint=arn:aws:iot:us-east-1:008744601422:thing/theme-park-realtime
