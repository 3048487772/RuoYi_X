package com.ruoyi;

import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@Slf4j
public class RuoYiApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        log.info("===============================================");
        log.info("|项目启动成功");
        log.info("|端口[{}]", SpringUtil.getProperty("server.port"));
        log.info("|文档地址:[http://localhost:{}/doc.html]", SpringUtil.getProperty("server.port"));
        log.info("===============================================");

    }
}
