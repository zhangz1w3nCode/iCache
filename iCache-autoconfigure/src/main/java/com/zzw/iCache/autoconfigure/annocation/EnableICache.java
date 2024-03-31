package com.zzw.iCache.autoconfigure.annocation;

import com.zzw.iCache.autoconfigure.config.RealCacheAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开始iCache缓存
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(RealCacheAutoConfiguration.class)
public @interface EnableICache {
}
