package com.example.runner;

import com.amazonaws.services.simplesystemsmanagement.AWSSimpleSystemsManagement;
import com.amazonaws.services.simplesystemsmanagement.model.DeleteParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;
import com.amazonaws.services.simplesystemsmanagement.model.GetParameterResult;
import com.amazonaws.services.simplesystemsmanagement.model.PutParameterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * https://docs.aws.amazon.com/systems-manager/latest/APIReference/API_PutParameter.html
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ParametersStoreRunner implements ApplicationRunner {

    private final AWSSimpleSystemsManagement awsSimpleSystemsManagement;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        PutParameterRequest putParameterRequest = new PutParameterRequest(); {
            putParameterRequest.withName("parametersStoreRunner");
            putParameterRequest.withValue("1");
        }
        awsSimpleSystemsManagement.putParameter(putParameterRequest);

        GetParameterRequest getParameterRequest = new GetParameterRequest(); {
            getParameterRequest.withName("parametersStoreRunner");
        }
        GetParameterResult parameter = awsSimpleSystemsManagement.getParameter(getParameterRequest);
        log.info("ParametersStoreRunner.parameter : {}", parameter.getParameter());

        DeleteParameterRequest deleteParameterRequest = new DeleteParameterRequest(); {
            deleteParameterRequest.withName("parametersStoreRunner");
        }

        awsSimpleSystemsManagement.deleteParameter(deleteParameterRequest);
    }
}
