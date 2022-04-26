package com.cloudnote.service;

import cn.hutool.core.util.StrUtil;
import com.cloudnote.controller.NoteTypeServlet;
import com.cloudnote.dao.BaseDao;
import com.cloudnote.dao.NoteTypeDao;
import com.cloudnote.po.CnNote;
import com.cloudnote.po.CnNoteType;
import com.cloudnote.util.Page;
import com.cloudnote.vo.NoteVo;
import com.cloudnote.vo.ResultInfo;
import com.sun.rowset.internal.Row;

import java.util.ArrayList;
import java.util.List;

public class NoteTypeService{

    private NoteTypeDao noteTypeDao = new NoteTypeDao();

    public List<CnNoteType> findNoteType(int userId) {

        List<CnNoteType> list = noteTypeDao.findNoteTypeByUserId(userId);
        return list;
    }

    public ResultInfo addOrUpdate(String typeId, String typeName,int userId) {
        ResultInfo<Object> info = new ResultInfo<>();
        info.setCode(1);
        if (StrUtil.isBlank(typeId)) {
            int row = noteTypeDao.addType(typeName, userId);
            if (row < 1) {
                info.setCode(0);
            }
        }else {
            int i = noteTypeDao.updateType(typeId, typeName);
            if (i < 1) {
                info.setCode(0);
            }
        }
        return info;
    }

    public ResultInfo daleteById(int id) {
        ResultInfo info = new ResultInfo();
        info.setCode(1);

        int row = noteTypeDao.deleteById(id);
        if (row < 1) {
            info.setCode(0);
        }

        return info;
    }
}
