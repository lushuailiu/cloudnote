package com.cloudnote.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.cloudnote.dao.UserDao;
import com.cloudnote.po.CnUser;
import com.cloudnote.vo.ResultInfo;

public class UserService {
    UserDao userDao = new UserDao();
    /**
     * Service层：（业务逻辑层：参数判断、业务逻辑处理）
     * 用户登录
     *         1. 判断参数是否为空
     *             如果为空
     *                 设置ResultInfo对象的状态码和提示信息
     *                 返回resultInfo对象
     *         2. 如果不为空，通过用户名查询用户对象
     *         3. 判断用户对象是否为空
     *                 如果为空
     *                     设置ResultInfo对象的状态码和提示信息
     *                     返回resultInfo对象
     *         4. 如果用户对象不为空，将数据库中查询到的用户对象的密码与前台传递的密码作比较 （将密码加密后再比较）
     *                如果密码不正确
     *                     设置ResultInfo对象的状态码和提示信息
     *                     返回resultInfo对象
     *         5. 如果密码正确
     *             设置ResultInfo对象的状态码和提示信息
     *         6. 返回resultInfo对象
     * @param userName
     * @param userPwd
     * @return
     */

    public ResultInfo<CnUser> userLogin(String userName, String userPwd) {
        ResultInfo<CnUser> resultInfo = new ResultInfo<>();
        CnUser cnUser = new CnUser(userName,userPwd);
        resultInfo.setResult(cnUser);

        if (StrUtil.isBlank(userName) || StrUtil.isBlank(userPwd)){
            // 如果为空 设置ResultInfo对象的状态码和提示信息
            resultInfo.setCode(0);
            resultInfo.setMsg("用户名或密码不能为空！");
            // 返回resultInfo对象
            return resultInfo;
        }

        CnUser user = userDao.getUserByUserName(userName);

        if (ObjectUtil.isNull(user)) {
            // 如果为空 设置ResultInfo对象的状态码和提示信息
            resultInfo.setCode(0);
            resultInfo.setMsg("该用户不存在！");
            // 返回resultInfo对象
            return resultInfo;
        }

        if (!user.getUpwd().equals(DigestUtil.md5Hex(userPwd))) {
            // 如果为空 设置ResultInfo对象的状态码和提示信息
            resultInfo.setCode(0);
            resultInfo.setMsg("密码错误！");
            // 返回resultInfo对象
            return resultInfo;
        }

        // 5. 如果密码正确
        //  设置ResultInfo对象的状态码和提示信息
        resultInfo.setCode(1);
        resultInfo.setResult(user);

        return  resultInfo;
    }

    /**
     * 1 合法
     * 0 不合法
     * @param nick
     * @param userId
     * @return
     */
    public ResultInfo checkNick(String nick, long userId) {
        ResultInfo<Object> info = new ResultInfo<>();
        info.setCode(1);
        if (StrUtil.isBlank(nick)) {
            info.setCode(0);
            return info;
        }

        CnUser user = userDao.getUserByNickAndNoUserId(nick,userId);

        if (user !=null) {
            info.setCode(0);
            return info;
        }

        return info;
    }
}
