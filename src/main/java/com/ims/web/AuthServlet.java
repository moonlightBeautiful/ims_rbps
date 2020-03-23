package com.ims.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ims.dao.AuthDao;
import com.ims.dao.RoleDao;
import com.ims.model.User;
import com.ims.util.DbUtil;
import com.ims.util.ResponseUtil;
import net.sf.json.JSONArray;



public class AuthServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DbUtil dbUtil=new DbUtil();
	AuthDao authDao=new AuthDao();
	RoleDao roleDao=new RoleDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("menu".equals(action)){
			this.menuAction(request, response);
		}
	}

	private void menuAction(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parentId=request.getParameter("parentId");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			HttpSession session=request.getSession();
			User currentUser=(User)session.getAttribute("currentUser");
			String authIds=roleDao.getAuthIdsById(con, currentUser.getRoleId());
			JSONArray jsonArray=authDao.getAuthsByParentId(con, parentId,authIds);
			ResponseUtil.write(response, jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
