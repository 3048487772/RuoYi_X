package com.ruoyi.generator.util;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.GenConstants;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.generator.config.GenConfig;
import com.ruoyi.generator.domain.GenTable;
import com.ruoyi.generator.domain.GenTableColumn;
import org.apache.commons.lang3.RegExUtils;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * 代码生成器 工具类
 *
 * @author ruoyi
 */
public class GenUtils {
    /**
     * 初始化表信息
     */
    public static void initTable(GenTable genTable, String operName) {
        genTable.setClassName(convertClassName(genTable.getTableName()));
        genTable.setPackageName(GenConfig.getPackageName());
        genTable.setModuleName(getModuleName(GenConfig.getPackageName()));
        genTable.setBusinessName(StringUtils.uncapitalize(genTable.getClassName()));
        genTable.setFunctionName(replaceText(genTable.getTableComment()));
        try {
            genTable.setFunctionAuthor(new String(GenConfig.getAuthor().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        genTable.setCreateBy(operName);
        genTable.setGenType(GenConfig.getGenType());
        genTable.setGenPath(GenConfig.getGenPath());
        genTable.addParams("mybatisPlus", GenConfig.getMybatisPlus());
        genTable.addParams("lombok", GenConfig.getLombok());
        genTable.addParams("swagger", GenConfig.getSwagger());
        genTable.addParams("imported", GenConfig.getImported());
        genTable.addParams("backModuleName", GenConfig.getBackModuleName());
        genTable.addParams("frontModuleName", GenConfig.getFrontModuleName());
        genTable.addParams("menuIcon", "#");
        genTable.addParams("override", GenConfig.getOverride());
        String options = JSON.toJSONString(genTable.getParams());
        genTable.setOptions(options);
    }

    /**
     * 初始化列属性字段
     */
    public static void initColumnField(GenTableColumn column, GenTable table) {
        String dataType = getDbType(column.getColumnType());
        String columnName = column.getColumnName();
        column.setTableId(table.getTableId());
        column.setCreateBy(table.getCreateBy());
        // 设置java字段名
        column.setJavaField(StringUtils.toCamelCase(columnName));
        // 设置默认类型
        column.setJavaType(GenConstants.TYPE_STRING);
        column.setQueryType(GenConstants.QUERY_EQ);

        //列表排除
        String[] unListType = {"text"};
        String[] unListComment = {"描述", "内容", "详情", "id","ID"};
        String[] unListColumnName = {"id"};
        //查询字段
        String[] queryComment = {"名称", "类型", "号"};
        //导出排除
        String[] unExportComment = {"文件"};
        String[] unImportComment = {"文件"};
        //模糊查询字段
        String[] likeName = {"name", "phone"};
        String[] likeComment = {"名称", "号"};
        //多选框组件
        String[] radioName = {"status"};
        //下拉框控件对应
        String[] selectName = {"type", "sex", "category"};
        //图片上传控件对应
        String[] imageUploadName = {"image", "logo", "photo"};
        String[] imageUploadComment = {"图片", "头像", "照片"};
        //文件上传控件对应
        String[] fileUploadName = {"file"};
        //富文本控件对应
        String[] editorName = {"content"};


        if (arraysContains(GenConstants.COLUMNTYPE_STR, dataType) || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType)) {
            // 字符串长度超过500设置为文本域
            Integer columnLength = getColumnLength(column.getColumnType());
            String htmlType = columnLength >= 500 || arraysContains(GenConstants.COLUMNTYPE_TEXT, dataType) ? GenConstants.HTML_TEXTAREA : GenConstants.HTML_INPUT;
            column.setHtmlType(htmlType);
        } else if (arraysContains(GenConstants.COLUMNTYPE_TIME, dataType)) {
            column.setJavaType(GenConstants.TYPE_DATE);
            if (dataType.equals("date")) {
                column.setHtmlType(GenConstants.HTML_DATE);
            } else {
                column.setHtmlType(GenConstants.HTML_DATETIME);
            } 
        } else if (arraysContains(GenConstants.COLUMNTYPE_NUMBER, dataType)) {
            column.setHtmlType(GenConstants.HTML_INPUT);

            // 如果是浮点型
            String[] str = StringUtils.split(StringUtils.substringBetween(column.getColumnType(), "(", ")"), ",");
            if (str != null && str.length == 2 && Integer.parseInt(str[1]) > 0) {
                column.setJavaType(GenConstants.TYPE_DOUBLE);
            }
            // 如果是整形
            else if (str != null && str.length == 1 && Integer.parseInt(str[0]) <= 10) {
                column.setJavaType(GenConstants.TYPE_INTEGER);
            }
            // 长整形
            else {
                column.setJavaType(GenConstants.TYPE_LONG);
            }
        } else if (arraysContains(GenConstants.COLUMNTYPE_BIT, dataType)) {
            column.setJavaType(GenConstants.TYPE_BOOLEAN);
        }


        // 插入字段（默认所有字段都需要插入）
        column.setIsInsert(GenConstants.REQUIRE);

        // 编辑字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_EDIT, columnName) && !column.isPk()) {
            column.setIsEdit(GenConstants.REQUIRE);
        }
        // 列表字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_LIST, columnName) && !column.isPk()) {
            if (!StrUtil.containsAny(column.getColumnType(), unListType) && !StrUtil.containsAny(column.getColumnComment(), unListComment) && !StrUtil.containsAny(column.getColumnName(), unListColumnName)) {
                column.setIsList(GenConstants.REQUIRE);
            }
        }
        // 查询字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            if (StringUtils.containsAny(column.getColumnComment(), queryComment)) {
                column.setIsQuery(GenConstants.REQUIRE);
            }
        }
        // 导出字段
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            if (!StringUtils.containsAny(column.getColumnComment(), unExportComment)) {
                column.addOption("export", GenConstants.REQUIRE);
            } else {
                column.addOption("export", GenConstants.UNREQUIRE);
            }
        } else {
            column.addOption("export", GenConstants.UNREQUIRE);
        }
        if (!arraysContains(GenConstants.COLUMNNAME_NOT_QUERY, columnName) && !column.isPk()) {
            if (!StringUtils.containsAny(column.getColumnComment(), unImportComment)) {
                column.addOption("import", GenConstants.REQUIRE);
            } else {
                column.addOption("import", GenConstants.UNREQUIRE);
            }
        } else {
            column.addOption("import", GenConstants.UNREQUIRE);
        }

        // 查询字段类型
        if (StrUtil.containsAny(columnName, likeName) || StrUtil.containsAny(column.getColumnComment(), likeComment)) {
            column.setQueryType(GenConstants.QUERY_LIKE);
        }
        // 状态字段设置单选框
        if (StrUtil.containsAny(columnName, radioName)) {
            column.setHtmlType(GenConstants.HTML_RADIO);
        }
        // 类型&性别字段设置下拉框
        else if (StrUtil.containsAny(columnName, selectName)) {
            column.setHtmlType(GenConstants.HTML_SELECT);
        }
        // 图片字段设置图片上传控件
        else if (StrUtil.containsAny(columnName, imageUploadName) || StrUtil.containsAny(column.getColumnComment(), imageUploadComment)) {
            column.setHtmlType(GenConstants.HTML_IMAGE_UPLOAD);
        }
        // 文件字段设置文件上传控件
        else if (StrUtil.containsAny(columnName, fileUploadName)) {
            column.setHtmlType(GenConstants.HTML_FILE_UPLOAD);
        }
        // 内容字段设置富文本控件
        else if (StrUtil.containsAny(columnName, editorName)) {
            column.setHtmlType(GenConstants.HTML_EDITOR);
        }
    }

    /**
     * 校验数组是否包含指定值
     *
     * @param arr         数组
     * @param targetValue 值
     * @return 是否包含
     */
    public static boolean arraysContains(String[] arr, String targetValue) {
        return Arrays.asList(arr).contains(targetValue);
    }

    /**
     * 获取模块名
     *
     * @param packageName 包名
     * @return 模块名
     */
    public static String getModuleName(String packageName) {
        int lastIndex = packageName.lastIndexOf(".");
        int nameLength = packageName.length();
        return StringUtils.substring(packageName, lastIndex + 1, nameLength);
    }

    /**
     * 获取业务名
     *
     * @param tableName 表名
     * @return 业务名
     */
    public static String getBusinessName(String tableName) {
        int lastIndex = tableName.lastIndexOf("_");
        int nameLength = tableName.length();
        return StringUtils.substring(tableName, lastIndex + 1, nameLength);
    }

    /**
     * 表名转换成Java类名
     *
     * @param tableName 表名称
     * @return 类名
     */
    public static String convertClassName(String tableName) {
        boolean autoRemovePre = GenConfig.getAutoRemovePre();
        String tablePrefix = GenConfig.getTablePrefix();
        if (autoRemovePre && StringUtils.isNotEmpty(tablePrefix)) {
            String[] searchList = StringUtils.split(tablePrefix, ",");
            tableName = replaceFirst(tableName, searchList);
        }
        return StringUtils.convertToCamelCase(tableName);
    }

    /**
     * 批量替换前缀
     *
     * @param replacementm 替换值
     * @param searchList   替换列表
     * @return
     */
    public static String replaceFirst(String replacementm, String[] searchList) {
        String text = replacementm;
        for (String searchString : searchList) {
            if (replacementm.startsWith(searchString)) {
                text = replacementm.replaceFirst(searchString, "");
                break;
            }
        }
        return text;
    }

    /**
     * 关键字替换
     *
     * @param text 需要被替换的名字
     * @return 替换后的名字
     */
    public static String replaceText(String text) {
        return RegExUtils.replaceAll(text, "(?:表|若依)", "");
    }

    /**
     * 获取数据库类型字段
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static String getDbType(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            return StringUtils.substringBefore(columnType, "(");
        } else {
            return columnType;
        }
    }

    /**
     * 获取字段长度
     *
     * @param columnType 列类型
     * @return 截取后的列类型
     */
    public static Integer getColumnLength(String columnType) {
        if (StringUtils.indexOf(columnType, "(") > 0) {
            String length = StringUtils.substringBetween(columnType, "(", ")");
            return Integer.valueOf(length);
        } else {
            return 0;
        }
    }
}
