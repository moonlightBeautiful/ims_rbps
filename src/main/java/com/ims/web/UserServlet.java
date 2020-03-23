package com.ims.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ims.dao.UserDao;
import com.ims.model.User;
import com.ims.util.DbUtil;
import com.ims.util.StringUtil;

public class UserServlet extends HttpServlet {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    DbUtil dbUtil = new DbUtil();
    UserDao userDao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if ("login".equals(action)) {
            login(request, response);
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String imageCode = request.getParameter("imageCode");
        request.setAttribute("userName", userName);
        request.setAttribute("password", password);
        request.setAttribute("imageCode", imageCode);
        if (StringUtil.isEmpty(userName) || StringUtil.isEmpty(password)) {
            request.setAttribute("error", "用户名或密码为空！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if (StringUtil.isEmpty(imageCode)) {
            request.setAttribute("error", "验证码为空！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if (!imageCode.equals(session.getAttribute("sRand"))) {
            request.setAttribute("error", "验证码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        User user = new User(userName, password);
        Connection con = null;
        try {
            con = dbUtil.getCon();
            User currentUser = userDao.login(con, user);
            if (currentUser == null) {
                request.setAttribute("error", "用户名或密码错误！");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                session.setAttribute("currentUser", currentUser);
                response.sendRedirect("main.jsp");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
