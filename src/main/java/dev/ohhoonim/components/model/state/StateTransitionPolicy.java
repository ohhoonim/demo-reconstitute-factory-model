package dev.ohhoonim.components.model.state;

/*
@startuml
title State Transition Model

note  as N1
    S: extends Status<S, T, C>
    T: extends TransitionEvent<S, C>
    C: Context
end note

interface StateTransitionPolicy<S, T, C> {
    default transition(status:S, transitionEvent:T): TransitionResult<S, C> 
}

note top of StateTransitionPolicy
transition 메서드 구현 
    return status.trigger(transitionEvent)
end note

interface Status<S, T, C> {
    trigger(event: T): TransitionResult<S, C>
}

interface TransitionEvent<C> {
    actions(): List<PostAction<C>>
}

interface TransitionResult<S, C> {
    status(): S
    actions(): List<PostAction<C>>
}

interface PostAction<C> <<FunctionalInterface>> {
    followUp(context: C): void
}

@enduml

@startuml
hide empty description

title Post State Transition

Published: Post 작성완료
Inprogress: 임시저장상태

[*] --> Published: Save 
[*] --> Inprogress: Draft 
Inprogress --> Published: Save 
Inprogress--> [*] : Draft

Published --> [*]


@enduml

*/


public interface StateTransitionPolicy<S extends Status<S, T, C>, T extends TransitionEvent<S, C>, C> {
    public default TransitionResult<S, C> transition(S status, T transitionEvent) {
        return status.trigger(transitionEvent);
    }
}
