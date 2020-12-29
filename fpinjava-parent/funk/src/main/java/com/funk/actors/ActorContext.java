package com.funk.actors;


public interface ActorContext<T, R> {

  void become(MessageProcessor<T, R> behavior);

  MessageProcessor<T, R> getBehavior();
}
