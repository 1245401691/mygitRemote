package cn.wolfcode.shop.Controller;

import cn.wolfcode.shop.domain.GoodsVo;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.domain.OrderMessage;
import cn.wolfcode.shop.domain.User;
import cn.wolfcode.shop.mq.MQSender;
import cn.wolfcode.shop.redis.RedisService;
import cn.wolfcode.shop.redis.SeckillGoodsKeyPerfix;
import cn.wolfcode.shop.result.CodeMsg;
import cn.wolfcode.shop.result.Result;
import cn.wolfcode.shop.service.IOrderInfoService;
import cn.wolfcode.shop.service.ISeckillOrderService;
import cn.wolfcode.shop.service.ISeckillService;
import cn.wolfcode.shop.util.AccessLimit;
import cn.wolfcode.shop.util.VerifyCodeImgUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.zookeeper.KeeperException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Controller
@RequestMapping("/seckill")
public class SeckillOrderController {
    @Reference
    private ISeckillService seckillService;
    @Reference
    private ISeckillOrderService seckillOrderService;
    @Reference
    private IOrderInfoService orderInfoService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MQSender mqSender;
    private static Map<String,Boolean> isOver = new ConcurrentHashMap();


    /**
     * 该方法用来获取图片验证码
     * @param response
     * @param user
     */
    @RequestMapping("/getVerifyCode")
    @ResponseBody
    public void getVerifyCode(HttpServletResponse response,User user){
        String exp = VerifyCodeImgUtil.generateVerifyCode();
        Integer calc = VerifyCodeImgUtil.calc(exp);
        BufferedImage verifyCodeImg = VerifyCodeImgUtil.createVerifyCodeImg(exp);
        try {
            ImageIO.write(verifyCodeImg, "JPEG", response.getOutputStream());
            redisService.set(SeckillGoodsKeyPerfix.VERIFYCODE,user.getId()+"",calc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 该方法用来处理秒杀商品的
     * @param goodId
     * @param user
     * @return
     */
    @AccessLimit(maxCount = 5,seconds = 5,needLogin=true)
    @RequestMapping("/getPath")
    @ResponseBody
    public Object getPath(Long goodId,User user,Integer verifyCode){
        Integer redisVerifyCode = redisService.get(SeckillGoodsKeyPerfix.VERIFYCODE, user.getId() + "", Integer.class);
        if(redisVerifyCode==null ||redisVerifyCode!=verifyCode){
            return Result.error(CodeMsg.VERIFYCODE_INVALID);
        }
        String uuid = UUID.randomUUID().toString().replace("-", "");
        redisService.set(SeckillGoodsKeyPerfix.SECKILL_PATH,user.getId()+":"+goodId,uuid);
        return Result.success(uuid);
    }

    @RequestMapping("/{path}/do_seckill")
    @ResponseBody
    public Object seckillOrder(@PathVariable String path, Long goodId, User user){
        if(path==null ||!path.equals(redisService.get(SeckillGoodsKeyPerfix.SECKILL_PATH,user.getId()+":"+goodId,String.class))){
            return CodeMsg.SECKILL_NOT_START;
        }
//        判断用户是否登录
        if(user==null){
            return CodeMsg.LOGIN_FAIL;
        }
//      04.秒杀逻辑
        GoodsVo goodsVo = seckillService.selectById(goodId);
//        4.2判断是否已经到达了开始时间
        if(new Date().getTime()-goodsVo.getStartDate().getTime()<0){
            return CodeMsg.SECKILL_NOT_START;
        }
        if(isOver!=null){
            if(isOver.get(goodId.toString())!=null&&isOver.get(goodId.toString())){
                return CodeMsg.SECKILL_NOT_GOODS;
            }
        }
        //判断是否重复下单
        OrderInfo orderInfo1 = redisService.get(SeckillGoodsKeyPerfix.SECKILL_GOOD_ORDER, goodId + ":" + user.getId(), OrderInfo.class);
        if(orderInfo1!=null){
            return CodeMsg.SECKILL_ORDER_NOT_ONE;
        }

        if(redisService.decr(String.valueOf(goodId))<0){
            isOver.put(goodId.toString(),true);
            return CodeMsg.SECKILL_NOT_GOODS;
        }
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setGoodId(goodId);
        orderMessage.setUserId(user.getId());
        try {
            //调用mq来进行消息的传递
            mqSender.sendOrderMsg(orderMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return CodeMsg.SUCCESS;
    }

    @RequestMapping("/do_detail")
    @ResponseBody
    public Object getDetail(Long goodId,User user){
       if(user==null){
           return CodeMsg.LOGIN_FAIL;
       }
       GoodsVo goodsVo = seckillService.selectById(goodId);
       OrderInfo orderInfo = orderInfoService.get(user.getId(), goodId);
       Map<String,Object> map =new HashMap<>();
       map.put("goods",goodsVo);
       map.put("order",orderInfo);
       return map;
    }
}
