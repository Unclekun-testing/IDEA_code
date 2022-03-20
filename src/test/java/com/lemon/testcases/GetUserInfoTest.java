package com.lemon.testcases;

import com.lemon.common.BaseTest;
import com.lemon.data.Environment;
import com.lemon.encryption.RSAManager;
import com.lemon.pojo.CasePojo;
import com.lemon.utils.ExcelUtil;
import com.lemon.utils.RandomDataUtil;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-03-09 15:50
 * @Desc：
 **/
/*
* 获取用户信息方法
*
* */
public class GetUserInfoTest extends BaseTest{
    //1、通过EasyPoi获取测试用例数据2、通过rest-assured发起接口请求3、通过Test-NG DataProvider实现数据驱动
        @BeforeClass
        //手机号参数化准备方法
        public void setup(){
            //准备未注册的手机号码
            String mobile_phone= RandomDataUtil.getUnregisterPhone();
            System.out.println("第一个"+mobile_phone);
            //保存到环境变量中
            Environment.envMap.put("mobile_phone",mobile_phone);
        }
        //将测试数据注入方法中
        @Test(dataProvider = "getUserInfoDatas")
        //数据提供源返回值为一维数组，数组中的参数为Excel测试用例集合
        //调用两个封装方法
        public void user_info(CasePojo casePojo) throws Exception {
           if(casePojo.getCaseId()>2){
               v3Author();
           }
            //参数化替换
            casePojo= paramsReplace(casePojo);
            //发起接口请求，res接收
            Response res = request(casePojo);
            //断言
            assertResponse(res,casePojo);
            //提取响应结果到环境变量
            extractToEnvironment(res,casePojo);
        }
        //V3 token鉴权封装方法
    public void v3Author() throws Exception {
        //得到时间戳timestamp，时间戳是毫秒，要转化为秒使用
        Long timestamp =System.currentTimeMillis()/1000;
        //sign参数获取（取 token 前 50 位再拼接上 timestamp 值，然后通过 RSA 公钥加密得到 的字符串）
        //定义变量保存tokenValue,由于返回的类型是Object,所以要强转String
        String tokenValue = (String) Environment.envMap.get("token");
        //调用substring方法，两个参数。开始位置，结束位置
        String token_50 =tokenValue.substring(0,50);
        String str = token_50+timestamp;
        //在Maven中引入开发的加密算法Jar包，调用RSA加密算法
        String  encryptStr= RSAManager.encryptWithBase64(str);
        //时间戳以及加密的字符串，要保存到环境变量中
        Environment.envMap.put("sign",encryptStr);
        Environment.envMap.put("timestamp",timestamp);
    }

        //数据提供源
        @DataProvider
        public Object[] getUserInfoDatas() {
            List<CasePojo> listDatas = ExcelUtil.readExcelSheetAllDatas (3);
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
