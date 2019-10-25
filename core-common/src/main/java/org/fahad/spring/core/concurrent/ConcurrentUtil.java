package org.fahad.spring.core.concurrent;

import com.google.common.util.concurrent.Striped;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentUtil {
    public static final Striped<Lock> sharedStrippedLocks = Striped.lock(500);
    public static final int DEFAULT_LOCK_TIMEOUT_SECONDS = 60;

    private ConcurrentUtil(){

    }

    public static <KeyType, ValueType, FactoryParamType> ValueType getOrCreate(Striped<Lock> creationLocks, ConcurrentMap<KeyType, ValueType> map,
                                                                               KeyType key, Factory<KeyType, ValueType, FactoryParamType> factory,
                                                                               FactoryParamType factoryParam) {
        return getOrCreate(creationLocks, map, key, factory, factoryParam, DEFAULT_LOCK_TIMEOUT_SECONDS, TimeUnit.SECONDS);
    }

    public static <KeyType, ValueType, FactoryParamType> ValueType getOrCreate(Striped<Lock> creationLocks, ConcurrentMap<KeyType, ValueType> map,
                                                                               KeyType key, Factory<KeyType, ValueType, FactoryParamType> factory,
                                                                               FactoryParamType factoryParam, long time, TimeUnit timeUnit) {
        ValueType value = map.get(key);
        if (value == null) {
            boolean acquired = false;
            Lock lock = creationLocks.get(key);
            try {
                acquired = tryLockNoException(lock, time, timeUnit);
                if (!acquired) {
                    // try again, just in case
                    value = map.get(key);
                    if (value != null) {
                        return value;
                    } else {
                        throw new RuntimeException("Failed to create resource using factory");
                    }
                }
                value = map.get(key);
                if (value == null) {
                    value = factory.create(key, factoryParam);
                    if (value != null) {
                        map.put(key, value);
                    }
                }
            } finally {
                if (acquired) {
                    lock.unlock();
                }
            }
        }
        return value;
    }



    public static boolean tryLockNoException(Lock lock, long duration, TimeUnit timeUnit) {
        try {
            return lock.tryLock(duration, timeUnit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static void acquireLockAndExecute(Runnable runnable, ReentrantLock lock) {
        lock.lock();
        try {
            runnable.run();
        } finally {
            lock.unlock();
        }
    }

    public static interface Factory<K, V, P> {
        V create(K key, P param);
    }

}
