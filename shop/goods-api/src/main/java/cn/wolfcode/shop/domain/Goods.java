package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class Goods implements Serializable {
    private Long id;
    private String goodName;
    private String goodTitle;
    private String goodImg;
    private String goodDetail;
    private BigDecimal goodPrice;
    private Integer goodStock;
}
