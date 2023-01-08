## Welcome to the Innovator Island workshop!

For workshop instructions, visit the [workshop's new instructions site](https://www.eventbox.dev/published/lesson/innovator-island/)

### Have an idea for this workshop? Found a bug? ###

If you have an idea for a module or feature in this workshop, or you have found a bug or need to report a problem, let us know!

- [Request a feature](https://github.com/aws-samples/aws-serverless-workshop-innovator-island/issues/new?assignees=&labels=&template=workshop-feature-request.md&title=)
- [Report an issue](https://github.com/aws-samples/aws-serverless-workshop-innovator-island/issues/new?assignees=&labels=&template=bug_report.md&title=)

Contact the author [@jbesw](https://twitter.com/jbesw) for any additional help or support requests.


aws s3 cp opencv-python-37.zip theme-park-sam-deploys-008744601422
aws lambda publish-layer-version --layer-name python-opencv-37 --description "OpenCV for Python 3.7" --content S3Bucket=theme-park-sam-deploys-008744601422,S3Key=opencv-python-37.zip --compatible-runtimes python3.7
