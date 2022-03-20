package com.lemon.utils;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-26 21:24
 * @Desc：
 **/
//项目中一些需要的常量写在这里，常量类
public class Constants {
    //项目访问地址
    public static final String PROJECT_URL="api.lemonban.com";
    //BASE_URI地址
    public static final String BASE_URI= "http://"+PROJECT_URL+"/futureloan";
    //Excel文件地址
    public static final String EXCEL_PATH= "src/test/rescources/api_testcases_futureloan_v1.xls";
    //控制台日志输出开关（true输出到控制台，false不输出到控制台，打印到Allure报表中），默认赋值true
    public static final boolean SHOW_CONSOLE_LOG=true;
}
