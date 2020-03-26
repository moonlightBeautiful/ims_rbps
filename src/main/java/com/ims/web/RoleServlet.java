package com.ims.web;

import com.ims.dao.RoleDao;
import com.ims.model.PageBean;
import com.ims.model.Role;
import com.ims.util.DbUtil;
import com.ims.util.JsonUtil;
import com.ims.util.ResponseUtil;
import com.ims.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

public class RoleServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DbUtil dbUtil = new DbUtil();
    RoleDao roleDao = new RoleDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("comBoList".equals(action)) {
            comBoList(request, response);
        } else if ("list".equals(action)) {
            roleList(request, response);
        }
    }

    private void comBoList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection con = null;
        try {
            con = dbUtil.getCon();
            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("roleId", "");
            jsonObject.put("roleName", "«Î—°‘Ò...");
            jsonArray.add(jsonObject);
            ResultSet rs = roleDao.roleList(con, null, new Role());
            jsonArray.addAll(JsonUtil.formatRsToJsonArray(rs));
            ResponseUtil.write(response, jsonArray);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void roleList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String page = request.getParameter("page");
        String rows = request.getParameter("rows");
        String s_roleName = request.getParameter("s_roleName");

        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));

        Role role = new Role();
        if (StringUtil.isNotEmpty(s_roleName)) {
            role.setRoleName(s_roleName);
        }

        Connection con = null;
        try {
            con = dbUtil.getCon();
            JSONObject result = new JSONObject();
            ResultSet rs = roleDao.roleList(con, pageBean, role);
            JSONArray jsonArray = JsonUtil.formatRsToJsonArray(rs);
            int total = roleDao.roleCount(con, role);
            result.put("rows", jsonArray);
            result.put("total", total);
            ResponseUtil.write(response, result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbUtil.closeCon(con);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
