package com.ims.web;

import com.ims.dao.RoleDao;
import com.ims.dao.UserDao;
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
    UserDao userDao = new UserDao();

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
        } else if ("delete".equals(action)) {
            roleDelete(request, response);
        } else if ("save".equals(action)) {
            roleSave(request, response);
        } else if ("auth".equals(action)) {
            auth(request, response);
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
            jsonObject.put("roleName", "请选择...");
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

    private void roleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String delIds = request.getParameter("delIds");
        Connection con = null;
        try {
            con = dbUtil.getCon();
            JSONObject result = new JSONObject();
            String str[] = delIds.split(",");
            for (int i = 0; i < str.length; i++) {
                boolean f = userDao.existUserWithRoleId(con, str[i]);
                if (f) {
                    result.put("errorIndex", i);
                    result.put("errorMsg", "角色下面有用户，不能删除！");
                    ResponseUtil.write(response, result);
                    return;
                }
            }
            int delNums = roleDao.roleDelete(con, delIds);
            if (delNums > 0) {
                result.put("success", true);
                result.put("delNums", delNums);
            } else {
                result.put("errorMsg", "删除失败");
            }
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

    private void roleSave(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleName = request.getParameter("roleName");
        String roleDescription = request.getParameter("roleDescription");
        String roleId = request.getParameter("roleId");
        Role role = new Role(roleName, roleDescription);
        if (StringUtil.isNotEmpty(roleId)) {
            role.setRoleId(Integer.parseInt(roleId));
        }
        Connection con = null;
        try {
            JSONObject result = new JSONObject();
            con = dbUtil.getCon();
            int saveNums = 0;
            if (StringUtil.isNotEmpty(roleId)) {
                saveNums = roleDao.roleUpdate(con, role);
            } else {
                saveNums = roleDao.roleAdd(con, role);
            }
            if (saveNums > 0) {
                result.put("success", true);
            } else {
                result.put("success", true);
                result.put("errorMsg", "保存失败");
            }
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

    private void auth(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String roleId = request.getParameter("roleId");
        String authIds = request.getParameter("authIds");
        Role role = new Role(Integer.parseInt(roleId), authIds);
        Connection con = null;
        try {
            JSONObject result = new JSONObject();
            con = dbUtil.getCon();
            int updateNums = roleDao.roleAuthIdsUpdate(con, role);
            if (updateNums > 0) {
                result.put("success", true);
            } else {
                result.put("errorMsg", "授权失败");
            }
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
