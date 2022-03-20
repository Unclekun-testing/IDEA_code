package com.lemon.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-27 11:42
 * @Desc：
 **/
//类似postman的环境变量类，会存取接口响应中需要的变量值
public class Environment {
    //实例化声明并定义一个envMap方法，static全局共享
    public static Map<String,Object> envMap= new HashMap<String,Object>();

}
