package cn.wolfcode.shop.mapper;

import cn.wolfcode.shop.domain.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;
import java.util.Set;

@Mapper
public interface GoodsMapper {

    @SelectProvider(type =GoodSelectProvider.class ,method ="queryByIds")
    List<Goods> selectByIds(@Param("ids") List<Long> ids);

    @Select("SELECT * FROM t_goods where id = #{goodsId}")
    Goods get(Long goodsId);

    class GoodSelectProvider{
        public String queryByIds(@Param("ids")List<Long> ids){
            StringBuffer sqlStr = new StringBuffer("select* from t_goods where id in");
            for(int i=0;i<ids.size();i++){
                if(i==0){
                    sqlStr.append("("+ids.get(i));
                }else if(i==ids.size()-1){
                    sqlStr.append(","+ids.get(i)+")");
                }else{
                    sqlStr.append(","+ids.get(i));
                }
            }
            return sqlStr.toString();
        }
    }
}
