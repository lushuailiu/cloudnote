package com.cloudnote.note;

import cn.hutool.crypto.digest.DigestUtil;
import com.cloudnote.dao.UserDao;
import com.cloudnote.po.CnUser;
import org.junit.Test;

public class UserApiTest {

    @Test
    public void getUserByUserName() {
        UserDao userDao = new UserDao();
        CnUser luke = userDao.getUserByUserName("Luke");
        System.out.println(luke);
    }

    @Test
    public void md5Test() {
        String Luke = DigestUtil.md5Hex("Luke");
        System.out.println(Luke);
    }
}
