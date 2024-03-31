//package com.zzw.autoconfigure.Test;
//
//import com.zzw.iCache.core.CacheObject.Cache;
//import com.zzw.autoconfigure.Test.Entity.ProductInfo;
//import com.zzw.autoconfigure.annocation.iCache;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@Slf4j
//public class TestController {
//
//    @iCache(name = "productInfoCache")
//    Cache<ProductInfo> productInfoCache;
//
//    @PostMapping("/put")
//    public Object put(@RequestParam String key,@RequestBody ProductInfo productInfo) {
//        productInfoCache.put(key, productInfo);
//        return "success";
//    }
//
//    @PostMapping("/get")
//    public Object put(@RequestParam String key) {
//        return productInfoCache.get(key);
//    }
//
//    @PostMapping("/test")
//    public Object put() {
//        return "6";
//    }
//
//
//}
