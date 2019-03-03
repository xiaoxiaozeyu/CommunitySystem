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

@WebServlet("/search")

public class SearchArticleServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String searchtitle = request.getParameter("searchtitle");
        //Article article=null;

        ArticleDao articleDao=new ArticleDao();
       // article=articleDao.searchArticle(searchtitle);
        List<Article> list=articleDao.searchArticle(searchtitle);

        if(list==null||list.size()==0){
            request.setAttribute("error","未找到文章,即将跳转主页！");
            request.getRequestDispatcher("index.jsp").forward(request,response);
            return;
        }else {
            HttpSession session=request.getSession();
            session.setAttribute("search",list);
            request.setAttribute("success","成功！");
            request.getRequestDispatcher("SearchArticles.jsp").forward(request,response);
            return;
        }
    }
}
