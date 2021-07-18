# AWS 통합 테스트 환경을 구축해보자

## 테스트 환경을 제공하는 localstack
localstack은 무료버전에서 S3, SQS등 다양한 AWS의 기능을 활용할 수 있게 Docker 기반의 컨테이너를 제공한다.
localstack wiki : https://github.com/localstack/localstack#configurations

## Testcontainers 이용한 AWS 테스트 코드 짜기
testcontainers 의존성을 주입 받음으로써 Junit 환경에서 Container를 통한 테스트 코드 작성 가능
1. 예시 TestCode : S3LocalStackTest
2. 공식 문서
 - Junit4 환경의 테스트 통합  : https://www.testcontainers.org/test_framework_integration/junit_4/
 - Jupiter / JUnit 5 환경의 테스트 통합 : https://www.testcontainers.org/test_framework_integration/junit_5/
 - localstack 활용 예제 : https://www.testcontainers.org/modules/localstack/


   




