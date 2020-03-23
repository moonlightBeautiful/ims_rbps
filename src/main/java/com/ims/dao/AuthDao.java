package com.ims.dao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuthDao {

    public JSONArray getAuthsByParentId(Connection con, String parentId) throws Exception {
        JSONArray jsonArray = new JSONArray();
        String sql = "select * from t_auth where parentId=?";
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

    public JSONArray getAllAuthsByParentId(Connection con, String parentId) throws Exception {
        JSONArray jsonArray = this.getAuthsByParentId(con, parentId);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if ("open".equals(jsonObject.getString("state"))) {
                continue;
            } else {
                jsonObject.put("children", getAllAuthsByParentId(con, jsonObject.getString("id")));
            }
        }
        return jsonArray;
    }
}
