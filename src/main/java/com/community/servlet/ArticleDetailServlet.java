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

@WebServlet("/detail")

public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String tiitleID = request.getParameter("titleID");

        ArticleDao articleDao=new ArticleDao();
        Article article = articleDao.articleDetail(tiitleID);

        HttpSession session=request.getSession();
        session.setAttribute("detail",article);
        request.setAttribute("success","成功！");
        request.getRequestDispatcher("ArticleDetails.jsp").forward(request,response);
    }
}
