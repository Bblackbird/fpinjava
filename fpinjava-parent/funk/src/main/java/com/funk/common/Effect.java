package com.funk.common;

public interface Effect<T> {
  void apply(T t);
}