package com.exmek.commons.function;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface HexaFunction<T1, T2, T3, T4, T5, T6, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t1 the first function argument
     * @param t2 the second function argument
     * @param t3 the third function argument
     * @param t4 the fourth function argument
     * @param t5 the fifth function argument
     * @param t6 the sixth function argument
     * @return the function result
     */
    R apply(T1 t1, T2 t2, T3 t3, T4 t4, T5 t5, T6 t6);

    /**
     * Returns a composed function that first applies this function to its input, and then applies the {@code after}
     * function to the result. If evaluation of either function throws an exception, it is relayed to the caller of the
     * composed function.
     *
     * @param <W> the type of output of the {@code after} function, and of the composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then applies the {@code after} function
     * @throws NullPointerException if after is null
     */
    default <W> HexaFunction<T1,T2, T3, T4, T5, T6, W> andThen(final Function<? super R, ? extends W> after) {
        Objects.requireNonNull(after);
        return (final T1 t1, final T2 t2, final T3 t3, T4 t4, T5 t5, T6 t6) -> after.apply(apply(t1, t2, t3, t4, t5, t6));
    }
}