package com.lemon.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-20 16:01
 * @Desc：
 **/

//创建获取Excel每行数据的实体类
public class CasePojo {
    //创建Excel每列数据的实体类属性
    @Excel(name = "序号(caseId)")
    private int caseId;
    @Excel(name = "接口模块(interface)")
    private String interfaceName;
    @Excel(name = "用例标题(title)")
    private String title;
    @Excel(name = "请求头(requestHeader)")
    private String requestHeader;
    @Excel(name = "请求方式(method)")
    private String method;
    @Excel(name = "接口地址(url)")
    private String url;
    @Excel(name = "参数输入(inputParams)")
    private String inputParams;
    @Excel(name = "期望返回结果(expected)")
    private String expected;
    @Excel(name = "提取表达式(extractExper)")
    private String extractExper;
    @Excel(name = "数据库断言(dbAssert)")
    private String dbAssert;

    //添加GET/SET方法
    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getExtractExper() {
        return extractExper;
    }

    public void setExtractExper(String extractExper) {
        this.extractExper = extractExper;
    }

    public String getDbAssert() {
        return dbAssert;
    }

    public void setDbAssert(String dbAssert) {
        this.dbAssert = dbAssert;
    }


    //添加toString方法
    @Override
    public String toString() {
        return "CasePojo{" +
                "caseId=" + caseId +
                ", interfaceName='" + interfaceName + '\'' +
                ", title='" + title + '\'' +
                ", requestHeader='" + requestHeader + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                ", inputParams='" + inputParams + '\'' +
                ", expected='" + expected + '\'' +
                ", extractExper='" + extractExper + '\'' +
                ", dbAssert='" + dbAssert + '\'' +
                '}';
    }


//添加构造方法，需要注意，EasyPoi底层框架读取实体类需要无参构造，如果只写了有参构造，Java会默认把无参构造覆盖
//    public CasePojo(int caseId, String interfaceName, String title, String requestHeader, String method, String url, String inputParams, String expected) {
//        this.caseId = caseId;
//        this.interfaceName = interfaceName;
//        this.title = title;
//        this.requestHeader = requestHeader;
//        this.method = method;
//        this.url = url;
//        this.inputParams = inputParams;
//        this.expected = expected;
//    }
//    public CasePojo(){
//
//    }
}
