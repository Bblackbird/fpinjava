package com.funk.actors;


import com.funk.common.Result;

public interface MessageProcessor<T, R> {

  R process(T t, Result<Actor<T, R>> sender);
}
