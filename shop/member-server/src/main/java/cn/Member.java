package cn;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableDubbo
@PropertySource({"classpath:zookeeper.properties","classpath:redis.properties"})
public class Member {
    public static void main(String[] args) {
        SpringApplication.run(Member.class,args);
    }
}
