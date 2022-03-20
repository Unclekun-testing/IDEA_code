package com.test.day01;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.io.File;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-15 17:38
 * @Desc：
 **/

public class RestAssuredTest {
    @Test
    //1.请求方式2.请求地址3.请求头字段4.请求参数
    public void register(){
        String jsonData="{\"mobile_phone\":\"13693368701\",\"pwd\":\"lemon123456\",\"type\":1}";
        RestAssured.
                given().header("X-Lemonban-Media-Type","lemonban.v1").
                contentType("application/json").body(jsonData).
                when().post("http://api.lemonban.com/futureloan/member/register").
                then().log().all();
    }
    @Test
    public void getRequest(){
        RestAssured.
                given().
                header("X-Lemonban-Media-Type","lemonban.v1").
                queryParam("mobilephone","13693368703").
                queryParam("pwd","123456").
                contentType("application/json").
                when().get("http://httpbin.org/get").
                then().log().all();
    }
    //form表单传参
    @Test
    public void postRequest01(){
        RestAssured.
                given().
                contentType("application/x-www-form-urlencoded").
                body("mobilephone=13693368704&pwd=123456").
                when().post("http://httpbin.org/post").
                then().log().all();
    }
    //post请求传输文件
    @Test
    public void postRequest02(){
        File file = new File("D:\\prada\\环境部署.pdf");
        RestAssured.
                given().
                contentType("multipart/form-data").
                multiPart(file).
                when().post("http://httpbin.org/post").
                then().log().all();
    }
}
