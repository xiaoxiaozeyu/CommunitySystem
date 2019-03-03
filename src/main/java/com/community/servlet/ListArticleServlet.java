package com.community.servlet;

import com.community.data.ArticleDao;
import com.community.entity.Article;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/list")

public class ListArticleServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        ArticleDao articleDao=new ArticleDao();
        List<Article> list=articleDao.showArticle();

        HttpSession session=request.getSession();
        session.setAttribute("list",list);
        response.sendRedirect("index.jsp");
    }
}
