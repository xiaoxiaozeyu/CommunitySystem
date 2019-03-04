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
import java.util.Date;

@WebServlet("/modifyAction")

public class ModifyActionServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //从jsp获取数据
        String title=request.getParameter("title");
        String content=request.getParameter("content");

        //从session获取username
        HttpSession session=request.getSession();
        User sessionUser= (User) session.getAttribute("user");
        Article sessionArticle= (Article) session.getAttribute("modify");
        String authorname=sessionUser.getUsername();
        Integer id=sessionArticle.getId();

        //从系统获取时间
        Date currentTime=new Date();

        //封装数据
        Article article=new Article();
        article.setId(id);
        article.setTitle(title);
        article.setContent(content);
        article.setAuthorname(authorname);
        article.setPublishtime(currentTime);

        //向数据库发送数据
        ArticleDao articleDao=new ArticleDao();
        boolean isModify=articleDao.modifyArticle(article);
        if(isModify){
            request.setAttribute("success","修改成功！");
            request.getRequestDispatcher("/listMyArticle").forward(request,response);
        }
    }
}
