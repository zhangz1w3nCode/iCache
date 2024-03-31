package com.zzw.iCache.autoconfigure.Test.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zhanyang
 * @Data:2024/3/25 15:02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private String skuSn;
    private String productName;
    private String productDesc;
}
