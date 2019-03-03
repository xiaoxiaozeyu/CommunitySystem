package com.community.servlet;

import com.community.data.UserDao;
import com.community.entity.User;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")

public class RegistrationServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置请求和响应的编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //接收来自页面的数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");


        //数据校验
        //检验密码和确认密码输入是否一致
        if(!password.trim().equals(confirm.trim())){//注意 ：这里有个感叹号
            //页面通过重定向到某个页面
            request.setAttribute("error","两次密码不一致，注册失败！");
            request.getRequestDispatcher("registration.jsp").forward(request,response);
            return;
        }

        //检验用户名是否重名
        /*
        //方法一：（利用login来进行检验）
        User queryUser = null;
        UserDao queryUserDao=new UserDao();
        queryUser=queryUserDao.login(username);
        if(queryUser!=null&&username.equals(queryUser.getUsername().trim())){
            request.setAttribute("error","用户已存在！");
            request.getRequestDispatcher("registration.jsp").forward(request,response);
            return;
        }
        */
        //方法二：自定义用户名检验函数
        UserDao queryUserDao=new UserDao();
        boolean queryUsername=queryUserDao.isExistUsername(username);
        if(queryUsername){
            request.setAttribute("error","用户已存在！");
            request.getRequestDispatcher("registration.jsp").forward(request,response);
            return;
        }

        //数据封装
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        //向数据库发送数据
        UserDao userDao = new UserDao();
        //调用UserDao的save方法将user对象保存
        int i = userDao.save(user);

        if(i==1){
            request.setAttribute("success","注册成功，即将跳转登陆界面！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }else {
            request.setAttribute("error","对不起，数据库繁忙，注册失败！");
            request.getRequestDispatcher("registration.jsp").forward(request,response);
            return;
        }

    }
}
