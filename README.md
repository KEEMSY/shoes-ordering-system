# Shoes Ordering System

진행 중

## **Context**

- [신발 주문 시스템을 계획하게 된 배경](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C%EC%9D%84-%EA%B3%84%ED%9A%8D%ED%95%98%EA%B2%8C-%EB%90%9C-%EB%B0%B0%EA%B2%BD)


<br><hr>

## **Decision**

- [신발 주문 시스템 분석 - 도메인](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EB%B6%84%EC%84%9D---%EB%8F%84%EB%A9%94%EC%9D%B8)
- [신발 주문 시스템 분석 - 아키텍처](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EB%B6%84%EC%84%9D---%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98)

<br><hr>

## **Consequence**

> **내부 아키텍처 설계**

- [도메인 설계](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%84%A4%EA%B3%84---%EB%8F%84%EB%A9%94%EC%9D%B8)
    - [Member](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EB%B6%84%EC%84%9D---%EB%8F%84%EB%A9%94%EC%9D%B8-:-Member)
    - [Product](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EB%B6%84%EC%84%9D---%EB%8F%84%EB%A9%94%EC%9D%B8-:-Product)
    - Order
    - Payment

<br>

> **외부 아키텍처 설계**

- [아키텍처 설계](https://github.com/KEEMSY/shoes-ordering-system/wiki/%EC%8B%A0%EB%B0%9C-%EC%A3%BC%EB%AC%B8-%EC%8B%9C%EC%8A%A4%ED%85%9C-%EC%84%A4%EA%B3%84---%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98)


<br><hr>

## 소개

신발 주문 시스템 프로젝트의 목표는 도메인 주도 설계(DDD), 클린 아키텍처(포트 및 어댑터) 및 이벤트 기반 아키텍처 접근 방식을 결합하여
도메인 중심의 구조화되고 모듈식이며 그리고 확장 가능한 시스템을 설계하는 것이다.
이에 더해, 신발 주문 영역의 요구 사항을 충족하고 향후 개발 및 개선을 위한 효율적이고 유연한 프로젝트를 목표로 설정하였다.

<br>

> **도메인 분석 및 설계(Domain Driven Design)**

프로젝트의 초기 단계에서, 신발 주문 시스템을 분석하고 설계하기 위해 도메인 주도 설계 원칙에 중점을 두었다.
핵심 도메인 개념, 동작 및 비즈니스 규칙을 식별함으로써 도메인에 대한 이해를 하고, 특정 도메인 요구 사항에 맞는
솔루션을 개발하는 것이 목표이다.

<br>

> **클린아키텍처(Ports and Adapters)**

클린 아키텍처 원칙, 특히 포트 및 어댑터 패턴을 프로젝트 설계에 적용하기 위해 노력했다.
이 접근법은 관심사의 분리와 서로 다른 계층 간의 명확한 경계 설정을 강조하며, 각 계층의 의존성은 도메인 계층을 향해야 한다.
포트/인터페이스 및 해당 어댑터/구현 클래스를 정의함으로써, 외부 시스템 및 내부 구성 요소를
변경할 수 있는 유연한 아키텍처를 달성하는 것을 목표로 하였다.

<br>

> **이벤트 기반 아키텍처(Event Driven Architecture)**

이 프로젝트의 핵심은 이벤트 기반 아키텍처의 적용이라고 말할 수 있다.
이벤트 기반 설계 원칙을 채택함으로써 서비스 간 느슨한 결합을 촉진하고 이벤트 기반 워크플로를 활성화하며
신발 주문 시스템의 전반적인 확장성과 성능을 향상시키는 것을 목표로 하였다.

