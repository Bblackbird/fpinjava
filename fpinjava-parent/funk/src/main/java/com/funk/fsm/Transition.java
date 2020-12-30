package com.funk.fsm;

import com.funk.common.Function;

public interface Transition<A, S> extends Function<StateTuple<A, S>, S> {
}
