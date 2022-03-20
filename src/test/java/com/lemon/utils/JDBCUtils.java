package com.lemon.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @Project: rest-assured
 * @Site: 2679824572
 * @Forum: Monster
 * @Copyright: ©bode
 * @Author: 达达
 * @Create: 2022-03-07 17:15
 * @Desc：
 **/


/*
* 数据库工具类
* */
public class JDBCUtils {
    /*
    * 和数据库建立链接，返回数据库链接对象
    * */
    public static Connection getConnection() {
//定义数据库连接
//Oracle：jdbc:oracle:thin:@localhost:1521:DBName
//SqlServer：jdbc:microsoft:sqlserver://localhost:1433; DatabaseName=DBName
//MySql：jdbc:mysql://localhost:3306/DBName
    String url="jdbc:mysql://api.lemonban.com/futureloan?useUnicode=true&characterEncoding=utf-8";
    String user="future";
    String password="123456";
        //定义数据库连接对象
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url,user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
    /*
    *更新修改数据库操作方法，可执行插入，修改，删除。参数是要执行的sql语句
    * */
    public static void updateData(String sql){
        //1、建立链接，这是一个数据库资源，使用完毕后需要及时关闭
        Connection conn= getConnection();
        //2、QueryRunner对象生成
        QueryRunner queryRunner = new QueryRunner();
        //执行Sql
        try {
            queryRunner.update(conn,sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //关闭链接
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    /*
    *查询单个字段的数据，参数是Sql语句，返回Object类型查询结果
    * */
    public static Object querySingleData(String sql){
        //1、建立链接，这是一个数据库资源，使用完毕后需要及时关闭
        Connection conn= getConnection();
        //2、QueryRunner对象生成
        QueryRunner queryRunner = new QueryRunner();
        //执行Sql，需要返回值Object
        Object data = null;
        try {
             data=queryRunner.query(conn,sql,new ScalarHandler<Object>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }return data;
    }

    /*
     *查询所有字段的数据，参数是Sql语句，返回List<Map<String,Object>>类型结果
     * */
    public static List<Map<String,Object>> queryAllDatas(String sql){
        //1、建立链接，这是一个数据库资源，使用完毕后需要及时关闭
        Connection conn= getConnection();
        //2、QueryRunner对象生成
        QueryRunner queryRunner = new QueryRunner();
        //执行Sql，需要返回值Object
        List<Map<String,Object>> data = null;
        try {
            data=queryRunner.query(conn,sql,new MapListHandler());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }return data;
    }
}
