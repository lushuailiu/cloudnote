package com.cloudnote.dao;

import com.cloudnote.po.CnNoteType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Luke
 * @DateTime: 4/26/2022 2:20 PM
 * @Description:
 */
public class NoteTypeDao  extends BaseDao {


    public List<CnNoteType> findNoteTypeByUserId(int userId) {
        String sql = "select typeId,typeName,userId from cn_note_type where userId = ?";

        List<Object> parms = new ArrayList<>();
        parms.add(userId);

        List list = queryRows(sql, parms, CnNoteType.class);

        return list;
    }

    public int addType(String typeName, int userId) {
        String sql = "insert into cn_note_type(typeName,userId) values (?,?)";
        List<Object> params = new ArrayList<>();
        params.add(typeName);
        params.add(userId);
        int row = executeUpdate(sql, params);
        return row;
    }

    public int updateType(String typeId, String typeName) {

        String sql = "update cn_note_type set typeName =? where typeId = ?";
        List<Object> params = new ArrayList<>();

        params.add(typeName);
        params.add(typeId);
        int row = executeUpdate(sql, params);
        return row;

    }

    public int deleteById(int id) {
        String sql = "delete from cn_note_type where typeId = ?";
        List<Object> params = new ArrayList<>();
        params.add(id);

        int row = executeUpdate(sql, params);
        return row;
    }
}
