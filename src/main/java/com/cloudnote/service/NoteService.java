package com.cloudnote.service;

import com.cloudnote.po.CnNote;
import com.cloudnote.util.Page;
import com.cloudnote.vo.NoteVo;

import java.util.List;

public class NoteService {
    public Page<CnNote> findNoteListByPage(String pageNum, String pageSize, long userId, String title, String date, String typeId) {
        return null;
    }

    public List<NoteVo> findNoteCountByDate(long userId) {
        return null;
    }

    public List<NoteVo> findNoteCountByType(long userId) {
        return null;
    }
}
