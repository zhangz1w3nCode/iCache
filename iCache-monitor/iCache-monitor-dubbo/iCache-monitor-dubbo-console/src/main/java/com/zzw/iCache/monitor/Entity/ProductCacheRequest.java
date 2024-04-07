package com.zzw.iCache.monitor.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCacheRequest {
    int areaId;
    String skuSn;
    Date tmAccount;
}
