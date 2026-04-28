# Aggregate Root 작성 가이드

## 1. 개요
Aggregate Root(이하 AR)는 도메인 모델의 진입점이자 데이터 정합성을 보장하는 경계입니다. `dev.ohhoonim.component.model.unit` 패키지의 기반 클래스들을 사용하여 식별성(Identity)과 이력 관리(Auditing) 기능을 일관되게 구현합니다.

## 2. 식별자 (EntityId) 정의
모든 AR은 고유한 식별자 타입을 가져야 합니다.
- **형식**: Java `record` 사용 권장.
- **인터페이스**: `EntityId`를 구현.
- **표준 패턴**:
    - 생성자에서 ULID 유효성 검증.
    - `Creator` 인터페이스 구현을 통한 생성/변환 로직 제공.

```java
public record MyId(String userId) implements EntityId {
    public MyId {
        if (userId == null || !Ulid.isValid(userId)) {
            throw new MyException("올바른 형식의 id가 아닙니다");
        }
    }
    public static Creator<MyId> Creator = new Creator<>() {
        @Override public MyId from(String value) { return new MyId(value); }
        @Override public MyId generate() { return new MyId(UlidCreator.getUlid().toString()); }
    };
    @Override public String getRawValue() { return userId; }
}
```

## 3. Aggregate Root 구조 및 구현

### 3.1 기본 상속 및 필드
- `BaseEntity<T extends EntityId>`를 상속받습니다.
- 관련 속성들은 컴포넌트(`record`) 단위로 묶어서 관리합니다.

### 3.2 생성자 설계
1. **신규 생성용 (Protected)**: 비즈니스 로직에 의해 처음 만들어질 때 사용합니다. `super(id, operator)`를 호출하여 생성/수정 정보를 자동 설정합니다.
2. **DB 복원용 (Private)**: 인프라 어댑터에서 기존 데이터를 불러올 때 사용합니다. 모든 Auditing 정보를 매개변수로 받아 부모 생성자에 전달합니다.

### 3.3 복원 메서드 (Reconstitute)
인프라 계층(`infra.adapter`)에서 도메인 객체를 다시 조립할 때 사용하는 정적 메서드입니다.

```java
public static MyAR reconstitute(MyId id, MyComponent component, MyStatus status,
        Instant createdAt, String createdBy, Instant modifiedAt, String modifiedBy) {
    return new MyAR(id, component, status, createdAt, createdBy, modifiedAt, modifiedBy);
}
```

## 4. 상태 관리 및 비즈니스 규칙
- **상태 패턴**: `UserStatus` 예시와 같이 `sealed interface`와 `record`를 사용한 상태 패턴 적용을 권장합니다.
- **수정 기록**: AR 내부에서 상태를 변경하는 비즈니스 메서드가 실행될 때, 마지막에 반드시 `recordModification(operator)`을 호출하여 수정 이력을 갱신합니다.
- **캡슐화**: 외부에서 직접 필드를 수정하지 못하도록 `Setter` 사용을 지양하고 의도가 담긴 메서드를 제공합니다.

## 5. 컴포넌트 (Value Objects)
- 데이터 그룹은 `record`를 사용하여 불변성을 유지합니다.
- `sealed interface [Name]Component` 패턴을 사용하여 한 AR에 속한 다양한 컴포넌트들을 한 곳에서 명시적으로 관리할 수 있습니다.
