package cn.wolfcode.shop.Controller;

import cn.wolfcode.shop.domain.Goods;
import cn.wolfcode.shop.domain.GoodsVo;
import cn.wolfcode.shop.domain.User;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.SeckillGoodsKeyPerfix;
import cn.wolfcode.shop.service.ISeckillService;
import cn.wolfcode.shop.vo.LoginVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.StringWriter;
import java.util.*;

@Controller
@RequestMapping("/goods")
public class SeckillGoodsController {
    @Reference
    private ISeckillService seckillService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private Configuration configuration;
    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String seckillToGoods(Model model){
        //需要goods秒杀商品详情
        //查询出秒杀商品的id及其库存信息，然后遍历将只存放map集合中
        String html = redisService.get(SeckillGoodsKeyPerfix.SECKILL_GOOD_PAGE, "", String.class);
        if(html!=null){
            return html;
        }
        try {
            Map<Long, Object> goodsVod = redisService.hgetAll(SeckillGoodsKeyPerfix.SECKILL_GOODS_LIST,GoodsVo.class);
            if(goodsVod!=null){
                List<GoodsVo> goodsVos = new ArrayList<>();
                for (Long goodId : goodsVod.keySet()) {
                    GoodsVo goodsVo = (GoodsVo) goodsVod.get(goodId);
                    goodsVos.add(goodsVo);
                }
                model.addAttribute("goodsList",goodsVos);
                html = getHtml(model,"goods_list");
                redisService.set(SeckillGoodsKeyPerfix.SECKILL_GOOD_PAGE,"",html);
                System.out.println("从缓存中去取");
            }else{
                List<GoodsVo> goodsVos = seckillService.selectAll();
                model.addAttribute("goodsList",goodsVos);
                html = getHtml(model,"goods_list");
                redisService.set(SeckillGoodsKeyPerfix.SECKILL_GOOD_PAGE,"",html);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return html;
    }

    private String getHtml(Model model, String goods_list) throws Exception {
        StringWriter sw = new StringWriter();
        Template template = configuration.getTemplate(goods_list+".ftl");
        template.process(model,sw);
        return sw.toString();
    }


    @RequestMapping("/to_detail")
    public String seckillToDetail(Model model, Long goodsId, User user){
        GoodsVo goodVo = redisService.hget(SeckillGoodsKeyPerfix.SECKILL_GOODS_LIST, goodsId + "", GoodsVo.class);
        if(goodVo!=null){
            model.addAttribute("good",goodVo);
            System.out.println("缓存中去取");
        }else{
            System.out.println("查询数据库");
            GoodsVo goodsVo = seckillService.selectById(goodsId);
            model.addAttribute("good",goodsVo);
        }
        model.addAttribute("user",user);
        return "goods_detail";
    }
}
