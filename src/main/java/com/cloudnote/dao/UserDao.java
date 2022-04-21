package com.cloudnote.dao;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.cloudnote.po.CnUser;
import com.cloudnote.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDao {
    public static Log log = LogFactory.get();


    public CnUser getUserByUserName(String userName){
        // 1. 定义 SQL 语句
        String sql = "select userId,uname,upwd,nick,mood,head,userId from cn_user where uname = ?";

        // 2. 设置参数集合
        List<Object> params = new ArrayList<>();
        params.add(userName);

        // 3. 调用BaseDao() 查询方法
        CnUser user = (CnUser) BaseDao.queryRow(sql, params, CnUser.class);

        return  user;
    }

    /**
     * 用户名查询用户对象,并返回用户对象
     * 1.获取数据库连接
     * 2.定义SQL语句
     * 3.预编译
     * 4.设置参数
     * 5.执行查询并返回结果集
     * 6.判断并分析结果集
     * 7.关闭资源
     * @return
     */
    public CnUser getUserByUserName2(String userName){
        log.info("getUserByUserName({}) Executing",userName);
        CnUser user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //1.获取数据库连接
            connection = DBUtil.getConnection();
            //2.定义SQL语句
            String sql = "select * from cn_user where uname = ? ";
            //3.预编译
            preparedStatement = connection.prepareStatement(sql);
            // 4.设置参数
            preparedStatement.setString(1,userName);
            // 5.执行查询并返回结果集
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                user = new CnUser();
                user.setUserId(resultSet.getInt("userId"));
                user.setUname(resultSet.getString("uname"));
                user.setUpwd(resultSet.getString("upwd"));
                user.setNick(resultSet.getString("nick"));
                user.setHead(resultSet.getString("head"));
                user.setMood(resultSet.getString("mood"));
            }

        } catch (SQLException e) {
            log.error("getUserByUserName执行失败!");
            e.printStackTrace();
        }

        return user;
    }

    public CnUser getUserByNickAndNoUserId(String nick, long userId) {
        String sql = "select * from cn_user where nick = ? and userId <> ?";

        List<Object> params = new ArrayList<>();
        params.add(nick);
        params.add(userId);
        CnUser user = (CnUser)BaseDao.queryRow(sql, params, CnUser.class);

        return user;
    }
}
