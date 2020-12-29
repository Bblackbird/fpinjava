package com.funk.actors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DaemonThreadFactory implements ThreadFactory {

  private final String prefix;
  private final AtomicInteger threadCount = new AtomicInteger(0);

  public DaemonThreadFactory(String prefix) {
    this.prefix = prefix + "-";
  }

  @Override
  public Thread newThread(Runnable runnableTask) {
    Thread thread = new Thread(runnableTask, prefix + threadCount.getAndIncrement());
    //Thread thread = Executors.defaultThreadFactory().newThread(runnableTask);
    thread.setDaemon(true);
    return thread;
  }
}
