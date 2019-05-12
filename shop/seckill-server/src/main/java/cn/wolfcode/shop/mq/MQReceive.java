package cn.wolfcode.shop.mq;

import cn.wolfcode.shop.domain.MQConfig;
import cn.wolfcode.shop.domain.OrderInfo;
import cn.wolfcode.shop.domain.OrderMessage;
import cn.wolfcode.shop.result.CodeMsg;
import cn.wolfcode.shop.service.ISeckillService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.activemq.ScheduledMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.util.Date;

@Component
public class MQReceive {
    @Autowired
    private ISeckillService seckillService;
//    @Reference
//    private IWebScoketService webScoketService;
//    @Autowired
//    private JmsTemplate jmsTemplate;
    @JmsListener(destination = MQConfig.ORDER_DESTINATION)
    public void receiveOrderMsg(Message message) throws JMSException {
        try{
            if(message instanceof ObjectMessage){

                ObjectMessage objMsg = (ObjectMessage) message;
                OrderMessage orderMessage = (OrderMessage) objMsg.getObject();
                OrderInfo orderInfo = seckillService.seckillOrder(orderMessage.getGoodId(), orderMessage.getUserId());
//                webScoketService.sendMessage(orderMessage.getUuid(), CodeMsg.ORDER_SUCCESS.fillArgs(orderInfo.getOrderNo()));
            }
        }catch(Exception e){
            //需要在activemq.xml中broker节点中添加schedulerSupport="true"
            e.printStackTrace();
            message.clearProperties();
            message.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY,2*1000);
            System.out.println("Order.SEND"+new Date());
//            jmsTemplate.convertAndSend("ORDER.DLQ",message);
        }
    }
//    @JmsListener(destination = "ORDER.DLQ")
//    public void receiveOrderDLQ(Message message) throws Exception {
//        if(message instanceof ObjectMessage){
//            System.out.println("Order.DLQ"+new Date());
//            ObjectMessage objMsg = (ObjectMessage) message;
//            OrderMessage orderMessage = (OrderMessage) objMsg.getObject();
//            seckillService.syncStock(orderMessage.getGoodId());
//            //Thread.sleep(2000);
//            webScoketService.sendMessage(orderMessage.getUuid(),CodeMsg.SECKILL_ERROR);
//        }
//    }
}
