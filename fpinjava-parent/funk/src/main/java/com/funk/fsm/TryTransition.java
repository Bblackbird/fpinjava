package com.funk.fsm;

import com.funk.common.Function;

import java.util.Objects;
// import lombok.extern.slf4j.Slf4j

//@Slf4j
public class TryTransition<A, S> implements Transition<A, S> {

    private Transition<A, S> transition;
    private Function<Exception, Function<StateTuple<A, S>, S>> errorHandler;

    public TryTransition(Transition<A, S> transition, Function<Exception, Function<StateTuple<A, S>, S>> errorHandler) {
        Objects.requireNonNull(transition);
        Objects.requireNonNull(errorHandler);
        this.transition = transition;
        this.errorHandler = errorHandler;
    }

    public static <U, V> TryTransition<U, V> of(Transition<U, V> transition,
                                                Function<Exception, Function<StateTuple<U, V>, V>> errorHandler) {
        return new TryTransition<>(transition, errorHandler);
    }

    @Override
    public S apply(StateTuple<A, S> arg) {
        try {
            return transition.apply(arg);
        } catch (Exception e) {
            return errorHandler.apply(e).apply(arg);
        }
    }

    @Override
    public <V> Function<V, S> compose(Function<V, StateTuple<A, S>> before) {
        return transition.compose(before);
    }

    @Override
    public <V> Function<StateTuple<A, S>, V> andThen(Function<S, V> after) {
        return transition.andThen(after);
    }
}
