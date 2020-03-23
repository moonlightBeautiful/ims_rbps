<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%
    if (session.getAttribute("currentUser") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>系统主界面</title>
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.9.4/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.9.4/themes/icon.css">
    <script type="text/javascript" src="jquery-easyui-1.9.4/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.9.4/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.9.4/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#tree").tree({
                lines: true,
                url: 'auth?action=menu&parentId=-1',
                onLoadSuccess: function () {
                    $("#tree").tree('expandAll');
                }
            });
        });
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height: 68px;">
    <div style="padding: 0px;margin: 0px;background-color: #E0ECFF;">
        <table>
            <tr>
                <td><img src="images/mainlogo.png"/></td>
                <td valign="bottom">欢迎：${currentUser.userName }&nbsp;『${currentUser.roleName }』</td>
            </tr>
        </table>
    </div>
</div>
<div region="center">
    <div class="easyui-tabs" fit="true" border="false" id="tabs">
        <div title="首页" data-options="iconCls:'icon-home'">
            <div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用</font></div>
        </div>
    </div>
</div>
<div region="west" style="width: 220px;padding: 5px;" title="导航菜单" split="true">
    <ul id="tree" class="easyui-tree"></ul>
</div>
<div region="south" style="height: 25px;padding: 5px;" align="center">
    版权所有 2013 Java知识分享网 <a href="http://www.java1234.com" target="_blank">www.java1234.com</a>
</div>
</body>
</html>