package com.exmek.commons.ref;

public class Holder<T> implements Ref<T> {

  private T referent;

  public Holder() {
  }

  public Holder(T referent) {
    this.referent = referent;
  }

  public static <T> Holder<T> of(T referent) {
    return new Holder<>(referent);
  }

  @Override
  public T get() {
    return referent;
  }

  @Override
  public void set(T referent) {
    this.referent = referent;
  }
}
