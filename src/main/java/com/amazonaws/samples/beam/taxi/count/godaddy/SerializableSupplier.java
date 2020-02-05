package com.amazonaws.samples.beam.taxi.count.godaddy;

import com.google.common.base.Supplier;

import java.io.Serializable;

/**
 * This can be used to get through lambda serialization of the {@link Supplier}.
 */
@FunctionalInterface
public interface SerializableSupplier<T> extends Supplier<T>, Serializable {

}
