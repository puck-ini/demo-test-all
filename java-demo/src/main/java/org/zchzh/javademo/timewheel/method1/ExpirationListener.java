package org.zchzh.javademo.timewheel.method1;

/**
 * @author zengchzh
 * @date 2021/9/23
 */
public interface ExpirationListener<E> {

    /**
     * Invoking when a expired event occurs.
     *
     * @param expiredObject
     */
    void expired(E expiredObject);
}
