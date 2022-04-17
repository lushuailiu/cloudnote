package com.cloudnote.note;

import cn.hutool.core.lang.Console;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.log.dialect.commons.ApacheCommonsLogFactory;
import cn.hutool.log.dialect.console.ConsoleLogFactory;
import cn.hutool.log.dialect.jdk.JdkLogFactory;
import com.cloudnote.util.DBUtil;
import org.junit.Test;

import java.sql.Connection;
import java.util.logging.Level;

/**
 * 测试数据库的连接
 */
public class DBTest {

    private static final Log log  = LogFactory.get();

    @Test
    public void testDBConn(){
        Connection connection = DBUtil.getConnection();
        log.info("This is {} log", Level.INFO);
        log.info("数据库连接成功:{}",connection);
        Console.log("aaa");
    }

    @Test
    public void testLog(){
        // 自动选择日志实现
        Log log = LogFactory.get();
        log.debug("This is {} log", "default");
        Console.log("----------------------------------------------------------------------");

        //自定义日志实现为Apache Commons Logging
        // LogFactory.setCurrentLogFactory(new ApacheCommonsLogFactory());
        // log.debug("This is {} log", "custom apache commons logging");
        // Console.log("----------------------------------------------------------------------");

        //自定义日志实现为JDK Logging
        // LogFactory.setCurrentLogFactory(new JdkLogFactory());
        // log.info("This is {} log", "custom jdk logging");
        // Console.log("----------------------------------------------------------------------");

//自定义日志实现为Console Logging
        LogFactory.setCurrentLogFactory(new ConsoleLogFactory());
        log.info("This is {} log", "custom Console");
        Console.log("----------------------------------------------------------------------");
    }

}
