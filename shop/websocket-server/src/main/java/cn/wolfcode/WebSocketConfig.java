package cn.wolfcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ImportAutoConfiguration(WebSocketConfig.class)
public class WebSocketConfig {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketConfig.class,args);
    }
}
