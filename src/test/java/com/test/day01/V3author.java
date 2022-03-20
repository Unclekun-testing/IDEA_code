package com.test.day01;

import com.lemon.encryption.RSAManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-03-14 18:24
 * @Desc：
 **/

public class V3author {
    @Test
    public void testAuthor() throws Exception {
        //发起登录请求
        String jsonData="{\"mobile_phone\":\"13693368714\",\"pwd\":\"12345678\"}";
        Response res =RestAssured.
                given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                contentType("application/json").body(jsonData).
                when().post("http://api.lemonban.com/futureloan/member/login").
                then().log().all().
                extract().response();
        //获取token值,需要强转为String，供拼接使用
        String tokenValue = (String) res.jsonPath().get("data.token_info.token");
//        System.out.println(tokenValue);
        //获取user_id
        Object userid = res.jsonPath().get("data.id");
        //发起充值请求
        //得到时间戳timestamp，时间戳是毫秒，要转化为秒使用
        Long timestamp =System.currentTimeMillis()/1000;
        //sign参数获取（取 token 前 50 位再拼接上 timestamp 值，然后通过 RSA 公钥加密得到 的字符串）
        //调用substring方法，两个参数。开始位置，结束位置
        String token_50 =tokenValue.substring(0,50);
        String str = token_50+timestamp;
        //在Maven中引入开发的加密算法Jar包，调用RSA加密算法
        String  encryptStr=RSAManager.encryptWithBase64(str);
        //拼接加密后的参数,加密后的字符串需要加""
        String jsonData2="{\"member_id\": "+userid+",\"amount\": 1000.0,\"timestamp\": "+timestamp+",\"sign\": \""+encryptStr+"\"}";
        Response res2 =RestAssured.
                given().log().all().
                header("X-Lemonban-Media-Type","lemonban.v3").
                contentType("application/json").body(jsonData2).
                header("Authorization","Bearer "+tokenValue).
                when().post("http://api.lemonban.com/futureloan/member/recharge").
                then().log().all().
                extract().response();
    }
}
