package com.ims.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbUtil {

    private String dbUrl = "jdbc:mysql://localhost:3306/ims_rbps?serverTimezone=UTC";
    private String dbUserName = "root";
    private String dbPassword = "root";
    private String jdbcName = "com.mysql.cj.jdbc.Driver";

    public Connection getCon() throws Exception {
        Class.forName(jdbcName);
        Connection con = DriverManager.getConnection(dbUrl, dbUserName, dbPassword);
        return con;
    }

    public void closeCon(Connection con) throws Exception {
        if (con != null) {
            con.close();
        }
    }

    public static void main(String[] args) {
        DbUtil dbUtil = new DbUtil();
        try {
            dbUtil.getCon();
            System.out.println("连接成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("连接数百");
        }
    }
}
