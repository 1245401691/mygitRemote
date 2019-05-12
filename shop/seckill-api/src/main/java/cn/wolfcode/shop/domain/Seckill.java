package cn.wolfcode.shop.domain;



import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Seckill implements Serializable {
    private Long id;
    private Long goodId;
    private BigDecimal seckillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
