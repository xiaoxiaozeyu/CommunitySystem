package com.community.servlet;

import com.community.data.MessageDao;
import com.community.data.UserDao;
import com.community.entity.User;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@WebServlet("/uploadImg")

public class UpdateImgServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String filename = null;
        // 设置上传图片的保存路径
        String savePath = this.getServletContext().getRealPath("/images");
        File file = new File(savePath);
        // 判断上传文件的保存目录是否存在
        if (!file.exists() && !file.isDirectory()) {
            System.out.println(savePath + "目录不存在，需要创建");
            // 创建目录
            file.mkdir();
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 2、创建一个文件上传解析器
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        // 3、判断提交上来的数据是否是上传表单的数据
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 按照传统方式获取数据
            return;
        }
        try {
            List<FileItem> list = upload.parseRequest(request);
            System.out.println(list.toString());// 文件的路径 以及保存的路径
            for (FileItem item : list) {
                filename = item.getName();// 获得一个项的文件名称
                if (filename == null || filename.trim().equals("")) {// 如果为空則跳过
                    continue;
                }
                // 报错 需要过滤文件名称 java.io.FileNotFoundException:
                // G:\测试02\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\FaceUp\WEB-INF\images\C:\Users\Ray\Pictures\2.jpeg
                // (文件名、目录名或卷标语法不正确。)

                filename = filename.substring(filename.lastIndexOf("/") + 1);
                //System.out.print(filename);
                if (filename.substring(filename.lastIndexOf(".") + 1).equals("png")
                        || filename.substring(filename.lastIndexOf(".") + 1).equals("jpg")
                        || filename.substring(filename.lastIndexOf(".") + 1).equals("jpeg")) {
                    InputStream in = item.getInputStream();// 获得上传的输入流
                    FileOutputStream out = new FileOutputStream(savePath + "/" + filename);// 指定web-inf目录下的images文件
                    request.setAttribute("path",  "/images"+"/" + filename);

                    //System.out.println("/images"+"/" + filename);
                    int len = 0;
                    byte buffer[] = new byte[1024];
                    while ((len = in.read(buffer)) > 0)// 每次读取
                    {
                        out.write(buffer, 0, len);
                    }
                    in.close();
                    out.close();
                    item.delete();
                } else {  //必须是图片才能上传否则失败
                    return ;
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }

        String imgpath= (String) request.getAttribute("path");

       // System.out.println(imgpath);
        HttpSession session=request.getSession();
        User sessionUser= (User) session.getAttribute("user");

        UserDao userDao=new UserDao();
        boolean isUpdateUserDao=userDao.updateImgPath(imgpath,sessionUser.getId());
        User currentUser=userDao.login(sessionUser.getUsername());


        MessageDao messageDao=new MessageDao();
        boolean isHasMessage=messageDao.isHasMessage(sessionUser.getUsername());
        boolean isUpdateMessageDao=true;
        if(isHasMessage){
            isUpdateMessageDao=messageDao.updateImgPath(imgpath,sessionUser.getUsername());
        }
        //System.out.println(isHasMessage);
        //System.out.println(isUpdateMessageDao);
        //System.out.println(isUpdateUserDao);

        if(isUpdateUserDao&&isUpdateMessageDao){
            session.removeAttribute("user");
            session.setAttribute("user",currentUser);
            session.setAttribute("success","头像修改成功！");
            response.sendRedirect("PersonalCenter.jsp");
        }else {
            session.setAttribute("error","头像修改失败！");
            response.sendRedirect("PersonalCenter.jsp");
        }
    }
}
