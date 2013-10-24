package com.silver.seed.pattern.factory;

/**
 *
 * @author Liaojian
 */
public interface VariableFactory<T, V> {
    public T create(V variable);
}
