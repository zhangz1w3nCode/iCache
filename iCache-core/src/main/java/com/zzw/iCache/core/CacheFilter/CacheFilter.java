package com.zzw.iCache.core.CacheFilter;

import com.zzw.iCache.core.RealCache.RealCache;
import com.zzw.iCache.core.RealCache.valueWrapper.ValueWrapper;

/**
 * 缓存过滤器
 * <p> 它可以扩展缓存中的功能，例如我们的监听器功能
 * <pre> 值得注意的是，千万别在拦截的方法中调用相同的方法，可能会导致死循环，如
 *     public V get(Cache<K, V>  filterChain, Cache<K, V>  realCache, K key){
 *          // 这样会导致死循环
 *          realCache.get("k1");
 *         return filterChain.get(key);
 *     };
 * 如必须要这么做
 * 1、请确保有条件的调用
 *     public V get(Cache<K, V>  filterChain, Cache<K, V>  realCache, K key){
 *          // 也需要确保条件不能每次调用都满足
 *          if(condition){
 *              realCache.get("k1");
 *          }
 *         return filterChain.get(key);
 *     };
 * 2、使用 filterChain来调用，但你的操作可能会逃离缓存的执行过程，导致一些功能无法使用
 *     public V get(Cache<K, V>  filterChain, Cache<K, V>  realCache, K key){
 *          // 这样就不会导致死循环，但是你做的操作，无法被filter,listener拦截
 *         filterChain.get("k1");
 *         return filterChain.get(key);
 *     };
 * </pre>
 * @author qiaolin
 * @version $Id:  CacheListener.java,v 0.1 2020年06月03日 17:25 $Exp
 */
public interface CacheFilter<V> {

    /**
     * 拦截缓存get操作
     *
     * @param filterChain 拦截器链
     * @param realCache   真实的缓存，如需操作缓存，需要使用这个
     * @param key         当前需要获取的key
     * @return 缓存中key对应的值
     */
    default ValueWrapper<V> get(RealCache<V> filterChain, RealCache<V>  realCache, String key){
        return filterChain.get(key);
    };


    /**
     * 拦截put操作
     *
     * @param filterChain 拦截器链
     * @param realCache   真实的缓存，如需操作缓存，需要使用这个
     * @param key         put的键
     * @param value       put的值
     * @return
     */
    default void put(RealCache<V>  filterChain, RealCache<V>  realCache, String key, V value) {
        filterChain.put(key, value);
    }

}
