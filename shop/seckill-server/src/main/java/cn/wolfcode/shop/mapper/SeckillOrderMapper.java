package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.SeckillOrder;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SeckillOrderMapper {
    @Select("SELECT * FROM t_seckill_order WHERE user_id=#{userId} AND good_id=#{goodId}")
    SeckillOrder get(@Param("goodId") Long goodId, @Param("userId") Long userId);

    @Insert("INSERT t_seckill_order(user_id,order_no,good_id) VALUES (#{userId},#{orderNo},#{goodId})")
    void insert(SeckillOrder seckillOrder);
}
