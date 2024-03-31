package com.zzw.iCache.autoconfigure.Test;

import com.zzw.iCache.autoconfigure.Test.Entity.ProductInfo;
import com.zzw.iCache.autoconfigure.annocation.iCache;
import com.zzw.iCache.core.Cache.Cache;
import org.springframework.stereotype.Component;

@Component
public class ProductInfoHolder {
    @iCache("productCache")
    Cache<ProductInfo> productInfoCache;

    public void put(String skuSn, ProductInfo productInfo) {
        productInfoCache.put(skuSn, productInfo);
    }

    public ProductInfo get(String skuSn) {
        return productInfoCache.get(skuSn);
    }
}
