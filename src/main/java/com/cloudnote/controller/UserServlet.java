package com.cloudnote.controller;

import cn.hutool.core.util.StrUtil;
import com.cloudnote.po.CnUser;
import com.cloudnote.service.UserService;
import com.cloudnote.vo.ResultInfo;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    // web层调用 service层
    private UserService userService = new UserService();




    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页导航高亮
        request.setAttribute("menu_page", "user");
        // 接收用户行为
        String actionName = request.getParameter("actionName"); // 参数名前后保持一致(前台接收什么，后台就是什么)
        // 判断用户行为 调用对应方法
        if ("login".equals(actionName)) {

            // 用户登录
            userLogin(request, response);
        } else if ("logout".equals(actionName)) {
            // 用户退出登录
            userLogOut(request, response);
        } else if ("userCenter".equals(actionName)) {
            // 进入个人中心
            userCenter(request, response);
        } else if ("userHead".equals(actionName)) {
            // 加载头像
            userHead(request, response);
        } else if ("checkNick".equals(actionName)) {
            // 验证昵称唯一性
            checkNick(request, response);
        } else if ("updateUser".equals(actionName)) {
            // 修改用户信息
            updateUser(request, response);
        }

    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) {
    }

    private void checkNick(HttpServletRequest request, HttpServletResponse response) {
    }

    private void userHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String imageName = request.getParameter("imageName");
        if(StrUtil.isBlank(imageName)) {
            String realPath = request.getServletContext().getRealPath("/WEB-INF/upload/");
            realPath += "404.jpg";
            File file = new File(realPath);
            response.setContentType( "image/jpeg");
            FileUtils.copyFile(file,response.getOutputStream());
        }
    }

    private void userCenter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 设置首页导航高亮
        request.setAttribute("menu_page", "user");

        request.setAttribute("changePage","user/info.jsp");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

    private void userLogOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //让session失效
        request.getSession().invalidate();
        //删除cookie
        Cookie cookie = new Cookie("user",null);
        cookie.setMaxAge(0);
        response.sendRedirect("login.jsp");
    }

    /**
     *  用户登录
     Web层：（控制层：接收参数、响应数据）
     1. 获取参数 （姓名、密码）
     2. 调用Service层的方法，返回ResultInfo对象
     3. 判断是否登录成功
     如果失败
     将resultInfo对象设置到request作用域中
     请求转发跳转到登录页面
     如果成功
     将用户信息设置到session作用域中
     判断用户是否选择记住密码（rem的值是1）
     如果是，将用户姓名与密码存到cookie中，设置失效时间，并响应给客户端
     如果否，清空原有的cookie对象
     重定向跳转到index页面
     * @param request
     * @param response
     */
    private void userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //  1. 获取参数 （姓名、密码）
        String userName = request.getParameter("userName"); // 参数名前后保持一致(前台接收什么，后台就是什么 在login.jsp查看)
        String userPwd = request.getParameter("userPwd");

        ResultInfo<CnUser> resultInfo = userService.userLogin(userName, userPwd);


        if (resultInfo.getCode() == 0) {
            //如果登录失败
            request.setAttribute("resultInfo",resultInfo);
            request.getRequestDispatcher("login.jsp").forward(request,response);
        } else {
            //如果登录成功
            //设置user信息到cookie中
            request.getSession().setAttribute("user",resultInfo.getResult());

            //获取是否选择记住密码
            String rem = request.getParameter("rem");
            if ("1".equals(rem)) {
                Cookie cookie = new Cookie("user", userName + "-" + userPwd);
                //设置失效时间60s
                cookie.setMaxAge(60);
                // 响应给客户端
                response.addCookie(cookie);
            } else {
                // 如果否，清空原有的cookie对象
                Cookie cookie = new Cookie("user", null);
                // 删除 cookie 设置 MaxAge 为 0
                cookie.setMaxAge(0);
                // 响应给客户端
                response.addCookie(cookie);
            }
                // 重定向跳转到index页面
                response.sendRedirect("index");
        }
    }
}
