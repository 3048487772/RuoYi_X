package com.ruoyi;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.extra.spring.SpringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.io.File;
import java.net.URL;

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
        docUrl = URLUtil.normalize(docUrl, false, true);
        
        String profile = StrUtil.format("|配置文件：{}", SpringUtil.getProperty("spring.profiles.active"));
        
        String doc = StrUtil.format("|文档地址:[{}]", docUrl);
        
        ClassLoader classLoader = RuoYiApplication.class.getClassLoader();
        

        URL resource = classLoader.getResource("static/index.html");
        String htmlUrl = null;
        if (ObjectUtil.isNotNull(resource)) {
            File file = new File(resource.getFile());
            if (file.exists()) {
                htmlUrl = StrUtil.format("|网页地址:[http://localhost:{}/{}/index.html]", SpringUtil.getProperty("server.port"), SpringUtil.getProperty("server.servlet.context-path"));
                htmlUrl = URLUtil.normalize(htmlUrl, false, true);
            }
        }

        int max = NumberUtil.max(line.length(), success.length(), port.length(), doc.length(),StrUtil.length(htmlUrl));
        line = StrUtil.fillAfter(line, '=', max + 8);
        
        log.info(line);
        log.info(success);
        log.info(port);
        log.info(profile);
        log.info(doc);
        if (ObjectUtil.isNotEmpty(htmlUrl)) {
            log.info(htmlUrl);
        }
        log.info(line);
    }

}
