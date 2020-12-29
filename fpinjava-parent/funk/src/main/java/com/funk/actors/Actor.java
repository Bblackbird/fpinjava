package com.funk.actors;

import com.funk.common.Result;

import java.util.concurrent.CompletableFuture;

public interface Actor<T, R> {

  static <T, R> Result<Actor<T, R>> noSender() {
    return Result.empty();
  }

  default Result<Actor<T, R>> self() {return Result.success(this);}

  ActorContext<T, R> getContext();

  default CompletableFuture<Result<R>> tell(T message) {
    return tell(message, self());
  }

  CompletableFuture<Result<R>> tell(T message, Result<Actor<T, R>> sender);

  void shutdown();

  default CompletableFuture<Result<R>> tell(T message, Actor<T, R> sender) {
    return tell(message, Result.of(sender));
  }

  enum Type {DIRECT, SERIAL, PARALLEL, STRIPED}
}
