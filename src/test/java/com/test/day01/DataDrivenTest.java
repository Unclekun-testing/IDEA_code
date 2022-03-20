package com.test.day01;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-20 15:29
 * @Desc：
 **/

//1、通过EasyPoi获取测试用例数据2、通过rest-assured发起接口请求3、通过Test-NG DataProvider实现数据驱动
public class DataDrivenTest {
    //将测试数据注入方法中
    @Test(dataProvider = "getRegisterDatas")
    //数据提供源返回值为一维数组，数组中的参数为Excel测试用例集合
    public void register(CasePojo casePojo) {
//        System.out.println(casePojo.getTitle());
        //获取请求头
        //需要把测试用例中请求头的json字符串转Map
        String requestHeaders = casePojo.getRequestHeader();
        //需要用到fastjson依赖，导入依赖后需要重启Idea
        Map requestHeaderMap = JSONObject.parseObject(requestHeaders);
        //获取请求地址
        String url = casePojo.getUrl();
        //获取请求参数
        String params = casePojo.getInputParams();
        Response res = RestAssured.
                given().headers(requestHeaderMap).
                body(params).
                when().post("http://api.lemonban.com/futureloan" + url).
                then().log().all().extract().response();
        //断言
        //从测试用例期望返回结果中取出响应数据（json格式，key value,键值：Gpath表达式，键名：期望结果）
        String expected = casePojo.getExpected();
        //字符串转Map,需要声明键的类型是String
        Map<String, Object> expectedmap = JSONObject.parseObject(expected);
        //遍历Map,需要声明键的类型是String
        Set<String> allKeySet = expectedmap.keySet();
        for (String key : allKeySet) {
            //key就是对应的Gpath表达式
            //获取实际的响应结果
            Object actualResult = res.jsonPath().get(key);
            //获取期望的响应结果
            Object expectedResult =expectedmap.get(key);
            Assert.assertEquals(actualResult,expectedResult);

        }
    }
        //数据提供源
        @DataProvider
        public Object[] getRegisterDatas() {
            //复制的绝对路径文件，但是/需要转义\\，不转义也没有报错。本地文件修改后，需要再次覆盖项目中的文件才能生效
            File file = new File("src/test/rescources/api_testcases_futureloan_v1.xls");
            //实例化导入Excel的参数配置
            ImportParams importParams = new ImportParams();
            //设置从Excle第几页读取数据
            importParams.setStartSheetIndex(0);
            //通过Easypoi工具类读取Excel中的数据
            List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
            //把集合数据转化为数组
            //return  listDatas.toArray();
            Object[] datas = listDatas.toArray();
            return datas;

        }





//    public static void main(String[] args) {
//        File file = new File("src/test/rescources/api_testcases_futureloan_v1.xls" );
//        //实例化导入Excel的参数配置
//        ImportParams importParams = new ImportParams();
//        //设置从Excle第几页读取数据
//        importParams.setStartSheetIndex(0);
//        //通过Easypoi工具类读取Excel中的数据
//        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file,CasePojo.class,importParams);
//        System.out.println(listDatas.size());
//    }

}