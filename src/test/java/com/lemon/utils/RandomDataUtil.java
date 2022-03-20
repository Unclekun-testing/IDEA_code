package com.lemon.utils;

import org.testng.IResultMap;

import java.util.Random;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-03-08 14:37
 * @Desc：
 **/
/*
* 随机数据工具类
* //第二种方法：先去查询数据库中手机号码最大的一个，在其基础上加1
* */
public class RandomDataUtil {
    //第一方法：先生成一个随机手机号码，再去查数据库，如果数据库存相同的，再生成一个。如果不存在，满足要求
    //随机手机号方法
    public static String randomPhone(){
        //定义号段
        String phonePrefix="133";
        Random random= new Random();
        //随机生成0-9范围内的整数，拼接号段
        for(int i=0;i<8;i++){
            //生成0-9范围内的整数
            int num = random.nextInt(9);
            phonePrefix = phonePrefix+num;
        }return phonePrefix;
    }


    public static String getUnregisterPhone(){
        //循环体外的代码连同continue被替换
//        String phone = randomPhone();
        //重新定义phone等于空的字符串
        String phone= "";
        while(true){
            //调用随机手机号方法
            phone = randomPhone();
            //查询数据库，寻找是否存在随机生成的手机号。拼接值是变量手机号，返回值是强转long类型
            long result =(long)JDBCUtils.querySingleData("select count(*)from member where mobile_phone= '"+phone+"'");
            if(1==result){
                //如果存在，继续生成一个随机数，可以不写continue
//                continue;
            }else{
                //说明不存在，跳出循环
                break;
            }
        }return phone;
    }

//    public static void main(String[] args) {
//        System.out.println(getUnregisterPhone());
//    }
}
