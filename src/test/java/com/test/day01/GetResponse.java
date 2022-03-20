package com.test.day01;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-17 12:17
 * @Desc：
 **/

public class GetResponse {
    //jsonpath获取数据
    @Test
    public void register(){
        String jsonData="{\"mobile_phone\":\"13693368707\",\"pwd\":\"12345678\",\"type\":1}";
        Response res =RestAssured.
                given().header("X-Lemonban-Media-Type","lemonban.v1").
                contentType("application/json").body(jsonData).
                when().post("http://api.lemonban.com/futureloan/member/register").
                then().log().all().
                extract().response();
        //获取Http状态码
        System.out.println(res.statusCode());
        //获取接口响应体中的数据，通过Json路径表达式获取到响应体中某一个节点的值
        Object userid = res.jsonPath().get("data.id");
        System.out.println("用户id为"+userid);
    }
    //htmlpath获取数据
    @Test
    public void getHtmlData(){
        Response res =RestAssured.
                given().
                when().get("http://www.baidu.com").
                then().log().all().
                extract().response();
        //获取title标签文本值
        String titleStr = res.htmlPath().get("html.head.title");
        System.out.println(titleStr);
        //获取标签的属性值
        String str = res.htmlPath().get("html.head.meta[0].@content");
        System.out.println(str);
    }
    //注册登录业务关联方法
    @Test
    public void registerLogin(){
        //发起注册接口请求
        String jsonData="{\"mobile_phone\":\"13693368714\",\"pwd\":\"12345678\",\"type\":1}";
        Response res =RestAssured.
                given().header("X-Lemonban-Media-Type","lemonban.v1").
                contentType("application/json").body(jsonData).
                when().post("http://api.lemonban.com/futureloan/member/register").
                then().log().all().
                extract().response();
        //从注册响应结果中获取注册手机号
        Object mobilephone = res.jsonPath().get("data.mobile_phone");
        System.out.println(mobilephone);

        //发起登录接口请求
        String jsonData2="{\"mobile_phone\":\""+mobilephone+"\",\"pwd\":\"12345678\"}";
        RestAssured.
                given().header("X-Lemonban-Media-Type","lemonban.v1").
                contentType("application/json").body(jsonData2).
                when().post("http://api.lemonban.com/futureloan/member/login").
                then().log().all().
                extract().response();

    }
    //登录充值接口关联方法，token鉴权
    @Test
    public void tokenLogin(){
        //发起登录请求
        String jsonData="{\"mobile_phone\":\"13693368714\",\"pwd\":\"12345678\"}";
        Response res =RestAssured.
                given().
                header("X-Lemonban-Media-Type","lemonban.v2").
                contentType("application/json").body(jsonData).
                when().post("http://api.lemonban.com/futureloan/member/login").
                then().log().all().
                extract().response();
        //获取token值
        Object tokenValue = res.jsonPath().get("data.token_info.token");
//        System.out.println(tokenValue);
        //获取user_id
        Object userid = res.jsonPath().get("data.id");

        //发起充值请求
        String jsonData2="{\"member_id\":\""+userid+"\",\"amount\":1000}";
        Response res2 =RestAssured.
                given().
                header("X-Lemonban-Media-Type","lemonban.v2").
                contentType("application/json").body(jsonData2).
                header("Authorization","Bearer "+tokenValue).
                when().post("http://api.lemonban.com/futureloan/member/recharge").
                then().log().all().
                extract().response();

    }


}
