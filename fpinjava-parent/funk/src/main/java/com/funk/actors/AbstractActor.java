package com.funk.actors;

import com.funk.common.Result;
import com.funk.common.Tuple;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public abstract class AbstractActor<T, R> implements Actor<T, R> {

  private final ActorContext<T, R> context;
  protected final String id;
  private final ExecutorService executor;

  public AbstractActor(String id, Type type, String threadNamePrefix) {
    super();
    this.id = id;
    this.executor = type == Type.SERIAL
        ? Executors.newSingleThreadExecutor(new DaemonThreadFactory(threadNamePrefix))
        : Executors.newCachedThreadPool(new DaemonThreadFactory(threadNamePrefix));

    this.context = new ActorContext<T, R>() {
      private MessageProcessor<T, R> behavior =
          AbstractActor.this::onReceive;
      @Override
      public synchronized void become(MessageProcessor<T, R> behavior) {
        this.behavior = behavior;
      }

      @Override
      public MessageProcessor<T, R> getBehavior() {
        return behavior;
      }
    };
  }

  public abstract R onReceive(T message, Result<Actor<T, R>> sender);

/*  public Result<Actor<T, R>> self() {
    return Result.success(this);
  }*/

  public ActorContext<T, R> getContext() {
    return this.context;
  }

  @Override
  public void shutdown() {
    this.executor.shutdown();
  }

  private R process(Tuple<T, Result<Actor<T,R>>> tuple) { return context.getBehavior().process(tuple._1, tuple._2);}

  public synchronized CompletableFuture<Result<R>> tell(final T message, Result<Actor<T,R>> sender) {
    return CompletableFuture.supplyAsync(() -> {
      try {
        //context.getBehavior().process(message, sender);
        return Result.success(context.getBehavior().process(message, sender));
      } catch (RejectedExecutionException e) {
        /*
         * This is probably normal and means all pending tasks
         * were canceled because the actor was stopped.
         */
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return null;
    }, executor).exceptionally(ex -> Result.failure(new RuntimeException(ex)));
  }
}
