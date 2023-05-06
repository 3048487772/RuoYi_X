package com.ruoyi;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.buf.UriUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class RuoYiApplication {
    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        log();
    }

    private static void log() {
        String line = "=";
        String success = "|项目启动成功";
        String port = StrUtil.format("|端口:[{}]", SpringUtil.getProperty("server.port"));
        String docUrl = StrUtil.format("http://localhost:{}/{}/doc.html", SpringUtil.getProperty("server.port"), SpringUtil.getProperty("server.servlet.context-path"));
        docUrl = URLUtil.normalize(docUrl,false,true);
        String profile = StrUtil.format("|配置文件：{}", SpringUtil.getProperty("spring.profiles.active"));
        String doc = StrUtil.format("|文档地址:[{}]", docUrl);
        int max = NumberUtil.max(line.length(), success.length(), port.length(), doc.length());
        line = StrUtil.fillAfter(line, '=', max+8);
       

        log.info(line);
        log.info(success);
        log.info(port);
        log.info(doc);
        log.info(profile);
        log.info(line);
    }

}
