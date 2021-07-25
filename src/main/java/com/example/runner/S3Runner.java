package com.example.runner;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * https://docs.aws.amazon.com/AmazonS3/latest/API/API_CreateBucket.html
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class S3Runner implements ApplicationRunner {
    private final AmazonS3 amazonS3;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        amazonS3.createBucket("foo");
        PutObjectResult result = amazonS3.putObject("foo", "bar", "baz");

        log.info("S3Runner result : {}", result);

    }
}
