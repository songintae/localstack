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
    private static final String DEFAULT_AWS_ACCESS_KEY_ID = "accessKey";
    private static final String DEFAULT_AWS_SECRET_ACCESS_KEY = "secretAccessKey";
    private static final String DEFAULT_REGIONS = Regions.AP_NORTHEAST_2.getName();

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

    public AwsClientBuilder.EndpointConfiguration getEndpointConfiguration() {
        return new AwsClientBuilder.EndpointConfiguration(getEndpoint(), getRegions());
    }

    public AWSCredentialsProvider getDefaultCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(getAccessKey(), getSecretKey()));
    }

    public String getEndpoint() {
        return String.format("http://%s:%s", getHostname(), getPort());
    }

    private String resolveLocalstackHostName(String hostname) {
        try {
            return InetAddress.getByName(hostname).getHostAddress();
        } catch (UnknownHostException e) {
            throw new IllegalStateException("Cannot obtain hostname", e);
        }
    }
}
