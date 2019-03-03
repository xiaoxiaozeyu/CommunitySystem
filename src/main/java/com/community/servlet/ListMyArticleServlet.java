package com.community.servlet;

import com.community.data.ArticleDao;
import com.community.entity.Article;
import com.community.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/listMyArticle")

public class ListMyArticleServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session=request.getSession();
        User currentUser= (User) session.getAttribute("user");
        String username=currentUser.getUsername();

        ArticleDao articleDao=new ArticleDao();
        List<Article> list=articleDao.showMyArticle(username);
        if(list==null||list.size()==0){
            request.setAttribute("error","当前未发表文章，即将跳转发表界面。");
            request.getRequestDispatcher("PublishArticle.jsp").forward(request,response);
            return;
        }
        session.setAttribute("myarticle",list);
        response.sendRedirect("ManageMyArticle.jsp");


    }
}
