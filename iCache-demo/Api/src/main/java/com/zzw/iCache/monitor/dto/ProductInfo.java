package com.zzw.iCache.monitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: zhanyang
 * @Data:2024/3/25 15:02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo implements Serializable {
    private String skuSn;
    private String productName;
    private String productDesc;
}
