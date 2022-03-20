package com.lemon.testcases;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.RandomDataUtil;
import io.restassured.RestAssured;
import io.restassured.config.JsonConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

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
public class LoginTest extends BaseTest {
    //注册关联登录前置测试用例执行方法
    @BeforeTest
    public void beforeClass(){
        //生成一条未注册的手机号
        String mobile_phone =RandomDataUtil.getUnregisterPhone();
        //保存到环境变量中
        Environment.envMap.put("mobile_phone",mobile_phone);
        //读取测试用例的第一条数据
        List<CasePojo> datas =ExcelUtil.readExcelSpecifyDatas(2,1,1);
        //定义一个变量保存第一条需要注册的数据
        CasePojo registerInfo = datas.get(0);
        //调用参数化替换方法
        registerInfo=paramsReplace(registerInfo);
        //发起接口请求，注册一个用户,集合当中只有一条数据，所以是get0
        //res来接收返回数据，获取手机号
         Response res =request(registerInfo);
         //将注册完成的手机号保存到环境变量方法中
        extractToEnvironment(res,registerInfo);
        //以下两行代码被替换
//         String mobilephone= res.jsonPath().get("data.mobile_phone");
//         //将手机号保存到环境变量
//        Environment.envMap.put("mobile_phone",mobilephone);
    }


    //将测试数据注入方法中
    @Test(dataProvider = "getLoginDatas")
    //数据提供源返回值为一维数组，数组中的参数为Excel测试用例集合
    //调用两个封装方法
    public void Login(CasePojo casePojo){
        System.out.println("环境变量"+Environment.envMap.get("mobile_phone"));
//        //在登录之前，调用regexReplace方法，从测试数据中提取需要替换的参数化数据
//        //结果是String类型，需要定义接收，调用regexReplace方法进行参数替换
//        String result=regexReplace(casePojo.getInputParams());
//        //拿到数据后，重新给测试用例执行
//        casePojo.setInputParams(result);

        //以上代码被替换
        casePojo = paramsReplace(casePojo);
        //发起接口请求，res接收
        Response res = request(casePojo);
        //断言
        assertResponse(res,casePojo);
    }
        //数据提供源
        @DataProvider
        public Object[] getLoginDatas() {
            List<CasePojo> listDatas = ExcelUtil.readExcelSpecifyDatas(2,2);
            return listDatas.toArray();
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