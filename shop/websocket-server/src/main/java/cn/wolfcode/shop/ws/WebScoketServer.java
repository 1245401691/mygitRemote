package cn.wolfcode.shop.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/websocket/{uuid}")
@Component
public class WebScoketServer {
    private Session session;
    public static ConcurrentHashMap<String,WebScoketServer> clientMap = new ConcurrentHashMap<String,WebScoketServer>();
    @OnOpen
    public void onOpen(Session session, @PathParam( "uuid") String uuid){
        this.session = session;
        System.out.println("开启。。。。。");
        clientMap.put(uuid,this);
    }
    @OnClose
    public void onClose(@PathParam( "uuid") String uuid){
        System.out.println("关闭");
        clientMap.remove(uuid);
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("关闭");
        System.out.println(message);

    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

/*    //给秒杀到某个商品的用户发送订单号
    public static void sendMessage(String uuid, CodeMsg codeMsg) throws IOException {
        System.out.println("发送消息uuid:"+uuid);
        WebScoketServer webScoketServer = clientMap.get(uuid);
        System.out.println(webScoketServer);
        if(webScoketServer!=null){
            webScoketServer.session.getBasicRemote().sendText(JSON.toJSONString(codeMsg));
        }
    }*/
}