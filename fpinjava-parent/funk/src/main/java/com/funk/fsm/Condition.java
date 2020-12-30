package com.funk.fsm;

import com.funk.common.Function;

public interface Condition<I, S> extends Function<StateTuple<I, S>, Boolean> {
}
