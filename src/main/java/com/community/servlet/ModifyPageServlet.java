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

@WebServlet("/modifyPage")

public class ModifyPageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String titleID = request.getParameter("titleID");

        ArticleDao articleDao=new ArticleDao();
        Article article = articleDao.articleDetail(titleID);

        if(article==null){
            request.setAttribute("error","数据库繁忙，修改失败！");
            request.getRequestDispatcher("ManageMyArticle.jsp").forward(request,response);
        }
        HttpSession session=request.getSession();
        session.setAttribute("modify",article);
        response.sendRedirect("AlterArticle.jsp");
    }
}
