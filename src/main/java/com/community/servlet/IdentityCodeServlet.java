package com.community.servlet;

import com.community.util.GraphicHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/identityCode/*")

public class IdentityCodeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取当前请求关联的会话(session)对象
        HttpSession session = request.getSession();

        // 设置向客户端( 浏览器 )发送的字节内容的类型 ( MIME 类型 )
        response.setContentType( "image/jpeg" );

        // 从 响应对象 ( resp ) 中获取可以向 Web客户端 ( 浏览器 ) 输出 字节数据的输出流
        OutputStream out = response.getOutputStream();

        //  调用 GraphicHelper 类的 create 方法 创建验证码图片，并将其输出到客户端(浏览器)，最后返回验证码内容
        String identifyCode = GraphicHelper.create( 4 , false , 180 , 35 , out , 50 );

        // 将验证码图片上的内容保存到当前会话中
        session.setAttribute( "IDENTITY_CODE" , identifyCode );

    }
}
