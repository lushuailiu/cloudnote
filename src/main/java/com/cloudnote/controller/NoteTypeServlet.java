package com.cloudnote.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.cloudnote.dao.NoteTypeDao;
import com.cloudnote.po.CnNote;
import com.cloudnote.po.CnNoteType;
import com.cloudnote.po.CnUser;
import com.cloudnote.service.NoteService;
import com.cloudnote.service.NoteTypeService;
import com.cloudnote.util.Page;
import com.cloudnote.vo.ResultInfo;
import org.apache.logging.log4j.core.util.JsonUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @Author: Luke
 * @DateTime: 4/26/2022 3:03 PM
 * @Description: TODO
 */
@WebServlet("/type")
public class NoteTypeServlet extends HttpServlet {

    private NoteTypeService typeService = new NoteTypeService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置首页导航高亮
        request.setAttribute("menu_page", "note");
        // 接收用户行为
        String actionName = request.getParameter("actionName"); // 参数名前后保持一致(前台接收什么，后台就是什么)

        if ("list".equals(actionName)) {
            typeList(request,response);
        } else if ("addOrUpdate".equals(actionName)) {
            typeAddOrUpdate(request,response);
        } else if ("delete".equals(actionName)) {
            deleteType(request, response);
        }

    }

    private void deleteType(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeId = request.getParameter("typeId");
        int id = Integer.parseInt(typeId);

        ResultInfo info = typeService.daleteById(id);

        PrintWriter writer = response.getWriter();
        JSONUtil.toJsonStr(info,writer);

    }

    private void typeAddOrUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String typeName = request.getParameter("typeName");
        String typeId = request.getParameter("typeId");

        HttpSession session = request.getSession();
        CnUser user = (CnUser)session.getAttribute("user");
        ResultInfo info = typeService.addOrUpdate(typeId,typeName,user.getUserId());
        PrintWriter writer = response.getWriter();

        String s = JSONUtil.toJsonStr(info);
        writer.write(s);
    }

    private void typeList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        CnUser user = (CnUser)session.getAttribute("user");


        List<CnNoteType> typeList = typeService.findNoteType(user.getUserId());

        request.setAttribute("typeList",typeList);

        request.setAttribute("changePage","type/list.jsp");

        request.getRequestDispatcher("index.jsp").forward(request,response);

    }

}
