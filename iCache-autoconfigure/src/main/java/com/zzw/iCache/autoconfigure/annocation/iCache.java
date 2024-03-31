package com.zzw.iCache.autoconfigure.annocation;

import com.zzw.iCache.core.CacheConstant.CacheConstant;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 缓存注解，可以在使用注解开启一个缓存
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface iCache {

    @AliasFor(annotation = iCache.class, value = "name")
    String value() default "";

    @AliasFor(annotation = iCache.class, value = "value")
    String name() default "";

    String type() default CacheConstant.CACHE_TYPE_CAFFEINE;

    String[] filter() default {};

    String[] listener() default {};
}
