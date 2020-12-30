package com.funk.fsm;

import com.funk.common.Function;

import java.util.Objects;

public class ObservableTransition<A, S> implements Transition<A, S> {

    private Transition<A, S> transition;
    private StateListener<A, S> listener;

    public ObservableTransition(Transition<A, S> transition, StateListener<A, S> listener) {
        Objects.requireNonNull(transition);
        Objects.requireNonNull(listener);
        this.transition = transition;
        this.listener = listener;
    }

    public static <A, S> Function<StateListener<A, S>, Function<Transition<A, S>, Transition<A, S>>> highCompose() {
        return listener -> transition -> of(transition, listener);
    }

    public static <A, S> Function<Transition<A, S>, Transition<A, S>> partialObservable(StateListener<A, S> listener) {
        Function<StateListener<A, S>, Function<Transition<A, S>, Transition<A, S>>> f = highCompose();
        return f.apply(listener);
    }

    private static <S, A> Transition<A, S> of(Transition<A, S> transition, StateListener<A, S> listener) {
        return new ObservableTransition<>(transition, listener);
    }

    @Override
    public S apply(StateTuple<A, S> arg) {
        try {
            S newState = transition.apply(arg);
            listener.onChange(newState, arg);
            return newState;
        } catch (Exception e) {
            listener.onError(e, arg);
            return arg.state;
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
