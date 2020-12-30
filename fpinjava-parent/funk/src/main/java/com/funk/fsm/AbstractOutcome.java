package com.funk.fsm;

import com.funk.common.List;

public abstract class AbstractOutcome<S, E> {

    public S currentState;
    public S previousState;
    public List<Input<E>> internalInputs;

    public AbstractOutcome(S state, AbstractOutcome<S, E> previousOutcome) {
        this(state, previousOutcome.currentState, List.list());
    }

    public AbstractOutcome(S state, S previousState) {
        this(state, previousState, List.list());
    }

    public AbstractOutcome(S currentState, S previousState, List<Input<E>> internalInputs) {
        this.currentState = currentState;
        this.previousState = previousState;
        this.internalInputs = internalInputs;
    }

    public void pushInternalInputs(List<Input<E>> inputs) {
        internalInputs = internalInputs.concat(inputs);
    }

    public List<Input<E>> popInternalInputs() {
        if (internalInputs.isEmpty())
            return internalInputs;
        List<Input<E>> temp = internalInputs;
        internalInputs = List.list();
        return temp;
    }

    public abstract AbstractOutcome<S, E> create(S state);

    public abstract AbstractOutcome<S, E> create(S state, List<Input<E>> internalInputs);

    public abstract AbstractOutcome<S, E> failed(S state);

    public abstract AbstractOutcome<S, E> rejected(S state);

    public AbstractOutcome<S, E> next(S nextState) {
        return create(nextState);
    }

    public AbstractOutcome<S, E> next(S nextState, List<Input<E>> internalInputs) {
        return create(nextState, internalInputs);
    }

    @Override
    public String toString() {
        return "Outcome{" + "\n" +
                "currentState=" + currentState + "\n" +
                ", previousState=" + previousState +
                "}";
    }
}
