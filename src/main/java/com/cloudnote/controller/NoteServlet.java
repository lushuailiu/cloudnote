package com.cloudnote.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.MD5;
import com.cloudnote.po.CnNoteType;
import com.cloudnote.po.CnUser;
import com.cloudnote.service.NoteService;
import com.cloudnote.service.NoteTypeService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/note")
@MultipartConfig
public class NoteServlet extends HttpServlet {

    private NoteService noteService = new NoteService();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // 设置首页导航高亮
        request.setAttribute("menu_page", "note");
        // 接收用户行为
        String actionName = request.getParameter("actionName"); // 参数名前后保持一致(前台接收什么，后台就是什么)

        if ("view".equals(actionName)) {
            view(request,response);
        }else if ("upload".equals(actionName)){
            upload(request,response);
        }

    }

    private void upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Part part = request.getPart("");
        String header = part.getHeader("Content-Disposition");

        String[] split = header.split(";");
        String filename = split[2].substring(split[2].indexOf("=")+1).substring(split[2].lastIndexOf("//")+1).replace("\"", "");
        String ext = filename.substring(filename.lastIndexOf(".")+1);


        //MD5 md5 = MD5.create();
        if ( !StrUtil.isBlank(filename)) {
            String filePath = request.getServletContext().getRealPath("WEB-INF/upload/");

            part.write(filePath + "\\" + filename);
        }
    }

    private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

       CnUser user = (CnUser) request.getSession().getAttribute("user");

        List<CnNoteType> typeList = new NoteTypeService().findNoteType(user.getUserId());

        request.setAttribute("changePage","note/view.jsp");
        request.setAttribute("typeList",typeList);

        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
