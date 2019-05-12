package cn.wolfcode.shop;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDubbo
@PropertySource("classpath:zookeeper.properties")
public class GoodsConfig {
    public static void main(String[] args) {
        SpringApplication.run(GoodsConfig.class,args);
    }
}
