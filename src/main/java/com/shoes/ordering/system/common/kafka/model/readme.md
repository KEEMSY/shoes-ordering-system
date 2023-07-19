## KafkaModel 관련 

나는 kafka 를 사용 간, 직렬화를 위해 avro 를 사용하기로 하였으며, `root/src/main/resources/avro` 경로 내의 `.avsc` 파일들을 통해 빌드 시, Avro 클래스 파일들을 생성된다.
그리고 생성될(된) 파일들은 현 디렉토리에 생성하고자 했으나, 문제가 발생했다. 

- `root/src/main/java` 에 Avro 관련 클래스들이 생성된다.

    *하나의 디렉토리로 모으기위해 현재 `root/src/main/java/avro/com/shose/ordering/system`에 생성되도록 수정하였다.*

- 공식문서 및 많은 시도를 해보았지만, 해당 문제를 해결하지 못했다.

<br>

이로인해 현재 `kafka/model` 디렉토리는 빌드시 생성되는 Avro 클래스 파일들을 수동으로 옮겨 놓은 상태이다.

- 해당 클래스를 사용하는 `각 domain/messaging/publisher` 내의 클래스들의 import 관련 주의가 필요하다.
- 새롭게 Avro 클래스를 추가하는 경우(`.avsc` 파일 추가 시) 현 디렉토리에 파일을 추가해야한다.


*현 디렉토리에 관리를 하고자 여렇 시도를 해보았지만, 아직 해결하지 못했다.. 추후 개발을 더 진행하면서, 해결 할 수 있다면.. 해결해보도록 해야겠다..*

<br><hr>

> **사용중인 토픽**

- create-member-request(createMemberRequestTopicName)
- update-member-request(updateMemberRequestTopicName)
- create-product-request(createProductRequestTopicName)
- update-product-request(updateProductRequestTopicName)
