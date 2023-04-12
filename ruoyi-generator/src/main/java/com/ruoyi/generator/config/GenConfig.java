package com.ruoyi.generator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 读取代码生成相关配置
 * 
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "gen")
@PropertySource(value = { "classpath:generator.yml" })
public class GenConfig
{
    /** 作者 */
    public static String author;

    /** 生成包路径 */
    public static String packageName;

    /** 自动去除表前缀，默认是false */
    public static boolean autoRemovePre;

    /** 表前缀(类名不会包含表前缀) */
    public static String tablePrefix;

    /** 生成时后端模块名称 **/
    public static String backModuleName;

    /** 生成时前端模块名称 **/
    public static String  frontModuleName;

    /** 是否生成swagger注解 **/
    public static Boolean swagger;
    
    /** 是否使用lombok注解 **/
    public static Boolean lombok;

    /** 是否生成导入功能 **/
    public static Boolean imported;

    /**
     * 默认生成方式
     **/
    public static String genType;
    
     /**
     * 路径生成时是否覆盖已存在文件
     **/
    public static Boolean override;

    public static Boolean getOverride() {
        return override;
    }

    @Value("${override}")
    public void setOverride(Boolean override) {
        GenConfig.override = override;
    }

    public static String getGenType() {
        return genType;
    }

    @Value("${genType}")
    public void setGenType(String genType) {
        GenConfig.genType = genType;
    }
    
    public static Boolean getImported() {
        return imported;
    }

    @Value("${imported}")
    public  void setImported(Boolean imported) {
        GenConfig.imported = imported;
    }

    public static Boolean getSwagger() {
        return swagger;
    }

    public static Boolean getLombok() {
        return lombok;
    }
    @Value("${lombok}")
    public void setLombok(Boolean lombok) {
        GenConfig.lombok = lombok;
    }

    @Value("${swagger}")
    public void setSwagger(Boolean swagger) {
        GenConfig.swagger = swagger;
    }

    public static String getFrontModuleName() {
        return frontModuleName;
    }
    @Value("${frontModuleName}")
    public  void setFrontModuleName(String frontModuleName) {
        GenConfig.frontModuleName = frontModuleName;
    }
    public static String getBackModuleName() {
        return backModuleName;
    }
    @Value("${backModuleName}")
    public  void setBackModuleName(String backModuleName) {
        GenConfig.backModuleName = backModuleName;
    }

    public static String getAuthor()
    {
        return author;
    }

    @Value("${author}")
    public void setAuthor(String author)
    {
        GenConfig.author = author;
    }

    public static String getPackageName()
    {
        return packageName;
    }

    @Value("${packageName}")
    public void setPackageName(String packageName)
    {
        GenConfig.packageName = packageName;
    }

    public static boolean getAutoRemovePre()
    {
        return autoRemovePre;
    }

    @Value("${autoRemovePre}")
    public void setAutoRemovePre(boolean autoRemovePre)
    {
        GenConfig.autoRemovePre = autoRemovePre;
    }

    public static String getTablePrefix()
    {
        return tablePrefix;
    }

    @Value("${tablePrefix}")
    public void setTablePrefix(String tablePrefix)
    {
        GenConfig.tablePrefix = tablePrefix;
    }
}
