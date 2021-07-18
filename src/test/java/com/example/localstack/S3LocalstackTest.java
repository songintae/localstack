package com.example.localstack;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

/**
 * TestContainer중 하나인 localstackContainer를 통해 AWS 통합테스트 환경을 구축
 */
@Testcontainers
class S3LocalStackTest {

    DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:0.11.3");

    @Container
    public LocalStackContainer localstack = new LocalStackContainer(localstackImage)
            .withServices(S3);

    @Test
    void S3UploadTest() {
        AmazonS3 s3 = AmazonS3ClientBuilder
                .standard()
                .withEndpointConfiguration(localstack.getEndpointConfiguration(S3))
                .withCredentials(localstack.getDefaultCredentialsProvider())
                .build();

        s3.createBucket("foo");
        s3.putObject("foo", "bar", "baz");
    }
}
