package com.ims.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDao {

    public String getRoleNameById(Connection con, int id) throws Exception {
        String roleName = null;
        String sql = "select roleName from t_role where roleId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            roleName = rs.getString("roleName");
        }
        return roleName;
    }

    public String getAuthIdsById(Connection con, int id) throws Exception {
        String authIds = null;
        String sql = "select authIds from t_role where roleId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            authIds = rs.getString("authIds");
        }
        return authIds;
    }

    public ResultSet roleList(Connection con) throws Exception {
        String sql = "select * from t_role";
        PreparedStatement pstmt = con.prepareStatement(sql);
        return pstmt.executeQuery();
    }
}
