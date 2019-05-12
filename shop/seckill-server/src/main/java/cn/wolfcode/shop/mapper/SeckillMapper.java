package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.GoodsVo;
import cn.wolfcode.shop.domain.Seckill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SeckillMapper {

    @Select("SELECT * FROM t_seckill_goods")
    List<Seckill> selectAll();

    @Select("SELECT * FROM t_seckill_goods WHERE good_id=#{goodsId}")
    Seckill get(Long goodsId);

    @Update("UPDATE t_seckill_goods SET stock_count=stock_count-1 WHERE good_id=#{id} AND stock_count>0")
    void update(GoodsVo goodsVo);
}
