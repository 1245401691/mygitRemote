package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface OrderInfoMapper {
    @Insert("INSERT t_order_info(order_no,user_id,good_id,good_img,delivery_addr_id," +
            "good_name,good_count,good_price,seckill_price,status,create_date,pay_date) " +
            "VALUES(#{orderNo},#{userId},#{goodId},#{goodImg}" +
            ",#{deliveryAddrId},#{goodName},#{goodCount},#{goodPrice},#{seckillPrice},#{status}" +
            ",#{createDate},#{payDate}) ")
    void insert(OrderInfo orderInfo);

    @Select("SELECT * FROM t_order_info WHERE user_id=#{userId} AND good_id=#{goodId}")
    OrderInfo get(@Param("userId") Long userId, @Param("goodId") Long goodId);

}
