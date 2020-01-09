package com.standbyside.testapi.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


/**
 * 根据Class对象生成创建数据表的sql.
 */
public class TableSqlGenerator {

    private static final Map<String, String> property2SqlColumnMap = new HashMap<>();
    private static final boolean autoIncrease = false;

    static {
        property2SqlColumnMap.put("integer", "int(11)");
        property2SqlColumnMap.put("short", "tinyint(4)");
        property2SqlColumnMap.put("long", "bigint(20)");
        property2SqlColumnMap.put("bigdecimal", "decimal(20, 2)");
        property2SqlColumnMap.put("double", "double precision not null");
        property2SqlColumnMap.put("float", "float");
        property2SqlColumnMap.put("boolean", "tinyint(1)");
        property2SqlColumnMap.put("timestamp", "datetime");
        property2SqlColumnMap.put("date", "datetime");
        property2SqlColumnMap.put("string", "varchar(256)");
        property2SqlColumnMap.put("localdatetime", "datetime");
    }

    public static String generateSql(Class clz, String tableName, String primaryKey) {
        Field[] fields = clz.getDeclaredFields();
        StringBuffer column = new StringBuffer();
        for (Field f : fields) {
            if (f.getName().equals(primaryKey)) {
                continue;
            }
            column.append(getColumnSql(f));
        }
        String sqlPrimaryKey = camelToUnderline(primaryKey).toLowerCase();
        StringBuffer sql = new StringBuffer();
        sql.append("\nDROP TABLE IF EXISTS `" + tableName + "`; ");
        sql.append(" \nCREATE TABLE `" + tableName + "`  (")
                .append(" \n     `" + sqlPrimaryKey + "` bigint(20) NOT NULL");
        if (autoIncrease) {
            sql.append(" AUTO_INCREMENT");
        }
        sql.append(",");
        sql.append(column);
        sql.append(" \n     PRIMARY KEY (`" + sqlPrimaryKey + "`)");
        sql.append(" \n) ENGINE = InnoDB");
        if (autoIncrease) {
            sql.append(" AUTO_INCREMENT = 1");
        }
        sql.append(" CHARACTER SET = utf8 COLLATE = utf8_general_ci;");
        return sql.toString();
    }

    private static String getColumnSql(Field field) {
        String tpl = "\n     `%s` %s DEFAULT NULL,";
        String typeName = field.getType().getSimpleName().toLowerCase();
        String sqlType = property2SqlColumnMap.get(typeName);
        if (sqlType == null || sqlType.isEmpty()) {
            System.out.println(field.getName() + ":" + field.getType().getName() + " 需要单独创建表");
            return "";
        }
        String column = camelToUnderline(field.getName()).toLowerCase();
        String sql = String.format(tpl, column, sqlType.toLowerCase());
        return sql;
    }

    private static String camelToUnderline(String param) {
        if (StringUtils.isBlank(param)) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append("_");
            }
            // 统一都转小写
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
}