1. build.gradle 설정
```groovy
...,

dependencies{
    ...,
    implementation 'io.awspring.cloud:spring-cloud-aws-s3:3.0.2'
    ...,
}

...,
```

2. application.yml
```yaml
spring:
  profiles:
    default: dev, security, aws
```

3. application-aws.yml
```yaml
spring:
  cloud:
    aws:
      credentials:
        accessKey: 발급받은 AccessKey
        secretKey: 발급받은 SecretKey
      s3:
        bucket: 생성한 버킷명
      region:
        static: us-east-2
      stack:
        auto: false
```
※ s3 정보 버킷과 리전
![2024-02-15 15 22 08 (5).png](s3_create%2F2024-02-15%2015%2022%2008%20%285%29.png)

※ s3 접근 액세스키, 시크릿키
![2024-02-15 15 40 42 (8).png](iam-token%2F2024-02-15%2015%2040%2042%20%288%29.png)


4. AWS S3 사용을 위한 스프링 설정 코드
```java
@Configuration
public class AWSConfig {
    @Value("${spring.cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secretKey}")
    private String accessSecret;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Bean
    public S3Client s3Client() {
        return S3Client.builder()
                .credentialsProvider(this::awsCredentials)
                .region(Region.of(region))
                .build();
    }

    private AwsCredentials awsCredentials() {
        return AwsBasicCredentials.create(accessKey, accessSecret);
    }
}


```
