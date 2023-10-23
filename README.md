# Shoes Ordering System

*진행 중*

## Description

신발 주문 시스템 프로젝트는 **도메인 중심 설계(Domain-Driven Design, DDD), 클린 아키텍처(Ports and Adapters)** 를 적용한 내부 아키텍처 설계를 적용하고 **Kafka**를 활용한 **이벤트 기반 아키텍처(Event-Driven Architecture, EDA)** 를 결합한 프로젝트 입니다. 

 프로젝트의 핵심 목표는 **도메인 중심의 개발**과 **클린 아키텍처** 를 통한 **유지보수성 강화** 입니다. DDD의 원칙을 적용하여 각 **도메인의 응집도**를 높이고 **풍부한 도메인 모델** 을 형성하고자 하였습니다.

 기존 주문시스템에서의 **주문과 결제 도메인의 강한 결합 문제**를 해결하기 위해 **이벤트 기반 아키텍처** 를 도입했습니다. **주문 시, 결제 실패로 주문이 실패하는 것이 아닌, 오로지 주문 도메인 로직의 문제 시에만 실패할 수 있도록 설계** 했습니다.


<br><hr>

## Flow

> **Overal Flow**

<img width="1245" alt="스크린샷 2023-10-23 오후 2 55 38" src="https://github.com/KEEMSY/shoes-ordering-system/assets/96563125/edea2daa-762d-41bf-9905-cf423f6e1be6">

*개발은 비용적인 문제로인해, 로컬환경에서 디버그서버 및 컨테이너를 통해 개발 및 테스트를 진행했습니다.* 

- 개발 과정은 `local 개발 및 테스트 -> git push, git action 을 통한 CI 확보 -> 완료된 main 를 기준으로 로컬 컨테이너 환경 구성` 을 거쳐 진행 되었습니다.   
- 기본 구성요소는 다음과 같이 설정되어 있습니다.
    - Nginx(1) 
    - WAS(3)
    - Kafka Brokers(3)
    - MySQL(1)
    - Redis(1)

<br>

> **Internal FLow**

<img width="1854" alt="스크린샷 2023-10-23 오후 4 23 29" src="https://github.com/KEEMSY/shoes-ordering-system/assets/96563125/aa165b6a-3082-4347-827d-3bac85ce8995">


- `Ports and Adapters` 적용으로 크게 `Adapter` 와 `Domain` 으로 구성됩니다.
    - Adapter: domain 에서 제공하는 Port 를 구현한 구현체이며, `In`, `Out` Adapter 로 구분된다.
        - In: Domain 계층을 호출하며, 전체적인 흐름을 주도하는 어댑터이다. `Controller` 어댑터가 이에 해당한다.
        - Out: Domain 계층에 의해 호출되며, 주도되는 어댑터이다. `DataAccess(영속성관련)`, `Messaging` `외부 서비스` 어댑터들이 이에 해당한다.
    - Domain: 도메인로직(비즈니스로직)이 존재하며, 인터페이스를 제공 `Port` 를 제공하며, `Port` 를 통해 의존성의 방향을 도메인을 향하게 한다.

<br><hr>

## Skills

|Infra|BackEnd|
|--|--|
|<img width="100%" src="https://github.com/KEEMSY/shoes-ordering-system/assets/96563125/18b47165-c620-4d3c-9c22-ac00e44442b4" />|<img width="100%" src="https://github.com/KEEMSY/shoes-ordering-system/assets/96563125/e9895118-da48-457d-bdb4-06eab1c7cff6" />|


<br><hr>

## ETC

### [프로젝트 세팅](https://github.com/KEEMSY/shoes-ordering-system/wiki/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%EC%84%B8%ED%8C%85)
### [ADRs](https://github.com/KEEMSY/shoes-ordering-system/wiki)

