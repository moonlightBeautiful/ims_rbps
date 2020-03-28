package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.ims.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AuthDao {

    /**
     * 子菜单
     *
     * @param con
     * @param parentId
     * @param authIds
     * @return
     * @throws Exception
     */
    public JSONArray getAuthByParentId(Connection con, String parentId, String authIds) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String sql = "select * from t_auth where parentId=? and authId in (" + authIds + ")";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, parentId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", rs.getInt("authId"));
            jsonObject.put("text", rs.getString("authName"));
            jsonObject.put("state", rs.getString("state"));
            jsonObject.put("iconCls", rs.getString("iconCls"));
            JSONObject attributeObject = new JSONObject();
            attributeObject.put("authPath", rs.getString("authPath"));
            jsonObject.put("attributes", attributeObject);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * 递归 子孙后代菜单
     *
     * @param con
     * @param parentId
     * @param authIds
     * @return
     * @throws Exception
     */
    public JSONArray getAuthsByParentId(Connection con, String parentId, String authIds) throws Exception {
        JSONArray jsonArray = this.getAuthByParentId(con, parentId, authIds);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("open".equals(jsonObject.getString("state"))) {
                continue;
            } else {
                jsonObject.put("children", getAuthsByParentId(con, jsonObject.getString("id"), authIds));
            }
        }
        return jsonArray;
    }

    public JSONArray getCheckedAuthByParentId(Connection con, String parentId, String authIds) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String sql = "select * from t_auth where parentId=? ";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1, parentId);
        ResultSet rs = pstmt.executeQuery();
        while (rs.next()) {
            JSONObject jsonObject = new JSONObject();
            int authId = rs.getInt("authId");
            jsonObject.put("id", authId);
            jsonObject.put("text", rs.getString("authName"));
            jsonObject.put("state", rs.getString("state"));
            jsonObject.put("iconCls", rs.getString("iconCls"));
            if (StringUtil.existStrArr(authId + "", authIds.split(","))) {
                jsonObject.put("checked", true);
            }
            JSONObject attributeObject = new JSONObject();
            attributeObject.put("authPath", rs.getString("authPath"));
            jsonObject.put("attributes", attributeObject);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public JSONArray getCheckedAuthsByParentId(Connection con, String parentId, String authIds) throws Exception {
        JSONArray jsonArray = this.getCheckedAuthByParentId(con, parentId, authIds);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("open".equals(jsonObject.getString("state"))) {
                continue;
            } else {
                jsonObject.put("children", getCheckedAuthsByParentId(con, jsonObject.getString("id"), authIds));
            }
        }
        return jsonArray;
    }
}
