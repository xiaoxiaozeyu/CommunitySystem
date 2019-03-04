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

@WebServlet("/updatePassword")

public class UpdatePasswordServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String oldPassword = request.getParameter("oldPassword");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirm");

        HttpSession session=request.getSession();
        User sessionUser = (User) session.getAttribute("user");
        System.out.println(sessionUser.getUsername()+sessionUser.getPassword());


        if(!sessionUser.getPassword().equals(oldPassword)){
            System.out.println(sessionUser.getPassword());
            System.out.println(oldPassword);
            System.out.println(!sessionUser.getPassword().equals(oldPassword));
            request.setAttribute("error","旧密码输入错误，修改失败！");
            request.getRequestDispatcher("AlterPassword.jsp").forward(request,response);
            return;
        }

        //数据校验
        //检验密码和确认密码输入是否一致
        if(!password.trim().equals(confirm.trim())){//注意 ：这里有个感叹号
            //页面通过重定向到某个页面
            request.setAttribute("error","两次密码不一致，修改失败！");
            request.getRequestDispatcher("AlterPassword.jsp").forward(request,response);
            return;
        }

        UserDao userDao=new UserDao();
        boolean isUpdate = userDao.updatePassword(password,sessionUser.getId());

        if(isUpdate){
            //页面通过重定向到某个页面
            request.setAttribute("success","修改成功，请重新登陆！");
            request.getRequestDispatcher("login.jsp").forward(request,response);
            session.removeAttribute("user");
            return;
        }else {
            //页面通过重定向到某个页面
            request.setAttribute("error","数据库繁忙，修改失败！");
            request.getRequestDispatcher("AlterPassword.jsp").forward(request,response);
            return;
        }



    }
}
