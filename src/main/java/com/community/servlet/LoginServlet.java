package com.community.servlet;

import com.community.data.UserDao;
import com.community.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")

public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //设置字符编码
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //接收来自页面的数据
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        //验证数据是否输入规范
        if(username==null||username.trim().isEmpty()){
            request.setAttribute("error","用户名为空");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }

        if(password==null||password.trim().isEmpty()){
            request.setAttribute("error","密码为空");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }

        //封装数据
        User user=null;

        //提供服务
        UserDao userDao=new UserDao();
        user=userDao.login(username);

        //验证用户名密码
        if(user==null){
            request.setAttribute("error","用户名不存在!");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }else if (!user.getPassword().equals(password)){
            request.setAttribute("error","密码错误!");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            return;
        }else {
            HttpSession session=request.getSession();
            session.setAttribute("user",user);
            response.sendRedirect("/list");
        }

    }
}
