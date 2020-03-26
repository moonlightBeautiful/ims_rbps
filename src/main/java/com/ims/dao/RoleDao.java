package com.ims.dao;

import com.ims.model.PageBean;
import com.ims.model.Role;
import com.ims.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RoleDao {

    /**
     * 查询用户角色名
     *
     * @param con
     * @param roleId
     * @return
     * @throws Exception
     */
    public String getRoleNameById(Connection con, int roleId) throws Exception {
        String roleName = null;
        String sql = "select roleName from t_role where roleId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, roleId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            roleName = rs.getString("roleName");
        }
        return roleName;
    }

    /**
     * 查询左侧菜单
     *
     * @param con
     * @param roleId
     * @return
     * @throws Exception
     */
    public String getAuthIdsById(Connection con, int roleId) throws Exception {
        String authIds = null;
        String sql = "select authIds from t_role where roleId=?";
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, roleId);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            authIds = rs.getString("authIds");
        }
        return authIds;
    }

    public ResultSet roleList(Connection con, PageBean pageBean, Role role) throws Exception {
        StringBuffer sb = new StringBuffer("select * from t_role");
        if (StringUtil.isNotEmpty(role.getRoleName())) {
            sb.append(" and roleName like '%" + role.getRoleName() + "%'");
        }
        if (pageBean != null) {
            sb.append(" limit " + pageBean.getStart() + "," + pageBean.getRows());
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        return pstmt.executeQuery();
    }

    public int roleCount(Connection con, Role role) throws Exception {
        StringBuffer sb = new StringBuffer("select count(*) as total from t_role ");
        if (StringUtil.isNotEmpty(role.getRoleName())) {
            sb.append(" and roleName like '%" + role.getRoleName() + "%'");
        }
        PreparedStatement pstmt = con.prepareStatement(sb.toString().replaceFirst("and", "where"));
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("total");
        } else {
            return 0;
        }
    }
}
