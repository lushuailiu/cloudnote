package com.cloudnote.service;

import com.cloudnote.dao.NoteTypeDao;
import com.cloudnote.po.CnNote;
import com.cloudnote.util.Page;
import com.cloudnote.vo.NoteVo;

import java.util.List;

public class NoteService {

    private NoteTypeDao noteTypeDao = new NoteTypeDao();

    public Page<CnNote> findNoteListByPage(String pageNum, String pageSize, int userId, String title, String date, String typeId) {
        return null;
    }

    public List<NoteVo> findNoteCountByDate(int userId) {
        return null;
    }

    public List<NoteVo> findNoteCountByType(int userId) {
        return null;
    }
}
