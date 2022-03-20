package com.lemon.common;

import com.alibaba.fastjson.JSONObject;
import com.lemon.data.Environment;
import com.lemon.pojo.CasePojo;

import com.lemon.utils.Constants;
import com.lemon.utils.JDBCUtils;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.config.JsonConfig.jsonConfig;
import static io.restassured.path.json.config.JsonPathConfig.NumberReturnType.BIG_DECIMAL;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-26 17:22
 * @Desc：
 **/

//公共方法父类
public class BaseTest {
    @BeforeSuite
    //在整个方法运行之前，只会运行一次，全局配置
    public void beforeSuite() throws FileNotFoundException {
        //把Json小数的返回类型配置成BigDecimal,因为后续实际结果与预期结果对比时，会有类型不一致问题
        RestAssured.config=RestAssured.config().jsonConfig(jsonConfig().numberReturnType(BIG_DECIMAL));
        //Rest-Assured基础的baseurl配置，设置之后，请求地址不需要拼接，自动识别
        RestAssured.baseURI= Constants.BASE_URI;
        //全局日志配置，将所有日志保存到文件中
        //打印流
//        PrintStream fileOutPutStream = new PrintStream(new File("test_all.log"));
        //过滤器方法，将日志由控制台重定向到文件当中，两个参数，请求日志过滤器，响应日志过滤器
//        RestAssured.filters(new RequestLoggingFilter(fileOutPutStream),new ResponseLoggingFilter(fileOutPutStream));

    }

