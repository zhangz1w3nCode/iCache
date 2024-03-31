package com.zzw.iCache.demo.Consumer;

import com.zzw.iCache.demo.Api.Entity.ProductInfo;
import com.zzw.iCache.demo.Api.TestFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class TestController {

    @DubboReference(version = "1.0.0")
    TestFacade testFacade;

    @PostMapping("/put")
    public Object put(@RequestBody ProductInfo productInfo) {
        return testFacade.put(productInfo);
    }

    @PostMapping("/get")
    public Object put(@RequestParam String key) {
        return testFacade.get(key);
    }

//    @PostMapping("/getValues")
//    public List<ValueWrapper<ProductInfo>> put() {
//        return testFacade.getAllValues();
//    }
//
//    @PostMapping("/size")
//    public String size() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
//        String res = "有" + testFacade.getAllValues().size() + "个缓存";
//        stopWatch.stop();
//        log.info("耗时：{}m", stopWatch.getTotalTimeMillis());
//
//        return res+"耗时："+stopWatch.getTotalTimeMillis()+ "ms";
//    }

}
