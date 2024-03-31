package com.zzw.iCache.demo.Provider.serviceImp;

import com.zzw.iCache.autoconfigure.annocation.iCache;
import com.zzw.iCache.core.Cache.Cache;
import com.zzw.iCache.demo.Api.Entity.ProductInfo;
import com.zzw.iCache.demo.Api.TestFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
@Service(version = "1.0.0")
@Slf4j
public class testCacheImp implements TestFacade {

    @iCache("productCache")
    Cache<ProductInfo> productInfoCache;

    @Override
    public Object put(ProductInfo productInfo) {
        //使用skuSn加今天的时间的年月日作为key
        //获取当前年月日作为字符串
        //获取一个uuid作为key：
        String uuid = UUID.randomUUID().toString();

        String key = productInfo.getSkuSn() + new SimpleDateFormat("yyyyMMdd").format(new Date())+uuid;
        productInfoCache.put(key, productInfo);
        //把key和value打印出来，方便查看
        log.info("key:{},value:{}", key, productInfo);
        return key;
    }

    @Override
    public Object get(String key) {
        ProductInfo productInfo = productInfoCache.get(key);
        log.info("key:{},value:{}", key, productInfo);
        return productInfo;
    }

//    @Override
//    public List<ValueWrapper<ProductInfo>> getAllValues() {
//        List<ValueWrapper<ProductInfo>> valueList = productInfoCache.getValues();
//        log.info("valueList:{}",valueList);
//        return valueList;
//    }
}
