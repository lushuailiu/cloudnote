package com.cloudnote.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    private static Properties properties = new Properties();

    static {
        //1.获取db.properties文件输入流
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            //2.加载配置
            properties.load(in);

            //3.获取驱动名称,加载驱动
            Class.forName(properties.getProperty("jdbcName"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据库连接
     * @return connection
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            String dbUrl = properties.getProperty("dbUrl");
            String username = properties.getProperty("username");
            String password = properties.getProperty("password");
            connection = DriverManager.getConnection(dbUrl, username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * 数据库的关闭
     * @param resultSet
     * @param statement
     * @param conn
     * @throws SQLException
     */
    public static void close(ResultSet resultSet, Statement statement,Connection conn) {

            try {
                if (resultSet != null) {
                    resultSet.close();
                }

                if (statement != null) {
                    statement.close();
                }

                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }


    }
}