    //数据提供源返回值为一维数组，数组中的参数为Excel测试用例集合
    /*
     * 接口请求方法封装，参数为测试数据实体类CasePojo,返回值类型Response
     * */
    public Response request(CasePojo casePojo) {
        //时间戳
//        Long timestamp =System.currentTimeMillis();
        //添加日志
        //创建文件夹,添加控制台开关控制
        //重新定义logFilepath作用域
        String logFilepath="";
        if(!Constants.SHOW_CONSOLE_LOG) {
            File dirFile = new File("logs\\" + casePojo.getInterfaceName());
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            PrintStream fileOutPutStream = null;
            //定义一个变量接收日志文件路径
             logFilepath = "logs\\" + casePojo.getInterfaceName() + "\\" + casePojo.getInterfaceName() + "_" + casePojo.getCaseId() + ".log";
            try {
                fileOutPutStream = new PrintStream(new File(logFilepath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().defaultStream(fileOutPutStream));
        }
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
        //获取请求方式
        String method = casePojo.getMethod();
        //定义一个Response类型返回，res接收
        Response res = null;
        //对不同的请求方式进行封装（get/post/patch/put/delete）
        if("get".equalsIgnoreCase(method)){
            //get请求需要在请求头后添加queryParam()方法
            res = RestAssured.
                    given().log().all().headers(requestHeaderMap).
                    when().get(url).
                    then().log().all().extract().response();
        }else if("post".equalsIgnoreCase(method)){
            res = RestAssured.
                    given().log().all().headers(requestHeaderMap).
                    body(params).
                    when().post(url).
                    then().log().all().extract().response();
        }else if ("patch".equalsIgnoreCase(method)){
            res = RestAssured.
                    given().log().all().headers(requestHeaderMap).
                    body(params).
                    when().patch(url).
                    then().log().all().extract().response();
        }else if ("put".equalsIgnoreCase(method)){
            res = RestAssured.
                    given().log().all().headers(requestHeaderMap).
                    body(params).
                    when().put(url).
                    then().log().all().extract().response();
        }
        //请求结束后，将接口日志添加到Allure报表中，添加控制台开关控制
        if(!Constants.SHOW_CONSOLE_LOG) {
            try {
                Allure.addAttachment("接口请求响应日志", new FileInputStream(logFilepath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        //返回结果
        return res;
    }
    /*
     *响应断言方法封装，参数为响应返回结果，测试数据实体类CasePojo
     * */
    public void assertResponse(Response res , CasePojo casePojo){
        //从测试用例期望返回结果中取出响应数据（json格式，key value,键值：Gpath表达式，键名：期望结果）
        String expected = casePojo.getExpected();
        if(expected !=null){
            //字符串转Map,需要声明键的类型是String
            Map<String, Object> expectedmap = JSONObject.parseObject(expected);
            //遍历Map,需要声明键的类型是String
            //判空
            Set<String> allKeySet = expectedmap.keySet();
            for (String key : allKeySet) {
                //key就是对应的Gpath表达式
                //获取实际的响应结果
                Object actualResult = res.jsonPath().get(key);
//            System.out.println("实际响应结果"+actualResult);
//            System.out.println("实际响应结果类型"+actualResult.getClass());
                //获取期望的响应结果
                Object expectedResult =expectedmap.get(key);
//            System.out.println("期望响应结果"+expectedResult);
//            System.out.println("期望响应结果类型"+expectedResult.getClass());
                Assert.assertEquals(actualResult,expectedResult);
            }
        }
    }
    /*
    * 数据库断言方法
    * */
    public void assertDB(CasePojo casePojo){
        //从测试用例中取出断言信息
        String dbAssertInfo=casePojo.getDbAssert();
        //判空
        if(dbAssertInfo != null) {
            //json字符串转Map
            Map<String, Object> mapDbAssert = JSONObject.parseObject(dbAssertInfo);
            Set<String> allKeys = mapDbAssert.keySet();
            //遍历集合
            for (String key : allKeys) {
                //数据库工具类执行查询操作(实际结果)
                Object dbActual = JDBCUtils.querySingleData(key);
                //根据数据库实际返回类型进行判断
                if(dbActual instanceof Long){
                    //获取测试用例中的数据库Sql,key为要执行的Sql语句(期望结果),强转成Integer接收
                    Integer dbExpected = (Integer) mapDbAssert.get(key);
                    //把Integer转成Long类型，统一类型，方便比较
                    Long expected = dbExpected.longValue();
                    //断言
                    Assert.assertEquals(dbActual, expected);
                }else{
                    Object expected=mapDbAssert.get(key);
                    //断言
                    Assert.assertEquals(dbActual, expected);
                }
            }
        }
    }



    /*
     * 环境变量公共方法
     * 提取接口响应中对应字段表达式的值，保存到环境变量中
     * 两个参数，响应信息，测试用例中的提取表达式（extractExper）
     * */
    public void extractToEnvironment(Response res,CasePojo casePojo){
        //获取到提取表达式（extractExper）：{"mobile_phone":"dada.mobile_phone"}
        //这是一个Json字符串
        String extractStr=casePojo.getExtractExper();
        System.out.println("表达式"+extractStr);
        //加判空处理，因为提取的是整列数据，所以有的用例不需要参数化，没有提取表达式，会有空指针的运行报错
        if(extractStr != null){
            //把提取的表达式转Map
            Map<String,Object> map = JSONObject.parseObject(extractStr);
            //通过KeySet遍历集合
            Set<String> allKetSets= map.keySet();
            for(String key:allKetSets){
                //key为变量名mobile_phone，value是dada.mobile_phone提取的Gpath表达式
                Object value = map.get(key);
                //从响应结果Response中获取到实际的手机号码值
                Object actualValue=res.jsonPath().get((String) value);
                //将对应的键和值保存到环境变量中
                Environment.envMap.put(key,actualValue);
            }
        }
    }
    /*
    * 正则表达式参数化替换方法
    * */
    //原始字符串写到参数中，orgstr
    public String regexReplace(String orgstr){
        //加判空处理
        if(orgstr !=null) {
            //java中的Util工具类中的Pattern类匹配器，compile方法实现正则匹配器，返回值pattern，需要加\\转义
            //表达式加（）是因为要分组
            Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
            //matcher方法匹配器，返回值matcher，拿到字符串后需要循环遍历
            Matcher matcher = pattern.matcher(orgstr);
            //重新给str定义一个返回值,每次循环之前都用最新的result
            String result = orgstr;
            //循环遍历匹配对象
            while (matcher.find()) {
                //0代表获取整个匹配的字符串，1代表获取分组的第一组内容
                String allFindStr = matcher.group(0);
                //找到{{XXX}}内部的字符串
                String innerStr = matcher.group(1);
                //从环境变量中找到需要替换的值
                Object replaceValue = Environment.envMap.get(innerStr);
                //replace()方法实现字符串数值替换,两个参数，oldChar,newChar。是String类型，所以需要加+""转义
                //第二次替换的结果应该是基于第一次替换的基础上再去替换，所以要在循环体外重新对str赋值,重新开辟了一个内存空间
                result = result.replace(allFindStr, replaceValue + "");
            }
            return result;
        }else{
            return orgstr;
        }
    }
    /*
    *所有用例数据的参数化替换方法，只要在对应的用例数据中有{{XXX}}对应的正则表达式，
    * 就会从环境变量中找XXX，找到就会替换
    * */
    public CasePojo paramsReplace(CasePojo casePojo){
        //1、请求头
        //获取到请求头
        String requestHeader=casePojo.getRequestHeader();
        //调用regexReplace方法进行参数替换
        String result =regexReplace(requestHeader);
        //重新对测试用例设置改变后的结果
        casePojo.setRequestHeader(result);
        //也可以用这部分代码替换：casePojo.setRequestHeader(regexReplace(requestHeader));

        //2、接口地址
        String url= casePojo.getUrl();
        casePojo.setUrl(regexReplace(url));

        //3、参数输入
//        String result1 =regexReplace(inputParams);
//        casePojo.setInputParams(result1);
//        System.out.println(result1);
        String inputParams = casePojo.getInputParams();
        casePojo.setInputParams(regexReplace(inputParams));
        System.out.println("表达式"+inputParams);

        //4、期望返回结果
        String expected = casePojo.getExpected();
        casePojo.setExpected(regexReplace(expected));
        //需要返回
        return casePojo;
    }
}
