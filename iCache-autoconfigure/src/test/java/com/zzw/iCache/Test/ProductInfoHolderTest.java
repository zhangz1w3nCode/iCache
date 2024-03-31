package com.zzw.iCache.Test;

import com.zzw.iCache.autoconfigure.Application;
import com.zzw.iCache.autoconfigure.Test.Entity.ProductInfo;
import com.zzw.iCache.autoconfigure.Test.ProductInfoHolder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {Application.class})
public class ProductInfoHolderTest {

    @Autowired
    ProductInfoHolder productInfoHolder;

    @Test
    public void testPut() {
        productInfoHolder.put("1",new ProductInfo("1","iphone11","苹果手机"));
        ProductInfo productInfo = productInfoHolder.get("1");
        System.out.println(productInfo);
    }
}
