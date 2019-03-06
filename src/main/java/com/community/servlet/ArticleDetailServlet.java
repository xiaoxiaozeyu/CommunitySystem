package com.community.servlet;

import com.community.data.ArticleDao;
import com.community.data.MessageDao;
import com.community.entity.Article;
import com.community.entity.Message;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/detail")

public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String titleID = request.getParameter("titleID");

        ArticleDao articleDao=new ArticleDao();
        Article article = articleDao.articleDetail(titleID);

        MessageDao messageDao=new MessageDao();
        List<Message> messages=messageDao.showThisMessage(titleID);

        HttpSession session=request.getSession();
        session.setAttribute("detail",article);
        session.setAttribute("articleMessage",messages);
        //request.setAttribute("success","成功！");
        //request.getRequestDispatcher("ArticleDetails.jsp").forward(request,response);
        response.sendRedirect("ArticleDetails.jsp");
    }
}
