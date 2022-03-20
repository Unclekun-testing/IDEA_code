package com.lemon.utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.pojo.CasePojo;

import java.io.File;
import java.util.List;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-02-23 16:09
 * @Desc：
 **/

    /*
    * 工具类静态方法，不需要实例化对象，类名.方法名方式就可以调用,读取指定Sheet中的所有数据
    * */
public class ExcelUtil {
    public static List<CasePojo> readExcelSheetAllDatas(int sheetNum) {
        //复制的绝对路径文件，但是/需要转义\\，不转义也没有报错。本地文件修改后，需要再次覆盖项目中的文件才能生效
        File file = new File(Constants.EXCEL_PATH);
        //实例化导入Excel的参数配置
        ImportParams importParams = new ImportParams();
        //设置从Excle第几页读取数据
        importParams.setStartSheetIndex(sheetNum-1);
        //通过Easypoi工具类读取Excel中的数据
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
        //把集合数据转化为数组
        return  listDatas;
//        Object[] datas = listDatas.toArray();
//        return datas;

    }
/*
* 读取指定Sheet中的指定数据
* */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNum,int startRows,int readRows) {
        //复制的绝对路径文件，但是/需要转义\\，不转义也没有报错。本地文件修改后，需要再次覆盖项目中的文件才能生效
        File file = new File(Constants.EXCEL_PATH);
        //实例化导入Excel的参数配置
        ImportParams importParams = new ImportParams();
        //设置从Excle第几页读取数据，从0开始
        importParams.setStartSheetIndex(sheetNum-1);
        //设置从第几行开始读，从0开始，不包括表头
        importParams.setStartRows(startRows-1);
        //设置一共读取几行
        importParams.setReadRows(readRows);
        //通过Easypoi工具类读取Excel中的数据
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
        //把集合数据转化为数组
        return  listDatas;
//        Object[] datas = listDatas.toArray();
//        return datas;

    }

    /*
     * 读取指定Sheet中的指定数据,方法重载，一直读取到最后的位置
     * */
    public static List<CasePojo> readExcelSpecifyDatas(int sheetNum,int startRows) {
        //复制的绝对路径文件，但是/需要转义\\，不转义也没有报错。本地文件修改后，需要再次覆盖项目中的文件才能生效
        File file = new File(Constants.EXCEL_PATH);
        //实例化导入Excel的参数配置
        ImportParams importParams = new ImportParams();
        //设置从Excle第几页读取数据
        importParams.setStartSheetIndex(sheetNum-1);
        //设置从第几行开始读
        importParams.setStartRows(startRows-1);

        //通过Easypoi工具类读取Excel中的数据
        List<CasePojo> listDatas = ExcelImportUtil.importExcel(file, CasePojo.class, importParams);
        //把集合数据转化为数组
        return  listDatas;
//        Object[] datas = listDatas.toArray();
//        return datas;

    }
}
