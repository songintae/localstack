package com.example.localstack;

import com.amazonaws.auth.*;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import org.springframework.beans.factory.InitializingBean;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class LocalStackContainer{

    private static final String DEFAULT_HOST_NAME = "localhost";
    private static final int DEFAULT_PORT = 4566;
    private static final String DEFAULT_AWS_ACCESS_KEY_ID = "test";
    private static final String DEFAULT_AWS_SECRET_ACCESS_KEY = "test";
    private static final String DEFAULT_REGIONS = Regions.AP_NORTHEAST_2.name();

    private final String hostname;
    private final int port;
    private final String regions;
    private final String accessKey;
    private final String secretKey;

    public LocalStackContainer() {
        this(DEFAULT_HOST_NAME, DEFAULT_PORT, DEFAULT_REGIONS, DEFAULT_AWS_ACCESS_KEY_ID, DEFAULT_AWS_SECRET_ACCESS_KEY);
    }

    public LocalStackContainer(String hostname, int port, String regions, String accessKey, String secretKey) {
        this.hostname = resolveLocalstackHostName(hostname);
        this.port = port;
        this.regions = regions;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getRegions() {
        return regions;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public AwsClientBuilder.EndpointConfiguration getEndpointConfiguration(Service service) {
        return new AwsClientBuilder.EndpointConfiguration(getEndpoint(service), getRegions());
    }

    public AWSCredentialsProvider getDefaultCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey()));
    }

    public String getEndpoint(Service service) {
        String endpoint = String.format("http://%s:%s", getHostname(), getPort());

        /*
         * Use the domain name wildcard *.localhost.localstack.cloud which maps to
         * 127.0.0.1 We need to do this because S3 SDKs attempt to access a domain
         * <bucket-name>.<service-host-name> which by default would result in
         * <bucket-name>.localhost, but that name cannot be resolved (unless hardcoded
         * in /etc/hosts)
         */
        if(service.equals(Service.S3) && hostname.contains(DEFAULT_HOST_NAME)) {
            return endpoint.replace(DEFAULT_HOST_NAME, "test.localhost.atlassian.io");
        }

        return endpoint;
    }

    private String resolveLocalstackHostName(String hostname) {
        try {
            return InetAddress.getByName(hostname).getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Cannot obtain hostname", e);
        }
    }

    private String resolveHostName() {
        try {
            // resolve IP address and use that as the endpoint so that path-style access is automatically used for S3
            String ipAddress = InetAddress.getByName(DEFAULT_HOST_NAME).getHostAddress();
            String DEFAULT_EDGE_PORT = "4566";
            return new URI("http://" +
                    ipAddress +
                    ":" +
                    DEFAULT_EDGE_PORT).toString();
        } catch (UnknownHostException | URISyntaxException e) {
            throw new IllegalStateException("Cannot obtain endpoint URL", e);
        }
    }


    public enum Service {
        API_GATEWAY("apigateway"),
        EC2("ec2"),
        KINESIS("kinesis"),
        DYNAMODB("dynamodb"),
        DYNAMODB_STREAMS("dynamodbstreams"),
        S3("s3"),
        FIREHOSE("firehose"),
        LAMBDA("lambda"),
        SNS("sns"),
        SQS("sqs"),
        REDSHIFT("redshift"),
        SES("ses"),
        ROUTE53("route53"),
        CLOUDFORMATION("cloudformation"),
        CLOUDWATCH("cloudwatch"),
        SSM("ssm"),
        SECRETSMANAGER("secretsmanager"),
        STEPFUNCTIONS("stepfunctions"),
        CLOUDWATCHLOGS("logs"),
        STS("sts"),
        IAM("iam"),
        KMS("kms");

        String localStackName;

        public String getName() {
            return localStackName;
        }

        Service(String localStackName) {
            this.localStackName = localStackName;
        }
    }
}
