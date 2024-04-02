package com.zzw.iCache.monitor;

import com.zzw.iCache.monitor.dto.ProductInfo;

public interface TestFacade {
    public Object put(ProductInfo productInfo);
    public Object get(String key);

    //public List<ValueWrapper<ProductInfo>> getAllValues();
}
