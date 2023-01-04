package hayk.harutyunyan.aws.s3.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class S3Configuration {

    @Bean
    public S3Client s3Client (
            @Value("${aws.region}") String s3Region,
            @Value("${aws.s3.key-id}") String s3KeyId,
            @Value("${aws.s3.access-key}") String s3AccessKey
    ) {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                s3KeyId,
                s3AccessKey);
        return S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(s3Region))
                .build();
    }

    @Bean
    public String s3Bucket (@Value("${aws.s3.bucket}") String s3Bucket) {
        return s3Bucket;
    }
}
