package com.community.servlet;

import com.community.data.ArticleDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete")

public class DeleteMyArticleServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        //删除多个数据时回传多个ID，因此放入数组中
        String[] deleteIDs = request.getParameterValues("deleteID");

        String deleteID = null;
        if(deleteIDs==null){
            deleteID="";
        }else {
            //创建一个StringBuffer类型的字符串
            StringBuffer buffer = new StringBuffer();
            for(int i=0;i<deleteIDs.length;i++){
                //通过for循环，将数据追加到StringBuffer数组中
                buffer=buffer.append(deleteIDs[i].toString()+"," );

            }
            //截取字符串
            deleteID=buffer.substring(0,buffer.length()-1);
        }

        //提供服务
        ArticleDao articleDao=new ArticleDao();
        boolean isDelete=articleDao.deleteMyArticle(deleteID);

        if(isDelete){
            request.setAttribute("success","删除成功！");
            request.getRequestDispatcher("ManageMyArticle.jsp").forward(request,response);
        }else {
            request.setAttribute("error","删除失败！");
            request.getRequestDispatcher("ManageMyArticle.jsp").forward(request,response);
        }


    }
}
