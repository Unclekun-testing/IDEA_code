package com.lemon.testcases;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSONObject;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.JDBCUtils;
import com.lemon.utils.RandomDataUtil;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;
import static org.apache.commons.lang3.ArrayUtils.toArray;

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
public class RegisterTest extends BaseTest {
    @BeforeClass
    //手机号参数化准备方法
    public void setup(){
        //准备三个未注册的手机号码
        String mobile_phone1= RandomDataUtil.getUnregisterPhone();
        String mobile_phone2= RandomDataUtil.getUnregisterPhone();
        String mobile_phone3= RandomDataUtil.getUnregisterPhone();
        System.out.println("第一个"+mobile_phone1);
        //保存到环境变量中
        Environment.envMap.put("mobile_phone1",mobile_phone1);
        Environment.envMap.put("mobile_phone2",mobile_phone2);
        Environment.envMap.put("mobile_phone3",mobile_phone3);

    }
    //将测试数据注入方法中
    @Test(dataProvider = "getRegisterDatas")
    //数据提供源返回值为一维数组，数组中的参数为Excel测试用例集合
    //调用两个封装方法
    public void register(CasePojo casePojo)  {
        //参数化替换
        casePojo= paramsReplace(casePojo);
         //发起接口请求，res接收
        Response res = request(casePojo);
        //断言
        assertResponse(res,casePojo);
        //数据库断言
        assertDB(casePojo);
    }


        //数据提供源
        @DataProvider
        public Object[] getRegisterDatas() {
           List<CasePojo> listDatas = ExcelUtil.readExcelSheetAllDatas (1);
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