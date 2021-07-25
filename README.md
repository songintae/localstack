# AWS 통합 테스트 환경을 구축해보자

## 테스트 환경을 제공하는 localstack
localstack은 무료버전에서 S3, SQS등 다양한 AWS의 기능을 활용할 수 있게 Docker 기반의 컨테이너를 제공한다.
- localstack : https://localstack.cloud
- localstack wiki : https://github.com/localstack/localstack

## Testcontainers 이용한 AWS 테스트 코드 짜기
testcontainers 의존성을 주입 받음으로써 Junit 환경에서 Container를 통한 테스트 코드 작성 가능
1. 예시 TestCode : S3LocalStackTest
2. 공식 문서
 - Junit4 환경의 테스트 통합  : https://www.testcontainers.org/test_framework_integration/junit_4/
 - Jupiter / JUnit 5 환경의 테스트 통합 : https://www.testcontainers.org/test_framework_integration/junit_5/
 - localstack 활용 예제 : https://www.testcontainers.org/modules/localstack/

## localstack을 이용한 AWS 통합 테스트 환경 구축
1. container 접근을 위한 클래스 : LocalstackContainer
2. 공식 문서
 - https://github.com/localstack/localstack
 - https://github.com/localstack/localstack-java-utils


## AWS CLI로 localstack 확인 방법

### aws cli 설치
https://docs.aws.amazon.com/ko_kr/cli/latest/userguide/install-cliv2-mac.html
    
### credentials 설정    
https://docs.aws.amazon.com/ko_kr/cli/latest/userguide/cli-configure-files.html

1. ~/.aws/credentials에 설정
```text
[default]
AWS_ACCESS_KEY_ID=test
AWS_SECRET_ACCESS_KEY=test
REGION=ap-northeast-2
```
2. aws configure
```text
예시 입력

AWS Access Key ID [None]: test
AWS Secret Access Key [None]: test
Default region name [None]: ap-northeast-2
Default output format [None]: json
```

### API 전송 (예시 SSM ParameterStore)
1. paramter 생성
```text
aws ssm put-parameter --endpoint-url http://localhost:4566 \
--name "parameter" \
--type "String" \
--value "1" \
--overwrite
```

2. parameter 검색
```text
aws ssm get-parameter --endpoint-url http://localhost:4566 \
--name "parameter"
```




