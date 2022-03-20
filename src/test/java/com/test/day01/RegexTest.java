package com.test.day01;

import com.lemon.data.Environment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-03-03 10:11
 * @Desc：
 **/
/*
* 正则表达式参数化练习方法
* */
public class RegexTest {
    public static void main(String[] args) {
        String str ="{\"mobile_phone\": \"{{mobile_phone}}\",\"pwd\": \"{{pwd}}\"}";
        System.out.println("原始的字符串"+str);
        //java中的Util工具类中的Pattern类匹配器，compile方法实现正则匹配器，返回值pattern，需要加\\转义
        //表达式加（）是因为要分组
        Pattern pattern =Pattern.compile("\\{\\{(.*?)\\}\\}");
        //matcher方法匹配器，返回值matcher，拿到字符串后需要循环遍历
        Matcher matcher  =pattern.matcher(str);
        //重新给str定义一个返回值,每次循环之前都用最新的result
        String result= str;
        //循环遍历匹配对象
        while (matcher.find()){
            //0代表获取整个匹配的字符串，1代表获取分组的第一组内容
            String allFindStr=matcher.group(0);
            System.out.println("找到的字串"+allFindStr);
            //找到{{XXX}}内部的字符串
            String innerStr =matcher.group(1);
            System.out.println("找到的内部的字符串"+innerStr);
            //从环境变量中找到需要替换的值
            //先给一个环境变量的值,测试代码用
            Environment.envMap.put("mobile_phone","13693368750");
            Environment.envMap.put("pwd","123456");
            Object replaceValue =Environment.envMap.get(innerStr);
            //replace()方法实现字符串数值替换,两个参数，oldChar,newChar。是String类型，所以需要加+""转义
            //第二次替换的结果应该是基于第一次替换的基础上再去替换，所以要在循环体外重新对str赋值,重新开辟了一个内存空间
             result =result.replace(allFindStr,replaceValue+"");
            System.out.println("替换之后的"+result);
        }
    }
}
