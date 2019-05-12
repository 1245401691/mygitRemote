package cn.wolfcode.shop.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SeckillOrder implements Serializable{
    private Long id;
    private String orderNo;
    private Long userId;
    private Long goodId;
}
