1.需求和数据库设计
    管理员：用户管理、角色管理、菜单管理
    业务上有：学生管理和课程管理
2.登陆实现
    验证码生成 image标签src获取图片的输出流
    验证码保存在 session中
3.主页面
    easyui树 



学习到：
    1.验证码
    2.easyui树菜单  叶子节点 open 非叶子节点 closed也行 open也行
    3.主页面请求过滤
    4.超链接执行js方法 href="javascript:searchUser()"
    5.input text的回车事件 onkeydown="if(event.keyCode==13) searchUser()"
    6.分页实体类的start计算=(page-1)*rows
    7.添加修改等需要弹出框用 easyui-dialog，在里面写form等
    8.disabled 不会传后台 readonly 传后台
    9.递归学习：方法A，获取子。方法B，调用A和B。