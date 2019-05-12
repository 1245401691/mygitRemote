package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class GoodsVo extends Goods{
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private BigDecimal seckillPrice;
}
