package shuaicj.example.mybatis.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Spring boot application.
 *
 * @author shuaicj 2019/07/19
 */
@SpringBootApplication
@EnableCaching
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
}
