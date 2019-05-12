package cn.wolfcode.shop.mq;

import cn.wolfcode.shop.domain.MQConfig;
import cn.wolfcode.shop.domain.OrderMessage;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

/**
 * 该类用来website来发送消息
 */
@Component
public class MQSender {
    @Autowired
    private JmsTemplate jmsTemplate;
    public void sendOrderMsg(OrderMessage message) throws JMSException {
        ObjectMessage objectMessage = new ActiveMQObjectMessage();
        objectMessage.setObject(message);
        jmsTemplate.convertAndSend(MQConfig.ORDER_DESTINATION,objectMessage);
    }
}
