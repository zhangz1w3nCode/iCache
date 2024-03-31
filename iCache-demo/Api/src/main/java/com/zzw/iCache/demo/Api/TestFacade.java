package com.zzw.iCache.demo.Api;

import com.zzw.iCache.demo.Api.Entity.ProductInfo;

public interface TestFacade {
    public Object put(ProductInfo productInfo);
    public Object get(String key);

    //public List<ValueWrapper<ProductInfo>> getAllValues();
}
