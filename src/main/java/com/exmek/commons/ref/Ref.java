package com.exmek.commons.ref;

public interface Ref<T> {

    T get();

    void set(T referent);
}