package net.gudenau.minecraft.datafixerapi.impl;

import com.google.common.collect.ImmutableMap;
import java.util.Map;

public final class Util{
    /**
     * Creates an unmodifiable map out of a varag array of key-value pairs.
     *
     * @param objects The flattened map
     * @param <T> A generic supertype
     * @param <K> The key type
     * @param <V> The value type
     * @return The new map
     */
    // The T generic is here to make other code cleaner.
    @SuppressWarnings("unchecked")
    public static <T, K, V> Map<K, V> createMap(T... objects){
        if(objects.length == 0){
            return Map.of();
        }
        if((objects.length & 1) != 0){
            throw new IllegalArgumentException("objects can not have an odd number of values");
        }
        if(objects.length == 2){
            // Java has a special case for this, it is likely to be in common use...
            return Map.of((K)objects[0], (V)objects[1]);
        }
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        for(int i = 0; i < objects.length; i += 2){
            builder.put((K)objects[i], (V)objects[i + 1]);
        }
        return builder.build();
    }
}
