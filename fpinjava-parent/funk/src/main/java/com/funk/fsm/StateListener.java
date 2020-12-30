package com.funk.fsm;

public interface StateListener<A, S> {
    default void onChange(S newState, StateTuple<A, S> inputState) {
    }

    default void onError(Exception e, StateTuple<A, S> inputState) {
    }

    default void onComplete() {
    }
}
