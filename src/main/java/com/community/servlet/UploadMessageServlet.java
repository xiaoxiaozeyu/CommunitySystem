package com.community.servlet;

import com.community.data.MessageDao;
import com.community.entity.Message;
import com.community.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@WebServlet("/uploadMessage")

public class UploadMessageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session=request.getSession();
        User sessionUser= (User) session.getAttribute("user");
        String messageContent=request.getParameter("message");
        String articleID=request.getParameter("articleID");
        Date messageTime=new Date();

        Message message=new Message();
        message.setMessage(messageContent);
        message.setMdate(messageTime);
        message.setMessageperson(sessionUser.getUsername());
        message.setArticleid(Integer.parseInt(articleID));

        MessageDao messageDao=new MessageDao();
        boolean isPublishMessage=messageDao.publish(message);
        if(isPublishMessage) {
            session.setAttribute("success", "留言成功！");
            request.getRequestDispatcher("/detail?titleID="+message.getArticleid()).forward(request, response);
        }
    }
}
