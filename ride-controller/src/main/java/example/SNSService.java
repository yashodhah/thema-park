package example;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import software.amazon.awssdk.services.sns.model.SnsException;

public class SNSService {

    public void pubTopic( String message, String topicArn) {

        try {
            SnsClient snsClient = SnsClient.builder()
                    .region(AppSettings.REGION)
                    .credentialsProvider(ProfileCredentialsProvider.create())
                    .build();

            PublishRequest request = PublishRequest.builder()
                    .message(message)
                    .topicArn(AppSettings.TOPIC_ARN)
                    .build();

            PublishResponse result = snsClient.publish(request);
            System.out.println(result.messageId() + " Message sent. Status is " + result.sdkHttpResponse().statusCode());

        } catch (SnsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}
