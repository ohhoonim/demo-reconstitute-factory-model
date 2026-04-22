# State Transition Model

## Model 구성요소

- Status<S, T, C>
- TransitionEvent<S, C>
- PostAction<C>
- TransitionResult<S, C>
- StateTransitionPolicy<S, T, C>


## 구현

- 각 모델 구성요소를 implements 한다.  

### [model] Status<S, T, C>

```java
public sealed interface PostStatus extends Status<PostStatus, PostTransitionEvent, Post> permits Published, Draft {

    public record Published() implements PostStatus{}
    public record Draft() implements PostStatus{}

}

```

### [model] TransitionEvent<S, C>

```java
public sealed interface PostTransitionEvent extends TransitionEvent<PostStatus, Post> permits Save, Draft {
    public record Save() implements PostTransitionEvent{
        @Override
        public List<PostAction<Post>> actions() {
            return List.of(
                // 여기에 람다식으로 적어주면 됨 (post) -> ....할일
                // PostAction<C>는 일종의 Consumer 이다.
            );
        }
    }
    public record Draft(List<PostAction<Post>> actions) implements PostTransitionEvent{}
}
```

💡 record 구현시 actions를 필드로 선언하면 함수형으로 외부 주입도 가능하다. 

### [N/A] PostAction<C>
(** 여기서 Post AR과는 전혀 무관하다)

- 일종의 Consumer 이다. 
- @FunctionalInterface 이므로 람다식으로 적어주면 된다.

### [model] TransitionResult<S, C>

구현안하면 상당히 귀찮아지니 구현해두자 

```java

public record PostTransitionResult(PostStatus status, List<PostAction<Post>> actions)
        implements TransitionResult<PostStatus, Post> {
}

```

### [model] StateTransitionPolicy<S, T, C>

간단하게 확장된 인터페이스로 만들어주면된다.(marker interface) 

```java

public interface PostStateTransitionPolicy extends StateTransitionPolicy<PostState, PostTransitionEvent, Post> {

}

```

StateTransitionPolicy는 다음과 같은 default 메소드를 제공한다.

```java
public default TransitionResult<S, C> transition(S status, T transitionEvent) {
    return status.trigger(transitionEvent);
}
```

### [Aggregate Root 수정] status 필드 추가 및 transition 메서드 작성

```java

public void transition(PostTransitionEvent event, PostStateTransitionPolicy policy) {
    var transitionResult = policy.transition(this.status, event);
    this.setStatus(transitionResult.status());
    transitionResult.actions().forEach(action -> action.followup(this));
}

public void setStatus(PostStatus status) {
    this.status = status;
}

```